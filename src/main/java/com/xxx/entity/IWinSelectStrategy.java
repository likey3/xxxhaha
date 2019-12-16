package com.xxx.entity;

import java.util.List;

public interface IWinSelectStrategy {
    //在所有盘口中比较选择押注对象
    public void betMoney(List<RaceStyle> markets);
}
