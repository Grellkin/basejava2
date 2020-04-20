package main.ru.javawebinar.basejava.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Sorry, resume with uuid = " + uuid + " already exists.", uuid);
    }
}
