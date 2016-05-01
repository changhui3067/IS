package com.successfactors.seb;

import com.successfactors.hermes.core.Meta;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;

import static com.successfactors.hermes.Constants.*;

/**
 * <code>SFEvent<T></code> is the base event structure.
 *
 * @author araza
 */
public class SFEvent<T> implements Serializable, Comparable {

  private static final long serialVersionUID = -7771917618104370068L;

  private Meta meta;
  private T body;

  private boolean acknowledged = true;


  /**
   * Creates a SFEvent object with the specified body.
   * @param body The body of the message.
   */
  public SFEvent(T body) {
    this.meta = new Meta();
    this.body = body;
  }

  /**
   * Default constructor. Make sure to set valid instances of body later.
   *
   */
  public SFEvent() {
    meta = new Meta();
  }
  /**
   * Getter meta.
   * @return the meta
   */
  public Meta getMeta() {
    return meta;
  }
  /**
   * Setter meta.
   * @param meta the meta to set
   */
  public void setMeta(Meta meta) {
    this.meta = meta;
  }
  /**
   * Getter body.
   * @return the body
   */
  public T getBody() {
    return body;
  }

  /**
   * Setter body.
   * @param body the body to set
   */
  public void setBody(T body) {
    this.body = body;
  }

  /**
   * Indicates that message was rejected by subscriber and should be re-delivered
   * @return the rejected state, true is message should be re-delivered
   */
  @JsonIgnore
  public boolean isAcknowledged() {
    return acknowledged;
  }

   /**
   * Sets the "rejected" flag.
   * This flag is true by default, so message will be automatically acknowledged
   *
   * @param acknowledged boolean for acknowledgement
   */
  @JsonIgnore
  public void setAcknowledged(boolean acknowledged){
    this.acknowledged = acknowledged;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(getClass().getName());
    sb.append(EQUALITY_SIGN)
      .append(LEFT_CURLY_BRACKET)
      .append("meta").append(COLON).append(this.getMeta()).append(COMMA)
      .append("body").append(COLON).append(this.getBody())
      .append(RIGHT_CURLY_BRACKET);
    return sb.toString();
  }

  @Override
  public int compareTo(Object obj) {
    if (obj instanceof SFEvent) {
      SFEvent that = (SFEvent) obj;
      Meta thisMeta = this.getMeta();
      Meta thatMeta = that.getMeta();
      if (thisMeta != null && thatMeta != null) {
        long thisPublishedAt = thisMeta.getPublishedAt();
        long thatPublishedAt = thatMeta.getPublishedAt();
        if (thisPublishedAt < thatPublishedAt) {
          return -1;
        } else if (thisPublishedAt > thatPublishedAt) {
          return 1;
        }
      }
    }
    return 0;
  }

}
