package com.zou.service.impl;

import com.zou.service.MetadataService;
import com.zou.type.EcmDocument;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * @author HO.CKERVOAZOU
 */
@ApplicationScoped
public class MetadataServiceImpl implements MetadataService {
    @Override
    public void addMandatory(EcmDocument ecmDocument) {
        Map<String, String> existing = ecmDocument.getMetadata();
        if (existing == null) {
            existing = new HashMap<>();
            ecmDocument.getMetadata().putAll(existing);
        }
        existing.put("#docid", ecmDocument.getId());
        existing.put("#docrev", String.valueOf(ecmDocument.getRevision()));
        existing.put("#doctype", ecmDocument.getDocumentSchema().getFunctionalType());
        existing.put("#filename", ecmDocument.getFileContent().getOriginalName());
    }

    @Override
    public Map<String, String> parse(String metadataAsString) {
        if (metadataAsString != null) {
            String DELIMITER = ",";
            String[] keyValues = metadataAsString.split(DELIMITER);
            return Arrays.stream(keyValues).map(x -> x.split("=")).collect(toMap(str -> str[0],
                    str -> str[1]));
        } else {
            return new HashMap<>();
        }
    }
}
