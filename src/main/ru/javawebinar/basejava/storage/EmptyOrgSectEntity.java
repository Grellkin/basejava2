package main.ru.javawebinar.basejava.storage;

import main.ru.javawebinar.basejava.model.Link;
import main.ru.javawebinar.basejava.model.Organization;
import main.ru.javawebinar.basejava.model.OrganizationSection;

import java.util.ArrayList;
import java.util.List;

public class EmptyOrgSectEntity {
    public OrganizationSection section = new OrganizationSection();
    public List<Organization.Position> positionList;
    public Link link;
    private List<Organization> listOrg;
    Organization organization;
    public EmptyOrgSectEntity(){
        listOrg = new ArrayList<>();
        section.setContent(listOrg);
    }

    public void reInit(){
        organization = new Organization();
        listOrg.add(organization);
        link = new Link();
        organization.setOrganizationName(link);
        positionList = new ArrayList<>();
        organization.setPositions(positionList);

    }

    public Organization getEmptyOrganization(){
        reInit();
        return organization;
    }

    public Organization.Position getEmptyPosition(){
        return new Organization.Position();
    }

}
