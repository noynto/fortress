package me.noynto.fortress;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Cette interface permet de créer une transaction.
 */
public interface CreateTransaction {

    /**
     * Exécute la création de la transaction.
     * @param description la description de la transaction
     * @param timestamp l'horodatage de la transaction
     * @param amount le montant de la transaction
     * @return la transaction créée
     */
    Transaction execute(String description, Instant timestamp, BigDecimal amount) throws Failed;

    class Failed extends Exception {
        public Failed(String message, Throwable cause) {
            super(message, cause);
        }

        public Failed(Throwable cause) {
            super(cause);
        }
    }
}
