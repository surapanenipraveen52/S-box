package com.idbms.s_box.amazonrds;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by praveen on 3/24/2015.
 */
public class GetGroups extends AsyncTask {
    private static final String TAG = "GetGroups";
    Context mContext;
    String userName;
    List<String> listGroups;
    public ListView lvItem;
    public ArrayAdapter<String> itemAdapter;
    public GetGroups(String UserName, Context context, ListView lv) {
        Log.v(TAG,"GetGroups Constructor");
        userName=UserName;
        mContext=context;
        lvItem=lv;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        listGroups = new ArrayList<String>();
        Log.v(TAG, "Size is "+ listGroups.size());
        String query="Select GroupName from mydb.UserGroups where userid = '"+userName+"'";
        RunQuery runQuery=new RunQuery();
        boolean isConnectionOpen = runQuery.openRDSConnection();
        if(isConnectionOpen){
            ResultSet resultSet = runQuery.runSelect(query);
            if(null==resultSet){
                return null;
            }
            try{
                Log.v(TAG, "Result set is not null");
                while (resultSet.next()){
                    String oneGroup=resultSet.getString("GroupName");
                    listGroups.add(oneGroup);
                }
                runQuery.closeConnection();
            }catch (SQLException se){
                Log.v(TAG, "Fucking Exception"+se.getMessage());
                runQuery.closeConnection();
            }
        }
        return null;
    }
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Log.v(TAG, "Updating groups");
        Log.v(TAG, "Updating groups"+listGroups.size());
        itemAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,listGroups);
        //itemAdapter.notifyDataSetChanged();
        lvItem.setAdapter(itemAdapter);
    }
}
