
package com.successfactors.sef.bean.genericobject;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.OneToMany;

import com.sf.sfv4.util.Tables.GENERIC_OBJECT_T;

import com.successfactors.genericobject.bean.model.APIVisibility;
import com.successfactors.genericobject.bean.model.DataType;
import com.successfactors.genericobject.bean.model.impl.AssociationType;
import com.successfactors.genericobject.bean.model.impl.EffectiveDating;
import com.successfactors.genericobject.bean.model.impl.Field;
import com.successfactors.genericobject.bean.model.impl.GOAssociationDefinition.Type;
import com.successfactors.genericobject.bean.model.impl.GOLocalizedData;
import com.successfactors.genericobject.bean.model.impl.MDFEntity;
import com.successfactors.genericobject.bean.model.impl.Visibility;

@MDFEntity(effectiveDating = EffectiveDating.NONE, isObjDefNeedsToBeSavedImplicitly = true, apiVisibility = APIVisibility.EDITABLE)
public class SEFEntityEventsMap {

  /** Generic Object Name */
  public static final String GO_NAME = "SEFEntityEventsMap";

  /** Object type */
  public static final String OBJECT_TYPE = "SEFEntityEventsMap";

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
  private String  entityName;
  
  @Column(name = GENERIC_OBJECT_T.SF_FIELD2, nullable = false)
  @Field(dataType = DataType.STRING)
  private String  effectiveDate;
  
  @OneToMany
  @AssociationType(type = Type.COMPOSITE)
  private List<SEFKeyValuePair> entityKeys;


  @OneToMany
  @AssociationType(type = Type.COMPOSITE)
  private List<SEFKeyValuePair> parameters;
  

  @OneToMany
  @AssociationType(type = Type.VALID_WHEN)
  private List<SEFEvent> events;
  
  /**
   * @return the rowId
   */
  public Long getRowId() {
    return rowId;
  }


  /**
   * @param rowId the rowId to set
   */
  public void setRowId(Long rowId) {
    this.rowId = rowId;
  }


  /**
   * @return the internalId
   */
  public Long getInternalId() {
    return internalId;
  }


  /**
   * @param internalId the internalId to set
   */
  public void setInternalId(Long internalId) {
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
   * @return the code
   */
  public Long getCode() {
    return code;
  }


  /**
   * @param code the code to set
   */
  public void setCode(Long code) {
    this.code = code;
  }


  /**
   * @return the entityName
   */
  public String getEntityName() {
    return entityName;
  }


  /**
   * @param entityName the entityName to set
   */
  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }


  /**
   * @return the effectiveDate
   */
  public String getEffectiveDate() {
    return effectiveDate;
  }


  /**
   * @param effectiveDate the effectiveDate to set
   */
  public void setEffectiveDate(String effectiveDate) {
    this.effectiveDate = effectiveDate;
  }


  /**
   * @return the entityKeys
   */
  public List<SEFKeyValuePair> getEntityKeys() {
    return entityKeys;
  }


  /**
   * @param entityKeys the entityKeys to set
   */
  public void setEntityKeys(List<SEFKeyValuePair> entityKeys) {
    this.entityKeys = entityKeys;
  }
 
  
  /**
   * @return the parameters
   */
  public List<SEFKeyValuePair> getParameters() {
    return parameters;
  }


  /**
   * @param parameters the parameters to set
   */
  public void setParameters(List<SEFKeyValuePair> parameters) {
    this.parameters = parameters;
  }


  /**
   * @return the events
   */
  public List<SEFEvent> getEvents() {
    return events;
  }


  /**
   * @param events the events to set
   */
  public void setEvents(List<SEFEvent> events) {
    this.events = events;
  }
  
  

  
  
  

}
