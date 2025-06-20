package com.example.PassBox;

public class PassList {

        private String id,site,username,password,note ;

        public PassList(String id, String site,String username,String password,String note) {
            this.id = id;
            this.site = site;
            this.username = username;
            this.password = password;
            this.note = note;
        }

        public String getId() { return id; }
        public String getSite() { return site; }
        public String getUsername() { return username; }
        public String getPassword() { return password; }
        public String getNote() { return note; }


}
