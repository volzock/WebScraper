package org.github.volzock.crawler;

import org.github.volzock.UrlBuilder;
import org.github.volzock.models.UrlModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class CrawlerContainer {
    private final Stack<UrlModel> noVisitedUrls;
    private final List<UrlModel> visitedUrls;
    private final HashSet<String> visitedUrlSet;

    public CrawlerContainer(UrlModel urlModel) {
        noVisitedUrls = new Stack<>(){{push(urlModel);}};
        visitedUrls = new ArrayList<>();
        visitedUrlSet = new HashSet<>();
    }

    public Integer sizeNoVisitedUrls() {
        synchronized (noVisitedUrls) {
            return noVisitedUrls.size();
        }
    }

    public void addToVisitedUrl(UrlModel urlModel) {
        synchronized (visitedUrls) {
            visitedUrls.add(urlModel);
        }
    }

    public UrlModel getNoVisitedUrl() {
        synchronized (noVisitedUrls) {
            return noVisitedUrls.pop();
        }
    }

    public void addNoVisitedUrl(UrlModel current, String url) {
        synchronized (this) {
            String newURL = new UrlBuilder(url, current.getUrl()).build();

            if (newURL == null) {
                return;
            }

            if (visitedUrlSet.contains(newURL)) {
                return;
            } else {
                visitedUrlSet.add(newURL);
            }

            noVisitedUrls.push(new UrlModel(newURL, current.getDepth() - 1));
        }
    }

    public List<UrlModel> getVisitedUrls() {
        return visitedUrls;
    }
}
