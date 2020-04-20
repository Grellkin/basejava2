package main.ru.javawebinar.basejava.storage;

import main.ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public class SortedArrayStorage extends AbstractArrayStorage {

    /**
     * Method based on {@link Arrays#binarySearch(int[], int) binarySearch},
     * MAKE SURE you understand return params of this method.
     *
     * @param uuid identifier of resume
     * @return index of element in storage if it presents, and special index if not
     */

    @Override
    protected Integer findSearchKey(String uuid) {
        Resume r = new Resume(uuid, "defaultName");
        return Arrays.binarySearch(storage, 0, size(), r, Resume.comparatorByUuid);
    }

    @Override
    protected void insertResume(int index, Resume r) {
        index = -(index + 1);
        System.arraycopy(storage, index, storage, index + 1, cursor - index);
        cursor++;
        storage[index] = r;
    }

    @Override
    protected void removeElement(Integer searchKey) {
        System.arraycopy(storage, searchKey + 1, storage, searchKey, (--cursor) - searchKey);
        storage[cursor] = null;
    }

    @Override
    protected List<Resume> getCopyOfStorage() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size()));
    }
}
