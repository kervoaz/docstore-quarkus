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

    // Retrieve a single order item from the database
    public T findByHashKeyAndRangKey(final String hashKey, final String rangeKey) {
        // Construct the key with partition and sort key
        Key key = Key.builder().partitionValue(hashKey)
                .sortValue(rangeKey)
                .build();

        return this.dynamoDbTable.getItem(key);
    }


    public List<T> findByHashKey(final String hashKey) {
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

//    private DynamoDbTable<T> getTable(DynamoDbEnhancedClient dynamoDbEnhancedClient,String tableName) {
//        // Create a tablescheme to scan our bean class order
//        return
//                dynamoDbEnhancedClient.table(tableName,
//                        TableSchema.fromBean(getType()));
//    }
}
