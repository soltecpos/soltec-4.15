-- -----------------------------------------------------
-- Database Schema for Blind Physical Inventory
-- Soltec POS v4.51
-- -----------------------------------------------------

-- Table for scheduled inventory tasks (e.g., Weekly drinks count)
CREATE TABLE IF NOT EXISTS inventory_schedules (
    ID VARCHAR(255) PRIMARY KEY,
    LOCATION_ID VARCHAR(255) NOT NULL,
    ASSIGNEE_ROLE VARCHAR(255) NOT NULL,
    FREQUENCY VARCHAR(50) NOT NULL, 
    CATEGORIES TEXT
);

-- Table for actual inventory count instances
CREATE TABLE IF NOT EXISTS inventory_tasks (
    ID VARCHAR(255) PRIMARY KEY,
    STATUS VARCHAR(50) NOT NULL, 
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    AUTHOR_ID VARCHAR(255) NOT NULL,
    LOCATION_ID VARCHAR(255) NOT NULL
);

-- Table for the specific lines within a task
CREATE TABLE IF NOT EXISTS inventory_task_lines (
    TASK_ID VARCHAR(255) NOT NULL,
    PRODUCT_ID VARCHAR(255) NOT NULL,
    SYSTEM_QTY DOUBLE PRECISION NOT NULL,
    COUNTED_QTY DOUBLE PRECISION,
    DIFFERENCE DOUBLE PRECISION,
    PRIMARY KEY (TASK_ID, PRODUCT_ID)
);

-- Index for faster queries
CREATE INDEX IDX_INV_TSK_STATUS ON inventory_tasks(STATUS);
CREATE INDEX IDX_INV_TSK_LOC ON inventory_tasks(LOCATION_ID);
