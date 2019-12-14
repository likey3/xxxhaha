package com.xxx.data.letou.template;

import bin.CustomResources;
import com.alibaba.fastjson.JSONObject;
import com.xxx.data.Letou;
import com.xxx.data.letou.LetouData;
import com.xxx.utils.JavaScriptEngine;
import com.xxx.utils.LZString;
import com.xxx.utils.dataTemplateParse.ITempplateParse;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.util.HashMap;

import java.util.Map;

public class LetouTemplate implements ITempplateParse {

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
    //js 运行引擎
    static ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");

    @Override
    public String Parse() {

        String json, json1, json2, json3, json4;


        Map<Properties, String> map = getTemplate();


        letouData = JSONObject.parseObject(getData(), LetouData.class);

        String funcName = Letou.JS.Parse.getValue();
        json = (String) JavaScriptEngine.execu(funcName, map.get(Properties.BetOptionContentTemplate), LZString.decompressFromBase64(letouData.d.get(0)));
        json1 = (String) JavaScriptEngine.execu(funcName, map.get(Properties.BetOptionContentCompetitorTemplate), LZString.decompressFromBase64(letouData.d.get(1)));
        json2 = (String) JavaScriptEngine.execu(funcName, map.get(Properties.BetOptionContentRateTeimlate), LZString.decompressFromBase64(letouData.d.get(2)));
        json3 = (String) JavaScriptEngine.execu(funcName, map.get(Properties.BetOptionContentTournamentTemplate), LZString.decompressFromBase64(letouData.d.get(3)));
        json4 = (String) JavaScriptEngine.execu(funcName, map.get(Properties.BetOptionContentGroupOptionTemplate), LZString.decompressFromBase64(letouData.d.get(4)));


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
