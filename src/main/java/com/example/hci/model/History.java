package com.example.hci.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "history")
public class History {
    @EmbeddedId
    private HistoryId id;

    @Column(name = "date")
    private Instant date;

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public HistoryId getId() {
        return id;
    }

    public void setId(HistoryId id) {
        this.id = id;
    }
}