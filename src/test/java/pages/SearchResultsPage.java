package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchResultsPage {
    WebDriver driver;

    // All product links in search results
    @FindBy(xpath = "//a[contains(@class,'_1fQZEK') or contains(@class,'s1Q9rs')]")
    List<WebElement> allProducts;

    // First product specifically
    @FindBy(xpath = "//a[contains(@href,'/p/')]")
    WebElement firstProduct;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Click first product
    public void clickFirstProduct() {
        firstProduct.click();
    }

    // Click product by index (0-based index)
    public void clickProductByIndex(int index) {
        if (index >= 0 && index < allProducts.size()) {
            allProducts.get(index).click();
        } else {
            System.out.println("Invalid product index: " + index);
        }
    }

    // Get total number of products in search results
    public int getTotalProductsCount() {
        return allProducts.size();
    }

    // Print all product names in search results
    public void printAllProductNames() {
        System.out.println("Products found:");
        for (WebElement product : allProducts) {
            System.out.println(product.getText());
        }
    }
}
