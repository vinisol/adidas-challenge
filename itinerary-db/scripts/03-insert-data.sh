#!/usr/bin/env bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    \connect itineraries;

    INSERT INTO itinerary (id, departure_city, arrival_city, departure_time, arrival_time) VALUES (nextval('itinerary_seq'), 'Berlin', 'Brussels', '2019-10-14T11:05:00', '2019-10-14T12:35:00');
    INSERT INTO itinerary (id, departure_city, arrival_city, departure_time, arrival_time) VALUES (nextval('itinerary_seq'), 'Berlin', 'Paris', '2019-10-14T09:25:00', '2019-10-14T11:15:00');
    INSERT INTO itinerary (id, departure_city, arrival_city, departure_time, arrival_time) VALUES (nextval('itinerary_seq'), 'Brussels', 'Madrid', '2019-10-14T14:35:00', '2019-10-14T16:50:00');
    INSERT INTO itinerary (id, departure_city, arrival_city, departure_time, arrival_time) VALUES (nextval('itinerary_seq'), 'Paris', 'Brussels', '2019-10-14T07:25:00', '2019-10-14T08:25:00');
    INSERT INTO itinerary (id, departure_city, arrival_city, departure_time, arrival_time) VALUES (nextval('itinerary_seq'), 'Paris', 'Roma', '2018-10-14T21:20:00', '2018-10-14T23:25:00');
    INSERT INTO itinerary (id, departure_city, arrival_city, departure_time, arrival_time) VALUES (nextval('itinerary_seq'), 'Roma', 'Madrid', '2019-10-14T18:25:00', '2019-10-14T21:05:00');
    INSERT INTO itinerary (id, departure_city, arrival_city, departure_time, arrival_time) VALUES (nextval('itinerary_seq'), 'Amsterdam', 'Roma', '2019-10-14T16:55:00', '2019-10-14T19:10:00');
    INSERT INTO itinerary (id, departure_city, arrival_city, departure_time, arrival_time) VALUES (nextval('itinerary_seq'), 'Amsterdam', 'Madrid', '2019-10-14T07:20:00', '2019-10-14T10:00:00');
    INSERT INTO itinerary (id, departure_city, arrival_city, departure_time, arrival_time) VALUES (nextval('itinerary_seq'), 'Madrid', 'Berlin', '2019-10-14T12:20:00', '2019-10-14T15:25:00');
EOSQL
