package com.zou.clevercloud;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

/**
 * @author HO.CKERVOAZOU
 */
@ApplicationScoped
public class Config {
    @ConfigProperty(name = "clevercloud.s3.endpoint")
    String endpoint;
    @ConfigProperty(name = "clevercloud.s3.keyID")
    String accessKey;
    @ConfigProperty(name = "clevercloud.s3.keySecret")
    String secretKey;

//    @Produces
//    @Named("clevercloud")
//    S3Client getS3Client() {
//        return AWSUtil.getS3Client(accessKey, secretKey, endpoint);
//    }
}
