package org.github.volzock;

import java.net.MalformedURLException;
import java.net.URL;

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
                this.url = null;
            } else {
                if (word.startsWith("/")) {
                    this.url = "http://" + host + word;
                } else {
                    int lastIndexOfReapetedWord = word.indexOf("/");
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

    public String build() {
        return this.url;
    }
}
