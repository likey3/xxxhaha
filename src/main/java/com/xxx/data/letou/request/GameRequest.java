package com.xxx.data.letou.request;

public class GameRequest {
    public Integer ItemID;
    public String LanguageCode;
    public String SportCode;
    public Integer TimeZone;
    public Integer TournamentGroupID;
    public Integer endPage;
    public Integer sortType;
    public Integer sportTournamentCode;
    public Integer startPage;
    public String strEventDate;

    public GameRequest(Integer itemID, String sportCode) {
        this.ItemID = itemID;
        this.SportCode = sportCode;

        this.LanguageCode = "zh-cn";
        this.TimeZone = 8;
        this.TournamentGroupID = -1;
        this.endPage = 500;
        this.sortType = 1;
        this.sportTournamentCode = 0;
        this.startPage = 1;
        this.strEventDate = "1900/1/1";
    }
}
