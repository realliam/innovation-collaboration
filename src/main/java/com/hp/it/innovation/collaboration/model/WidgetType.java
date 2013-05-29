package com.hp.it.innovation.collaboration.model;

public class WidgetType extends Component {

    /**
     * 
     */
    private static final long serialVersionUID = -755611558699441252L;
    
    private String typeName;
    
    private String displayName;
    
    private String description;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
}
