package com.successfactors.seb;

import java.io.Serializable;

/**
 * Created by Xc on 2016/4/23.
 */
public class SebEvent<T> implements Serializable {

    private static final long serialVersionUID = 4927638206211020949L;

    private long publishedAt;
    private String eventId;
    private int priority;
    private String publishedBy;
    private T body;

    public long getPublishedAt() {
        return publishedAt;
    }

    public String getEventId() {
        return eventId;
    }

    public String getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(String publishedBy) {
        this.publishedBy = publishedBy;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setPublishedAt(long publishedAt) {
        this.publishedAt = publishedAt;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
