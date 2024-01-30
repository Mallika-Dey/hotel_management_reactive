CREATE TABLE IF NOT EXISTS hotel (
    id SERIAL PRIMARY KEY,
    loc_id SERIAL,
    amenities VARCHAR (250),
    availability BOOLEAN,
    max_price SERIAL,
    min_price SERIAL
);

CREATE TABLE IF NOT EXISTS room_type (
    id SERIAL PRIMARY KEY,
    room_type VARCHAR (250)
);

CREATE TABLE IF NOT EXISTS location (
    id SERIAL PRIMARY KEY,
    district VARCHAR (250),
    latitude DECIMAL,
    longitude DECIMAL
);