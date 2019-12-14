package com.xxx.download;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Download {
    protected Map<String, String> cookies = new HashMap<String, String>();
    protected Map<String, String> headers = new HashMap<String, String>();

    boolean enableProxy = false;

    public Download(Map<String, String> cookies) {
        this(cookies, null);
    }

    public Download() {
    }

    public Download(Map<String, String> cookies, Map<String, String> headers) {
        if (cookies != null) this.cookies.putAll(cookies);
        this.headers = headers == null ? this.headers : headers;
    }

    public Document download(String url, Connection.Method method, Map<String, String> data) {
        return download(url, method, data, null);
    }

    public Document download(String url, Connection.Method method, Map<String, String> data, String payload) {
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, InetSocketAddress.createUnresolved("127.0.0.1", 12345));
        Connection connection = Jsoup.connect(url)
                .headers(headers)
                .ignoreContentType(true)
                .timeout(20 * 1000)
                .method(method);

        if (enableProxy) {
            connection.proxy(proxy);
        }

        if (cookies != null) connection.cookies(cookies);
        if (data != null) connection.data(data);
        if (payload != null && !payload.isEmpty()) connection.requestBody(payload);

        Connection.Response response = null;
        try {
            response = connection.execute();

            Map<String, String> newCookies = response.cookies();
            updateCookies(newCookies);

            return response.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public abstract void setCookies();

    public Document download(String url) {
        return download(url, Connection.Method.GET, null);
    }

    protected void updateCookies(Map<String, String> newCookies) {
        newCookies.forEach((k, v) -> {
            if (cookies.containsKey(k)) cookies.replace(k, v);
            else cookies.put(k, v);
        });
    }

    public void replaceCookie(String key, String value) {
        if (cookies.containsKey(key)) cookies.replace(key, value);
        else cookies.put(key, value);
    }

    public void setHeaders(Map<String, String> stringStringMap) {
        this.headers = stringStringMap;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public abstract void setCommonHeader();

}
