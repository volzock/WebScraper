package org.github.volzock.crawler;

import org.github.volzock.models.UrlModel;
import org.github.volzock.requests.HttpGetRequest;

import java.nio.charset.StandardCharsets;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CrawlerThread extends Thread {

    private static final Pattern HREF_PATTERN = Pattern.compile("<a\\s+([^>]*\\s+)?href=\"([^>\"]+)\"\\s*([^>]*)?>");
    private final CrawlerContainer crawlerContainer;

    public CrawlerThread(CrawlerContainer crawlerContainer) {
        super();
        this.crawlerContainer = crawlerContainer;
    }

    @Override
    public void run() {
        try {
            UrlModel urlModel = crawlerContainer.getNoVisitedUrl();
            crawlerContainer.addToVisitedUrl(urlModel);
            System.out.println(urlModel);

            if (urlModel.getDepth() == 1) {
                return;
            }

            try (HttpGetRequest request = new HttpGetRequest(urlModel.getUrl())) {
                try (Scanner scanner = new Scanner(request.send(), StandardCharsets.UTF_8)) {
                    String match = "";
                    while (match != null) {
                        match = scanner.findWithinHorizon(HREF_PATTERN, 0);
                        if (match != null) {
                            String href = scanner.match().group(2);
                            crawlerContainer.addNoVisitedUrl(urlModel, href);
                        }
                    }
                }
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
        } catch (EmptyStackException ignore) {}
    }
}
