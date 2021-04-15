package fr.iambluedev.hallyos.economy.repository;

import fr.iambluedev.hallyos.economy.model.Transaction;
import xyz.anana.pine.core.field.Field;
import xyz.anana.pine.core.table.Table;

import java.util.Arrays;

public class TransactionRepository extends Table {

    public TransactionRepository() {
        super("hallyos_transactions");
    }

    public void save(Transaction transaction) {
        this.insert(Arrays.asList(
                new Field("sender_id", transaction.getSender() == null ? -1 : transaction.getSender().getId()),
                new Field("receiver_id", transaction.getReceiver() == null ? -1 : transaction.getReceiver().getId()),
                new Field("transaction_type", transaction.getType()),
                new Field("transaction_amount", transaction.getAmount()),
                new Field("created_at", transaction.getAt())
        ));
    }

}
