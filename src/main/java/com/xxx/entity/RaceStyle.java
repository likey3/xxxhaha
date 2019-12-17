package com.xxx.entity;

import java.util.*;

/**
 * 比赛方式
 */
public abstract class RaceStyle {

    //所有盘口的押注类型抽象，没有时后期可以添加
    public static  enum OddsType{
        独赢盘_含加时,
        让分盘_含加时,
        总分大小盘_含加时,
        总进球数_大_小_含加时,
        总得分_单_双_上半场,
        让分盘_上半场,
        总分大小盘_上半场,
        总得分单_双_含加时,
        T1_总得分数_含加时,
        T2_总得分数_含加时,

    }

    //各个参赛者对应名次下的赔率
    private Map<GamblingEntity, List<Double>> orderOdds;

    //参加比赛的对象
    private List<GamblingEntity> competitors;

    //比赛时间
    private Date date;


    //一场比赛中可以押注对象
    private List<GamblingEntity> winEntities = new ArrayList<>();

    //押注类型,在不同的盘口下要统一
    private Map<OddsType,Map<GamblingEntity, List<Double>>> oddsCategories;


    //唯一标识该场比赛，指定域名下唯一
    //不同域名下的同一RaceStyle必须一致
    private String ID;

    public RaceStyle() {
        competitors = new ArrayList<>();
        orderOdds = new Hashtable<>();

    }

    //插入时排好顺序，以便后续不同盘口比较确定对像
    public void addCompetor(GamblingEntity competitor) {
        if (competitors.size() == 0) {
            this.competitors.add(competitor);
        } else {
            int index = competitors.size();//要插入的位置
            for (int i = 0; i < competitors.size(); i++) {
                if (competitor.getID().compareTo(competitors.get(i).getID()) < 0) {
                    index = i;
                    break;
                }
            }
            competitors.add(index, competitor);
        }

    }

    public void putOrderOdds(GamblingEntity entity, List<Double> orderOdds) {
        this.orderOdds.put(entity, orderOdds);
    }


    //获得指定对像的赔率
    public List<Double> getEntityOdds(GamblingEntity entity) {
        return orderOdds.get(entity);
    }

    public GamblingEntity getEntity(int index) {
        return competitors.get(index);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public List<GamblingEntity> getWinEntities() {
        return winEntities;
    }

    public void setWinEntities(List<GamblingEntity> winEntities) {
        this.winEntities = winEntities;
    }
}

