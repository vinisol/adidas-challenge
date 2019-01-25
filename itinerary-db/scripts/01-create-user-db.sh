#!/usr/bin/env bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE USER john PASSWORD 'pw123';
    CREATE DATABASE itineraries;
    GRANT ALL PRIVILEGES ON DATABASE itineraries TO john;
EOSQL
