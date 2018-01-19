package io.ddd.core.event;

/**
 * 说明：
 *
 * @author 周靖捷
 * Created by 周靖捷 on 2017/12/28.
 */
public interface MailBox {
    static final MailBox JVM_MAIL_BOX = new DefaultMailBox();

    static MailBox get() {
        return JVM_MAIL_BOX;
    }

    void post(Object mail);

    void registerReceiver(Object receiver);

    void unRegisterReceiver(Object receiver);


}
