package io.ddd.sample.aak.domain.role;

import io.ddd.core.Interaction;
import io.ddd.core.exception.DomainLayerException;
import io.ddd.core.tools.$;
import io.ddd.sample.aak.domain.entity.AccountEntry;
import io.ddd.sample.aak.domain.entity.Voucher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public interface IVoucher extends Interaction<Voucher> {
    static IVoucher builder(String id) {
        Voucher voucher = new Voucher();
        voucher.setId(id);
        voucher.setAccountEntries(new ArrayList<>());

        IVoucher iVoucher = $.cast(IVoucher.class, voucher);
        return iVoucher;
    }


    default IVoucher addTrailBalancedEntries(AccountEntry... accountEntries) {

        BigDecimal creditAmount = BigDecimal.ZERO;

        BigDecimal debitAmount = BigDecimal.ZERO;

        for (AccountEntry accountEntry : accountEntries) {
            accountEntry.setVoucherId(that().getId());
            switch (accountEntry.getDirection()) {
                case C:
                    creditAmount = creditAmount.add(accountEntry.getAmount());
                    break;
                case D:
                    debitAmount = debitAmount.add(accountEntry.getAmount());
                    break;
                default:
                    throw new DomainLayerException(that().toString() + "-->" +
                            "Unknown AccountDirection[" + accountEntry.getDirection() + "]", this);
            }
        }

        if (creditAmount.setScale(2).equals(debitAmount.setScale(2))) {
            that().getAccountEntries().addAll(Arrays.asList(accountEntries));
            return this;
        }
        throw new DomainLayerException(that().toString() + "-->Wrong Vouchers Not Trail Balanced Entries", this);
    }
}
