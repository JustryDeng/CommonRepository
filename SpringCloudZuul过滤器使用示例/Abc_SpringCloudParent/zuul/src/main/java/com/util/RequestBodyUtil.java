package com.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * 获取请求体 --- 工具类
 *
 * @author JustryDeng
 * @date 2019/3/19 20:11
 */
public class RequestBodyUtil extends HttpServletRequestWrapper {


    private final String body;

    public String getBody() {
        return body;
    }

    public RequestBodyUtil(HttpServletRequest request) throws IOException {
        super(request);
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        body = sb.toString();
    }

}