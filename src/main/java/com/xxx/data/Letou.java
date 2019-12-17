package com.xxx.data;

import bin.CustomResources;
import com.alibaba.fastjson.JSONObject;
import com.xxx.data.letou.LetouData;
import com.xxx.data.letou.MenuData;
import com.xxx.data.letou.SubMenuData;
import com.xxx.download.Download;
import com.xxx.entity.GambleMarket;
import com.xxx.utils.JavaScriptEngine;
import com.xxx.utils.LZString;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class Letou {
    public static final String JSON_LINE_SEPARATOR = "§";
    public static final String COLUMN_SEPARATOR = "`";

    private static Download download;

    //运动项目的分类请求地址
    private String menuDataUrl = "https://msp2.19letou.com/Sports/Asia/Index.aspx/LeftMenu";
    //所有的运动项目
    private List<MenuData> menus;

    public void setMenus() {
        menus = new ArrayList<>();
        Document document = download.download(menuDataUrl, Connection.Method.POST, null, "{LanguageCode: \"zh-cn\"}");

        String testdata = "{\"d\":[\"ƧҀǆ᧖ᣠᜨΠ㙨⁆Ǹᢡ䳠᳔る傰ḗ幐˒凡Ѷ༬\u2458\u2435䬈Ż⠾毰q䈬⨰傱⅑Ȱˡ࠱』\u0378㈸ƀ¼Ⴐ䌡㓣Ȗ䚐兇⑦̴ⵔ〣ㅰਮ⠩皈\u0B51㞦䫛ᴰ疑悍夡ᙍ烺犸䃰洔䁄燜\u0B31⊆ܳʪ呁\u0091罠㱞彵⩋䅓掩凂⚠╙⁒乾ҍණバ℡世䁆ⷞ唩⬲⌡ሢả䦠ᅋ\\u2028㋾Ң㖔攄ዠ沠ᓙ‷\u2E70ۤ埉㩢牝⊀楠㒱劸Ʋ檡⪣㇈Ⴀڕ 峢᱆ⴣ淤℡㟮䁋粃䒸栾߃\u0382Ŝ亠⤋⁇䍐ޖ畅ქ⁔९⌼倧䋃䩌ॠ֦抐䇄〰 \",\"Ƨƀǂ\u2BC3ఢ炚⇩ᪧ䄈\u2066Ф恛᳔̰ほ➹ᨳࡔ\u008Cጱ⺠乨圡䂆ᶇ囒棊狧坎怵侴͚ᄎࢬበ㌢炁˲㚬櫮嚌\u2BEA勁瀎ᗜ䍎⼉勋∠㧶ᢦ塖໑㹹穴嫛⚏䥥䫌ᾝཏ姰昱Է䠱ᶡ梄屴ᮢ姭!烠0Ir敠\u175A䀬䠡㑲ᐢ⥐\u312Eत穀ᰢ栦ᱴ\u008D屁\u1CC9眬恗ðෟྤˋᔠ㏪ຸ樿ᾢ\u0FF4ḿ朷⭀䈂碬恙\u1AF0ӫ㊤ơᗻၽ瓤䐡⋆ጼª両ຍ\u0083¨\u2D26ᑻ咛㾺呲タࠈ䀠㧀ɱ✡素㠵âϒ㼂ဠ\"]}";
        LetouData data = JSONObject.parseObject(testdata, LetouData.class);
        //     LetouData data = JSONObject.parseObject(document.body().html(), LetouData.class);

        //所有运动类型的数据
        String json0 = MenuData.buildMenuDataJson(data.d.get(0));
        List<String> array = JSONObject.parseArray(json0, String.class);
        array.forEach(x -> menus.add(JSONObject.parseObject(x, MenuData.class)));

        //对应运动类型下的子分类
        List<SubMenuData> subMenus = new ArrayList<>();
        String json1 = SubMenuData.buildSubMenuDataJson(data.d.get(1));
        List<String> array1 = JSONObject.parseArray(json1, String.class);
        array1.forEach(x -> subMenus.add(JSONObject.parseObject(x, SubMenuData.class)));

        //将各个子分类设置到相应的主分类下
        menus.parallelStream().forEach(x->
        {
            subMenus.parallelStream().forEach(y->
            {
                if (y.SportCode.equals(x.SportCode))
                {
                    x.subMenuData.add(y);
                }
            });
        });

    }

    public void setDownload(Download ldown) {
        download = ldown;
    }

    public static void getGames() {
        String json = "{\"d\":[\"AwRgBgTMbSCcBmCCDsBWawujCALAGwRiShwD0IElEABKAFxpoNZgAqATgK4CmuYAGIBDADYBnflQB02NigJCxkkiS59cAcuDEI4WImTpic8PiIko8GjXogmLNuqlKJUiLLlgFrlaudawAhgCPq4hggEKJjYZoTEpNZUtsB4rMDpHDwuIm64HnLQ6L78/tmBeCEm4UiRKCaxuPGWZDZU9GlYmQHguSoyhd6KfaVq5SDaaCHBBrX1DVhxFokUye2p6d3jJfme2N5TI6pZGhPAigiVs0YQV43mCVar1OudGU7bRwNexUdlp9oUCEptdUD5TE1lk82nQNl0Pqcdt99ihKn8xgDgAAOEKKUEoFA4iEPFpJF6wt5bRFfAo/YbKUYnKTaOC4ImkJCgPAzRoILH80nPFL2BDvaA9HYEPZsSJM449TQgTDVKCi0V4EGNCBYghUQVtEC0EBYhiiqk5BkhaWwYLouXARXgCAqoJwVHAVnEjD6tawiCbBEWvJSwbeu0KvW3GBsvBwHRxmBan3k+gIBga80CGnWsBoNGW/7MkDBCAgmNxiBwBY4R6tX2p9OOcWfS3Ith5naFrT4SB48vxu6LSDJlJpjOBrOt2n7NC2gsYotTCBA2BY2PxvFJlYwhvj5vUqc5jvh8aKxTa6PG9eVolb6H1oKNzO9Q+h/N5LsTEBAyuXtdx0UYiHWsyVHJ8JxfPI22gWdOwXbsiTCeANWYC8IQ1EAlRHTCjRNeF9yDFQEBzb84LlL9PTLZDmFnICcAwrDtzWQ0oADAjJzyYjBlIk8ASgGA+2ovME3QtBMOgJiXhY+x8PInYuK8UiAk/TQ9BgFdwhQvNbyHBiJPvKTaD0Ni5KOBT9h4+dyNUkx2SEvABVE8TsJY/1ZIlMySKBXjmWQXAkLgLTsUTXSxMYgzKBYtN3JbTivLIhUoz1AwtKwpzwrrQzkBMjzLXMthLI/eCHVLXAZk0mjvXS/TMsiozorFUy8vinytAgC52SsZAsRATch3weBgkk2xetNRrcri7joFaiZlxgcqoAIYAFCxT0tTm4acPgHLYqIlqrMSokrlIJaFDgTVgI2iKtrgHaD0mxTvIO09f1FaNVX5TD9C1S5WU22ExybJqHosp6iust6PT/Hq0DgXqQpwSJgs21y7sI/gPCVUGEvKB1QlwTA2RhuGdMRpacRRoy3PG3aMekLGCrBvxis0JBcEEz7YeS3lyew2EMjNCCdkxx6cYBBBglI1diZ7CEkYp67+bG59hfp0WZtZypZdINAetAb6hz5XUQKFHDspi+69u4pnGQVBAphAMsoF1pViwR8Ajb1SnzZpy3+Hy2AbflU9ZXgd7gBd0ALsRnUvcVuxlaFzzrbF5lUBCDTnbgKs5ohAhYeYPn6BQRP2Mgq31ee8WcT86BndCPAcAhFA4EUf76GplWjhDLxddT3AgA===\",\"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\",1,38]}";

        LetouData letouData = JSONObject.parseObject(json, LetouData.class);

        System.out.println(LZString.decompressFromBase64(letouData.d.get(0)));
        System.out.println();

        System.out.println(LZString.decompressFromBase64(letouData.d.get(1)));
        System.out.println();

        System.out.println(LZString.decompressFromBase64(letouData.d.get(2)));
        System.out.println();

        System.out.println(LZString.decompressFromBase64(letouData.d.get(3)));
        System.out.println();

        System.out.println(LZString.decompressFromBase64(letouData.d.get(4)));

    }

    public static void getBetData() {
        download.setCookies();

        //       String json = "{\"d\":[\"ᆠට㎪\u0E5F棟㕡䠦֧ᐽ篰怷⁴Ʀ櫤䂆x㠤愰㓂倹技橭\u202E䕡ॹ䇎᪀䑀͐Řϳы愠ड℅E䪝䰠㠡㦸ˑ❒㖉混咄䲰\u0D81怡榹瘉\u07BAಐ搠ઠᎠ⮠⧁䂒燨ᠨ⡓㐆焨ࢯᐯⵔࠐ昣ⰽÕ潆˛ឥⱊ劵䠸塃䫼ᤕ⚤\u177F⽄㒅Ϫⶍ٠ \",\"ঠᠡ傎交㖬ۻ䶬ɑ乧䚘\u0E70⭓ӎ䘦ɱ䀡*\u20F4ᕠժ䃿☢⦯䙖ᘇ$䎀㡈ᮠ悀牭ₘ\u0EA9Ժ窬Ƹⱇ墄ⓠˬ揰ᵟ⁰ᕹ‾䠦㳆╮䆢㊐\u008C璀ቆの恨ࢯᐯⵔࠐ昦塚˷㵴ਠ㪹疬㙚斲⍻ヅò䕠ം〺䧈\u0094⨢ዓ÷൯࿈亿括ͣ\u0093Ʌ䀪だ㎾倠⏈ ☡种䐠媂‴\u0E70Ռ吢珋印\u20C0\u0C50ঢ匸\u0EF9療欈樻䵦ᙔಧ″䀮懢䨰䦦䶬晶䊰⯇w戲ⷴ嘰歨ႌ✖ࠠ\",\"͠ళ8∨愧ᚲ榡ʋ䕸ᨢ夈ᩱ⠬煐㕆倧⋀䓌惷ഠӺᄿࢢህ϶䴧碆ې㨫⼓嫜౭甥控⒊္么\u202Bڈ渼䛉ǩ᪠熒㪐ඒ刧䄊㖨ॾؠᶸ⓸ᠠภ㩅纣⣢╦䢋ᓞ吷䪑㼿ࡄ坤ǅ噱ࠊಽ䇁ᠸGǨሽᜭ✃\u242C紂㓸ѝ䥻⨩ጧ䫇䰝䚅લ榶Ớ斫\u0E8E䏵㉡䔄樨仔䶫䯤㐶䩺㩧ಃཱ瑜壢㓃垰撗撓Ꮨ礤沪㭰ⷓ#㊎\u0EEC⥓䙣扨Ẁ笩Ū⤠\",\"ᆠళ9旦\u0380֤䃽ᴤ㖬\u038D怢Iہ⨠哈ด恅Ҁ͓䡠✸猀\u0EE6②努䂇ণ⥕Zᔤ▆ۘ梤ࠪ性‡把దႈ⭮帢儥䀧ᖦ䓻ࢥ̮Ĳ㊴㔉憯Ĩࢯᐯⵔࠐ昦塚˷㴥䢮ȱ㵂禵巇ც灵㤨\u0874ؾہȢ⯃\u0080╌ḱᢳҎ∶凢ᢅ泼繬U\u0C64∈⹉ʴ㳠≴晨㌮ㅥᝁ⠩㧉၂ᆀ⣘䉡奕ẹ䢅\u0088氽ຸzᅧ䚌彑瘡ৠ䉔疃㽩係病⫁甍ഗ搔䥭䩁晦吡垿䬐㵡ῠ∰⣱癢佛挘୳䅢烑挵ᴲሥ暰敒㗏䅖䳜⧂ᘆⰸ⣚攤渁漲ᔧ絴☱椵̚珀洩榎澿vŠߝ\u176D㧈獇潶割კ⓯揬⊗≫᱁犆✤唁✢ᨢ⏅⚒晇⍔Ⲅ刢犅㶰┷牮劐兢䍥䁵᮱刾ഹ巩層㒍☑ᩲ⑅凋⣜涀帨ต⯃Ƈ\u0D45⑦䀹£⫛憀汅淃吿抹㞏\u244C筎ઋ\u1F47娾爹↺晈㋒朢䣔㔃⨴\u09D3ཌ㇇䛡组Ḍ⬝䦲ᦻ䶓囫㬗ᄵः㊰禣㜓嫀兢⫦乢䄠渣Ɒ݄模灚嵲わ\u2454]䀦瀢䰠✰ژǥ敐〸ӦὯᐋ䃢\u2D75ᤡ瓷Oඐౘ瓧ᯨൟ伷㫦⺱Р圠ހl㮨ν\u07B9炝柉玐䡘瓥悁痉࿀⚝湈瓢Ꭷ疼ூ㈜拕ɠ᮸\u0BDD䟌悜ᒙ௬≕ұ濈ϝ憸Â俐琤䈻監ιऔҚℐ䂈\u0DFCၱ熂敕\u0CC9扲朱昢\u08D1⊓ං梐瑄屴痣徨券\\u2029\u0DFC撖ுᣁ䤰怲ԡ境\u0E62歖ஃ䀿ᝲ㡷矣婽ठ䉸ല䚘ѓᙪ摵ٲ\u0D04㺒䅳䀴\u2060Ṅ⼡晄琡樚墰ˋᐠ㵀ƾ⣙絤掰تˎ恘Ҡ䓅捌樺秠ᤧ愵䂲䏈⠺ᝥᙃ硅ᓝᘑ咫ᗠ⢳㦎䁄˅拖䞪狛懨绚垰產暑啺㓰䣾ᛤ䀡檈䷩Ȯ⁄⺵帔戇◈Ẏఖ廳፳\u242B㶰咠㰛઼㲆礋\u1AC2⎀家䟊寶禡牤仹㫨㟻媈墨ё∸剎凸ᇎ䡦䥁傢ࢤᐧ㤘Ᏺ嫝ᄾ嫂ặ岖䐡⏄\u0E5D䠞浢䕑縋䛪ᬶ㊆瑛ࠁ畄䙾䩖寈ᐡ窢楔娑筓䟤⁞Üڢۨݞު窡燨՞ᚰȤ䄠㎾倠ऊ ʀІ孬䰰š攠ᓒ倢珋嫺ප䊃⣀屬憥\u2428ݱ摙¬㕤ໂũ㫈⨉㈣昅㫎㖽㦵疦༦⌡偈溓㢤籦掌႕ບㅚ䚱\u1AF8䦠嬗㷎痚á垜塴۲峣⑫ᭂъ匁椕݄㚈爀\u1718皶Ꮿ㲃㷰\u0DF6㝾忶摡娀\u2060吁䛢烣呍⬮⇰¹⚕㒼冢氣䴂澐㮜䍜㭊䶛␤ވ䮆䇺ɻ㶤懰䁞ց爌䕚\u2E4A等䇺侥䇻ᘤચ\u0605簉㚛氻垷য়䊞竊߳俞䠄湝\u0D49窈┨;ბឪ礜ڧᐪ憞焎ᙛ漣搳糐ᰘ殡凲ฑὙ䋡圩䢍枝䪻ㅀ⪃₀䊏㰖潤\u0BFEሊ䅣ݩ⦡పဳ爳Ժ㝖木Ϟ䎃硼䳢懸ၚḚ⡰筰戗֛瞤⦨Ҳ\u1C39ⱎ▏優筊刨㒨潸ౘ䄴䇵梜ᛠȪণ牞䙨㗅®༡ᩢ®䂂籞䈀⊦㑪ވ棤䚉䊀⦪煗栽硙扌㥥ᡊ䜤⅀棊࡞㈋Wூⴲዙ媉䷇܈榇ᐪ᠑痋㇛求⅗綀ᘰ侥ଌ⻢吸甑\u0530縩ᯑ䥋⡚ड息ୀ䢭ӛ烦㯧\u2428࿔ⵉぇ䚩暹姣㌢ㄫ厉憊Ⅲી矨⅊ㇹ\u1CBEࡘ絭6㔲暺榫ᥱ嚭岚㾻亯栬䷖ತܨ熽⦺◃畍㣖墹椱匠\u05EDヮ垁ẵ㉉Αᝐ㉠\u0C70䙵㐌䚡哃䳩㗎牀⨄彪犞债䳏孨\u1AEAկʙ液᧐戓ፖ剒ⴧ᷃▝̧涔⾉䱼搦桌睘䲴㴥䦦礌磫\u0CA9䬙榳禢〓έ©⬴ㅭ୰ⱇ̀㛒῍䕐崵擜䨮㧩⦒ᘢ⩏勑\u0DFA῎䓀宒条桅㨆幢\u0091㱌勚䨙☧\u197F∐拌Ⱌ喇妌垪睌ኅⱙ䅻ℨ\u0E80⋖@དྷ⁍哺狯兖⸉摄墖喔\u2FE7㌙Ӵ֊坂呠欚⾕㈬\u0DF6姳હ毶⿐䍣埪傢 \",\"1\",\"ᆣ䨳й䄦怶ဠ\"]}";
        String json = "{\"d\":[1,\"ᆠി⟱ဩ݈ጪᮮ⡠撀ᮎ䌿ᓀ\u0B29˫䨩䮆吠㡤\u05CF琦┅ᠠ求ɼ䘬ƒ䁓\u0E61少䛊栴\u0E60㵀桙⁉㋀\u175A劬ㅠ佇⁌盀ᠲ偞╖Kۀ㛌〰⡊Ɛᲅ䀢ᵪ,\u1879䮲搈晌堮漹俀Ꭶ倣ᵰဍⰨ堽尷ᢴ咺ঀ岹䱐\u0096ᕑףԥ⁰⥑ᶼ\u0AB4⚀㻈\u2BF9株䩔|沠\"]}";

        LetouData letouData = JSONObject.parseObject(json, LetouData.class);

   /*     System.out.println(LZString.decompressFromUTF16(letouData.d.get(0)));
        System.out.println();
*/
        System.out.println(LZString.decompressFromUTF16(letouData.d.get(1)));
        System.out.println();

  /*      System.out.println(LZString.decompressFromUTF16(letouData.d.get(2)));
        System.out.println();

        System.out.println(LZString.decompressFromUTF16(letouData.d.get(3)));
        System.out.println();

        System.out.println(LZString.decompressFromUTF16(letouData.d.get(5)));
  */
    }

    public static void main(String[] args) {
        //  getGames();

        //     getMenu().forEach(x -> System.out.println(x));


        //     LetouTemplate kk = new LetouTemplate();
        //    kk.Parse();

        /*String jsonData = "{\"d\":[1,\"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\",\"ƧҀǄ\u0560个灉䩈᳔怩䆍撫ͤᡄ‹䄾攬䪠\u0E71ဢ擁㔤⟎瞔‵䫿ቁ㩰Ġ氰äĬ\u008Cƽ涋$猰䀮暿ᓃ䖈\u0B7E⬛ᵡ䩥⧸峌糴ℳ᪖塊䥚ᄦ⋬\u1B4C䷳⣘ቬš灒惰娜竮⃐㚸fỢᒄ䆢⑲\u1C89手ʕ噃䐾ხ᧐牽\u18F8か㚁ᝄ։䑇圶ᡉᡐ\u0AD8捖\u1AD0⮄೦㖤筝䄂䉬㒎啅楓䀷я㕑ቋ碶♖䷑咳䝏挗ঘ⎥⇰យᗎ㠵㦀娮⮧ኄ岑䒆ឬ䙜㢿㷨妠\u20F1㣨洹ಳ嵡儸\u08D3გ梛⸣⧨嶲᮱ᄨ㉏ߓ塨\u087D↨䂠 \"]}";
        String jsonData2 = "{\"d\":[\"ƧҀǆ᧖ᣠᜨᶠ☢塁ሬ掇䁙㒀䢁ၛ矜〣㉾䀶ⴰ停ᢲグ⭋倥㛀㷏‡⍦䡰棣ᢣ₈\u08CF烂䂽ᠴ㺀¼Ⴐ椡㓣Ȗ䚐ᦠ\u0093散ภ捰Ǒ⠪݈Ԗ㐫ᣐ哌庵\u1878晨㦡㸞䃻殘\u242Aဪぅ⡛ჰӑ㞠だᥥ⁸ࡇ′㣾ʦ䑮᪡⤴Ⴀ㤟⁜ί⿄ባ䬁䘜傰䁅᳀\u196E⪴㎫⯑䆩í筠ፍ坨⤤漦ᆣ夡ཡ糠\u08B5値ᦏӧ㌨⇫煮〦\u202A\u2E70\u05F7ᐣㅊ痃ᄞጺᠺ်⒏Ӂ琬䉠狹䀦㫀ῼ慣ᨫ࠽℅×杠ᘜ\u2FE8┕䱓ȹoᏀԽ㐢㱓[宩ɣ環庳侒䇂ᚥ⍤唠ᘻ৽Ɖ \",\"Ƥጤ瀡克冬Ɛ㵁撺䏠畀⍄ڄ䄅Ⴀ⬬ѷ⺡ǹ㰉䍘䫁咁喢绲ᤷ塔缰晳;ᖨᔮ䜉墘䊬䭀˥\u0B54ᬢ䙥凒楠ڗ‡挺ݤ䪟༙玄Դ佼ࣣᾸས穰㵚䑞䶭琉▴Ⳓ悛\u07BAᅾ枮ģᦳ\u19AD㈑䡲Ẳ⁃ڠĕ ࠠᒠ⦅‷ᵠ٨Ô⪴ŉႚᄊᲀ〼ƈ̼⨠㛼夕悄殦ျ䂈ۿ䑂ŵ䪠ᨅ㞂䯩媸݆娡ᦍ傠ぅឝ卌ᡒᾠ䴵䬋盳౭摑㫒Ѥ㫬䈡ຍ\u0083è⣈ᡪ䊟㯽噡⦿〝䡠Y倡ᑷ䈞䀼դ㒣೯⢨ \"]}";
        LetouData letouData = JSONObject.parseObject(jsonData2, LetouData.class);

        String item1 = letouData.d.get(0);
        String item2 = letouData.d.get(1);

        System.out.println(toLineText(LZString.decompressFromUTF16(item1)));
        System.out.println(toLineText(LZString.decompressFromUTF16(item2)));
    */
    }


    public static String toLineText(String text) {
        return String.join(System.lineSeparator(), text.split(Letou.JSON_LINE_SEPARATOR));
    }

    public Letou() {
        setJsFile();
    }

    private void setJsFile() {
        JavaScriptEngine.setScriptFile(CustomResources.getLetouScript());
    }

    /**
     * Letou 脚本所具有的js函数名
     */
    public enum JS {
        Parse("Parse");
        private final String funcName;

        JS(String val) {
            funcName = val;
        }

        public String getValue() {
            return funcName;
        }

    }

    public static GambleMarket creatGambleMarket()
    {
        GambleMarket market=new GambleMarket();



        return  market;
    }
}
