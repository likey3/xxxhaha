package com.xxx.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 将gamble所需要的属性抽象出来以便比较
 */
public  class GamblingEntity {

    //所在域名地址
    private String webAdress;

    //主参加对象名称
    private String name;

    //比赛时间
    private Date date;

    //参与竞争对象
    private List<GamblingEntity> rivals;

    //唯一标识该场比赛，指定域名下唯一
    //不同域名下的同样队伍必须一致
    private Long ID;

    //假定押注时所在比赛的名次，当只有两个参赛者时，名次1为赢，2为输，
    //如果名次相同则为平
    private int order;

    //该名次下的赔率
    private double odds;

    //下注金额
    private double betMoney;


    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
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
