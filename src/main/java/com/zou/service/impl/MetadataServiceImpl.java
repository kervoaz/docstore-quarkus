package com.zou.service.impl;

import com.zou.service.MetadataService;
import com.zou.type.EcmMetadata;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * @author HO.CKERVOAZOU
 */
@ApplicationScoped
public class MetadataServiceImpl implements MetadataService {
    private final String DELIMITER = ";";

    //    @Inject
//    DocumentConfigurationRepository documentConfigurationRepository;
    @Override
    public EcmMetadata parse(String metadataAsString) {
        String[] keyValues = metadataAsString.split(DELIMITER);
        Map<String, String> keyValue = Arrays.stream(keyValues).map(x -> x.split("=")).collect(toMap(str -> str[0],
                str -> str[1]));
        return new EcmMetadata(keyValue);
    }

    @Override
    public void validate(EcmMetadata ecmMetadata) {
        //documentConfigurationRepository.getMetadataConfiguration(ecmMetadata.)
    }
}
