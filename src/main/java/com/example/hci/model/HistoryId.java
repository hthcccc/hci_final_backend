package com.example.hci.model;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HistoryId implements Serializable {
    private static final long serialVersionUID = -338159529854297911L;
    @Column(name = "user_id", nullable = false, length = 24)
    private String userId;
    @Column(name = "news_id", nullable = false, length = 32)
    private String newsId;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(newsId, userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HistoryId entity = (HistoryId) o;
        return Objects.equals(this.newsId, entity.newsId) &&
                Objects.equals(this.userId, entity.userId);
    }
}