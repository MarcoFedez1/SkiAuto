package Tests;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Pages.Browser;
import Pages.homeSki;
import Pages.ikon;
import Pages.payment;
import Pages.travelers;

public class p {
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
	    @Test (priority = 0, description = "pruebas")
		public void pruebas() throws InterruptedException {
	    	homeSki homePage = new homeSki(driver);
	    	homePage.closeNowonSale();
	    	homePage.clickIkonLink();
	    	Assert.assertEquals(driver.getCurrentUrl(), "https://epic.events.ski.com/ikon");
	    	ikon iPage = new ikon(driver);
	    	iPage.clickBuyNow();
	    	iPage.AddAdultPass(1);
	    	iPage.AddChildPass(1);
	    	iPage.AddTeensPass(1);
	    	iPage.AddToddlerPass(1);
	    	Thread.sleep(300);
	    	String price = iPage.getPrice();
	    	iPage.clickLetBookIt();
	    	travelers tPage = new travelers(driver);
	    	Assert.assertEquals(price, tPage.getPrice());
	    	Assert.assertEquals(driver.getCurrentUrl(), "https://epic.events.ski.com/travelers");
	    }
}
