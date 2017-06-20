package com.moornmo.ltms;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.moornmo.framework.AfterLoad;
import com.moornmo.framework.BeforeSave;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;
import org.jvnet.hk2.annotations.Service;
import org.metaworks.annotation.*;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by uengine on 2017. 5. 29..
 */
@Entity
@Table(name = "TB_PROD")
@Multitenant
@TenantDiscriminatorColumn(name = "TENANTID", contextProperty = "tenant-id")
@Face(displayName = "제품")
public class Product implements BeforeSave, AfterLoad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long pNo;
    @Hidden
    @org.metaworks.annotation.Id
        public Long getpNo() {
            return pNo;
        }
        public void setpNo(Long pNo) {
            this.pNo = pNo;
        }


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
    @Name
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

    @Hidden
    public boolean isDeleted() {
        return deleted;
    }


    @Column(name="TENANTID", insertable=false, updatable=false)
    String tenantId;
    @Hidden
        public String getTenantId() {
            return tenantId;
        }
        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    List<Property> props;
        @Hidden
        public List<Property> getProps() {
            return props;
        }
        public void setProps(List<Property> props) {
            this.props = props;
        }

    @Transient
    Map<String, String> props_;
        @JsonAnyGetter
        @Hidden
        public Map<String, String> getProps_() {
            return props_;
        }
        public void setProps_(Map<String, String> props_) {
            this.props_ = props_;
        }
        @JsonAnySetter
        public void addProps_(String key, String value) {
            if(this.props_ == null)
                this.props_ = new HashMap<String, String>();

            this.props_.put(key, value);
        }


    @Override //this will not be required if the CASCADE option operates properly.
    public void beforeSave() {

        if(getProps_()!=null){
            setProps(new ArrayList<Property>());

            for(String key : getProps_().keySet()){
                Property property = new Property();
                property.setProduct(this); ///// this part is specific
                property.setPropName(key);
                property.setValue(getProps_().get(key));

                getProps().add(property);

            }
        }

    }

    @Override
    public void afterLoad() {
        if(getProps()!=null){
            setProps_(new HashMap<String, String>());

            for(Property property : getProps()){
                getProps_().put(property.getPropName(), property.getValue());
            }
        }
    }


    @ServiceMethod(callByContent = true)
    public void validate(){
        if(getProdName()==null){
            throw new IllegalArgumentException("Prod name must be not null");
        }
    }
}
