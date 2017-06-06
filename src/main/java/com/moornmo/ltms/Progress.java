package com.moornmo.ltms;

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


}
