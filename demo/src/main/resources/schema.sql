create table products (
  id bigint primary key auto_increment,
  description varchar(500),
  category varchar(10),
  price double,
  withdrawn integer default 0,
  production_date date
);