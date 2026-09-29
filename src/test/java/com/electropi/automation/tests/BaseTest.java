package com.electropi.automation.tests;

import com.electropi.automation.config.Config;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();

        BrowserType browserType = switch (Config.browser().toLowerCase()) {
            case "firefox" -> playwright.firefox();
            case "webkit" -> playwright.webkit();
            default -> playwright.chromium();
        };

        browser = browserType.launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(Config.headless())
                        .setSlowMo(Config.slowMo())
        );

        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(1440, 900)
        );

        page = context.newPage();
        page.setDefaultTimeout(Config.timeoutMs());
        page.setDefaultNavigationTimeout(Config.timeoutMs());
    }

    @AfterEach
    void tearDown() {
        if (context != null) {
            context.close();
        }

        if (browser != null) {
            browser.close();
        }

        if (playwright != null) {
            playwright.close();
        }
    }
}
