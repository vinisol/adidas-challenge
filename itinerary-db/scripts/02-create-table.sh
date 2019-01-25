#!/usr/bin/env bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    \connect itineraries;

    CREATE TABLE itinerary (
        id integer PRIMARY KEY,
        departure_city VARCHAR(255) NOT NULL,
        arrival_city VARCHAR(255) NOT NULL,
        departure_time TIMESTAMP NOT NULL,
        arrival_time TIMESTAMP NOT NULL,
        CONSTRAINT valid_cities CHECK (departure_city <> arrival_city));

    CREATE SEQUENCE itinerary_seq;

    GRANT SELECT, INSERT, UPDATE, DELETE ON itinerary TO john;
EOSQL
