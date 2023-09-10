BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, type_of_food, user_type_of_food,
restaurant, is_a_match, restaurant_type_of_food, user_likes;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	full_name varchar(200) NOT NULL,
    email varchar(150) NOT NULL,
    zip_code int NOT NULL,
    address varchar(200) NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE type_of_food (
    type_id SERIAL,
    categories_name varchar(200) NOT NULL,
    CONSTRAINT PK_type PRIMARY KEY(type_id)
);

CREATE TABLE user_type_of_food (
    user_id int NOT NULL,
    type_id int NOT NULL,
	CONSTRAINT PK_user_type_of_food PRIMARY KEY(user_id, type_id),
    CONSTRAINT FK_user_type_of_food_user FOREIGN KEY(user_id) REFERENCES users(user_id),
    CONSTRAINT FK_user_type_of_food_type_of_food FOREIGN KEY(type_id) REFERENCES type_of_food(type_id)
);

CREATE TABLE restaurant (
    restaurant_id SERIAL,
    restaurant_name varchar(50) NOT NULL,
	description text NOT NULL,
	phone_number int NOT NULL,
	rating int NOT NULL,
	review text NOT NULL,
	photo_path varchar(500) NOT NULL,
	zip_code int NOT NULL,
	home_page varchar(200) NOT NULL,
	address varchar(200) NULL,
	CONSTRAINT PK_restaurant PRIMARY KEY(restaurant_id)
	);

	CREATE TABLE user_likes (
        like_id SERIAL,
        restaurant_id int NOT NULL,
        user_id int NOT NULL,
        CONSTRAINT PK_like PRIMARY KEY(like_id),
        CONSTRAINT FK_like_user FOREIGN KEY(user_id) REFERENCES users(user_id),
        CONSTRAINT FK_like_restaurant FOREIGN KEY(restaurant_id) REFERENCES restaurant(restaurant_id)
    );

    CREATE TABLE restaurant_type_of_food (
        restaurant_id int NOT NULL,
        type_id int NOT NULL,
    	CONSTRAINT PK_restaurant_type_of_food PRIMARY KEY(restaurant_id, type_id),
        CONSTRAINT FK_restaurant_type_of_food_restaurant FOREIGN KEY(restaurant_id) REFERENCES restaurant(restaurant_id),
        CONSTRAINT FK_restaurant_type_of_food_type_of_food FOREIGN KEY(type_id) REFERENCES type_of_food(type_id)
    );

COMMIT TRANSACTION;
