package com.idbms.s_box.amazonrds;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by praveen on 3/25/2015.
 */
public class GetFiles extends AsyncTask {
    private static final String TAG = "GetFiles";
    Context mContext;
    String groupName;
    List<String> listFiles;
    public ListView lvItem;
    public ArrayAdapter<String> itemAdapter;
    ProgressDialog pd;
    public GetFiles(String groupname, Context context, ListView lv) {
        Log.v(TAG, "GetGroups Constructor");
        groupName = groupname;
        mContext=context;
        lvItem=lv;
        pd=new ProgressDialog(context);
//        pd.setTitle("");
        pd.setMessage("Fetching Files Data");
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd.show();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        listFiles = new ArrayList<String>();
        String query="select filename from mydb.Files where belongsto = '"+groupName+"'";
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
                    String oneGroup=resultSet.getString("filename");
                    listFiles.add(oneGroup);
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
        Log.v(TAG, "Updating files");
        Log.v(TAG, "Updating files"+listFiles.size());
        itemAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,listFiles);
        //itemAdapter.notifyDataSetChanged();
        lvItem.setAdapter(itemAdapter);
        pd.dismiss();
    }
}
