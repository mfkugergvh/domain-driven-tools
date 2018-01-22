package io.ddd.sample.aak.domain.entity;

public class TheAccountBookkeeper {
    private Party operateParty;
    private Account operateAccount;

    public Party getOperateParty() {
        return operateParty;
    }

    public void setOperateParty(Party operateParty) {
        this.operateParty = operateParty;
    }

    public Account getOperateAccount() {
        return operateAccount;
    }

    public void setOperateAccount(Account operateAccount) {
        this.operateAccount = operateAccount;
    }
}
