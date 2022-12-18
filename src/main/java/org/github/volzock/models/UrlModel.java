package org.github.volzock.models;

import java.util.Objects;

public class UrlModel extends BaseModel{
    private String url;
    private Integer depth;

    public UrlModel() {}

    public UrlModel(String url, Integer depth) {
        this.url = url;
        this.depth = depth;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlModel urlModel = (UrlModel) o;
        return url.equals(urlModel.url) && depth.equals(urlModel.depth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, depth);
    }

    @Override
    public String toString() {
        return "UrlModel{" +
                "url='" + url + '\'' +
                ", depth=" + depth +
                '}';
    }
}
