package com.xxx.data.letou;

import bin.CustomResources;
import com.xxx.data.Letou;
import com.xxx.utils.JavaScriptEngine;
import com.xxx.utils.LZString;

public class SubMenuData {

    public String SportCode;
    public String ItemName;
    public String ItemID;
    public String ItemCount;
    public String DC;

    static final String LeftMenuSubTemplates = "SportCode,ItemName,ItemID,ItemCount,DC";
    public static String buildSubMenuDataJson(String data) {
        data = LZString.decompressFromUTF16(data);
        String json = (String) JavaScriptEngine.execu(Letou.JS.Parse.getValue(), LeftMenuSubTemplates, data);
        return json;

    }
}
