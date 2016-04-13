
package com.successfactors.sef.bean.genericobject;

import javax.persistence.Column;

import com.sf.sfv4.util.Tables.GENERIC_OBJECT_T;

import com.successfactors.genericobject.bean.model.DataType;
import com.successfactors.genericobject.bean.model.impl.EffectiveDating;
import com.successfactors.genericobject.bean.model.impl.Field;
import com.successfactors.genericobject.bean.model.impl.MDFEntity;
import com.successfactors.genericobject.bean.model.impl.Visibility;


@MDFEntity(effectiveDating = EffectiveDating.FROM_PARENT)
public class SEFKeyValuePair  {
  /** Generic Object Name */
  public static final String GO_NAME = "SEFKeyValuePair";

  /** Object type */
  public static final String OBJECT_TYPE = "SEFKeyValuePair";

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
  @Field(dataType = DataType.AUTO_NUMBER, visibility = Visibility.NOT_VISIBLE)
  private Long code;

  /** field name code */
  public static final String CODE = "code";
  
  
  @Column(name = GENERIC_OBJECT_T.SF_FIELD1)
  @Field(dataType = DataType.STRING)
  private String key;
  
  
  @Column(name = GENERIC_OBJECT_T.SF_FIELD2)
  @Field(dataType = DataType.STRING)
  private String value;


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
   * @return the key
   */
  public String getKey() {
    return key;
  }


  /**
   * @param key the key to set
   */
  public void setKey(String key) {
    this.key = key;
  }


  /**
   * @return the value
   */
  public String getValue() {
    return value;
  }


  /**
   * @param value the value to set
   */
  public void setValue(String value) {
    this.value = value;
  }
  
  
  public static long hashCode(final String key, final String value) {
    final int prime = 31;
    long result = 1L;
    
    result = prime * result + key.hashCode();
    result = prime * result + value.hashCode();
   
    return result;
  }
}

