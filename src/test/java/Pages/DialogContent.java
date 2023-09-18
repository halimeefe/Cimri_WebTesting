package Pages;

import Utilities.PageDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DialogContent extends Parent {

    public DialogContent(){
        PageFactory.initElements(PageDriver.getDriver(),this);

    }

    @FindBy(xpath = "(//*[text()='Giriş Yap'])[2]")
    public WebElement loginSecond;

    @FindBy(xpath = "(//*[text()='Giriş Yap'])[1]")
    public WebElement loginFirst;

    @FindBy(css = "[id='email']")
    public WebElement inputMail;

    @FindBy(xpath = "(//input[@type='password'])[1]")
    public WebElement inputPassword;

    @FindBy(css = ".sc-iCoGMd.juPwmI")
    // css selector de class elementinin boşluklarına nokta koymalıyız aksi takdirde COMPOUND CLASS NAMES NOT PERMİTTED  HATASI ALIRIZ
    public WebElement loginBtn;

    @FindBy(className = "arrowNext")
    public WebElement categories;

    @FindBy(xpath = "(//*[@class='rightNavMenuItem link'])[2]")
    public WebElement fiyatiDusenler;

    @FindBy(xpath = "//*[@class='s1a29zcm-10 dbqLIu']/div")
    public WebElement firstProduct;

    @FindBy(xpath = "(//*[@class='s1fqyqkq-4 ckAXTq'])[1]/following-sibling::p") // 1.
    public WebElement highestPrice;

    @FindBy(xpath = "(//*[@class='s1fqyqkq-4 ckAXTq'])[2]/following-sibling::p")
    public WebElement lowestPrice;

    @FindBy(xpath = "(//*[@class='s1fqyqkq-10 bItbsS']/p)[1]")
    public WebElement currentPrice;

    @FindBy(css = "[class='s1wl91l5-2 dWTGzr']")
    public WebElement goShopping;

    @FindBy(xpath = "//span[text()='Hesabım']")
    public WebElement logOut1;

    @FindBy(xpath = "(//*[@class='menuCategoryItemTitle'])[4]")
    public WebElement logOut2;

    @FindBy(xpath = "(//*[@class='sc-dlnjwi biYKOn'])[1]")
    public WebElement rememberMe;


}
