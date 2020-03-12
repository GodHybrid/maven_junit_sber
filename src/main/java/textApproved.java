import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class textApproved implements ExpectedCondition<Boolean>
{
    String expectedText;
    String xPath;
    Integer count = 0;

    public textApproved(String expectedText, String xPath)
    {
        this.expectedText = expectedText;
        this.xPath = xPath;
    }

    @Override
    public Boolean apply(WebDriver driver)
    {
        Boolean isTextFieldCorrect = driver.findElement(By.xpath(xPath)).getAttribute("value").equals(expectedText);
        if(!isTextFieldCorrect)
        {
            driver.findElement(By.xpath(xPath)).clear();
            driver.findElement(By.xpath(xPath)).sendKeys(expectedText);
        }
        return isTextFieldCorrect;
    }
}
