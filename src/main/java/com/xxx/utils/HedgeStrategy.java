package com.xxx.utils;

import com.xxx.entity.GambleMarket;
import com.xxx.entity.GamblingEntity;
import com.xxx.entity.IWinSelectStrategy;
import com.xxx.entity.RaceStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 对冲策略，a*b>1 此时赔率包含本金
 */
public class HedgeStrategy implements IWinSelectStrategy {



    //初始押注额度
    private double initialBet = 100;

    //计算得出的结果
    private List<List<GamblingEntity>> results = new ArrayList<List<GamblingEntity>>();



    /**
     * @param markets 不同盘口下的同一比赛，待完成
     */
    @Override
    public void betMoney(List<RaceStyle> markets) {
        Random rand=new Random();
        for (int i = 0; i < markets.size(); i++) {
            RaceStyle race1 = markets.get(i);

            for (int j = i + 1; j < markets.size(); j++) {
                List<GamblingEntity> group1 = new ArrayList<>();
                RaceStyle race2 = markets.get(j);

                List<Double> odds1 = race1.getEntityOdds(race1.getEntity(0));
                List<Double> odds2 = race2.getEntityOdds(race2.getEntity(1));
                //假设第一个名次1，第二个名次2
                double odd1 = odds1.get(0);
                double odd2 = odds2.get(1);
                double rate1 = odd1 * odd2;
                //假设第一个名次2，第二个名次1
                odd1 = odds1.get(1);
                odd2 = odds2.get(0);
                double rate2 = odd1 * odd2;
                GamblingEntity entity1=null;
                GamblingEntity entity2=null;
                if (rate2 > rate1 && rate1 > 1) {
                    entity1 = race1.getEntity(1);
                    entity1.setOrder(1);
                    entity1.setOdds(odd1);
                    entity1.setBetMoney(initialBet);

                     entity2 = race2.getEntity(0);
                    entity2.setOrder(0);
                    entity2.setOdds(odd2);
                    double bet2= rand.nextDouble()*(initialBet*odd1- initialBet/odd2);
                    entity2.setBetMoney(bet2);

                    group1.add(entity1);
                    group1.add(entity2);
                    results.add(group1);

                } else if (rate1 > rate2 && rate2 > 1) {

                    entity1 = race1.getEntity(0);
                    entity1.setOrder(0);
                    entity1.setOdds(odd1);
                    entity1.setBetMoney(initialBet);

                    entity2 = race2.getEntity(1);
                    entity2.setOrder(1);
                    entity2.setOdds(odd2);
                    double bet2= rand.nextDouble()*(initialBet*odd1- initialBet/odd2);
                    entity2.setBetMoney(bet2);

                    group1.add(entity1);
                    group1.add(entity2);
                    results.add(group1);
                }

            }
        }


    }

    public double getInitialBet() {
        return initialBet;
    }

    public void setInitialBet(double initialBet) {
        this.initialBet = initialBet;
    }
}
