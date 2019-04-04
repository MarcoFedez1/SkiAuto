package Pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class gmail {
	
	WebDriver driver;
	
	public gmail (WebDriver driver){
        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);
    }
	
	@FindBy (id = "identifierId")
	private WebElement email;
	
	@FindBy (xpath = "//*[@id=\"password\"]/div[1]/div/div[1]/input")
	private WebElement pass;
	
	@FindBy (id = "identifierNext")
	private WebElement idNext;
	
	@FindBy (id = "passwordNext")
	private WebElement passNext;
	
	@FindBy (id = ":2r")
	private WebElement Emails;
	
	public void gmailSingIn(String Email, String Pass) {
		email.clear();
		email.sendKeys(Email);
		idNext.click();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		pass.clear();
		pass.sendKeys(Pass);
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		js.executeScript("arguments[0].click()", passNext);
	}
	
	public void verifyEmail() throws InterruptedException {
		Thread.sleep(5000);
		List<WebElement> row = Emails.findElements(By.xpath("//tr[@class='zA yO']"));
		for (int i = 0; i < row.size(); i++) {
			WebElement concern = row.get(i).findElement(By.className("bog"));
			String concernTxt = concern.getAttribute("innerText");
			if(concernTxt.equals("Your quote request")) {
				concern.click();
				String EmailSender = getEmailSender();
				Assert.assertTrue(EmailSender.contains("@ski.com"));	
			}
		}
	}
	
	public String getEmailSender() throws InterruptedException {
		try {
			driver.findElement(By.cssSelector("div.adn.ads > div.gs > div.gE.iv.gt > table > tbody > tr:nth-child(1) > td.gF.gK > table > tbody > tr > td > h3 > span > span"));
			return driver.findElement(By.cssSelector("div.adn.ads > div.gs > div.gE.iv.gt > table > tbody > tr:nth-child(1) > td.gF.gK > table > tbody > tr > td > h3 > span > span")).getAttribute("innerText");
		} catch (NoSuchElementException e) {
			driver.findElement(By.cssSelector("div.adf.ads > div.gs.gt > div > table > tbody > tr:nth-child(1) > td:nth-child(1) > span"));
			Thread.sleep(1500);
			return driver.findElement(By.cssSelector("div.adf.ads > div.gs.gt > div > table > tbody > tr:nth-child(1) > td:nth-child(1) > span")).getAttribute("innerText");
		}
	}
}
