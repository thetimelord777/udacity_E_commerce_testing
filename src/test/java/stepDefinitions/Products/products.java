package stepDefinitions.Products;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.checkerframework.checker.units.qual.C;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import stepDefinitions.UserRegLog.userRegLog;
import webPages.*;

/*
    Testing using Assertion using Testng,selenium  with Cucumber as testing framework.
 */
public class products {
    WebDriver driver = null;
    SearchPage searchPage = null;
    NavBars navBars = null;
    CategoriesPage categoriesPage = null;
    ProductItemPage productItemPage = null;

    @Before
    public void userOpensBrowser(){
        //sets the chrome driver to the exe driver downloaded from the chromium project.
        System.setProperty("webdriver.chrome.driver",
                System.getProperty("user.dir")+"\\resources\\chromedriver.exe");

        //create a new driver to control the browser from the selenium package.
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        searchPage = new SearchPage(driver);
        navBars = new NavBars(driver);
        categoriesPage = new CategoriesPage(driver);
        productItemPage = new ProductItemPage(driver);
    }

    @After
    public void quitTest(){
        driver.quit();
    }

    @Given("Logged User {string} {string} navigates to search page")
    public void loggedUserNavigatesToSearchPage(String email, String password) {

        driver.navigate().to("https://demo.nopcommerce.com/login");
        userRegLog.userEntersValidEmailAndPassword(email,password,driver);
        driver.navigate().to("https://demo.nopcommerce.com/search");
    }

    @When("user searches for {string} in search and enters")
    public void userSearchesForInSearchAndEnters(String searchWord) {
        searchPage.searchInPOM().sendKeys(searchWord);
        searchPage.searchInPOM().sendKeys(Keys.ENTER);
    }

    @Then("check for any search results")
    public void checkForAnySearchResults() throws InterruptedException {
        Assert.assertTrue("No reults recieved",searchPage.searchResultsPOM(0)!=null);
    }

    @Given("logged User {string} {string} navigates to home page")
    public void loggedUserNavigatesToHomePage(String email, String password) {

        driver.navigate().to("https://demo.nopcommerce.com/login");
        userRegLog.userEntersValidEmailAndPassword(email,password,driver);
        driver.navigate().to("https://demo.nopcommerce.com");

    }

    @When("user selects currency {string}")
    public void userSelectsCurrency(String currency) {
        navBars.currencySelectPOM().selectByVisibleText(currency);
    }

    @When("user selects currency {int}")
    public void userSelectsCurrency(int index) {
        navBars.currencySelectPOM().selectByIndex(index);
    }
    @Then("check for any item's currency {string}")
    public void checkForAnyItemSCurrency(String currencySign) {
        Assert.assertTrue("the chosen currency not the same as shown currecnt",searchPage.getFirstVisibleProductPriceTag().charAt(0) == currencySign.charAt(0));
    }

    @When("user hovers on category {string}")
    public void userHoversOnCategory(String category) {
        Actions action = new Actions(driver);
        action.moveToElement(navBars.searchCategoriesPOM(category)).perform();
    }

    @And("clicks on subcategory {string}")
    public void userClicksOnSubcategory(String subCategory) {
        navBars.searchSubCategoriesPOM(subCategory).click();
    }

    @When("user clicks on category {string}")
    public void userClicksOnCategory(String category) {
        navBars.searchCategoriesPOM(category).click();
    }

    @Then("check for current category title {string}")
    public void checkForCategoryItem(String categoryTitle) {
        Assert.assertTrue("the selected category is not the shown category",
                categoriesPage.categoryNamePOM().getText().toLowerCase().trim().contains(categoryTitle.toLowerCase().trim()));
    }

    @Given("logged User {string} {string} navigates to shoes page")
    public void loggedUserNavigatesToShoesPage(String email, String password) {
        driver.navigate().to("https://demo.nopcommerce.com/login");
        userRegLog.userEntersValidEmailAndPassword(email,password,driver);
        driver.navigate().to("https://demo.nopcommerce.com");
        userHoversOnCategory("Apparel");
        userClicksOnSubcategory("shoes");
    }

    @When("user clicks on color selector {string} radio buttons")
    public void userClicksOnColorSelectorRadioButtons(String color) {
        categoriesPage.searchShoeColorsListItem(color).click();
    }

    @And("user clicks on first item search option")
    public void userClicksOnFirstItemSearchOption() throws InterruptedException {
        Thread.sleep(2000);
        searchPage.searchResultsPOM(0).click();
    }

    @Then("check for available color {string}")
    public void checkForAvailableColor(String color) {
        Assert.assertTrue("Color is not in the selected element",productItemPage.checkForProductColorRadio(color));
    }

    @When("user clicks on tag {string}")
    public void userClicksOnTag(String tag) {
        searchPage.searchPopularTagsPOM(tag).click();
    }

    @Then("check for item tags {string}")
    public void checkForItemTags(String tag) {
        Assert.assertTrue("selected item don't have the selected tag",
                productItemPage.checkForProductTag(tag));
    }

}
