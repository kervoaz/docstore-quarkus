package com.zou.dao.dynamodb;

import com.zou.dao.DocumentDefinitionRepository;
import com.zou.type.DocumentDefinition;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author HO.CKERVOAZOU
 */
@ApplicationScoped
public class DocumentDefinitionRepositoryImpl extends DynamoRepositoryImpl<DocumentDefinition> implements DocumentDefinitionRepository {

    @ConfigProperty(name = "docs.dynamodb.document.definition.table-name")
    String tableName;

    @Inject
    DynamoDbEnhancedClient dynamoDbEnhancedClient;

    public DocumentDefinitionRepositoryImpl() {
        super();
    }

    @PostConstruct
    void init() {
        super.dynamoDbTable = dynamoDbEnhancedClient.table(tableName,
                TableSchema.fromBean(DocumentDefinition.class));
    }

    @Override
    public DocumentDefinition findByType(String documentType) {
        return findByHashKey(documentType).get(0);
    }

    // Store the order item in the database
    @Override
    public void save(final DocumentDefinition documentDefinition) {
        super.save(documentDefinition);
    }
}
