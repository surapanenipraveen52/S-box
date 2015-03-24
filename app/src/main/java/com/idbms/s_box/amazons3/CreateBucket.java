package com.idbms.s_box.amazons3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * Created by praveen on 3/23/2015.
 */
public class CreateBucket {
    public boolean createBucket(String bucketName){
        AWSCredentials credentials =  new BasicAWSCredentials(
                "AKIAJAPTQ3XJ2VVHLN2A",
                "Y3aKBXQRnlpkorYOphBm1djdtQEuhq3i/PlkJ9Bj");
        AmazonS3 s3client = new AmazonS3Client(credentials);
        s3client.createBucket(bucketName);
        return true;
    }
}
