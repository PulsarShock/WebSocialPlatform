package com.zlz.websocialplatform.entity;


public class User {
    String name;
    String sex;
    int age;

    public User(String name, String sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\": \"" + name + '\"' +
                ", \"sex\": \"" + sex + '\"' +
                ", \"age\":" + age +
                "}";
    }
}
