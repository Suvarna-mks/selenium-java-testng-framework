# selenium-java-testng-framework

[![selenium](https://github.com/Suvarna-mks/selenium-java-testng-framework/actions/workflows/selenium.yml/badge.svg)](https://github.com/Suvarna-mks/selenium-java-testng-framework/actions/workflows/selenium.yml)

A Selenium WebDriver + Java + TestNG framework using the Page Object Model, tested against [saucedemo.com](https://www.saucedemo.com). Runs in parallel, headless by default, on Chrome and Firefox in CI.

## Run it

Needs Java 17+ and Maven.

```bash
mvn test                                  # all tests, headless Chrome
mvn test -Dheadless=false                 # watch the browser
mvn test -Dbrowser=firefox
mvn test -Dgroups=smoke                   # smoke group only
```

Reports: `target/surefire-reports/index.html`. Screenshots of failed tests: `target/screenshots/`.

## What's covered

| class | tests |
|---|---|
| LoginTest | valid login, 4 invalid logins from a `@DataProvider` |
| InventoryTest | 6 products, sort by price, sort by name Z-A, cart badge |
| CheckoutTest | 2-item checkout with subtotal, 8% tax and total checked, postal code required |

## How it's built

```
src/test/java/io/github/suvarnamks/qa/
  base/DriverFactory     ThreadLocal WebDriver, chrome/firefox, headless flag
  base/BaseTest          open before each test, quit after
  pages/                 BasePage (explicit waits), Login, Inventory, Cart, Checkout
  data/Users             credentials + @DataProvider for negative logins
  listeners/             screenshot on failure
  tests/                 LoginTest, InventoryTest, CheckoutTest
testng.xml               parallel="methods", thread-count 3
```

## Why I built this

Most QA job descriptions I read ask for Selenium with Java and TestNG. I wanted a clean, small framework showing the parts interviewers ask about: POM, explicit waits, data providers, parallel runs with ThreadLocal drivers, listeners, and CI.

## What I learned

- My checkout tests failed with the Checkout button clearly visible in the screenshot. The cause was Chrome's "password found in a data breach" popup after logging in with the public demo password. It's browser UI, so it never shows in page screenshots, but it swallows clicks. Turning off the password manager prefs in `ChromeOptions` fixed it
- Flaky clicks: waiting for the element isn't enough. After `addToCart` I wait for the button to turn into "Remove", and after Checkout I wait for the next URL, so a lost click fails at the right line
- On this single-page app the URL changes before the old list leaves the page, so the cart once read 6 products instead of 2. Reading only inside `.cart_list`, with a wait that retries on stale elements, fixed it
- Parallel tests need one driver per thread. A static driver shared by all tests breaks as soon as `parallel="methods"` is on, which is why `DriverFactory` uses `ThreadLocal`
- Selenium Manager (built into Selenium 4) downloads the matching chromedriver, so no WebDriverManager or driver path setup is needed
- Page methods that return the next page (`loginAs()` returns `InventoryPage`) make tests read like the user journey
- Comparing money as `double` needs a delta in `assertEquals`, or 43.18 vs 43.179999 fails

## Known limitations / TODO

- [ ] Allure or Extent report instead of the default TestNG report
- [ ] Read base URL and browser from a config file per environment
- [ ] Retry analyzer for known-flaky network issues, with a log of retried tests
