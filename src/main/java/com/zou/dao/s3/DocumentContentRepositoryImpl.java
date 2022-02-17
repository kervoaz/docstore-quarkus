package com.zou.dao.s3;

import com.zou.dao.DocumentContentRepository;
import com.zou.type.EcmDocument;
import com.zou.type.EcmMetadata;
import com.zou.type.FileContent;
import com.zou.type.StorageInformation;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.time.OffsetDateTime;

/**
 * @author HO.CKERVOAZOU
 */
@Slf4j
@ApplicationScoped
public class DocumentContentRepositoryImpl extends CommonS3 implements DocumentContentRepository {
    @Inject
    S3Client s3;

    @Override
    public StorageInformation upload(FileContent fileContent, EcmMetadata metadata) {
        StorageInformation storageInformation = new StorageInformation();
        storageInformation.setObjectKey(OffsetDateTime.now().getYear() + "/" + fileContent.getOriginalName());
        PutObjectResponse putResponse = s3.putObject(buildPutRequest(fileContent, storageInformation, metadata),
                RequestBody.fromFile(uploadToTemp(fileContent.getContent())));
        if (putResponse != null) {
            storageInformation.setBucket(fileContent.getOriginalName());
            storageInformation.setVersionId(putResponse.versionId());
            log.info("Storage OK {}", putResponse);
            return storageInformation;
        } else {
            log.error("Storage KO {}");
            throw new RuntimeException("Storage fails on S3");
        }
    }

    @Override
    public FileContent download(EcmDocument ecmDocument) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GetObjectResponse getObjectResponse =
                    s3.getObject(buildGetRequest(ecmDocument.getStorageInformation().getObjectKey()),
                            ResponseTransformer.toOutputStream(baos));

            FileContent fileContent = new FileContent();
            fileContent.setContentDownloaded(baos);
            fileContent.setMimeType(getObjectResponse.contentType());

            return fileContent;
        } catch (Exception e) {
            //TODO
            log.error("error dowload", e);
            throw new RuntimeException(e);
        }
    }
}
