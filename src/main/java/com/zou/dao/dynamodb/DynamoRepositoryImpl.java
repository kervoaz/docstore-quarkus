package com.zou.dao.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HO.CKERVOAZOU
 */


public abstract class DynamoRepositoryImpl<T> {

    DynamoDbTable<T> dynamoDbTable;

    public DynamoRepositoryImpl() {
    }

    public void save(final T dynamoBean) {
        this.dynamoDbTable.putItem(dynamoBean);
    }

    // Retrieve all items from the database for the hashKey
    public T getItem(final String hashKey) {
        // Construct the key with partition and sort key
        Key key = Key.builder().partitionValue(hashKey)
                .build();

        return this.dynamoDbTable.getItem(key);
    }

    // Retrieve a single item from the database
    public T getItem(final String hashKey, final String rangeKey) {
        // Construct the key with partition and sort key
        Key key = Key.builder().partitionValue(hashKey)
                .sortValue(rangeKey)
                .build();

        return this.dynamoDbTable.getItem(key);
    }

    // Retrieve all items from the database for the hashKey
    public List<T> findAllByHashKey(final String hashKey) {
        QueryConditional query = QueryConditional.keyEqualTo(
                Key.builder()
                        .partitionValue(hashKey)
                        .build()
        );
        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
                .queryConditional(query)
                .build();
        PageIterable<T> ecmDocumentPageIterable =
                this.dynamoDbTable.query(request);

        return ecmDocumentPageIterable.items()
                .stream().collect(Collectors.toList());

    }

    public List<T> scan() {
        PageIterable<T> ecmDocumentPageIterable =
                this.dynamoDbTable.scan();

        return ecmDocumentPageIterable.items()
                .stream().collect(Collectors.toList());

    }

}
