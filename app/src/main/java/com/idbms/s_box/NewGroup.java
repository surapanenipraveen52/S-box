package com.idbms.s_box;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.idbms.s_box.newgroup.CreateNewGroup;

import java.util.ArrayList;


public class NewGroup extends Activity {

    private EditText etUserName;
    private EditText etGroupName;
    private Button btnAdd;
    private Button btnCreate;
    private ListView lvItem;
    private ArrayList<String> itemArray;
    private ArrayAdapter<String> itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        setUpView();
    }
    private void setUpView() {


        etUserName = (EditText)this.findViewById(R.id.username);
        etGroupName = (EditText)this.findViewById(R.id.groupname);
        btnAdd = (Button)this.findViewById(R.id.addmembers);
        lvItem = (ListView)this.findViewById(R.id.listView);
        btnCreate = (Button)this.findViewById(R.id.create);
        itemArray = new ArrayList<String>();
        itemArray.clear();
        itemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,itemArray);
        lvItem.setAdapter(itemAdapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                addItemList();
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (isInputValid(etGroupName)) {
                  new CreateNewGroup(etGroupName.getText().toString(),NewGroup.this,itemArray).execute();
                }
            }
        });
    }
    protected void addItemList() {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub
        if (isInputValid(etUserName)) {
            itemArray.add(0, etUserName.getText().toString());
            etUserName.setText("");
            itemAdapter.notifyDataSetChanged();

        }
    }
    protected boolean isInputValid(EditText etInput2) {
        // TODO Auto-generatd method stub
        if (etInput2.getText().toString().trim().length()<1) {
            etInput2.setError("Please Enter Valid Name");
            return false;
        } else {
            return true;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
