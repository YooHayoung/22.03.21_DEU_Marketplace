-- CREATE user localDeuMarket;

-- SELECT * FROM user;

-- DROP DATABASE deuMarketplace;
-- CREATE DATABASE deuMarketplace DEFAULT CHARACTER SET utf8;

-- SHOW DATABASES;

-- GRANT ALL privileges ON deuMarketplace.* TO localDeuMarket@'%' IDENTIFIED BY '';

-- FLUSH PRIVILEGES;

-- SHOW GRANTs FOR localDeuMarket;


-- ------------------------------------------------------------------


# DROP TABLE post_comment;
# DROP TABLE post_recommend;
# DROP TABLE post_img;
# DROP TABLE post;
# DROP TABLE item_deal;
# DROP TABLE chat_log;
# DROP TABLE chat_room;
# DROP TABLE wish_item;
# DROP TABLE item_img;
# DROP TABLE item;
# DROP TABLE post_category;
# DROP TABLE item_category;
# DROP TABLE lecture;
# DROP TABLE member;
-- -------------------------------------------------
CREATE OR replace TABLE member (
	member_id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(20) NOT NULL,
	email VARCHAR(40) NOT NULL,
	nickname VARCHAR(20) NOT NULL,
	certification BOOLEAN NOT NULL,
	created_date TIMESTAMP NOT NULL,
	last_modified_date TIMESTAMP NOT NULL,
	PRIMARY KEY(member_id)
);

CREATE OR REPLACE TABLE lecture (
	lecture_id BIGINT NOT NULL AUTO_INCREMENT,
	lecture_name varchar(40) NOT NULL,
	professor_name VARCHAR(30),
	PRIMARY KEY(lecture_id)
);

CREATE OR REPLACE TABLE item_category (
	item_category_id BIGINT NOT NULL AUTO_INCREMENT,
	category_name VARCHAR(30) NOT NULL,
	PRIMARY KEY(item_category_id)
);

CREATE OR REPLACE TABLE post_category (
	post_category_id BIGINT NOT NULL AUTO_INCREMENT,
	category_name VARCHAR(30) NOT NULL,
	PRIMARY KEY(post_category_id)
);

CREATE OR REPLACE TABLE item (
	item_id BIGINT NOT NULL AUTO_INCREMENT,
	member_id BIGINT NOT NULL,
	item_category_id BIGINT NOT NULL,
	title VARCHAR(50) NOT NULL,
	lecture_id BIGINT,
	write_state VARCHAR(30),
	surface_state VARCHAR(30),
	regular_price INT,
	price INT NOT NULL,
	description VARCHAR(1000) NOT NULL,
	classification VARCHAR(10) NOT NULL,
	created_date TIMESTAMP NOT NULL,
	last_modified_date TIMESTAMP NOT NULL,
	PRIMARY KEY(item_id),
	FOREIGN KEY(member_id) REFERENCES member(member_id),
	FOREIGN KEY(item_category_id) REFERENCES item_category(item_category_id),
	FOREIGN KEY(lecture_id) REFERENCES lecture(lecture_id)
);

CREATE OR REPLACE TABLE item_img (
	item_img_id BIGINT NOT NULL AUTO_INCREMENT,
	item_id BIGINT NOT NULL,
	img_file VARCHAR(100) NOT NULL,
	img_seq INT NOT NULL,
	created_date TIMESTAMP NOT NULL,
	last_modified_date TIMESTAMP NOT NULL,
	PRIMARY KEY(item_img_id),
	FOREIGN KEY(item_id) REFERENCES item(item_id)
);

CREATE OR REPLACE TABLE wish_item (
	wish_item_id BIGINT NOT NULL AUTO_INCREMENT,
	wished_member_id BIGINT NOT NULL,
	item_id BIGINT NOT NULL,
	created_date TIMESTAMP NOT NULL,
	last_modified_date TIMESTAMP NOT NULL,
	PRIMARY KEY(wish_item_id),
	FOREIGN KEY(wished_member_id) REFERENCES member(member_id),
	FOREIGN KEY(item_id) REFERENCES item(item_id)
);

CREATE OR REPLACE TABLE chat_room (
	chat_room_id BIGINT NOT NULL AUTO_INCREMENT,
	item_id BIGINT NOT NULL,
	requested_member_id BIGINT NOT NULL,
	socekt VARCHAR(100) NOT NULL,
	created_date TIMESTAMP NOT NULL,
	last_modified_date TIMESTAMP NOT NULL,
	PRIMARY KEY(chat_room_id),
	FOREIGN KEY(item_id) REFERENCES item(item_id),
	FOREIGN KEY(requested_member_id) REFERENCES member(member_id)
);

CREATE OR REPLACE TABLE chat_log (
	chat_log_id BIGINT NOT NULL AUTO_INCREMENT,
	chat_room_id BIGINT NOT NULL,
	sender_id BIGINT NOT NULL,
	recipient_id BIGINT NOT NULL,
	content VARCHAR(255) NOT NULL,
	created_date TIMESTAMP NOT NULL,
	last_modified_date TIMESTAMP NOT NULL,
	is_read BOOLEAN NOT NULL,
	PRIMARY KEY(chat_log_id),
	FOREIGN KEY(chat_room_id) REFERENCES chat_room(chat_room_id),
	FOREIGN KEY(sender_id) REFERENCES member(member_id),
	FOREIGN KEY(recipient_id) REFERENCES member(member_id)
);

CREATE OR REPLACE TABLE item_deal (
	item_deal_id BIGINT NOT NULL AUTO_INCREMENT,
	item_id BIGINT NOT NULL,
	appointment_date_time TIMESTAMP NOT NULL,
	meeting_place VARCHAR(100) NOT NULL,
	deal_state VARCHAR(20) NOT NULL,
	created_date TIMESTAMP NOT NULL,
	last_modified_date TIMESTAMP NOT NULL,
	PRIMARY KEY(item_deal_id),
	FOREIGN KEY(item_id) REFERENCES item(item_id)
);

CREATE OR REPLACE TABLE post (
	post_id BIGINT NOT NULL AUTO_INCREMENT,
	post_category_id BIGINT NOT NULL,
	writer_id BIGINT NOT NULL,
	title VARCHAR(100) NOT NULL,
	content VARCHAR(1000) NOT NULL,
	created_date TIMESTAMP NOT NULL,
	last_modified_date TIMESTAMP NOT NULL,
	PRIMARY KEY(post_id),
	FOREIGN KEY(post_category_id) REFERENCES post_category(post_category_id),
	FOREIGN KEY(writer_id) REFERENCES member(member_id)
);

CREATE OR REPLACE TABLE post_img (
	post_img_id BIGINT NOT NULL AUTO_INCREMENT,
	post_id BIGINT NOT NULL,
	img_file VARCHAR(100) NOT NULL,
	img_seq INT NOT NULL,
	created_date TIMESTAMP NOT NULL,
	last_modified_date TIMESTAMP NOT NULL,
	PRIMARY KEY(post_img_id),
	FOREIGN KEY(post_id) REFERENCES post(post_id)
);

CREATE OR REPLACE TABLE post_recommend (
	post_recommend_id BIGINT NOT NULL AUTO_INCREMENT,
	post_id BIGINT NOT NULL,
	member_id BIGINT NOT NULL,
	created_date TIMESTAMP NOT NULL,
	last_modified_date TIMESTAMP NOT NULL,
	PRIMARY KEY(post_recommend_id),
	FOREIGN KEY(post_id) REFERENCES post(post_id),
	FOREIGN KEY(member_id) REFERENCES member(member_id)
);

CREATE OR REPLACE TABLE post_comment (
	post_comment_id BIGINT NOT NULL AUTO_INCREMENT,
	post_id BIGINT NOT NULL,
	member_id BIGINT NOT NULL,
	content VARCHAR(200) NOT NULL,
	created_date TIMESTAMP NOT NULL,
	last_modified_date TIMESTAMP NOT NULL,
	PRIMARY KEY(post_comment_id),
	FOREIGN KEY(post_id) REFERENCES post(post_id),
	FOREIGN KEY(member_id) REFERENCES member(member_id)
);