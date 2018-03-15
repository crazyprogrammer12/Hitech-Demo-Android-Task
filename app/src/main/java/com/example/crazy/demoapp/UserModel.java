package com.example.crazy.demoapp;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by mecra on 3/14/2018.
 */

public class UserModel {

    private List<UserResult> userResults;

    public List<UserResult> getUserResults() {
        return userResults;
    }

    public void setUserResults(List<UserResult> userResults) {
        this.userResults = userResults;
    }

    @Table(name = "UserInfo")
    public static class UserResult extends Model {

        @Column(name = "UserID")
        private String userId;

        @Column(name = "Name")
        private String name;

        @Column(name = "Email")
        private String email;

        @Column(name = "Company")
        private String company;

        @Column(name = "Mobile")
        private String mobile;

        @Column(name = "Password")
        private String password;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
