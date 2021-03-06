package com.moornmo.ltms;

import org.metaworks.annotation.Available;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.ServiceMethod;

import javax.persistence.*;

/**
 * Created by uengine on 2017. 5. 30..
 */
@Entity
@Table(name = "TB_PROG")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long progId;
        public Long getProgId() {
            return progId;
        }
        public void setProgId(Long progId) {
            this.progId = progId;
        }

    String progType;
        public String getProgType() {
            return progType;
        }
        public void setProgType(String progType) {
            this.progType = progType;
        }

    String progName;
        public String getProgName() {
            return progName;
        }
        public void setProgName(String progName) {
            this.progName = progName;
        }

    @Available(when="progType=='runnable'")
    @ServiceMethod()
    public void start(){
        System.out.println("Start the progress");
        setProgType("running");
    }


    @Available(when="progType=='running'")
    @ServiceMethod()
    public void suspend(){
        System.out.println("Start the progress");
        setProgType("stopped");
    }


    @Available(when="progType=='stopped'")
    @ServiceMethod()
    public void resume(){
        start();
    }


    @JoinColumn(name = "prodId")
   // @OneToOne(cascade = CascadeType.ALL)
    Product product;
    @Face(options = {"class", "vue-component"}, values = {"com.moornmo.ltms.Product", "reference-picker"})
        public Product getProduct() {
            return product;
        }
        public void setProduct(Product product) {
            this.product = product;
        }

}
