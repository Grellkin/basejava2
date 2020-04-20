package main.ru.javawebinar.basejava.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.ru.javawebinar.basejava.model.AbstractSection;
import main.ru.javawebinar.basejava.model.Resume;

import java.io.Reader;
import java.io.Writer;

public class JsonParser{

    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(AbstractSection.class, new JsonAdapter<>())
            .setPrettyPrinting()
            .create();

    public void toJson(Object object, Writer writer){
        gson.toJson(object, writer);
    }

    public Resume fromJson(Reader reader){
       return gson.fromJson(reader, Resume.class);
    }




}
