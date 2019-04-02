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
    
    @Test (priority = 0, description = "Verify that selecting destination, dates, and travelers is able to search travel")
	public void DestinationsOneHotel() throws InterruptedException {
		homeSki homePage = new homeSki(driver);
		HoteDetails hdPage = new HoteDetails(driver);
		homePage.closeNowonSale(); 
		String HotelName = "";
		String [] resorts = SkiResorts.Resorts2();
		for (int k = 0; k < resorts.length; k++ ) {
			System.out.println(resorts[k]);
			Thread.sleep(1000);
			homePage.closeNowonSale(); 
			homePage.setDestination(resorts[k]);
			homePage.setStartEndDates();
			homePage.clickTravelers();
			homePage.setTravelers(3,0,1);
			homePage.clickSearch();
			String infoDetails = hdPage.PriceDisplayed();
			String [] info1 = infoDetails.split("-");
			HotelName = hdPage.getHotelName();
			 if (info1[0].equals("true")) {
				 Price = hdPage.clickSelectRoom();
				 if(Price.isEmpty()) {
				 	System.out.println(HotelName + " don't have visible price");
				 }else {
				 	Thread.sleep(1500);
					softAssertion.assertEquals(driver.getCurrentUrl(), "https://www.ski.com/book/checkout");
					checkout checkPage = new checkout(driver);
					softAssertion.assertEquals(checkPage.getTotalPrice(), Price);
					checkPage.clickRemoveTrip();
					hdPage.closeNewTabs();
				 } 
			}else {
				System.out.println(HotelName + " don't have visible price");
			}
			driver.get(homeURL);
		}
		softAssertion.assertAll();
	}
}
