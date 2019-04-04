package Tests;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Pages.Browser;
import Pages.HoteDetails;
import Pages.SkiResorts;
import Pages.checkout;
import Pages.gmail;
import Pages.homeSki;

public class pruebas {
	static WebDriver driver;
    static String Error;
    static String URL;
    static String homeURL;
    static String Price;
    SoftAssert softAssertion = new SoftAssert();
    @BeforeTest
    public void setup() throws MalformedURLException{
    	driver = Browser.LaunchBrowser("chrome");
    	homeURL = driver.getCurrentUrl();
    }
    
    @Test (priority = 3, description = "Submit a Quote")
	public void verifyEmail() throws InterruptedException {
    	driver.get("https://mail.google.com/mail");
    	gmail gmailPage = new gmail(driver);
    	gmailPage.gmailSingIn("fedez.marco1", "Lenovo@1");
    	gmailPage.verifyEmail();
	}
}
