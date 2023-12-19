create or replace function nams_tbl()
returns table (f_name varchar, l_name varchar) as $$
		SELECT
			arrays.firstnames[s.a % ARRAY_LENGTH(arrays.firstnames,1) + 1] AS firstname,
			arrays.lastnames[s.a % ARRAY_LENGTH(arrays.lastnames,1) + 1] AS lastname
		FROM     
			generate_series(1,300) AS s(a) -- number of names to generate
		CROSS JOIN(
			SELECT ARRAY[
			'Adam','Bill','Bob','Calvin','Donald','Dwight','Frank','Fred','George','Howard',
			'James','John','Jacob','Jack','Martin','Matthew','Max','Michael',
			'Paul','Peter','Phil','Roland','Ronald','Samuel','Steve','Theo','Warren','William',
			'Abigail','Alice','Allison','Amanda','Anne','Barbara','Betty','Carol','Cleo','Donna',
			'Jane','Jennifer','Julie','Martha','Mary','Melissa','Patty','Sarah','Simone','Susan'
			] AS firstnames,
			ARRAY[
				'Matthews','Smith','Jones','Davis','Jacobson','Williams','Donaldson','Maxwell','Peterson','Stevens',
				'Franklin','Washington','Jefferson','Adams','Jackson','Johnson','Lincoln','Grant','Fillmore','Harding','Taft',
				'Truman','Nixon','Ford','Carter','Reagan','Bush','Clinton','Hancock'
			] AS lastnames
		) AS arrays;
$$  LANGUAGE sql;

CREATE OR REPLACE FUNCTION random_between(low INT ,high INT) 
   RETURNS INT AS
$$
BEGIN
   RETURN floor(random()* (high-low + 1) + low);
END;
$$ language 'plpgsql' STRICT;

CREATE TABLE IF NOT EXISTS persons (
	id uuid PRIMARY KEY,
	first_name VARCHAR(30) NOT NULL,
	last_name VARCHAR(30) NOT NULL,
	age INTEGER NOT NULL,
	address VARCHAR(200) NOT NULL
);

insert into persons (id, first_name, last_name, age, address) 
select gen_random_uuid(), mynames.f_name, mynames.l_name, random_between(20,100), mynames.l_name from nams_tbl() as mynames;


