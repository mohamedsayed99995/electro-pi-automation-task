package com.electropi.automation.pages;

import com.electropi.automation.config.Config;
import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {

    private final String emailInput = "input[name='email']";
    private final String passwordInput = "input[name='password']";
    private final String loginButton = "button[type='submit']";

    public LoginPage(Page page) {
        super(page);
    }

    public LoginPage open() {
        page.navigate(Config.baseUrl() + "/login");
        waitForPageLoad();
        return this;
    }

    public InventoryPage loginAsStoreAdmin() {
        page.locator(emailInput).fill(Config.adminEmail());
        page.locator(passwordInput).fill(Config.adminPassword());
        page.locator(loginButton).click();

        page.waitForURL("**/dashboard**");
        return new InventoryPage(page);
    }
}
