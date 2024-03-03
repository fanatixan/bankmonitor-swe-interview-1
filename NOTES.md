# Notes

## Assumptions and decisions during refactoring

- The API never returned the transactions' ID but it seemed an important field. Therefore, I added it to the response.
- `Transaction.setData()`'s return value was never used so I got rid of it. This made it straightforward to utilize Lombok for accessor generation.
