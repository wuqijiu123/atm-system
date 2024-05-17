package com.wzy;

import java.util.Random;

public class Account {
    private String number;
    private String name;
    private String sex;
    private int password;
    private double money;
    double limit = 10000.0;

    public Account() {
    }

    public Account(int count, String name, String sex, int password, double money) {
        this.number = createNumber(count);
        this.name = name;
        this.sex = sex;
        this.password = password;
        this.money = money;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public static String createNumber(int count){
        String p = "000000000";
        StringBuilder id = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            id.append(r.nextInt(10));
        }
        String m = String.valueOf(count);
        return id + p.substring(0,9-m.length()) + m;
    }

}
