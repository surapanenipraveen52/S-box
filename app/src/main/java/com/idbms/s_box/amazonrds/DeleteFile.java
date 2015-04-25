package com.idbms.s_box.amazonrds;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

/**
 * Created by praveen on 4/25/2015.
 */
public class DeleteFile extends AsyncTask {
    String groupName;
    String fileName;
    Context context;
    ListView lv;

    public DeleteFile(String GroupName, String FileName, Context Context, ListView LV){
        groupName = GroupName;
        fileName = FileName;
        context = Context;
        lv = LV;
    }
    @Override
    protected Object doInBackground(Object[] params) {
        String query = "delete from mydb.Files where BelongsTo ='"+groupName+"' and filename ='"+fileName+"'";
        RunQuery runQuery=new RunQuery();
        boolean isConnectionOpen = runQuery.openRDSConnection();
        if(isConnectionOpen){
            runQuery.runInsert(query);
        }runQuery.closeConnection();
        return null;
    }
    protected void onPostExecute(Object o) {
        new GetFiles(groupName,context,lv).execute();

        super.onPostExecute(o);
    }
}
