package tests;

import base.BaseClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchResultsPage;
import utils.ExcelUtils;

public class FlipkartTest extends BaseClass {

    @DataProvider(name = "productData")
    public Object[][] getProducts() {
        String path = System.getProperty("user.dir") + "/testdata/products.xlsx";
        return ExcelUtils.getExcelData(path, "Sheet1");
    }

    @Test(dataProvider = "productData")
    public void addProductsToCart(String productName) throws InterruptedException {
        HomePage home = new HomePage(driver);
        SearchResultsPage searchPage = new SearchResultsPage(driver);
        ProductPage productPage = new ProductPage(driver);

        home.closeLoginPopupIfPresent();
        home.searchProduct(productName);

        // click first product and add to cart
        searchPage.clickFirstProduct();
      //  productPage.switchToProductTab();
        productPage.addToCart();

        // return to home page for next iteration
        driver.navigate().to("https://www.flipkart.com/");
        Thread.sleep(2000);
    }
}