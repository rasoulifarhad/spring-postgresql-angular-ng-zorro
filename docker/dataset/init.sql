CREATE TABLE IF NOT EXISTS persons (
	id uuid PRIMARY KEY,
	first_name VARCHAR(30) NOT NULL,
	last_name VARCHAR(30) NOT NULL,
	age INTEGER NOT NULL,
	address VARCHAR(200) NOT NULL
);

insert into persons (id, first_name, last_name, age, address) values ('597f4585-aa32-4606-bcc5-ae070616de3b', 'Worden', 'Handes', 29, '4566 Di Loreto Park');
insert into persons (id, first_name, last_name, age, address) values ('fb3013fb-5343-4832-ac95-1ee6f0fa264d', 'Eddie', 'Kivits', 48, '97525 Oakridge Trail');
insert into persons (id, first_name, last_name, age, address) values ('25e55652-5a29-4a7a-94ba-38d960d45d9f', 'Lonnie', 'Gianni', 45, '578 Knutson Center');

