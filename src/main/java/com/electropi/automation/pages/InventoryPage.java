package com.electropi.automation.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class InventoryPage extends BasePage {

    private final String inventoryLink = "text=Inventory";
    private final String productNameInput = "input[name='productName']";
    private final String priceInput = "input[name='price']";
    private final String saveButton = "button:has-text('Save')";
    private final String loadingSpinner = "[data-testid='loading-spinner']";
    private final String successToast = "[role='alert']";

    public InventoryPage(Page page) {
        super(page);
    }

    public InventoryPage open() {
        page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Inventory"))
                .click();

        page.waitForURL("**/inventory**");
        waitForSpinnerToDisappear(loadingSpinner);
        return this;
    }

    public InventoryPage enterProductName(String productName) {
        page.locator(productNameInput).fill(productName);
        return this;
    }

    public InventoryPage enterPrice(String price) {
        page.locator(priceInput).fill(price);
        return this;
    }

    public InventoryPage save() {
        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Save"))
                .click();
        return this;
    }

    public void assertSuccessToast(String expectedMessage) {
        var toast = page.locator(successToast).filter(
                new com.microsoft.playwright.Locator.FilterOptions()
                        .setHasText(expectedMessage)
        );

        toast.waitFor(new com.microsoft.playwright.Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
    }
}
