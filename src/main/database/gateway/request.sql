create table gateway.request(
id int primary key auto_increment not null,
request_id varchar(256) not null,
service_id varchar(256) not null,
timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
client_id varchar(256) not null,
index(requestId),
index(timestamp)
);