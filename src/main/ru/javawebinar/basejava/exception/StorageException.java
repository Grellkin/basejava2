package main.ru.javawebinar.basejava.exception;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(String message, Exception e) {
        this(message, "defaultID", e);
    }

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid, Exception e) {
        super(message, e);
        this.uuid = uuid;
    }

    public StorageException(String message) {
        this(message, null, null);
    }
}