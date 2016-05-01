package com.successfactors.hermes.bean;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.successfactors.category.api.enums.ModuleEnum;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;


/**
 * The Subscriber meta data bean
 * @author wkliu
 *
 */
public class SubscriberMetaDataBean {
  
  private static Logger logger = LogManager.getLogger();
  
  /**
   * The Subscriber class name with package(annotation subscriber)
   * or the subscriber name(rest subscriber) 
   */
  private String subscriberName;

  /**
   * The identity of the subscriber.
   */
  private String id;

  /**
   * The i18n key for name of the subscriber.
   */
  private String nameI18n;

  /**
   * The i18n key for description of the subscriber.
   */
  private String descI18n;

  /**
   * The subscriber impact area.
   */
  private List<String> impactArea;

  /**
   * The module of the subscriber.
   */
  private ModuleEnum module;

  /**
   * Indicate whether the subscriber is a smart suite subscriber. 
   */
  private boolean isSmartSub = false;

  /**
   * Indicate whether the subscriber can be disabled.
   */
  private boolean canDisable = true;

  /**
   * The required feature for the subscriber.
   */
  private String[] featureEnabledCheckFlag;

  @JsonCreator
  public SubscriberMetaDataBean(@JsonProperty("subscriberName") String subscriberName, @JsonProperty("id") String id, @JsonProperty("nameI18n")String nameI18n, @JsonProperty("descI18n")String descI18n,
      @JsonProperty("impactArea")List<String> impactArea, @JsonProperty("module")ModuleEnum module, @JsonProperty("smartSub")boolean isSmartSub, @JsonProperty("canDisable")boolean canDisable,
      @JsonProperty("featureEnabledCheckFlag")String[] featureEnabledCheckFlag) {
    this.subscriberName = subscriberName;
    this.id = id;
    this.nameI18n = nameI18n;
    this.descI18n = descI18n;
    this.impactArea = impactArea;
    this.module = module;
    this.isSmartSub = isSmartSub;
    this.canDisable = canDisable;
    this.featureEnabledCheckFlag = featureEnabledCheckFlag;
  }

  public String getSubscriberName() {
    return subscriberName;
  }

  public String getId() {
    return id;
  }

  public String getNameI18n() {
    return nameI18n;
  }

  public String getDescI18n() {
    return descI18n;
  }

  public List<String> getImpactArea() {
    return impactArea;
  }

  public ModuleEnum getModule() {
    return module;
  }

  public boolean isSmartSub() {
    return isSmartSub;
  }

  public boolean isCanDisable() {
    return canDisable;
  }

  public String[] getFeatureEnabledCheckFlag() {
    return featureEnabledCheckFlag;
  }

  public String toJSON() {
    try {
      return new ObjectMapper().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      logger.error("Failed to generate JSON data for : " + id, e);
    }
    return null;
  }

  public static SubscriberMetaDataBean fromJSON(byte[] data, String subscriberName) {
    try {
      return new ObjectMapper().readValue(data, SubscriberMetaDataBean.class);
    } catch (IOException e) {
      logger.error("Failed to parse JSON to SubscriberMetaDataBean for subscriber : " + subscriberName, e);
    }
    return null;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (canDisable ? 1231 : 1237);
    result = prime * result + ((descI18n == null) ? 0 : descI18n.hashCode());
    result = prime * result + Arrays.hashCode(featureEnabledCheckFlag);
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result
        + ((impactArea == null) ? 0 : impactArea.hashCode());
    result = prime * result + (isSmartSub ? 1231 : 1237);
    result = prime * result + ((module == null) ? 0 : module.hashCode());
    result = prime * result + ((nameI18n == null) ? 0 : nameI18n.hashCode());
    result = prime * result
        + ((subscriberName == null) ? 0 : subscriberName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SubscriberMetaDataBean other = (SubscriberMetaDataBean) obj;
    if (canDisable != other.canDisable)
      return false;
    if (descI18n == null) {
      if (other.descI18n != null)
        return false;
    } else if (!descI18n.equals(other.descI18n))
      return false;
    if (!Arrays.equals(featureEnabledCheckFlag, other.featureEnabledCheckFlag))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (impactArea == null) {
      if (other.impactArea != null)
        return false;
    } else if (!impactArea.equals(other.impactArea))
      return false;
    if (isSmartSub != other.isSmartSub)
      return false;
    if (module == null) {
      if (other.module != null)
        return false;
    } else if (!module.equals(other.module))
      return false;
    if (nameI18n == null) {
      if (other.nameI18n != null)
        return false;
    } else if (!nameI18n.equals(other.nameI18n))
      return false;
    if (subscriberName == null) {
      if (other.subscriberName != null)
        return false;
    } else if (!subscriberName.equals(other.subscriberName))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "SubscriberMetaDataBean [subscriberName=" + subscriberName + ", id="
        + id + ", nameI18n=" + nameI18n + ", descI18n=" + descI18n
        + ", impactArea=" + impactArea + ", module=" + module + ", isSmartSub="
        + isSmartSub + ", canDisable=" + canDisable
        + ", featureEnabledCheckFlag="
        + Arrays.toString(featureEnabledCheckFlag) + "]";
  }

//  public static void main(String ...strings) {
//    SubscriberMetaDataBean bean = new SubscriberMetaDataBean("subscriberName", "id", "nameI18n",
//        "descI18n", Arrays.asList("impactArea", "test"), ModuleEnum.MODULE_ANALYTICS, true, true, new String[] { "RBP",
//            "Talent" });
//    String json = bean.toJSON();
//    System.out.println(json);
//    SubscriberMetaDataBean descrilizedBean = SubscriberMetaDataBean.fromJSON(json.getBytes(), "testSubscriber");
//    System.out.println(descrilizedBean.equals(bean));
//  }
}
