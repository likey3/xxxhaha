package com.xxx.service;

import com.xxx.entity.GamblingEntity;
import com.xxx.entity.RaceStyle;

import java.util.List;

/**
 * 由不同网站的数据得到比赛接口，归一化格式方便比较是否相同
 */
public interface IRaceStyle {
    List<RaceStyle> GetRaceStyles();

    /**
     * 得到比赛对象，特定网站的数据获得后对比数据库中的实体提取数据库中的
     * 一致对象
     *
     * @param str
     * @return
     */
    GamblingEntity generateEntity(String str);

    /**
     * 获得比赛时间，并归一化为标准格式,以格林威治时间为标准
     *1970-1-1 0:00:00
     * @param str
     * @return
     */
    long getRaceTime(String str);

    /**
     * 获得押注类型 归一化格式
     *
     * @param str
     * @return
     */
    RaceStyle.OddsType getOddsType(String str);

    /**
     * 获得比赛类型 归一化格式
     *
     * @param str
     * @return
     */
    RaceStyle.RaceType getRaceType(String str);
}
