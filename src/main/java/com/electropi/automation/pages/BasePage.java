package com.electropi.automation.pages;

import com.microsoft.playwright.Page;

public abstract class BasePage {

    protected final Page page;

    protected BasePage(Page page) {
        this.page = page;
    }

    protected void waitForPageLoad() {
        page.waitForLoadState();
    }

    protected void waitForSpinnerToDisappear(String selector) {
        var spinner = page.locator(selector);

        if (spinner.count() > 0) {
            spinner.first().waitFor(
                    new com.microsoft.playwright.Locator.WaitForOptions()
                            .setState(com.microsoft.playwright.options.WaitForSelectorState.HIDDEN)
            );
        }
    }
}
