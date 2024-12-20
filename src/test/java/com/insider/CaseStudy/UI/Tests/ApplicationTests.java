package com.insider.CaseStudy.UI.Tests;

import com.insider.CaseStudy.SpringStartApplication;
import com.insider.CaseStudy.UI.Tests.BaseTest.BaseTests;
import com.insider.CaseStudy.UI.pages.CareersPage;
import com.insider.CaseStudy.UI.pages.HomePage;
import com.insider.CaseStudy.UI.pages.OpenPositionsPage;
import com.insider.CaseStudy.UI.pages.QualityAssurancePage;
import com.insider.CaseStudy.UI.utilities.BrowserUtils;
import com.insider.CaseStudy.UI.utilities.ConfigurationReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest(classes = SpringStartApplication.class)
class ApplicationTests extends BaseTests {

    @Autowired
    private HomePage homePage;

    @Autowired
    private CareersPage careersPage;

    @Autowired
    private QualityAssurancePage qualityAssurancePage;

    @Autowired
    private OpenPositionsPage openPositionsPage;


    @Test
    public void careerTest() {
        // Step 1: Open Insider homepage
        driver.get(ConfigurationReader.getProperty("appUrl"));
        if (homePage.acceptCookiesBtn.isDisplayed()) {
            homePage.acceptCookiesBtn.click();
        }

        // Step 2: Click on the “Company” menu and select “Careers”
        homePage.companyBtn.click();
        homePage.careersBtn.click();

        // Step 3: Verify the "Locations", "Teams", "Life at Insider" blocks are present
        BrowserUtils.getAssertThat(driver.getCurrentUrl().equals("https://useinsider.com/careers/"));
        BrowserUtils.getAssertThat(careersPage.scrollToElement(careersPage.customerSuccessBtn).isEnabled());
        BrowserUtils.getAssertThat(careersPage.scrollToElement(careersPage.newYorkLocationBtn).isEnabled());
        BrowserUtils.getAssertThat(careersPage.scrollToElement(careersPage.lifeAtInsiderImage).isEnabled());
    }

    @ParameterizedTest
    @CsvSource({
            "'Istanbul, Turkey', 'Quality Assurance'"
    })
    public void qualityAssuranceTest(String location, String department) throws InterruptedException {
        // Step 1: Go to Quality Assurance careers page
        driver.get(ConfigurationReader.getProperty("appQualityAssuranceUrl"));
        homePage.waitForVisibilityClickableAndclickWithJS(homePage.acceptCookiesBtn);

        // Step 2: Click on "See all QA jobs"
        qualityAssurancePage.seeAllQAJobsBtn.click();

        // Step 3: Filter jobs by Location: “Istanbul, Turkey” and Department: “Quality Assurance”
        openPositionsPage.waitForTextContainsVisibility(openPositionsPage.filterByDepartment, department);
        /*
        Select selectLocation = new Select(openPositionsPage.locationDropdown);
        selectLocation.selectByVisibleText(location);

        Select selectDepartment = new Select(openPositionsPage.departmentDropdown);
        selectDepartment.selectByVisibleText(department);
         */
        openPositionsPage.filterByLocation.click();
        openPositionsPage.waitForVisibility(openPositionsPage.filterByLocations);
        for (WebElement filterByLocation : openPositionsPage.filterByLocations) {
            if (filterByLocation.getText().equals(location)) {
                openPositionsPage.scrollToElement(filterByLocation).click();
                break;
            }
        }
        openPositionsPage.waitForStaleWebElements(openPositionsPage.positionListsLocator);
        openPositionsPage.filterByDepartment.click();
        openPositionsPage.waitForVisibility(openPositionsPage.filterByDepartments);
        for (WebElement filterByDepartment : openPositionsPage.filterByDepartments) {
            if (filterByDepartment.getText().equals(department)) {
                filterByDepartment.click();
                break;
            }
        }

        openPositionsPage.waitForElementListSizeToBeMoreThanZero(openPositionsPage.positionLists);
        openPositionsPage.scrollToElement(openPositionsPage.positionLists.get(0));

        // Step 4: Verify job list is present
        BrowserUtils.getAssertThat(openPositionsPage.positionLists.size() > 0);

        // Step 5: Verify jobs' Position, Department, and Location
        openPositionsPage.waitForTextContainsWithFluentWait(openPositionsPage.positionDepartments.get(0), openPositionsPage.firstPositionDepartmentLocator);
        for (WebElement positionTitle : openPositionsPage.positionTitles) {
            BrowserUtils.getAssertThat(positionTitle.getText().contains(department));
        }

        for (WebElement positionDepartment : openPositionsPage.positionDepartments) {
            BrowserUtils.getAssertThat(positionDepartment.getText().contains(department));
        }

        for (WebElement positionLocation : openPositionsPage.positionLocations) {
            BrowserUtils.getAssertThat(positionLocation.getText().contains(location));
        }

        // Step 6: Click "View Role" and verify redirection
        openPositionsPage.scrollToElement(openPositionsPage.viewRoleBtn).click();

        // Step 7: Verify the redirection to the Lever application form page
        Set<String> allWindowHandles = driver.getWindowHandles();
        for (String each : allWindowHandles) {
            driver.switchTo().window(each);
            if (driver.getTitle().contains("Insider.")) {
                break;
            }
        }
        BrowserUtils.getAssertThat(driver.getCurrentUrl().contains("lever"));
    }


}
