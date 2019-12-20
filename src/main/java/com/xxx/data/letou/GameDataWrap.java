package com.xxx.data.letou;

import com.alibaba.fastjson.JSONObject;
import com.xxx.data.Letou;
import com.xxx.data.letou.OriginGameData.*;

import com.xxx.entity.RaceStyle;
import com.xxx.service.IRaceStyle;
import com.xxx.utils.JavaScriptEngine;
import com.xxx.utils.LZString;

import javax.swing.plaf.synth.Region;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 将远端解析的数据
 */
public class GameDataWrap implements IRaceStyle {


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

    //解析出的数据
    private List<Data0> data0s;
    private List<Data1> data1s;
    private List<Data2> data2s;
    private List<Data3> data3s;
    private List<Data4> data4s;


    private LetouData letouData;

    //网站的比赛数据
    Map<String, List<Data1>> groupD1;
    Map<String, List<Data2>> groupD2;

    public void BuildGameData(String str1) {

        str1 = getData();
        Map<Properties, String> map = getTemplate();
        letouData = JSONObject.parseObject(str1, LetouData.class);
        String funcName = Letou.JS.Parse.getValue();

        String[] jsons = new String[5];

        long st1 = System.currentTimeMillis();

        //region 解析对应数据
        letouData.d.forEach(x -> {
            int index = letouData.d.indexOf(x);
            if (index < 5) {
                Properties type = null;

                switch (index) {
                    case 0:
                        type = Properties.BetOptionContentTemplate;
                        break;
                    case 1:
                        type = Properties.BetOptionContentCompetitorTemplate;
                        break;
                    case 2:
                        type = Properties.BetOptionContentRateTeimlate;
                        break;
                    case 3:
                        type = Properties.BetOptionContentTournamentTemplate;
                        break;
                    case 4:
                        type = Properties.BetOptionContentGroupOptionTemplate;
                        break;

                }
                String str = LZString.decompressFromBase64(letouData.d.get(index));
                jsons[index] = JavaScriptEngine.execu(funcName, map.get(type), str);
            }
        });

        //endregion
        long s2=System.currentTimeMillis();

        data0s=JSONObject.parseArray(jsons[0],Data0.class);
        data1s=JSONObject.parseArray(jsons[1],Data1.class);
        data2s=JSONObject.parseArray(jsons[2],Data2.class);
        data3s=JSONObject.parseArray(jsons[3],Data3.class);
        data4s=JSONObject.parseArray(jsons[4],Data4.class);
        long s3=System.currentTimeMillis();
        System.out.println(s3-s2);

        //该类型数据利用EventCode 标识唯一比赛
        //region创建比赛
        //按EventCode分组
        groupD1 = data1s.parallelStream().collect(Collectors.groupingBy(x -> x.EventCode));
        groupD2 = data2s.parallelStream().collect(Collectors.groupingBy(x -> x.EventCode));


        System.out.println(s2 - st1);

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

    @Override
    public List<RaceStyle> GetRaceStyles() {
        List<RaceStyle> styles = new ArrayList<>();
        for (Map.Entry<String, List<Data1>> entry : groupD1.entrySet()) {
            String key = entry.getKey();
            List<Data1> d1s = entry.getValue();
            List<Data2> d2s = groupD2.get(key);
            for (Data2 d2 : d2s) {
                RaceStyle race = new RaceStyle();

            }

        }
        return styles;
    }

}
