package org.github.volzock;

import org.github.volzock.crawler.Crawler;
import org.github.volzock.utils.Validators;

public class Application {
    public static void main(String[] args) {
        if (args.length != 3 || !Validators.isNumber(args[1]) || !Validators.isHttpUrl(args[0]) || !Validators.isNumber(args[2])) {
            throw new IllegalArgumentException("Input dont looks like: <url:string(only http)> <depth:int>");
        }
        Crawler crawler = new Crawler(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        crawler.start();
        System.out.println(crawler);
    }
}




