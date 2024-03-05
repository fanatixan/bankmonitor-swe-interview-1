package bankmonitor.repository.model.mapper;

import bankmonitor.IntegrationTestContext;
import bankmonitor.model.Transaction;
import bankmonitor.repository.model.TransactionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Transaction model-entity mapping")
class TransactionEntityMapperTest extends IntegrationTestContext {

    @Autowired
    TransactionEntityMapper mapper;

    @DisplayName("WHEN mapping domain to entity THEN id, amount, and reference are copied")
    @Test
    void shouldMapDomainToEntity() {
        // given
        long id = 3;
        int amount = 42;
        String reference = "bar";
        var domain = Transaction.of(id, amount, reference);

        // when
        var entity = mapper.toEntity(domain);

        // then
        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getAmount()).isEqualTo(amount);
        assertThat(entity.getReference()).isEqualTo(reference);
    }

    @DisplayName("WHEN mapping entity to domain THEN id, amount, and reference are copied")
    @Test
    void shouldMapEntityToDomain() {
        // given
        long id = 3;
        int amount = 42;
        String reference = "bar";
        var entity = new TransactionEntity();
        entity.setId(id);
        entity.setAmount(amount);
        entity.setReference(reference);

        // when
        var domain = mapper.toDomain(entity);

        // then
        assertThat(domain.getId()).isEqualTo(id);
        assertThat(domain.getAmount()).isEqualTo(amount);
        assertThat(domain.getReference()).isEqualTo(reference);
    }

}
