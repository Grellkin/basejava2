package main.ru.javawebinar.basejava.storage.FIleWR;

import main.ru.javawebinar.basejava.model.*;
import main.ru.javawebinar.basejava.util.XmlParser;

import java.io.*;

public class XmlStreamFWR implements FileWriterReader{

    private XmlParser xmlParser;

    public XmlStreamFWR() {
        xmlParser = new XmlParser(
                Resume.class, Organization.class, Link.class,
                OrganizationSection.class, TextSection.class, ListSection.class, Organization.Position.class);
    }

    @Override
    public void writeResumeToFile(OutputStream os, Resume r) throws IOException {
        try(Writer writer = new OutputStreamWriter(os)) {
            xmlParser.marshall(r, writer);
        }
    }

    @Override
    public Resume readResumeFromFile(InputStream is) throws IOException {
        try(Reader reader = new InputStreamReader(is)){
            return xmlParser.unmarshall(reader);
        }
    }


}
