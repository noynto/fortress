package me.noynto.fortress.domain.transactions;

import me.noynto.fortress.domain.shared.TransactionId;

public class Transaction {

    private TransactionId id;
    private TransactionDescription description;
    private TransactionAmount amount;
    private TransactionType type;
    private TransactionState state;
    private TransactionCreationDate creationDate;
    private TransactionEffectiveDate effectiveDate;

    public TransactionId id() {
        return this.id;
    }

    public void id(TransactionId id) {
        this.id = id;
    }

    public TransactionDescription description() {
        return this.description;
    }

    public void description(TransactionDescription description) {
        this.description = description;
    }

    public TransactionAmount amount() {
        return this.amount;
    }

    public void amount(TransactionAmount amount) {
        this.amount = amount;
    }

    public TransactionType type() {
        return this.type;
    }

    public void type(TransactionType type) {
        this.type = type;
    }

    public TransactionState state() {
        return this.state;
    }

    public void state(TransactionState state) {
        this.state = state;
    }

    public TransactionCreationDate creationDate() {
        return creationDate;
    }

    public void creationDate(TransactionCreationDate creationDate) {
        this.creationDate = creationDate;
    }

    public TransactionEffectiveDate effectiveDate() {
        return effectiveDate;
    }

    public void effectiveDate(TransactionEffectiveDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
