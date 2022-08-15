package com.zou;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author HO.CKERVOAZOU
 */
public class AWSUtil {
    public static S3Client getS3Client(String accessKey, String secretKey, String endpoint) {
        AwsCredentialsProvider awsCredentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(
                accessKey, secretKey));
        URI uri = null;
        try {
            uri = new URI(endpoint);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return S3Client.builder().credentialsProvider(awsCredentialsProvider).endpointOverride(uri).build();
    }
}
