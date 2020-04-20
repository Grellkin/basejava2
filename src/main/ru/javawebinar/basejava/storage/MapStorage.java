package main.ru.javawebinar.basejava.storage;

import main.ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected String findSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isElementPresentInStorage(String searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected void insertElement(String searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    @Override
    protected Resume getElement(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void updateElement(String searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    @Override
    protected void removeElement(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> getCopyOfStorage() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
