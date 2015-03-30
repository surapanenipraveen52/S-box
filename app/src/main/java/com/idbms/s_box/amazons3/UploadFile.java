package com.idbms.s_box.amazons3;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.idbms.s_box.amazonrds.GetFiles;
import com.idbms.s_box.amazonrds.RunQuery;

import java.io.File;

/**
 * Created by praveen on 3/25/2015.
 */
public class UploadFile extends AsyncTask {
    private static final String TAG ="UploadFile" ;
    String path;
    String groupName;
    String owner;
    String fileName;
    ProgressDialog pd;
    Context context;
    ListView lv;

    public UploadFile(String Path, String GroupName,String Owner, String FileName,Context mContext,ListView listView){
        path = Path;
        groupName = GroupName;
        owner = Owner;
        fileName = FileName;
        context=mContext;
        lv=listView;
        pd=new ProgressDialog(context);
        pd.setMessage("Uploading File");
    }
    protected void onPreExecute() {
        super.onPreExecute();
        pd.show();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIAJAPTQ3XJ2VVHLN2A",
                "Y3aKBXQRnlpkorYOphBm1djdtQEuhq3i/PlkJ9Bj");
        AmazonS3 s3client = new AmazonS3Client(credentials);
        String bucketName = "s-box-"+owner.replace("@","attr").toLowerCase()+"-"+groupName.toLowerCase();
        Log.v(TAG, "Bucket Name is " + bucketName);
        PutObjectResult result = s3client.putObject(new PutObjectRequest(bucketName, fileName,
                new File(path))
                .withCannedAcl(CannedAccessControlList.PublicRead));
        String fileId=result.getETag();
        if(null == fileId)
        {
            Log.v(TAG, "UPLOAD fAILED");
        }
        Log.v(TAG, "File Id is " +fileId);
        String filesQuery = "Insert into mydb.Files values('"+fileId+"','"+fileName+"','"+owner+"','"
                +groupName+"','00','_self',null)";
        RunQuery runQuery = new RunQuery();
        boolean isConnectionOpen = runQuery.openRDSConnection();
        if(isConnectionOpen){
            runQuery.runInsert(filesQuery);
        }runQuery.closeConnection();
        return null;
    }
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        pd.setMessage("Uploading Done");
        pd.dismiss();
        new GetFiles(groupName,context, lv).execute();
    }
}
