package tests;

import base.BaseClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.annotations.*;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchResultsPage;
import utils.ExcelUtils;
import utils.ExtentManager;

public class FlipkartTest extends BaseClass {

    public static ExtentReports extent;
    public static ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        // ExtentReports initialize karo
        extent = ExtentManager.getReporter();
    }

    @DataProvider(name = "productData")
    public Object[][] getProducts() {
        String path = System.getProperty("user.dir") + "/testdata/products.xlsx";
        return ExcelUtils.getExcelData(path, "Sheet1");
    }

    @Test(dataProvider = "productData")
    public void addProductsToCart(String productName) throws InterruptedException {
        // Test create karo report me
        test = extent.createTest("Add Product: " + productName);

        try {
            HomePage home = new HomePage(driver);
            SearchResultsPage searchPage = new SearchResultsPage(driver);
            ProductPage productPage = new ProductPage(driver);

            test.log(Status.INFO, "Navigating to Flipkart home page");
            driver.get("https://www.flipkart.com/");

            home.closeLoginPopupIfPresent();
            test.log(Status.INFO, "Login popup closed if present");

            home.searchProduct(productName);
            test.log(Status.INFO, "Searched for product: " + productName);

            searchPage.clickFirstProduct();
            test.log(Status.INFO, "Clicked first product in search results");

            productPage.addToCart();
            test.log(Status.PASS, "Product added to cart successfully");

            // return to home page for next iteration
            driver.navigate().to("https://www.flipkart.com/");
            Thread.sleep(500);

        } catch (Exception e) {
            test.log(Status.FAIL, "Test failed due to: " + e.getMessage());
        }
    }

    @AfterSuite
    public void tearDownReport() {
        // Report finalize karo
        if (extent != null) {
            extent.flush();
        }
    }
}
