package com.insider.CaseStudy.UI.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Driver {

    static ChromeOptions chromeOptions = new ChromeOptions();
    static FirefoxOptions firefoxOptions = new FirefoxOptions();

    /*
    Creating a private constructor, so we are closing
    access to the object of this class from outside the class
     */
    private Driver() {

    }

    /*
    We make web driver variable private, because we want to close access
    from outside the class. We make it static because we will
    use it in a static method.
     */
    //private static WebDriver driver;
    private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();

    /*
    Create a re-usable utility method which will return same driver
    instance when we call it
     */
    public static WebDriver getDriver() {


        if (driverPool.get() == null) {
            /*
            We read our browserType from configuration.properties.
            This way, we can control which browser is opened from outside
            our code, from configuration.properties.
             */
            String browserType = ConfigurationReader.getProperty("browser");

            /*
            Depending on the browserType that will be return from configuration.properties file
            switch statement will determine the case, and open the matching browser
             */
            switch (browserType) {
                case "chrome":
                    Map<String, Object> prefs = new HashMap<>();
                    chromeOptions.setExperimentalOption("prefs", prefs);
                    //chromeOptions.addArguments("--remote-allow-origins=*");
                    //chromeOptions.addArguments("incognito");
                    chromeOptions.addArguments("--disable-extensions");
                    chromeOptions.addArguments("--disable-infobars");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--disable-web-security");
                    chromeOptions.addArguments("--dns-prefetch-disable");
                    chromeOptions.addArguments("--disable-browser-side-navigation");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--always-authorize-plugins");
                    chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                    driverPool.set(new ChromeDriver(chromeOptions));
                    driverPool.get().manage().window().maximize();
                    driverPool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
                    break;
                case "headless-chrome":
                    chromeOptions.addArguments("--no-sandbox"); // Bypass OS security model
                    chromeOptions.addArguments("--disable-extensions"); // disabling extensions
                    chromeOptions.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("incognito");
                    chromeOptions.addArguments("window-size=1920,1080");
                    driverPool.set(new ChromeDriver(chromeOptions));
                    driverPool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
                    break;

                case "firefox":
                    firefoxOptions.setAcceptInsecureCerts(true);
                    firefoxOptions.addPreference("network.http.phishy-userpass-length", 255);
                    firefoxOptions.addPreference("network.automatic-ntlm-auth.allow-non-fqdn", true);
                    firefoxOptions.addPreference("network.negotiate-auth.allow-non-fqdn", true);
                    firefoxOptions.addPreference("browser.tabs.remote.autostart", false);
                    firefoxOptions.addPreference("browser.tabs.remote.autostart.1", false);
                    firefoxOptions.addPreference("browser.tabs.remote.autostart.2", false);
                    firefoxOptions.addPreference("browser.tabs.remote.force-enable", false);
                    firefoxOptions.addPreference("browser.download.folderList", 2);
                    firefoxOptions.addPreference("browser.helperApps.alwaysAsk.force", false);
                    firefoxOptions.addPreference("browser.download.manager.showWhenStarting", false);
                    firefoxOptions.addPreference("browser.download.useDownloadDir", true);
                    firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    firefoxOptions.addPreference("browser.download.manager.alertOnEXEOpen", false);
                    firefoxOptions.addPreference("browser.download.manager.focusWhenStarting", false);
                    firefoxOptions.addPreference("browser.download.manager.useWindow", false);
                    firefoxOptions.addPreference("browser.download.manager.showAlertOnComplete", false);
                    firefoxOptions.addPreference("browser.download.manager.closeWhenDone", true);
                    driverPool.set(new FirefoxDriver(firefoxOptions));
                    driverPool.get().manage().window().maximize();
                    driverPool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
                    break;
                case "remote-chrome":
                    try {
                        String gridAddress = "jenkins_selenium-chrome-server_1";
                        URL url = new URL("http://" + gridAddress + ":4444/wd/hub");
                        driverPool.set(new RemoteWebDriver(url, chromeOptions));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
        return driverPool.get();
    }

    //this method will make sure our driver value is always null after using quit() method
    public static void closeDriver() {
        if (driverPool.get() != null) {
            driverPool.get().quit();//this line will terminate the existing session. value will not even be null
            driverPool.remove();
        }
    }







}
