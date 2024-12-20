package com.insider.CaseStudy.UI.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpenPositionsPage extends BasePage{

    @FindBy(xpath = "//select[@id='filter-by-location']")
    public WebElement locationDropdown;

    @FindBy(xpath = "//ul[@id='select2-filter-by-location-results']/li")
    public List<WebElement> filterByLocations;

    @FindBy(xpath = "//span[@id='select2-filter-by-location-container']")
    public WebElement filterByLocation;

    @FindBy(xpath = "//ul[@id='select2-filter-by-department-results']/li")
    public List<WebElement> filterByDepartments;

    @FindBy(xpath = "//span[@id='select2-filter-by-department-container']")
    public WebElement filterByDepartment;

    @FindBy(xpath = "//select[@id='filter-by-department']")
    public WebElement departmentDropdown;

    @FindBy(xpath = "//p[contains(@class,'position-title')]")
    public List<WebElement> positionTitles;

    @FindBy(xpath = "//span[contains(@class,'position-department')]")
    public List<WebElement> positionDepartments;

    @FindBy(xpath = "//div[contains(@class,'position-location')]")
    public List<WebElement> positionLocations;

    @FindBy(xpath = "//div[contains(@class,'position-list-item-wrapper')]")
    public List<WebElement> positionLists;

    @FindBy(xpath = "(//a[.='View Role'])[1]")
    public WebElement viewRoleBtn;

    public  By firstPositionDepartmentLocator=By.xpath("(//span[contains(@class,'position-department')])[1]");
    public  By positionListsLocator=By.xpath("//div[contains(@class,'position-list-item-wrapper')]");




}
