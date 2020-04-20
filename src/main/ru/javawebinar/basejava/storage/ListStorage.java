package main.ru.javawebinar.basejava.storage;

import main.ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean isElementPresentInStorage(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void insertElement(Integer searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getElement(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void updateElement(Integer searchKey, Resume resume) {
        storage.set(searchKey, resume);
    }

    @Override
    protected void removeElement(Integer searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }


    @Override
    protected List<Resume> getCopyOfStorage() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
