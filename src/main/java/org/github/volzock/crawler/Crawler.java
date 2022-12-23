package org.github.volzock.crawler;

import org.github.volzock.models.UrlModel;

import java.util.*;
import java.util.stream.Collectors;

public class Crawler {
    private final Integer THREADS_QUANTITY;
    private final CrawlerContainer crawlerContainer;

    public Crawler(String baseUrl, int depth, int threadsQuantity) {
        this.crawlerContainer = new CrawlerContainer(new UrlModel(baseUrl, depth));
        this.THREADS_QUANTITY = threadsQuantity;
    }

    public void start() {
        List<Thread> threads = new ArrayList<>();
        Thread current;

        do {
            for (int i = 0; i < crawlerContainer.sizeNoVisitedUrls() && i < (THREADS_QUANTITY - threads.size()); ++i) {
                current = new CrawlerThread(crawlerContainer);
                current.start();
                threads.add(current);
            }
            threads = threads.stream().filter(Thread::isAlive).collect(Collectors.toList());
        } while (threads.size() > 0 || crawlerContainer.sizeNoVisitedUrls() > 0);

    }

    @Override
    public String toString() {
        return crawlerContainer.getVisitedUrls()
                .stream()
                .map(Objects::toString)
                .collect(Collectors.joining("\n"));
    }
}
