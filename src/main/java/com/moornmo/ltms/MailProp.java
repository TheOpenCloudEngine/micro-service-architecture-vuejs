package com.moornmo.ltms;

import javax.persistence.*;

/**
 * Created by uengine on 2017. 6. 6..
 */

@Entity
public class MailProp {



    @Id
    String propName;
        public String getPropName() {
            return propName;
        }
        public void setPropName(String propName) {
            this.propName = propName;
        }

    String value;
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }


    @Id
    @ManyToOne
    @JoinColumn(name="mailId")
    Mail mail;
        public Mail getMail() {
            return mail;
        }
        public void setMail(Mail mail) {
            this.mail = mail;
        }



}
