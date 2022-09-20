package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PlayersTable {
    WebDriver driver;

    @FindBy(css = ".panel-heading")
    public WebElement playerManagement;

    @FindBy(xpath = "//a[text()='Username']")
    public WebElement orderByUsernameBtn;

    @FindBy(css = ".asc")
    public WebElement sortedBtn;

    @FindBy(xpath = "//*[@id='payment-system-transaction-grid']/table/tbody/tr[*]/td[2]")
    public List<WebElement> usernameColumn;

    public PlayersTable(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public boolean isLoaded() {
        return playerManagement.isDisplayed();
    }

    public void sortByRegDate() {
        orderByUsernameBtn.click();
    }

    public void waitForSorted() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(sortedBtn));
    }


}