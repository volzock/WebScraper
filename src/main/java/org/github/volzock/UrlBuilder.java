package org.github.volzock;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UrlBuilder {
    private String url;

    public UrlBuilder(String word, String baseURL) {
        String host;
        try {
            URL url = new URL(word);
            if (!"http".equals(url.getProtocol())) {
                this.url = null;
            }
        } catch (MalformedURLException ignored) {
            try {
                host = new URL(baseURL).getHost();
            } catch (MalformedURLException e) {
                host = baseURL;
            }
            if (word.contains("#") || word.startsWith("mailto:") || word.startsWith("javascript")) {
                this.url = null;
            } else if (word.startsWith("../")) {
                List<String> relativeUrlComponents = Arrays.stream(word.split("../")).collect(Collectors.toList());
                List<String> currentUrlComponents = Arrays.stream(baseURL.split("/")).collect(Collectors.toList());

                if (currentUrlComponents.size() - 2 - relativeUrlComponents.size() >= 0) {
                    baseURL = currentUrlComponents
                            .stream()
                            .limit(currentUrlComponents.size() - relativeUrlComponents.size() + 1)
                            .collect(Collectors.joining("/"));
                    this.url =  baseURL + "/" + relativeUrlComponents.get(relativeUrlComponents.size() - 1);
                } else {
                    this.url = null;
                }
            } else {
                if (word.startsWith("/")) {
                    this.url = "http://" + host + word;
                } else {
                    int lastIndexOfReapetedWord = word.indexOf("/");

                    if (lastIndexOfReapetedWord == -1) {
                        this.url = null;
                    } else {
                        String repeatedWord = word.substring(0, lastIndexOfReapetedWord);
                        if (baseURL.endsWith(repeatedWord)) {
                            this.url = baseURL + (baseURL.endsWith("/") ? "" : "/") + word.substring(lastIndexOfReapetedWord + 1);
                        } else {
                            this.url = baseURL + (baseURL.endsWith("/") ? "" : "/") + word;
                        }
                    }
                }
            }
        }
    }

    public String build() {
        return this.url;
    }
}
