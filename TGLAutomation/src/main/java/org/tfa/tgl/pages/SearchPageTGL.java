package org.tfa.tgl.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.tfa.framework.core.WebDriverUtil;
import org.tfa.framework.utilities.testdata.TestData;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPageTGL {
	
	private WebDriverUtil webUtil=WebDriverUtil.getObject();
	private TestData data=TestData.getObject();
	private boolean flag;
	Logger log=Logger.getLogger("rootLogger");
	ArrayList <String> names=new ArrayList <String>() ;
	
	public boolean verifyAppYearDefaultSelection()
	{
		boolean flag=false;
		
		Select appyeardd=new Select(webUtil.getElement("Tgl_appyear_dd"));
		
		if(appyeardd.getFirstSelectedOption().getText().contains("2020"))
			return flag=true;
		else
			flag=false;
			
		
		return flag;
		
	}
	
	public boolean verifymorelinkclick()
	{
		boolean flag=false;
		
		webUtil.click("Tgl_moreSearchOptionsLink");
		List <WebElement> webelementcontainer= webUtil.getDriver().findElements(By.xpath("//div[@data-hook='extended-content']//input-container"));
		
		for(WebElement element : webelementcontainer){
			
			if(element.isDisplayed()&&element.isEnabled())
					flag=true;
			else
					return flag=false;
			
			
		}
		
		//webUtil.getElement(arg0)
		return flag;
	}
	
	public boolean verifydefaultsort() throws InterruptedException{
		boolean flag=false;
		
			
		webUtil.getDriver().manage().window().maximize();
		webUtil.setTextBoxValue("Tgl_firstname", "a");
		webUtil.click("Home_Tgl_Search2_btn");
		Thread.sleep(1000);
		
		 WebElement datahook=webUtil.getDriver().findElement(By.xpath("//tbody[@data-hook='results']"));
		
		 List <WebElement>searchresults=datahook.findElements(By.xpath("//tr"));
		 
		 int size=searchresults.size();
		 
		 searchresults.clear();
		 int i=1;
		 String s;
		 
		 while(i<size){	
		 s=String.valueOf(i);	 
		 searchresults.add(datahook.findElement(By.xpath("//tr["+s+"]/td[1]/a")));
		 i++;}
		 
		if(searchresults.size()<4){
			webUtil.setTextBoxValue("Tgl_firstname", "e");
			searchresults=webUtil.getDriver().findElements(By.xpath("//tbody[@data-hook='results']"));}
		
		if(searchresults.size()<4){
			webUtil.setTextBoxValue("Tgl_firstname", "d");
			searchresults=webUtil.getDriver().findElements(By.xpath("//tbody[@data-hook='results']"));}
		
		if(searchresults.size()<4){
			log.info("not enough search results");return false;}
			
		
		for(WebElement w: searchresults){
			
			names.add(w.getText());
			
		}
		
		flag=isSorted(names);
		
		
		return flag;
	}
	
	
	public boolean isSorted(ArrayList<String> s){
		boolean flag=false;
		
		Iterator<String> iter= s.iterator();
		
		String current, previous = iter.next();
		previous=previous.toLowerCase();
	    while (iter.hasNext()) {
	        current = iter.next();
	        current=current.toLowerCase();
	        if (previous.compareTo(current) > 0) {
	            return false;
	        }
	        previous = current;
	    }
	    return true;
		

	}
	
	public boolean verifyColumnHeaders() throws InterruptedException{
		boolean flag=false;
		//Thread.sleep(1000);
		
		By headerlocator=By.xpath("//tr[@data-hook='column-headers']//th");
		WebDriverWait localwait = new WebDriverWait(webUtil.getDriver(), 30);
		localwait.until(ExpectedConditions.visibilityOfElementLocated(headerlocator));
		
		List <WebElement> we=webUtil.getDriver().findElements(By.xpath("//tr[@data-hook='column-headers']//th"));
		int size=we.size();
		int i=1;
		List <String> headers=new ArrayList(); 
		String s;
		while(i<=size){
			
		s =	webUtil.getDriver().findElement(By.xpath("//tr[@data-hook='column-headers']//th["+String.valueOf(i)+"]//span")).getText();
		headers.add(s);i++;
		}
		if((headers.contains("Name"))&&(headers.contains("Person Id"))&&
		headers.contains("Status")&&headers.contains("Interview Deadline")&&headers.contains("Submitted Date")&&
		headers.contains("Stage")&&headers.contains("Step")&&headers.contains("Exit Code"))
			
			flag=true;
		else
			return flag=false;
		
		
		return flag;
	}

}
