package com.idbms.s_box;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.idbms.s_box.amazonrds.GetGroups;


public class MainActivity extends ActionBarActivity {

    private static final String TAG ="MainActivity" ;
    public static String loginId ="surapanenipraveen@gmail.com" ;
    ListView lv;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        setContentView(R.layout.activity_main);
        lv=(ListView)this.findViewById(R.id.groups_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Log.v(TAG, "Clicked id "+lv.getItemAtPosition(position).toString());
                Intent intent = new Intent(getApplicationContext(),FilesList.class);
                intent.putExtra("groupName", lv.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });
        new GetGroups("surapanenipraveen@gmail.com",this,lv).execute();

        /*Button newGroup= (Button)findViewById(R.id.newgroup);
        newGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "search onclick");
                Intent i = new Intent(getApplicationContext(),NewGroup.class);
                startActivity(i);
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_settings:
                return true;
            case R.id.new_group:
                Log.v(TAG, "search onclick");
                Intent i = new Intent(getApplicationContext(),NewGroup.class);
                startActivityForResult(i,1);
//                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(TAG,"onActiivtyResult");
        new GetGroups("surapanenipraveen@gmail.com",this,lv).execute();

    }
}