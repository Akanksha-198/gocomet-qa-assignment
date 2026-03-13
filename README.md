# GoComet QA Intern Assignment
## Automated Testing with Java + Playwright + Cucumber

---

## 📋 Assignment Overview

Automated test suite for **CaseKaro** (https://casekaro.com/) that:
1. Navigates to the website
2. Searches for Apple mobile covers
3. Validates no other brands appear (negative validation)
4. Finds iPhone 16 Pro cases
5. Adds all 3 materials (Hard, Soft, Glass) to cart
6. Validates cart contents
7. Prints item details (material, price, link) to console

---

## 🏗️ Project Structure

```
gocomet-qa-assignment/
├── pom.xml
└── src/
    └── test/
        ├── java/
        │   ├── pages/
        │   │   ├── PlaywrightManager.java     # Browser lifecycle manager
        │   │   ├── HomePage.java              # Homepage - navigation & search
        │   │   ├── SearchResultsPage.java     # Search results & brand validation
        │   │   ├── ProductListingPage.java    # Product listing & model search
        │   │   ├── ProductDetailsPage.java    # Product details & add to cart
        │   │   └── CartPage.java              # Cart validation & printing
        │   ├── stepDefinitions/
        │   │   └── MobileCoverSteps.java      # All Cucumber step definitions
        │   └── runner/
        │       └── CucumberTestRunner.java    # JUnit 5 test runner
        └── resources/
            ├── features/
            │   └── MobileCoverShopping.feature  # Cucumber BDD feature file
            └── junit-platform.properties
```

---

## ⚙️ Prerequisites

| Tool        | Version     |
|-------------|-------------|
| Java        | 17+         |
| Maven       | 3.8+        |
| Chromium    | Auto-downloaded by Playwright |

---

## 🚀 Setup & Run

### 1. Install Playwright browsers
```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install chromium"
```

### 2. Run the tests
```bash
mvn test
```

### 3. View the HTML report
Open `target/cucumber-reports/report.html` in your browser.

---

## 🎯 Key Design Decisions

### ✅ No try-catch blocks
As per requirements, all exceptions propagate naturally. Playwright's built-in timeout/assertion mechanisms handle failures gracefully.

### ✅ Cucumber Feature File
Full BDD scenario written in Gherkin with Background, Given/When/Then steps.

### ✅ Assertions
`org.junit.jupiter.api.Assertions` used throughout for all validations:
- URL contains `casekaro.com` after navigation
- Apple brand visible after search
- Competitor brands NOT visible (negative validation)
- Cart contains exactly 3 items
- Each material (Hard, Soft, Glass) present in cart

### ✅ Console Output (Step 11)
Detailed table printed to stdout:
```
================================================================================
📦 CART ITEM DETAILS SUMMARY
================================================================================
#     Material   Price        Product Link
--------------------------------------------------------------------------------
1     Hard       ₹ 69.00      https://casekaro.com/products/...
2     Soft       ₹ 149.00     https://casekaro.com/products/...
3     Glass      ₹ 249.00     https://casekaro.com/products/...
================================================================================
```

### ✅ Page Object Model
Clean separation of concerns — each page has its own class.

---

## 📦 Dependencies

| Library                          | Version | Purpose                     |
|----------------------------------|---------|-----------------------------|
| `com.microsoft.playwright`       | 1.44.0  | Browser automation          |
| `io.cucumber:cucumber-java`      | 7.18.0  | BDD step definitions        |
| `io.cucumber:cucumber-junit-...` | 7.18.0  | JUnit 5 Cucumber engine     |
| `io.cucumber:cucumber-picocontainer` | 7.18.0 | DI between step classes  |
| `org.junit.jupiter`              | 5.10.2  | Assertions & test lifecycle |

---

## 🔍 Negative Validation Details

After searching "Apple", the test validates that these brands are **NOT** present:
- Samsung, OnePlus, Xiaomi, Redmi, Realme, OPPO, Vivo, Motorola, Nokia, Google Pixel

Each brand triggers an `assertFalse` with a descriptive failure message.
