package com.xxx.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 将gamble所需要的属性抽象出来以便比较
 */
public  class GamblingEntity {


    //主参加对象特征，用于确定是同一个参与对象
    //特征可能有多个
    private List<String> names;


    //唯一标识比赛者，指定域名下唯一
    //不同域名下的同样队伍必须一致
    private String ID;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public static class BetClass {
        //假定押注时所在比赛的名次，当只有两个参赛者时，名次1为赢，2为输，
        //如果名次相同则为平
        private int order;

        //该名次下的赔率
        private double odds;

        //下注金额
        private double betMoney;

        /**
         * @param order    获得的比赛名次
         * @param odds     赔率
         * @param betMoney 下注金额
         */
        public BetClass(int order, double odds, double betMoney) {
            this.betMoney = betMoney;
            this.odds = odds;
            this.order = order;
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
}
