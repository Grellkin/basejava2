package main.ru.javawebinar.basejava.storage.FIleWR;

import main.ru.javawebinar.basejava.model.Resume;
import main.ru.javawebinar.basejava.util.JsonParser;

import java.io.*;

public class JsonStreamFWR implements FileWriterReader{

    private JsonParser parser;

    public JsonStreamFWR() {
        this.parser = new JsonParser();
    }

    @Override
    public void writeResumeToFile(OutputStream os, Resume r) throws IOException {
        try(Writer writer = new OutputStreamWriter(os)){
            parser.toJson(r, writer);
        }
    }

    @Override
    public Resume readResumeFromFile(InputStream is) throws IOException {
        try(Reader reader = new InputStreamReader(is)){
            return parser.fromJson(reader);
        }
    }

}
