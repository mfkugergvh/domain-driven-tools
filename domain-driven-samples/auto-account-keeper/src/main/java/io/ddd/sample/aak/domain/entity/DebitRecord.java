package io.ddd.sample.aak.domain.entity;

import java.math.BigDecimal;

public class DebitRecord {
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
