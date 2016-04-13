package com.successfactors.sef.metadata;


public class SubscriberMetadata {

  public static class Subscriber {
    
    public static class ImpactArea {    

      private String impactArea;
     
      /**
       * @return the impact area
       */
      public String getImpactArea() {
        return impactArea;
      }

      /**
       * @param impactArea
       *          the impact area to set
       */
      public void setImpactArea(final String impactArea) {
        this.impactArea = impactArea;
      }
    }
    
    
    private String module;
    private final ImpactArea[] subscribingImpactArea = new ImpactArea[0];
    private String subscriberDescription;
    private Boolean status;
    private Boolean reDelivered;
    private Long daysInAdvance;
    private String pocFeature;
    private String externalLink;
    private String externalLinkTitle;

    /**
     * @return the module
     */
    public String getModule() {
      return module;
    }

    /**
     * @param module
     *          the module to set
     */
    public void setModule(final String module) {
      this.module = module;
    }


    /**
     * @return the subscribingImpactArea
     */
    public ImpactArea[] getSubscribingImpactArea() {
      return subscribingImpactArea;
    }

    /**
     * @return the subscriberDescription
     */
    public String getSubscriberDescription() {
      return subscriberDescription;
    }

    /**
     * @param subscriberDescription
     *          the subscriberDescription to set
     */
    public void setSubscriberDescription(final String subscriberDescription) {
      this.subscriberDescription = subscriberDescription;
    }

    /**
     * @return the status
     */
    public Boolean getStatus() {
      return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Boolean status) {
      this.status = status;
    }

    /**
     * @return the reDelivered
     */
    public Boolean getReDelivered() {
      return reDelivered;
    }

    /**
     * @param reDelivered the reDelivered to set
     */
    public void setReDelivered(Boolean reDelivered) {
      this.reDelivered = reDelivered;
    }

    /**
     * @return the daysInAdvance
     */
    public Long getDaysInAdvance() {
      return daysInAdvance;
    }

    /**
     * @param daysInAdvance the daysInAdvance to set
     */
    public void setDaysInAdvance(Long daysInAdvance) {
      this.daysInAdvance = daysInAdvance;
    }


    /**
     * @return the pocFeature
     */
    public String getPocFeature() {
      return pocFeature;
    }

    /**
     * @param pocFeature the pocFeature to set
     */
    public void setPocFeature(String pocFeature) {
      this.pocFeature = pocFeature;
    }

    /**
     * @return the externalLink
     */
    public String getExternalLink() {
      return externalLink;
    }

    /**
     * @param externalLink the externalLink to set
     */
    public void setExternalLink(String externalLink) {
      this.externalLink = externalLink;
    }

    /**
     * @return the externalLinkTitle
     */
    public String getExternalLinkTitle() {
      return externalLinkTitle;
    }

    /**
     * @param externalLinkTitle the externalLinkTitle to set
     */
    public void setExternalLinkTitle(String externalLinkTitle) {
      this.externalLinkTitle = externalLinkTitle;
    }

  }

  private String type;
  private final Subscriber[] subscribers = new Subscriber[0];

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public Subscriber[] getSubscribers() {
    return subscribers;
  }

}
