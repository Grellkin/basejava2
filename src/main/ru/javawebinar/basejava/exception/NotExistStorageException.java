package main.ru.javawebinar.basejava.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Sorry, resume with uuid = " + uuid + " does not exist.", uuid);
    }
}
