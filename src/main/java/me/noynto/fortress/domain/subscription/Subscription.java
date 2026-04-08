package me.noynto.fortress.domain.subscription;

import me.noynto.fortress.domain.shared.SubscriptionId;


public class Subscription {
    private SubscriptionId id;
    private final SubscriptionFrequency frequency = SubscriptionFrequency.MONTHLY;
}
