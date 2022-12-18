package org.github.volzock;

import org.github.volzock.models.UrlModel;
import org.github.volzock.requests.HttpGetRequest;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Crawler {
    private static final Pattern HREF_PATTERN = Pattern.compile("<a\\s+([^>]*\\s+)?href=\"([^>\"]+)\"\\s*([^>]*)?>");

    private Stack<UrlModel> noVisitedUrls;
    private List<UrlModel> visitedUrls;
    private HashSet<String> visitedUrlSet;

    public Crawler(String baseUrl, int depth) {
        noVisitedUrls = new Stack<>();
        visitedUrls = new ArrayList<>();
        visitedUrlSet = new HashSet<>();

        noVisitedUrls.push(new UrlModel(baseUrl, depth));
        visitedUrlSet.add(baseUrl);
    }

    public void start() {
        while (!noVisitedUrls.isEmpty()) {
            UrlModel urlModel = noVisitedUrls.pop();
            visitedUrls.add(urlModel);

            if (urlModel.getDepth() == 1) {
                continue;
            }

            try (HttpGetRequest request = new HttpGetRequest(urlModel.getUrl())) {
                try(Scanner scanner = new Scanner(request.send(), StandardCharsets.UTF_8)) {
                    String match = "";
                    while (match != null) {
                        match = scanner.findWithinHorizon(HREF_PATTERN, 0);
                        if (match != null) {
                            String href = scanner.match().group(2);
                            this.addToNoVisitedPages(urlModel, href);
                        }
                    }
                }
            } catch (Exception ignore) {}
        }
    }

    @Override
    public String toString() {
        return visitedUrls.stream().map(Objects::toString).collect(Collectors.joining("\n"));
    }

    private void addToNoVisitedPages(UrlModel current, String url) {
//        String result = "";
//
//        if (!url.startsWith("http:")) {
//            if (url.contains("#")) {
//                return;
//            } else if (url.contains("mailto:")) {
//                return;
//            } else if (url.startsWith("javascript")) {
//                return;
//            } else if (url.startsWith("https")) {
//                return;
//            } else if (url.startsWith("ftp")) {
//                return;
//            }
//
//            if (url.startsWith("..")) {
//
//            }
//
//        } else {
//            result = url;
//        }

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
