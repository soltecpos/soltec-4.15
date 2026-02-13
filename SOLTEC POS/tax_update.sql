-- SQL Script to Configure Colombian Taxes
-- Run this in your Database Manager (e.g. Workbench or via Unicenta Configuration if possible)

-- 1. Create Tax Categories
-- Assuming IDs from your image: 00, 01, 02

INSERT INTO taxcategories (id, name) VALUES ('00', 'Exento') ON DUPLICATE KEY UPDATE name='Exento';
INSERT INTO taxcategories (id, name) VALUES ('01', 'Estándar 19%') ON DUPLICATE KEY UPDATE name='Estándar 19%';
INSERT INTO taxcategories (id, name) VALUES ('02', 'Reducido 5%') ON DUPLICATE KEY UPDATE name='Reducido 5%';
INSERT INTO taxcategories (id, name) VALUES ('03', 'Impoconsumo') ON DUPLICATE KEY UPDATE name='Impoconsumo';

-- 2. Create Taxes
-- IVA 19%
INSERT INTO taxes (id, name, category, rate, ratecascade, rateorder) 
VALUES ('01', 'IVA_19', '01', 0.19, 0, 1) 
ON DUPLICATE KEY UPDATE rate=0.19;

-- IVA 5%
INSERT INTO taxes (id, name, category, rate, ratecascade, rateorder) 
VALUES ('02', 'IVA_5', '02', 0.05, 0, 2) 
ON DUPLICATE KEY UPDATE rate=0.05;

-- Exento (0%)
INSERT INTO taxes (id, name, category, rate, ratecascade, rateorder) 
VALUES ('00', 'Exento', '00', 0.00, 0, 3) 
ON DUPLICATE KEY UPDATE rate=0.00;

-- INC (Impoconsumo 8%) - Often applied to restaurants
INSERT INTO taxes (id, name, category, rate, ratecascade, rateorder) 
VALUES ('05', 'INC', '03', 0.08, 0, 4) 
ON DUPLICATE KEY UPDATE rate=0.08;

-- Placeholder for others (ICA, ICUI) - Set to 0% initially
INSERT INTO taxes (id, name, category, rate, ratecascade, rateorder) 
VALUES ('03', 'IC', '00', 0.00, 0, 5) 
ON DUPLICATE KEY UPDATE rate=0.00;

INSERT INTO taxes (id, name, category, rate, ratecascade, rateorder) 
VALUES ('04', 'ICA', '00', 0.00, 0, 6) 
ON DUPLICATE KEY UPDATE rate=0.00;

INSERT INTO taxes (id, name, category, rate, ratecascade, rateorder) 
VALUES ('06', 'ICUI', '00', 0.00, 0, 7) 
ON DUPLICATE KEY UPDATE rate=0.00;
