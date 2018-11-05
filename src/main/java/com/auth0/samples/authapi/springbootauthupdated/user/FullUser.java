package com.auth0.samples.authapi.springbootauthupdated.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FullUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String parentName;
    private String kidName;
    private String parentEmail;
    private String kidEmail;
    private String parentCellphone;
    private String kidCellphone;
    private String password;

    public String getKidEmail() {
        return kidEmail;
    }

    public void setKidEmail(String kidEmail) {
        this.kidEmail = kidEmail;
    }

    public String getParentCellphone() {
        return parentCellphone;
    }

    public void setParentCellphone(String parentCellphone) {
        this.parentCellphone = parentCellphone;
    }

    public String getKidCellphone() {
        return kidCellphone;
    }

    public void setKidCellphone(String kidCellphone) {
        this.kidCellphone = kidCellphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getKidName() {
        return kidName;
    }

    public void setKidName(String kidName) {
        this.kidName = kidName;
    }
}