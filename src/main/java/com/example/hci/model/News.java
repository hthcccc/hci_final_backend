package com.example.hci.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "news")
public class News {
    @Id
    @Column(name = "id", nullable = false, length = 32)
    private String id;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "part", length = 10)
    private String part;

    @Column(name = "date")
    private Instant date;

    @Column(name = "url", length = 100)
    private String url;

    @Column(name = "count", nullable = false)
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}