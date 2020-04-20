package main.ru.javawebinar.basejava.storage;

import main.ru.javawebinar.basejava.model.Resume;

import java.util.List;

public interface Storage {

    void save(Resume resume);

    Resume get(String uuid);

    void update(Resume resume);

    void delete(String uuid);

    List<Resume> getAllSorted();

    void clear();

    int size();
}
