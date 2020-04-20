package main.ru.javawebinar.basejava.storage;


import main.ru.javawebinar.basejava.model.Resume;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.ru.javawebinar.basejava.exception.ExistStorageException;
import main.ru.javawebinar.basejava.exception.NotExistStorageException;

import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger log = LogManager.getRootLogger();

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
//      log.info("Save resume with uuid = " + uuid); //временно отключил, мешало колличество записей
        SK searchKey = findSearchKey(uuid);
        if (isElementPresentInStorage(searchKey)) {
            log.warn("Element " + uuid + " already present in storage");
            throw new ExistStorageException(uuid);
        }
        insertElement(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        log.debug("Get resume with uuid = " + uuid);
        SK searchKey = findSearchKey(uuid);
        checkAndLog(uuid, searchKey);
        return getElement(searchKey);
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        log.info("Update resume with uuid = " + uuid);
        SK searchKey = findSearchKey(uuid);
        checkAndLog(uuid, searchKey);
        updateElement(searchKey, resume);
    }

    @Override
    public void delete(String uuid) {
        log.info("Delete resume with uuid = " + uuid);
        SK searchKey = findSearchKey(uuid);
        checkAndLog(uuid, searchKey);
        removeElement(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getCopyOfStorage();
        list.sort(Resume.comparatorByFullNameAndUuid);
        return list;
    }

    private void checkAndLog(String uuid, SK searchKey) {
        if (!isElementPresentInStorage(searchKey)) {
            log.info("Element " + uuid + " not present in storage");
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract SK findSearchKey(String uuid);

    protected abstract void insertElement(SK searchKey, Resume resume);

    protected abstract Resume getElement(SK searchKey);

    protected abstract void updateElement(SK searchKey, Resume resume);

    protected abstract void removeElement(SK searchKey);

    protected abstract List<Resume> getCopyOfStorage();

    protected abstract boolean isElementPresentInStorage(SK searchKey);
}
