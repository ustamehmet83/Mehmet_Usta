package com.insider.CaseStudy.UI.utilities;

import com.insider.CaseStudy.UI.tests.BaseTest.BaseTests;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOf;

public class BrowserUtils {


    /**
     * Wait till element is invisible
     *
     * @param webElement
     */
    public void waitForInvisibility(WebElement webElement) {
        Driver.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(60));
        wait.until(invisibilityOf(webElement));
    }

    /**
     * Wait till element is visible
     *
     * @param webElement
     */
    public void waitForVisibility(WebElement webElement) {
        Driver.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    /**
     * Wait till elements is visible
     *
     * @param webElements
     */
    public void waitForVisibility(List<WebElement> webElements) {
        Driver.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(60));
        for (WebElement webElement : webElements) {
            wait.until(ExpectedConditions.visibilityOf(webElement));
        }


    }

    /**
     * Wait till elements is clickable
     *
     * @param webElement
     */

    public void waitForClickable(WebElement webElement) {
        Driver.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(15));
        wait.until(elementToBeClickable(webElement));
    }


    /**
     * Clicks on an element using JavaScript
     *
     * @param element
     */
    public void clickWithJS(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", element);
    }


    /**
     * Wait till elements is visible
     * Wait till elements is clickable
     * click element with JS executor
     *
     * @param element
     */

    public void waitForVisibilityClickableAndclickWithJS(WebElement element) {
        waitForVisibility(element);
        waitForClickable(element);
        clickWithJS(element);
    }


    /**
     * Scrolls down to an element using JavaScript
     *
     * @param element
     */
    public WebElement scrollToElement(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) Driver.getDriver();
        jsExecutor.executeScript("const rect = arguments[0].getBoundingClientRect();" +
                "const y = rect.top + window.scrollY - (window.innerHeight / 2);" +
                "window.scrollTo(0, y);", element);
        return element;
    }

    /**
     * Scroll to the top of the page
     */
    public void scrollTopToPage() {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("window.scrollTo(0, 0);");
    }


    /**
     * Performs a pause
     *
     * @param seconds
     */
    public void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Assert all after scenario
     * Write error message if scenario fail
     *
     */


    /**
     * Check file is available which download Location directory
     *
     * @param downloadLocation , expectedFileName
     */
    public boolean isFileAvailable(String downloadLocation, String expectedFileName) {

        File folder = new File(downloadLocation);
        File[] listOfFiles = folder.listFiles();
        boolean isFileAvailable = false;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                String fileName = listOfFile.getName();
                System.out.println("fileName = " + fileName);
                if (fileName.matches(expectedFileName)) {
                    isFileAvailable = true;
                }
            }
        }
        return isFileAvailable;

    }

    /**
     * Delete file in the directory folder
     *
     * @param downloadLocation , expectedFileName
     */
    public void cleanFolder(String downloadLocation) throws IOException {
        File directory = new File(downloadLocation);
        FileUtils.cleanDirectory(directory);
    }

    /**
     * wait till file is downloaded
     *
     * @param downloadLocation , expectedFileName
     */
    public void waitForFileDownload(String downloadLocation, String fileName, int timeout) throws InterruptedException {
        Path filePath = Paths.get(downloadLocation, fileName);
        for (int i = 0; i < timeout; i++) {
            if (Files.exists(filePath)) {
                break;
            }
            Thread.sleep(1000);
        }

    }

    public void waitForStaleWebElement(WebElement element) {
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(30));
        wait.until(refreshed(ExpectedConditions.visibilityOf(element)));
    }

    public void waitForStaleWebElements(By locator) {
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(30));

            wait.until(refreshed(ExpectedConditions.presenceOfAllElementsLocatedBy(locator)));


    }
    public <T> ExpectedCondition<T> refreshed(final ExpectedCondition<T> condition) {
        return new ExpectedCondition<T>() {
            public T apply(WebDriver driver) {
                boolean stale = true;
                int retries = 0;
                T result = null;

                while (stale && retries < 5) {
                    try {
                        result = condition.apply(Driver.getDriver());
                        stale = false;
                        waitFor(1);
                    } catch (StaleElementReferenceException ex) {
                        retries++;
                    }
                }

                if (stale) {
                    throw new RuntimeException("Element is still stale after 5 attempts");
                }

                return result;
            }

            public String toString() {
                return String.format("condition (%s) to be refreshed", condition);
            }
        };
    }

    public void waitForTextContainsVisibility(WebElement webElement, String Text) {
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(30));
        wait.until(textToBeContainElementTextContent(webElement, Text));
    }

    public ExpectedCondition<Boolean> textToBeContainElementTextContent(final WebElement element, final String text) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                try {
                    String elementText = element.getAttribute("textContent");
                    return elementText != null ? elementText.contains(text) : false;
                } catch (StaleElementReferenceException var3) {
                    return false;
                }
            }
        };
    }

    public static void getThreadAssert() {
        if (BaseTests.softAssertionsThreadTotal.get() != null) {
            try {
                BaseTests.softAssertionsThreadTotal.get().assertAll();
            } catch (AssertionError e) {
                System.out.println("Assertion errors: " + e.getMessage());
                throw e;
            } finally {
                BaseTests.softAssertionsThreadTotal.remove();
            }
        }
    }

    public static void getAssertThat(boolean bool) {

        if (BaseTests.softAssertionsThread.get() == null) {
            BaseTests.softAssertionsThread.set(new SoftAssertions());
        }
        BaseTests.softAssertionsThread.get().assertThat(bool).isTrue();
        BaseTests.softAssertionsThreadTotal.get().assertThat(bool).isTrue();
        if (BaseTests.softAssertionsThread.get() != null) {
            try {
                BaseTests.softAssertionsThread.get().assertAll();
            } catch (AssertionError e) {
                takeScreenshot(Driver.getDriver()); // Take screenshot on failure
            } finally {
                BaseTests.softAssertionsThread.remove(); // Clean up
            }
        }
    }


    public static void takeScreenshot(WebDriver driver) {
        try {
            File screenshotFile = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filePath = System.getProperty("tests.dir") + File.separator + "ScreenShots" + File.separator + timestamp+ ".png";
            FileUtils.copyFile(screenshotFile, new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void waitForVisibilityWithFluentWait(WebElement webElement) {
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        FluentWait wait = new FluentWait(Driver.getDriver());
        wait.withTimeout(Duration.ofSeconds(15));
        wait.pollingEvery(Duration.ofSeconds(1));
        wait.ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }


    public void waitForTextContainsWithFluentWait(WebElement webElement, By locator) {
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        FluentWait<WebDriver> wait = new FluentWait<>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        wait.until(textToBeStable(webElement, locator));
    }

    public ExpectedCondition<Boolean> textToBeStable(WebElement element, By locator) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                String previousText = element.getAttribute("textContent");
                int retries = 0;
                for (int i = 0; i < 5; i++) {
                    try {
                        while (retries < 5) {
                            waitFor(1);
                            WebElement element = Driver.getDriver().findElement(locator);
                            String currentText = element.getAttribute("textContent");
                            if (!currentText.equals(previousText)) {
                                previousText = currentText;
                                currentText = "";
                            }
                            if ((currentText.contains(previousText))) {
                                return true;
                            }
                            retries++;
                        }
                    } catch (StaleElementReferenceException st) {
                    }
                }
                return false;
            }

            @Override
            public String toString() {
                return String.format("Element is not stabil");
            }
        };
    }

    public void waitForElementListSizeToBeMoreThanZero(List<WebElement> elements) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(15));
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                List<WebElement> element = elements;
                return elements.size() > 0;
            }
        });
    }


    public static void clearScreenshotsFolder() {

        String screenshotsFolderPath = System.getProperty("tests.dir") + File.separator + "ScreenShots";

        File folder = new File(screenshotsFolderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        } else {
            System.out.println("Screenshots folder does not exist or is not a directory.");
        }
    }

}
