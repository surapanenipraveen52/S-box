package com.idbms.s_box;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.idbms.s_box.amazonrds.GetFiles;
import com.idbms.s_box.amazons3.DownloadFile;
import com.idbms.s_box.amazons3.UploadFile;
import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;


public class FilesList extends ActionBarActivity {
    ListView lv;
    public static String groupName;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final String TAG ="FilesList" ;
    private static final int REQUEST_CODE = 6384; // onActivityResult request

    /*@Override
    public void onContentChanged() {
        super.onContentChanged();
        View empty = findViewById(R.id.empty);
        lv=(ListView)this.findViewById(R.id.files_list);
        lv.setEmptyView(empty);
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files_list);
        groupName=getIntent().getExtras().getString("groupName");
        lv=(ListView)this.findViewById(R.id.files_list);
        Log.v(TAG, "File path is " + this.getFilesDir());
        final Context c=this;
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Log.v(TAG, "Clicked id " + lv.getItemAtPosition(position).toString());
                String fileName = lv.getItemAtPosition(position).toString();
                new DownloadFile(groupName,fileName, c).execute();
            }
        });
        new GetFiles(groupName,this,lv).execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_files_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_settings:
                return true;
            case R.id.new_file:
                Log.v(TAG, "new file");
                Intent target = FileUtils.createGetContentIntent();
                // Create the chooser Intent
                Intent intent = Intent.createChooser(
                        target, "Choose file");
                try {
                    startActivityForResult(intent, REQUEST_CODE);
                } catch (ActivityNotFoundException e) {
                    // The reason for the existence of aFileChooser
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if (resultCode == RESULT_OK) {
           if (data != null) {
               // Get the URI of the selected file
               final Uri uri = data.getData();
               try {
                   // Get the file path from the URI
                   final String path = FileUtils.getPath(this, uri);
                   Log.v(TAG, "Selected path si "+path);
                   String fileName=path.substring(path.lastIndexOf("/")+1);
                   new UploadFile(path,groupName,MainActivity.loginId,fileName,this,lv).execute();
               }catch (Exception e) {
               }

           }

           }

    }
}
