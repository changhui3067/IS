package com.successfactors.sef.bean.genericobject;

import javax.persistence.Column;

import com.sf.sfv4.util.Tables.GENERIC_OBJECT_T;

import com.successfactors.genericobject.bean.model.APIVisibility;
import com.successfactors.genericobject.bean.model.DataType;
import com.successfactors.genericobject.bean.model.impl.EffectiveDating;
import com.successfactors.genericobject.bean.model.impl.Field;
import com.successfactors.genericobject.bean.model.impl.GOLocalizedData;
import com.successfactors.genericobject.bean.model.impl.MDFEntity;
import com.successfactors.genericobject.bean.model.impl.Visibility;

@MDFEntity(effectiveDating = EffectiveDating.NONE, isObjDefNeedsToBeSavedImplicitly = true, apiVisibility = APIVisibility.EDITABLE)
public class SEFSubscriberConfiguration {

  /** Generic Object Name */
  public static final String GO_NAME = "SEFSubscriberConfiguration";

  /** Object type */
  public static final String OBJECT_TYPE = "SEFSubscriberConfiguration";

  /** Row Id. */
  @Column(name = GENERIC_OBJECT_T.ROW_ID, nullable = false)
  @Field(visibility = Visibility.NOT_VISIBLE)
  private Long rowId;

  /** Internal ID - not visible **/
  @Column(name = GENERIC_OBJECT_T.INTERNAL_ID)
  @Field(visibility = Visibility.NOT_VISIBLE)
  private Long internalId;

  /** External Code */
  @Column(name = GENERIC_OBJECT_T.EXTERNAL_CODE, nullable = false)
  @Field(dataType = DataType.NUMBER)
  private Long code;

  /** field name code */
  public static final String CODE = "code";

  /** External Name */
  @Column(name = GENERIC_OBJECT_T.EXTERNAL_NAME)
  private GOLocalizedData externalName;

  @Column(name = GENERIC_OBJECT_T.SF_FIELD1, nullable = false)
  @Field(dataType = DataType.STRING)
  private String eventType;

  @Column(name = GENERIC_OBJECT_T.SF_FIELD2)
  @Field(dataType = DataType.STRING)
  private String catetoryID;

  @Column(name = GENERIC_OBJECT_T.SF_FIELD3)
  @Field(dataType = DataType.STRING)
  private String subscriberID;

  @Column(name = GENERIC_OBJECT_T.SF_FIELD4, nullable = false)
  @Field(dataType = DataType.BOOLEAN)
  private boolean enabled;

  @Column(name = GENERIC_OBJECT_T.SF_FIELD5)
  @Field(dataType = DataType.NUMBER)
  private Long daysInAdvance;

  @Column(name = GENERIC_OBJECT_T.SF_FIELD6)
  @Field(dataType = DataType.BOOLEAN)
  private Boolean reDelivered;

  /**
   * @return the rowId
   */
  public Long getRowId() {
    return rowId;
  }

  /**
   * @param rowId
   *          the rowId to set
   */
  public void setRowId(final Long rowId) {
    this.rowId = rowId;
  }

  /**
   * @return the code
   */
  public Long getCode() {
    return code;
  }

  /**
   * @param code
   *          the code to set
   */
  public void setCode(final Long code) {
    this.code = code;
  }

  /**
   * @return the internalId
   */
  public Long getInternalId() {
    return internalId;
  }

  /**
   * @param internalId
   *          the internalId to set
   */
  public void setInternalId(final Long internalId) {
    this.internalId = internalId;
  }

  /**
   * @return the externalName
   */
  public GOLocalizedData getExternalName() {
    return externalName;
  }

  /**
   * @param externalName
   *          the externalName to set
   */
  public void setExternalName(final GOLocalizedData externalName) {
    this.externalName = externalName;
  }

  /**
   * @return the eventType
   */
  public String getEventType() {
    return eventType;
  }

  /**
   * @param eventType
   *          the eventType to set
   */
  public void setEventType(final String eventType) {
    this.eventType = eventType;
  }

  /**
   * @return the catetoryID
   */
  public String getCatetoryID() {
    return catetoryID;
  }

  /**
   * @param catetoryID
   *          the catetoryID to set
   */
  public void setCatetoryID(final String catetoryID) {
    this.catetoryID = catetoryID;
  }

  /**
   * @return the subscriberID
   */
  public String getSubscriberID() {
    return subscriberID;
  }

  /**
   * @param subscriberID
   *          the subscriberID to set
   */
  public void setSubscriberID(final String subscriberID) {
    this.subscriberID = subscriberID;
  }

  /**
   * @return the enabled
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * @param enabled
   *          the enabled to set
   */
  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * @return the daysInAdvance
   */
  public Long getDaysInAdvance() {
    return daysInAdvance;
  }

  /**
   * @param daysInAdvance
   *          the daysInAdvance to set
   */
  public void setDaysInAdvance(final Long daysInAdvance) {
    this.daysInAdvance = daysInAdvance;
  }

  /**
   * @return the reDelivered
   */
  public Boolean getReDelivered() {
    return reDelivered;
  }

  /**
   * @param reDelivered
   *          the reDelivered to set
   */
  public void setReDelivered(final Boolean reDelivered) {
    this.reDelivered = reDelivered;
  }

}
