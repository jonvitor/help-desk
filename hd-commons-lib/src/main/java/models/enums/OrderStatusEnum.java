package models.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
public enum OrderStatusEnum {

    OPEN("Open"),
    CLOSED("Closed"),
    IN_PROGRESS("In Progress"),
    CANCELLED("Cancelled");

    @Getter
    private final String description;

    public static OrderStatusEnum toEnum(String description) {
        return Arrays.stream(OrderStatusEnum.values())
                .filter(orderStatusEnum -> orderStatusEnum.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid OrderStatusEnum description: " + description));
    }

}
