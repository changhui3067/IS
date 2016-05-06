package com.successfactors.sef.metadata;

/**
 * Created by Xc on 2016/4/24.
 * eventMetadata, will be defined in an outer json file
 */
public class EventMetadata {

    public ParamMetadata[] getParams() {
        return params;
    }

    public void setParams(ParamMetadata[] params) {
        this.params = params;
    }

    public static class ParamMetadata {

        private String name;
        private String type;
        private String description;
        private String descriptionMessageKey;
        private boolean hasValueAlways;

        /**
         * Get name
         *
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * Set name
         *
         * @param name
         *          the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Get type
         *
         * @return the type
         */
        public String getType() {
            return type;
        }

        /**
         * Set type
         *
         * @param type
         *          the type to set
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * Get description
         *
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set description
         *
         * @param description
         *          the description to set
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get description message key
         *
         * @return the descriptionMessageKey
         */
        public String getDescriptionMessageKey() {
            return descriptionMessageKey;
        }

        /**
         * Set description message key
         *
         * @param descriptionMessageKey
         *          the descriptionMessageKey to set
         */
        public void setDescriptionMessageKey(String descriptionMessageKey) {
            this.descriptionMessageKey = descriptionMessageKey;
        }

        /**
         * Get has values always
         *
         * @return the hasValueAlways
         */
        public boolean isHasValueAlways() {
            return hasValueAlways;
        }

        /**
         * Set has values always
         *
         * @param hasValueAlways
         *          the hasValueAlways to set
         */
        public void setHasValueAlways(boolean hasValueAlways) {
            this.hasValueAlways = hasValueAlways;
        }
    }
    //
    private String topic;

    //unique id
    private String type;

    //description on web UI
    private String descriptionMessage;

    //title on UI
    private String title;

    private ParamMetadata[] params = new ParamMetadata[0];


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescriptionMessage() {
        return descriptionMessage;
    }

    public void setDescriptionMessage(String descriptionMessage) {
        this.descriptionMessage = descriptionMessage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
