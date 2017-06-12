package com.moornmo.ltms;

import javax.persistence.*;

/**
 * Created by uengine on 2017. 6. 6..
 */

@Entity
public class Property {



    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

//    public long getpNo() {
//        return pNo;
//    }
//
//    public void setpNo(long pNo) {
//        this.pNo = pNo;
//    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Id
    @ManyToOne
    @JoinColumn(name="pNo")
    Product product;

    @Id
    String propName;

    String value;
}
