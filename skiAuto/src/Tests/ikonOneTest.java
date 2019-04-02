package Tests;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Pages.Browser;
import Pages.homeSki;
import Pages.ikon;
import Pages.payment;
import Pages.travelers;

public class ikonOneTest {
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
	    @AfterTest
	    public void after() {
	    	driver.close();
	    }
	    
	    @Test (priority = 9, description = "Fill billing and payment information and click 'Reserve Now'")
		public void BillingInformation() throws InterruptedException   {
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
	    	String [] names = {"Marco",  "Pablo", "Jocelyn", "Victor"};
	    	String [] bdays = {"06/25/1985", "02/14/2012", "08/07/2002", "10/14/2017" };
	    	tPage.setAdultTraveler(names, "Fernandez", bdays);
	    	tPage.clickContinue();
	    	payment payPage = new payment(driver);
	    	Thread.sleep(2000);
	    	payPage.setBillingInformation("fedez.marco1@gmail.com", "Marco", "Fernandez", "504 N Jordan Ave", "Liberal", "KS", "US", "69701", "6325897485");
	    	payPage.setPaymentMethod("4111111111111111", "235", "05", "2021");
	    	payPage.clickReserveNow();
	    }
}
