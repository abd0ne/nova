CREATE TABLE users (
	id INT PRIMARY KEY,
	first_name VARCHAR(255) NULL,
	last_name VARCHAR(255) NULL,
	e_mail VARCHAR(255)
);

CREATE TABLE tasks (
	id INT PRIMARY KEY,
	task_name VARCHAR(255),
	fk_user INT
);
