package bankmonitor.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "transaction")
public class Transaction {

    public static final String REFERENCE_KEY = "reference";
    public static final String AMOUNT_KEY = "amount";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created_at")
    private LocalDateTime timestamp;

    @Column(name = "data")
    private String data;

    public Transaction(String jsonData) {
        this.timestamp = LocalDateTime.now();
        this.data = jsonData;
    }

    public String getData() {
        return this.data;
    }

    public Boolean setData(String data) {
        this.data = data;
        return true;
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
