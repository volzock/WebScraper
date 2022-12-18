package org.github.volzock.requests;

import org.github.volzock.utils.Validators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class HttpRequest implements AutoCloseable {
    private static final Integer PORT = 80;
    private String host;
    private String path;
    private String type;
    private Map<String, String> headers;
    private Socket socket;

    public HttpRequest(String url) throws MalformedURLException {
        getDomainNameAndPathFromUrl(url);
        headers = new HashMap<>(){{put("Host", host);}};
    }

    protected void setType(String type) {
        this.type = type;
    }

    public HttpRequest addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public InputStream send() throws IOException{
        socket = new Socket(host, 80);
        socket.setSoTimeout(1000);
        this.sendData();
        return socket.getInputStream();
    }

    private void sendData() throws IOException{
        PrintWriter out = new PrintWriter(socket.getOutputStream());

        out.printf("%s %s HTTP/1.1\r\n", type.toUpperCase(Locale.ROOT), path);

        for (var key : headers.keySet()) {
            out.printf("%s: %s\r\n", key, headers.get(key));
        }

        out.print("\r\n");
        out.flush();
    }

    private void getDomainNameAndPathFromUrl(String url) throws MalformedURLException{
        if (!Validators.isHttpUrl(url)) {
            throw new MalformedURLException();
        }

        int indexOfSlash = url.indexOf("/", 7);
        if (indexOfSlash == -1) {
            indexOfSlash = url.length();
        }

        this.host = url.substring(7, indexOfSlash);
        this.path = url.length() == indexOfSlash ? "/" : url.substring(indexOfSlash);
    }

    @Override
    public void close() throws Exception {
        socket.close();
    }

}
