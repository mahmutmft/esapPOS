-- WAITER
CREATE TABLE waiter
(
    id       INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- ITEM
CREATE TABLE item
(
    id          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        VARCHAR(100)   NOT NULL,
    price       NUMERIC(10, 2) NOT NULL,
    description VARCHAR(255),
    image_path  VARCHAR(255)
);
-- table
CREATE TABLE restaurant_table
(
    id        INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name      VARCHAR(100),
    status    VARCHAR(100) NOT NULL,
    waiter_id INT,

    FOREIGN KEY (waiter_id) REFERENCES waiter (id)
);
-- sale
CREATE TABLE sale
(
    id          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    waiter_id   INT,
    table_id    INT,
    total_price NUMERIC(10, 2) NOT NULL,
    date_time   TIMESTAMP      NOT NULL,

    FOREIGN KEY (waiter_id) REFERENCES waiter (id),
    FOREIGN KEY (table_id) REFERENCES restaurant_table (id)
);
-- orders
CREATE TABLE orders
(
    id         INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    confirmed  BOOLEAN   NOT NULL,
    table_id   INT       NOT NULL,
    sale_id    INT,

    FOREIGN KEY (table_id) REFERENCES restaurant_table (id),
    FOREIGN KEY (sale_id) REFERENCES sale (id)
);

-- order item
CREATE TABLE order_item
(
    id       INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    order_id INTEGER NOT NULL,
    item_id  INTEGER NOT NULL,
    quantity INTEGER NOT NULL,

    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (item_id) REFERENCES item (id)
);

-- stock
CREATE TABLE stock
(
    id          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    item_id     INTEGER        NOT NULL UNIQUE,
    quantity    INTEGER        NOT NULL,
    stock_price NUMERIC(10, 2) NOT NULL,

    FOREIGN KEY (item_id) REFERENCES item (id)
);
-- stock movement
CREATE TABLE stock_movement
(
    id        INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    item_id   INTEGER     NOT NULL,
    quantity  INTEGER     NOT NULL,
    type      VARCHAR(50) NOT NULL,
    date_time TIMESTAMP   NOT NULL,

    FOREIGN KEY (item_id) REFERENCES item (id)
);