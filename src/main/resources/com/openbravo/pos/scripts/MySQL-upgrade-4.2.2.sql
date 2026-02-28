-- Database upgrade script for MySQL
-- v4.2.2 - v4.3

UPDATE applications SET NAME = $APP_NAME{}, VERSION = $APP_VERSION{} WHERE ID = $APP_ID{};
