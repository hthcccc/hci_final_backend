package com.example.hci.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_id", nullable = false, length = 24)
    private String id;

    @Column(name = "name", length = 24)
    private String name;

    @Column(name = "salt", length = 24)
    private String salt;

    @Column(name = "password", length = 32)
    private String password;

    @Column(name = "phone", length = 11)
    private String phone;

    @Column(name = "mail", length = 100)
    private String mail;

    @Column(name = "location", length = 50)
    private String location;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Lob
    @Column(name = "sex")
    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}