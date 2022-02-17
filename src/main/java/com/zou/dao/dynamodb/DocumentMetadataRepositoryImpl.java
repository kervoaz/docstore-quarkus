package com.zou.dao.dynamodb;

import com.zou.dao.DocumentMetadataRepository;
import com.zou.type.EcmDocument;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HO.CKERVOAZOU
 */
@ApplicationScoped
public class DocumentMetadataRepositoryImpl implements DocumentMetadataRepository {

    @ConfigProperty(name = "docs.dynamodb.document.repository.table-name")
    String tableName;

    @Inject
    DynamoDbEnhancedClient dynamoDbenhancedClient;

    // Store the order item in the database
    @Override
    public void save(final EcmDocument ecmDocument) {
        DynamoDbTable<EcmDocument> ecmDocumentDynamoDbTable = getTable();
        ecmDocumentDynamoDbTable.putItem(ecmDocument);
    }

    // Retrieve a single order item from the database
    @Override
    public EcmDocument findByIdAndRevision(final String id, final int revision) {
        DynamoDbTable<EcmDocument> ecmDocumentDynamoDbTable = getTable();
        // Construct the key with partition and sort key
        Key key = Key.builder().partitionValue(id)
                .sortValue(revision)
                .build();

        return ecmDocumentDynamoDbTable.getItem(key);
    }

    @Override
    public List<EcmDocument> findById(final String id) {
        DynamoDbTable<EcmDocument> ecmDocumentDynamoDbTable = getTable();
        QueryConditional query = QueryConditional.keyEqualTo(
                Key.builder()
                        .partitionValue(id)
                        .build()
        );
        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
                .queryConditional(query)
                .build();
        PageIterable<EcmDocument> ecmDocumentPageIterable =
                ecmDocumentDynamoDbTable.query(request);

        return ecmDocumentPageIterable.items()
                .stream().collect(Collectors.toList());

    }

    private DynamoDbTable<EcmDocument> getTable() {
        // Create a tablescheme to scan our bean class order
        return
                dynamoDbenhancedClient.table(tableName,
                        TableSchema.fromBean(EcmDocument.class));
    }

    public EcmDocument findLastRevision(@NotNull String id) {
        return findById(id).stream().max(Comparator.comparing(i -> i.getRevision())).orElse(null);//.orElseThrow
    }
}
