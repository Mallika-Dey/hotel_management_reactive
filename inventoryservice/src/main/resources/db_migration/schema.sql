CREATE TABLE IF NOT EXISTS room_booked (
    id SERIAL PRIMARY KEY,
    hotel_details_id INTEGER,
    start_date TIMESTAMP,
    end_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS hotel_details (
    id SERIAL PRIMARY KEY,
    hotel_id INTEGER,
    room_type_id INTEGER,
    capacity INTEGER,
    price INTEGER
);