package com.insider.CaseStudy.UI.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;

@Component
public class HomePage extends BasePage {

    @FindBy(xpath = "//a[@id='wt-cli-accept-all-btn']")
    public WebElement acceptCookiesBtn;

    @FindBy(xpath = "//a[normalize-space()='Company']")
    public WebElement companyBtn;

    @FindBy(xpath = "//a[.='Careers']")
    public WebElement careersBtn;





}
