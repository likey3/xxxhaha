package com.xxx.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 盘口
 */
public abstract class GambleMarket {


    //所在域名
    String webAdress;

    //用户名与密码
    String userName;
    String userPassword;


    //该盘口所具有的所有比赛
  private Map<String,RaceStyle> allRaces;

    //该盘口下值得押注的比赛
    private List<RaceStyle> winRaces;

    public Map<String,RaceStyle> getAllRaces() {
        return allRaces;

    }



    public void setAllRaces( Map<String,RaceStyle> allRaces) {
        this.allRaces = allRaces;
    }

    //获得指定类型的比赛者
    public RaceStyle getSpecialRace(String id)
    {
        return allRaces.get(id);
    }


    public List<RaceStyle> getWinRaces() {
        return winRaces;
    }

    public void setWinRaces(List<RaceStyle> winRaces) {
        this.winRaces = winRaces;
    }

    /**
     * 获得需要提交的数据，由具体类实现
     *
     * @return
     */
    public abstract  Map<String,String> GetBetPost();

    /**
     * 生成一致性数据实体GambleMarket 所需要的数据
     */
    protected abstract void creatGambleMarket();
}
