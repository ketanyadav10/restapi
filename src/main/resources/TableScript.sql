DROP TABLE Contact;
CREATE TABLE Contact(
   contact_id INT,
   first_name VARCHAR(20),
   last_name VARCHAR(20),
   email_id VARCHAR(50),
   phone_no VARCHAR(20),
   status VARCHAR(8) ,
   constraint ps_contact_id_PK primary key ( contact_id ),
    constraint status_check CHECK (status IN ('ACTIVE','INACTIVE'))
);

INSERT INTO Contact (contact_id, first_name, last_name,email_id, phone_no, status) VALUES (1, 'Ketan', 'Yadav', 'kyadav073@gmail.com', '+917387431338','ACTIVE');
commit;
select * from Contact;