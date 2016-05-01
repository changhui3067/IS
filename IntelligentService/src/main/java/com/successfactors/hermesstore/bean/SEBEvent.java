package com.successfactors.hermesstore.bean;

import static com.successfactors.hermesstore.util.Constants.*;

import java.io.Serializable;
import java.util.Date;

import com.successfactors.hermesstore.util.DateUtils;

/**
 * POJO which represents an SEBEvent entity in SEB_EVENT table.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
/*
@Entity
@Table(name=SEB_EVENT_STORE_TABLE)
*/
public class SEBEvent implements Serializable, Comparable {

  private static final long serialVersionUID = 2520212823232946514L;
  
  private long id;
  private String eventId;
  private String companyId;
  private String userId;
  private String eventType;
  private String transactionId;
  private String topic;
  private byte[] event;
  private int publishOrder = 1;
  private EventStatusEnum status;
  private String processResult;
  private int retryTimes;
  private Date publishedAt;
  private String createdBy;
  private Date createdDate;
  private String lastUpdatedBy;
  private Date lastUpdatedDate;
  
  public SEBEvent() {
    
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }
  
  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public byte[] getEvent() {
    return event;
  }

  public void setEvent(byte[] event) {
    this.event = event;
  }

  public int getPublishOrder() {
    return publishOrder;
  }

  public void setPublishOrder(int publishOrder) {
    this.publishOrder = publishOrder;
  }
  
  public EventStatusEnum getStatus() {
    return status;
  }
  
  public void setStatus(EventStatusEnum status) {
    this.status = status;
  }

  public String getProcessResult() {
    return processResult;
  }

  public void setProcessResult(String processResult) {
    this.processResult = processResult;
  }

  public int getRetryTimes() {
    return retryTimes;
  }

  public void setRetryTimes(int retryTimes) {
    this.retryTimes = retryTimes;
  }

  public Date getPublishedAt() {
    return publishedAt;
  }

  public void setPublishedAt(Date publishedAt) {
    this.publishedAt = publishedAt;
  }
  
  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  public Date getLastUpdatedDate() {
    return lastUpdatedDate;
  }

  public void setLastUpdatedDate(Date lastUpdatedDate) {
    this.lastUpdatedDate = lastUpdatedDate;
  }
  
  @Override
  public int compareTo(Object obj) {
    if (obj instanceof SEBEvent) {
      return this.publishOrder - ((SEBEvent) obj).publishOrder;
    }
    return 0;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(getClass().getSimpleName());
    sb.append(EQUALITY_SIGN)
      .append(LEFT_CURLY_BRACKET)
        .append("id").append(COLON).append(this.getId()).append(COMMA)
        .append("eventId").append(COLON).append(DOUBLE_QUOTATION).append(this.getEventId()).append(DOUBLE_QUOTATION).append(COMMA)
        .append("companyId").append(COLON).append(DOUBLE_QUOTATION).append(this.getCompanyId()).append(DOUBLE_QUOTATION).append(COMMA)
        .append("userId").append(COLON).append(DOUBLE_QUOTATION).append(this.getUserId()).append(DOUBLE_QUOTATION).append(COMMA)
        .append("eventType").append(COLON).append(DOUBLE_QUOTATION).append(this.getEventType()).append(DOUBLE_QUOTATION).append(COMMA)
        .append("transactionId").append(COLON).append(DOUBLE_QUOTATION).append(this.getTransactionId()).append(DOUBLE_QUOTATION).append(COMMA)
        .append("topic").append(COLON).append(DOUBLE_QUOTATION).append(this.getTopic()).append(DOUBLE_QUOTATION).append(COMMA)
        .append("order").append(COLON).append(this.getPublishOrder()).append(COMMA)
        .append("status").append(COLON).append(this.getStatus()).append(COMMA)
        .append("processResult").append(COLON).append(DOUBLE_QUOTATION).append(this.getProcessResult()).append(DOUBLE_QUOTATION).append(COMMA)
        .append("retryTimes").append(COLON).append(this.getRetryTimes()).append(COMMA)
        .append("publishedAt").append(COLON)
          .append(DOUBLE_QUOTATION)
            .append(DateUtils.convertDate2String(this.getPublishedAt(), DateUtils.DEFAULT_SIMPLE_DATE_FORMAT))
          .append(DOUBLE_QUOTATION).append(COMMA)
        .append("createdBy").append(COLON).append(DOUBLE_QUOTATION).append(this.getCreatedBy()).append(DOUBLE_QUOTATION).append(COMMA)
        .append("createdDate").append(COLON)
          .append(DOUBLE_QUOTATION)
            .append(DateUtils.convertDate2String(this.getCreatedDate(), DateUtils.DEFAULT_SIMPLE_DATE_FORMAT))
          .append(DOUBLE_QUOTATION).append(COMMA)
        .append("lastUpdatedBy").append(COLON).append(DOUBLE_QUOTATION).append(this.getLastUpdatedBy()).append(DOUBLE_QUOTATION).append(COMMA)
        .append("lastUpdatedDate").append(COLON)
          .append(DOUBLE_QUOTATION)
            .append(DateUtils.convertDate2String(this.getLastUpdatedDate(), DateUtils.DEFAULT_SIMPLE_DATE_FORMAT))
          .append(DOUBLE_QUOTATION)
      .append(RIGHT_CURLY_BRACKET);
    return sb.toString();
  }
  
  /*
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator=SEB_EVENT_ID_GENERATOR)
  @SequenceGenerator(name=SEB_EVENT_ID_GENERATOR, sequenceName=SEB_EVENT_ID_SEQUENCE, allocationSize=1)
  @Column(name=SEB_EVENT_COLUMN_ID)
  private long id;
  
  @Column(name=SEB_EVENT_COLUMN_EVENT_ID, unique=true)
  private String eventId;
  
  @Column(name=SEB_EVENT_COLUMN_COMPANY_ID)
  private String companyId;
  
  @Column(name=SEB_EVENT_COLUMN_EVENT_TYPE)
  private String eventType;
  
  @Column(name=SEB_EVENT_COLUMN_TRANSACTION_ID)
  private String transactionId;
  
  @Column(name=SEB_EVENT_COLUMN_IS_BULK)
  private boolean isBulk;
  
  @Column(name=SEB_EVENT_COLUMN_PRIORITY)
  private int priority;
  
  @Lob
  @Column(name=SEB_EVENT_COLUMN_TOPIC)
  private byte[] topic;
  
  @Lob
  @Column(name=SEB_EVENT_COLUMN_EVENT)
  private byte[] event;
  
  @Enumerated(EnumType.STRING)
  @Column(name=SEB_EVENT_COLUMN_STATUS)
  private EventStatusEnum status;
  
  @Lob
  @Column(name=SEB_EVENT_COLUMN_PROCESS_RESULT)
  private String processResult;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name=SEB_EVENT_COLUMN_PUBLISHED_AT)
  private Date publishedAt;
  
  @Column(name=SEB_EVENT_COLUMN_CREATED_BY)
  private String createdBy;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name=SEB_EVENT_COLUMN_CREATED_DATE)
  private Date createdDate;
  
  @Column(name=SEB_EVENT_COLUMN_LAST_UPDATED_BY)
  private String lastUpdatedBy;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name=SEB_EVENT_COLUMN_LAST_UPDATED_DATE)
  private Date lastUpdatedDate;
  */
  
}
