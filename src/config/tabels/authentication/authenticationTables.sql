
create table if not exists A_USER(
    USER_ID BIGINT NOT NULL ,
    USER_NAME VARCHAR(200) NULL ,
    EMAIL VARCHAR(200) NOT NULL ,
    IS_LIVE INT NOT NULL ,
    CREATED_DATE BIGINT NOT NULL ,
    UPDATE_DATE BIGINT NOT NULL ,

    constraint A_USER PRIMARY KEY (USER_ID)
);

create table if not exists A_SECURITY(
    AUTH_ID BIGINT NOT NULL ,
    USER_ID BIGINT NOT NULL ,
    AUTH_SALT VARCHAR(500) NOT NULL ,
    USER_SIGNATURE VARCHAR(500) NOT NULL ,
    USER_EMAIL_UNIQUE_TOKEN VARCHAR(500) NOT NULL ,
    CREATED_DATE BIGINT NOT NULL ,
    UPDATED_DATE BIGINT NOT NULL ,

    constraint A_SECURITY PRIMARY KEY (AUTH_ID),
    constraint A_SECURITY_FK1 FOREIGN KEY ( USER_ID ) references A_USER( USER_ID ) ON DELETE CASCADE 
);

create table if not exists A_IDENTIFICATION(
    IDENTIFICATION_ID BIGINT NOT NULL ,
    USER_ID BIGINT NOT NULL ,
    IDENTIFICATION_KEY VARCHAR(500) NOT NULL ,
    IS_LIVE INT NOT NULL ,
    CREATED_DATE BIGINT NOT NULL ,

    constraint A_IDENTIFICATION PRIMARY KEY (IDENTIFICATION_ID),
    constraint A_IDENTIFICATION_FK1 FOREIGN KEY ( USER_ID ) references A_USER( USER_ID ) ON DELETE CASCADE 
);

create table if not exists A_USER_PROFILE (
    PROFILE_ID BIGINT NOT NULL ,
    USER_ID BIGINT NOT NULL ,
    CREATED_DATE BIGINT NOT NULL ,
    UPDATED_DATE BIGINT NOT NULL ,

    constraint A_USER_PROFILE PRIMARY KEY (PROFILE_ID),
    constraint A_USER_PROFILE_FK1 FOREIGN KEY ( USER_ID ) references A_USER( USER_ID ) ON DELETE CASCADE 
);

/* create table if not exists USER_AUTHENTICATION ( 

    ID BIGINT NOT NULL ,
    USER_NAME VARCHAR(200) NULL ,
    EMAIL VARCHAR(200) NOT NULL ,
    PASSWORD VARCHAR(1000) NOT NULL ,
    PASSWORD_SALT VARCHAR(50) NULL ,
    PASSWORD_REMINDER_EXPIRY BIGINT NULL ,
    EMAIL_CONFORMATION_TOKEN VARCHAR(100) NULL,
    EMAIL_CONFORMATION INT NOT NULL ,
    CREATED_DATE BIGINT NOT NULL ,
    UPDATE_DATE BIGINT NOT NULL ,
    IS_LIVE INT NOT NULL ,

    constraint USER_AUTHENTICATION PRIMARY KEY (ID)
);

create table if not exists USER_PROFILE ( 

    ID BIGINT NOT NULL,
    USER_AUTH_ID BIGINT NOT NULL ,
    FULL_NAME VARCHAR(400) NULL ,
    FIRST_NAME VARCHAR(200) NULL ,
    LAST_NAME VARCHAR(200) NULL ,
    LANDED_DATE BIGINT NOT NULL ,
    UPDATE_DATE BIGINT NOT NULL ,

    constraint USER_PROFILE PRIMARY KEY (ID) ,
     constraint USER_AUTHENTICATION_FK1 FOREIGN KEY ( USER_AUTH_ID ) references USER_AUTHENTICATION( ID ) 
);
*/
