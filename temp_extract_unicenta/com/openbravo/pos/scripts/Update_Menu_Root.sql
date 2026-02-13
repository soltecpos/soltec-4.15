
UPDATE RESOURCES 
SET CONTENT = REPLACE(CONTENT, 
    'submenu.addPanel("/com/openbravo/images/bookmark.png", "Menu.TaxCategories", "com.openbravo.pos.inventory.TaxCategoriesPanel");', 
    'submenu.addPanel("/com/openbravo/images/bookmark.png", "Menu.TaxCategories", "com.openbravo.pos.inventory.TaxCategoriesPanel");' || CHAR(10) || '        submenu.addPanel("/com/openbravo/images/bookmark.png", "Menu.PaymentCategories", "com.openbravo.pos.inventory.PaymentCategoriesPanel");')
WHERE NAME = 'Menu.Root' AND CONTENT NOT LIKE '%Menu.PaymentCategories%';
