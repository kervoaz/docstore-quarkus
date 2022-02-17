package com.zou;

import com.zou.type.Fruit;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HO.CKERVOAZOU
 */


@ApplicationScoped
public class FruitSyncService extends AbstractService {

    @Inject
    DynamoDbClient dynamoDB;

    @ConfigProperty(name = "fruit.table-name")
    String tableName;

    public String getTableName() {
        return tableName;
    }

//    DynamoDbEnhancedClient dynamoDbEnhancedClient=DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDB).build();

    public List<Fruit> findAll() {
        return dynamoDB.scanPaginator(scanRequest()).items().stream()
                .map(Fruit::from)
                .collect(Collectors.toList());
    }

    public List<Fruit> add(Fruit fruit) {
        dynamoDB.putItem(putRequest(fruit));
        return findAll();
    }

    public Fruit get(String name) {
        return Fruit.from(dynamoDB.getItem(getRequest(name)).item());
    }

//    // Store the order item in the database
//    public void save(final Fruit fruit) {
//        DynamoDbTable<Fruit> orderTable = getTable();
//        orderTable.putItem(fruit);
//    }
//
//    // Retrieve a single order item from the database
//    public Fruit findById(final String id) {
//        DynamoDbTable<Fruit> jobExecutionTable = getTable();
//        // Construct the key with partition and sort key
//        Key key = Key.builder().partitionValue(id)
////                .sortValue(runTime)
//                .build();
//
//        return jobExecutionTable.getItem(key);
//    }
//
//    private DynamoDbTable<Fruit> getTable() {
//        // Create a tablescheme to scan our bean class order
//        return
//                dynamoDbEnhancedClient.table(tableName,
//                        TableSchema.fromBean(Fruit.class));
//    }
}
