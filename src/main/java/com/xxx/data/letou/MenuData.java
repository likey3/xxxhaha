package com.xxx.data.letou;


import com.xxx.data.Letou;
import com.xxx.utils.JavaScriptEngine;
import com.xxx.utils.LZString;

import java.util.ArrayList;
import java.util.List;


public class MenuData {
    public String SportCode;
    public String OrderBy;
    public String MultiLangName;
    public  String EventCount;
    public  String DC;
    public String OutrightEventCount;
    public String TimeLineScale;

    //子菜单所具有的数据
public List<SubMenuData>  subMenuData=new ArrayList<>();

private  static final String LeftMenuTemplates = "SportCode,OrderBy,MultiLangName,EventCount,DC,OutrightEventCount,TimeLineScale";

    public MenuData() {

    }

    public MenuData(String menuCode) {

    }



    /**
     * 将远端得到的原始数据根据模板解析为MenuData 的json字串
     * @param data
     * @return
     */

    public static String buildMenuDataJson(String data)
    {
        data= LZString.decompressFromUTF16(data);
       String json=(String)JavaScriptEngine.execu(Letou.JS.Parse.getValue(),LeftMenuTemplates,data);
    return json;

    }
}
