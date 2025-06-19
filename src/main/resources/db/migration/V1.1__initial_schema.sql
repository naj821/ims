CREATE TABLE users(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(5) DEFAULT 'staff',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE profiles(
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    first_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50),
    last_name VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categories(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users(email, password, role)
VALUES('admin@gmail.com', '$2a$10$B9OK8YBcNWc0MtK7wqH.6eHEgBIDDXXBy36HGUohvvS.rhfew6VSe', 'admin');

INSERT INTO categories(name)
VALUES('Groceries');
INSERT INTO categories(name)
VALUES('Body Essentials');
INSERT INTO categories(name)
VALUES('Baby & Child Essentials');
INSERT INTO categories(name)
VALUES('Personal Care');
INSERT INTO categories(name)
VALUES('Kitchen Essentials');
INSERT INTO categories(name)
VALUES('Cleaning Supplies');
INSERT INTO categories(name)
VALUES('Laundry');
INSERT INTO categories(name)
VALUES('Bathroom Supplies');

CREATE TABLE products(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    category_id UUID REFERENCES categories(id) ON DELETE NO ACTION,
    product_name VARCHAR(50) NOT NULL,
    quantity BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE token(
id SERIAL PRIMARY KEY,
user_id UUID,
hashed_access_token VARCHAR(255) NOT NULL,
hashed_refresh_token VARCHAR(255) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);