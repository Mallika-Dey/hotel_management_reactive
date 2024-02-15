CREATE TABLE IF NOT EXISTS room_booked (
    id SERIAL PRIMARY KEY,
    hotel_details_id INTEGER,
    start_date DATE,
    end_date DATE
);

CREATE TABLE IF NOT EXISTS hotel_details (
    id SERIAL PRIMARY KEY,
    hotel_id INTEGER,
    room_type_id INTEGER,
    room_count INTEGER,
    price INTEGER
);