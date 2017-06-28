package com.moornmo.ltms;

import com.moornmo.framework.SearchForm;
import org.metaworks.annotation.Hidden;

/**
 * Created by uengine on 2017. 6. 25..
 */
public class ProductSearch extends Product implements SearchForm {

    @Override
    @Hidden
    public String getProdStandard() {
        return super.getProdStandard();
    }

    @Override
    @Hidden
    public String getProdStandard2() {
        return super.getProdStandard2();
    }

    @Override
    @Hidden
    public String getProdStandard3() {
        return super.getProdStandard3();
    }

    @Override
    @Hidden
    public String getProdStandard4() {
        return super.getProdStandard4();
    }

    @Override
    @Hidden
    public long getCurRestNum() {
        return super.getCurRestNum();
    }

    @Override
    @Hidden
    public long getOptiNum() {
        return super.getOptiNum();
    }
}
