package org.github.volzock;

import org.github.volzock.requests.HttpGetRequest;
import org.github.volzock.utils.Validators;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        if (args.length != 2 || !Validators.isNumber(args[1]) || !Validators.isHttpUrl(args[0])) {
            throw new IllegalArgumentException("Input dont looks like: <url:string(only http)> <depth:int>");
        }
        Crawler crawler = new Crawler(args[0], Integer.parseInt(args[1]));
        crawler.start();
        System.out.println(crawler);
    }
}




