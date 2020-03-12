import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class fieldChanged implements ExpectedCondition<Boolean>
{
    String percent;
    String xPath;

    public fieldChanged(String xPath, String currentPercent)
    {
        this.xPath = xPath;
        percent = currentPercent;
    }

    @Override
    public Boolean apply(WebDriver driver)
    {
        Boolean didPercentChange = driver.findElement(By.xpath(xPath)).getText().equals(percent);
        return !didPercentChange;
    }
}
