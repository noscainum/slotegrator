package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



public class AdminDashboard {

    public WebDriver driver;

    @FindBy(id = "header-logo")
    public WebElement logo;

    @FindBy(css = ".fa-users")
    public WebElement playersButton;


    public AdminDashboard(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickPlayers() {
        playersButton.click();
    }

    public boolean isLoaded(){
        return logo.isDisplayed();
    }
}