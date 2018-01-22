package io.ddd.sample.aak.domain.role;

import io.ddd.core.action.Mode;
import io.ddd.core.event.MailBox;
import io.ddd.core.event.persist.annotation.OnPersist;
import io.ddd.core.exception.DomainLayerException;
import io.ddd.core.tools.$;
import io.ddd.sample.aak.domain.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.UUID;

public class BookkeepingAccountantTest {
    static Logger logger = LoggerFactory.getLogger(BookkeepingAccountantTest.class);

    public static class MockRepository {


        @OnPersist(mode = Mode.INSERT)
        public void insertVoucher(Voucher voucher) {
            logger.info("insert object --> {}", voucher.toString());
        }


    }


    @Before
    public void setUp() throws Exception {
        MailBox.get().registerReceiver(new MockRepository());
    }

    @Test
    public void write() throws Exception {
        IVoucher iVoucher = IVoucher.builder(UUID.randomUUID().toString());
        AccountEntry creditEntry = new AccountEntry();
        creditEntry
                .id(UUID.randomUUID().toString())
                .topTitle(TopTitleKind.ASSET)
                .subTitle("现金")
                .direction(Direction.C)
                .amount(new BigDecimal("200.00"));
        AccountEntry debitEntry = new AccountEntry();
        debitEntry
                .id(UUID.randomUUID().toString())
                .topTitle(TopTitleKind.LIABILITY)
                .direction(Direction.D)
                .subTitle("欠款")
                .amount(new BigDecimal("200.00"));
        iVoucher.addTrailBalancedEntries(creditEntry, debitEntry);
        BookkeepingAccountant bookkeepingAccountant = $.cast(BookkeepingAccountant.class, new TheAccountBookkeeper());
        try {
            bookkeepingAccountant.write(iVoucher.that());
        } catch (DomainLayerException e) {
            e.getTargetException().printStackTrace();
        }
    }

    @Test
    public void trialBalance() throws Exception {
    }

}