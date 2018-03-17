package com.tseong.learning.others.socket.httpServer;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HttpServerDemo {

    public static void main(String[] args) {

        try {

            InetSocketAddress inetSock = new InetSocketAddress(9998);
            HttpServer httpServer = HttpServer.create(inetSock, 19);
            httpServer.createContext("/", new MyHttpHandler());
            httpServer.createContext("/test", new MyHttpHandler());
            httpServer.setExecutor(null);

            httpServer.start();
            System.out.println("httpserver test started");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static class MyHttpHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

            // 针对请求的处理部分
            // 返回请求响应时，遵循HTTP协议

            String responseString = "<font color='#ff0000'>Hello! This a HttpServer!</font>";
            Headers responseHeader = new Headers();

            String pattern = "EEE, dd MMM yyyy HH:mm:ss zzz";
            DateFormat df = new SimpleDateFormat(pattern, Locale.US);
            responseHeader.set("Date", df.format(new Date()));

            responseHeader.set("Content-length", Long.toString(responseString.length()));
            httpExchange.sendResponseHeaders(200, responseString.length());

            OutputStream bos = httpExchange.getResponseBody();
            bos.write(responseString.getBytes());
            bos.close();
        }
    }
}


// browse http://127.0.0.1:9998/

