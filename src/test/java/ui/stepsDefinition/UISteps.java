package ui.stepsDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import ui.pages.AdminDashboard;
import ui.pages.LoginPage;
import ui.pages.PlayersTable;
import util.ConfProperties;

import java.sql.SQLOutput;
import java.time.Duration;
import java.util.*;
import java.util.stream.Stream;

public class UISteps {

    WebDriver driver;

    @Given("The browser is opened")
    public void the_browser_is_opened() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();

    }

    @When("Login page opening")
    public void login_page_opening() {
        driver.get(ConfProperties.getProperty("loginpage"));
        LoginPage loginPage = new LoginPage(driver);

    }

    @When("Admin page authorization")
    public void admin_page_authorization() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.inputLogin(ConfProperties.getProperty("adminlogin"));
        loginPage.inputPassword(ConfProperties.getProperty("adminpass"));
        loginPage.clickSignInButton();
    }

    @Then("User successfully logged in")
    public void assert_authorized() {
        AdminDashboard adminDashboard = new AdminDashboard(driver);
        Assert.assertTrue(adminDashboard.isLoaded());
    }

    @When("Players table loading")
    public void players_table_loading() {
        AdminDashboard adminDashboard = new AdminDashboard(driver);
        adminDashboard.clickPlayers();
    }

    @Then("Players table loaded")
    public void assert_table_loaded() {
        PlayersTable playersTable = new PlayersTable(driver);
        Assert.assertTrue(playersTable.isLoaded());
    }

    @When("Table sorting")
    public void table_sorting() {
        PlayersTable playersTable = new PlayersTable(driver);
        playersTable.sortByRegDate();
        playersTable.waitForSorted();
    }

    @Then("Table is sorted correctly")
    public void table_is_sorted_correctly() {
        PlayersTable playersAfterSort = new PlayersTable(driver);
        Boolean isSorted = true;
        String previous = "";
        for (WebElement current : playersAfterSort.usernameColumn) {
            System.out.println(current.getText() + " comparing to " + previous);
            if (current.getText().compareTo(previous) < 0)
                isSorted = false;
            previous = current.getText();
        }
        Assert.assertTrue(isSorted);

    }

    @Then("Browser closing")
    public void browser_closing() {
        driver.quit();
    }
}
