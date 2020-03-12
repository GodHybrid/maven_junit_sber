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
        hover.perform();

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
        waitForLoad.until(ExpectedConditions.visibilityOf(selectorDriver.findElement(By.xpath(estateCostXPath))));
        waitForLoad.until(ExpectedConditions.elementToBeClickable(By.xpath(estateCostXPath)));
        WebElement estateCostElement = selectorDriver.findElement(By.xpath(estateCostXPath));
        waitForLoad.until(ExpectedConditions.elementToBeClickable(By.xpath(estateCostXPath)));
        clickOn(estateCostElement);
        while(!estateCostElement.getAttribute("value").isEmpty()) estateCostElement.clear();
        //estateCostElement.sendKeys(Keys.CONTROL + "a");
        //estateCostElement.sendKeys(Keys.DELETE);
        estateCostElement.sendKeys("5180000");
        while(!checkSendKeys(estateCostXPath, "5 180 000 ₽"))
        {
            Thread.sleep(50);
        }

        String feeXPath = "//input[@id='initialFee']";
        waitForLoad.until(ExpectedConditions.visibilityOf(selectorDriver.findElement(By.xpath(feeXPath))));
        waitForLoad.until(ExpectedConditions.elementToBeClickable(By.xpath(feeXPath)));
        WebElement feeElement = selectorDriver.findElement(By.xpath(feeXPath));
        waitForLoad.until(ExpectedConditions.elementToBeClickable(By.xpath(feeXPath)));
        clickOn(feeElement);
        while(!feeElement.getAttribute("value").isEmpty()) feeElement.clear();
        feeElement.sendKeys("3058000");
        while(!checkSendKeys(feeXPath, "3 058 000 ₽"))
        {
            Thread.sleep(50);
        }

        String termXPath = "//input[@id='creditTerm']";
        waitForLoad.until(ExpectedConditions.visibilityOf(selectorDriver.findElement(By.xpath(termXPath))));
        waitForLoad.until(ExpectedConditions.elementToBeClickable(By.xpath(termXPath)));
        WebElement termElement = selectorDriver.findElement(By.xpath(termXPath));
        waitForLoad.until(ExpectedConditions.elementToBeClickable(By.xpath(termXPath)));
        clickOn(termElement);
        termElement.sendKeys(Keys.CONTROL + "a");
        termElement.sendKeys(Keys.DELETE);
        termElement.sendKeys("30");
        while(!checkSendKeys(termXPath, "30 лет"))
        {
            Thread.sleep(50);
        }

        controlCheck(estateCostXPath, "5 180 000 ₽");
        controlCheck(feeXPath, "3 058 000 ₽");
        controlCheck(termXPath, "30 лет");

        String sberCardXPath = "//input[@data-test-id='paidToCard']/parent::label";
        waitForLoad.until(ExpectedConditions.visibilityOf(selectorDriver.findElement(By.xpath(sberCardXPath))));
        WebElement sberCardElement = selectorDriver.findElement(By.xpath(sberCardXPath));
        switchSwitch(sberCardElement);

        String checkIncomeSwitchXPath = "//div[contains(text(),'возможность подтвердить доход')]/parent::div/parent::div";
        waitForLoad.until(ExpectedConditions.presenceOfElementLocated(By.xpath(checkIncomeSwitchXPath)));

        String incomeSwitchXPath = "//input[@data-test-id='canConfirmIncome']/parent::label";
        waitForLoad.until(ExpectedConditions.visibilityOf(selectorDriver.findElement(By.xpath(incomeSwitchXPath))));
        WebElement incomeSwitchElement = selectorDriver.findElement(By.xpath(incomeSwitchXPath));
        waitForLoad.until(ExpectedConditions.elementToBeClickable(incomeSwitchElement));
        clickOn(incomeSwitchElement);

        String youngFamilyXPath = "//input[@data-test-id='youngFamilyDiscount']/parent::label";
        waitForLoad.until(ExpectedConditions.visibilityOf(selectorDriver.findElement(By.xpath(youngFamilyXPath))));
        WebElement youngFamilySwitchElement = selectorDriver.findElement(By.xpath(youngFamilyXPath));
        waitForLoad.until(ExpectedConditions.elementToBeClickable(youngFamilySwitchElement));
        clickOn(youngFamilySwitchElement);

        controlCheck(estateCostXPath, "5 180 000 ₽");
        controlCheck(feeXPath, "3 058 000 ₽");
        controlCheck(termXPath, "30 лет");

        checkData();

        justwait();

        clickOn(incomeSwitchElement);

        justwait();

        checkData();

        testIfTrueData();
    }

    private void switchSwitch(WebElement me)
    {
        me.click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void controlCheck(String XPath, String s)
    {
        if(!selectorDriver.findElement(By.xpath(XPath)).getAttribute("value").equals(s))
        {
            WebElement tmpElem = selectorDriver.findElement(By.xpath(XPath));
            tmpElem.sendKeys(Keys.CONTROL + "a");
            tmpElem.sendKeys(Keys.DELETE);
            tmpElem.sendKeys(s);
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            controlCheck(XPath, s);
        }
        return;
    }

    private boolean checkSendKeys(String estateCostElement, String s) throws InterruptedException
    {
        if(!selectorDriver.findElement(By.xpath(estateCostElement)).getAttribute("value").equals(s))
        {
            //System.out.println(selectorDriver.findElement(By.xpath(estateCostElement)).getAttribute("value"));
            //Thread.sleep(50);
            //checkSendKeys(estateCostElement, s);
            return false;
        }
        return true;
    }

    private void checkData()
    {
        waitForLoad.until(ExpectedConditions.visibilityOf(selectorDriver.findElement(By.xpath("//input[@data-test-id='youngFamilyDiscount']/parent::label[@class='dcCalc_switch dcCalc_switch_size_medium dcCalc_switch_checked']"))));
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
        waitForLoad.until(ExpectedConditions.elementToBeClickable(me));
        me.click();
    }

    @After
    public void finisher()
    {
        selectorDriver.quit();
    }
}
