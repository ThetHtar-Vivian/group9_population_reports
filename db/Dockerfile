# Use the latest MySQL image
FROM mysql/mysql-server:latest

COPY world-db/world.sql /docker-entrypoint-initdb.d
# Set the root password
ENV MYSQL_ROOT_PASSWORD abc123!@#