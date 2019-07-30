package com.anjukakoralage.hondapromoadmin;

/**
 * Created by anjukakoralage on 29,July,2019
 */
public class Profile {
    private String name;
    private String age;
    private String tp;
    private String city;
    private String date;
    private Boolean permission;

    public Profile() {
    }

    public Profile(String name, String age, String tp, String city, String date, Boolean permission) {
        this.name = name;
        this.age = age;
        this.tp = tp;
        this.city = city;
        this.date = date;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getPermission() {
        return permission;
    }

    public void setPermission(Boolean permission) {
        this.permission = permission;
    }
}
