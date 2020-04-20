package main.ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationSection extends AbstractSection {

    private static final long serialVersionUID = 4L;
    private List<Organization> content;

    public OrganizationSection() {
    }

    public OrganizationSection(List<Organization> content) {
        this.content = content;
    }

    public OrganizationSection(Organization... organizations) {
        this.content = Arrays.asList(organizations);
    }

    public List<Organization> getContent() {
        return content;
    }

    public void setContent(List<Organization> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "content=" + content +
                '}';
    }
}
