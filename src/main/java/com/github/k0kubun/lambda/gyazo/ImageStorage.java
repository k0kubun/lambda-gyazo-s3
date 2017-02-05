package com.github.k0kubun.lambda.gyazo;

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
        uploadWithS3("lambda-gyazo", "hello.png", image);
        return "https://s3-ap-northeast-1.amazonaws.com/lambda-gyazo/hello.png";
    }

    private void uploadWithS3(String bucket, String key, String image) {
        s3client.putObject(bucket, key, image);
        s3client.setObjectAcl(bucket, key, CannedAccessControlList.PublicRead);
    }
}
