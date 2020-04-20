package main.ru.javawebinar.basejava.storage.FIleWR;

import main.ru.javawebinar.basejava.model.Resume;
import main.ru.javawebinar.basejava.exception.StorageException;

import java.io.*;

public class ObjectStreamFWR implements FileWriterReader{
    @Override
    public Resume readResumeFromFile(InputStream is) {
        try(ObjectInputStream ois = new ObjectInputStream(is)){
            return (Resume) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new StorageException("Can`t read from file", e);
        }
    }

    @Override
    public void writeResumeToFile(OutputStream os, Resume resume) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(os)){
            oos.writeObject(resume);
        }
    }
}
