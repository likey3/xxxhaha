package com.xxx.entity;

import java.util.*;

/**
 * 比赛方式
 */
public class RaceStyle {


    //所有盘口的押注类型抽象，没有时后期可以添加
    //在不同的盘口下要统一
    public static enum OddsType {
        独赢盘_含加时("独赢盘 (含加时)"),
        让分盘_含加时("让分盘 (含加时)"),
        总分大小盘_含加时("总分大小盘 (含加时)"),
        总进球数_大_小_含加时("总进球数 大/小 (含加时)"),
        总得分_单_双_上半场("总得分 单/双 - 上半场"),
        让分盘_上半场(" 让分盘 - 上半场"),
        总分大小盘_上半场("总分大小盘 - 上半场"),
        总得分单_双_含加时("总得分单/双 (含加时)"),
        T1_总得分数_含加时("T1_总得分数"),
        T2_总得分数_含加时("T2_总得分数");

        //可用于识别同一押注类型的特征标识，无法自动识别的人工识别
        //也可以做二次校正
        private final Set<String> features = new HashSet<>();

        private OddsType(String... features) {
            for (String str : features) {
                this.features.add(str);
            }

        }

        /**
         * 将传入的特征字串识别为对应押注类型
         *
         * @param feature 特征字符串
         * @return
         */
        public boolean isSame(String feature) {

            return features.contains(feature.trim());
        }

    }

    //比赛类型，用来识别不同网站的同一比赛
    public static enum RaceType {
        Football("足球"),
        Basketball("篮球"),
        ESports("电子竞技"),
        Tennis("网球"),
        VirtualFootball("虚拟足球"),
        IceHockey("冰上曲棍球"),
        Baseball("棒球"),
        Volleyball("排球"),
        AmericanFootball("美式足球"),
        TableTennis("乒乓球"),
        AustralianFootball("澳洲足球"),
        Rugby("英式橄榄球"),
        Five_a_side("五人制足球"),
        Handball("手球"),
        FloorBall("地板球"),
        BandyBall("班迪球"),
        Cricket("板球"),
        WaterPolo("水球");


        private final Set<String> features = new HashSet<>();

        private RaceType(String... features) {
            for (String str : features) {
                this.features.add(str);
            }
        }

        /**
         * 将传入的特征字串识别为对应比赛类型
         *
         * @param feature 特征字符串
         * @return
         */
        public boolean isSame(String feature) {
            return features.contains(feature);
        }
    }

    ;

    //参加比赛的对象
    private List<GamblingEntity> competitors;

    //比赛时间 距1970-1-1 0:00:00 所经过的毫秒数
    private long date;

    //比赛类型
    private RaceType type;

    //一场比赛中可以押注对象,该对象下的下注类型及下注参量
    private Map<GamblingEntity, Map<OddsType, GamblingEntity.BetClass>> winEntities = null;


    //不同押注类型下，不同参加者各个名次的赔率
    private Map<GamblingEntity, Map<OddsType, List<Double>>> oddsCategories = null;


    //唯一标识该场比赛，指定域名下唯一
    //不同域名下的同一RaceStyle必须一致
    private String ID;

    public RaceStyle() {
        competitors = new ArrayList<>();

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

    public void setCompetitors(List<GamblingEntity> competitors) {
        this.competitors = competitors;
    }

    //避免使用时赋予不适合的变量
    public void setOddsCategories(GamblingEntity entity, OddsType type, List<Double> odds) {
        if (oddsCategories == null) {
            oddsCategories = new HashMap<>();

        } else {
            if (oddsCategories.get(entity) == null) {
                oddsCategories.put(entity, new HashMap<>());

            }
            oddsCategories.get(entity).put(type, odds);
        }
    }


    //获得指定对像的赔率
    public List<Double> getEntityOdds(GamblingEntity entity, OddsType type) {
        return oddsCategories.get(entity).get(type);
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

    public Map<GamblingEntity, Map<OddsType, GamblingEntity.BetClass>> getWinEntities() {
        return winEntities;
    }

    //方便设置
    public void setWinEntities(GamblingEntity winEntity, OddsType type, GamblingEntity.BetClass bet) {
        if (winEntities == null) {
            winEntities = new HashMap<>();

        } else {
            if (winEntities.get(winEntity) == null) {
                winEntities.put(winEntity, new HashMap<>());

            }
            winEntities.get(winEntity).put(type, bet);
        }
    }

    public RaceType getRaceType() {
        return type;
    }

    public void setRaceType(RaceType type) {
        this.type = type;
    }
}

