package com.moornmo.ltms;

import com.moornmo.framework.SearchForm;
import org.metaworks.annotation.Hidden;

/**
 * Created by uengine on 2017. 6. 25..
 */
public class ProductSearch extends Product implements SearchForm {

    @Override
    public String getProdStandard() {
        return super.getProdStandard();
    }

    @Override
    public String getProdStandard2() {
        return super.getProdStandard2();
    }

    @Override
    public String getProdStandard3() {
        return super.getProdStandard3();
    }

    @Override
    public String getProdStandard4() {
        return super.getProdStandard4();
    }

    @Override
    public long getCurRestNum() {
        return super.getCurRestNum();
    }

    @Override
    public long getOptiNum() {
        return super.getOptiNum();
    }
}
