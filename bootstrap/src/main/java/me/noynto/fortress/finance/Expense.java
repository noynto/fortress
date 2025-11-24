package me.noynto.fortress.finance;

import java.math.BigDecimal;
import java.util.Objects;

public record Expense(
        Reference reference,
        Label label,
        Amount amount
) {
    public Expense {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(label);
        Objects.requireNonNull(reference);
    }

    public record Amount(BigDecimal value) {
        public Amount {
            Objects.requireNonNull(value, "La valeur est requise.");
        }
    }

    public record Label(String value) {
        public Label {
            Objects.requireNonNull(value, "La valeur est requise.");
        }
    }

    public record Reference(
            String value
    ) {
        public Reference {
            Objects.requireNonNull(value, "La valeur est requise.");
        }
    }
}
