create table gateway.fixer(
id int primary key auto_increment not null,
base char(3) not null,
timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
currency char(3) not null,
rate double not null,
index(timestamp),
index(currency)
);