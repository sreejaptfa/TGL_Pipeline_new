package org.tfa.tgl.pages;

import java.awt.event.KeyEvent;
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
	WebDriverWait localwait;
	
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
		 
		 int size;
		 
		 if(searchresults.size()<4){
				webUtil.setTextBoxValue("Tgl_firstname", "e");
				datahook=webUtil.getDriver().findElement(By.xpath("//tbody[@data-hook='results']"));
				searchresults=datahook.findElements(By.xpath("//tr"));
		 }
			
		if(searchresults.size()<4){
				webUtil.setTextBoxValue("Tgl_firstname", "d");
				datahook=webUtil.getDriver().findElement(By.xpath("//tbody[@data-hook='results']"));
				searchresults=datahook.findElements(By.xpath("//tr"));
		}
			
		if(searchresults.size()<4){
				log.info("not enough search results");return false;}
		 
		
		size=searchresults.size();
		 searchresults.clear();
		 int i=1;
		 String s;
		 
		 while(i<size){	
		 s=String.valueOf(i);	 
		 searchresults.add(datahook.findElement(By.xpath("//tr["+s+"]/td[1]/a")));
		 i++;}
		 
		
			
		
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
		localwait = new WebDriverWait(webUtil.getDriver(), 30);
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
	
	public boolean verifyRowIsLinked(){
		boolean flag=false;
		
		
		webUtil.setTextBoxValue("Tgl_firstname", "a");
		webUtil.click("Home_Tgl_Search2_btn");
		By firstrowxpath=By.xpath("//tbody[@data-hook='results']/tr[1]/td/a");
		WebDriverWait localwait=new WebDriverWait(webUtil.getDriver(), 30);
		localwait.until(ExpectedConditions.visibilityOfElementLocated(firstrowxpath));
		
		WebElement firstrow=webUtil.getDriver().findElement(By.xpath("//tbody[@data-hook='results']/tr[1]/td/a"));
		String rowname=webUtil.getDriver().findElement(By.xpath("//tbody[@data-hook='results']/tr[1]/td/a")).getText();
		
		
		firstrow.click();
		
		By firstrowdetailxpath=By.xpath("//h2[@class='applicant-context-heading']/div");
		localwait.until(ExpectedConditions.visibilityOfElementLocated(firstrowdetailxpath));
		
		if(webUtil.getDriver().getCurrentUrl().contains("details"))
			flag=true;
		else
			return flag=false;
		
		
		String name=webUtil.getDriver().findElement(By.xpath("//h2[@class='applicant-context-heading']/div")).getText();
		
		if(name.contains(rowname))
			flag=true;
		else
			return flag=false;
		
		return flag;
	}
	public boolean verifyEachFilter() throws InterruptedException{
		boolean flag=false;
		String s;
		int size,i=1;
		localwait=new WebDriverWait(webUtil.getDriver(), 30);
		By rowdetailxpath=By.xpath("//tbody[@data-hook='results']/tr[2]");
		//localwait.until(ExpectedConditions.visibilityOfElementLocated(rowdetailxpath));
		
		webUtil.getElement("Tgl_InterviewDeadlinefilter_txt").click();
		webUtil.getDriver().findElement(By.xpath("(//div[@data-value='02/28/20'])[1]")).click();
		Thread.sleep(500);
		webUtil.click("Home_Tgl_Search2_btn");
		//Thread.sleep(1000);	
		localwait.until(ExpectedConditions.visibilityOfElementLocated(rowdetailxpath));
		
			
		List <WebElement>searchresults=webUtil.getDriver().findElements(By.xpath("//tbody[@data-hook='results']//tr"));
		size=searchresults.size();
		
		while(i<=size){
			
		WebElement row=webUtil.getDriver().findElement(By.xpath("//tbody[@data-hook='results']/tr["+String.valueOf(i)+"]/td[4]/a"));
			s=row.getText();		
			if(s.contains("02/28/20"))
				flag=true;
			else
				return flag=false;
			
			i++;
		}
		
	
		webUtil.getDriver().navigate().refresh();
		Thread.sleep(500);
		//webUtil.getDriver().findElement(By.xpath("(//div[@class='selectize-input items not-full has-options']/input)[1]")).clear();
		webUtil.getElement("Tgl_TGLStatus_txt").click();
		Thread.sleep(500);
		webUtil.getDriver().findElement(By.xpath("//div[@data-value='COMPLETE']")).click();
		Thread.sleep(500);
		webUtil.click("Home_Tgl_Search2_btn");
		localwait.until(ExpectedConditions.visibilityOfElementLocated(rowdetailxpath));
		
		searchresults=webUtil.getDriver().findElements(By.xpath("//tbody[@data-hook='results']//tr"));
		size=searchresults.size();
		
		for (i=1;i<=size || i<25 ;i++){
			
		WebElement row=webUtil.getDriver().findElement(By.xpath("//tbody[@data-hook='results']/tr["+String.valueOf(i)+"]/td[3]/a"));
			s=row.getText();		
			if(s.contains("COMPLETE"))
				flag=true;
			else
				return flag=false;
			
			
		}
		
		return flag;
	}
	
	
}












