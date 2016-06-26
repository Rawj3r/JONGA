package com.risencolab.rogernkosi.jonga;

/**
 * Created by empirestate on 6/25/16.
 */
public class ContactsModel {
    String cname, cnumber;
    int cid;

    public ContactsModel(CBuilder builder) {
        this.cname = builder.ccname;
        this.cnumber = builder.ccnumber;
        this.cid = builder.ccid;
    }

    public static class CBuilder{

        String ccname, ccnumber;
        int ccid;

        public CBuilder setCcname(String ccname) {
            this.ccname = ccname;
            return CBuilder.this;
        }

        public CBuilder setCcnumber(String ccnumber) {
            this.ccnumber = ccnumber;
            return CBuilder.this;
        }

        public CBuilder setCcid(int ccid) {
            this.ccid = ccid;
            return CBuilder.this;
        }
        public ContactsModel build(){
            return new ContactsModel(CBuilder.this);
        }
    }

}
