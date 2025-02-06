#!/bin/bash
set -e

# Run the standard entrypoint script in the background
docker-entrypoint.sh postgres &

# Wait for PostgreSQL to be ready
until pg_isready -h localhost -p 5432 -U postgres; do
  echo "Waiting for PostgreSQL..."
  sleep 2
done

# Run the custom initialization script only if the database is empty
if psql -U postgres -d studentsDB -c '\dt' | grep -q 'No relations found.'; then
  echo "Initializing database..."
  psql -U postgres -d studentsDB -f /docker-entrypoint-initdb.d/init.sql
else
  echo "Database already initialized."
  psql -U postgres -d studentsDB -f /docker-entrypoint-initdb.d/init.sql
fi

# Keep the container running
wait