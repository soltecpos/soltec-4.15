
-- Database upgrade script for PostgreSQL
-- v4.3 - v4.5

-- UPDATE MENU --
UPDATE resources SET content = $FILE{/com/openbravo/pos/templates/Menu.Root.txt} WHERE name = 'Menu.Root';

-- UPDATE App' version
UPDATE applications SET NAME = $APP_NAME{}, VERSION = $APP_VERSION{} WHERE ID = $APP_ID{};
