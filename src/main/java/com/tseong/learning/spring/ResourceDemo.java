package com.tseong.learning.spring;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ResourceDemo {

    public static void main(String[] args) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource res = resourceLoader.getResource("https://www.baidu.com");
        System.out.println(res instanceof UrlResource); // true

        BufferedReader bf = new BufferedReader(new InputStreamReader(res.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String temp = null;
        while ((temp = bf.readLine()) != null) {
            sb.append(temp);
        }
        System.out.println(sb.toString());

        System.out.println("---");

    }
}
