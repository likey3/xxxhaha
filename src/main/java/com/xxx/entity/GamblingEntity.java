package com.xxx.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 将gamble所需要的属性抽象出来以便比较
 */
public  class GamblingEntity {


    //主参加对象名称
    private String name;


    //唯一标识比赛者，指定域名下唯一
    //不同域名下的同样队伍必须一致
    private String ID;

    //假定押注时所在比赛的名次，当只有两个参赛者时，名次1为赢，2为输，
    //如果名次相同则为平
    private int order;

    //该名次下的赔率
    private double odds;

    //下注金额
    private double betMoney;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }

    public double getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(double betMoney) {
        this.betMoney = betMoney;
    }
}
