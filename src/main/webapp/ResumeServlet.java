package main.webapp;

import main.ru.javawebinar.basejava.model.*;
import main.ru.javawebinar.basejava.storage.EmptyOrgSectEntity;
import main.ru.javawebinar.basejava.storage.Storage;
import main.ru.javawebinar.basejava.util.Config;
import main.ru.javawebinar.basejava.util.DateUtil;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResumeServlet extends HttpServlet {

    private Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Resume resume;
        String uuid = request.getParameter("uuid");
        if (isNotEmpty(uuid) && !uuid.equals("createUUID")) {
            resume = new Resume(request.getParameter("uuid"), request.getParameter("fullName"));
        } else {
            resume = new Resume();
            resume.setFullName(request.getParameter("fullName"));
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (isNotEmpty(value)) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (isNotEmpty(value)) {
                AbstractSection section;
                switch (type){
                    case PERSONAL:
                    case OBJECTIVE:
                        section = new TextSection(value);
                        resume.addSection(type, section);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        section = new ListSection(Arrays.asList(value.split("\n")));
                        resume.addSection(type, section);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> orgs = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        String[] values = request.getParameterValues(type.name());
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (isNotEmpty(name)) {
                                List<Organization.Position> positions = new ArrayList<>();
                                String pfx = type.name() + i;
                                String[] startDates = request.getParameterValues(pfx + "startDate");
                                String[] endDates = request.getParameterValues(pfx + "endDate");
                                String[] titles = request.getParameterValues(pfx + "position");
                                String[] descriptions = request.getParameterValues(pfx + "info");
                                for (int j = 0; j < titles.length; j++) {
                                    if (isNotEmpty(titles[j])) {
                                        positions.add(new Organization.Position(
                                                DateUtil.checkStartDateAndSet(startDates[j]),
                                                DateUtil.checkEndDateAndSet(endDates[j]), titles[j], descriptions[j] ));
                                    }
                                }
                                orgs.add(new Organization(new Link(name, urls[i]), positions));
                            }
                        }
                        resume.addSection(type, new OrganizationSection(orgs));
                        break;
                }
            } else {
                resume.getSections().remove(type);
            }
        }
        if (uuid.equals("createUUID") || !isNotEmpty(uuid)){
            storage.save(resume);
        } else {
            storage.update(resume);
        }

        response.sendRedirect("/resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        Resume resume;
        String uuid = request.getParameter("uuid");

        if (isNotEmpty(uuid)) {
            if (uuid.equals("createUUID")){
                resume = new Resume(uuid, "defaultName");
                //storage.save(resume);
            } else{
                resume = storage.get(uuid);
            }
            switch (request.getParameter("action")) {
                case "view":
                    request.setAttribute("resume", resume);
                    getServletContext().getRequestDispatcher("/WEB-INF/jsp/view.jsp").forward(request, response);
                    return;
                case "delete":
                    storage.delete(uuid);
                    break;
                case "edit":
                    request.setAttribute("resume", resume);
                    for (SectionType type :
                            SectionType.values()) {
                        AbstractSection section = resume.getSections().get(type);
                        if (section == null){
                            switch (type){
                                case PERSONAL:
                                case OBJECTIVE:
                                    section = new TextSection("");
                                    break;
                                case ACHIEVEMENT:
                                case QUALIFICATIONS:
                                    section = new ListSection(new ArrayList<>());
                                    break;
                                case EXPERIENCE:
                                case EDUCATION:
                                    section = new OrganizationSection(new ArrayList<>());
                                    break;
                            }
                            resume.addSection(type, section);
                        }
                    }
                    prepareOrganizations(resume);
                    getServletContext().getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
                default:
                    return;
            }
        }
        request.getServletContext();
        request.setAttribute("resumes", storage.getAllSorted());
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
    }

    private void prepareOrganizations(Resume resume) {
        EmptyOrgSectEntity emptyEntity = new EmptyOrgSectEntity();
        OrganizationSection workSect =(OrganizationSection) resume.getSections().get(SectionType.EXPERIENCE);
        OrganizationSection eduSect =(OrganizationSection) resume.getSections().get(SectionType.EDUCATION);
        workSect.getContent().add(emptyEntity.getEmptyOrganization());
        eduSect.getContent().add(emptyEntity.getEmptyOrganization());
        List<Organization> newList = Stream.concat(workSect.getContent().stream(), eduSect.getContent().stream())
                .collect(Collectors.toList());
        for (Organization org : newList) {
            List<Organization.Position> positions = org.getPositions();
            if (positions == null){
                positions = new ArrayList<>();
                org.setPositions(positions);
            } else {
                positions.add(emptyEntity.getEmptyPosition());
            }
        }
    }

    private static boolean isNotEmpty(String test){
        return test != null && test.trim().length() != 0;
    }
}




