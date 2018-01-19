package io.ddd.core.event.provide;


/**
 * 说明：
 *
 * @author 周靖捷
 * Created by 周靖捷 on 2018/1/8.
 */
public interface IdProvider extends Provider<String> {
    @Override
    default Class<String> getProvidedType() {
        return String.class;
    }
}
