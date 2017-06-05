package com.moornmo.ltms;

import org.metaworks.annotation.Face;
import org.metaworks.annotation.Hidden;
import org.metaworks.annotation.Order;

import javax.persistence.*;

/**
 * Created by uengine on 2017. 5. 29..
 */
@Entity
@Table(name = "TB_PROD")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long pNo;

    String prodNumber;
    private boolean deleted;

    @Face(displayName = "품번(제품코드)")
    @Order(10)
        public String getProdNumber() {
            return prodNumber;
        }
        public void setProdNumber(String prodNumber) {
            this.prodNumber = prodNumber;
        }

    String prodName;
    @Face(displayName = "품명")
    @Order(20)
        public String getProdName() {
            return prodName;
        }
        public void setProdName(String prodName) {
            this.prodName = prodName;
        }

    String prodStandard;
    @Face(displayName = "대표규격")
    @Order(30)
        public String getProdStandard() {
            return prodStandard;
        }
        public void setProdStandard(String prodStandard) {
            this.prodStandard = prodStandard;
        }

    String prodStandard2;
    @Face(displayName = "규격2")
    @Order(40)
        public String getProdStandard2() {
            return prodStandard2;
        }
        public void setProdStandard2(String prodStandard2) {
            this.prodStandard2 = prodStandard2;
        }

    String prodStandard3;
    @Face(displayName = "규격3")
    @Order(50)
        public String getProdStandard3() {
            return prodStandard3;
        }
        public void setProdStandard3(String prodStandard3) {
            this.prodStandard3 = prodStandard3;
        }

    String prodStandard4;
    @Face(displayName = "규격4")
    @Order(60)
    @Hidden
        public String getProdStandard4() {
            return prodStandard4;
        }
        public void setProdStandard4(String prodStandard4) {
            this.prodStandard4 = prodStandard4;
        }

    long curRestNum;
    @Face(displayName = "현재재고수")
    @Order(70)
        public long getCurRestNum() {
            return curRestNum;
        }
        public void setCurRestNum(long curRestNum) {
            this.curRestNum = curRestNum;
        }

    long optiNum;
    @Face(displayName = "적정재고수")
    @Order(80)
        public long getOptiNum() {
            return optiNum;
        }
        public void setOptiNum(long optiNum) {
            this.optiNum = optiNum;
        }


    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
