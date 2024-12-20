package com.insider.CaseStudy.UI.pages;

import com.insider.CaseStudy.UI.utilities.BrowserUtils;
import com.insider.CaseStudy.UI.utilities.Driver;
import org.openqa.selenium.support.PageFactory;

import javax.annotation.PostConstruct;

public class BasePage extends BrowserUtils {

    @PostConstruct
    public void initDriver() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

}
