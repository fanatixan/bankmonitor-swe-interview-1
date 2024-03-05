package bankmonitor.controller.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponse {

    long id;
    int amount = -1;
    String reference = "";

}
