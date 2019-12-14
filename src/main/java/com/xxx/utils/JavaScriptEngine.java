package com.xxx.utils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavaScriptEngine {
    private static JavaScriptEngine instance =null;

    private static ScriptEngine engine;

    //单例模式，线程不安全
    public static JavaScriptEngine getInstance(){
        if (instance==null)
            instance =new JavaScriptEngine();
        return  instance;
    }

    private JavaScriptEngine(){
            engine=new ScriptEngineManager().getEngineByName("javascript");
    }

    /**
     *
     * @param funcName 要执行的函数名称
     * @param paras 携带的参数
     * @return 返回json格式字符串
     */
    public static String execu(String funcName,Object ...paras)
    {
        String str="";
        Invocable invocable = (Invocable) engine;
        try {
            str=(String) invocable.invokeFunction(funcName,paras);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return  str;
    }

    /**
     * 设置要执行的js脚本
     * @param str 脚本源码
     */
    public static void setScriptFile(String str){

        try {
            engine.eval(str);
        } catch (ScriptException e) {
            e.printStackTrace();
        }

    }

}
