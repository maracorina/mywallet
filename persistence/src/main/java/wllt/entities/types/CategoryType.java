package wllt.entities.types;

import java.util.HashMap;
import java.util.Map;

public enum CategoryType {
    INCOME("INCOME"),
    BUDGET("BUDGET"),
    CATEGORY("CATEGORY");

    private String actualString;

    CategoryType(String actualString) {
        this.actualString = actualString;
    }

    public String getActualString() {
        return actualString;
    }

    //****** Reverse Lookup Implementation************//

    //Lookup table
    private static final Map<String, CategoryType> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (CategoryType transactionType : CategoryType.values()) {
            lookup.put(transactionType.getActualString(), transactionType);
        }
    }

    //This method can be used for reverse lookup purpose
    public static CategoryType get(String actualString) {
        return lookup.get(actualString);
    }
}
