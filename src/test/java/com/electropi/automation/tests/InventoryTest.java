package com.electropi.automation.tests;

import com.electropi.automation.pages.InventoryPage;
import com.electropi.automation.pages.LoginPage;
import org.junit.jupiter.api.Test;


public class InventoryTest extends BaseTest {

    @Test
    void storeAdminCanCreateInventoryProduct() {

        InventoryPage inventoryPage = new LoginPage(page)
                .open()
                .loginAsStoreAdmin()
                .open();

        inventoryPage
                .enterProductName("Wireless Mouse")
                .enterPrice("25.00")
                .save();

        inventoryPage.assertSuccessToast("Product saved successfully");
    }
}
