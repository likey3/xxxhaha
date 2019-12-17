package com.xxx.data.letou;

import com.xxx.download.Download;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class letouDownload extends Download {

    public letouDownload() {
        super();
    }
    public letouDownload(Map<String, String> cookies) {
        super(cookies);
    }

    public letouDownload(Map<String, String> cookies, Map<String, String> headers) {
        super(cookies, headers);
    }

    public void setCookies() {
        cookies.put("AW_KENO_SSO_ITEM_VALUE_00004_002", "yE2xLB63jrK4krjg5TZOtiXUndVJGoUac38JsAtEEvV6ralkqTf65VwJyjlhcZTQHIQJ0rxE6Qiuj3oPtW0hh0G51G4d9JYbA1%2fpkkxe2UHOF1JvkrAs%2fguR7JKiJwZuXlcFvwVoMsasO9tGiXduEJIPJqi2ACOIitXzeOjhNMmciFknW7G6AG9ofRQJfOUeu8jqcIY5jSlOtia73Q1zqP2mgglFokapqLzCuDsx%2bDA0oy2Y85iJ8VwHo300WLUFhnUUN61vYbaC2WZ%2b6TfNcxbWQuo0duhDjDU5EbAQ%2fOUJDKDv9YEpQQ585AH9jcWW");
    }


    public  void setCommonHeader()
    {

        //   -H 'Pragma: no-cache' -H 'Cache-Control: no-cache'
        //-H 'Origin: https://msp2.19letou.com'
        // -H 'Sec-Fetch-Site: same-origin' -H 'Sec-Fetch-Mode: cors' -H 'Referer: https://msp2.19letou.com/Sports/Asia/Index.aspx?tpid=002&token=d1ba456540c42f01b862f00b2bd9463672bebd4d95397ecc829a9a2d5d6f4f27f8e615e3dc14694337f7ef2afb16ce06b9456e4f4274a06ff9fb96bafa540a71e9d499184486bd5521a50b3316079e6338aabc4de832f2e6f5b9e4f027078098&languagecode=zh-cn&guest=login&oddstype=00001&sc=' -H 'Accept-Encoding: gzip, deflate, br' -H 'Accept-Language: zh,en;q=0.9,zh-CN;q=0.8' -H 'Cookie: 5fdss=zh-cn; bobos=/cn/; iywek=www.19letou.com; isdye=production; _ga=GA1.2.1769180065.1575596370; ASP.NET_SessionId=cxxfl2m1zmprurv2a1ipk1mf; AW_KENO_LANG=zh-cn; _gid=GA1.2.650246691.1575938008; d3jg9=U2FsdGVkX1+k0KIeaIpqNcmnkTdySQuOO9l3bc9oA0g=; fkei2=1-5def6955-9cbdf479972bd1594ed3e20e; AW_KENO_SSO_ITEM_VALUE_00004_002=yE2xLB63jrK4krjg5TZOtiXUndVJGoUac38JsAtEEvV6ralkqTf65VwJyjlhcZTQHIQJ0rxE6Qiuj3oPtW0hh0G51G4d9JYbA1%2fpkkxe2UHOF1JvkrAs%2fguR7JKiJwZuXlcFvwVoMsasO9tGiXduEJIPJqi2ACOIitXzeOjhNMmciFknW7G6AG9ofRQJfOUeu8jqcIY5jSlOtia73Q1zqP2mgglFokapqLzCuDsx%2bDA0oy2Y85iJ8VwHo300WLUFhnUUN61vYbaC2WZ%2b6TfNcxbWQuo0duhDjDU5EbAQ%2fOUJDKDv9YEpQQ585AH9jcWW' --data-binary '{"LanguageCode":"zh-cn"}' --compressed

        headers.put("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36");
        headers.put("Cache-Control", "no-cache");
        headers.put("Pragma", "no-cache");
        headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Upgrade-Insecure-Requests", "1");
        //headers.put("Origin", "http://cep-pv.saicmotort.com");
        headers.put("Content-Type", "application/json; charset=UTF-8");
        headers.put("Connection", "keep-alive");
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("Accept-Language", "zh-cn,zh;q=0.9,en;q=0.8");
        headers.put("Sec-Fetch-Site", "same-origin");
        headers.put("Sec-Fetch-Mode",  "cors");
        headers.put("Referer", "https://msp2.19letou.com/Sports/Asia/Index.aspx?tpid=002");


    }


}
