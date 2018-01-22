package io.ddd.sample.aak.domain.entity;

import java.math.BigDecimal;

public class AccountEntry {
    private String voucherId;
    private String id;
    private Direction direction;
    private BigDecimal amount;
    private TopTitleKind topTitle;
    private String subTitle;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public AccountEntry topTitle(TopTitleKind topTitle) {
        this.topTitle = topTitle;
        return this;
    }

    public AccountEntry subTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    public AccountEntry direction(Direction direction) {
        this.direction = direction;
        return this;
    }

    public AccountEntry amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setTopTitle(TopTitleKind topTitle) {
        this.topTitle = topTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public AccountEntry voucherId(String voucherId) {
        this.voucherId = voucherId;
        return this;
    }

    public AccountEntry id(String id) {
        this.id = id;
        return this;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TopTitleKind getTopTitle() {
        return topTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    @Override
    public String toString() {
        return "AccountEntry{id=" + id + "}";
    }
}
