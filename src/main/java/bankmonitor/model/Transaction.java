package bankmonitor.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction {

    @Setter(AccessLevel.PRIVATE)
    @EqualsAndHashCode.Include
    long id;
    int amount = -1;
    String reference = "";

    public static Transaction of(long id, Integer amount, String reference) {
        var result = new Transaction();
        result.setId(id);
        if (amount != null) {
            result.setAmount(amount);
        }
        if (reference != null) {
            result.setReference(reference);
        }
        return result;
    }

    public static Transaction of(Integer amount, String reference) {
        return of(0, amount, reference);
    }

}
