import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class SberIpoteka_Test
{
    Actions bobDoSomething;
    WebDriver selectorDriver;
    WebDriverWait waitForLoad;

    String creditSum, monthlyPay, necInc, percent = "";

    @Before
    public void preparation()
    {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

        selectorDriver = new ChromeDriver();
        selectorDriver.manage().window().maximize();
        selectorDriver.get("https://sberbank.ru/");
        selectorDriver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

        waitForLoad = new WebDriverWait(selectorDriver, 20);

        bobDoSomething = new Actions(selectorDriver);
    }

    @Test
    public void execute() throws InterruptedException {
        String mainMenuXPath = "//span[@class='lg-menu__text' and contains(text(), 'Ипотека')]";
        String subMenuAreaXPath = "//div[@id='submenu-1']";
        WebElement mainMenuElement = selectorDriver.findElement(By.xpath(mainMenuXPath));
        WebElement subMenuAreaElement = selectorDriver.findElement(By.xpath(subMenuAreaXPath));

        Actions hover = bobDoSomething.moveToElement(mainMenuElement);
        hover.perform();//lg-menu__sub-link

        waitForLoad.until(ExpectedConditions.visibilityOf(subMenuAreaElement));

        String linkToMortgageXPath = "//a[@class='lg-menu__sub-link' and contains(text(), 'готовое жильё')]";
        WebElement linkToMortgageElement = selectorDriver.findElement(By.xpath(linkToMortgageXPath));
        hover = bobDoSomething.moveToElement(linkToMortgageElement);
        hover.perform();
        clickOn(linkToMortgageElement);

        String mortgageSettingsAreaXPath = "//div[contains(@data-pid, 'Iframe')]/parent::div";
        waitForLoad.until(ExpectedConditions.presenceOfElementLocated(By.xpath(mortgageSettingsAreaXPath)));
        WebElement mortgageSettingsElement = selectorDriver.findElement(By.xpath(mortgageSettingsAreaXPath));
        JavascriptExecutor je = (JavascriptExecutor) selectorDriver;
        je.executeScript("arguments[0].scrollIntoView();", mortgageSettingsElement);
        waitForLoad.until(ExpectedConditions.visibilityOf(mortgageSettingsElement));
        clickOn(mortgageSettingsElement);
        selectorDriver.switchTo().frame(0);

        String estateCostXPath = "//input[@id='estateCost']";
        waitForLoad.until(ExpectedConditions.elementToBeClickable(By.xpath(estateCostXPath)));
        WebElement estateCostElement = selectorDriver.findElement(By.xpath(estateCostXPath));
        clickOn(estateCostElement);
        estateCostElement.sendKeys(Keys.CONTROL + "a");
        estateCostElement.sendKeys(Keys.DELETE);
        estateCostElement.sendKeys("5180000");
        checkSendKeys(estateCostXPath, "5 180 000 ₽");

        justwait();

        String feeXPath = "//input[@id='initialFee']";
        WebElement feeElement = selectorDriver.findElement(By.xpath(feeXPath));
        clickOn(feeElement);
        feeElement.clear();
        feeElement.sendKeys("3058000");
        checkSendKeys(feeXPath, "3 058 000 ₽");

        justwait();

        String termXPath = "//input[@id='creditTerm']";
        WebElement termElement = selectorDriver.findElement(By.xpath(termXPath));
        clickOn(termElement);
        termElement.sendKeys(Keys.CONTROL + "a");
        termElement.sendKeys(Keys.DELETE);
        termElement.sendKeys("30");

        justwait();

        String sberCardXPath = "//input[@data-test-id='paidToCard']/parent::label";
        WebElement sberCardElement = selectorDriver.findElement(By.xpath(sberCardXPath));
        waitForLoad.until(ExpectedConditions.elementToBeClickable(sberCardElement));
        sberCardElement.click();

        String checkIncomeSwitchXPath = "//div[contains(text(),'возможность подтвердить доход')]/parent::div/parent::div";
        waitForLoad.until(ExpectedConditions.presenceOfElementLocated(By.xpath(checkIncomeSwitchXPath)));

        String incomeSwitchXPath = "//input[@data-test-id='canConfirmIncome']/parent::label";
        WebElement incomeSwitchElement = selectorDriver.findElement(By.xpath(incomeSwitchXPath));
        clickOn(incomeSwitchElement);

        String youngFamilyXPath = "//input[@data-test-id='youngFamilyDiscount']/parent::label";
        WebElement youngFamilySwitchElement = selectorDriver.findElement(By.xpath(youngFamilyXPath));
        waitForLoad.until(ExpectedConditions.visibilityOf(youngFamilySwitchElement));
        clickOn(youngFamilySwitchElement);
        //waitForLoad.until(ExpectedConditions.presenceOfElementLocated(By.xpath(checkIncomeSwitchXPath + "[contains(@class, 'dcCalc_switch_checked')]")));

        justwait();

        checkData();

        justwait();

        clickOn(incomeSwitchElement);

        justwait();

        checkData();
        testIfTrueData();
    }

    private void checkSendKeys(String estateCostElement, String s) throws InterruptedException
    {
        if(!selectorDriver.findElement(By.xpath(estateCostElement)).getAttribute("value").equals(s))
        {
            System.out.println(selectorDriver.findElement(By.xpath(estateCostElement)).getAttribute("value"));
            Thread.sleep(50);
            checkSendKeys(estateCostElement, s);
        }
        return;
    }

    private void checkData()
    {
        creditSum = selectorDriver.findElement(By.xpath("//span[@data-test-id='amountOfCredit']")).getText();
        monthlyPay = selectorDriver.findElement(By.xpath("//span[@data-test-id='monthlyPayment']")).getText();
        necInc = selectorDriver.findElement(By.xpath("//span[@data-test-id='requiredIncome']")).getText();
        percent = selectorDriver.findElement(By.xpath("//span[@data-test-id='rate']")).getText();
    }

    private void testIfTrueData()
    {
        Assert.assertEquals("2 122 000 ₽", creditSum);
        Assert.assertEquals("17 535 ₽", monthlyPay);
        Assert.assertEquals("29 224 ₽", necInc);
        Assert.assertEquals("9,4 %", percent);

    }

    private void justwait() throws InterruptedException
    {
        Thread.sleep(500);
        return;
    }

    private void clickOn(WebElement me)
    {
        waitForLoad.until(ExpectedConditions.visibilityOf(me));
        me.click();
    }

    @After
    public void finisher()
    {
        selectorDriver.quit();
    }
}
