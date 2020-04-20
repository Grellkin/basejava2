package main.ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

@XmlRootElement
public class Resume implements Serializable {

    private static final long serialVersionUID = 4L;
    private String uuid;
    private String fullName;
    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public static final Comparator<Resume> comparatorByUuid = Comparator.comparing(Resume::getUuid);
    public static final Comparator<Resume> comparatorByFullNameAndUuid =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public Resume() {
        this(UUID.randomUUID().toString(), "defaultName");
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(fullName, "Full name should be not null");
        Objects.requireNonNull(uuid, "Uuid should be not null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void addContact(ContactType type, String info) {
        contacts.put(type, info);
    }

    public void addSection(SectionType type, AbstractSection section) {
        sections.put(type, section);
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setContacts(Map<ContactType, String> contacts) {
        this.contacts = contacts;
    }

    public void setSections(Map<SectionType, AbstractSection> sections) {
        this.sections = sections;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        if (!fullName.equals(resume.fullName)) return false;
        if (!contacts.equals(resume.contacts)) return false;
        return sections.equals(resume.sections);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + contacts.hashCode();
        result = 31 * result + sections.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", contacts=" + contacts +
                ", sections=" + sections +
                '}';
    }
}
