create table if not exists resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text     not null
);

create table if not exists contact
(
    id          serial   not null
        constraint contact_pk
            primary key,
    type        text     not null,
    value       text     not null,
    resume_uuid varchar(36) not null
        constraint contact_resume_uuid_fk
            references resume
            on update restrict on delete cascade
);

create unique index if not exists contact_uuid_type_index
    on contact (resume_uuid, type);


create table organization
(
    organization_name varchar(50) not null
        constraint organization_pk
            primary key,
    url               varchar(50)
);

create unique index organization_organization_name_uindex
    on organization (organization_name);

create table position
(
    position          varchar(30)               not null,
    start_date        date default CURRENT_DATE not null,
    end_date          date,
    organization_name varchar(50)               not null
        constraint position__org_name_fk
            references organization
            on update cascade on delete restrict,
    resume_uuid       varchar(36)               not null
        constraint position_resume_uuid_fk
            references resume
            on update restrict on delete cascade,
    type              varchar(15)               not null,
    posit_id          serial                    not null
        constraint position_pk
            primary key,
    info              text
);

create table text_section
(
    type        varchar(20) not null,
    info        text,
    resume_uuid varchar(36) not null
        constraint text_section__resume_uuid_fk
            references resume
            on update restrict on delete cascade,
    id          serial      not null
        constraint text_section_pk
            primary key
);

create unique index text_section_uuid_and_type_index
    on text_section (resume_uuid, type);

