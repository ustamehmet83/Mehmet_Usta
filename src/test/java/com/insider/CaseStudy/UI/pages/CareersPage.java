package com.insider.CaseStudy.UI.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;

@Component
public class CareersPage extends BasePage{

    @FindBy(xpath = "//a[@href='https://useinsider.com/careers/customer-success/']/img")
    public WebElement customerSuccessBtn;

    @FindBy(xpath = "//img[@src='https://useinsider.com/assets/media/2021/03/indianapolis-1.png']")
    public WebElement newYorkLocationBtn;

    @FindBy(xpath = "(//div[contains(@style, 'https://useinsider.com/assets/media/2022/09/life-at-insider-16.png')])[1]")
    public WebElement lifeAtInsiderImage;




}
