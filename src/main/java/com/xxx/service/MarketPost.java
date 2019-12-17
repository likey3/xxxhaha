package com.xxx.service;

import com.xxx.entity.GambleMarket;

import java.util.Map;

/**
 * 生成盘口所需要的post数据
 */
public interface MarketPost {
    Map<String,String> GetBetPost(GambleMarket market);
}
