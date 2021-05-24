-- there are 214 tables in this database: jdbc:oracle:thin:@10.105.16.85:1521:CFCAPRE
drop table ADVERTINFO cascade constraints;
create table ADVERTINFO
(
    ADVERTNAME  varchar2(30) not null,
    SORTID      number(20,0),
    ADVERTURL   varchar2(128),
    ADVERTIMAGE varchar2(256),
    NOTE        varchar2(300),
    CREATETIME  varchar2(20),
    STARTDATE   varchar2(20),
    JNLNO       varchar2(10),
    UPDATETIME  timestamp
);
drop table BANKHOLIDAY cascade constraints;
create table BANKHOLIDAY
(
    HOLIDAYDATE timestamp not null,
    HOLIDAYTYPE char(1)   not null,
    CURRENCY    varchar2(20),
    primary key (HOLIDAYDATE, HOLIDAYTYPE)
);
drop table BANKPROD cascade constraints;
create table BANKPROD
(
    PRDID              varchar2(64) not null,
    MODULEID           varchar2(16) not null,
    PRDNAME            varchar2(64),
    PRDSTATE           char(1) not null,
    MGMTPRDFLAG        char(1),
    PRDCHECKFLAG       char(1) not null,
    PRDAUTHFLAG        char(1) not null,
    PRDRELEASEFLAG     char(1) not null,
    CHECKTYPE          char(1),
    CHECKDEPT          char(1),
    CHECKDIV           number(20,0),
    PRDOTPFLAG         char(1),
    PRDDIGITSIGNFLAG   char(1),
    PRDTRSPASSWORDFLAG char(1),
    ACFLAG             char(1),
    LIMITFLAG          char(1),
    FEEFLAG            char(1),
    BANKLIMITFLAG      char(1),
    REPORTFLAG         char(1),
    FINANCEFLAG        char(1),
    CREATEUSERSEQ      number(20,0),
    CREATEDEPTSEQ      number(20,0),
    CREATETIME         timestamp,
    UPDATEUSERSEQ      number(20,0),
    UPDATEDEPTSEQ      number(20,0),
    UPDATETIME         timestamp,
    primary key (PRDID)
);
drop table CHANNEL cascade constraints;
create table CHANNEL
(
    CHANNELID      varchar2(4) not null,
    CHANNELNAME    varchar2(32) not null,
    CHANNELSTATE   char(1) not null,
    SERVICECHANNEL char(1),
    primary key (CHANNELID)
);
drop table COUNTRYINFO cascade constraints;
create table COUNTRYINFO
(
    CODE   varchar2(64) not null,
    NAMECN varchar2(255),
    NAMEEN varchar2(255) not null,
    INDEXS number(38,0) not null
);
drop table DAILY_INCREMENT cascade constraints;
create table DAILY_INCREMENT
(
    INC_TYPE         varchar2(50) not null,
    APPLICATION_DATE varchar2(10) not null,
    MAX_VAL          number(0,-127) not null,
    SEED             number(0,-127) not null,
    STEP             number(0,-127) not null,
    LAST_UPDATE_TMS  timestamp not null,
    primary key (INC_TYPE, APPLICATION_DATE)
);
drop table DEPT cascade constraints;
create table DEPT
(
    DEPTSEQ            number(20,0) not null,
    DEPTID             varchar2(32) not null,
    DEPTNAME           varchar2(64) not null,
    ADDR               varchar2(128),
    ZIPCODE            char(1),
    PHONE              varchar2(32),
    EMAIL              varchar2(128),
    DEPTSTATE          char(1) not null,
    DEPTTYPE           char(1) not null,
    DEPTLEVEL          char(1) not null,
    EXTLEVEL           char(1),
    DEPTCLASS          char(1),
    COMNUM             varchar2(12),
    PARENTDEPTSEQ      number(0,-127),
    CITYCODE           varchar2(16),
    PROVCD             varchar2(16),
    LAT                varchar2(30),
    LON                varchar2(30),
    AREA               varchar2(10),
    DETAILADDRESS      varchar2(200),
    TRADEAUTOMATICCODE varchar2(100),
    BANKCODE           varchar2(50),
    primary key (DEPTSEQ)
);
drop table DEPTEXT cascade constraints;
create table DEPTEXT
(
    DEPTSEQ          number(20,0) not null,
    EXCHNO           varchar2(16),
    PAYNO            varchar2(32),
    PAYFLAG          char(1),
    CONTACTNAME      varchar2(150),
    CONTACTTEL       varchar2(32),
    CONTACTMOBILE    varchar2(16),
    PRODSET          varchar2(64),
    REMARK           varchar2(512),
    WORKDAYBEGINTIME varchar2(5),
    WORKDAYENDTIME   varchar2(5),
    HOLIDAYSTARTTIME varchar2(5),
    HOLIDAYENDTIME   varchar2(5),
    primary key (DEPTSEQ)
);
drop table DEPTMERGE cascade constraints;
create table DEPTMERGE
(
    DEPTSEQ        number(20,0) not null,
    NEWDEPTSEQ     number(20,0),
    MERGEDATE      varchar2(10),
    CREATETIME     timestamp,
    BATCHSTARTTIME timestamp,
    CREATEUSERSEQ  number(20,0),
    CREATEDEPTSEQ  number(20,0),
    MERGEFLAG      char(1),
    primary key (DEPTSEQ)
);
drop table DEPTPROD cascade constraints;
create table DEPTPROD
(
    DEPTSEQ number(20,0) not null,
    PRDID   varchar2(64) not null,
    primary key (DEPTSEQ, PRDID)
);
drop table DEPTPRODGROUP cascade constraints;
create table DEPTPRODGROUP
(
    PRODGROUPNAME varchar2(64) not null,
    DEPTSEQ       number(20,0) not null,
    MODULEID      varchar2(16) not null,
    CHANNELID     varchar2(4) not null,
    DEFAULTFLAG   varchar2(1),
    primary key (PRODGROUPNAME, DEPTSEQ, MODULEID, CHANNELID)
);
drop table DEPTPRODGROUPPROD cascade constraints;
create table DEPTPRODGROUPPROD
(
    PRDID         varchar2(64) not null,
    PRODGROUPNAME varchar2(64) not null,
    DEPTSEQ       number(20,0) not null,
    MODULEID      varchar2(16) not null,
    CHANNELID     varchar2(4) not null,
    primary key (PRDID, PRODGROUPNAME, DEPTSEQ, MODULEID, CHANNELID)
);
drop table DETAILQUERYINFO cascade constraints;
create table DETAILQUERYINFO
(
    DETAILJNLNO             number(20,0) not null,
    CORENO                  varchar2(128),
    CORENOP                 varchar2(128),
    DOCUMENTDATE            varchar2(10),
    ACCOUNT                 varchar2(128) not null,
    CLOSINGAVAILABLEBALANCE varchar2(128),
    CLOSINGBALANCE          varchar2(128),
    CLOSINGBALANCENAT       varchar2(128),
    CREDITTURNOVER          varchar2(128),
    CREDITTURNOVERNAT       varchar2(128),
    CURRCODE                varchar2(64),
    DEBETTURNOVER           varchar2(128),
    DEBETTURNOVERNAT        varchar2(128),
    OPENINGBALANCE          varchar2(128),
    OPENINGBALANCENAT       varchar2(128),
    STATEMENTDATE           varchar2(10) not null,
    STATEMENTTYPE           varchar2(255),
    UUID                    varchar2(64) not null,
    ACNAME                  varchar2(256),
    CUSNAMEEN               varchar2(256),
    primary key (DETAILJNLNO, ACCOUNT, STATEMENTDATE)
);
drop table DETAILQUERYLOG cascade constraints;
create table DETAILQUERYLOG
(
    DETAILJNLNO          number(20,0) not null,
    ACNO                 varchar2(40) not null,
    BDATE                varchar2(8) not null,
    EDATE                varchar2(8) not null,
    CIFSEQ               number(20,0) not null,
    FLG                  char(1) not null,
    USERSEQ              number(20,0) not null,
    DATETIMEWITHTIMEZONE timestamp with timezont,
    PARENTJNLNO          number(20,0),
    QUERYCIFSEQ          number(20,0),
    QUERYUSERSEQ         number(20,0),
    FLAG                 number(0,-127) not null,
    ISPDF                number(0,-127),
    primary key (DETAILJNLNO)
);
drop table DETAILQUERYRECEIVE cascade constraints;
create table DETAILQUERYRECEIVE
(
    DETAILJNLNO         number(20,0) not null,
    TYPE                varchar2(32) not null,
    ACCEPTDATE          varchar2(128),
    ACCEPTDOCDATE       varchar2(128),
    DOCREF              varchar2(128),
    SAVEDDOCREF         varchar2(128),
    ACCEPTNUMBER        varchar2(128),
    ACCEPTOPERTYPE      varchar2(128),
    ACCEPTPARTNUMBER    varchar2(128),
    ACCEPTRESTAMOUNT    varchar2(128),
    AMOUNT              varchar2(128),
    AMOUNTNAT           varchar2(128),
    BANKOFFICIALS       varchar2(128),
    CBCCODE             varchar2(128),
    CASHSYMBOL          varchar2(128),
    CURRCODE            varchar2(128),
    DOCDATEPARAM        varchar2(128),
    DOCNUMPARAM         varchar2(128),
    DOCUMENTDATE        varchar2(10),
    DOCUMENTNUMBER      varchar2(128),
    GROUND              varchar2(255),
    OKATOCODE           varchar2(128),
    OPERTYPE            varchar2(128),
    PAYGRNDPARAM        varchar2(128),
    PAYTYPEPARAM        varchar2(128),
    PAYUNTIL            varchar2(128),
    PAYER               varchar2(256),
    PAYERACCOUNT        varchar2(128),
    PAYERBIC            varchar2(128),
    PAYERBANKNAME       varchar2(128),
    PAYERCORRACCOUNT    varchar2(128),
    PAYERINN            varchar2(128),
    PAYERKPP            varchar2(128),
    PAYMENTURGENT       varchar2(128),
    RECEIVER            varchar2(255),
    RECEIVERACCOUNT     varchar2(128),
    RECEIVERBIC         varchar2(128),
    RECEIVERBANKNAME    varchar2(128),
    RECEIVERCORRACCOUNT varchar2(128),
    RECEIVERINN         varchar2(128),
    RECEIVERKPP         varchar2(128),
    SENDTYPE            varchar2(128),
    STAT1256            varchar2(128),
    TAXPERIODPARAM      varchar2(128),
    RDOCREF             varchar2(128),
    UUID                varchar2(64) not null,
    VALUEDATE           varchar2(128)
);
drop table DICT_CBR_ACCOUNT cascade constraints;
create table DICT_CBR_ACCOUNT
(
    RBIC                    varchar2(50),
    CBR_ACCOUNT             varchar2(50),
    REGULATION_ACCOUNT_TYPE varchar2(20),
    CK                      varchar2(20),
    CBR_BIC                 varchar2(50),
    DATE_IN                 varchar2(20),
    ACCOUNT_STATUS          varchar2(20)
);
drop table DICT_RBIC_ACTION cascade constraints;
create table DICT_RBIC_ACTION
(
    ACTION_SEQ    number(0,-127),
    ACTION_NO     varchar2(50),
    ACTION_DATE   varchar2(50),
    ACTION_BY     varchar2(50),
    BUSINESS_DAY  varchar2(20),
    CREATE_REASON varchar2(100),
    CREATE_TMS    varchar2(50),
    ACTION_TYPE   varchar2(20),
    BIC_VERSION   varchar2(20)
);
drop table DICT_RBIC_ENTRY cascade constraints;
create table DICT_RBIC_ENTRY
(
    ACTION_SEQ         number(0,-127),
    RBIC               varchar2(50),
    ENTRY_NAME         varchar2(500),
    CNTR_CD            varchar2(50),
    RGN                varchar2(50),
    IND                varchar2(50),
    TNP                varchar2(50),
    NNP                varchar2(50),
    ADR                varchar2(500),
    DATE_IN            varchar2(50),
    PT_TYPE            varchar2(50),
    SRVCS              varchar2(50),
    XCH_TYPE           varchar2(50),
    UUID               varchar2(50),
    PARTICIPANT_STATUS varchar2(50),
    ENTRY_NAME_RU      varchar2(500)
);
drop table DICT_RU_EN_TRANSLATION cascade constraints;
create table DICT_RU_EN_TRANSLATION
(
    RUSSIAN varchar2(25) not null,
    ENGLISH varchar2(25) not null,
    primary key (RUSSIAN, ENGLISH)
);
drop table DP_JTFCANCEL cascade constraints;
create table DP_JTFCANCEL
(
    JNL_NO        number(20,0) not null,
    PARENT_JNL_NO number(20,0) not null,
    PROCESS_STATE varchar2(8) not null,
    PROCESS_TIME  timestamp not null,
    CIF_SEQ       number(10,0) not null,
    USER_SEQ      number(10,0) not null,
    REMARK        varchar2(512) not null,
    CODE          varchar2(100) not null,
    primary key (JNL_NO)
);
drop table DP_JTFCANCEL_HIST cascade constraints;
create table DP_JTFCANCEL_HIST
(
    JNL_NO        number(20,0) not null,
    PARENT_JNL_NO number(20,0) not null,
    PROCESS_STATE varchar2(8) not null,
    PROCESS_TIME  timestamp not null,
    CIF_SEQ       number(10,0) not null,
    USER_SEQ      number(10,0) not null,
    REMARK        varchar2(512) not null,
    CODE          varchar2(100) not null,
    primary key (JNL_NO)
);
drop table DP_JTFOPENING cascade constraints;
create table DP_JTFOPENING
(
    JNL_NO              number(20,0) not null,
    TRANS_SEQ           varchar2(40) not null,
    SVC_ID              varchar2(64) not null,
    PROCESS_STATE       varchar2(8) not null,
    PROCESS_TIME        timestamp with timezont not null,
    TRANS_STATE         varchar2(20) not null,
    CIF_SEQ             number(20,0) not null,
    USER_SEQ            number(20,0) not null,
    DP_PROD_SEQ         number(3,0) not null,
    PAYER_ACCOUNT_NO    varchar2(40) not null,
    DEPOSIT_ACCOUNT_NO  varchar2(40),
    PAYEE_ACCOUNT_NO    varchar2(40) not null,
    PAYER_AMOUNT        number(0,-127) not null,
    START_DATE          timestamp with timezont not null,
    END_DATE            timestamp with timezont,
    VALUE_DATE          timestamp with timezont,
    APPLICATION_DATE    timestamp with timezont,
    PERIOD_DAYS         number(10,0),
    RATE                number(16,5) not null,
    CALCULATION_METHOD  varchar2(40) not null,
    INTEREST_PAY_METHOD varchar2(40) not null,
    AGREEMENT_NUMBER    varchar2(100) not null,
    AGREEMENT_DATE      varchar2(50) not null,
    ON_DEMAND_RATE      number(16,5),
    RATE_REQUEST_ID     varchar2(50),
    APPLICATION_DOC     varchar2(500),
    AVAILABLE_AMOUNT    number(0,-127) not null,
    primary key (JNL_NO)
);
drop table DP_JTFOPENING_HIST cascade constraints;
create table DP_JTFOPENING_HIST
(
    JNL_NO              number(20,0) not null,
    TRANS_SEQ           varchar2(40) not null,
    SVC_ID              varchar2(64) not null,
    PROCESS_STATE       varchar2(8) not null,
    PROCESS_TIME        timestamp not null,
    TRANS_STATE         varchar2(20) not null,
    CIF_SEQ             number(20,0) not null,
    USER_SEQ            number(20,0) not null,
    DP_PROD_SEQ         number(3,0) not null,
    PAYER_ACCOUNT_NO    varchar2(40) not null,
    DEPOSIT_ACCOUNT_NO  varchar2(40),
    PAYEE_ACCOUNT_NO    varchar2(40) not null,
    PAYER_AMOUNT        number(0,-127) not null,
    START_DATE          timestamp with timezont not null,
    END_DATE            timestamp with timezont,
    VALUE_DATE          timestamp with timezont,
    APPLICATION_DATE    timestamp with timezont,
    PERIOD_DAYS         number(10,0),
    RATE                number(16,2) not null,
    CALCULATION_METHOD  varchar2(40) not null,
    INTEREST_PAY_METHOD varchar2(40) not null,
    AGREEMENT_NUMBER    varchar2(100) not null,
    AGREEMENT_DATE      varchar2(50) not null,
    ON_DEMAND_RATE      number(16,5),
    RATE_REQUEST_ID     varchar2(50),
    APPLICATION_DOC     varchar2(500),
    AVAILABLE_AMOUNT    number(0,-127) not null
);
drop table DP_JTFWITHDRAW cascade constraints;
create table DP_JTFWITHDRAW
(
    JNL_NO            number(20,0) not null,
    TRANS_SEQ         varchar2(40) not null,
    OPENING_TRANS_SEQ varchar2(40) not null,
    SVC_ID            varchar2(64) not null,
    PROCESS_STATE     varchar2(8) not null,
    TRANS_STATE       varchar2(20) not null,
    CIF_SEQ           number(10,0) not null,
    USER_SEQ          number(10,0) not null,
    PAYEE_ACCOUNT_NO  varchar2(40) not null,
    WITHDRAW_TYPE     varchar2(40) not null,
    WITHDRAW_AMOUNT   number(24,6) not null,
    WITHDRAW_DATE     timestamp with timezont not null,
    PROCESS_TIME      timestamp not null,
    primary key (JNL_NO)
);
drop table DP_JTFWITHDRAW_HIST cascade constraints;
create table DP_JTFWITHDRAW_HIST
(
    JNL_NO            number(20,0) not null,
    TRANS_SEQ         varchar2(40) not null,
    OPENING_TRANS_SEQ varchar2(40) not null,
    SVC_ID            varchar2(64) not null,
    PROCESS_STATE     varchar2(8) not null,
    TRANS_STATE       varchar2(20) not null,
    CIF_SEQ           number(10,0) not null,
    USER_SEQ          number(10,0) not null,
    PAYEE_ACCOUNT_NO  varchar2(40) not null,
    WITHDRAW_TYPE     varchar2(40) not null,
    WITHDRAW_AMOUNT   number(24,6) not null,
    WITHDRAW_DATE     timestamp with timezont not null,
    PROCESS_TIME      timestamp not null
);
drop table DP_PROD cascade constraints;
create table DP_PROD
(
    SEQ                 number(3,0) not null,
    TP_SEQ              number(3,0) not null,
    CURRENCY            varchar2(10) not null,
    MIN_OPEN_AMOUNT     number(24,6) not null,
    MAX_OPEN_AMOUNT     number(24,6) not null,
    MIN_WITHDRAW_AMOUNT number(24,6) not null,
    TONER_LIMITATION    varchar2(50) not null,
    TONER_EXPRESS       varchar2(100),
    NOTIFICATION_DAYS   number(3,0) not null,
    ACTIVE_IND          varchar2(10) not null,
    TONER_UNIT          varchar2(10),
    MIN_DEPOSIT_BALANCE number(24,6),
    primary key (SEQ)
);
drop table DP_PRODTP cascade constraints;
create table DP_PRODTP
(
    SEQ                     number(3,0) not null,
    PROD_TYPE               varchar2(100) not null,
    NAME                    varchar2(100) not null,
    NAME_CN                 varchar2(100) not null,
    DEF_MIN_OPEN_AMOUNT     number(24,6) not null,
    DEF_MAX_OPEN_AMOUNT     number(24,6) not null,
    DEF_MIN_WITHDRAW_AMOUNT number(24,6) not null,
    SUPPORT_WEEKEND_DAY     number(1,0) not null,
    DEF_MIN_DEPOSIT_BALANCE number(24,6),
    primary key (SEQ)
);
drop table DP_USRPROD cascade constraints;
create table DP_USRPROD
(
    CIF_SEQ  number(20,0) not null,
    PROD_SEQ number(3,0) not null
);
drop table ECACCT cascade constraints;
create table ECACCT
(
    CIFSEQ        number(20,0),
    ACSEQ         number(20,0) not null,
    DEPTSEQ       number(20,0),
    BANKACTYPE    varchar2(4) not null,
    BANKACSUBTYPE varchar2(5),
    ACNO          varchar2(40) not null,
    ACNAME        varchar2(150) not null,
    ACORDER       number(0,-127),
    CURRENCY      varchar2(3),
    CRFLAG        char(1),
    ASSOCIFACFLAG char(1),
    ASSOCIFLEVEL  char(1),
    CORECIFNO     varchar2(40),
    ACALIAS       varchar2(150),
    ACSTATE       char(1) not null,
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    MAJORCARDFLAG char(1),
    ACOWNERID     varchar2(10),
    primary key (ACSEQ)
);
drop table ECACCTGROUPRELATION cascade constraints;
create table ECACCTGROUPRELATION
(
    ACSEQ         number(20,0) not null,
    CIFSEQ        number(20,0) not null,
    PARENTCIFSEQ  number(20,0) not null,
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp
);
drop table ECACCTGROUPRELATIONHIST cascade constraints;
create table ECACCTGROUPRELATIONHIST
(
    MAINTJNLNO      number(20,0),
    MAINTCODE       varchar2(4),
    MAINTMCHANNELID varchar2(16),
    MAINTMCHJNLNO   varchar2(64),
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp,
    MAINTTIMESTAMP  timestamp,
    ACSEQ           number(20,0) not null,
    CIFSEQ          number(20,0) not null,
    PARENTCIFSEQ    number(20,0) not null
);
drop table ECACCTHIST cascade constraints;
create table ECACCTHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    CIFSEQ          number(20,0),
    ACSEQ           number(20,0) not null,
    DEPTSEQ         number(20,0),
    BANKACTYPE      varchar2(4) not null,
    BANKACSUBTYPE   varchar2(5),
    ACNO            varchar2(40) not null,
    ACNAME          varchar2(150) not null,
    ACORDER         number(0,-127),
    CURRENCY        varchar2(3),
    CRFLAG          char(1),
    ASSOCIFACFLAG   char(1),
    ASSOCIFLEVEL    char(1),
    CORECIFNO       varchar2(40),
    ACALIAS         varchar2(150),
    ACSTATE         char(1)   not null
);
drop table ECACCTMCH cascade constraints;
create table ECACCTMCH
(
    ACSEQ         number(20,0) not null,
    MCHANNELID    varchar2(16) not null,
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    OPTIONFLG     varchar2(4),
    primary key (ACSEQ, MCHANNELID)
);
drop table ECACCTMCHHIST cascade constraints;
create table ECACCTMCHHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    ACSEQ           number(20,0) not null,
    MCHANNELID      varchar2(16) not null
);
drop table ECACCTRULE cascade constraints;
create table ECACCTRULE
(
    ACSEQ         number(20,0) not null,
    RULENAMESPACE varchar2(64) not null,
    RULETYPE      varchar2(16) not null,
    RULEID        varchar2(64) not null,
    RULEDEF       varchar2(1024),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (ACSEQ, RULENAMESPACE, RULETYPE, RULEID)
);
drop table ECACCTRULEHIST cascade constraints;
create table ECACCTRULEHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    ACSEQ           number(20,0) not null,
    RULENAMESPACE   varchar2(64) not null,
    RULETYPE        varchar2(16) not null,
    RULEID          varchar2(64) not null,
    RULEDEF         varchar2(1024)
);
drop table ECACESSLOG cascade constraints;
create table ECACESSLOG
(
    ACCESSJNLNO     number(20,0) not null,
    MCHANNELID      varchar2(16) not null,
    LOGINTYPE       char(1)   not null,
    TERMINALTYPE    varchar2(5),
    TERMINALID      varchar2(20),
    DEPTSEQ         number(20,0),
    CIFSEQ          number(20,0),
    USERSEQ         number(20,0),
    USERID          varchar2(128),
    ACNO            varchar2(40),
    BANKACTYPE      varchar2(4),
    ACCESSDATE      timestamp not null,
    ACCESSTIMESTAMP timestamp not null,
    ACCESSIP        varchar2(32),
    JNLSTATE        char(1)   not null,
    RETURNCODE      varchar2(64),
    RETURNMSG       varchar2(1024),
    CHANNELJNLNO    varchar2(64),
    USERAGENT       varchar2(256),
    UUID            varchar2(64),
    primary key (ACCESSJNLNO)
);
drop table ECACESSLOGHIST cascade constraints;
create table ECACESSLOGHIST
(
    ACCESSJNLNO     number(20,0) not null,
    MCHANNELID      varchar2(16) not null,
    LOGINTYPE       char(1)   not null,
    TERMINALTYPE    varchar2(5),
    TERMINALID      varchar2(20),
    DEPTSEQ         number(20,0),
    CIFSEQ          number(20,0),
    USERSEQ         number(20,0),
    USERID          varchar2(128),
    ACNO            varchar2(40),
    BANKACTYPE      varchar2(4),
    ACCESSDATE      timestamp not null,
    ACCESSTIMESTAMP timestamp not null,
    ACCESSIP        varchar2(32),
    JNLSTATE        char(1)   not null,
    RETURNCODE      varchar2(64),
    RETURNMSG       varchar2(1024),
    CHANNELJNLNO    varchar2(64),
    USERAGENT       varchar2(256),
    UUID            varchar2(64),
    primary key (ACCESSJNLNO)
);
drop table ECACLIMIT cascade constraints;
create table ECACLIMIT
(
    CIFSEQ        number(20,0) not null,
    ACNO          varchar2(40) not null,
    MCHANNELID    varchar2(16) not null,
    LOGINTYPE     char(1) not null,
    CURRENCY      varchar2(3) not null,
    CIFLEVEL      varchar2(8) not null,
    PRDID         varchar2(64) not null,
    LIMITTYPE     varchar2(128) not null,
    LMTLEVEL      char(1) not null,
    ACTYPE        varchar2(4) not null,
    DEPTSEQ       varchar2(16) not null,
    LMTAMT        number(15,2),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    CERTTYPE      varchar2(5),
    primary key (CIFSEQ, ACNO, MCHANNELID, LOGINTYPE, CURRENCY, CIFLEVEL, PRDID, LIMITTYPE, LMTLEVEL, ACTYPE, DEPTSEQ)
);
drop table ECACLIMITHIST cascade constraints;
create table ECACLIMITHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp,
    MAINTTIMESTAMP  timestamp,
    CIFSEQ          number(20,0),
    ACNO            varchar2(40),
    MCHANNELID      varchar2(16),
    LOGINTYPE       char(1),
    CURRENCY        varchar2(3),
    CIFLEVEL        varchar2(8),
    PRDID           varchar2(64),
    LIMITTYPE       varchar2(128),
    LMTLEVEL        char(1),
    ACTYPE          varchar2(4),
    DEPTSEQ         varchar2(16),
    LMTAMT          number(15,2)
);
drop table ECACLIMITRT cascade constraints;
create table ECACLIMITRT
(
    CIFSEQ      number(20,0) not null,
    ACNO        varchar2(40) not null,
    LMTID       varchar2(128) not null,
    TRANSDATE   timestamp not null,
    TRANSAMOUNT number(17,2) not null,
    primary key (CIFSEQ, ACNO, LMTID, TRANSDATE)
);
drop table ECCIF cascade constraints;
create table ECCIF
(
    CIFSEQ         number(20,0) not null,
    MODULEID       varchar2(16),
    CIFENGNAME     varchar2(128),
    CIFNAMEPY      varchar2(128),
    CIFNAME        varchar2(150) not null,
    CIFLEVEL       varchar2(8),
    CORECIFLEVEL   varchar2(8),
    COREDEPTSEQ    number(20,0),
    CIFTYPE        varchar2(8),
    CIFCONTROL     char(1),
    CIFMONITOR     char(1),
    CIFEXEMPT      char(1),
    CIFLOANFLG     char(1),
    CIFFINVIPFLG   char(1),
    CCQUERYPWD     varchar2(128),
    TRANSFERTYPE   char(1),
    CIFDEPTSEQ     number(20,0),
    CIFSTATE       char(1),
    ACTIVECODE     varchar2(10),
    CREATEUSERSEQ  number(20,0),
    CREATEDEPTSEQ  number(20,0),
    CREATETIME     timestamp,
    UPDATEUSERSEQ  number(20,0),
    UPDATEDEPTSEQ  number(20,0),
    UPDATETIME     timestamp,
    UPDATEMCHANNEL varchar2(16),
    UPDATEJNLNO    number(20,0),
    UPDATECIFSEQ   number(20,0),
    primary key (CIFSEQ)
);
drop table ECCIFADDR cascade constraints;
create table ECCIFADDR
(
    CIFSEQ        number(20,0) not null,
    ADDRTYPE      varchar2(16) not null,
    ADDRLINE2     varchar2(128),
    ADDRLINE3     varchar2(128),
    ADDRLINE4     varchar2(128),
    ADDRLINE5     varchar2(128),
    ADDR          varchar2(256),
    CITYCODE      varchar2(16),
    CITYAREACODE  varchar2(32),
    STREET        varchar2(64),
    BUILDING      varchar2(32),
    ROOMNO        varchar2(16),
    POSTCODE      varchar2(20),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    ADDRESSCN     varchar2(200),
    EMAIL         varchar2(200),
    TELPHONE      varchar2(200),
    primary key (CIFSEQ, ADDRTYPE)
);
drop table ECCIFADDRHIST cascade constraints;
create table ECCIFADDRHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    CIFSEQ          number(20,0) not null,
    ADDRTYPE        varchar2(16) not null,
    CITYCODE        varchar2(16),
    ADDRLINE2       varchar2(128),
    ADDRLINE3       varchar2(128),
    ADDRLINE4       varchar2(128),
    ADDRLINE5       varchar2(128),
    ADDR            varchar2(256),
    CITYAREACODE    varchar2(32),
    STREET          varchar2(64),
    BUILDING        varchar2(32),
    ROOMNO          varchar2(16),
    POSTCODE        varchar2(20)
);
drop table ECCIFGROUPRELATION cascade constraints;
create table ECCIFGROUPRELATION
(
    CIFSEQ                 number(20,0) not null,
    GROUPLEVEL             varchar2(2) not null,
    PARENTCIFSEQ           number(20,0) not null,
    GROUPTOPCIFSEQ         number(20,0) not null,
    CREATEUSERSEQ          number(20,0),
    CREATEDEPTSEQ          number(20,0),
    CREATETIME             timestamp,
    UPDATEUSERSEQ          number(20,0),
    UPDATEDEPTSEQ          number(20,0),
    UPDATETIME             timestamp,
    PARENTGROUPAUTHFLAG    varchar2(2),
    PARENTGROUPAUTHUSERSEQ varchar2(20),
    primary key (CIFSEQ)
);
drop table ECCIFGROUPRELATIONHIST cascade constraints;
create table ECCIFGROUPRELATIONHIST
(
    MAINTJNLNO      number(20,0),
    MAINTCODE       varchar2(4),
    MAINTMCHANNELID varchar2(16),
    MAINTMCHJNLNO   varchar2(64),
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp,
    MAINTTIMESTAMP  timestamp,
    CIFSEQ          number(20,0) not null,
    GROUPLEVEL      varchar2(2) not null,
    PARENTCIFSEQ    number(20,0) not null,
    GROUPTOPCIFSEQ  number(20,0) not null
);
drop table ECCIFHIST cascade constraints;
create table ECCIFHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    CIFSEQ          number(20,0),
    MODULEID        varchar2(16),
    CIFENGNAME      varchar2(128),
    CIFNAMEPY       varchar2(128),
    CIFNAME         varchar2(150),
    CIFLEVEL        varchar2(8),
    CORECIFLEVEL    varchar2(8),
    COREDEPTSEQ     number(20,0),
    CIFTYPE         varchar2(8),
    CIFCONTROL      char(1),
    CIFMONITOR      char(1),
    CIFEXEMPT       char(1),
    CIFLOANFLG      char(1),
    CIFFINVIPFLG    char(1),
    CCQUERYPWD      varchar2(128),
    TRANSFERTYPE    char(1),
    CIFDEPTSEQ      number(20,0),
    CIFSTATE        char(1),
    UPDATEMCHANNEL  varchar2(16),
    UPDATEJNLNO     number(20,0),
    UPDATECIFSEQ    number(20,0)
);
drop table ECCIFID cascade constraints;
create table ECCIFID
(
    CIFSEQ        number(20,0) not null,
    IDTYPE        varchar2(4) not null,
    IDNO          varchar2(64),
    EFFDATE       timestamp,
    EXPDATE       timestamp,
    PRIMARYFLAG   char(1),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    EXPIRING_BSET varchar2(10),
    EXPIRED_BSET  varchar2(10),
    primary key (CIFSEQ, IDTYPE)
);
drop table ECCIFIDHIST cascade constraints;
create table ECCIFIDHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    CIFSEQ          number(20,0) not null,
    IDTYPE          varchar2(4) not null,
    IDNO            varchar2(32),
    EFFDATE         timestamp,
    EXPDATE         timestamp,
    PRIMARYFLAG     char(1)
);
drop table ECCIFLIMIT cascade constraints;
create table ECCIFLIMIT
(
    CIFSEQ        number(20,0) not null,
    MCHANNELID    varchar2(16) not null,
    LOGINTYPE     char(1) not null,
    PAYERACNO     varchar2(40) not null,
    PAYEEACNO     varchar2(40) not null,
    CURRENCY      varchar2(3) not null,
    CIFLEVEL      varchar2(8) not null,
    PRDID         varchar2(64) not null,
    LIMITTYPE     varchar2(128) not null,
    LMTLEVEL      char(1) not null,
    LMTAMT        number(15,2),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (CIFSEQ, MCHANNELID, LOGINTYPE, PAYERACNO, PAYEEACNO, CURRENCY, CIFLEVEL, PRDID, LIMITTYPE, LMTLEVEL)
);
drop table ECCIFLIMITHIST cascade constraints;
create table ECCIFLIMITHIST
(
    MAINTJNLNO      number(20,0),
    MAINTCODE       varchar2(4),
    MAINTMCHANNELID varchar2(16),
    MAINTMCHJNLNO   varchar2(64),
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp,
    MAINTTIMESTAMP  timestamp,
    CIFSEQ          number(20,0),
    MCHANNELID      varchar2(16),
    LOGINTYPE       char(1),
    PAYERACNO       varchar2(40) not null,
    PAYEEACNO       varchar2(40) not null,
    CURRENCY        varchar2(3),
    CIFLEVEL        varchar2(8),
    PRDID           varchar2(64),
    LIMITTYPE       varchar2(128),
    LMTLEVEL        char(1) not null,
    LMTAMT          number(15,2)
);
drop table ECCIFLIMITRT cascade constraints;
create table ECCIFLIMITRT
(
    CIFSEQ      number(20,0) not null,
    LMTID       varchar2(128) not null,
    TRANSDATE   timestamp not null,
    TRANSAMOUNT number(17,2) not null,
    primary key (CIFSEQ, LMTID, TRANSDATE)
);
drop table ECCIFMCH cascade constraints;
create table ECCIFMCH
(
    CIFSEQ                number(20,0) not null,
    MCHANNELID            varchar2(16) not null,
    FEETIME               timestamp,
    AGREEMENTNO           varchar2(32),
    AGREEDATE             timestamp,
    AGREEDETPTSEQ         number(20,0),
    ACNO                  varchar2(40),
    CMCHANNESTATE         char(1),
    CRMNO                 varchar2(32),
    CRMNAME               varchar2(150),
    CRMTEL                varchar2(32),
    CRMDEPTNO             varchar2(32),
    RERERRERNAME          varchar2(150),
    RERERRERTYPE          char(1),
    REFERRERNO            varchar2(32),
    REFERRERDEPTSEQ       number(20,0),
    RERERRERNO            varchar2(32),
    RERERRERCIFNO         varchar2(32),
    LOCKSTATE             char(1),
    LOCKBEGINDATE         timestamp,
    LOCKENDDATE           timestamp,
    LOCKENDTIME           timestamp,
    LOCKBEGINTIME         timestamp,
    OPENTIME              timestamp,
    CLOSETIME             timestamp,
    OPENUSERSEQ           number(20,0),
    CLOSEUSERSEQ          number(20,0),
    CLOSEREASON           varchar2(32),
    SELFREGFLAG           char(1),
    REOPENFLAG            char(1),
    BATCHFLAG             char(1),
    USEDATE               timestamp,
    USERNO                varchar2(20),
    USERNAME              varchar2(40),
    USERMOBILE            varchar2(16),
    USERTEL               varchar2(32),
    USERDEPTNO            varchar2(60),
    OPENCITY              varchar2(64),
    OPENDEPTSEQ           number(20,0) not null,
    CHECKUSERSEQ          number(20,0),
    CHECKDEPTSEQ          number(20,0),
    CREATEUSERSEQ         number(20,0),
    CREATEDEPTSEQ         number(20,0),
    CREATETIME            timestamp,
    UPDATEUSERSEQ         number(20,0),
    UPDATEDEPTSEQ         number(20,0),
    UPDATETIME            timestamp,
    REGISTERUPDATETIME    timestamp,
    REGISTERUPDATEDEPTSEQ number(22,0),
    CLOSEJNLNO            number(20,0),
    primary key (CIFSEQ, MCHANNELID)
);
drop table ECCIFMCHHIST cascade constraints;
create table ECCIFMCHHIST
(
    MAINTJNLNO            number(20,0) not null,
    MAINTCODE             varchar2(4) not null,
    MAINTMCHANNELID       varchar2(16) not null,
    MAINTMCHJNLNO         varchar2(64) not null,
    MAINTDEPTSEQ          number(20,0),
    MAINTCIFSEQ           number(20,0),
    MAINTUSERSEQ          number(20,0),
    MAINTDATE             timestamp not null,
    MAINTTIMESTAMP        timestamp not null,
    CIFSEQ                number(20,0) not null,
    MCHANNELID            varchar2(16) not null,
    FEETIME               timestamp,
    AGREEMENTNO           varchar2(32),
    AGREEDATE             timestamp,
    AGREEDETPTSEQ         number(20,0),
    ACNO                  varchar2(40),
    CMCHANNESTATE         char(1),
    CRMNO                 varchar2(32),
    CRMNAME               varchar2(150),
    CRMTEL                varchar2(32),
    CRMDEPTNO             varchar2(32),
    RERERRERNAME          varchar2(150),
    RERERRERTYPE          char(1),
    REFERRERNO            varchar2(32),
    REFERRERDEPTSEQ       number(20,0),
    RERERRERNO            varchar2(32),
    RERERRERCIFNO         varchar2(32),
    LOCKSTATE             char(1),
    LOCKBEGINDATE         timestamp,
    LOCKENDDATE           timestamp,
    LOCKENDTIME           timestamp,
    LOCKBEGINTIME         timestamp,
    OPENTIME              timestamp,
    CLOSETIME             timestamp,
    OPENUSERSEQ           number(20,0),
    CLOSEUSERSEQ          number(20,0),
    CLOSEREASON           varchar2(32),
    SELFREGFLAG           char(1),
    REOPENFLAG            char(1),
    BATCHFLAG             char(1),
    USEDATE               timestamp,
    USERNO                varchar2(20),
    USERNAME              varchar2(40),
    USERMOBILE            varchar2(16),
    USERTEL               varchar2(32),
    USERDEPTNO            varchar2(60),
    OPENCITY              varchar2(20),
    OPENDEPTSEQ           number(20,0),
    CHECKUSERSEQ          number(20,0),
    CHECKDEPTSEQ          number(20,0),
    REGISTERUPDATETIME    timestamp,
    REGISTERUPDATEDEPTSEQ number(22,0)
);
drop table ECCIFNUMDESC cascade constraints;
create table ECCIFNUMDESC
(
    CIFSEQ        number(20,0) not null,
    ENUMTYPE      varchar2(16) not null,
    ENUMVALUE     varchar2(64) not null,
    DESCRIPTION   varchar2(128),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (CIFSEQ, ENUMTYPE, ENUMVALUE)
);
drop table ECCIFNUMDESCHIST cascade constraints;
create table ECCIFNUMDESCHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    CIFSEQ          number(20,0) not null,
    ENUMTYPE        varchar2(16) not null,
    ENUMVALUE       varchar2(64) not null,
    DESCRIPTION     varchar2(128)
);
drop table ECCIFPRODSET cascade constraints;
create table ECCIFPRODSET
(
    CIFSEQ        number(20,0) not null,
    PRDSETID      varchar2(64) not null,
    MCHANNELID    varchar2(16),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (CIFSEQ, PRDSETID)
);
drop table ECCIFPRODSETHIST cascade constraints;
create table ECCIFPRODSETHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    CIFSEQ          number(20,0) not null,
    PRDSETID        varchar2(64) not null,
    MCHANNELID      varchar2(16)
);
drop table ECCIFROLE cascade constraints;
create table ECCIFROLE
(
    ROLESEQ       number(20,0) not null,
    CIFSEQ        number(20,0) not null,
    ROLENAME      varchar2(150) not null,
    ROLESTATE     char(1) not null,
    DEFAULTFLAG   char(1),
    ADMINROLEFLAG char(1),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    REMARK        varchar2(50),
    primary key (ROLESEQ)
);
drop table ECCIFROLEAC cascade constraints;
create table ECCIFROLEAC
(
    ROLESEQ       number(20,0) not null,
    ACSEQ         number(20,0) not null,
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (ROLESEQ, ACSEQ)
);
drop table ECCIFROLEACHIST cascade constraints;
create table ECCIFROLEACHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    ROLESEQ         number(20,0) not null,
    ACSEQ           number(20,0) not null
);
drop table ECCIFROLEHIST cascade constraints;
create table ECCIFROLEHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    ROLESEQ         number(20,0) not null,
    CIFSEQ          number(20,0) not null,
    ROLENAME        varchar2(150) not null,
    ROLESTATE       char(1)   not null,
    DEFAULTFLAG     char(1),
    ADMINROLEFLAG   char(1)
);
drop table ECCIFROLEPRD cascade constraints;
create table ECCIFROLEPRD
(
    ROLESEQ       number(20,0) not null,
    PRDID         varchar2(64) not null,
    MAKERIGHT     char(1),
    CHECKRIGHT    char(1),
    AUTHRIGHT     char(1),
    AUTHGROUP     varchar2(4),
    RELEASERIGHT  char(1),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (ROLESEQ, PRDID)
);
drop table ECCIFROLEPRDAC cascade constraints;
create table ECCIFROLEPRDAC
(
    ROLESEQ       number(20,0) not null,
    PRDID         varchar2(64) not null,
    ACSEQ         number(20,0) not null,
    MAKERIGHT     char(1) not null,
    CHECKRIGHT    char(1) not null,
    AUTHRIGHT     char(1) not null,
    AUTHGROUP     varchar2(4),
    RELEASERIGHT  char(1) not null,
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (ROLESEQ, PRDID, ACSEQ)
);
drop table ECCIFROLEPRDACHIST cascade constraints;
create table ECCIFROLEPRDACHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    ROLESEQ         number(20,0) not null,
    PRDID           varchar2(64) not null,
    ACSEQ           number(20,0) not null,
    MAKERIGHT       char(1)   not null,
    CHECKRIGHT      char(1)   not null,
    AUTHRIGHT       char(1)   not null,
    AUTHGROUP       varchar2(4),
    RELEASERIGHT    char(1)   not null
);
drop table ECCIFROLEPRDHIST cascade constraints;
create table ECCIFROLEPRDHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    ROLESEQ         number(20,0) not null,
    PRDID           varchar2(64) not null,
    MAKERIGHT       char(1),
    CHECKRIGHT      char(1),
    AUTHRIGHT       char(1),
    AUTHGROUP       varchar2(4),
    RELEASERIGHT    char(1)
);
drop table ECCIFRULE cascade constraints;
create table ECCIFRULE
(
    CIFSEQ        number(20,0) not null,
    MODULEID      varchar2(16) not null,
    RULENAMESPACE varchar2(64) not null,
    RULEID        varchar2(64) not null,
    RULETYPE      varchar2(16) not null,
    RULEDEF       varchar2(1024),
    RULESCRIPT    varchar2(2048),
    LASTUPDUSER   number(20,0),
    LASTUPDTIME   timestamp,
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (CIFSEQ, MODULEID, RULENAMESPACE, RULEID, RULETYPE)
);
drop table ECCIFRULEHIST cascade constraints;
create table ECCIFRULEHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    CIFSEQ          number(20,0) not null,
    MODULEID        varchar2(16) not null,
    RULENAMESPACE   varchar2(64) not null,
    RULEID          varchar2(64) not null,
    RULETYPE        varchar2(16) not null,
    RULEDEF         varchar2(1024),
    RULESCRIPT      varchar2(2048)
);
drop table ECCIFTEL cascade constraints;
create table ECCIFTEL
(
    CIFSEQ        number(20,0) not null,
    TELTYPE       varchar2(16) not null,
    TELCNCD       varchar2(8),
    TELAREACD     varchar2(5),
    TELNO         varchar2(32),
    TELEXT        varchar2(10),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    TELAUTHFLG    varchar2(2),
    primary key (CIFSEQ, TELTYPE)
);
drop table ECCIFTELHIST cascade constraints;
create table ECCIFTELHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    CIFSEQ          number(20,0) not null,
    TELTYPE         varchar2(16) not null,
    TELCNCD         varchar2(8),
    TELAREACD       varchar2(5),
    TELNO           varchar2(32),
    TELEXT          varchar2(10),
    TELAUTHFLG      varchar2(2)
);
drop table ECDIV cascade constraints;
create table ECDIV
(
    DIVSEQ        number(20,0) not null,
    CIFSEQ        number(20,0) not null,
    DIVID         varchar2(20),
    DIVNAME       varchar2(128) not null,
    DIVSTATE      char(1),
    DIVCODE       varchar2(20),
    DEPTSEQ       varchar2(20),
    DEPTLEVEL     char(1),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (DIVSEQ)
);
drop table ECDIVHIST cascade constraints;
create table ECDIVHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    DIVSEQ          number(20,0) not null,
    CIFSEQ          number(20,0) not null,
    DIVID           varchar2(5),
    DIVNAME         varchar2(128) not null,
    DEPTSEQ         varchar2(20),
    DIVCODE         varchar2(20),
    DEPTLEVEL       char(1),
    DIVSTATE        char(1)
);
drop table ECEMPLOYEE cascade constraints;
create table ECEMPLOYEE
(
    EMPLOYEESEQ   number(20,0) not null,
    EMPLOYEETYPE  varchar2(8) not null,
    EMPLOYEEID    varchar2(32) not null,
    EMPLOYEENAME  varchar2(150),
    TELEPHONE     varchar2(32),
    USERSEQ       number(20,0),
    DEPTSEQ       number(20,0),
    CORETELLERID  varchar2(32),
    DUTY          varchar2(16),
    OFFICE        varchar2(32),
    VIRTUALFLAG   char(1),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (EMPLOYEESEQ)
);
drop table ECEMPLOYEEHIST cascade constraints;
create table ECEMPLOYEEHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    EMPLOYEESEQ     number(20,0) not null,
    EMPLOYEETYPE    varchar2(8) not null,
    EMPLOYEEID      varchar2(32) not null,
    EMPLOYEENAME    varchar2(150),
    TELEPHONE       varchar2(32),
    USERSEQ         number(20,0),
    DEPTSEQ         number(20,0),
    CORETELLERID    varchar2(32),
    DUTY            varchar2(16),
    OFFICE          varchar2(32),
    VIRTUALFLAG     char(1)
);
drop table ECEXTCIFNO cascade constraints;
create table ECEXTCIFNO
(
    CIFSEQ        number(20,0) not null,
    CIFNOTYPE     varchar2(5) not null,
    CIFNO         varchar2(40) not null,
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (CIFSEQ, CIFNOTYPE)
);
drop table ECEXTCIFNOHIST cascade constraints;
create table ECEXTCIFNOHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    CIFSEQ          number(20,0) not null,
    CIFNOTYPE       varchar2(5) not null,
    CIFNO           varchar2(40) not null
);
drop table ECIFBANKINNERPAYEE cascade constraints;
create table ECIFBANKINNERPAYEE
(
    CIFSEQ              number(20,0) not null,
    PAYEENO             varchar2(32) not null,
    PAYEEACNO           varchar2(40) not null,
    PAYEEACNAME         varchar2(150),
    PAYEEACALIAS        varchar2(150),
    EMAIL               varchar2(128),
    MOBILE              varchar2(16),
    PAYERNOTICEFLAG     char(1),
    PAYERBACKNOTICEFLAG char(1),
    LIMITPERTRS         number(15,2),
    LIMITPERDAY         number(15,2),
    PAYEETYPE           char(1),
    USETYPE             char(1) not null,
    PAYEREFFFLAG        char(1),
    EFFDATE             timestamp,
    EXPDATE             timestamp,
    BANKNAME            varchar2(128),
    CREATEUSERSEQ       number(20,0),
    CREATEDEPTSEQ       number(20,0),
    CREATETIME          timestamp,
    UPDATEUSERSEQ       number(20,0),
    UPDATEDEPTSEQ       number(20,0),
    UPDATETIME          timestamp,
    TRSCURRENCY         varchar2(32),
    primary key (CIFSEQ, PAYEENO, USETYPE)
);
drop table ECIFBANKINNERPAYEEHIST cascade constraints;
create table ECIFBANKINNERPAYEEHIST
(
    MAINTJNLNO          number(20,0) not null,
    MAINTCODE           varchar2(4) not null,
    MAINTMCHANNELID     varchar2(16) not null,
    MAINTMCHJNLNO       varchar2(64) not null,
    MAINTDEPTSEQ        number(20,0),
    MAINTCIFSEQ         number(20,0),
    MAINTUSERSEQ        number(20,0),
    MAINTDATE           timestamp not null,
    MAINTTIMESTAMP      timestamp not null,
    CIFSEQ              number(20,0) not null,
    PAYEENO             varchar2(32) not null,
    PAYEEACNO           varchar2(40) not null,
    PAYEEACNAME         varchar2(150),
    PAYEEACALIAS        varchar2(150),
    EMAIL               varchar2(128),
    MOBILE              varchar2(16),
    PAYERNOTICEFLAG     char(1),
    PAYERBACKNOTICEFLAG char(1),
    LIMITPERTRS         number(15,2),
    LIMITPERDAY         number(15,2),
    PAYEETYPE           char(1),
    USETYPE             char(1)   not null,
    PAYEREFFFLAG        char(1),
    EFFDATE             timestamp,
    BANKNAME            varchar2(128),
    EXPDATE             timestamp
);
drop table ECIFCROSSBANKPAYEE cascade constraints;
create table ECIFCROSSBANKPAYEE
(
    CIFSEQ              number(20,0) not null,
    PAYEENO             varchar2(32) not null,
    PAYEEACNO           varchar2(40) not null,
    PAYEEACNAME         varchar2(150),
    PAYEEACALIAS        varchar2(150),
    CTRYSUBDIVCD        varchar2(8),
    CTRYSUBDIVCDNAME    varchar2(30),
    CITYCODE            varchar2(16),
    CITYNAME            varchar2(128),
    PAYERNOTICEFLAG     char(1),
    PAYERBACKNOTICEFLAG char(1),
    PAYEEBANKID         varchar2(32),
    PAYEEBANKNAME       varchar2(128),
    PAYEEDEPTID         varchar2(32),
    PAYEEDEPTNAME       varchar2(130),
    PAYEEEXNO           varchar2(16),
    PAYEECLEARBANKID    varchar2(16),
    EMAIL               varchar2(128),
    MOBILE              varchar2(16),
    PAYEREFFFLAG        char(1),
    PAYEETYPE           char(1),
    USETYPE             char(1) not null,
    LIMITPERDAY         number(15,2),
    LIMITPERTRS         number(15,2),
    EFFDATE             timestamp,
    EXPDATE             timestamp,
    CREATEUSERSEQ       number(20,0),
    CREATEDEPTSEQ       number(20,0),
    CREATETIME          timestamp,
    UPDATEUSERSEQ       number(20,0),
    UPDATEDEPTSEQ       number(20,0),
    UPDATETIME          timestamp,
    TRSCURRENCY         varchar2(32),
    primary key (CIFSEQ, PAYEENO, USETYPE)
);
drop table ECIFCROSSBANKPAYEEHIST cascade constraints;
create table ECIFCROSSBANKPAYEEHIST
(
    MAINTJNLNO          number(20,0) not null,
    MAINTCODE           varchar2(4) not null,
    MAINTMCHANNELID     varchar2(16) not null,
    MAINTMCHJNLNO       varchar2(64) not null,
    MAINTDEPTSEQ        number(20,0),
    MAINTCIFSEQ         number(20,0),
    MAINTUSERSEQ        number(20,0),
    MAINTDATE           timestamp not null,
    MAINTTIMESTAMP      timestamp not null,
    CIFSEQ              number(20,0) not null,
    PAYEENO             varchar2(32) not null,
    PAYEEACNO           varchar2(40) not null,
    PAYEEACNAME         varchar2(150),
    PAYEEACALIAS        varchar2(150),
    CTRYSUBDIVCD        varchar2(8),
    CTRYSUBDIVCDNAME    varchar2(30),
    CITYCODE            varchar2(16),
    CITYNAME            varchar2(128),
    PAYERNOTICEFLAG     char(1),
    PAYERBACKNOTICEFLAG char(1),
    PAYEEBANKID         varchar2(32),
    PAYEEBANKNAME       varchar2(128),
    PAYEEDEPTID         varchar2(32),
    PAYEEDEPTNAME       varchar2(128),
    PAYEEEXNO           varchar2(16),
    PAYEECLEARBANKID    varchar2(16),
    EMAIL               varchar2(128),
    MOBILE              varchar2(16),
    PAYEREFFFLAG        char(1),
    PAYEETYPE           char(1),
    USETYPE             char(1)   not null,
    LIMITPERDAY         number(15,2),
    LIMITPERTRS         number(15,2),
    EFFDATE             timestamp,
    EXPDATE             timestamp
);
drop table ECORG cascade constraints;
create table ECORG
(
    CIFSEQ        number(20,0) not null,
    CORECIFNAME   varchar2(150),
    INDUSTRYCODE  varchar2(8),
    FUNCTYPE      char(1),
    ENTPROP       varchar2(16),
    GROUPFLAG     char(1),
    PARENTCIFSEQ  number(20,0),
    POSFLAG       char(1),
    PAYFLAG       char(1),
    E2BFLAG       char(1),
    NORMALFLAG    char(1),
    PRIMARYACNO   varchar2(40),
    SECRETNOTICE  varchar2(128),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    CIFCHECKSELF  char(1),
    primary key (CIFSEQ)
);
drop table ECORGHIST cascade constraints;
create table ECORGHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    CIFSEQ          number(20,0) not null,
    CORECIFNAME     varchar2(150),
    INDUSTRYCODE    varchar2(8),
    FUNCTYPE        char(1),
    ENTPROP         varchar2(16),
    GROUPFLAG       char(1),
    PARENTCIFSEQ    number(20,0),
    POSFLAG         char(1),
    PAYFLAG         char(1),
    E2BFLAG         char(1),
    NORMALFLAG      char(1),
    PRIMARYACNO     varchar2(40),
    SECRETNOTICE    varchar2(128)
);
drop table ECORGPERS cascade constraints;
create table ECORGPERS
(
    CIFSEQ        number(20,0) not null,
    RELATIONCD    varchar2(8) not null,
    NAME          varchar2(150),
    IDTYPE        varchar2(4),
    IDNO          varchar2(52),
    TELPHONE      varchar2(32),
    MOBILE        varchar2(17),
    EMAIL         varchar2(128),
    OFFICEFAX     varchar2(32),
    POSTCODE      varchar2(8),
    OFFICETEL     varchar2(32),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    NAMECN        varchar2(200),
    IDEXPIRED     timestamp,
    primary key (CIFSEQ, RELATIONCD)
);
drop table ECORGPERSHIST cascade constraints;
create table ECORGPERSHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    CIFSEQ          number(20,0) not null,
    RELATIONCD      varchar2(8) not null,
    NAME            varchar2(150),
    IDTYPE          varchar2(4),
    IDNO            varchar2(32),
    TELPHONE        varchar2(32),
    MOBILE          varchar2(17),
    EMAIL           varchar2(128),
    OFFICEFAX       varchar2(32),
    POSTCODE        varchar2(8),
    OFFICETEL       varchar2(32)
);
drop table ECPERS cascade constraints;
create table ECPERS
(
    CIFSEQ             number(20,0) not null,
    CTRYCD             varchar2(4),
    GENDER             char(1),
    BIRTHDATE          timestamp,
    COMPANY            varchar2(128),
    EDUCATION          varchar2(4),
    INCOME             varchar2(4),
    VOCATION           varchar2(4),
    CIVILSTATE         char(1),
    OPTIMALCONTACTTIME varchar2(8),
    TELLERFLAG         char(1),
    TELLERNO           varchar2(32),
    CREATEUSERSEQ      number(20,0),
    CREATEDEPTSEQ      number(20,0),
    CREATETIME         timestamp,
    UPDATEUSERSEQ      number(20,0),
    UPDATEDEPTSEQ      number(20,0),
    UPDATETIME         timestamp,
    primary key (CIFSEQ)
);
drop table ECPERSHIST cascade constraints;
create table ECPERSHIST
(
    MAINTJNLNO         number(20,0) not null,
    MAINTCODE          varchar2(4) not null,
    MAINTMCHANNELID    varchar2(16) not null,
    MAINTMCHJNLNO      varchar2(64) not null,
    MAINTDEPTSEQ       number(20,0),
    MAINTCIFSEQ        number(20,0),
    MAINTUSERSEQ       number(20,0),
    MAINTDATE          timestamp not null,
    MAINTTIMESTAMP     timestamp not null,
    CIFSEQ             number(20,0) not null,
    CTRYCD             varchar2(4),
    GENDER             char(1),
    BIRTHDATE          timestamp,
    COMPANY            varchar2(128),
    EDUCATION          varchar2(4),
    INCOME             varchar2(4),
    VOCATION           varchar2(4),
    CIVILSTATE         char(1),
    OPTIMALCONTACTTIME varchar2(8),
    TELLERFLAG         char(1),
    TELLERNO           varchar2(32)
);
drop table ECPUBLISHINFO cascade constraints;
create table ECPUBLISHINFO
(
    MODULEID     number(20,0),
    INFOPLANNAME varchar2(128) not null,
    INFOCONTENT  varchar2(1024),
    PRODUCT      varchar2(20),
    NOTE         varchar2(100),
    CREATETIME   varchar2(50),
    STARTDATE    varchar2(50),
    JNLNO        varchar2(20),
    UPDATETIME   timestamp
);
drop table ECUSR cascade constraints;
create table ECUSR
(
    USERSEQ         number(20,0) not null,
    DIVSEQ          number(20,0),
    CIFSEQ          number(20,0) not null,
    USERNAME        varchar2(150) not null,
    USERID          varchar2(128),
    PASSWORD        varchar2(128),
    IDTYPE          varchar2(4),
    IDNO            varchar2(32),
    EMAIL           varchar2(128),
    PHONE           varchar2(32),
    USERSTATE       char(1)   not null,
    GENDER          char(1),
    TITLE           varchar2(32),
    NATIONALITY     varchar2(64),
    CIFACRIGHT      char(1),
    OPENDATE        timestamp not null,
    CLOSEDATE       timestamp,
    USERDUTY        varchar2(32),
    USERPLACE       varchar2(32),
    OUTOFOFFICEFLAG char(1),
    AUTHGROUP       varchar2(4),
    ADMINUSER       char(1),
    MOBILEPHONE     varchar2(30),
    CLOSEREASON     varchar2(200),
    CREATEUSERSEQ   number(20,0),
    CREATEDEPTSEQ   number(20,0),
    CREATETIME      timestamp,
    UPDATEUSERSEQ   number(20,0),
    UPDATEDEPTSEQ   number(20,0),
    UPDATETIME      timestamp,
    PREFER_LAN      varchar2(15),
    primary key (USERSEQ)
);
drop table ECUSRAC cascade constraints;
create table ECUSRAC
(
    USERSEQ       varchar2(32) not null,
    ACSEQ         varchar2(32) not null,
    CREATEUSERSEQ varchar2(32),
    CREATEDEPTSEQ varchar2(32),
    CREATETIME    timestamp,
    UPDATEUSERSEQ varchar2(32),
    UPDATEDEPTSEQ varchar2(32),
    UPDATETIME    timestamp
);
drop table ECUSRACHIST cascade constraints;
create table ECUSRACHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    USERSEQ         number(20,0) not null,
    ACSEQ           number(20,0) not null
);
drop table ECUSRAUTHMODMCH cascade constraints;
create table ECUSRAUTHMODMCH
(
    USERSEQ       number(20,0) not null,
    AUTHMOD       varchar2(2) not null,
    MCHANNELID    varchar2(16) not null,
    CREATETIME    timestamp,
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    primary key (USERSEQ, AUTHMOD, MCHANNELID)
);
drop table ECUSRAUTHMODMCHHIST cascade constraints;
create table ECUSRAUTHMODMCHHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    USERSEQ         number(20,0) not null,
    AUTHMOD         varchar2(2) not null,
    MCHANNELID      varchar2(16) not null,
    CREATETIME      timestamp,
    CREATEUSERSEQ   number(20,0),
    CREATEDEPTSEQ   number(20,0),
    UPDATETIME      timestamp,
    UPDATEUSERSEQ   number(20,0),
    UPDATEDEPTSEQ   number(20,0)
);
drop table ECUSRCERT cascade constraints;
create table ECUSRCERT
(
    ISSUERCACODE     varchar2(5) not null,
    CERTDN           varchar2(200) not null,
    USERSEQ          number(20,0) not null,
    CERTSERIALNO     varchar2(64),
    CERTREFNO        varchar2(64),
    CERTAUTHCODE     varchar2(64),
    CERTAPPLYDATE    timestamp,
    CERTDOWNLOADDATE timestamp,
    CERTUPDATEDATE   timestamp,
    CERTRECOVERDATE  timestamp,
    USBKEYFLAG       char(1),
    USBKEYSN         varchar2(64),
    CERTDOWNCODE     varchar2(64),
    CERTSTATE        char(1),
    EXPIREDATE       timestamp,
    PREEXPIREDATE    timestamp,
    REMARK           varchar2(200),
    DELAYDOWNDATE    timestamp,
    PINNO            varchar2(32),
    FEESTATE         char(1),
    FEETYPE          char(1),
    FEETRANSFERTYPE  char(1),
    FEEACNO          varchar2(40),
    BATCHFLAG        char(1),
    CREATEUSERSEQ    number(20,0),
    CREATEDEPTSEQ    number(20,0),
    CREATETIME       timestamp,
    UPDATEUSERSEQ    number(20,0),
    UPDATEDEPTSEQ    number(20,0),
    UPDATETIME       timestamp,
    FEEJNLNO         number(20,0),
    CURRENCY         varchar2(10),
    FEE              varchar2(15),
    IDNO             varchar2(30),
    IDTYPE           varchar2(5),
    SIGNATURECERT    clob,
    primary key (ISSUERCACODE, CERTDN)
);
drop table ECUSRCERTHIST cascade constraints;
create table ECUSRCERTHIST
(
    MAINTJNLNO       number(20,0) not null,
    MAINTCODE        varchar2(4) not null,
    MAINTMCHANNELID  varchar2(16) not null,
    MAINTMCHJNLNO    varchar2(64) not null,
    MAINTDEPTSEQ     number(20,0),
    MAINTCIFSEQ      number(20,0),
    MAINTUSERSEQ     number(20,0),
    MAINTDATE        timestamp not null,
    MAINTTIMESTAMP   timestamp not null,
    ISSUERCACODE     varchar2(5),
    CERTDN           varchar2(512),
    USERSEQ          number(20,0) not null,
    CERTSERIALNO     varchar2(64),
    CERTREFNO        varchar2(64),
    CERTAUTHCODE     varchar2(64),
    CERTAPPLYDATE    timestamp,
    CERTDOWNLOADDATE timestamp,
    CERTUPDATEDATE   timestamp,
    CERTRECOVERDATE  timestamp,
    USBKEYFLAG       char(1),
    USBKEYSN         varchar2(64),
    CERTDOWNCODE     varchar2(64),
    CERTSTATE        char(1),
    EXPIREDATE       timestamp,
    PREEXPIREDATE    timestamp,
    REMARK           varchar2(512),
    DELAYDOWNDATE    timestamp,
    PINNO            varchar2(32),
    FEESTATE         char(1),
    FEETYPE          char(1),
    FEETRANSFERTYPE  char(1),
    FEEACNO          varchar2(40),
    BATCHFLAG        char(1),
    FEEJNLNO         number(20,0),
    CURRENCY         varchar2(10),
    FEE              varchar2(15),
    IDNO             varchar2(30),
    IDTYPE           varchar2(5),
    SIGNATURECERT    clob
);
drop table ECUSRHIST cascade constraints;
create table ECUSRHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    USERSEQ         number(20,0) not null,
    DIVSEQ          number(20,0),
    CIFSEQ          number(20,0),
    USERNAME        varchar2(150),
    USERID          varchar2(128),
    PASSWORD        varchar2(128),
    IDTYPE          varchar2(4),
    IDNO            varchar2(32),
    EMAIL           varchar2(128),
    PHONE           varchar2(32),
    USERSTATE       char(1),
    GENDER          char(1),
    TITLE           varchar2(32),
    NATIONALITY     varchar2(64),
    MOBILEPHONE     varchar2(30),
    CIFACRIGHT      char(1),
    OPENDATE        timestamp,
    CLOSEDATE       timestamp,
    USERDUTY        varchar2(32),
    USERPLACE       varchar2(32),
    OUTOFOFFICEFLAG char(1),
    AUTHGROUP       varchar2(4),
    ADMINUSER       char(1),
    PREFER_LAN      varchar2(15)
);
drop table ECUSRIDREC cascade constraints;
create table ECUSRIDREC
(
    CIFSEQ    number(20,0) not null,
    USERIDSEQ number(0,-127),
    USERIDS   varchar2(512),
    primary key (CIFSEQ)
);
drop table ECUSRLOGINTYPE cascade constraints;
create table ECUSRLOGINTYPE
(
    USERSEQ            number(20,0) not null,
    MCHANNELID         varchar2(16) not null,
    LOGINTYPE          char(1) not null,
    USERID             varchar2(128),
    PASSWORD           varchar2(128),
    LOGINTYPESTATE     char(1) not null,
    UPDATEPASSWORDDATE timestamp,
    WRONGPASSCOUNT     number(0,-127),
    UNLOCKDATE         timestamp,
    FIRSTLOGINTIME     timestamp,
    LASTLOGINTIME      timestamp,
    LASTLOGINADDR      varchar2(32),
    CREATEUSERSEQ      number(20,0),
    CREATEDEPTSEQ      number(20,0),
    CREATETIME         timestamp,
    UPDATEUSERSEQ      number(20,0),
    UPDATEDEPTSEQ      number(20,0),
    UPDATETIME         timestamp,
    OLD_PASSWORD       varchar2(3000),
    primary key (USERSEQ, MCHANNELID, LOGINTYPE)
);
drop table ECUSRLOGINTYPEHIST cascade constraints;
create table ECUSRLOGINTYPEHIST
(
    MAINTJNLNO         number(20,0) not null,
    MAINTCODE          varchar2(4) not null,
    MAINTMCHANNELID    varchar2(16) not null,
    MAINTMCHJNLNO      varchar2(64) not null,
    MAINTDEPTSEQ       number(20,0),
    MAINTCIFSEQ        number(20,0),
    MAINTUSERSEQ       number(20,0),
    MAINTDATE          timestamp not null,
    MAINTTIMESTAMP     timestamp not null,
    USERSEQ            number(20,0) not null,
    MCHANNELID         varchar2(16) not null,
    LOGINTYPE          char(1)   not null,
    USERID             varchar2(128),
    PASSWORD           varchar2(128),
    LOGINTYPESTATE     char(1)   not null,
    UPDATEPASSWORDDATE timestamp,
    WRONGPASSCOUNT     number(0,-127),
    UNLOCKDATE         timestamp,
    FIRSTLOGINTIME     timestamp,
    LASTLOGINTIME      timestamp,
    LASTLOGINADDR      varchar2(32)
);
drop table ECUSRMCH cascade constraints;
create table ECUSRMCH
(
    USERSEQ            number(20,0) not null,
    MCHANNELID         varchar2(16) not null,
    SECRETNOTICE       varchar2(256),
    USERID             varchar2(128),
    PASSWORD           varchar2(128),
    USERMCHSTATE       char(1) not null,
    ACTIVATIONTIME     timestamp,
    UPDATEPASSWORDDATE timestamp,
    WRONGPASSCOUNT     number(0,-127),
    LOCKTIME           timestamp,
    UNLOCKDATE         timestamp,
    FIRSTLOGINTIME     timestamp,
    LASTLOGINTIME      timestamp,
    LOGINTIMES         number(0,-127),
    LASTLOGINADDR      varchar2(32),
    LASTMACHINECODE    varchar2(512),
    MOBILEPHONE        varchar2(30),
    MOVESIGN           varchar2(2),
    CREATEUSERSEQ      number(20,0),
    CREATEDEPTSEQ      number(20,0),
    CREATETIME         timestamp,
    UPDATEUSERSEQ      number(20,0),
    UPDATEDEPTSEQ      number(20,0),
    UPDATETIME         timestamp,
    USERCIFNO          varchar2(20),
    primary key (USERSEQ, MCHANNELID)
);
drop table ECUSRMCHACCT cascade constraints;
create table ECUSRMCHACCT
(
    ACSEQ         number(20,0) not null,
    USERSEQ       number(20,0) not null,
    MCHANNELID    varchar2(16) not null,
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    OPTIONFLG     varchar2(4),
    primary key (ACSEQ, USERSEQ, MCHANNELID)
);
drop table ECUSRMCHACCTHIST cascade constraints;
create table ECUSRMCHACCTHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    ACSEQ           number(20,0) not null,
    USERSEQ         number(20,0) not null,
    MCHANNELID      varchar2(16) not null
);
drop table ECUSRMCHHIST cascade constraints;
create table ECUSRMCHHIST
(
    MAINTJNLNO         number(20,0) not null,
    MAINTCODE          varchar2(4) not null,
    MAINTMCHANNELID    varchar2(16) not null,
    MAINTMCHJNLNO      varchar2(64) not null,
    MAINTDEPTSEQ       number(20,0),
    MAINTCIFSEQ        number(20,0),
    MAINTUSERSEQ       number(20,0),
    MAINTDATE          timestamp not null,
    MAINTTIMESTAMP     timestamp not null,
    USERSEQ            number(20,0) not null,
    MCHANNELID         varchar2(16) not null,
    SECRETNOTICE       varchar2(256),
    USERID             varchar2(128),
    PASSWORD           varchar2(128),
    MOBILEPHONE        varchar2(20),
    USERMCHSTATE       char(1)   not null,
    ACTIVATIONTIME     timestamp,
    UPDATEPASSWORDDATE timestamp,
    WRONGPASSCOUNT     number(0,-127),
    LOCKTIME           timestamp,
    UNLOCKDATE         timestamp,
    FIRSTLOGINTIME     timestamp,
    LASTLOGINTIME      timestamp,
    LOGINTIMES         number(0,-127),
    LASTLOGINADDR      varchar2(32),
    MOVESIGN           varchar2(2)
);
drop table ECUSROTP cascade constraints;
create table ECUSROTP
(
    VENDORID      varchar2(32) not null,
    OTPTYPE       varchar2(3) not null,
    OTPNO         varchar2(32) not null,
    USERSEQ       number(20,0) not null,
    CIFSEQ        number(20,0) not null,
    DEVICENO      varchar2(32),
    MCHANNELID    varchar2(16) not null,
    OTPSTATE      char(1) not null,
    OTPSEED       varchar2(225),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (VENDORID, OTPTYPE, OTPNO)
);
drop table ECUSROTPHIST cascade constraints;
create table ECUSROTPHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    VENDORID        varchar2(32) not null,
    OTPTYPE         varchar2(3) not null,
    OTPNO           varchar2(32) not null,
    OTPSEED         varchar2(225),
    USERSEQ         number(20,0),
    OTPSTATE        char(1)   not null
);
drop table ECUSRPRDPROT cascade constraints;
create table ECUSRPRDPROT
(
    USERSEQ       number(20,0) not null,
    PROTOCOL_TYPE varchar2(20) not null,
    FLAG          number(1,0),
    CREATE_TMS    timestamp,
    primary key (USERSEQ, PROTOCOL_TYPE)
);
drop table ECUSRROLE cascade constraints;
create table ECUSRROLE
(
    USERSEQ       number(20,0) not null,
    ROLESEQ       number(20,0) not null,
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (USERSEQ, ROLESEQ)
);
drop table ECUSRROLEHIST cascade constraints;
create table ECUSRROLEHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    USERSEQ         number(20,0) not null,
    ROLESEQ         number(20,0) not null
);
drop table ECUSRRULE cascade constraints;
create table ECUSRRULE
(
    USERSEQ       number(20,0) not null,
    MODULEID      varchar2(16) not null,
    RULENAMESPACE varchar2(64) not null,
    RULEID        varchar2(64) not null,
    RULETYPE      varchar2(16) not null,
    RULEDEF       varchar2(1024),
    RULESCRIPT    varchar2(2056),
    LASTUPDUSER   varchar2(20),
    LASTUPDTIME   timestamp,
    primary key (USERSEQ, MODULEID, RULENAMESPACE, RULEID, RULETYPE)
);
drop table ECUSRRULEHIST cascade constraints;
create table ECUSRRULEHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    USERSEQ         number(20,0) not null,
    MODULEID        varchar2(16) not null,
    RULENAMESPACE   varchar2(64) not null,
    RULEID          varchar2(64) not null,
    RULETYPE        varchar2(16) not null,
    RULEDEF         varchar2(1024),
    RULESCRIPT      varchar2(2056),
    LASTUPDUSER     varchar2(20),
    LASTUPDTIME     timestamp
);
drop table ECWHITELIST cascade constraints;
create table ECWHITELIST
(
    CIFSEQ     number(20,0) not null,
    PRDLIMIT   varchar2(20),
    HANDLERS   varchar2(20),
    RECHECK    varchar2(20),
    CREATETIME timestamp,
    UPDATETIME timestamp,
    primary key (CIFSEQ)
);
drop table ECWHITELISTHIST cascade constraints;
create table ECWHITELISTHIST
(
    CIFSEQ     number(20,0),
    PRDLIMIT   varchar2(20),
    HANDLERS   varchar2(20),
    RECHECK    varchar2(20),
    CREATETIME timestamp
);
drop table FXPAYEE cascade constraints;
create table FXPAYEE
(
    CIFSEQ                  number(20,0) not null,
    PAYEENO                 varchar2(32) not null,
    PAYEEACNAME             varchar2(256) not null,
    PAYEEACCOUNTNO          varchar2(40) not null,
    PAYEEADDR               varchar2(1024),
    PAYEEBANKNAME           varchar2(256),
    PAYEEBANKADDR           varchar2(1024),
    PAYEEBANKCODE           varchar2(32),
    PAYEEBANKAGENCYBANKNAME varchar2(256),
    PAYEEBANKAGENCYBANKADDR varchar2(1024),
    PAYEEBANKAGENCYBANKCODE varchar2(32),
    CREATETIME              timestamp,
    CREATEUSERSEQ           number(20,0),
    UPDATETIME              timestamp,
    UPDATEUSERSEQ           number(20,0),
    primary key (CIFSEQ, PAYEENO)
);
drop table FX_CCY_SETTING cascade constraints;
create table FX_CCY_SETTING
(
    TRANS_DATE             varchar2(20) not null,
    CURRENCY_NAME1         varchar2(3) not null,
    CURRENCY_NAME2         varchar2(3) not null,
    DECIMALS               number(3,0) not null,
    UNSTABLE_DECIMALS      number(3,0) not null,
    STANDARD_AMOUNT        number(20,0),
    LAST_UPDATE_TMS        timestamp not null,
    STANDARD_CURRENCY_NAME varchar2(3),
    REQUEST_ID             varchar2(50)
);
drop table FX_CURRENCY cascade constraints;
create table FX_CURRENCY
(
    CURRENCY_ID    varchar2(20) not null,
    CURRENCY_ID_CN varchar2(50) not null,
    SCALE_MODE     number(3,0) not null,
    ROUND_MODE     number(3,0) not null,
    primary key (CURRENCY_ID)
);
drop table FX_CURRENCY_PAIR cascade constraints;
create table FX_CURRENCY_PAIR
(
    CURRENCY_PAIR_ID    varchar2(20) not null,
    CURRENCY_PAIR_ID_CN varchar2(200) not null,
    SCALE_MODE          number(3,0) not null,
    ROUND_MODE          number(3,0) not null,
    primary key (CURRENCY_PAIR_ID)
);
drop table FX_JTF_TRADE_INFO cascade constraints;
create table FX_JTF_TRADE_INFO
(
    JNL_NO             number(20,0) not null,
    PARENT_JNL_NO      number(20,0),
    AUDIT_PROCESS_MODE varchar2(32),
    SVC_ID             varchar2(64),
    NOTICE             varchar2(100),
    PROCESS_STATE      varchar2(8),
    PROCESS_TIME       timestamp,
    TRANS_MTH          varchar2(30),
    TRANS_TYPE         varchar2(10) not null,
    PAYEE_ACCOUNT_NO   varchar2(40) not null,
    PAYEE_CURRENCY     varchar2(3) not null,
    PAYER_ACCOUNT_NO   varchar2(40) not null,
    PAYER_CURRENCY     varchar2(3) not null,
    TRANS_CUR          varchar2(10),
    TRANS_AMOUNT       number(24,6) not null,
    CURRENCY_PAIR      varchar2(10),
    BID_ASK_TYPE       varchar2(10),
    RATE               number(18,6),
    CONVERT_AMOUNT     number(24,6),
    TNS_CD             varchar2(10),
    REMARK             varchar2(3000),
    EXCEED_TRANS_LIMIT varchar2(1),
    CERTIFIED_DOC      varchar2(300),
    PURPOSE_CODE       varchar2(3),
    PURPOSE_DETAIL     varchar2(600),
    PAYEE_ACOWNER_ID   varchar2(10),
    PAYER_ACOWNER_ID   varchar2(10),
    primary key (JNL_NO)
);
drop table FX_JTF_TRADE_INFO_HIST cascade constraints;
create table FX_JTF_TRADE_INFO_HIST
(
    JNL_NO             number(20,0) not null,
    PARENT_JNL_NO      number(20,0),
    AUDIT_PROCESS_MODE varchar2(32),
    SVC_ID             varchar2(64),
    NOTICE             varchar2(100),
    PROCESS_STATE      varchar2(8),
    PROCESS_TIME       timestamp,
    TRANS_MTH          varchar2(30),
    TRANS_TYPE         varchar2(10) not null,
    PAYEE_ACCOUNT_NO   varchar2(40) not null,
    PAYEE_CURRENCY     varchar2(3) not null,
    PAYER_ACCOUNT_NO   varchar2(40) not null,
    PAYER_CURRENCY     varchar2(3) not null,
    TRANS_CUR          varchar2(10),
    TRANS_AMOUNT       number(24,6) not null,
    CURRENCY_PAIR      varchar2(10),
    BID_ASK_TYPE       varchar2(10),
    RATE               number(18,6),
    CONVERT_AMOUNT     number(24,6),
    TNS_CD             varchar2(10),
    REMARK             varchar2(3000),
    EXCEED_TRANS_LIMIT varchar2(1),
    CERTIFIED_DOC      varchar2(300),
    PURPOSE_CODE       varchar2(3),
    PURPOSE_DETAIL     varchar2(600),
    PAYEE_ACOWNER_ID   varchar2(10),
    PAYER_ACOWNER_ID   varchar2(10)
);
drop table FX_MAIN_SETTING cascade constraints;
create table FX_MAIN_SETTING
(
    TRANS_DATE                 varchar2(20) not null,
    SUBMIT_TIMEOUT_SECS        number(0,-127),
    LIMIT_SUBMIT_TIMEOUT_SECS  number(0,-127),
    TRADE_CONFIRM_TIMEOUT_SECS number(0,-127),
    LAST_UPDATE_TMS            timestamp not null,
    REQUEST_ID                 varchar2(50),
    primary key (TRANS_DATE)
);
drop table FX_MANUAL_RATE cascade constraints;
create table FX_MANUAL_RATE
(
    QUOTE_SEQ           varchar2(50) not null,
    TRANS_CURRENCY_NAME varchar2(3),
    TRANS_AMOUNT        number(24,6),
    CURRENCY_PAIR_NAME  varchar2(10),
    DIRECTION           varchar2(10),
    VALUE_DATE          varchar2(20),
    RATE                number(16,6),
    CONVERT_AMOUNT      number(24,6),
    REASON              varchar2(500)
);
drop table FX_MANUAL_RATE_REQ cascade constraints;
create table FX_MANUAL_RATE_REQ
(
    QUOTE_SEQ       varchar2(50) not null,
    TRANS_SEQ       varchar2(50) not null,
    CIFSEQ          number(0,-127),
    USERSEQ         number(0,-127),
    ACTIVE_IND      char(1),
    LAST_UPDATE_TMS timestamp not null
);
drop table FX_PENDING_DETAIL cascade constraints;
create table FX_PENDING_DETAIL
(
    PARENT_JNL_NO       varchar2(50) not null,
    TRANS_SEQ           varchar2(50) not null,
    TRANS_MTH           varchar2(20) not null,
    TRANS_TYPE          varchar2(20) not null,
    PROCESS_STATUS      varchar2(8) not null,
    TRANS_STATUS        varchar2(20),
    CLIENT_ID           varchar2(32) not null,
    MAKER_ID            varchar2(32) not null,
    MAKER_NAME          varchar2(100),
    MAKE_TMS            timestamp not null,
    CHECKER_ID          varchar2(32),
    CHECKER_NAME        varchar2(100),
    CHECK_TMS           timestamp,
    AUDITOR_ID          varchar2(32),
    AUDIT_TMS           timestamp,
    PAYER_ACCOUNT_NO    varchar2(32) not null,
    PAYER_CURRENCY_NAME varchar2(3) not null,
    PAYEE_ACCOUNT_NO    varchar2(32) not null,
    PAYEE_CURRENCY_NAME varchar2(3) not null,
    TRANS_CURRENCY_NAME varchar2(3) not null,
    TRANS_AMOUNT        number(24,6) not null,
    REF_AMOUNT          number(24,6),
    REF_RATE            number(18,6),
    ACT_AMOUNT          number(24,6),
    ACT_RATE            number(18,6),
    BOP_CODE            varchar2(10),
    CERTIFIED_DOC       varchar2(255),
    REMARKS             varchar2(3000),
    EXCEED_TRANS_LIMIT  varchar2(1),
    SUBMIT_TMS          timestamp,
    LAST_UPDATE_TMS     timestamp not null,
    EXPIRY_TMS          timestamp not null,
    CURRENCY_PAIR       varchar2(10),
    BID_ASK_TYPE        varchar2(10),
    PURPOSE_CODE        varchar2(3),
    PURPOSE_DETAIL      varchar2(600),
    SUBMITOR_ID         varchar2(32),
    SUBSCRIBE_ID        varchar2(50),
    QUOTATION_SET_ID    varchar2(50),
    QUOTATION_ID        varchar2(50),
    RETURN_CODE         varchar2(50),
    RETURN_MESSAGE      varchar2(1000),
    VALUE_DATE          varchar2(30),
    ALREADY_READ        varchar2(1),
    PAYEE_ACOWNER_ID    varchar2(10),
    PAYER_ACOWNER_ID    varchar2(10),
    NOTIFICATION_SEQ    varchar2(50)
);
drop table FX_RBS_SETTING cascade constraints;
create table FX_RBS_SETTING
(
    SFX_TRANS_LIMIT             number(24,6) not null,
    FX_TRANS_LIMIT              number(24,6) not null,
    FX_STANDARD_QUOTE_LIMIT     number(24,6) not null,
    PENDING_CONFIRM_TIMEOUT_HRS number(3,0) not null
);
drop table FX_STANDARD_QUOTE cascade constraints;
create table FX_STANDARD_QUOTE
(
    NOTIFY_SEQ          varchar2(32) not null,
    NOTIFY_DATE         varchar2(20) not null,
    TERM                varchar2(10) not null,
    DATE_FROM           timestamp,
    RATE_CATG           varchar2(10),
    CURRENCY_NAME1      varchar2(3),
    CURRENCY_CODE1      varchar2(3),
    CURRENCY_NAME2      varchar2(3),
    CURRENCY_CODE2      varchar2(3),
    TRANS_CURRENCY_NAME varchar2(3),
    TRANS_CURRENCY_CODE varchar2(3),
    MIN_TRANS_AMOUNT    number(24,6),
    MAX_TRANS_AMOUNT    number(24,6),
    BID                 number(16,6),
    OFFER               number(16,6),
    LAST_UPDATE_TMS     timestamp not null
);
drop table FX_SUBSCRIBE_QUOTE cascade constraints;
create table FX_SUBSCRIBE_QUOTE
(
    RATE_NOTIFICATION_ID varchar2(50) not null,
    RATE_ID              varchar2(50) not null,
    SUBSCRIBE_ID         varchar2(50) not null,
    NODE_ID              varchar2(50),
    BID                  varchar2(50) not null,
    ASK                  varchar2(50) not null,
    ARRIVAL_TMS          varchar2(50) not null,
    EXPIRY_TMS           timestamp not null,
    RATE_SEQ             number(20,0) not null,
    primary key (RATE_SEQ)
);
drop table FX_SUBSCRIBE_REL cascade constraints;
create table FX_SUBSCRIBE_REL
(
    NODE_ID         varchar2(50) not null,
    USER_ID         varchar2(50) not null,
    SUBSCRIBE_ID    varchar2(50) not null,
    CURRENCY_PAIR   varchar2(50),
    NOTIFICATION_ID varchar2(50),
    LAST_UPDATE_TMS timestamp
);
drop table JAGBATCH cascade constraints;
create table JAGBATCH
(
    BATCHNO           number(20,0) not null,
    TOTALAMOUNT       number(15,2),
    TOTALCOUNT        number(0,-127),
    PAYERBANKACTYPE   varchar2(4),
    PAYERACNO         varchar2(40) not null,
    PAYERCURRENCYCODE varchar2(3) not null,
    PAYERCRFLAG       char(1),
    PROCESSSTATE      varchar2(8) not null,
    TRANSDATE         timestamp,
    EAGENTMARK        varchar2(64),
    SUCCCOUNT         number(0,-127),
    SUCCAMOUNT        number(15,2),
    PAYERACNAME       varchar2(150),
    primary key (BATCHNO)
);
drop table JAGBATCHDETAIL cascade constraints;
create table JAGBATCHDETAIL
(
    BATCHNO           number(20,0) not null,
    SEQNO             number(0,-127) not null,
    PROCESSSTATE      varchar2(8) not null,
    PAYEEACNO         varchar2(40) not null,
    PAYEEACNAME       varchar2(150) not null,
    PAYEEBANKACTYPE   varchar2(4),
    PAYEECURRENCYCODE varchar2(3) not null,
    PAYEECRFLAG       char(1),
    TRSAMOUNT         number(15,2) not null,
    FUNDUSAGE         varchar2(128),
    ATTACHINFO        varchar2(256),
    TRANSDATE         timestamp,
    FAILRETURNMSG     varchar2(1024),
    primary key (BATCHNO, SEQNO)
);
drop table JAGBATCHDETAILHIST cascade constraints;
create table JAGBATCHDETAILHIST
(
    BATCHNO           number(20,0) not null,
    SEQNO             number(0,-127) not null,
    PROCESSSTATE      varchar2(8) not null,
    PAYEEACNAME       varchar2(150) not null,
    PAYEEBANKACTYPE   varchar2(4),
    PAYEEACNO         varchar2(40) not null,
    PAYEECURRENCYCODE varchar2(3) not null,
    PAYEECRFLAG       char(1),
    TRSAMOUNT         number(15,2) not null,
    FUNDUSAGE         varchar2(128),
    ATTACHINFO        varchar2(256),
    TRANSDATE         timestamp,
    FAILRETURNMSG     varchar2(1024),
    primary key (BATCHNO, SEQNO)
);
drop table JAGBATCHHIST cascade constraints;
create table JAGBATCHHIST
(
    BATCHNO           number(20,0) not null,
    TOTALAMOUNT       number(15,2),
    TOTALCOUNT        number(0,-127),
    PAYERBANKACTYPE   varchar2(4),
    PAYERACNO         varchar2(40) not null,
    PAYERCURRENCYCODE varchar2(3) not null,
    PAYERCRFLAG       char(1),
    PROCESSSTATE      varchar2(8) not null,
    TRANSDATE         timestamp,
    EAGENTMARK        varchar2(64),
    SUCCCOUNT         number(0,-127),
    SUCCAMOUNT        number(15,2),
    primary key (BATCHNO)
);
drop table JTDSAVTIM cascade constraints;
create table JTDSAVTIM
(
    JNLNO              number(20,0) not null,
    PROCESSSTATE       varchar2(8),
    PAYERACNO          varchar2(40) not null,
    PAYERBANKACTYPE    varchar2(4),
    PAYERSUBACNO       varchar2(40),
    PAYERSUBACTYPE     varchar2(4),
    PAYERSUBACSEQ      number(0,-127),
    PAYERCURRENCYCODE  varchar2(3) not null,
    PAYERCRFLAG        char(1),
    PAYEEACNO          varchar2(40),
    PAYEEBANKACTYPE    varchar2(4),
    PAYEESUBACNO       varchar2(40),
    PAYEESUBACTYPE     varchar2(4),
    PAYEESUBACSEQ      number(0,-127),
    TRSCURRENCYCODE    varchar2(3),
    TRSCRFLAG          char(1),
    TRSAMOUNT          number(15,2) not null,
    DRAWTYPE           char(1),
    TIMTYPE            varchar2(3),
    TERM               varchar2(8),
    TIMREMAINFLAG      char(1),
    RENEWALCOUNT       number(0,-127),
    TIMRENEWTERM       varchar2(8),
    TIMRENEWACNO       varchar2(40),
    TIMRENEWBANKACTYPE varchar2(4),
    TRSPASSWORD        varchar2(128),
    primary key (JNLNO)
);
drop table JTDSAVTIMHIST cascade constraints;
create table JTDSAVTIMHIST
(
    JNLNO              number(20,0) not null,
    PROCESSSTATE       varchar2(8),
    PAYERACNO          varchar2(40) not null,
    PAYERBANKACTYPE    varchar2(4),
    PAYERSUBACNO       varchar2(40),
    PAYERSUBACTYPE     varchar2(4),
    PAYERSUBACSEQ      number(0,-127),
    PAYERCURRENCYCODE  varchar2(3) not null,
    PAYERCRFLAG        char(1),
    PAYEEACNO          varchar2(40),
    PAYEEBANKACTYPE    varchar2(4),
    PAYEESUBACNO       varchar2(40),
    PAYEESUBACTYPE     varchar2(4),
    PAYEESUBACSEQ      number(0,-127),
    TRSCURRENCYCODE    varchar2(3),
    TRSCRFLAG          char(1),
    TRSAMOUNT          number(15,2) not null,
    DRAWTYPE           char(1),
    TIMTYPE            varchar2(3),
    TERM               varchar2(8),
    TIMREMAINFLAG      char(1),
    RENEWALCOUNT       number(0,-127),
    TIMRENEWTERM       varchar2(8),
    TIMRENEWACNO       varchar2(40),
    TIMRENEWBANKACTYPE varchar2(4),
    TRSPASSWORD        varchar2(128),
    primary key (JNLNO)
);
drop table JTFBACH cascade constraints;
create table JTFBACH
(
    BATCHJNLNO   number(20,0) not null,
    SCHEDULEFLAG char(1) not null,
    SCHEDULERULE varchar2(128),
    PROCESSSTATE varchar2(8) not null,
    PAYERACNO    varchar2(40),
    PAYERACNAME  varchar2(150),
    FUNDUSAGE    varchar2(128),
    TOTALAMOUNT  number(15,2),
    TOTALCOUNT   number(0,-127),
    ENTRYCOUNT   number(0,-127),
    ENTRYAMOUNT  number(15,2),
    ENTRYTYPE    char(1),
    BATCHTYPE    varchar2(2),
    JOBID        varchar2(64),
    PROCESSTIME  timestamp,
    TRANSDATE    timestamp,
    primary key (BATCHJNLNO)
);
drop table JTFBACHDETAIL cascade constraints;
create table JTFBACHDETAIL
(
    JNLNO               number(20,0) not null,
    BATCHJNLNO          number(20,0) not null,
    SVCID               varchar2(64),
    PROCESSSTATE        varchar2(8) not null,
    PAYERBANKACTYPE     varchar2(4),
    PAYERACNO           varchar2(40) not null,
    PAYERSUBACNO        varchar2(40),
    PAYERSUBACTYPE      varchar2(4),
    PAYERSUBACSEQ       number(0,-127),
    PAYERCURRENCYCODE   varchar2(3) not null,
    PAYERCRFLAG         char(1),
    PAYEECIFTYPE        char(1),
    PAYEEACNAME         varchar2(150) not null,
    PAYEEBANKACTYPE     varchar2(4),
    PAYEEACNO           varchar2(40) not null,
    CITYCODE            varchar2(8),
    PAYEECURRENCYCODE   varchar2(3) not null,
    PAYEECRFLAG         char(1),
    TRSCURRENCYCODE     varchar2(3) not null,
    TRSCRFLAG           char(1),
    TRSAMOUNT           number(15,2) not null,
    FUNDUSAGE           varchar2(32),
    ATTACHINFO          varchar2(256),
    TRSPASSWORD         varchar2(128),
    PRIORITY            char(1),
    PAYERCHANNEL        varchar2(4),
    PAYEEBANKDEPTID     varchar2(16),
    MPFLAG              char(1),
    PAYEEBANKCLEARID    varchar2(16),
    BANKINNERFLAG       char(1),
    SAMECITYFLAG        char(1),
    PROCESSTIME         timestamp,
    BANKCODE            varchar2(32),
    PAYEEBANKPROVNAME   varchar2(32),
    PAYEEBANKCITYNAME   varchar2(32),
    PAYEEBANKIDNAME     varchar2(128),
    PAYEEBANKDEPTIDNAME varchar2(128),
    PAYEEBANKPROV       varchar2(32),
    TRANSFERTYPE        varchar2(16),
    ISADDTOPAYEEBOOK    char(1),
    PAYEEACID           varchar2(32),
    PAYEEBANKCITY       varchar2(32),
    PAYEEBANKID         varchar2(32),
    NOTICE              varchar2(256),
    PAYERACNAME         varchar2(150),
    TRSTYPEFLAG         char(1),
    TRANSDATE           timestamp,
    RELATIONJNLNO       varchar2(128),
    FAILREASON          varchar2(256),
    FEEAMOUNT           number(15,2)
);
drop table JTFBACHDETAILHIST cascade constraints;
create table JTFBACHDETAILHIST
(
    JNLNO               number(20,0) not null,
    BATCHJNLNO          number(20,0),
    SVCID               varchar2(64),
    PROCESSSTATE        varchar2(8) not null,
    PAYERBANKACTYPE     varchar2(4),
    PAYERACNO           varchar2(40) not null,
    PAYERSUBACNO        varchar2(40),
    PAYERSUBACTYPE      varchar2(4),
    PAYERSUBACSEQ       number(0,-127),
    PAYERCURRENCYCODE   varchar2(3) not null,
    PAYERCRFLAG         char(1),
    PAYEECIFTYPE        char(1),
    PAYEEACNAME         varchar2(150) not null,
    PAYEEBANKACTYPE     varchar2(4),
    PAYEEACNO           varchar2(40) not null,
    CITYCODE            varchar2(8),
    PAYEECURRENCYCODE   varchar2(3) not null,
    PAYEECRFLAG         char(1),
    TRSCURRENCYCODE     varchar2(3) not null,
    TRSCRFLAG           char(1),
    TRSAMOUNT           number(15,2) not null,
    FUNDUSAGE           varchar2(32),
    ATTACHINFO          varchar2(256),
    TRSPASSWORD         varchar2(128),
    PRIORITY            char(1),
    PAYERCHANNEL        varchar2(4),
    PAYEEBANKDEPTID     varchar2(16),
    MPFLAG              char(1),
    PAYEEBANKCLEARID    varchar2(16),
    BANKINNERFLAG       char(1),
    SAMECITYFLAG        char(1),
    PROCESSTIME         timestamp,
    BANKCODE            varchar2(32),
    PAYEEBANKPROVNAME   varchar2(32),
    PAYEEBANKCITYNAME   varchar2(32),
    PAYEEBANKIDNAME     varchar2(128),
    PAYEEBANKDEPTIDNAME varchar2(128),
    PAYEEBANKPROV       varchar2(32),
    TRANSFERTYPE        varchar2(16),
    ISADDTOPAYEEBOOK    char(1),
    PAYEEACID           varchar2(32),
    PAYEEBANKCITY       varchar2(32),
    PAYEEBANKID         varchar2(32),
    NOTICE              varchar2(256),
    PAYERACNAME         varchar2(150),
    TRSTYPEFLAG         char(1),
    TRANSDATE           timestamp,
    RELATIONJNLNO       number(20,0),
    primary key (JNLNO)
);
drop table JTFBACHHIST cascade constraints;
create table JTFBACHHIST
(
    BATCHJNLNO   number(20,0) not null,
    SCHEDULEFLAG char(1) not null,
    SCHEDULERULE varchar2(128),
    PROCESSSTATE varchar2(8) not null,
    TOTALAMOUNT  number(15,2),
    TOTALCOUNT   number(0,-127),
    ENTRYCOUNT   number(0,-127),
    ENTRYAMOUNT  number(15,2),
    ENTRYTYPE    char(1),
    PROCESSTIME  timestamp,
    TRANSDATE    timestamp,
    primary key (BATCHJNLNO)
);
drop table JTFBANKINNER cascade constraints;
create table JTFBANKINNER
(
    JNLNO              number(20,0) not null,
    SCHEDULEFLAG       char(1) not null,
    SCHEDULERULE       varchar2(128),
    PROCESSSTATE       varchar2(8) not null,
    PAYERBANKACTYPE    varchar2(4),
    PAYERACNO          varchar2(40) not null,
    CORECIFNO          varchar2(40),
    PAYERSUBACNO       varchar2(40),
    PAYERSUBACTYPE     varchar2(4),
    PAYERSUBACSEQ      number(0,-127),
    PAYERCURRENCYCODE  varchar2(3) not null,
    PAYERCRFLAG        char(1),
    PAYEEMOBILE        varchar2(16),
    PAYEEDEPTSEQ       number(20,0),
    PAYEECIFTYPE       char(1),
    PAYEEACNAME        varchar2(150) not null,
    PAYEEBANKACTYPE    varchar2(4),
    PAYEEACNO          varchar2(40) not null,
    PAYEECURRENCYCODE  varchar2(3) not null,
    PAYEECRFLAG        char(1),
    TRSCURRENCYCODE    varchar2(3) not null,
    TRSCRFLAG          char(1),
    TRSAMOUNT          number(15,2) not null,
    FUNDUSAGE          varchar2(128),
    ATTACHINFO         varchar2(630),
    TRSPASSWORD        varchar2(128),
    SVCID              varchar2(64),
    ISADDTOPAYEEBOOK   char(1),
    PAYEEACID          varchar2(32),
    NOTICE             varchar2(256),
    PROCESSTIME        timestamp,
    PARENTJNLNO        number(20,0),
    PAYERACNAME        varchar2(150) not null,
    BATCHJNLNO         number(20,0),
    TRSTYPEFLAG        char(1),
    TRANSFERPOLICYTYPE varchar2(8),
    MOBILE             varchar2(12),
    ORDERID            number(20,0),
    TRANSDATE          timestamp,
    FEECODE            varchar2(32),
    FEEAMOUNT          number(15,2),
    CERTIFIEDDOC       varchar2(200),
    primary key (JNLNO)
);
drop table JTFBANKINNERHIST cascade constraints;
create table JTFBANKINNERHIST
(
    JNLNO              number(20,0) not null,
    SCHEDULEFLAG       char(1) not null,
    SCHEDULERULE       varchar2(128),
    PROCESSSTATE       varchar2(8) not null,
    PAYERBANKACTYPE    varchar2(4),
    PAYERACNO          varchar2(40) not null,
    CORECIFNO          varchar2(40),
    PAYERSUBACNO       varchar2(40),
    PAYERSUBACTYPE     varchar2(4),
    PAYERSUBACSEQ      number(0,-127),
    PAYERCURRENCYCODE  varchar2(3) not null,
    PAYERCRFLAG        char(1),
    PAYEEMOBILE        varchar2(16),
    PAYEEDEPTSEQ       number(20,0),
    PAYEECIFTYPE       char(1),
    PAYEEACNAME        varchar2(150) not null,
    PAYEEBANKACTYPE    varchar2(4),
    PAYEEACNO          varchar2(40) not null,
    PAYEECURRENCYCODE  varchar2(3) not null,
    PAYEECRFLAG        char(1),
    TRSCURRENCYCODE    varchar2(3) not null,
    TRSCRFLAG          char(1),
    TRSAMOUNT          number(15,2) not null,
    FUNDUSAGE          varchar2(128),
    ATTACHINFO         varchar2(630),
    TRSPASSWORD        varchar2(128),
    SVCID              varchar2(64),
    ISADDTOPAYEEBOOK   char(1),
    PAYEEACID          varchar2(32),
    NOTICE             varchar2(256),
    PROCESSTIME        timestamp,
    PARENTJNLNO        number(20,0),
    PAYERACNAME        varchar2(150),
    BATCHJNLNO         number(20,0),
    TRSTYPEFLAG        char(1),
    TRANSFERPOLICYTYPE varchar2(8),
    MOBILE             varchar2(12),
    ORDERID            number(20,0),
    TRANSDATE          timestamp,
    FEECODE            varchar2(32),
    FEEAMOUNT          number(15,2),
    CERTIFIEDDOC       varchar2(200)
);
drop table JTFCROSSBANK cascade constraints;
create table JTFCROSSBANK
(
    JNLNO               number(20,0) not null,
    SCHEDULEFLAG        char(1) not null,
    SCHEDULERULE        varchar2(128),
    PROCESSSTATE        varchar2(8) not null,
    PAYERBANKACTYPE     varchar2(4),
    PAYERACNO           varchar2(40) not null,
    CORECIFNO           varchar2(40),
    PAYERSUBACNO        varchar2(40),
    PAYERSUBACTYPE      varchar2(4),
    PAYERSUBACSEQ       number(0,-127),
    PAYERCURRENCYCODE   varchar2(3) not null,
    PAYERCRFLAG         char(1),
    PAYEEBANKPROV       varchar2(32),
    PAYEEBANKCITY       varchar2(32),
    PAYEEBANKID         varchar2(32),
    PAYEEBANKDEPTID     varchar2(32),
    PAYEECIFTYPE        char(1),
    PAYEEACNAME         varchar2(150) not null,
    PAYEEBANKACTYPE     varchar2(4),
    PAYEEACNO           varchar2(40) not null,
    CITYCODE            varchar2(8),
    PAYEECURRENCYCODE   varchar2(3) not null,
    PAYEECRFLAG         char(1),
    TRSCURRENCYCODE     varchar2(3) not null,
    TRSCRFLAG           char(1),
    TRSAMOUNT           number(15,2) not null,
    NOTICEPAYEEFLAG     char(1),
    PAYEEMOBILE         varchar2(16),
    FUNDUSAGE           varchar2(128),
    ATTACHINFO          varchar2(640),
    TRSPASSWORD         varchar2(128),
    PRIORITY            char(1),
    PAYERCHANNEL        varchar2(4),
    MPFLAG              char(1),
    SAMECITYIFLAG       char(1),
    PAYEEBANKCLEARID    varchar2(16),
    SVCID               varchar2(64),
    ISADDTOPAYEEBOOK    char(1),
    PAYEEACID           varchar2(32),
    NOTICE              varchar2(256),
    PROCESSTIME         timestamp,
    BANKCODE            varchar2(32),
    PAYEEBANKPROVNAME   varchar2(32),
    PAYEEBANKCITYNAME   varchar2(32),
    PAYEEBANKIDNAME     varchar2(128),
    PAYEEBANKDEPTIDNAME varchar2(512),
    PARENTJNLNO         number(20,0),
    PAYERACNAME         varchar2(150),
    BATCHJNLNO          number(20,0),
    TRSTYPEFLAG         char(1),
    MOBILE              varchar2(12),
    ORDERID             number(20,0),
    ENTINNERNO          varchar2(40),
    TRANSDATE           timestamp,
    FEECODE             varchar2(32),
    FEEAMOUNT           number(15,2),
    CERTIFIEDDOC        varchar2(200),
    primary key (JNLNO)
);
drop table JTFCROSSBANKHIST cascade constraints;
create table JTFCROSSBANKHIST
(
    JNLNO               number(20,0) not null,
    SCHEDULEFLAG        char(1) not null,
    SCHEDULERULE        varchar2(128),
    PROCESSSTATE        varchar2(8) not null,
    PAYERBANKACTYPE     varchar2(4),
    PAYERACNO           varchar2(40) not null,
    CORECIFNO           varchar2(40),
    PAYERSUBACNO        varchar2(40),
    PAYERSUBACTYPE      varchar2(4),
    PAYERSUBACSEQ       number(0,-127),
    PAYERCURRENCYCODE   varchar2(3) not null,
    PAYERCRFLAG         char(1),
    PAYEEBANKPROV       varchar2(32),
    PAYEEBANKCITY       varchar2(32),
    PAYEEBANKID         varchar2(32),
    PAYEEBANKDEPTID     varchar2(32),
    PAYEECIFTYPE        char(1),
    PAYEEACNAME         varchar2(150) not null,
    PAYEEBANKACTYPE     varchar2(4),
    PAYEEACNO           varchar2(40) not null,
    CITYCODE            varchar2(8),
    PAYEECURRENCYCODE   varchar2(3) not null,
    PAYEECRFLAG         char(1),
    TRSCURRENCYCODE     varchar2(3) not null,
    TRSCRFLAG           char(1),
    TRSAMOUNT           number(15,2) not null,
    NOTICEPAYEEFLAG     char(1),
    PAYEEMOBILE         varchar2(16),
    FUNDUSAGE           varchar2(128),
    ATTACHINFO          varchar2(640),
    TRSPASSWORD         varchar2(128),
    PRIORITY            char(1),
    PAYERCHANNEL        varchar2(4),
    MPFLAG              char(1),
    SAMECITYIFLAG       char(1),
    PAYEEBANKCLEARID    varchar2(16),
    SVCID               varchar2(64),
    ISADDTOPAYEEBOOK    char(1),
    PAYEEACID           varchar2(32),
    NOTICE              varchar2(256),
    PROCESSTIME         timestamp,
    BANKCODE            varchar2(32),
    PAYEEBANKPROVNAME   varchar2(32),
    PAYEEBANKCITYNAME   varchar2(32),
    PAYEEBANKIDNAME     varchar2(128),
    PAYEEBANKDEPTIDNAME varchar2(512),
    PARENTJNLNO         number(20,0),
    PAYERACNAME         varchar2(150),
    BATCHJNLNO          number(20,0),
    TRSTYPEFLAG         char(1),
    MOBILE              varchar2(12),
    ORDERID             number(20,0),
    ENTINNERNO          varchar2(40),
    TRANSDATE           timestamp,
    FEECODE             varchar2(32),
    FEEAMOUNT           number(15,2),
    CERTIFIEDDOC        varchar2(200),
    DOCUMENTNUMBER      varchar2(15)
);
drop table JTFFOREIGNEXCHANGE cascade constraints;
create table JTFFOREIGNEXCHANGE
(
    JNLNO                      number(20,0) not null,
    CIFSEQ                     number(20,0) not null,
    PROCESSSTATE               varchar2(8) not null,
    PROCESSTIME                timestamp,
    TRANSDATE                  timestamp,
    PARENTJNLNO                number(20,0),
    BATCHJNLNO                 number(20,0),
    DCLRTYOTRY                 char(1) not null,
    PAYERACCOUNTNO             varchar2(40) not null,
    RMTNCCCY                   varchar2(3) not null,
    PAYEEACCOUNTNO             varchar2(40) not null,
    PAYEECURRENCYCODE          varchar2(3) not null,
    TRSAMOUNT                  number(15,2) not null,
    PAYEEACNAME                varchar2(256) not null,
    PAYEEBANKCODE              varchar2(32),
    PAYEEBANKNAME              varchar2(256),
    PAYEEBANKADDR              varchar2(1024),
    PAYEEBANKAGENCYBANKCODE    varchar2(32),
    PAYEEBANKAGENCYBANKNAME    varchar2(256),
    PAYEEBANKAGENCYBANKADDR    varchar2(1024),
    RMTPSTCR                   varchar2(630),
    PAYEXPENSE                 varchar2(150),
    PAYEERESIDENTCOUNTRY       varchar2(50),
    PAYTYPE                    varchar2(20),
    PYNATR                     varchar2(128),
    TSNCD                      varchar2(20),
    THECRSPNGCCY               varchar2(3),
    THECRSPNGAMT               number(15,2),
    TSNCD2                     varchar2(20),
    THECRSPNGCCY2              varchar2(3),
    THECRSPNGAMT2              number(15,2),
    HTHRGDSPYMT                varchar2(20),
    CTRNO                      varchar2(50),
    INVCNUM                    varchar2(50),
    BUSCODE                    varchar2(50),
    CERTIFIEDDOC               varchar2(1024) not null,
    CANCELFLAG                 char(1) not null,
    FEEDBACKCOMMENTS           varchar2(1024),
    BACKUPFIELD1               varchar2(50),
    BACKUPFIELD2               varchar2(50),
    BACKUPFIELD3               varchar2(50),
    BACKUPFIELD4               varchar2(50),
    ISADDTOPAYEEBOOK           char(1),
    SEQUENCE                   varchar2(1),
    INN                        varchar2(32),
    IECC                       varchar2(9),
    RUSSIANSORTCODE            varchar2(9),
    DOCUMENTNUMBER             varchar2(15),
    PAYEEACADDRESS             varchar2(255),
    VOCODE                     varchar2(5),
    CORRESPONDINGACCOUNT       varchar2(35),
    PAYEEKPPCODE               varchar2(9),
    RUBFLAG                    varchar2(1),
    PAYEEBANKAGENCYBANKACCOUNT varchar2(40),
    PAYMENTMETHOD              varchar2(300),
    BONDEDPAYMENT              char(1),
    TRANSCODE                  varchar2(20),
    TRANSREMARK                varchar2(300),
    BUSINESSDOC                varchar2(90),
    PAYMENTNATURE              varchar2(300),
    TRANSREMARK1               varchar2(300),
    PAYEECORRESPONDINGACCOUNT  varchar2(35)
);
drop table JTFFOREIGNEXCHANGEHIST cascade constraints;
create table JTFFOREIGNEXCHANGEHIST
(
    JNLNO                      number(20,0) not null,
    CIFSEQ                     number(20,0) not null,
    PROCESSSTATE               varchar2(8) not null,
    PROCESSTIME                timestamp,
    TRANSDATE                  timestamp,
    PARENTJNLNO                number(20,0),
    BATCHJNLNO                 number(20,0),
    DCLRTYOTRY                 char(1) not null,
    PAYERACCOUNTNO             varchar2(40) not null,
    RMTNCCCY                   varchar2(3) not null,
    PAYEEACCOUNTNO             varchar2(40) not null,
    PAYEECURRENCYCODE          varchar2(3) not null,
    TRSAMOUNT                  number(15,2) not null,
    PAYEEACNAME                varchar2(256) not null,
    PAYEEBANKCODE              varchar2(32),
    PAYEEBANKNAME              varchar2(256),
    PAYEEBANKADDR              varchar2(1024),
    PAYEEBANKAGENCYBANKCODE    varchar2(32),
    PAYEEBANKAGENCYBANKNAME    varchar2(256),
    PAYEEBANKAGENCYBANKADDR    varchar2(1024),
    RMTPSTCR                   varchar2(630),
    PAYEXPENSE                 varchar2(150),
    PAYEERESIDENTCOUNTRY       varchar2(50),
    PAYTYPE                    varchar2(20),
    PYNATR                     varchar2(128),
    TSNCD                      varchar2(20),
    THECRSPNGCCY               varchar2(3),
    THECRSPNGAMT               number(15,2),
    TSNCD2                     varchar2(20),
    THECRSPNGCCY2              varchar2(3),
    THECRSPNGAMT2              number(15,2),
    HTHRGDSPYMT                varchar2(20),
    CTRNO                      varchar2(50),
    INVCNUM                    varchar2(50),
    BUSCODE                    varchar2(50),
    CERTIFIEDDOC               varchar2(1024) not null,
    CANCELFLAG                 char(1) not null,
    FEEDBACKCOMMENTS           varchar2(1024),
    BACKUPFIELD1               varchar2(50),
    BACKUPFIELD2               varchar2(50),
    BACKUPFIELD3               varchar2(50),
    BACKUPFIELD4               varchar2(50),
    ISADDTOPAYEEBOOK           char(1),
    SEQUENCE                   varchar2(1),
    INN                        varchar2(32),
    IECC                       varchar2(9),
    RUSSIANSORTCODE            varchar2(9),
    DOCUMENTNUMBER             varchar2(15),
    PAYEEACADDRESS             varchar2(255),
    VOCODE                     varchar2(5),
    CORRESPONDINGACCOUNT       varchar2(35),
    PAYEEKPPCODE               varchar2(9),
    RUBFLAG                    varchar2(1),
    PAYEEBANKAGENCYBANKACCOUNT varchar2(40),
    PAYMENTMETHOD              varchar2(300),
    BONDEDPAYMENT              char(1),
    TRANSCODE                  varchar2(20),
    TRANSREMARK                varchar2(300),
    BUSINESSDOC                varchar2(90),
    PAYMENTNATURE              varchar2(300),
    TRANSREMARK1               varchar2(300),
    PAYEECORRESPONDINGACCOUNT  varchar2(255)
);
drop table JTFSIGNATURETRACE cascade constraints;
create table JTFSIGNATURETRACE
(
    JNLNO         number(20,0) not null,
    CIFSEQ        number(20,0),
    USERSEQ       number(20,0),
    PUBCERTKEY    clob,
    TRANSACTIONID varchar2(50),
    SIGNDATA      clob,
    SIGNATURE     clob,
    TRANSDATE     timestamp,
    primary key (JNLNO)
);
drop table JTFTRANSFERCANCEL cascade constraints;
create table JTFTRANSFERCANCEL
(
    JNLNO        number(20,0) not null,
    PROCESSSTATE varchar2(8) not null,
    CIFSEQ       number(20,0),
    USERSEQ      number(20,0),
    PARENTJNLNO  number(20,0),
    PROCESSTIME  timestamp,
    TRANSDATE    timestamp,
    REMARK       varchar2(512) not null,
    CODE         varchar2(256) not null,
    primary key (JNLNO)
);
drop table JTFTRANSFERCANCELHIST cascade constraints;
create table JTFTRANSFERCANCELHIST
(
    JNLNO        number(20,0) not null,
    PROCESSSTATE varchar2(8) not null,
    CIFSEQ       number(20,0),
    USERSEQ      number(20,0),
    PARENTJNLNO  number(20,0),
    PROCESSTIME  timestamp,
    TRANSDATE    timestamp,
    REMARK       varchar2(512) not null,
    CODE         varchar2(256) not null
);
drop table JTFTRANSFERFEEDBACK cascade constraints;
create table JTFTRANSFERFEEDBACK
(
    JNLNO    number(20,0) not null,
    FEEDBACK varchar2(1024) not null,
    primary key (JNLNO)
);
drop table JVOUCHERJNL cascade constraints;
create table JVOUCHERJNL
(
    JNLNO         number(20,0) not null,
    MODULEID      varchar2(16),
    CIFSEQ        number(20,0),
    PRINTCOUNT    number(0,-127),
    LASTPRINTDATE timestamp,
    CREATEDATE    timestamp,
    DEPTSEQ       number(20,0),
    PRDID         varchar2(64),
    USERSEQ       number(20,0),
    CHECKUSERSEQ  number(20,0),
    SERVICEID     varchar2(64),
    TRANSTIME     timestamp,
    primary key (JNLNO)
);
drop table JVOUCHERPRINT cascade constraints;
create table JVOUCHERPRINT
(
    JNLNO         number(20,0) not null,
    MODULEID      varchar2(16),
    PRINTCOUNT    number(0,-127),
    TRANSDATA     blob,
    CIFSEQ        number(20,0),
    LASTPRINTDATE timestamp,
    CREATEDATE    timestamp,
    primary key (JNLNO)
);
drop table MCACLIMIT cascade constraints;
create table MCACLIMIT
(
    CIFSEQ        varchar2(20) not null,
    ACNO          varchar2(40) not null,
    MCHANNELID    varchar2(16) not null,
    DEPTSEQ       varchar2(16) not null,
    LOGINTYPE     char(1) not null,
    CURRENCY      varchar2(3) not null,
    CIFLEVEL      varchar2(8) not null,
    PRDID         varchar2(64) not null,
    ACTYPE        char(1) not null,
    LIMITTYPE     varchar2(128) not null,
    LIMITAMOUNT   number(15,2),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    CERTTYPE      varchar2(20),
    primary key (CIFSEQ, ACNO, MCHANNELID, DEPTSEQ, LOGINTYPE, CURRENCY, CIFLEVEL, PRDID, ACTYPE, LIMITTYPE)
);
drop table MCAPPLOCK cascade constraints;
create table MCAPPLOCK
(
    LOCKTYPE  varchar2(64) not null,
    LOCKID    varchar2(128) not null,
    JNLNO     number(20,0),
    TRANSDATE timestamp,
    TRANSTIME timestamp,
    primary key (LOCKTYPE, LOCKID)
);
drop table MCAPSBANK cascade constraints;
create table MCAPSBANK
(
    BANKNO        varchar2(16) not null,
    BANKNAME      varchar2(128),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (BANKNO)
);
drop table MCAPSCITY cascade constraints;
create table MCAPSCITY
(
    CITYCD        varchar2(16) not null,
    CITYNAME      varchar2(128),
    PROVCD        varchar2(16),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (CITYCD)
);
drop table MCAPSPROVINCE cascade constraints;
create table MCAPSPROVINCE
(
    PROVCD        varchar2(16) not null,
    PROVNAME      varchar2(64),
    PROVTYPE      char(1),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (PROVCD)
);
drop table MCAPSRTGSNODE cascade constraints;
create table MCAPSRTGSNODE
(
    BANKCODE        varchar2(20) not null,
    STATUS          varchar2(1),
    CATEGORY        varchar2(2),
    CLSCODE         varchar2(3),
    DRECCODE        varchar2(12),
    NODECODE        varchar2(5),
    SUPRLIST        varchar2(130),
    PBCCODE         varchar2(12),
    CITYCODE        varchar2(4),
    ACCTSTATUS      varchar2(1),
    ASALTDT         varchar2(8),
    ASALTTM         varchar2(14),
    LNAME           varchar2(255),
    SNAME           varchar2(80),
    ADDR            varchar2(255),
    POSTCODE        varchar2(6),
    TEL             varchar2(90),
    EMAIL           varchar2(255),
    EFFDATE         varchar2(8),
    INVDATE         varchar2(8),
    ALTDATE         varchar2(20),
    ALTTYPE         varchar2(1),
    ALTISSNO        varchar2(8),
    REMARK          varchar2(180),
    ALTFLAG         char(1),
    LARGEAMOUNTFLAG char(1),
    SMALLAMOUNTFLAG char(1),
    primary key (BANKCODE)
);
drop table MCAPSRTGSNODEE cascade constraints;
create table MCAPSRTGSNODEE
(
    BANKCODE        varchar2(20),
    PAYCODE         varchar2(20),
    STATUS          varchar2(1),
    CATEGORY        varchar2(2),
    CLSCODE         varchar2(3),
    DRECCODE        varchar2(12),
    NODECODE        varchar2(5),
    SUPRLIST        varchar2(130),
    PBCCODE         varchar2(12),
    CITYCODE        varchar2(4),
    ACCTSTATUS      varchar2(1),
    ASALTDT         varchar2(8),
    ASALTTM         varchar2(14),
    LNAME           varchar2(255),
    SNAME           varchar2(80),
    ADDR            varchar2(255),
    POSTCODE        varchar2(6),
    TEL             varchar2(90),
    EMAIL           varchar2(255),
    EFFDATE         varchar2(8),
    INVDATE         varchar2(8),
    ALTDATE         varchar2(20),
    ALTTYPE         varchar2(1),
    ALTISSNO        varchar2(8),
    REMARK          varchar2(180),
    ALTFLAG         char(1),
    LARGEAMOUNTFLAG char(1),
    SMALLAMOUNTFLAG char(1)
);
drop table MCAPSRTGSNODES cascade constraints;
create table MCAPSRTGSNODES
(
    SWIFTCODE  varchar2(32),
    STATUS     varchar2(32),
    CATEGORY   varchar2(2),
    NODECODE   varchar2(5),
    SUPRLIST   varchar2(130),
    PBCCODE    varchar2(12),
    CITYCODE   varchar2(8),
    ACCTSTATUS varchar2(1),
    ASALTDT    varchar2(8),
    ASALTTM    varchar2(14),
    LNAME      varchar2(255),
    SNAME      varchar2(80),
    ADDR       varchar2(255),
    POSTCODE   varchar2(6),
    TEL        varchar2(90),
    EMAIL      varchar2(255),
    EFFDATE    varchar2(8),
    INVDATE    varchar2(8),
    ALTDATE    varchar2(20),
    ALTTYPE    varchar2(1),
    ALTISSNO   varchar2(8),
    REMARK     varchar2(180),
    ALTFLAG    char(1),
    BANKNAME   varchar2(256)
);
drop table MCCALBACKSETTING cascade constraints;
create table MCCALBACKSETTING
(
    CURRENCY   varchar2(10),
    AMOUNT     varchar2(20) not null,
    CREATETIME timestamp,
    UPDATETIME timestamp
);
drop table MCCIFLIMIT cascade constraints;
create table MCCIFLIMIT
(
    CIFSEQ        varchar2(20) not null,
    MCHANNELID    varchar2(16) not null,
    LOGINTYPE     char(1) not null,
    PAYERACNO     varchar2(40) not null,
    PAYEEACNO     varchar2(40) not null,
    CURRENCY      varchar2(3) not null,
    CIFLEVEL      varchar2(8) not null,
    PRDID         varchar2(64) not null,
    LIMITTYPE     varchar2(128) not null,
    LIMITAMOUNT   number(15,2) not null,
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (CIFSEQ, MCHANNELID, LOGINTYPE, PAYERACNO, PAYEEACNO, CURRENCY, CIFLEVEL, PRDID, LIMITTYPE)
);
drop table MCCNTGROUNDJNL cascade constraints;
create table MCCNTGROUNDJNL
(
    JNLNO            number(15,0) not null,
    PAYERACCOUNT     varchar2(64) not null,
    PAYERACCOUNTNAME varchar2(64),
    PAYEEACCOUNT     varchar2(64) not null,
    PAYEEACCOUNTNAME varchar2(64),
    TRSAMOUNT        number(15,2) not null,
    QUETATIONSID     number(15,2),
    QUETATIONBID     number(15,2),
    FILEPATH         varchar2(64),
    CURRENCYPAIR     varchar2(15) not null,
    FXCODE           varchar2(32),
    DCODE            varchar2(32),
    CREATETIME       timestamp,
    UPDATETIME       timestamp,
    FILESTATUS       varchar2(5) not null,
    USETIMES         varchar2(1),
    PARENTJNLNO      number(15,0),
    primary key (JNLNO)
);
drop table MCCODE cascade constraints;
create table MCCODE
(
    CODETYPE      varchar2(32) not null,
    CODE          varchar2(16) not null,
    DESCRIPTION   varchar2(128) not null,
    STATUS        char(1) not null,
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (CODETYPE, CODE)
);
drop table MCDRAFT cascade constraints;
create table MCDRAFT
(
    JNLSEQ        number(20,0),
    CIFSEQ        number(20,0) not null,
    USERSEQ       number(20,0) not null,
    CREATETIME    timestamp,
    UPDATETIME    timestamp,
    CONTENT       blob,
    TEMPLATE      varchar2(1),
    NAME          varchar2(64),
    TRANSACTIONID varchar2(30)
);
drop table MCGROUND cascade constraints;
create table MCGROUND
(
    PRODUCTID  varchar2(50),
    RULEID     varchar2(20) not null,
    CREATETIME timestamp,
    UPDATETIME timestamp,
    CURRENCY   varchar2(10)
);
drop table MCGROUNDDEPT cascade constraints;
create table MCGROUNDDEPT
(
    DEPTSEQ       number(20,0) not null,
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (DEPTSEQ)
);
drop table MCGROUNDSETTING cascade constraints;
create table MCGROUNDSETTING
(
    RULEID        number(20,0) not null,
    LOADCOUNT     number(6,0),
    CHANNELID     varchar2(16),
    MODULEID      varchar2(16),
    GROUNDTYPE    varchar2(16),
    EFFECTDATE    timestamp,
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (RULEID)
);
drop table MCGROUNDSETTINGDETAIL cascade constraints;
create table MCGROUNDSETTINGDETAIL
(
    RULEID        number(20,0) not null,
    CHANNELID     varchar2(16),
    MODULEID      varchar2(16),
    GROUNDTYPE    varchar2(16),
    CIFNO         varchar2(40),
    ACNO          varchar2(40),
    AMOUNT        number(15,2),
    CIFIP         varchar2(32),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    CURRENCY      varchar2(10)
);
drop table MCHANNEL cascade constraints;
create table MCHANNEL
(
    MCHANNELID    varchar2(16) not null,
    MODULEID      varchar2(16) not null,
    CHANNELID     varchar2(4) not null,
    MCHANNELSTATE char(1) not null,
    primary key (MCHANNELID)
);
drop table MCJNL cascade constraints;
create table MCJNL
(
    JNLNO            number(20,0) not null,
    MCHANNELID       varchar2(16) not null,
    LOGINTYPE        char(1),
    TERMINALTYPE     varchar2(5),
    TERMINALID       varchar2(20),
    DEPTSEQ          number(20,0),
    CIFSEQ           number(20,0),
    USERSEQ          number(20,0),
    TRANSDATE        timestamp not null,
    TRANSTIME        timestamp not null,
    TRANSRESPTIME    timestamp,
    TRANSCODE        varchar2(64) not null,
    SERVICEID        varchar2(64),
    PRDID            varchar2(64),
    JNLSTATE         char(1)   not null,
    RETURNCODE       varchar2(64),
    RETURNMSG        varchar2(1024),
    CHANNELTRANSTIME timestamp,
    CHANNELJNLNO     varchar2(64),
    PARENTJNLNO      number(20,0),
    LOCALADDR        varchar2(32),
    SYSTEMTYPE       varchar2(8),
    RESPONSEJNLNO    varchar2(64),
    PAYERACNO        varchar2(40),
    PAYERBANK        varchar2(128),
    PAYERACNAME      varchar2(150),
    PAYEEACNO        varchar2(40),
    PAYEEBANK        varchar2(255),
    PAYEEACNAME      varchar2(150),
    AMOUNT           number(15,2),
    CURRENCY         varchar2(3),
    REMARK           varchar2(200),
    CUSTOMERCIFSEQ   number(20,0),
    CUSTOMERNAME     varchar2(150),
    UUID             varchar2(64),
    PROCESSTIME      timestamp,
    AUTHMODE         varchar2(10),
    primary key (JNLNO)
);
drop table MCJNLDATA cascade constraints;
create table MCJNLDATA
(
    JNLNO       number(20,0) not null,
    TRANSDATE   timestamp not null,
    JNLDATATYPE char(1)   not null,
    JNLDATA     blob      not null,
    primary key (JNLNO, JNLDATATYPE)
);
drop table MCJNLDATAHIST cascade constraints;
create table MCJNLDATAHIST
(
    JNLNO       number(20,0) not null,
    TRANSDATE   timestamp not null,
    JNLDATATYPE char(1)   not null,
    JNLDATA     blob      not null,
    primary key (JNLNO, JNLDATATYPE)
);
drop table MCJNLHIST cascade constraints;
create table MCJNLHIST
(
    JNLNO            number(20,0) not null,
    MCHANNELID       varchar2(16) not null,
    LOGINTYPE        char(1),
    TERMINALTYPE     varchar2(5),
    TERMINALID       varchar2(20),
    DEPTSEQ          number(20,0),
    CIFSEQ           number(20,0),
    USERSEQ          number(20,0),
    TRANSDATE        timestamp not null,
    TRANSTIME        timestamp not null,
    TRANSRESPTIME    timestamp,
    TRANSCODE        varchar2(64) not null,
    SERVICEID        varchar2(64),
    PRDID            varchar2(64),
    JNLSTATE         char(1)   not null,
    RETURNCODE       varchar2(64),
    RETURNMSG        varchar2(1024),
    CHANNELTRANSTIME timestamp,
    CHANNELJNLNO     varchar2(64),
    PARENTJNLNO      number(20,0),
    LOCALADDR        varchar2(32),
    SYSTEMTYPE       varchar2(8),
    RESPONSEJNLNO    varchar2(64),
    PAYERACNO        varchar2(40),
    PAYERBANK        varchar2(128),
    PAYERACNAME      varchar2(150),
    PAYEEACNO        varchar2(40),
    PAYEEBANK        varchar2(255),
    PAYEEACNAME      varchar2(150),
    AMOUNT           number(15,2),
    CURRENCY         varchar2(3),
    REMARK           varchar2(200),
    CUSTOMERCIFSEQ   number(20,0),
    CUSTOMERNAME     varchar2(150),
    UUID             varchar2(64),
    PROCESSTIME      timestamp,
    AUTHMODE         varchar2(10),
    primary key (JNLNO)
);
drop table MCJNLQUERYLOG cascade constraints;
create table MCJNLQUERYLOG
(
    JNLNO        number(20,0) not null,
    MCHANNELID   varchar2(16) not null,
    LOGINTYPE    char(1),
    TERMINALTYPE varchar2(5),
    TERMINALID   varchar2(20),
    DEPTSEQ      number(20,0),
    CIFSEQ       number(20,0),
    USERSEQ      number(20,0),
    ACNO         varchar2(40),
    BANKACTYPE   varchar2(4),
    TRANSDATE    timestamp not null,
    TRANSTIME    timestamp,
    RETURNTIME   timestamp,
    JNLSTATE     char(1)   not null,
    PRDID        varchar2(64),
    TRANSCODE    varchar2(64),
    SERVICEID    varchar2(64),
    CHANNELJNLNO varchar2(64),
    RETURNCODE   varchar2(1024),
    RETURNMSG    varchar2(1024),
    UUID         varchar2(64),
    primary key (JNLNO)
);
drop table MCJNLQUERYLOGHIST cascade constraints;
create table MCJNLQUERYLOGHIST
(
    JNLNO        number(20,0) not null,
    MCHANNELID   varchar2(16) not null,
    LOGINTYPE    char(1),
    TERMINALTYPE varchar2(5),
    TERMINALID   varchar2(20),
    DEPTSEQ      number(20,0),
    CIFSEQ       number(20,0),
    USERSEQ      number(20,0),
    ACNO         varchar2(40),
    BANKACTYPE   varchar2(4),
    TRANSDATE    timestamp not null,
    TRANSTIME    timestamp,
    RETURNTIME   timestamp,
    JNLSTATE     char(1)   not null,
    PRDID        varchar2(64),
    TRANSCODE    varchar2(64),
    SERVICEID    varchar2(64),
    CHANNELJNLNO varchar2(64),
    RETURNCODE   varchar2(1024),
    RETURNMSG    varchar2(1024),
    UUID         varchar2(64),
    primary key (JNLNO)
);
drop table MCROLE cascade constraints;
create table MCROLE
(
    MCHANNELID    varchar2(16) not null,
    CIFTYPE       varchar2(2) not null,
    ROLEID        varchar2(32) not null,
    ROLENAME      varchar2(32),
    ROLESTATE     char(1),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (MCHANNELID, CIFTYPE, ROLEID)
);
drop table MCROLEPRODUCT cascade constraints;
create table MCROLEPRODUCT
(
    MCHANNELID    varchar2(16) not null,
    CIFTYPE       varchar2(2) not null,
    ROLEID        varchar2(32) not null,
    PRDID         varchar2(64) not null,
    AUTHFLAG      varchar2(1),
    ENTERFLAG     varchar2(1),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (MCHANNELID, CIFTYPE, ROLEID, PRDID)
);
drop table MCSCHEDULERULE cascade constraints;
create table MCSCHEDULERULE
(
    JNLNO            number(20,0) not null,
    SEQNO            number(0,-127) not null,
    MODULEID         varchar2(16),
    TRANSDATE        timestamp,
    TIMERFREQ        char(1),
    TIMERRULE        varchar2(256),
    BEGINDATE        timestamp,
    ENDDATE          timestamp,
    ORDERTIMES       number(0,-127),
    EXETIMES         number(0,-127),
    TIMERSTATE       char(1),
    LOGINTYPE        char(1),
    TERMINALTYPE     varchar2(5),
    TERMINALID       varchar2(20),
    DEPTSEQ          number(20,0),
    CIFSEQ           number(20,0),
    USERSEQ          number(20,0),
    TRANSTIME        timestamp not null,
    TRANSCODE        varchar2(64) not null,
    SERVICEID        varchar2(64),
    PRDID            varchar2(64),
    CHANNELTRANSTIME timestamp,
    CHANNELJNLNO     varchar2(64),
    primary key (JNLNO, SEQNO)
);
drop table MCSERVICESWITCH cascade constraints;
create table MCSERVICESWITCH
(
    MCHANNELID    varchar2(16) not null,
    PRDID         varchar2(64) not null,
    CLOSEDATE     timestamp,
    CLOSETIME     varchar2(8),
    REOPENDATE    timestamp,
    REOPENTIME    varchar2(8),
    CLOSENOTICE   varchar2(128),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (MCHANNELID, PRDID)
);
drop table MCTRSTIME cascade constraints;
create table MCTRSTIME
(
    MCHANNELID       varchar2(16) not null,
    PRDID            varchar2(64) not null,
    DEPTSEQ          number(20,0) not null,
    OPENTIME         varchar2(5),
    CLOSETIME        varchar2(5),
    CLOSENOTICE      varchar2(128),
    HOLIDAYCLOSEFLAG char(1),
    CLOSEPROCFLAG    char(1),
    CREATEUSERSEQ    number(20,0),
    CREATEDEPTSEQ    number(20,0),
    CREATETIME       timestamp,
    UPDATEUSERSEQ    number(20,0),
    UPDATEDEPTSEQ    number(20,0),
    UPDATETIME       timestamp,
    primary key (MCHANNELID, PRDID, DEPTSEQ)
);
drop table MINFOPUBLISHITEM cascade constraints;
create table MINFOPUBLISHITEM
(
    INFOPLANID  varchar2(16) not null,
    ITEMID      varchar2(8) not null,
    ITEMCONTENT blob,
    primary key (INFOPLANID, ITEMID)
);
drop table MINFOPUBLISHPLAN cascade constraints;
create table MINFOPUBLISHPLAN
(
    INFOPLANID    varchar2(16) not null,
    INFOPLANNAME  varchar2(64) not null,
    CHANNELID     varchar2(4),
    MODULEID      varchar2(16),
    PLANLOCATION  varchar2(16),
    INFOPLANTYPE  char(1),
    PLAYTYPE      char(1),
    CIFLEVEL      varchar2(1024),
    CIFGROUP      varchar2(1024),
    PRODUCT       long,
    STARTDATE     timestamp,
    ENDDATE       timestamp,
    INFOPLANSTATE char(1),
    INFOPLANDESC  varchar2(600),
    NOTE1         varchar2(600),
    NOTE2         varchar2(600),
    CONTENT       blob,
    PAGENO        varchar2(16),
    LOCATIONNO    varchar2(16),
    SENDRANGE     varchar2(8),
    SENDTYPE      varchar2(8),
    CIFSEQ        number(20,0),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (INFOPLANID)
);
drop table MINFOPUBLISHRULE cascade constraints;
create table MINFOPUBLISHRULE
(
    MCHANNELID   varchar2(16) not null,
    PLANLOCATION varchar2(16) not null,
    INFOPLANTYPE char(1),
    WORDLIMIT    varchar2(16),
    IMGLIMITSIZE varchar2(16),
    OTHERLIMIT   varchar2(100),
    primary key (MCHANNELID, PLANLOCATION)
);
drop table MODULE cascade constraints;
create table MODULE
(
    MODULEID     varchar2(16) not null,
    MODULENAME   varchar2(64) not null,
    MODULEABBRID varchar2(8) not null,
    primary key (MODULEID)
);
drop table MONTHREPORTJOBRECORDS cascade constraints;
create table MONTHREPORTJOBRECORDS
(
    JOB_MONTH    varchar2(10) not null,
    JOB_TRS_TIME timestamp,
    primary key (JOB_MONTH)
);
drop table MONTHREPORTRECORDS cascade constraints;
create table MONTHREPORTRECORDS
(
    CIFSEQ       varchar2(20) not null,
    YEARS        varchar2(10),
    MONTHS       varchar2(10),
    JNLNO        varchar2(20),
    COMPLETE     varchar2(10),
    DOWNLOAD     varchar2(10),
    BDATE        varchar2(10),
    EDATE        varchar2(10),
    FILEDOWNLOAD varchar2(10)
);
drop table MSGBOARD cascade constraints;
create table MSGBOARD
(
    MSGNO          varchar2(20) not null,
    MSGASKER       varchar2(40),
    ASKERPHONE     varchar2(20),
    ASKEREMAIL     varchar2(30),
    ASKTPYE        varchar2(100),
    TITLE          varchar2(360),
    MSGSTATE       varchar2(2),
    INSERTUSERID   varchar2(20),
    INSERTDATE     timestamp,
    UPDATEDATE     timestamp,
    REPLYWAY       varchar2(2),
    REPLYCONTENT   varchar2(1500),
    REPLYER        varchar2(20),
    MSGTYPE        varchar2(5),
    REPLYMSGNO     varchar2(20),
    REPLYDATE      timestamp,
    PATH           varchar2(100),
    TITLECONTENT   clob,
    READ_ALREADY   varchar2(5),
    CANCELSTATE    varchar2(5) not null,
    MSGTYPECONTENT varchar2(360),
    RECEIVER       varchar2(20) not null,
    CUSTOMERNAME   varchar2(128),
    CUSTOMERSEQ    varchar2(20),
    FILESNAME      long,
    REPLYPATH      varchar2(100),
    REPLYFILESNAME long,
    RECEIVERNAME   varchar2(128),
    primary key (MSGNO)
);
drop table MSGBOARDHIS cascade constraints;
create table MSGBOARDHIS
(
    MSGNO          varchar2(20) not null,
    MSGASKER       varchar2(40),
    ASKERPHONE     varchar2(20),
    ASKEREMAIL     varchar2(30),
    ASKTPYE        varchar2(100),
    TITLE          varchar2(360),
    MSGSTATE       varchar2(2),
    INSERTUSERID   varchar2(20),
    INSERTDATE     timestamp,
    UPDATEDATE     timestamp,
    OPERTYPE       varchar2(20),
    REPLYWAY       varchar2(2),
    REPLYCONTENT   varchar2(500),
    REPLYER        varchar2(20),
    MSGTYPE        varchar2(5),
    REPLYMSGNO     varchar2(20),
    REPLYDATE      timestamp,
    TITLECONTENT   clob,
    CUSTOMERNAME   varchar2(128),
    CUSTOMERSEQ    varchar2(20),
    FILESNAME      long,
    REPLYPATH      varchar2(100),
    REPLYFILESNAME long
);
drop table PASSWORD_RULE cascade constraints;
create table PASSWORD_RULE
(
    RULE_ID            varchar2(100) not null,
    RULE_TYPE          varchar2(10),
    RULE_NAME_CN       varchar2(200),
    RULE_NAME_EN       varchar2(200),
    RULE_VALUE         varchar2(10),
    RULE_REGULAR       varchar2(200),
    RULE_DESC_CN       varchar2(200),
    RULE_DESC_EN       varchar2(200),
    RULE_ERROR_INFO_EN varchar2(200),
    RULE_ERROR_INFO_CN varchar2(200),
    primary key (RULE_ID)
);
drop table PRDSET cascade constraints;
create table PRDSET
(
    PRDSETID     varchar2(64) not null,
    MODULEID     varchar2(16),
    PRDSETTYPEID varchar2(32) not null,
    PRDSETNAME   varchar2(64),
    PARENTID     varchar2(64),
    ORDERINDEX   number(0,-127),
    primary key (PRDSETID)
);
drop table PRDSETPRD cascade constraints;
create table PRDSETPRD
(
    PRDSETID varchar2(64) not null,
    PRDID    varchar2(64) not null,
    primary key (PRDSETID, PRDID)
);
drop table PRODCH cascade constraints;
create table PRODCH
(
    PRODID      varchar2(64) not null,
    CHANNELID   varchar2(4) not null,
    MGMTPRDFLAG char(1),
    primary key (PRODID, CHANNELID)
);
drop table PRODUCT_MENU cascade constraints;
create table PRODUCT_MENU
(
    TRANS_NAME varchar2(50),
    PRD_ID     varchar2(128)
);
drop table PRODUCT_WHITELIST cascade constraints;
create table PRODUCT_WHITELIST
(
    PRODUCT_ID varchar2(256),
    CIF_ID     varchar2(20) not null,
    TRANS_NAME varchar2(20) not null,
    primary key (CIF_ID, TRANS_NAME)
);
drop table RISKTRSHIST cascade constraints;
create table RISKTRSHIST
(
    TRANSID           number(10,0) not null,
    TRANSACTIONNAME   varchar2(20),
    PAYOUTACCOUNTNO   varchar2(20),
    PAYOUTACCOUNTNAME varchar2(20),
    TRANSACTIONAMOUNT number(20,0),
    PAYINACCOUNTNO    varchar2(20),
    PAYINACCOUNTNAME  varchar2(20),
    TRADETIME         timestamp not null,
    STATUS            varchar2(10),
    ELSEDOCUMENT      varchar2(20),
    ECONSUMER         varchar2(20),
    EBANKCUSTNO       varchar2(20),
    ENDREASON         varchar2(30),
    primary key (TRANSID)
);
drop table RJNL cascade constraints;
create table RJNL
(
    JNLNO       number(20,0),
    TRANSDATE   timestamp,
    TRANSTIME   timestamp,
    UUID        varchar2(64) not null,
    MCUUID      varchar2(64),
    HOSTUUID    varchar2(64),
    RETURNTIME  timestamp,
    HOSTTRSCODE varchar2(64),
    RETURNCODE  varchar2(64),
    RETURNMSG   varchar2(1024),
    SPNAME      varchar2(64),
    primary key (UUID)
);
drop table RJNLHIST cascade constraints;
create table RJNLHIST
(
    JNLNO       number(20,0),
    TRANSDATE   timestamp,
    TRANSTIME   timestamp,
    UUID        varchar2(64) not null,
    MCUUID      varchar2(64),
    HOSTUUID    varchar2(64),
    RETURNTIME  timestamp,
    HOSTTRSCODE varchar2(64),
    RETURNCODE  varchar2(64),
    RETURNMSG   varchar2(1024),
    SPNAME      varchar2(64),
    primary key (UUID)
);
drop table RULE cascade constraints;
create table RULE
(
    MODULEID      varchar2(16) not null,
    RULENAMESPACE varchar2(64) not null,
    RULETYPE      varchar2(16) not null,
    RULEID        varchar2(64) not null,
    RULEDEF       varchar2(1024),
    RULESCRIPT    varchar2(2048),
    PRDID         varchar2(64),
    DESCRIPTION   varchar2(256),
    LASTUPDUSER   number(20,0),
    LASTUPDTIME   timestamp,
    primary key (MODULEID, RULENAMESPACE, RULETYPE, RULEID)
);
drop table SHENTACAUTH cascade constraints;
create table SHENTACAUTH
(
    ACSEQ         number(20,0),
    PRDID         varchar2(200),
    CIFSEQ        number(20,0),
    L1COUNT       number(0,-127),
    L2COUNT       number(0,-127),
    L3COUNT       number(0,-127),
    L4COUNT       number(0,-127),
    L5COUNT       number(0,-127),
    L6COUNT       number(0,-127),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    MAXAMOUNT     number(15,2),
    MINAMOUNT     number(15,2),
    UPDATETIME    timestamp,
    CURRENCY      varchar2(10)
);
drop table SHENTPRDAUTH cascade constraints;
create table SHENTPRDAUTH
(
    CIFSEQ        number(20,0) not null,
    PRDID         varchar2(64) not null,
    L1COUNT       number(0,-127),
    L2COUNT       number(0,-127),
    L3COUNT       number(0,-127),
    L4COUNT       number(0,-127),
    L5COUNT       number(0,-127),
    L6COUNT       number(0,-127),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (CIFSEQ, PRDID)
);
drop table SHENTUSRACAUTH cascade constraints;
create table SHENTUSRACAUTH
(
    USERSEQ       number(20,0),
    ACSEQ         number(20,0),
    PRDID         varchar2(64),
    CIFSEQ        number(20,0),
    AUTHLEVEL     number(0,-127),
    MINAMOUNT     number(15,2),
    MAXAMOUNT     number(15,2),
    FINALAMOUNT   number(15,2),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    CURRENCY      varchar2(10)
);
drop table SHENTUSRACAUTHHIST cascade constraints;
create table SHENTUSRACAUTHHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    USERSEQ         number(20,0) not null,
    ACSEQ           number(20,0) not null,
    AUTHLEVEL       number(0,-127),
    MINAMOUNT       number(15,2),
    MAXAMOUNT       number(15,2),
    FINALAMOUNT     number(15,2)
);
drop table SHENTUSRPRDAUTH cascade constraints;
create table SHENTUSRPRDAUTH
(
    CIFSEQ        number(20,0) not null,
    PRDID         varchar2(64) not null,
    USERSEQ       number(20,0) not null,
    AUTHLEVEL     number(0,-127),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp,
    primary key (CIFSEQ, PRDID, USERSEQ)
);
drop table SHENTUSRPRDAUTHHIST cascade constraints;
create table SHENTUSRPRDAUTHHIST
(
    MAINTJNLNO      number(20,0) not null,
    MAINTCODE       varchar2(4) not null,
    MAINTMCHANNELID varchar2(16) not null,
    MAINTMCHJNLNO   varchar2(64) not null,
    MAINTDEPTSEQ    number(20,0),
    MAINTCIFSEQ     number(20,0),
    MAINTUSERSEQ    number(20,0),
    MAINTDATE       timestamp not null,
    MAINTTIMESTAMP  timestamp not null,
    CIFSEQ          number(20,0) not null,
    PRDID           varchar2(64) not null,
    USERSEQ         number(20,0) not null,
    AUTHLEVEL       number(0,-127)
);
drop table SIGNTRSTRACEINFO cascade constraints;
create table SIGNTRSTRACEINFO
(
    JNLNO         number(20,0) not null,
    CIFSEQ        number(20,0),
    USERSEQ       number(20,0),
    PUBCERTKEY    clob,
    TRANSACTIONID varchar2(50),
    SIGNDATA      clob,
    SIGNATURE     clob,
    primary key (JNLNO)
);
drop table SWIFTCODETABLE cascade constraints;
create table SWIFTCODETABLE
(
    SWIFT_CODE          varchar2(8) not null,
    BRANCH_CODE         varchar2(3) not null,
    TAG                 varchar2(10),
    INSTITUTION_NAME    varchar2(255),
    CITY_HEADING        varchar2(255),
    SUBTYPE_INDICATION  varchar2(255),
    VALUE_ADDED_SERVICE varchar2(255),
    EXTRA_INFO          varchar2(1024),
    PHYSICAL_ADDRESS_1  varchar2(255),
    PHYSICAL_ADDRESS_2  varchar2(255),
    PHYSICAL_ADDRESS_3  varchar2(255),
    PHYSICAL_ADDRESS_4  varchar2(255),
    LOCATION            varchar2(255),
    COUNTRY_NAME        varchar2(255),
    POB_NAME            varchar2(255),
    POB_LOCATION        varchar2(255),
    POB_COUNTRY_NAME    varchar2(255),
    BRANCH_INFOMATION   varchar2(255),
    primary key (SWIFT_CODE, BRANCH_CODE)
);
drop table SWIFTINFO cascade constraints;
create table SWIFTINFO
(
    ID           varchar2(32),
    CODE         varchar2(64),
    KOD_BANK     varchar2(32) not null,
    ISO_DIG      varchar2(16),
    ISO_LATTWO   varchar2(16),
    ISO_LATTHREE varchar2(16),
    SUBTYPE      varchar2(16),
    CO_TYPE      varchar2(16),
    CB_DATE      varchar2(32),
    CE_DATE      varchar2(32),
    NAME_SWIFT   varchar2(512) not null
);
drop table TASK cascade constraints;
create table TASK
(
    TASKSEQ          number(20,0) not null,
    PROCESSSTATE     varchar2(8) not null,
    TASKCREATEDATE   timestamp not null,
    SVCID            varchar2(64) not null,
    PRDID            varchar2(64),
    TASKLOCK         number(0,-127),
    CIFSEQ           number(20,0) not null,
    USERSEQ          number(20,0),
    MCHANNELID       varchar2(16) not null,
    LOGINTYPE        char(1)   not null,
    TASKEXPIREDATE   timestamp,
    DEPTSEQ          number(20,0),
    CHECKUSERSEQ     number(20,0),
    CHECKCIFSEQ      number(20,0),
    CHECKTIME        timestamp,
    CHECKSTATE       varchar2(4),
    CHECKREMARK      varchar2(200),
    BANKUSERSEQ      number(20,0),
    BANKOPSTATE      varchar2(4),
    BANKOPTIME       timestamp,
    BANKREMARK       varchar2(200),
    CHECKTYPE        char(1),
    BATCHJNLNO       number(20,0),
    ACSEQ            number(20,0),
    ACNO             varchar2(40),
    ACNAME           varchar2(150),
    AMOUNT           number(15,2),
    TRANSCODE        varchar2(64),
    CURRENCY         varchar2(3),
    TASKCREATETIME   timestamp,
    CURRENTAUTHGROUP varchar2(4),
    AUTHCOUNT        number(0,-127),
    AUTHEDCOUNT      number(0,-127),
    primary key (TASKSEQ)
);
drop table TASKJNL cascade constraints;
create table TASKJNL
(
    TASKSEQ          number(20,0) not null,
    PROCESSSTATE     varchar2(8) not null,
    TASKCREATEDATE   timestamp not null,
    SVCID            varchar2(64) not null,
    PRDID            varchar2(64),
    TASKLOCK         number(0,-127),
    CIFSEQ           number(20,0) not null,
    USERSEQ          number(20,0),
    MCHANNELID       varchar2(16) not null,
    LOGINTYPE        char(1)   not null,
    TASKEXPIREDATE   timestamp,
    DEPTSEQ          number(20,0),
    CHECKUSERSEQ     number(20,0),
    CHECKCIFSEQ      number(20,0),
    CHECKTIME        timestamp,
    CHECKSTATE       varchar2(4),
    CHECKREMARK      varchar2(200),
    BANKUSERSEQ      number(20,0),
    BANKOPSTATE      varchar2(4),
    BANKOPTIME       timestamp,
    BANKREMARK       varchar2(200),
    CHECKTYPE        char(1),
    BATCHJNLNO       number(20,0),
    ACSEQ            number(20,0),
    ACNO             varchar2(40),
    ACNAME           varchar2(150),
    AMOUNT           number(15,2),
    TRANSCODE        varchar2(64),
    CURRENCY         varchar2(3),
    TASKCREATETIME   timestamp,
    CURRENTAUTHGROUP varchar2(4),
    AUTHCOUNT        number(0,-127),
    AUTHEDCOUNT      number(0,-127),
    READCHECKFLAG    char(1),
    primary key (TASKSEQ)
);
drop table TASKJNLHIST cascade constraints;
create table TASKJNLHIST
(
    TASKSEQ        number(20,0) not null,
    PROCESSSTATE   varchar2(8) not null,
    TASKCREATEDATE timestamp not null,
    SVCID          varchar2(64) not null,
    PRDID          varchar2(64),
    TASKLOCK       number(0,-127),
    CIFSEQ         number(20,0) not null,
    USERSEQ        number(20,0),
    MCHANNELID     varchar2(16) not null,
    LOGINTYPE      char(1)   not null,
    TASKEXPIREDATE timestamp,
    DEPTSEQ        number(20,0),
    CHECKUSERSEQ   number(20,0),
    CHECKCIFSEQ    number(20,0),
    CHECKTIME      timestamp,
    CHECKSTATE     varchar2(4),
    CHECKREMARK    varchar2(200),
    BANKUSERSEQ    number(20,0),
    BANKOPSTATE    varchar2(4),
    BANKOPTIME     timestamp,
    BANKREMARK     varchar2(200),
    CHECKTYPE      char(1),
    BATCHJNLNO     number(20,0),
    primary key (TASKSEQ)
);
drop table TASKQUEUE cascade constraints;
create table TASKQUEUE
(
    TASKSEQ number(20,0) not null,
    USERSEQ number(20,0) not null,
    OPTYPE  char(1) not null,
    primary key (TASKSEQ, USERSEQ, OPTYPE)
);
drop table TASKSTAT cascade constraints;
create table TASKSTAT
(
    TASKSEQ    number(20,0) not null,
    OPTYPE     char(1) not null,
    PARTYTYPE  char(1) not null,
    DEPTSEQ    number(20,0),
    DIVSEQ     number(20,0),
    USERGROUP  varchar2(4),
    USERSEQ    number(20,0),
    STEP       number(0,-127) not null,
    SUBSTEP    number(0,-127) not null,
    STATE      varchar2(4) not null,
    OPDATE     timestamp,
    REMARK     varchar2(128),
    MCHANNELID varchar2(16),
    primary key (TASKSEQ, OPTYPE, STEP, SUBSTEP)
);
drop table TASKSTATJNL cascade constraints;
create table TASKSTATJNL
(
    TASKSEQ    number(20,0) not null,
    OPTYPE     char(1) not null,
    USERSEQ    number(20,0),
    STEP       number(0,-127) not null,
    SUBSTEP    number(0,-127) not null,
    STATE      varchar2(4) not null,
    OPDATE     timestamp,
    REMARK     varchar2(128),
    MCHANNELID varchar2(16),
    primary key (TASKSEQ, OPTYPE, STEP, SUBSTEP)
);
drop table TASKSTATJNLHIST cascade constraints;
create table TASKSTATJNLHIST
(
    TASKSEQ    number(20,0) not null,
    OPTYPE     char(1) not null,
    USERSEQ    number(20,0),
    STEP       number(0,-127) not null,
    SUBSTEP    number(0,-127) not null,
    STATE      varchar2(4) not null,
    OPDATE     timestamp,
    REMARK     varchar2(128),
    MCHANNELID varchar2(16),
    primary key (TASKSEQ, OPTYPE, STEP, SUBSTEP)
);
drop table TEMP_MCROLE cascade constraints;
create table TEMP_MCROLE
(
    MCHANNELID    varchar2(16) not null,
    CIFTYPE       varchar2(2) not null,
    ROLEID        varchar2(32) not null,
    ROLENAME      varchar2(32),
    ROLESTATE     char(1),
    CREATEUSERSEQ number(20,0),
    CREATEDEPTSEQ number(20,0),
    CREATETIME    timestamp,
    UPDATEUSERSEQ number(20,0),
    UPDATEDEPTSEQ number(20,0),
    UPDATETIME    timestamp
);
drop table TRANSLATE_CODE cascade constraints;
create table TRANSLATE_CODE
(
    CHINESE varchar2(200) not null,
    ENGLISH varchar2(200) not null
);
drop table USER_NOTIFY cascade constraints;
create table USER_NOTIFY
(
    USERSEQ        number(20,0) not null,
    CIFSEQ         number(20,0) not null,
    NOTIFY_TYPE    varchar2(50) not null,
    NOTIFY_CONTENT varchar2(2048) not null,
    CREATE_TMS     timestamp,
    EXPIRY_TMS     timestamp
);
drop table WAND_HOLDERS cascade constraints;
create table WAND_HOLDERS
(
    WAND_TYPE   varchar2(20) not null,
    WAND_HOLDER varchar2(40),
    EXPIRY_TIME timestamp,
    primary key (WAND_TYPE)
);