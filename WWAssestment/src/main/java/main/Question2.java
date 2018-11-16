package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Question2 {
	
	WebDriver driver;
	WebDriverWait wait;
	List<WebElement> weekSchedule;
	
	private final String webUrl = "https://www.weightwatchers.com/us/";
	private final String expectedHomepageTitle = "WW (Weight Watchers): Weight Loss & Wellness Help";
	private final String expectedWebpageTitle = "Find a Studio & Meeting Near You | WW USA";
	private final String zipcode = "10011";
	
	@BeforeTest
    public static void setupTest() {
    	// setup driver executable file path
        WebDriverManager.chromedriver().setup();
    }
	
	@BeforeClass
    public void setupClass() {
    	// initialize driver & configurations
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
    }
	
    @AfterClass 
    public void teardownClass() {
    	try {
    		Thread.sleep(5000);
    	}
    	catch (InterruptedException e) {
			e.printStackTrace();
    	}
    	driver.quit();
    }
    
	@DataProvider
	public static Object[][] setWeekday() {
		Object[][] weekdays = new Object[7][1];
		
		weekdays[0][0] = "Sun";
		weekdays[1][0] = "Mon";
		weekdays[2][0] = "Tue";
		weekdays[3][0] = "Wed";
		weekdays[4][0] = "Thu";
		weekdays[5][0] = "Fri";
		weekdays[6][0] = "Sat";

		return weekdays;
	}
    
    @Test
    public void verifyHomepageLoads() {
		driver.get(webUrl);
		Assert.assertEquals(driver.getTitle(), expectedHomepageTitle);
    }
    
    @Test(dependsOnMethods={"verifyHomepageLoads"})
    public void verifyWebpageLoads() {
    	driver.findElement(
    		By.className("menu-link-find-a-studio")
    	).click();
    	
		Assert.assertEquals(driver.getTitle(), expectedWebpageTitle);
    }
    
    @Test(dependsOnMethods={"verifyWebpageLoads"})
    public void getFirstResult() {
    	
    	// Search zipcode
    	driver.findElement(
    		By.id("meetingSearch")
    	).sendKeys(zipcode);
    	
    	driver.findElement(
    		By.className("input-group-btn")
    	).click();
    	
    	// Get location name
    	WebElement firstResult = driver.findElements(
    		By.className("meeting-locations-list__item")
    	).get(0);
    	
    	String locationName = firstResult.findElement(
    		By.className("location__name")
    	).findElement(
    		By.tagName("span")
    	).getText();
    	
    	// Get location distance
    	String locationDistance = firstResult.findElement(
        	By.className("location__distance")
    	).getText();
    	
    	System.out.println(locationName);
    	System.out.println(locationDistance);
    	
    	// Click on result and save week schedule
    	firstResult.click();
    	
    	wait.until(
    		ExpectedConditions.presenceOfElementLocated(
    			By.className("meeting-detail-bottom-top")
    		)
    	);
        	
        weekSchedule = driver.findElements(
        	By.className("schedule-detailed-day")
        );
    }
    
    @Test(dependsOnMethods={"getFirstResult"}, dataProvider="setWeekday")
    public void getDaySchedule(String weekday) {
    
    	int dayIndex;
    	String leader;
    	List<WebElement> daySchedule;
        Map<String, Integer> meetingCount = new HashMap<String, Integer>();

    	switch(weekday) {
    		case "Sun":
    			dayIndex = 0;
    			break;
    		case "Mon":
    			dayIndex = 1;
    			break;
    		case "Tue":
    			dayIndex = 2;
    			break;
    		case "Wed":
    			dayIndex = 3;
    			break;
    		case "Thu":
    			dayIndex = 4;
    			break;
    		case "Fri":
    			dayIndex = 5;
    			break;
    		case "Sat":
    			dayIndex = 6;
    			break;
    		default:
    			dayIndex = 0;
    			break;
    	}
    	
    	// Get day schedule from week schedule
    	daySchedule = weekSchedule.get(dayIndex).findElements(
    		By.className("schedule-detailed-day-meetings-item")
    	);
    	
    	for(int i = 0; i < daySchedule.size(); i++) {
    		
    		leader = daySchedule.get(i).findElement(
    			By.className("schedule-detailed-day-meetings-item-leader")
    		).getText();
    		
    		if(!meetingCount.containsKey(leader))
    			// Add name to table
    			meetingCount.put(leader, 1);
    		else
    			// Increment count
    			meetingCount.put(leader, meetingCount.get(leader)+1);
    	}
    	
    	System.out.println(weekday);
    	
    	for(String name : meetingCount.keySet()) {
    		System.out.println("Name: " + name + ", Total: " + meetingCount.get(name));
    	}
    }
}

