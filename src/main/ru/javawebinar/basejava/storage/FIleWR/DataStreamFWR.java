package main.ru.javawebinar.basejava.storage.FIleWR;



import main.ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class DataStreamFWR implements FileWriterReader {

    @Override
    public void writeResumeToFile(OutputStream os, Resume r) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            writeContacts(r, dos);
            writeSections(r, dos);
        }
    }

    @Override
    public Resume readResumeFromFile(InputStream is) throws IOException {
        Resume resume = new Resume();
        try (DataInputStream dis = new DataInputStream(is)) {
            resume.setUuid(dis.readUTF());
            resume.setFullName(dis.readUTF());
            resume.setContacts(readContacts(dis));
            resume.setSections(readSections(dis));
        }
        return resume;
    }

    private void writeContacts(Resume r, DataOutputStream dos) throws IOException {
        dos.writeInt(r.getContacts().size());
        for (Map.Entry<ContactType, String> entry :
                r.getContacts().entrySet()) {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        }
    }

    private Map<ContactType, String> readContacts(DataInputStream dis) throws IOException {
        Map<ContactType, String> retMap = new EnumMap<>(ContactType.class);
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            retMap.put(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
        return retMap;
    }

    private void writeSections(Resume r, DataOutputStream dos) throws IOException {
        dos.writeInt(r.getSections().size());
        for (Map.Entry<SectionType, AbstractSection> entry :
                r.getSections().entrySet()) {
            dos.writeUTF(entry.getKey().toString());
            switch (entry.getKey()) {
                case PERSONAL:
                case OBJECTIVE:
                    dos.writeUTF(((TextSection) entry.getValue()).getContent());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    writeSimpleStringList(((ListSection) entry.getValue()).getContent(), dos);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    writeOrganizations(dos, ((OrganizationSection) entry.getValue()).getContent());
                    break;
                default:
            }
        }
    }

    private Map<SectionType, AbstractSection> readSections(DataInputStream dis) {
        Map<SectionType, AbstractSection> retMap = new EnumMap<>(SectionType.class);
        try {
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sec = SectionType.valueOf(dis.readUTF());
                switch (sec) {
                    case PERSONAL:
                    case OBJECTIVE:
                        retMap.put(sec, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        retMap.put(sec, new ListSection(readSimpleStringList(dis)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        retMap.put(sec, new OrganizationSection(readOrganizations(dis)));
                        break;
                    default:
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retMap;
    }

    private void writeOrganizations(DataOutputStream dos, List<Organization> content) throws IOException {
        dos.writeInt(content.size());
        for (Organization org :
                content) {
            dos.writeUTF(org.getOrganizationName().getName());
            dos.writeUTF(org.getOrganizationName().getUrl());
            writePositions(org, dos);
        }
    }

    private List<Organization> readOrganizations(DataInputStream dis) {
        List<Organization> organizations = new ArrayList<>();
        try {
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                organizations.add(new Organization(dis.readUTF(), dis.readUTF(), readPositions(dis)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return organizations;
    }

    private void writePositions(Organization organization, DataOutputStream dos) {
        try {
            dos.writeInt(organization.getPositions().size());
            for (Organization.Position pos :
                    organization.getPositions()) {
                dos.writeUTF(pos.getDateOfStart().toString());
                dos.writeUTF(pos.getDateOfEnd().toString());
                dos.writeUTF(pos.getPosition());
                dos.writeUTF(pos.getInfo());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<Organization.Position> readPositions(DataInputStream dis) {
        List<Organization.Position> positions = new ArrayList<>();
        try {
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                Organization.Position position = new Organization.Position();
                position.setDateOfStart(LocalDate.parse(dis.readUTF()));
                position.setDateOfEnd(LocalDate.parse(dis.readUTF()));
                position.setPosition(dis.readUTF());
                position.setInfo(dis.readUTF());
                positions.add(position);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return positions;
    }

    private void writeSimpleStringList(List<String> content, DataOutputStream dos) {
        try {
            dos.writeInt(content.size());
            for (String st :
                    content) {
                dos.writeUTF(st);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> readSimpleStringList(DataInputStream dis) {
        List<String> retList = new ArrayList<>();
        try {
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                retList.add(dis.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retList;
    }


}
