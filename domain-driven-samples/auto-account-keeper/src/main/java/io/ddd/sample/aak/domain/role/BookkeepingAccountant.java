package io.ddd.sample.aak.domain.role;

import io.ddd.core.Interaction;
import io.ddd.core.exception.DomainLayerException;
import io.ddd.mixin.Persistable;
import io.ddd.sample.aak.domain.entity.AccountEntry;
import io.ddd.sample.aak.domain.entity.TheAccountBookkeeper;
import io.ddd.sample.aak.domain.entity.Voucher;

import java.math.BigDecimal;
import java.util.List;

public interface BookkeepingAccountant extends Interaction<TheAccountBookkeeper>, Persistable {


    /**
     * Bookkeeping A Voucher
     *
     * @param voucher Voucher
     */
    default void write(Voucher voucher) {
        if (!trialBalance(voucher)) {
            throw new IncorrectVoucherException("DebitAmount And CreditAmount Must Be Equal", this);
        }

        try {
            insert(voucher);
        } catch (Exception e) {
            throw new DomainLayerException(voucher.toString() + "-->Persist Domain Object Failed", this, e);
        }


    }

    /**
     * Trail Balance
     *
     * @param voucher Voucher
     * @return Boolean-->isBalanced
     */
    default boolean trialBalance(Voucher voucher) {

        List<AccountEntry> accountEntries = voucher.getAccountEntries();

        BigDecimal creditAmount = BigDecimal.ZERO;

        BigDecimal debitAmount = BigDecimal.ZERO;

        for (AccountEntry accountEntry : accountEntries) {
            switch (accountEntry.getDirection()) {
                case C:
                    creditAmount = creditAmount.add(accountEntry.getAmount());
                    break;
                case D:
                    debitAmount = debitAmount.add(accountEntry.getAmount());
                    break;
                default:
                    throw new IncorrectVoucherException(voucher.toString() + "-->" +
                            "Unknown AccountDirection[" + accountEntry.getDirection() + "]", this);
            }
        }

        return creditAmount.setScale(2).equals(debitAmount.setScale(2));
    }

    class IncorrectVoucherException extends DomainLayerException {
        public IncorrectVoucherException(String s, BookkeepingAccountant bookkeepingAccountant) {
            super(s, bookkeepingAccountant);
        }
    }
}
