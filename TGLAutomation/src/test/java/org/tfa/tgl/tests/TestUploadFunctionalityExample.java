package org.tfa.tgl.tests;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestUploadFunctionalityExample {
	Logger log = Logger.getLogger("rootLogger");
	
	@Test
	public void TGL11133TestUploadFunctionalityExample() {
		 if(SystemUtils.IS_OS_LINUX){
        	 log.info("Jenkins Pipeline");
        }
		String  downloadedFilePath =System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\";
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();	
		System.out.println(System.getProperty("user.dir"));
		chromePrefs.put("download.default_directory", downloadedFilePath);
		chromePrefs.put("directory_upgrade", true);
		chromePrefs.put("extension_to_open", "");
		chromePrefs.put("prompt_for_download", false);
		
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		WebDriver driver = new ChromeDriver(options);

		driver.get("https://qamerlin.teachforamerica.org/ada/sign-in");
		driver.findElement(By.id("username")).sendKeys("ssharma");
		driver.findElement(By.id("password")).sendKeys("Rockstar1");
		driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();
		
		driver.findElement(By.xpath("//button[text()='Clear']")).click();
		driver.findElement(By.xpath("//button[text()='More search options']")).click();
		driver.findElement(By.xpath("//input[@id='personid-input']")).sendKeys("4342507");
		driver.findElement(By.xpath("//span[text()='Search']")).click();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement Category_Body = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody[@data-hook='results']/tr[1]/td[1]/a")));
		Category_Body.click();
		
		WebDriverWait wait1 = new WebDriverWait(driver, 30);
		WebElement Category_Body1 = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='support-document']//ancestor::div[@data-hook='applicant-tax-section']//following-sibling::table[@class='documents-table']//tbody/tr[1]//a")));
		Category_Body1.click();
		
		driver.navigate().refresh();
		WebDriverWait wait2 = new WebDriverWait(driver, 60);
		wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='support-document']//ancestor::div[@data-hook='applicant-tax-section']//following-sibling::table[@class='documents-table']//tbody/tr[1]//a")));

		String  downloadedFilePath1 =System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\TGLUploadDocument.pdf";
		final File folder = new File(downloadedFilePath);
		log.info("File path is -> "+folder.getAbsolutePath());		
		log.info("Directory exists " + folder.exists());
	
		log.info("Can read "+folder.canRead());
		log.info("Can write "+folder.canWrite());
		log.info("Can execute "+folder.canExecute());
		log.info("number items in folder "+folder.list().length);

		File file = new File(downloadedFilePath1);
		if(file.exists()){
			file.delete();
				Assert.assertTrue(true);
		}else{
			Assert.assertFalse(true);
		}
		log.info("==================Browser Logs==================================");
		LogEntries logEntries =driver.manage().logs().get(LogType.BROWSER);
	    for (LogEntry entry : logEntries) {
	    	log.info(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
	    }
	    log.info("=======================================================");
	        
		driver.quit();
	}
}