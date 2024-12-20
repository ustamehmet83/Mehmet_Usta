package com.insider.CaseStudy.UI.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;

@Component
public class QualityAssurancePage extends BasePage {

    @FindBy(xpath = "//a[.='See all QA jobs']")
    public WebElement seeAllQAJobsBtn;
}
