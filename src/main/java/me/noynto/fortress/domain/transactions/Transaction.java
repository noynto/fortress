package me.noynto.fortress.domain.transactions;

import me.noynto.fortress.domain.shared.TransactionId;

public class Transaction {

    private TransactionId id;
    private TransactionDescription description;
    private TransactionAmount amount;
    private TransactionType type;
    private TransactionState state;
    private TransactionApplicationDate effectiveDate;
    private TransactionOwner owner;
    private TransactionIssueDate issueDate;

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

    public TransactionApplicationDate applicationDate() {
        return effectiveDate;
    }

    public void applicationDate(TransactionApplicationDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public TransactionOwner owner() {
        return owner;
    }

    public void owner(TransactionOwner creator) {
        this.owner = creator;
    }

    public TransactionIssueDate issueDate() {
        return issueDate;
    }

    public void issueDate(TransactionIssueDate issueDate) {
        this.issueDate = issueDate;
    }
}
