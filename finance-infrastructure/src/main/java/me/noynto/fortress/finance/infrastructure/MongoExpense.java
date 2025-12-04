package me.noynto.fortress.finance.infrastructure;

import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.mongodb.MongoDbClientBuilder;
import me.noynto.fortress.Expense;
import me.noynto.fortress.ExpenseProvider;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Stream;

public record MongoExpense(
        DbClient client
) implements ExpenseProvider {

    public static MongoExpense init() {
        DbClient client = MongoDbClientBuilder.create().url("mongodb://192.168.49.2:30017").username("fortress").password("fortress_password").credDb("admin").build();
        return new MongoExpense(client);
    }

    @Override
    public Expense create(Expense.Label label, Expense.Amount amount) {
        long result = client.execute()
                .createInsert("""
                        {
                            "collection": "expenses",
                            "value": {
                                "_id": $ref,
                                "label": $label,
                                "amount": $amount
                            }
                        }
                        """)
                .addParam("label", label.value())
                .addParam("amount", amount.value())
                .addParam("ref", UUID.randomUUID().toString())
                .execute();
        Expense.Reference reference = new Expense.Reference(String.valueOf(result));
        return new Expense(reference, label, amount);
    }

    @Override
    public Stream<Expense> read() {
        return client.execute().createQuery("""
                        {
                            "collection": "expenses",
                            "query": {}
                        }
                        """)
                .execute()
                .map(row -> {
                    Expense.Reference reference = new Expense.Reference(row.column("_id").getString());
                    Expense.Label label = new Expense.Label(row.column("label").getString());
                    Expense.Amount amount = new Expense.Amount(BigDecimal.valueOf(row.column("amount").getDouble()));
                    return new Expense(reference, label, amount);
                });
    }
}
