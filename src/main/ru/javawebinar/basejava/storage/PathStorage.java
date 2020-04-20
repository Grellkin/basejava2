package main.ru.javawebinar.basejava.storage;

import main.ru.javawebinar.basejava.model.Resume;
import main.ru.javawebinar.basejava.storage.FIleWR.FileWriterReader;
import main.ru.javawebinar.basejava.exception.StorageException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {

    private Path directory;
    //pattern Strategy give you a possibility to choose what type of files use and how to store data there
    private FileWriterReader fileWR;

    public PathStorage(Path directory, FileWriterReader readerWriter) {
        Objects.requireNonNull(directory, "directory " + directory.getFileName() + " must be not null");
        Objects.requireNonNull(readerWriter, "File reader/writer is required.");
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(directory.getFileName() + " is not a directory");
        }
        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException("directory has constraints of reading/writing");
        }
        this.directory = directory;
        this.fileWR = readerWriter;
    }

    @Override
    protected Path findSearchKey(String path) {
        return directory.resolve(path);
    }

    @Override
    protected boolean isElementPresentInStorage(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected void insertElement(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("IOException while creating file.", getNameOfFile(path), e);
        }
        updateElement(path, resume);
    }

    @Override
    protected Resume getElement(Path path) {
        try {
            return fileWR.readResumeFromFile(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IOException while writing to file.", getNameOfFile(path), e);
        }
    }

    @Override
    protected void updateElement(Path path, Resume resume) {
        try (BufferedOutputStream os = new BufferedOutputStream(Files.newOutputStream(path))) {
            fileWR.writeResumeToFile(os, resume);
        } catch (IOException e) {
            throw new StorageException("IOException while writing to file.", getNameOfFile(path), e);
        }
    }

    @Override
    protected void removeElement(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("IOException while deleting file.", getNameOfFile(path), e);
        }
    }

    @Override
    protected List<Resume> getCopyOfStorage() {
        MyWalker walker = new MyWalker(MyWalker.COPY_ALL_PARAM);
        traverseFileTree(walker);
        return walker.resumes;
    }

    @Override
    public void clear() {
        traverseFileTree(new MyWalker(MyWalker.CLEAR_PARAM));
    }

    @Override
    public int size() {
        MyWalker walker = new MyWalker(MyWalker.SIZE_PARAM);
        traverseFileTree(walker);
        return walker.countOfFiles;
    }

    private String getNameOfFile(Path path) {
        return path.getFileName().toString();
    }

    private void traverseFileTree(MyWalker walker) {
        try {
            Files.walkFileTree(directory, walker);
        } catch (IOException e) {
            throw new StorageException("IOException while traversing file tree.", getNameOfFile(directory), e);
        }
    }

    private class MyWalker extends SimpleFileVisitor<Path> {

        private int countOfFiles = 0;
        private List<Resume> resumes = new ArrayList<>();

        private static final int CLEAR_PARAM = 1;
        private static final int SIZE_PARAM = 2;
        private static final int COPY_ALL_PARAM = 3;
        private final int CHOSEN_PARAM;

        private MyWalker(int param) {
            CHOSEN_PARAM = param;
        }

        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
            switch (CHOSEN_PARAM) {
                case CLEAR_PARAM:
                    Files.delete(path);
                    break;
                case SIZE_PARAM:
                    countOfFiles++;
                    break;
                case COPY_ALL_PARAM:
                    resumes.add(getElement(path));
                    break;
                default:
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
