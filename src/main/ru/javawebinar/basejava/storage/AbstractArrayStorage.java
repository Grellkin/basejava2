package main.ru.javawebinar.basejava.storage;

import main.ru.javawebinar.basejava.model.Resume;
import main.ru.javawebinar.basejava.exception.StorageException;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int cursor;

    @Override
    protected boolean isElementPresentInStorage(Integer searchKey) {
        return (searchKey >= 0);
    }

    @Override
    protected Resume getElement(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected void insertElement(Integer searchKey, Resume resume) {
        if (size() == STORAGE_LIMIT) {
            throw new StorageException("Sorry, storage is full.", resume.getUuid());
        }
        insertResume(searchKey, resume);
    }

    @Override
    protected void updateElement(Integer searchKey, Resume resume) {
        storage[searchKey] = resume;
    }

    @Override
    public int size() {
        return cursor;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, null);
        cursor = 0;
    }

    protected abstract void insertResume(int index, Resume r);

}


