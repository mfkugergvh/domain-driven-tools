package io.ddd.core;

/**
 * 说明：
 *
 * @author 周靖捷
 * Created by 周靖捷 on 2017/12/26.
 */
public abstract class Role<DATA> implements Interaction<DATA> {
    protected DATA data;

    public Role<DATA> actAs(DATA data) {
        this.data = data;
        return this;
    }

    @Override
    public DATA that() {
        return data;
    }

}
