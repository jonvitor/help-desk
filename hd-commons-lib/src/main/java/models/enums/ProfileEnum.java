package models.enums;

import java.util.Arrays;

public enum ProfileEnum {
    PROFILE_ADMIN("ROLE_ADMIN"),
    PROFILE_CUSTOMER("ROLE_CUSTOMER"),
    PROFILE_TECHNICIAN("ROLE_TECHNICIAN");

    private final String description;

    ProfileEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static ProfileEnum toEnum(String description) {
        return Arrays.stream(ProfileEnum.values())
                .filter(profileEnum -> profileEnum.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid description: " + description));
    }
}
