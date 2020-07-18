CREATE TABLE products
(
    id       bigint     NOT NULL,
    product_name  varchar(100),
    manuf_id      bigint NOT NULL,
    cat_id        bigint NOT NULL,
    price         DECIMAL,
    amnt_in_stock int(10),
    descr         varchar(300),
    popularity    int(11),
    total_score int(11),
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE manufacturers
(
    id bigint NOT NULL AUTO_INCREMENT,
    name     varchar(30) UNIQUE,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE categories
(
    id bigint NOT NULL AUTO_INCREMENT,
    name   varchar(30) UNIQUE,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE product_attributes
(
    id        int     not null AUTO_INCREMENT,
    prod_id   bigint NOT NULL,
    attribute varchar(20),
    value     varchar(20),
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id     bigint NOT NULL AUTO_INCREMENT,
    cust_name    varchar(20),
    cust_phone   varchar(20),
    cust_email   varchar(35),
    order_date   timestamp,
    order_status ENUM ('PENDING', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING',
    PRIMARY KEY (id)
);

CREATE TABLE order_product
(
    order_id bigint NOT NULL,
    prod_id  bigint NOT NULL,
    quantity int,
    PRIMARY KEY (order_id, prod_id)
);

CREATE TABLE product_comments
(
    id   bigint auto_increment,
    prod_id      bigint,
    user         char(20),
    score        ENUM ('AWFUL', 'BAD', 'GOOD_ENOUGH', 'GOOD', 'BEST'),
    comment_text varchar(500) NOT NULL,
    comment_date timestamp,
    PRIMARY KEY (id)
);


/*Add foreign keys*/
ALTER TABLE products
    ADD CONSTRAINT products_manufacturers_fk
        FOREIGN KEY (manuf_id) REFERENCES manufacturers (id);

ALTER TABLE products
    ADD CONSTRAINT products_categories_fk
        FOREIGN KEY (cat_id) REFERENCES categories (id);

ALTER TABLE product_attributes
    ADD CONSTRAINT attributes_products_fk
        FOREIGN KEY (prod_id) REFERENCES products (id);

ALTER TABLE product_comments
    ADD CONSTRAINT comments_products_fk
        FOREIGN KEY (prod_id) REFERENCES products (id);

ALTER TABLE order_product
    ADD CONSTRAINT order_product_product_fk
        FOREIGN KEY (prod_id) REFERENCES products (id);

ALTER TABLE order_product
    ADD CONSTRAINT order_product_order_fk
        FOREIGN KEY (order_id) REFERENCES orders (id);


CREATE trigger average_score_insert
    AFTER INSERT
    ON product_comments
    FOR EACH ROW
    update products
    set total_score=(SELECT avg(score+0)
                     from product_comments
                     where product_comments.prod_id = products.prod_id)
    where prod_id = NEW.prod_id;

CREATE trigger average_score_update
    AFTER UPDATE
    ON product_comments
    FOR EACH ROW
    update products
    set total_score=(SELECT avg(score+0)
                     from product_comments
                     where product_comments.prod_id = products.prod_id)
    where prod_id = NEW.prod_id;


CREATE trigger popularity_insert
    AFTER INSERT
    ON order_product
    FOR EACH ROW
    update products
    set popularity=(SELECT sum(quantity)
                     from order_product
                     where prod_id = NEW.prod_id)
    where prod_id = NEW.prod_id;

CREATE trigger popularity_update
    AFTER UPDATE
    ON order_product
    FOR EACH ROW
    update products
    set popularity=(SELECT sum(quantity)
                    from order_product
                    where prod_id = NEW.prod_id)
    where prod_id = NEW.prod_id;

