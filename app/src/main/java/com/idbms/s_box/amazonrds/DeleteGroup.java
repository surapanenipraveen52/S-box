package com.idbms.s_box.amazonrds;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.idbms.s_box.MainActivity;

/**
 * Created by praveen on 4/25/2015.
 */
public class DeleteGroup extends AsyncTask
{
    String groupName;
    Context context;
    ListView lv;

    public DeleteGroup(String GroupName, Context Context, ListView LV){
        groupName = GroupName;
        context = Context;
        lv = LV;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        String query = "delete from mydb.UserGroups where groupname ='"+groupName+"'";
        RunQuery runQuery=new RunQuery();
        boolean isConnectionOpen = runQuery.openRDSConnection();
        if(isConnectionOpen){
            runQuery.runInsert(query);
        }runQuery.closeConnection();
        return null;
    }
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        new GetGroups(MainActivity.loginId,context,lv).execute();
    }
}
