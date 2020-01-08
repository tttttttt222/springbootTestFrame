package com.examples.demo.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author:
 * @Date: 2019/6/21
 */
@Slf4j
public class HttpTools {
    private final static OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).
            writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();

    public static String sendPost(String url, Map<String, String> headers, Map<String, String> params) {
        headers = (headers == null ? new HashMap<>() : headers);
        params = (params == null ? new HashMap<>() : params);

        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet())
            formBodyBuilder.add(entry.getKey(), entry.getValue());

        Request.Builder request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .method("POST", formBodyBuilder.build());

        for (Map.Entry<String, String> header : headers.entrySet()) {
            request.header(header.getKey(), header.getValue());
        }

        try {
            Response response = ohHttpCall(request.build());
            return response.body().string();
        } catch (Exception e) {
            log.error("请求URL={} 出现异常", url, e);
            return null;
        }
    }

    public static String sendPost(String url, Map<String, String> params) {
        params = (params == null ? new HashMap<>() : params);

        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet())
            formBodyBuilder.add(entry.getKey(), entry.getValue());

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .method("POST", formBodyBuilder.build())
                .build();

        try {
            Response response = ohHttpCall(request);
            return response.body().string();
        } catch (Exception e) {
            log.error("请求URL={} 出现异常", url, e);
            return null;
        }
    }

    public static String sendGet(String url) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = ohHttpCall(request);
            return response.body().string();
        } catch (Exception e) {
            log.error("请求URL={} 出现异常", url, e);
            return null;
        }
    }

    private static Response ohHttpCall(Request request) throws Exception {
        Response response = client.newCall(request).execute();

        if (response == null || !response.isSuccessful()) {
            log.error("请求URL={} 失败 {}", request.url(), response);
            return null;
        }
        return response;
    }
}
