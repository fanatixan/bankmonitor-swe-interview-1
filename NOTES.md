# Notes

## Assumptions and decisions during refactoring

- The API never returned the transactions' ID but it seemed an important field. Therefore, I added it to the response.
- `Transaction.setData()`'s return value was never used so I got rid of it. This made it straightforward to utilize Lombok for accessor generation.
- The business logic in the controller's update method implies that `amount` and `reference` are the only important fields of a transaction (aside ID and timestamp).
  But the raw JSON data is stored, updated, and exposed. If a client depends on this, best case scenario is that it's inconvenient because the JSON string needs to
  be parsed, worst case scenario is that it's a security issue; for example could become a data leak. For a real application this needs to be checked and the change
  should bump a major version (since it's a breaking change). I saw that `data.sql` inserts more data into the field. I assumed that this raw json storage is only a bug,
  and no clients actually depends on it. Therefore, I went ahead with the refactor and got rid of the raw JSON data. This simplified the data model and the business logic.
  If it turned out that data is needed then I'd still do the refactor but store the additional data in a `Map<String, Object>` in the database and serialize/deserialize
  using Jackson's `@JsonAnySetter` and `@JsonAnyGetter`.
- Both `amount` and `reference` seems to be a mandatory field, but I refactored in such a way that they are optional in the create and update endpoints to maintain
  backward compatibility. I ensured this behavior with additional API tests for create.
- Since we're only looking up transactions by ID at the moment, there is no point to add more indices to the DB. In the future, further indexing should be considered.
- For the application to be runnable, I didn't fully externalize the configuration. In a real-life situation, H2 should have been moved to be a test-scoped
  dependency, and the JDBC driver should have been a Postgres driver.
- I considered adding validation, but even `data.sql` contains data that makes hard to figure out validation rules. This needs to be discovered with the PO.
- Most probably caching all transactions is not the best idea. If we assume that we frequently retrieve the transaction list, but rarely update/create them,
  then it can be justified.
- In a microservices environment in-memory caching is suboptimal. Therefore, in a production environment I'd configure a Redis or Hazelcast-based distributed cache.
