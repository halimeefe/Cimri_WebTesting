package Pages;

import Utilities.PageDriver;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.List;

public class Parent {

    WebDriverWait wait = new WebDriverWait(PageDriver.getDriver(), Duration.ofSeconds(20));

    public void sendKeysFunc(WebElement element, String text) {
        waitUntilVisible(element);
        scrollToElement(element);
        element.clear();
        element.sendKeys(text);
    }

    public void clickFunc(WebElement element) {
        waitUntilClickable(element);
        scrollToElement(element);
        element.click();
    }

    public void clickFunction(WebElement element) { // scroll  işlemi yapmadan clickleme
        waitUntilClickable(element);
        element.click();
    }

    public void verifyContainsTextFunction(WebElement element, String value) {
        wait.until(ExpectedConditions.textToBePresentInElement(element, value));
        Assert.assertTrue(element.getText().toLowerCase().contains(value.toLowerCase()));
        new Actions(PageDriver.getDriver()).sendKeys(Keys.ESCAPE).perform();
    }


    public void scrollToElement(WebElement element) {

        JavascriptExecutor js = (JavascriptExecutor) PageDriver.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", element);

    }

    public void waitUntilVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitUntilClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public String findFromExcel(String text) { // Excel dosyasından login dataları döndürmesi için hazırlanan fonksiyon

        String returnData = "";

        String path = "src/main/resources/CimriLoginData_Excel.xlsx";

        Sheet sheet = null;

        try {
            FileInputStream inputStream = new FileInputStream(path);  // Dosyayı try-with-resources ile aç
            Workbook workbook = WorkbookFactory.create(inputStream);  // workbook'u aldım
            sheet = workbook.getSheetAt(0);  // sheet'e ulaştım


            for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {

                if (sheet.getRow(i).getCell(0).toString().equalsIgnoreCase(text)) {
                    for (int j = 1; j < sheet.getRow(i).getPhysicalNumberOfCells(); j++) {
                        returnData += sheet.getRow(i).getCell(j);

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // Hata İzleme
        }
        if (returnData.isEmpty()) {
            return "Veri bulunamadı"; // Veri yoksa boş dize yerine bu mesajı döndür
        }

        return returnData;
    }


}







