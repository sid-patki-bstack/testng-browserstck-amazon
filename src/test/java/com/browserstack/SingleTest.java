package com.browserstack;

import org.openqa.selenium.*;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class SingleTest extends BrowserStackTestNGTest {

    @Test
    public void test() throws Exception {
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        driver.get("https://www.amazon.com");
        //Mazimize current window
        driver.manage().window().maximize();

        //Delay execution for 5 seconds to view the maximize operation
        Thread.sleep(5000);

        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("iPhone X" + Keys.ENTER);
        WebElement iosCheckBox = wait.until(presenceOfElementLocated(By.xpath("//span[contains(text(),'iOS')]")));
        iosCheckBox.click();


//        driver.findElement(By.xpath("//span[contains(text(),'Sort by:')]")).click();
        driver.findElement(By.cssSelector("div>form>span>span")).click();
        wait.until(presenceOfElementLocated((By.id("s-result-sort-select_2")))).click();

        List<WebElement> mobileElements = driver.findElements(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[1]/div[2]/div[1]/span[3]/div[2]/*/div[1]/span[1]/div[1]/div[1]/div[2]/div[2]/div[1]"));
        for (WebElement webElement : mobileElements) {
            String link = webElement.findElement(By.className("a-link-normal")).getAttribute("href");
            List<WebElement> priceList = webElement.findElements(By.cssSelector("span[class='a-offscreen']"));
            String price = (priceList.size() > 0 ? priceList.get(0).getAttribute("innerHTML") : "Not available");
            String name = webElement.findElement(By.cssSelector("span.a-size-medium.a-color-base.a-text-normal")).getText();

            System.out.println(name + " " + price + " " + link);

        }
        try {
            wait.until(ExpectedConditions.titleContains("Amazon.com : iPhone X"));
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"" + "passed" + "\", \"reason\": \"" + "Yaay title contains 'Amazon.com : iPhone X" + "\"}}");
        } catch (Exception e) {
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"" + "failed" + "\", \"reason\": \"" + "Naay title does not contain 'Amazon.com : iPhone X" + "\"}}");
        }
        Assert.assertEquals("Amazon.com : iPhone X", driver.getTitle());
    }
}
