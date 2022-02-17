package com.zou.dao.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.DefaultAttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;

/**
 * @author HO.CKERVOAZOU
 */
public class EcmDocumentsConverters implements AttributeConverterProvider {
    @Override
    public <T> AttributeConverter<T> converterFor(EnhancedType<T> enhancedType) {
        return DefaultAttributeConverterProvider.create().converterFor(enhancedType);
    }
}
