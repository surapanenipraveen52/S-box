package com.idbms.s_box.amazons3;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.idbms.s_box.amazonrds.RunQuery;
import com.idbms.s_box.utils.OpenFile;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by praveen on 4/10/2015.
 */
public class DownloadFile extends AsyncTask {
    private static final String TAG ="FilesList" ;
    public String groupName;
    public String fileName;
    public Context context;
    ProgressDialog pd;
    public DownloadFile(String GroupName, String FileName, Context c) {
        groupName = GroupName;
        fileName = FileName;
        context = c;
        pd=new ProgressDialog(context);
        pd.setMessage("Downloading "+fileName+"..");
    }
    protected void onPreExecute() {
        super.onPreExecute();
        pd.show();
    }
    @Override
    protected Object doInBackground(Object[] params) {
        Log.v(TAG,"File Namei s"+fileName+"Hro is"+groupName);
        String owner="";
        File f = new File(Environment.getExternalStorageDirectory()+ "/S-Box",
                groupName);
        if (!f.exists()) {
            f.mkdirs();
        }
        Log.v(TAG,"path is "+f.getPath());
        File outputFile= new File(f.getPath()+"/"+fileName);
        if(outputFile.exists())
        {
            new OpenFile().openFile(outputFile,context);
        }else{
        String query = "select UploadedBy from mydb.Files where BelongsTo ='"+groupName+"' and filename ='"+fileName+"'";
        RunQuery runQuery=new RunQuery();
        boolean isConnectionOpen = runQuery.openRDSConnection();
        if(isConnectionOpen){
            ResultSet resultSet = runQuery.runSelect(query);
            if(null==resultSet){
                return null;
            }
            try{
                while (resultSet.next()){
                    owner=resultSet.getString("UploadedBy");
                    Log.v(TAG,owner);
                }
                runQuery.closeConnection();
            }catch (SQLException se){
                Log.v(TAG, "Fucking Exception"+se.getMessage());
                runQuery.closeConnection();
            }
            String bucketName = "s-box-"+owner.replace("@","attr").toLowerCase()+"-"+groupName.toLowerCase();
            AWSCredentials credentials = new BasicAWSCredentials(
                    "AKIAJAPTQ3XJ2VVHLN2A",
                    "Y3aKBXQRnlpkorYOphBm1djdtQEuhq3i/PlkJ9Bj");
            AmazonS3 s3client = new AmazonS3Client(credentials);
            GetObjectRequest request = new GetObjectRequest(bucketName,fileName);
            S3Object object = s3client.getObject(request);
            try {
            byte[] bytes = IOUtils.toByteArray(object.getObjectContent());

                FileOutputStream  outputStream = new FileOutputStream(outputFile);
                outputStream.write(bytes);
                new OpenFile().openFile(outputFile,context);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }
        return null;

    }
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        pd.setMessage("Done");
        pd.dismiss();
    }
}
