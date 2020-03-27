package wllt.entities.types;

import java.util.HashMap;
import java.util.Map;

public enum StatusType {
    ACTIVE(1),
    DEACTIVATED(0);

    private Integer actualValue;

    StatusType(Integer actualValue) {
        this.actualValue = actualValue;
    }

    public Integer getActualValue() {
        return actualValue;
    }

    //****** Reverse Lookup Implementation************//

    //Lookup table
    private static final Map<Integer, StatusType> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (StatusType userStatus : StatusType.values()) {
            lookup.put(userStatus.getActualValue(), userStatus);
        }
    }

    //This method can be used for reverse lookup purpose
    public static StatusType get(Integer actualValue) {
        return lookup.get(actualValue);
    }
}
