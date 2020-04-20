package main.ru.javawebinar.basejava.model;

import main.ru.javawebinar.basejava.util.DateUtil;
import main.ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {

    private static final long serialVersionUID = 4L;
    private Link organizationName;
    private List<Position> positions;

    public Organization() {
    }

    public Organization(Link homePage, List<Position> positions) {
        this.organizationName = homePage;
        this.positions = positions;
    }

    public Organization(String companyName, String url, Position... positions) {
        this.organizationName = new Link(companyName, url);
        this.positions = Arrays.asList(positions);
    }

    public Organization(String companyName, String url, List<Position> positions) {
        this.organizationName = new Link(companyName, url);
        this.positions = positions;
    }

    public void setOrganizationName(Link organizationName) {
        this.organizationName = organizationName;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public Link getOrganizationName() {
        return organizationName;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(organizationName, that.organizationName) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationName, positions);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "organizationName=" + organizationName +
                ", positions=" + positions +
                '}';
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        protected LocalDate dateOfStart;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        protected LocalDate dateOfEnd;
        protected String position;
        protected String info;

        public Position() {
        }

        public Position(int startYear, Month startMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.FUTURE, title, description);
        }

        public Position(LocalDate dateOfStart, LocalDate dateOfEnd, String position, String info) {
            Objects.requireNonNull(dateOfStart);
            Objects.requireNonNull(dateOfEnd);
            Objects.requireNonNull(position);
            this.dateOfStart = dateOfStart;
            this.dateOfEnd = dateOfEnd;
            this.position = position;
            this.info = info;
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), title, description);
        }

        public void setDateOfStart(LocalDate dateOfStart) {
            this.dateOfStart = dateOfStart;
        }

        public void setDateOfEnd(LocalDate dateOfEnd) {
            this.dateOfEnd = dateOfEnd;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getPosition() {
            return position;
        }

        public LocalDate getDateOfStart() {
            return dateOfStart;
        }

        public LocalDate getDateOfEnd() {
            return dateOfEnd;
        }

        public String getInfo() {
            return info;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position1 = (Position) o;
            return Objects.equals(dateOfStart, position1.dateOfStart) &&
                    Objects.equals(dateOfEnd, position1.dateOfEnd) &&
                    Objects.equals(position, position1.position) &&
                    Objects.equals(info, position1.info);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dateOfStart, dateOfEnd, position, info);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "dateOfStart=" + dateOfStart +
                    ", dateOfEnd=" + dateOfEnd +
                    ", position='" + position + '\'' +
                    ", info='" + info + '\'' +
                    '}';
        }
    }
}


