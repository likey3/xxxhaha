package com.xxx.entity;

import java.util.*;

/**
 * 比赛方式
 */
public abstract class RaceStyle {
    //各个参赛者对应名次下的赔率
    private Map<GamblingEntity, List<Double>> orderOdds;

    //参加比赛的对象
    private List<GamblingEntity> competitors;

    //具体判断输赢的方式
    protected abstract void sortCompetors();


    public RaceStyle() {
        competitors = new ArrayList<>();
        orderOdds = new Hashtable<>();
    }

    //插入时排好顺序，以便后续不同盘口比较确定对像
    public void addCompetor(GamblingEntity competitor) {
        if (competitors.size() == 0) {
            this.competitors.add(competitor);
        } else {
            int index =  competitors.size();//要插入的位置
            for (int i = 0; i < competitors.size(); i++) {
                if (competitor.getID() < competitors.get(i).getID()) {
                    index = i;
                    break;
                }
            }
            competitors.add(index,competitor);
        }

    }

    public void putOrderOdds(GamblingEntity entity, List<Double> orderOdds) {
        this.orderOdds.put(entity, orderOdds);
    }


    //获得指定对像的赔率
    public List<Double> getEntityOdds(GamblingEntity entity) {
        return orderOdds.get(entity);
    }

    public GamblingEntity getEntity(int index)
    {
        return competitors.get(index);
    }
}
