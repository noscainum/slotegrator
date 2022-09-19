package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LoginPage {

    public WebDriver driver;

    @FindBy(id = "UserLogin_username")
    public WebElement loginForm;

    @FindBy(id = "UserLogin_password")
    public WebElement passwordForm;

    @FindBy(name = "yt0")
    public WebElement signInButton;


    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void inputLogin (String login){
        loginForm.sendKeys(login);
    }

    public void inputPassword (String password){
        passwordForm.sendKeys(password);
    }

    public void clickSignInButton () {
        signInButton.click();
    }


}
