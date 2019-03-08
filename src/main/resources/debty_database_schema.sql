
drop table if exists bank_account ;
drop table if exists bill;
drop table if exists bank_account;
drop table if exists bill ;
drop table if exists bill_bill_payments ;
drop table if exists bill_users ;
drop table if exists bill_payment;
drop table if exists debt ;
drop table if exists event ;
drop table if exists event_bills ;
drop table if exists event_users ;
drop table if exists person ;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;

create table bank_account (id bigint not null, createdAt timestamp, modifiedAt timestamp, name varchar(255), number varchar(255), primary key (id));

create table bill (id bigint not null, createdAt timestamp, currency varchar(255), description varchar(255), modifiedAt timestamp, sum decimal(19,2), title varchar(255), buyer_id bigint, primary key (id));

create table bill_bill_payments (bill_id bigint not null, bill_payments_id bigint not null);

create table bill_persons (bill_id bigint not null, persons_id bigint not null);

create table bill_payment (id bigint not null, createdAt timestamp, modifiedAt timestamp, sum decimal(19,2), persons_id bigint, primary key (id));

create table debt (id bigint not null, createdAt timestamp, currency varchar(255), modifiedAt timestamp, sum decimal(19,2), payer_id bigint, receiver_id bigint, primary key (id));

create table event (id bigint not null, closed timestamp, createdAt timestamp, modifiedAt timestamp, title varchar(255), primary key (id));

create table event_bills (event_id bigint not null, bills_id bigint not null);

create table event_persons (event_id bigint not null, persons_id bigint not null);

create table person (id bigint not null, createdAt timestamp, email varchar(255), first_name varchar(255), last_name varchar(255), modifiedAt timestamp, password varchar(255), bank_account_id bigint, primary key (id));

alter table bill add constraint FKkurhoqjirffn15yhfp9mhmpse foreign key (buyer_id) references person;

alter table bill_bill_payments add constraint FKb6lm6np6dkaw5vthpqvahlyiq foreign key (bill_payments_id) references bill_payment;

alter table bill_bill_payments add constraint FKnrv8jalxonqpwfwg8ijewmfnq foreign key (bill_id) references bill;

alter table bill_persons add constraint FK1i87f9ty74dsxhmhpw5ggjch6 foreign key (persons_id) references person;

alter table bill_persons add constraint FKbn6s368sj4bb4e622m72usqvt foreign key (bill_id) references bill;

alter table bill_payment add constraint FKtghxf6il6x1yagpjkx0kbflid foreign key (persons_id) references person;

alter table debt add constraint FKd5uutt7i6b1uyiweujw9vdml8 foreign key (payer_id) references person;

alter table debt add constraint FKnoonmc1rdjlp76dfis5uy319x foreign key (receiver_id) references person;

alter table event_bills add constraint FKl418ye0nt519khe3lnicxsv0o foreign key (bills_id) references bill;

alter table event_bills add constraint FK77bw21c8h5lgj2frbb0is6y9i foreign key (event_id) references event;

alter table event_persons add constraint FKl0v7owuu67q8oy7hld3hb7o8m foreign key (persons_id) references person;

alter table event_persons add constraint FKhs210moo9x8pcdtyvasahrkmt foreign key (event_id) references event;
alter table person add constraint FKelf1n7mrytfidb2gijv2gqmsv foreign key (bank_account_id) references bank_account;

