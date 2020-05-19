/*
Part of the code that was used to create db tables.
*/
/* Create tables*/
CREATE TABLE product
(
    prod_id       char(5)     NOT NULL,
    product_name  varchar(20),
    manuf_id      smallint(5) NOT NULL,
    cat_id        smallint(5) NOT NULL,
    price         DECIMAL,
    amnt_in_stock int(10),
    descr         varchar(300),
    popularity    int(11),
    CONSTRAINT PRIMARY KEY (prod_id)
);

CREATE TABLE manufacturer
(
    manuf_id smallint(5) NOT NULL AUTO_INCREMENT,
    name     varchar(20) UNIQUE,
    CONSTRAINT PRIMARY KEY (manuf_id)
);

CREATE TABLE category
(
    cat_id smallint(5) NOT NULL AUTO_INCREMENT,
    name   varchar(20) UNIQUE,
    CONSTRAINT PRIMARY KEY (cat_id)
);

CREATE TABLE product_attribute
(
    prod_id   char(5) NOT NULL,
    attribute varchar(20),
    value     varchar(20),
    id        int     not null auto_increment,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE `order`
(
    order_id     smallint(5) NOT NULL AUTO_INCREMENT,
    cust_phone   varchar(20),
    cust_name    varchar(20),
    cust_email   varchar(35),
    order_date   datetime,
    order_status ENUM ('PENDING', 'COMPLETED', 'CANCELLED'),
    PRIMARY KEY (order_id)
);

CREATE TABLE order_product
(
    order_id smallint(5) NOT NULL,
    prod_id  char(5)     NOT NULL,
    CONSTRAINT FOREIGN KEY (prod_id) REFERENCES product (prod_id),
    CONSTRAINT FOREIGN KEY (order_id) REFERENCES `order` (order_id)
);

CREATE TABLE product_comments
(
    prod_id      char(5),
    user         char(20),
    score        ENUM ('AWFUL', 'BAD', 'GOOD_ENOUGH', 'GOOD', 'BEST'),
    comment_text varchar(500) NOT NULL,
    comment_date datetime,
    comment_id   int,
    PRIMARY KEY (comment_id)
);

CREATE TABLE service_comments
(
    user         char(20),
    score        ENUM ('AWFUL', 'BAD', 'GOOD_ENOUGH', 'GOOD', 'BEST'),
    comment_text varchar(500) NOT NULL,
    comment_date datetime,
    PRIMARY KEY (user)
);

CREATE TABLE service_requests
(
    id             int not null auto_increment,
    descr          varchar(500),
    cust_phone     varchar(20),
    request_status ENUM ('PENDING', 'COMPLETED'),
    PRIMARY KEY (id)
);
/*Add foreign keys*/
ALTER TABLE product
    ADD CONSTRAINT
        FOREIGN KEY (manuf_id) REFERENCES manufacturer (manuf_id);
ALTER TABLE product
    ADD CONSTRAINT
        FOREIGN KEY (cat_id) REFERENCES category (cat_id);
ALTER TABLE product_attribute
    ADD CONSTRAINT
        FOREIGN KEY (prod_id) REFERENCES product (prod_id);

/*Create indexes*/
ALTER TABLE product_comment
    ADD UNIQUE idx_product_comments (prod_id, user);
ALTER TABLE manufacturer
    ADD UNIQUE idx_product_comments (name);
ALTER TABLE category
    ADD UNIQUE idx_product_comments (name);

/*Create triggers*/
CREATE trigger average_score
    AFTER INSERT
    ON product_comment
    FOR EACH ROW
    update product
    set total_score=(SELECT avg(score)
                     from product_comment
                     where product_comment.prod_id = product.prod_id)
    where prod_id = NEW.prod_id;