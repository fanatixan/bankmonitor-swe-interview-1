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
import org.json.JSONObject;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction {

    public static final String REFERENCE_KEY = "reference";
    public static final String AMOUNT_KEY = "amount";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @EqualsAndHashCode.Include
    long id;

    @Column(name = "created_at")
    LocalDateTime timestamp;

    @Column(name = "data")
    @Getter
    @Setter
    String data;

    public Transaction(String jsonData) {
        this.timestamp = LocalDateTime.now();
        this.data = jsonData;
    }

    public Integer getAmount() {
        JSONObject jsonData = new JSONObject(this.data);
        if (jsonData.has("amount")) {
            return jsonData.getInt("amount");
        } else {
            return -1;
        }
    }

    public String getReference() {
        JSONObject jsonData = new JSONObject(this.data);
        if (jsonData.has(REFERENCE_KEY)) {
            return jsonData.getString(REFERENCE_KEY);
        } else {
            return "";
        }
    }
}
