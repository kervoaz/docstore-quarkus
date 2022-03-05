package com.zou.dao.s3;

import com.zou.dao.DocumentContentRepository;
import com.zou.type.EcmDocument;
import com.zou.type.FileContent;
import com.zou.type.StorageInformation;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;

/**
 * @author HO.CKERVOAZOU
 */
@Slf4j
@ApplicationScoped
public class DocumentContentRepositoryImpl extends CommonS3 implements DocumentContentRepository {

    @ConfigProperty(name = "bucket.name")
    String bucketName;

    @Inject
    S3Client s3;

    @Override
    public StorageInformation upload(EcmDocument ecmDocument) {
        Instant start = Instant.now();
        StorageInformation storageInformation = new StorageInformation();
        storageInformation.setBucket(bucketName);
        storageInformation.setObjectKey(getS3ObjectKey(ecmDocument));
        PutObjectResponse putResponse = s3.putObject(buildPutRequest(ecmDocument.getFileContent(), storageInformation
                        , ecmDocument.getMetadata()),
                RequestBody.fromFile(uploadToTemp(ecmDocument.getFileContent().getContent())));
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        if (putResponse != null) {
            storageInformation.setVersionId(putResponse.versionId());
            log.info("Storage OK in {} {}", timeElapsed, putResponse);
            return storageInformation;
        } else {
            log.error("Storage KO");
            throw new RuntimeException("Storage fails on S3");
        }
    }

    @Override
    public FileContent download(EcmDocument ecmDocument) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GetObjectResponse getObjectResponse =
                    s3.getObject(buildGetRequest(ecmDocument.getStorageInformation()),
                            ResponseTransformer.toOutputStream(baos));

            FileContent fileContent = new FileContent();
            fileContent.setContentDownloaded(baos);
            fileContent.setMimeType(getObjectResponse.contentType());

            return fileContent;
        } catch (Exception e) {
            //TODO
            log.error("error download", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(EcmDocument ecmDocument) {
        DeleteObjectResponse deleteObjectResponse =
                s3.deleteObject(buildDeleteObjectRequest(ecmDocument.getStorageInformation()));
        deleteObjectResponse.versionId();
    }

    String getS3ObjectKey(EcmDocument ecmDocument) {
        String[] file = ecmDocument.getFileContent().getOriginalName().split("\\.");
        if (file.length > 1 && file[1] != null) {
            return OffsetDateTime.now().getYear() + "/" + ecmDocument.getId() + "." + file[1];
        } else {
            return OffsetDateTime.now().getYear() + "/" + ecmDocument.getId();
        }
    }
}
