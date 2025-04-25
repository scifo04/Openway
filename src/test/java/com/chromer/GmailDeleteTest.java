package com.chromer;

import org.testng.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GmailDeleteTest {

    WebDriver driver;
    
    @BeforeClass
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:/Program Files (x86)/Google/Chrome/Application/chrome.exe");

        options.addArguments("user-data-dir=C:/Users/Marvin Scifo/AppData/Local/Google/Chrome/User Data");
        options.addArguments("profile-directory=Profile 1");

        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        options.addArguments("--disable-web-security");
        options.addArguments("--disable-site-isolation-trials");
        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        System.setProperty("webdriver.chrome.driver", "D:/chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void deleteEmailFromGmail() throws InterruptedException {
        driver.get("https://mail.google.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement gunakanAkunLain = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Gunakan akun lain')]")));
            if (gunakanAkunLain.isDisplayed()) {
                System.out.println("Found 'Use another account' button, clicking it...");
                gunakanAkunLain.click();
            }
        } catch (Exception e) {
            System.out.println("No 'Use another account' button found.");
        }

        try {
            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("identifierId")));
            if (usernameField.isDisplayed()) {
                usernameField.sendKeys("fluffyfluff12345678@gmail.com");
                driver.findElement(By.id("identifierNext")).click();
                WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
                passwordField.sendKeys("PamPamPam123");
                driver.findElement(By.id("passwordNext")).click();
            }
        } catch (Exception e) {
            System.out.println("No 'Identifier Id' found");
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tr.zA")));

        WebElement firstCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("tr.zA:nth-child(1) td.oZ-x3 div[role='checkbox']")));
        firstCheckbox.click();

        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[aria-label='Delete']")));
        deleteButton.click();

        Thread.sleep(2000);
        System.out.println("Successfully deleted email");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}