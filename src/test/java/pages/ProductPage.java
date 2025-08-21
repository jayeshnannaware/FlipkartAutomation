package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {
    WebDriver driver;

    // Dynamic XPath for Add to Cart button
    By addToCartButton = By.xpath("//button[contains(text(),'Add') or contains(text(),'ADD')]");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public void addToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            // Handle popup if present
            try {
                WebElement popupClose = driver.findElement(By.xpath("//button[contains(text(),'✕')]"));
                if (popupClose.isDisplayed()) popupClose.click();
            } catch (Exception e) { /* popup not present */ }

            // Wait until button is clickable and handle stale element
            WebElement addBtn = wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.elementToBeClickable(addToCartButton)));

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addBtn);

            // Click the button
            addBtn.click();

        } catch (Exception e) {
            System.out.println("Failed to click Add to Cart: " + e.getMessage());
        }
    }
}
