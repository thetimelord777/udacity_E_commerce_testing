package webPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductItemPage {
    WebDriver driver;

    public ProductItemPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = "div.add-to-cart button")
    WebElement addToCartButton;

    @FindBy(css = "div.overview-buttons .add-to-wishlist-button")
    WebElement addToWishListButton;

    @FindBy(css = "div.overview-buttons .add-to-compare-list-button")
    WebElement addToCompareButton;

    public WebElement addToCartButtonPOM(){
        return addToCartButton;
    }

    public WebElement addToWishListButtonPOM(){
        return addToWishListButton;
    }
    public WebElement addToCompareButtonPOM(){
        return addToCompareButton;
    }

    public List<WebElement> productColourOptions(){
        return driver.findElements(By.cssSelector("#color-squares-10 > li span"));
    }

    public boolean checkForProductColorRadio(String color){
        for(WebElement itemColor : productColourOptions()){
            if(itemColor.getAttribute("title").toLowerCase().trim().contains(color.toLowerCase().trim())){
                return true;
            }
        }
        return false;
    }

    public List<WebElement> productTags(){
        return driver.findElements(By.cssSelector(".product-tags-list > ul > li.tag > .producttag"));
    }

    public boolean checkForProductTag(String tag){
        for(WebElement productTag: productTags()){
            if(productTag.getText().toLowerCase().trim().contains(tag.toLowerCase().trim())){
                return true;
            }
        }

        return false;
    }

}
