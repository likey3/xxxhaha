package com.xxx.log;

import org.apache.logging.log4j.Level;

public class Log4jAssist {
    /**
     * 只输出与盘口处理相关的数据,log文件为gamble.log 此日志一旦有记录
     * 说明需要解决错误，不然会影响押注
     */
    public static Level GambleCheck = Level.getLevel("GAMBLE_CHECK");
}
