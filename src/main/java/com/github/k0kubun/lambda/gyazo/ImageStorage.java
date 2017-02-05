package com.github.k0kubun.lambda.gyazo;

import java.util.Random;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;

class ImageStorage {
    AmazonS3 s3client;

    public ImageStorage() {
        this.s3client = AmazonS3ClientBuilder.defaultClient();
    }

    public String upload(String image) {
        String filename = getRandomHexString(32);
        uploadWithS3("lambda-gyazo", filename, image);
        return "https://s3-ap-northeast-1.amazonaws.com/lambda-gyazo/" + filename;
    }

    private void uploadWithS3(String bucket, String key, String image) {
        s3client.putObject(bucket, key, image);
        s3client.setObjectAcl(bucket, key, CannedAccessControlList.PublicRead);
    }

    private String getRandomHexString(int length) {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while (sb.length() < length) {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, length);
    }
}
