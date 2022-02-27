package com.zou.dao.s3;

import com.zou.type.FileContent;
import com.zou.type.StorageInformation;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author HO.CKERVOAZOU
 */

@Slf4j
abstract public class CommonS3 {
    private final static String TEMP_DIR = System.getProperty("java.io.tmpdir");

    protected PutObjectRequest buildPutRequest(@NotNull FileContent fileContent,
                                               @NotNull StorageInformation storageInformation,
                                               Map<String, String> ecmMetadata) {
        if (ecmMetadata != null) {
            return PutObjectRequest.builder()
                    .bucket(storageInformation.getBucket())
                    .key(storageInformation.getObjectKey())
                    .contentType(fileContent.getMimeType())
                    .metadata(ecmMetadata)
                    .build();
        } else {
            return PutObjectRequest.builder()
                    .bucket(storageInformation.getBucket())
                    .key(storageInformation.getObjectKey())
                    .contentType(fileContent.getMimeType())
                    .build();
        }
    }

    protected GetObjectRequest buildGetRequest(@NotNull StorageInformation storageInformation) {
        return GetObjectRequest.builder()
                .bucket(storageInformation.getBucket())
                .key(storageInformation.getObjectKey())
                .versionId(storageInformation.getVersionId())
                .build();
    }

    protected DeleteObjectRequest buildDeleteObjectRequest(@NotNull StorageInformation storageInformation) {
        return DeleteObjectRequest.builder()
                .bucket(storageInformation.getBucket())
                .key(storageInformation.getObjectKey())
                .versionId(storageInformation.getVersionId())
                .build();
    }

    protected File tempFilePath() {
        return new File(TEMP_DIR, new StringBuilder().append("s3AsyncDownloadedTemp")
                .append((new Date()).getTime()).append(UUID.randomUUID())
                .append(".").append(".tmp").toString());
    }

    protected File uploadToTemp(InputStream data) {
        File tempPath;
        try {
            tempPath = File.createTempFile("uploadS3Tmp", ".tmp");
            Files.copy(data, tempPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            log.error("temp S3", ex);
            throw new RuntimeException(ex);
        }

        return tempPath;
    }
}