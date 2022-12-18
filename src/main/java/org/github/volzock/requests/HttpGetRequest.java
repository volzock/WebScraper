package org.github.volzock.requests;

import java.net.MalformedURLException;

public class HttpGetRequest extends HttpRequest{
    public HttpGetRequest(String url) throws MalformedURLException {
        super(url);
        super.setType("GET");
    }
}
