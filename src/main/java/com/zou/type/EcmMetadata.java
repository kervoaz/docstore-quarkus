package com.zou.type;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HO.CKERVOAZOU
 */
@DynamoDbBean
public class EcmMetadata {

    private final static String DOCID = "id";
    private final static String DOCREV = "revision";

    Map<String, String> metadata;

    public EcmMetadata() {
    }

    public EcmMetadata(String key, String value) {
        this.metadata = new HashMap<>();
        this.metadata.put(key, value);
    }

    public EcmMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public void add(String key, String value) {
        if (metadata == null) {
            this.metadata = new HashMap<>();
            this.metadata.put(key, value);
        } else {
            this.metadata.put(key, value);
        }
    }

    public Map<String, String> get() {
        return this.metadata;
    }

    public String get(String key) {
        return metadata.get(key);
    }

    public String getId() {
        return metadata.get(DOCID);
    }

    public int getRevision() {
        return Integer.parseInt(metadata.get(DOCREV));
    }
}
