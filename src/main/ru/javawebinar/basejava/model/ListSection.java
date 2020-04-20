package main.ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListSection extends AbstractSection {

    private static final long serialVersionUID = 4L;
    private List<String> content;

    public ListSection() {
    }

    public ListSection(List<String> content) {
        this.content = content;
    }

    public ListSection(String... content) {
        this.content = Arrays.asList(content);
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String st :
                content) {
            builder.append(st);
            builder.append("\n");
        }

        return builder.toString();
    }
}
