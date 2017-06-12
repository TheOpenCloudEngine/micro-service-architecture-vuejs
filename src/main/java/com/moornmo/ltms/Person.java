package com.moornmo.ltms;

import org.metaworks.annotation.Face;

import javax.persistence.*;

/**
 * Created by uengine on 2017. 6. 12..
 */
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String ssn;

    String firstName;

    String lastName;

    int age;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @JoinColumn(name = "prodId")
    Product product;
        @Face(options = {"class", "vue-component"}, values = {"com.moornmo.ltms.Product", "reference-picker"})
        public Product getProduct() {
            return product;
        }
        public void setProduct(Product product) {
        this.product = product;
    }

}
