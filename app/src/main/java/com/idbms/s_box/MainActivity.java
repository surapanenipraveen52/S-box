package com.idbms.s_box;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.idbms.s_box.amazonrds.GetGroups;


public class MainActivity extends ActionBarActivity {

    private static final String TAG ="MainActivity" ;
    public static final String loginId ="surapanenipraveen@gmail.com" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetGroups("surapanenipraveen@gmail.com",this,(ListView)this.findViewById(R.id.groups_list)).execute();
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
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
