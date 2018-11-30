package com.agtech.lunch.enums;

/**
 * Created by IntelliJ IDEA
 * User: jinshi.zjs
 * Date: 2018-11-30
 * Time: 14:00
 */
public enum Restaurant {

    XIAOHENGSHUIJIAO("小恒水饺", 0),
    LAOWANCHENG("老碗盛", 500),
    JIYEJIA("吉野家", 200),
    XINJIANGLAMIAN("新疆拉面", 100),
    YANGUOFU("杨国福", 100),
    GUOLING("郭林", 100),
    DASHIDAI("大食代", 50),
    MEATMATE("Meat Mate", 2),
    ;


    private String name;
    private int weight;


    Restaurant(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }
}
