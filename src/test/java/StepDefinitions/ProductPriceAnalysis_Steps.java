package StepDefinitions;

import Pages.DialogContent;
import Utilities.PageDriver;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class ProductPriceAnalysis_Steps {

    DialogContent dc = new DialogContent();
    WebDriverWait wait = new WebDriverWait(PageDriver.getDriver(), Duration.ofSeconds(20));

    @Given("I navigate to the website")
    public void ıNavigateToTheWebsite() {


        PageDriver.getDriver().get("https://www.cimri.com/");

        wait.until(ExpectedConditions.urlToBe("https://www.cimri.com/"));
        PageDriver.getDriver().manage().window().maximize();


    }

    @When("I verify website Url")
    public void ıVerifyWebsiteUrl() {
        String expectedUrl = "https://www.cimri.com/";
        String actualUrl = PageDriver.getDriver().getCurrentUrl();
        System.out.println(actualUrl);
        Assert.assertEquals(expectedUrl, actualUrl, "URL doğrulama başarısız oldu");


    }

    @And("I click on the login modüle")
    public void ıClickOnTheLoginModüle() {

        Actions actions = new Actions(PageDriver.getDriver());
        actions.moveToElement(dc.loginFirst).build().perform();
        dc.clickFunc(dc.loginSecond);



    }


    @And("I enter my email and password")
    public void ıEnterMyEmailAndPassword() {
        dc.sendKeysFunc(dc.inputMail, dc.findFromExcel("username"));
        dc.sendKeysFunc(dc.inputPassword, dc.findFromExcel("password"));

    }

    @And("I click on the login button")
    public void ıClickOnTheLoginButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[@class='sc-dlnjwi biYKOn'])[1]")));
        dc.clickFunc(dc.rememberMe);
        dc.clickFunc(dc.loginBtn);

    }


    @And("I select a random category")
    public void ıSelectARandomCategory() throws InterruptedException {


            Actions actions = new Actions(PageDriver.getDriver());
            actions.moveToElement(dc.categories).build().perform();


            // Kategorileri bul
            By categoryLocator = By.cssSelector("[class='menuCategoryListItemContainer']>div");
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(categoryLocator));  // Dinamik elementler için Bekleme süresi
            List<WebElement> categoryList = PageDriver.getDriver().findElements(categoryLocator); // categoryleri liste attım

            int categoryCount = categoryList.size();

            if (categoryCount > 0) {
                // Rastgele bir indeks seç
                Random random = new Random();
                int randomIndex = random.nextInt(categoryCount - 1);//-1, son ürün hariç diğerleri arasında random ürün seçer


                // Rastgele seçilen kategoriyi tıkla
                WebElement randomCategory = categoryList.get(randomIndex);

                randomCategory.click();
            } else {
                System.out.println("Kategoriler bulunamadı.");
            }

        }


    @And("I click on the fiyati düsenler")
    public void ıClickOnTheFiyatiDüsenler() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[@class='rightNavMenuItem link'])[2]")));
        dc.clickFunc(dc.fiyatiDusenler);


    }

    @And("I click on the first product")
    public void ıClickOnTheFirstProduct() {

        JavascriptExecutor js = (JavascriptExecutor) PageDriver.getDriver();
        js.executeScript("arguments[0].style.visibility='visible';", dc.firstProduct); // elemente tıklamadan locate edemediğim için önce görünür hale getirdim
        // sonra locaterını  yazıp action ile yönlendirdim.
        Actions actions = new Actions(PageDriver.getDriver());
        actions.click(dc.firstProduct).build().perform();

    }

    @And("I verify the highest price within the period")
    public void ıVerifyTheHighestPriceWithinThePeriod() {

        //scrollIntoView işlevinin block parametresi, elementi istediğim konumda görünür hale getirmek için kullandım
        // Bu parametre, elementin dikey (yatay eksende)istediğim şekilde görünür olmasını sağlar

        // PARAMETRELERİN ANLAMLARI:

        //block: 'start': Elementi önce görünür hale getirecek, sayfanın en üst kısmında görünecektir.

        //block: 'end': Element sayfanın en alt kısmında görünecektir.

        //block: 'center': Element sayfanın dikey ortasında görünecektir.


        ((JavascriptExecutor) PageDriver.getDriver()).executeScript
                ("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'end', inline: 'nearest' });", dc.highestPrice);
        System.out.println("Dönem içi en yüksek fiyat:\t" + dc.highestPrice.getText());

    }

    @And("I verify the lowest price within the period")
    public void ıVerifyTheLowestPriceWithinThePeriod() {
        ((JavascriptExecutor) PageDriver.getDriver()).executeScript
                ("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'end', inline: 'nearest' });", dc.lowestPrice);
        System.out.println("Dönem içi en düşük fiyat:\t" + dc.lowestPrice.getText());


    }

    @And("I verify the current lowest price")
    public void ıVerifyTheCurrentLowestPrice() {
        ((JavascriptExecutor) PageDriver.getDriver()).executeScript
                ("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'end', inline: 'nearest' });", dc.currentPrice);
        System.out.println("Şu an en ucuz fiyat:\t" + dc.currentPrice.getText());


    }


    @And("I export price to Excel file")
    public void ıExportPriceToExcelFile() {

        String highestPrice = PageDriver.getDriver().findElement(By.xpath("(//*[@class='s1fqyqkq-4 ckAXTq'])[1]/following-sibling::p")).getText();
        String lowestPrice = PageDriver.getDriver().findElement(By.xpath("(//*[@class='s1fqyqkq-4 ckAXTq'])[2]/following-sibling::p")).getText();
        String currentPrice = PageDriver.getDriver().findElement(By.xpath("(//*[@class='s1fqyqkq-10 bItbsS']/p)[1]")).getText();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Fiyat Grafiği");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Dönem İçi En Yüksek Fiyat");
        headerRow.createCell(1).setCellValue("Dönem İçi En Düşük Fiyat");
        headerRow.createCell(2).setCellValue("Şu An En Ucuz Fiyat");

        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue(highestPrice);
        dataRow.createCell(1).setCellValue(lowestPrice);
        dataRow.createCell(2).setCellValue(currentPrice);

        // Excel dosyasını kaydet
        try (FileOutputStream outputStream = new FileOutputStream("fiyatlar.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


        }
        @Then("I export the product details to an Excel file")
        public void ıExportTheProductDetailsToAnExcelFile() {

        By itemLocator = By.cssSelector("[class='s10v53f3-0 dvgoOH'] > div > ul > li");
        List<WebElement> itemList = PageDriver.getDriver().findElements(itemLocator);

        Workbook workbook = new XSSFWorkbook();//Bir Excel çalışma kitabı oluşturdum
        Sheet sheet = workbook.createSheet("Özellikler");// özellikler adında bir dosya

        // Her özellik için bir satır ekledim ve özellikleri ayrı hücrelere yazdım
        for (int i = 0; i < itemList.size(); i++) {  //itemList üzerinde bir döngü başlatır. Her özellik için
            // bir satır eklemek ve özellikleri ayrı hücrelere yazmak için bu döngü kullanılır.

            WebElement item = itemList.get(i);
            Row row = sheet.createRow(i); //Excel sayfasına bir satır ekler. Bu, her bir özelliği temsil eden bir satırı oluşturur.

            String itemText = item.getText(); //Özelliğin metin içeriğini alır. Yani özelliğin tüm metnini itemText değişkenine koyar.
            String[] ozellikler = itemText.split("\n");//itemText içeriğini satır sonlarına göre ayırır
            // ve bu satırları ozellikler adlı bir diziye koyar. Bu, özelliklerin her birini ayrı bir hücreye koymak için kullanılır.

            // Özellikleri ayrı hücrelere yazdım
            for (int j = 0; j < ozellikler.length; j++) {//ozellikler dizisini döngüye alır
                Cell cell = row.createCell(j); //Her bir hücreyi oluşturur.
                cell.setCellValue(ozellikler[j].trim()); // trim() kullanarak başındaki ve sonundaki boşlukları kaldırdım
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream("ozellikler.xlsx")) {//Excel dosyasına verileri yazmak için
            // bir çıktı akışı oluşturur ve dosyayı kaydeder.
            workbook.write(outputStream);// bu satır verilerin dosyaya yazılmasını gerçekleştirir.
        } catch (IOException e) { //eğer bir hata (IOException) meydana gelirse, bu hatayı yakalar ve ekrana yazdırır
            e.printStackTrace();
        }

    }

    @And("I go to the store with the cheapest price")
    public void ıGoToTheStoreWithTheCheapestPrice() {
        ((JavascriptExecutor) PageDriver.getDriver()).executeScript("window.scrollTo(0, 0);");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='s1wl91l5-2 dWTGzr']")));
        dc.clickFunc(dc.goShopping);


    }

    @Then("I verify the Url of the redirected store and go back to the previous window")
    public void ıVerifyTheUrlOfTheRedirectedStoreAndGoBackToThePreviousWindow() throws InterruptedException {

        String currentWindowHandle = PageDriver.getDriver().getWindowHandle();// Bu tanımlayıcı, açılan her pencerenin veya sekmenin benzersiz bir kimliğidir.
        // Her pencereye veya sekme açıldığında, WebDriver bu kimliği oluşturur ve kaydeder.
        Thread.sleep(5000);

// Tüm pencere tanımlayıcılarını aldım
        Set<String> windowHandles = PageDriver.getDriver().getWindowHandles();

// Yeni pencereye geçmek için döngü kullandım
        for (String handle : windowHandles) {
            if (!handle.equals(currentWindowHandle)) {
                PageDriver.getDriver().switchTo().window(handle); // Yeni pencereye geçiş
                break;
            }
        }

        //  yeni pencerede  URL'yi aldım
        String newWindowURL = PageDriver.getDriver().getCurrentUrl();
        System.out.println("Yeni pencere URL'si: " + newWindowURL);

        // Önceki pencereye geri dön
        PageDriver.getDriver().switchTo().window(currentWindowHandle);

    }

    @Then("I verify the URL of the redirected store")
    public void ıVerifyTheURLOfTheRedirectedStore() throws InterruptedException {

        String currentWindowHandle = PageDriver.getDriver().getWindowHandle();
        Thread.sleep(5000);

        Set<String> windowHandles = PageDriver.getDriver().getWindowHandles();


        for (String handle : windowHandles) {
            if (!handle.equals(currentWindowHandle)) {
                PageDriver.getDriver().switchTo().window(handle);
                break;
            }
        }


        String newWindowURL = PageDriver.getDriver().getCurrentUrl();
        System.out.println("Yeni pencere URL'si: " + newWindowURL);

        // Önceki pencereye geri dön
        PageDriver.getDriver().switchTo().window(currentWindowHandle);

    }

    @Then("I log out")
    public void ıLogOut() throws InterruptedException {
        Actions actions = new Actions(PageDriver.getDriver());
        actions.moveToElement(dc.logOut1).click().build().perform();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[@class='menuCategoryItemTitle'])[4]")));
        actions.moveToElement(dc.logOut2).click().build().perform();


    }



}
