package com.hp.it.innovation.collaboration.common;

public enum UserRoleEnum {
    MEMBER("MEMBER", "Member"), ADMIN("ADMIN", "Administrator"), SUPERVISOR("SUPERVISOR", "Supervisor");

    private String name;
    private String displayName;

    private UserRoleEnum(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
