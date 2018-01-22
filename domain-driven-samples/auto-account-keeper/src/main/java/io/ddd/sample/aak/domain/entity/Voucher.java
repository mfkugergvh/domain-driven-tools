package io.ddd.sample.aak.domain.entity;

import java.util.List;

public class Voucher {

    private List<AccountEntry> accountEntries;
    private String id;

    public List<AccountEntry> getAccountEntries() {
        return accountEntries;
    }

    public void setAccountEntries(List<AccountEntry> accountEntries) {
        this.accountEntries = accountEntries;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "accountEntries=" + accountEntries +
                ", id=" + id +
                '}';
    }
}
