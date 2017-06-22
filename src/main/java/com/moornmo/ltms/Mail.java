package com.moornmo.ltms;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.moornmo.framework.AfterLoad;
import com.moornmo.framework.BeforeSave;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Hidden;
import org.metaworks.annotation.ServiceMethod;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by uengine on 2017. 6. 21..
 */
@Entity
@Table(name="TB_MAIL")
@Multitenant
@TenantDiscriminatorColumn(name = "TENANTID", contextProperty = "tenant-id")
@Face(displayName = "메일발송리스트")
public class Mail implements BeforeSave, AfterLoad{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long mailId;
    @Hidden
        public Long getMailId() {
            return mailId;
        }
        public void setMailId(Long mailId) {
            this.mailId = mailId;
        }

    String sender;
    @Face(displayName = "발신자")
        public String getSender() {
            return sender;
        }
        public void setSender(String sender) {
            this.sender = sender;
        }

    String recipient;
    @Face(displayName = "수신자")
        public String getRecipient() {
            return recipient;
        }
        public void setRecipient(String recipient) {
            this.recipient = recipient;
        }

    String content;
    @Face(options="vue-component", values="text-area")

        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }



    @ServiceMethod
    public void send(){
        System.out.println("메일을 발송했습니다 to " + getRecipient());
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mail")
    List<MailProp> mailPropList;
    @Hidden
        public List<MailProp> getMailPropList() {
            return mailPropList;
        }
        public void setMailPropList(List<MailProp> mailPropList) {
            this.mailPropList = mailPropList;
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
            setMailPropList(new ArrayList<MailProp>());

            for(String key : getProps_().keySet()){
                MailProp property = new MailProp();
                property.setMail(this); ///// this part is specific
                property.setPropName(key);
                property.setValue(getProps_().get(key));

                getMailPropList().add(property);

            }
        }

    }

    @Override
    public void afterLoad() {
        if(getMailPropList()!=null){
            setProps_(new HashMap<String, String>());

            for(MailProp property : getMailPropList()){
                getProps_().put(property.getPropName(), property.getValue());
            }
        }
    }



}
