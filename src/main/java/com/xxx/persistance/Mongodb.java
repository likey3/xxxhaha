package com.xxx.persistance;

import com.alibaba.fastjson.JSON;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.xxx.entity.GamblingEntity;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Mongodb {

    String ip;
    int port;

    //mongodb 实例
    private static MongoClient mongoClient;
    MongoDatabase database;

    //mongodb 集合，相当于sql表
    MongoCollection<Document> gambleDoc;

    private static Mongodb ourInstance = new Mongodb();

    public static Mongodb getInstance() {
        return ourInstance;
    }

    private Mongodb() {

        //初始化数据库的相关设置
        ip = "localhost";
        int port = 27017;
        mongoClient = new MongoClient(ip, port);//如果mongo为安全验证模式，需要提供用户名和密码

        MongoClientOptions.Builder options = new MongoClientOptions.Builder();
        options.cursorFinalizerEnabled(true);
        //连接池设置为300个连接，默认为100
        options.connectionsPerHost(300);
        //连接超时
        options.connectTimeout(30000);
        //等待时间
        options.maxWaitTime(5000);
        //套接字超时,0--无限制
        options.socketTimeout(0);
        //线程队列数，如果连接满了队列会抛出 out of semaphores to get db；
        options.threadsAllowedToBlockForConnectionMultiplier(5000);

        options.build();

        database = mongoClient.getDatabase("gambles");
        gambleDoc = database.getCollection("gamble");

        //查找集合中的所有文档
        FindIterable findIterable = gambleDoc.find();
        MongoCursor<Document> cursor = findIterable.iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            GamblingEntity er = JSON.parseObject(doc.toJson(), GamblingEntity.class);
            Object obj = doc.get("_id");
            String kjl = doc.toString();
            ObjectId iud = doc.getObjectId(doc);
            System.out.println(cursor.next());
        }

        //准备数据
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("fadf", "你打几分；0");
//        map.put("fdaffad", 341341);
//        map.put("time", new Date());

        //     Document document = new Document(map);
        //      gambleDoc.insertOne(document);


    }


    public MongoCollection<Document> getGambleDoc() {
        return gambleDoc;
    }


}
