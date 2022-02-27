package com.zou.dao.dynamodb;

import com.zou.dao.DocumentDefinitionRepository;
import com.zou.type.DocumentSchema;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author HO.CKERVOAZOU
 */
@ApplicationScoped
public class DocumentDefinitionRepositoryImpl extends DynamoRepositoryImpl<DocumentSchema> implements DocumentDefinitionRepository {

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
                TableSchema.fromBean(DocumentSchema.class));
    }

    @Override
    public DocumentSchema findByType(@NotEmpty String documentType) {
        return getItem(documentType);
    }

    // Store the order item in the database
    @Override
    public void store(@NotNull final DocumentSchema documentSchema) {
        save(documentSchema);
    }
}
