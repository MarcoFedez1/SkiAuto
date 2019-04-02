package Tests;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Pages.Browser;
import Pages.HoteDetails;
import Pages.Lodging;
import Pages.RequestQuote;
import Pages.checkout;
import Pages.homeSki;

public class hotelsTest {
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
	public void SelectTravelDates() throws InterruptedException {
		homeSki homePage = new homeSki(driver);
		Lodging l = new Lodging(driver);
		HoteDetails hdPage = new HoteDetails(driver);
		RequestQuote rqPage = new RequestQuote(driver);
		homePage.closeNowonSale(); 
		String HotelName = "";
		Thread.sleep(1000);
		homePage.closeNowonSale(); 
		homePage.setDestination("Alta Badia");
		String dates = homePage.setStartEndDates();
		homePage.clickTravelers();
		homePage.setTravelers(3,0,1);
		homePage.clickSearch();
		softAssertion.assertEquals(l.getTitle(), "Available Lodging " + dates);
		int [] size = l.numberTabs(l.getNumberResults());
		int r = 10;
		for (int j = 0; j < size[1]; j ++) {
			if (j == (size[1]-1)) {
				r = size[0];
			}
			for (int i = 0; i < r; i ++) {
				l.SwitchWinTabs();
				String [] info = l.reviewHotel(i);
		 		if (info[2].equals("true")) {
					String infoDetails = hdPage.PriceDisplayed();
					String [] info1 = infoDetails.split("-");
					HotelName = hdPage.getHotelName();
		 			if (info1[0].equals("true")) {
			 			softAssertion.assertEquals(HotelName + " "  + info1[1], info[1] + " "  + info[0]);
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
						softAssertion.assertEquals(rqPage.getTitle(), "Get your free, personalized quote today!");
						rqPage.setRequestQuote("Marco", "Fernandez", "fedez.marco1@gmail.com", "3123568974");
						hdPage.closeNewTabs();
					}	
		 		}else {
		 			Thread.sleep(500);
		 			hdPage.closeNewTabs();
		 		}
		 		hdPage.closeNewTabs();
			}
			if(size[1] > 1) {
				l.clickNextPage();
			}
		}
		driver.get(homeURL);
		softAssertion.assertAll();
	}
}
