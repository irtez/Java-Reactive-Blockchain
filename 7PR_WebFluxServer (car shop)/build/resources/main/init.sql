CREATE TABLE IF NOT EXISTS cars (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    brand VARCHAR(255),
    color VARCHAR(255),
    mileage INTEGER,
    year_release INTEGER,
    price INTEGER
    );