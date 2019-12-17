package com.xxx.data.letou;

import com.alibaba.fastjson.JSONObject;
import com.xxx.data.Letou;
import com.xxx.utils.JavaScriptEngine;
import com.xxx.utils.LZString;

import javax.swing.plaf.synth.Region;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class GameData {
    static enum Properties {
        BetOptionContentTemplate("BetOptionContentTemplate"),
        BetOptionContentCompetitorTemplate("BetOptionContentCompetitorTemplate"),
        BetOptionContentRateTeimlate("BetOptionContentRateTeimlate"),
        BetOptionContentTournamentTemplate("BetOptionContentTournamentTemplate"),
        BetOptionContentGroupOptionTemplate("BetOptionContentGroupOptionTemplate");
        private final String property;

        Properties(String val) {
            property = val;
        }

        public String getValue() {
            return property;
        }

    }

    LetouData letouData;


    public String Parse() {

        String json, json1, json2, json3, json4;


        Map<Properties, String> map = getTemplate();


        letouData = JSONObject.parseObject(getData(), LetouData.class);

        String funcName = Letou.JS.Parse.getValue();

        long st1=System.currentTimeMillis();


        //region 解析对应数据
        String[] jsons=new String[5];
        letouData.d.parallelStream().forEach(x->{
            int index=letouData.d.indexOf(x);
            if ( index<5)
            {
                Properties type=null;

                switch (index)
                {
                    case 0:
                        type=Properties.BetOptionContentTemplate;
                        break;
                    case 1:
                        type=Properties.BetOptionContentCompetitorTemplate;
                        break;
                    case 2:
                        type=Properties.BetOptionContentRateTeimlate;
                        break;
                    case 3:
                        type=Properties.BetOptionContentTournamentTemplate;
                        break;
                    case 4:
                        type=Properties.BetOptionContentGroupOptionTemplate;
                        break;

                }
                String str= LZString.decompressFromBase64(letouData.d.get(index));
                jsons[index]=JavaScriptEngine.execu(funcName, map.get(type),str);
            }
        });
        //endregion
        long s2=System.currentTimeMillis();
        System.out.println(s2-st1);
        return "";
    }

    /**
     * 解析所需要的模板
     *
     * @return
     */
    Map<Properties, String> getTemplate() {
        Map<Properties, String> templates = new HashMap();
        String BetOptionContentTemplate = "OrderByTournament,OrderByEventTime,EventType,EventCode,SportCode,SportTournamentCode,EventDateTime,StrEventDateTime,HasLive,Status,IsStatistic,EventWeight,GroupOptionCnt,NeutralGround,TA,TB,RTD,IsShowTeamLogo,HasStreaming";
        String BetOptionContentCompetitorTemplate = "EventCode,SportCompetitorCode,CompetitorMultiLangName,TeamAB,HasBigFig,HasMediumFig,HasSmallFig";
        String BetOptionContentRateTeimlate = "GUID,EventCode,GroupOptionCode,SpecialBetValue,SPVDisplay,OptionCode,OptionRate,OptionName,tagid,GOID";
        String BetOptionContentTournamentTemplate = "OrderBy,SportTournamentCode,TouMultiLangName,EV";
        String BetOptionContentGroupOptionTemplate = "GroupOptionCode,GroupOptionName,GLSpBetGroupOptionGUID,OrderBy";


        templates.put(Properties.BetOptionContentTemplate, BetOptionContentTemplate);
        templates.put(Properties.BetOptionContentCompetitorTemplate, BetOptionContentCompetitorTemplate);
        templates.put(Properties.BetOptionContentRateTeimlate, BetOptionContentRateTeimlate);
        templates.put(Properties.BetOptionContentTournamentTemplate, BetOptionContentTournamentTemplate);
        templates.put(Properties.BetOptionContentGroupOptionTemplate, BetOptionContentGroupOptionTemplate);
        return templates;
    }

    /**
     * 解析所需要的数据
     *
     * @return
     */
    String getData() {
        String data = "";
        File f = new File("E:\\WEB\\xxxhaha\\src\\main\\java\\bin\\letou\\receiveData");
        Long fileL = f.length();
        byte[] filecontent = new byte[fileL.intValue()];

        FileInputStream in = null;

        try {
            in = new FileInputStream(f);
            in.read(filecontent);
            in.close();
            data = new String(filecontent, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public void setLetouData(LetouData letouData) {
        this.letouData = letouData;
    }
}
