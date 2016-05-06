package com.successfactors.sef;

import com.successfactors.sef.metadata.EventMetadata;

import java.io.Serializable;
import java.util.Map;

import static com.successfactors.seb.Constants.*;


/**
 * <code>SefEvent<T></code> is the base event structure.
 *
 * @author araza
 */
public class SefEvent implements Serializable{

    private static final long serialVersionUID = -7771917618104370068L;

    private EventMetadata meta;

    private Map<String,Object> params;

    private boolean acknowledged = true;


    /**
     * Default constructor. Make sure to set valid instances of body later.
     */
    public SefEvent(EventMetadata meta) {
        this.meta = meta;
    }

    /**
     * Default constructor. Make sure to set valid instances of body later.
     */
    public SefEvent() {
    }

    /**
     *
     */
    public SefEvent(EventMetadata meta,Map<String,Object> params) {
        this.meta = meta;
        this.params = params;
    }

    /**
     * Getter meta.
     *
     * @return the meta
     */
    public EventMetadata getMeta() {
        return meta;
    }

    /**
     * Setter meta.
     *
     * @param meta the meta to set
     */
    public void setMeta(EventMetadata meta) {
        this.meta = meta;
    }


    /**
     * Indicates that message was rejected by subscriber and should be re-delivered
     *
     * @return the rejected state, true is message should be re-delivered
     */
    public boolean isAcknowledged() {
        return acknowledged;
    }

    /**
     * Sets the "rejected" flag.
     * This flag is true by default, so message will be automatically acknowledged
     *
     * @param acknowledged boolean for acknowledgement
     */
    public void setAcknowledged(boolean acknowledged) {
        this.acknowledged = acknowledged;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getName());
        sb.append(EQUALITY_SIGN)
                .append(LEFT_CURLY_BRACKET)
                .append("meta").append(COLON).append(this.getMeta()).append(COMMA)
                .append("body").append(COLON).append(RIGHT_CURLY_BRACKET);
        return sb.toString();
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
