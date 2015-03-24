package com.idbms.s_box.newgroup;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.idbms.s_box.MainActivity;
import com.idbms.s_box.amazonrds.RunQuery;
import com.idbms.s_box.amazons3.CreateBucket;

import java.util.List;

/**
 * Created by praveen on 3/23/2015.
 */
public class CreateNewGroup extends AsyncTask {
    private static final String TAG = "AnalyzeUserInputs";
    Context mContext;
    String groupName;
    List<String> membersList;
    public CreateNewGroup(String GroupName, Context context, List<String> members) {
        Log.v(TAG, "Creating New Group");
        groupName=GroupName;
        mContext=context;
        membersList=members;
    }
    @Override
    protected Object doInBackground(Object[] params) {
        String groupId="s-box-"+ MainActivity.loginId.toLowerCase()+"-"+groupName.toLowerCase();
        boolean bucketStatus= new CreateBucket().createBucket(groupId.replace("@","attr"));
        Log.v(TAG, "Bucket Created"+bucketStatus);
        if(bucketStatus){
            Log.v(TAG, "Calling Run Query");
            RunQuery runQuery=new RunQuery();
            boolean isConnectionOpen= runQuery.openRDSConnection();
            Log.v(TAG, "Is Connectio Opened"+isConnectionOpen);
            if(isConnectionOpen) {
                String queryGroupInfo = "INSERT INTO mydb.Groups\n" +
                        "VALUES ('"+groupName+"','"+groupId+"','"+MainActivity.loginId+"',null)";
                boolean status= runQuery.runInsert(queryGroupInfo);
                if(!status) {
                    return null;
                }
                for (String oneMember : membersList) {
                    String queryUserGroups = "INSERT INTO mydb.UserGroups\n" +
                            "VALUES ('"+groupId+"','"+oneMember+"','001','"+groupName+"','"+MainActivity.loginId+"')";
                    status=runQuery.runInsert(queryUserGroups);
                    if (!status){
                        return  null;
                    }
                }
                runQuery.closeConnection();
            }
        }
        return null;
    }
}
