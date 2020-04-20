package main.ru.javawebinar.basejava.storage.FIleWR;

import main.ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileWriterReader {

    Resume readResumeFromFile(InputStream is) throws IOException;

    void writeResumeToFile(OutputStream os, Resume resume) throws IOException;
}
