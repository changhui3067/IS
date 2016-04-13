
package com.successfactors.sef.bean.genericobject;

import javax.persistence.Column;

import com.sf.sfv4.util.Tables.GENERIC_OBJECT_T;

import com.successfactors.genericobject.bean.model.DataType;
import com.successfactors.genericobject.bean.model.impl.EffectiveDating;
import com.successfactors.genericobject.bean.model.impl.Field;
import com.successfactors.genericobject.bean.model.impl.MDFEntity;
import com.successfactors.genericobject.bean.model.impl.Visibility;


@MDFEntity(effectiveDating = EffectiveDating.NONE)
public class SEFEvent  {
  /** Generic Object Name */
  public static final String GO_NAME = "SEFEvent";

  /** Object type */
  public static final String OBJECT_TYPE = "SEFEvent";

  /** Row Id. */
  @Column(name = GENERIC_OBJECT_T.ROW_ID, nullable = false)
  @Field(visibility = Visibility.NOT_VISIBLE)
  private Long rowId;

  /** External Code */
  @Column(name = GENERIC_OBJECT_T.EXTERNAL_CODE, nullable = false)
  @Field(dataType = DataType.NUMBER, visibility = Visibility.NOT_VISIBLE)
  private Long code;
  
  /** Internal ID - not visible **/
  @Column(name = GENERIC_OBJECT_T.INTERNAL_ID)
  @Field(visibility = Visibility.NOT_VISIBLE)
  private Long internalId;

  
  @Column(name = GENERIC_OBJECT_T.SF_FIELD1)
  @Field(dataType = DataType.STRING)
  private String eventType;


  public Long getRowId() {
    return rowId;
  }


  public void setRowId(Long rowId) {
    this.rowId = rowId;
  }


  public Long getInternalId() {
    return internalId;
  }


  public void setInternalId(Long internalId) {
    this.internalId = internalId;
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


  public String getEventType() {
    return eventType;
  }


  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public static long hashCode(final String event) {
    final int prime = 31;
    long result = 1L;
    
    result = prime * result + event.hashCode();
 
    return result;
  }
  

}

