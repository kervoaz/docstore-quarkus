package com.zou;

import com.zou.type.EcmDocument;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author HO.CKERVOAZOU
 */
@Slf4j
public class Utils {
    static InputStream[] duplicateStream(InputStream in) {
        InputStream[] ins = new InputStream[2];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            in.transferTo(baos);
            ins[0] = new ByteArrayInputStream(baos.toByteArray());
            ins[1] = new ByteArrayInputStream(baos.toByteArray());
        } catch (IOException e) {
            ins[0] = in;
            ins[1] = null;
        }
        return ins;
    }

    //TODO documentId could be the sha256 of the doc in order to avoid duplicate
    private static String getFileChecksum(MessageDigest digest, File file) throws IOException {
        //Get file input stream for reading the file content
        FileInputStream fis = new FileInputStream(file);

        //Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        //Read file data and update in message digest
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }

        //close the stream; We don't need it now.
        fis.close();

        //Get the hash's bytes
        byte[] bytes = digest.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        //return complete hash
        return sb.toString();
    }

    public String getDocumentId(EcmDocument ecmDocument, File file) throws NoSuchAlgorithmException, IOException {
        //Use SHA-1 algorithm
        MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");

        //SHA-1 checksum
        return getFileChecksum(shaDigest, file);
    }

    private static File uploadToTemp(String uuid, InputStream data) {
        File tempPath;
        try {
            tempPath = File.createTempFile(uuid, ".tmp");
            Files.copy(data, tempPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            log.error("temp S3", ex);
            throw new RuntimeException(ex);
        }

        return tempPath;
    }
}
