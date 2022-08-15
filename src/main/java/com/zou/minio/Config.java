package com.zou.minio;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

/**
 * @author HO.CKERVOAZOU
 */
@ApplicationScoped
public class Config {
    @ConfigProperty(name = "minio.s3.endpoint")
    String endpoint;
    @ConfigProperty(name = "minio.s3.keyID")
    String accessKey;
    @ConfigProperty(name = "minio.s3.keySecret")
    String secretKey;

//    @Produces
//    @Named("minio")
//    S3Client getS3Client() {
//        return AWSUtil.getS3Client(accessKey, secretKey, endpoint);
//    }
}
