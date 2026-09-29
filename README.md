# Electro Pi -  UI Automation

Implementation of **Part 1 - UI Test Automation & Architecture** from the Electro Pi QA Automation Engineer assessment.

## Flow automated

1. Login as Store Admin
2. Navigate to Inventory
3. Enter Product Name
4. Enter Price
5. Click Save
6. Assert the success toast

## Technology

- Java 17
- Playwright for Java
- JUnit 5
- Maven
- Page Object Model

## Project structure

```text
electro-pi-automation-task
├── pom.xml
├── README.md
├── .gitignore
└── src/
    ├── main/
    │   └── java/com/electropi/automation/
    │       ├── config/
    │       │   └── Config.java
    │       └── pages/
    │           ├── BasePage.java
    │           ├── LoginPage.java
    │           └── InventoryPage.java
    └── test/
        ├── java/com/electropi/automation/tests/
        │   ├── BaseTest.java
        │   └── InventoryTest.java
        └── resources/
            ├── config.properties
            └── testdata/
                └── inventory.properties
```

## Setup

### 1. Configure the application

Edit:

`src/test/resources/config.properties`

```properties
base.url=https://website.com
browser=chromium
headless=true
slowmo=0
timeout.ms=15000
```

Set credentials as environment variables instead of committing passwords:

```powershell
$env:STORE_ADMIN_EMAIL="admin@example.com"
$env:STORE_ADMIN_PASSWORD="your-password"
```

### 2. Install Playwright browsers

```bash
mvn test
```

If browsers are not installed yet:

```bash
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install chromium"
```

## Run

```bash
mvn clean test
```

Run headed:

```bash
mvn clean test -Dheadless=false
```

Run with a different base URL:

```bash
mvn clean test -Dbase.url=https://staging.example.com
```

## Selector strategy

The assessment does not provide the application's actual DOM. The Page Objects therefore use semantic selectors such as:

- `getByLabel("Email")`
- `getByRole(BUTTON, new Page.GetByRoleOptions().setName("Save"))`
- `getByText("Inventory")`

If the real application has `data-testid` attributes, prefer those because they are less coupled to UI wording.

Example:

```java
page.getByTestId("inventory-nav").click();
```

Keep application-specific selectors inside Page Objects; tests should describe business behavior rather than locator details.

## Stability strategy

This implementation intentionally does not use `Thread.sleep()`.

Playwright automatically waits for elements to become actionable. Explicit waits are used only for meaningful application states, such as:

- URL navigation
- loading spinner disappearing
- success toast becoming visible

For APIs that render asynchronously, prefer waiting for the UI state or a specific response rather than waiting an arbitrary number of milliseconds.
