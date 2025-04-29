package dev.nj.tms.dictionaries;

public enum TaskStatus {
    CREATED("CREATED");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TaskStatus valueOfString(String value) {
        for (TaskStatus e : values()) {
            if (e.value.equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }
}
