-- there are 11 tables in this database: jdbc:oracle:thin:@10.105.16.85:1521:CFCAPRE
drop table IBSACCESSLOG cascade constraints;
create table IBSACCESSLOG
(
    ACCESSJNLNO  varchar2(64) not null,
    ACCESSSTATE  char(1)   not null,
    ACCESSDATE   timestamp not null,
    MACHINEID    varchar2(255),
    ACCESSIP     varchar2(32),
    MODULEID     varchar2(16) not null,
    CHANNELID    varchar2(16) not null,
    LOGINTYPE    char(1)   not null,
    USERSEQ      number(20,0),
    UNIQUEUSERID varchar2(64),
    USERAGENT    varchar2(256),
    primary key (ACCESSJNLNO, ACCESSSTATE)
);
drop table IBSACCESSLOGHIST cascade constraints;
create table IBSACCESSLOGHIST
(
    ACCESSJNLNO  varchar2(64) not null,
    ACCESSSTATE  char(1)   not null,
    ACCESSDATE   timestamp not null,
    MACHINEID    varchar2(255),
    ACCESSIP     varchar2(32),
    MODULEID     varchar2(16) not null,
    CHANNELID    varchar2(16) not null,
    LOGINTYPE    char(1)   not null,
    USERSEQ      number(20,0),
    UNIQUEUSERID varchar2(64),
    USERAGENT    varchar2(256),
    primary key (ACCESSJNLNO, ACCESSSTATE)
);
drop table IBSJNL cascade constraints;
create table IBSJNL
(
    JNLNO           varchar2(64) not null,
    CIFSEQ          number(20,0) not null,
    USERSEQ         number(20,0) not null,
    MODULEID        varchar2(16) not null,
    CHANNELID       varchar2(16) not null,
    LOGINTYPE       char(1),
    TRANSCODE       varchar2(64) not null,
    TRANSDATE       timestamp not null,
    TRANSTIME       timestamp not null,
    RETURNTIME      timestamp,
    ACNO            varchar2(40),
    BANKACTYPE      varchar2(4),
    ACNAME          varchar2(128),
    PAYERACNO       varchar2(40),
    PAYEEACNO       varchar2(40),
    AMOUNT          number(15,2),
    CURRENCY        varchar2(3),
    LOCALADDR       varchar2(32),
    JNLSTATE        char(1)   not null,
    RETURNCODE      varchar2(64),
    RETURNMSG       varchar2(128),
    REMOTEJNLNO     number(20,0),
    REMOTETRANSCODE varchar2(64),
    primary key (JNLNO)
);
drop table IBSJNLHIST cascade constraints;
create table IBSJNLHIST
(
    JNLNO           varchar2(64) not null,
    CIFSEQ          number(20,0) not null,
    USERSEQ         number(20,0) not null,
    MODULEID        varchar2(16) not null,
    CHANNELID       varchar2(16) not null,
    LOGINTYPE       char(1),
    TRANSCODE       varchar2(64) not null,
    TRANSDATE       timestamp not null,
    TRANSTIME       timestamp not null,
    RETURNTIME      timestamp,
    ACNO            varchar2(40),
    BANKACTYPE      varchar2(4),
    ACNAME          varchar2(128),
    PAYERACNO       varchar2(40),
    PAYEEACNO       varchar2(40),
    AMOUNT          number(15,2),
    CURRENCY        varchar2(3),
    LOCALADDR       varchar2(32),
    JNLSTATE        char(1)   not null,
    RETURNCODE      varchar2(64),
    RETURNMSG       varchar2(128),
    REMOTEJNLNO     number(20,0),
    REMOTETRANSCODE varchar2(64),
    primary key (JNLNO)
);
drop table MCMENU cascade constraints;
create table MCMENU
(
    MENUID      varchar2(50) not null,
    NAME        varchar2(50) not null,
    MODULEID    varchar2(50) not null,
    PARENTID    varchar2(50) not null,
    MENULEVEL   varchar2(50) not null,
    PATH        varchar2(100),
    ROLE        varchar2(200),
    IMAGE       varchar2(100),
    SHOWLEVEL   varchar2(10),
    LIMITED_IND char(1)
);
drop table PRODUCTTRS cascade constraints;
create table PRODUCTTRS
(
    PRDID     varchar2(64) not null,
    TRANSCODE varchar2(64) not null,
    MODULEID  varchar2(50) not null,
    primary key (PRDID, TRANSCODE, MODULEID)
);
drop table RULE cascade constraints;
create table RULE
(
    MODULEID      varchar2(16) not null,
    RULENAMESPACE varchar2(64) not null,
    RULETYPE      varchar2(16) not null,
    RULEID        varchar2(64) not null,
    RULEDEF       varchar2(2048),
    RULESCRIPT    varchar2(2048),
    PRDID         varchar2(64),
    DESCRIPTION   varchar2(256),
    LASTUPDUSER   number(20,0),
    LASTUPDTIME   timestamp,
    primary key (MODULEID, RULENAMESPACE, RULETYPE, RULEID)
);
drop table SCHEDULETASK cascade constraints;
create table SCHEDULETASK
(
    TASKID         varchar2(5) not null,
    TASKNAME       varchar2(32) not null,
    TASKSTATUS     char(1) not null,
    CRONEXPRESSION varchar2(32) not null,
    CONCURRENT     char(1) not null,
    DESCRIPTION    varchar2(100),
    TASKGROUP      varchar2(32),
    TARGETOBJECT   varchar2(100) not null,
    TARGETMETHOD   varchar2(32) not null,
    STARTTIME      timestamp,
    primary key (TASKID)
);
drop table SCHEDULETASKLOG cascade constraints;
create table SCHEDULETASKLOG
(
    TASKUUID   varchar2(32) not null,
    CREATETIME timestamp,
    TASKID     varchar2(5) not null,
    TASKNAME   varchar2(32) not null,
    STATUS     varchar2(10) not null,
    REJCODE    varchar2(32),
    REJMSG     varchar2(300),
    primary key (TASKUUID)
);
drop table SCHEDULETASK_MAN cascade constraints;
create table SCHEDULETASK_MAN
(
    TASKID         varchar2(5) not null,
    TASKNAME       varchar2(32) not null,
    TASKSTATUS     char(1) not null,
    CRONEXPRESSION varchar2(32) not null,
    CONCURRENT     char(1) not null,
    DESCRIPTION    varchar2(100),
    TASKGROUP      varchar2(32),
    TARGETOBJECT   varchar2(100) not null,
    TARGETMETHOD   varchar2(32) not null,
    STARTTIME      timestamp,
    primary key (TASKID)
);
drop table SPEEDMENU cascade constraints;
create table SPEEDMENU
(
    USERSEQ   number(20,0) not null,
    MENUID    varchar2(50) not null,
    CREATTIME varchar2(50),
    ICONID    varchar2(50),
    FLAG      varchar2(10) not null,
    MENUNAME  varchar2(50)
);
