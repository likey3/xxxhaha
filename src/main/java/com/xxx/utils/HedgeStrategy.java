package com.xxx.utils;

import com.xxx.entity.GambleMarket;
import com.xxx.entity.GamblingEntity;
import com.xxx.entity.IWinSelectStrategy;
import com.xxx.entity.RaceStyle;

import java.util.*;

/**
 * 对冲策略，a*b>1 此时赔率包含本金
 */
public class HedgeStrategy implements IWinSelectStrategy {


    //初始押注额度
    private double initialBet = 100;


    /**
     * @param markets 不同盘口下的同一比赛
     */
    @Override
    public void betMoney(List<GambleMarket> markets) {
        //挑选出不同盘口下的同一比赛
        GambleMarket market = markets.get(0);
        Set<String> keys = market.getAllRaces().keySet();
        List<RaceStyle[]> raceStyles = new ArrayList<RaceStyle[]>();
        keys.parallelStream().forEach(x -> {
            RaceStyle[] marketRaceStyle = new RaceStyle[markets.size()];
            markets.parallelStream().forEach(y -> {
                int index1 = markets.indexOf(y);
                marketRaceStyle[index1] = y.getSpecialRace(x);
                //清空链表中原先可能存在的元素
                marketRaceStyle[index1].getWinEntities().clear();
            });
            raceStyles.add(marketRaceStyle);
        });


        Random rand = new Random();

        for (int k1 = 0; k1 < raceStyles.size(); k1++) {
            RaceStyle[] races = raceStyles.get(k1);
            for (int i = 0; i < markets.size(); i++) {
                RaceStyle race1 = races[i];

                for (int j = i + 1; j < markets.size(); j++) {
                    List<GamblingEntity> group1 = new ArrayList<>();
                    RaceStyle race2 = races[j];
                    for (RaceStyle.OddsType type1 : RaceStyle.OddsType.values()) {
                        List<Double> odds1 = race1.getEntityOdds(race1.getEntity(0), type1);
                        List<Double> odds2 = race2.getEntityOdds(race2.getEntity(1), type1);
                        if (odds1 != null && odds2 != null) {
                            //假设第一个名次1，第二个名次2
                            double odd1 = odds1.get(0);
                            double odd2 = odds2.get(1);
                            double rate1 = odd1 * odd2;
                            //假设第一个名次2，第二个名次1
                            odd1 = odds1.get(1);
                            odd2 = odds2.get(0);
                            double rate2 = odd1 * odd2;
                            GamblingEntity entity1 = null;
                            GamblingEntity entity2 = null;
                            if (rate2 > rate1 && rate1 > 1) {
                                entity1 = race1.getEntity(1);
                                race1.setWinEntities(entity1, type1, new GamblingEntity.BetClass(1, odd1, initialBet));


                                entity2 = race2.getEntity(0);
                                double bet2 = rand.nextDouble() * (initialBet * odd1 - initialBet / odd2);
                                race1.setWinEntities(entity2, type1, new GamblingEntity.BetClass(0, odd2, bet2));
                            } else if (rate1 > rate2 && rate2 > 1) {

                                entity1 = race1.getEntity(0);
                                race1.setWinEntities(entity1, type1, new GamblingEntity.BetClass(1, odd1, initialBet));

                                entity2 = race2.getEntity(1);
                                double bet2 = rand.nextDouble() * (initialBet * odd1 - initialBet / odd2);
                                race1.setWinEntities(entity2, type1, new GamblingEntity.BetClass(0, odd2, bet2));
                            }
                        }

                    }


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
