package io.ddd.core.event.provide;

/**
 * 说明：
 *
 * @author 周靖捷
 * Created by 周靖捷 on 2017/12/27.
 */
public interface Provider<T> {

    T provide();

    Class<T> getProvidedType();

}
