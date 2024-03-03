package bankmonitor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @EqualsAndHashCode.Include
    long id;

    @Column(name = "created_at")
    LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "amount")
    @Getter
    @Setter
    int amount = -1;

    @Column(name = "reference")
    @Getter
    @Setter
    String reference = "";

    public static Transaction of(Integer amount, String reference) {
        var result = new Transaction();
        if (amount != null) {
            result.setAmount(amount);
        }
        if (reference != null) {
            result.setReference(reference);
        }
        return result;
    }

}
