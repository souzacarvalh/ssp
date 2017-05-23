/**
 * ############## SSP DATABASE SAMPLE DATA ##############
 * # Author: Felipe F. de Souza Carvalho                #
 * # Data: 2015/02/15                                   #
 * # Version: 1.0                                       #
 * # Target: MySQL 5.5.28                               #
 * ######################################################
 */

USE SSP_DB;

INSERT INTO TICKET_STATUS(NAME) VALUES("Opened");
INSERT INTO TICKET_STATUS(NAME) VALUES("In Analysis");
INSERT INTO TICKET_STATUS(NAME) VALUES("Waiting Response");
INSERT INTO TICKET_STATUS(NAME) VALUES("Canceled");
INSERT INTO TICKET_STATUS(NAME) VALUES("Resolved");

INSERT INTO PRIORITY(NAME) VALUES ("Low");
INSERT INTO PRIORITY(NAME) VALUES ("Medium");
INSERT INTO PRIORITY(NAME) VALUES ("High");
INSERT INTO PRIORITY(NAME) VALUES ("Urgent");
INSERT INTO PRIORITY(NAME) VALUES ("Emergency");

INSERT INTO PRODUCT(NAME, DESCRIPTION, PRODUCT_TYPE) VALUES ("Other", "Other sorts of products and services", "PRODUCT");
INSERT INTO PRODUCT(NAME, DESCRIPTION, PRODUCT_TYPE) VALUES ("Information Technology", "IT Services", "SERVICE");
INSERT INTO PRODUCT(NAME, DESCRIPTION, PRODUCT_TYPE) VALUES ("Internal Systems", "Systems", "PRODUCT");

INSERT INTO CATEGORY(NAME, DESCRIPTION, STATUS, PRODUCT_ID) VALUES("Other", "Other sorts of categories", "ACTIVE", 1);
INSERT INTO CATEGORY(NAME, DESCRIPTION, STATUS, PRODUCT_ID) VALUES("Purchases", "Equipment acquisition", "ACTIVE", 2);
INSERT INTO CATEGORY(NAME, DESCRIPTION, STATUS, PRODUCT_ID) VALUES("Hardware Instalation", "General hardware equipment installation", "ACTIVE", 2);
INSERT INTO CATEGORY(NAME, DESCRIPTION, STATUS, PRODUCT_ID) VALUES("Software Instalation", "General software installation", "ACTIVE", 2);
INSERT INTO CATEGORY(NAME, DESCRIPTION, STATUS, PRODUCT_ID) VALUES("Hardware Maintenance", "General hardware equipment maintenance", "ACTIVE", 2);
INSERT INTO CATEGORY(NAME, DESCRIPTION, STATUS, PRODUCT_ID) VALUES("Network Maintenance", "LAN Network Maintenance", "ACTIVE", 2);
INSERT INTO CATEGORY(NAME, DESCRIPTION, STATUS, PRODUCT_ID) VALUES("Linux", "Linux Operating System Family", "ACTIVE", 2);
INSERT INTO CATEGORY(NAME, DESCRIPTION, STATUS, PRODUCT_ID) VALUES("Windows", "Windows Operating System Family", "ACTIVE", 2);
INSERT INTO CATEGORY(NAME, DESCRIPTION, STATUS, PRODUCT_ID) VALUES("OS2", "IBM OS2 Operating System Family", "INACTIVE", 2);
INSERT INTO CATEGORY(NAME, DESCRIPTION, STATUS, PRODUCT_ID) VALUES("ERP", "Enterprise Resource Planning", "ACTIVE", 3);
INSERT INTO CATEGORY(NAME, DESCRIPTION, STATUS, PRODUCT_ID) VALUES("Administrative Systems", "Company's Administrative System", "ACTIVE", 3);
INSERT INTO CATEGORY(NAME, DESCRIPTION, STATUS, PRODUCT_ID) VALUES("Financial Systems", "Company's Financial Systems", "ACTIVE", 3);

-- Staff Groups
INSERT INTO SUPPORT_GROUP (NAME, GROUP_STATUS, MANAGMENT_GROUP, RECEIVE_NOT_ROUTED) VALUES ('System Administrators', 'ACTIVE', 1, 0);
INSERT INTO SUPPORT_GROUP (NAME, GROUP_STATUS, MANAGMENT_GROUP, RECEIVE_NOT_ROUTED) VALUES ('Support Managers', 'ACTIVE', 1, 0);
INSERT INTO SUPPORT_GROUP (NAME, GROUP_STATUS, MANAGMENT_GROUP, RECEIVE_NOT_ROUTED) VALUES ('L1 Support Analysts', 'ACTIVE', 0, 1);
INSERT INTO SUPPORT_GROUP (NAME, GROUP_STATUS, MANAGMENT_GROUP, RECEIVE_NOT_ROUTED) VALUES ('L2 Support Analysts', 'ACTIVE', 0, 0);
INSERT INTO SUPPORT_GROUP (NAME, GROUP_STATUS, MANAGMENT_GROUP, RECEIVE_NOT_ROUTED) VALUES ('L3 Support Analysts', 'ACTIVE', 0, 0);

-- Administrator
INSERT INTO CREDENTIAL (USERNAME, PASSWORD, EXPIRATION_DATE, STATUS, ACCESS_TYPE) VALUES('admin', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', '9999-12-31', 'ACTIVE', 'ADMINISTRATION');
INSERT INTO ANALYST(FIRST_NAME, LAST_NAME, CREATE_DATE, EMAIL, ANALYST_STATUS, GROUP_ID, USERNAME) VALUES ('System Administrator', '', NOW(), 'admin@mycompany.com', 'ACTIVE', 1, 'admin');
INSERT INTO REQUESTER (FIRST_NAME, LAST_NAME, EMAIL, CONTACT_NUMBER, REQUESTER_STATUS, CREATE_DATE, USERNAME) VALUES ('System Administrator', '', 'admin@mycompany.com', '(99)9999-9999', 'ACTIVE', NOW(), 'admin');

-- Support Manager
INSERT INTO CREDENTIAL (USERNAME, PASSWORD, EXPIRATION_DATE, STATUS, ACCESS_TYPE) VALUES('manager1', 'WZRHGrsBESr8wYFZ9sx0tPURuZgG2lmzyvWpwXPKz8U=', DATE_ADD(NOW(), INTERVAL 3 MONTH), 'ACTIVE', 'ADMINISTRATION');
INSERT INTO ANALYST (FIRST_NAME, LAST_NAME, CREATE_DATE, EMAIL, ANALYST_STATUS, GROUP_ID, USERNAME) VALUES ('Manager', 'Number 1', NOW(), 'souzacarvalh@gmail.com', 'ACTIVE', 2, 'manager1');
INSERT INTO REQUESTER (FIRST_NAME, LAST_NAME, EMAIL, CONTACT_NUMBER, REQUESTER_STATUS, CREATE_DATE, USERNAME) VALUES ('Manager', 'Number 1', 'souzacarvalh@gmail.com', '(99)9999-9999', 'ACTIVE', NOW(), 'manager1');

-- L2 Support Analyst
INSERT INTO CREDENTIAL (USERNAME, PASSWORD, EXPIRATION_DATE, STATUS, ACCESS_TYPE) VALUES('l1analyst', 'WZRHGrsBESr8wYFZ9sx0tPURuZgG2lmzyvWpwXPKz8U=', DATE_ADD(NOW(), INTERVAL 3 MONTH), 'ACTIVE', 'SUPPORT');
INSERT INTO ANALYST (FIRST_NAME, LAST_NAME, CREATE_DATE, EMAIL, ANALYST_STATUS, GROUP_ID, USERNAME) VALUES ('Support Analyst', 'L1', NOW(), 'souzacarvalh@gmail.com', 'ACTIVE', 3, 'l1analyst');
INSERT INTO REQUESTER (FIRST_NAME, LAST_NAME, EMAIL, CONTACT_NUMBER, REQUESTER_STATUS, CREATE_DATE, USERNAME) VALUES ('Support Analyst', 'L1', 'souzacarvalh@gmail.com', '(99)9999-9999', 'ACTIVE', NOW(), 'l1analyst');

-- L2 Support Analyst
INSERT INTO CREDENTIAL (USERNAME, PASSWORD, EXPIRATION_DATE, STATUS, ACCESS_TYPE) VALUES('l2analyst', 'WZRHGrsBESr8wYFZ9sx0tPURuZgG2lmzyvWpwXPKz8U=', DATE_ADD(NOW(), INTERVAL 3 MONTH), 'ACTIVE', 'SUPPORT');
INSERT INTO ANALYST (FIRST_NAME, LAST_NAME, CREATE_DATE, EMAIL, ANALYST_STATUS, GROUP_ID, USERNAME) VALUES ('Support Analyst', 'L2', NOW(), 'souzacarvalh@gmail.com', 'ACTIVE', 4, 'l2analyst');
INSERT INTO REQUESTER (FIRST_NAME, LAST_NAME, EMAIL, CONTACT_NUMBER, REQUESTER_STATUS, CREATE_DATE, USERNAME) VALUES ('Support Analyst', 'L2', 'souzacarvalh@gmail.com', '(99)9999-9999', 'ACTIVE', NOW(), 'l2analyst');

-- Customer
INSERT INTO CREDENTIAL (USERNAME, PASSWORD, EXPIRATION_DATE, STATUS, ACCESS_TYPE) VALUES('felipec', 'WZRHGrsBESr8wYFZ9sx0tPURuZgG2lmzyvWpwXPKz8U=', DATE_ADD(NOW(), INTERVAL 3 MONTH), 'ACTIVE', 'CUSTOMER');
INSERT INTO REQUESTER (FIRST_NAME, LAST_NAME, EMAIL, CONTACT_NUMBER, REQUESTER_STATUS, CREATE_DATE, USERNAME) VALUES ('Felipe', 'Carvalho', 'souzacarvalh@gmail.com', '(99)9999-9999', 'ACTIVE', NOW(), 'felipec');
