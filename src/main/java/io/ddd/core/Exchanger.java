package io.ddd.core;

/**
 * 说明：
 *
 * @author 周靖捷
 * Created by 周靖捷 on 2017/12/27.
 */
public interface Exchanger<CONSUME, PROVIDE> {

    PROVIDE exchange(CONSUME consume);

}
