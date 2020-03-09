package org.tfa.tgl.pages;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

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
	ArrayList <String> namesnew=new ArrayList <String>() ;
	
	WebDriverWait localwait;
	private Random r;
	protected static String randompersonid;
	By inputcontainerlocator;
	By datahooklocator;
	By columnheaderslocator;
	By firstrowxpath;
	By firstrownamelocator;
	By firstrowfullnamelocator;
	By firstrowpersonidlocator;
	By searchresultstable;
	By applicantnameheading;
	
	/* All the locator hardcoding to be replaced with TestData Sheets - Pending
	 * 
	 * 
	 */
	public SearchPageTGL(){
		
		r=new Random();
		inputcontainerlocator = By.xpath("//div[@data-hook='extended-content']//input-container");
		datahooklocator=By.xpath("//tbody[@data-hook='results']");
		columnheaderslocator=By.xpath("//tr[@data-hook='column-headers']//th");
		firstrowxpath=By.xpath("//tbody[@data-hook='results']/tr[1]/td/a");
		firstrownamelocator=By.xpath("//tbody[@data-hook='results']/tr[1]/td/a");
		firstrowfullnamelocator=By.xpath("//tbody[@data-hook='results']/tr[1]/td[1]/a");
		firstrowpersonidlocator=By.xpath("//tbody[@data-hook='results']/tr[1]/td[2]/a");
		searchresultstable=By.xpath("//tbody[@data-hook='results']//tr");
		applicantnameheading=By.xpath("//h2[@class='applicant-context-heading']/div");
	}
	// TestCase - SearchPage - Step 6
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
	// TestCase - SearchPage - Step 7
	public boolean verifymorelinkclick()
	{
		boolean flag=false;
		//More link is already clicked in previous test steps
		//webUtil.click("Tgl_moreSearchOptionsLink");
		List <WebElement> webelementcontainer= webUtil.getDriver().findElements(inputcontainerlocator);
		
		for(WebElement element : webelementcontainer){
			
			if(element.isDisplayed()&&element.isEnabled())
					flag=true;
			else
					return flag=false;
			
			
		}
		
		//webUtil.getElement(arg0)
		return flag;
	}
	// TestCase - SearchPage - Step 2
	public boolean verifydefaultsort() throws InterruptedException{
		boolean flag=false;
		
		webUtil.getDriver().navigate().refresh();
		webUtil.waitForBrowserToLoadCompletely();
		webUtil.selectByIndex("Tgl_appyear_dd", 0);
		webUtil.click("Tgl_moreSearchOptionsLink");
		webUtil.getDriver().manage().window().maximize();
		webUtil.holdOn(3);
		webUtil.setTextBoxValue("Tgl_firstname", "a");
		webUtil.holdOn(2);
		webUtil.click("Home_Tgl_Search2_btn");
		webUtil.holdOn(3);
		
		 WebElement datahook=webUtil.getDriver().findElement(datahooklocator);
		
		 List <WebElement>searchresults=datahook.findElements(By.xpath("//tr"));
		 
		 int size;
		 
		 if(searchresults.size()<4){
				webUtil.setTextBoxValue("Tgl_firstname", "e");
				datahook=webUtil.getDriver().findElement(datahooklocator);
				searchresults=datahook.findElements(By.xpath("//tr"));
		 }
			
		if(searchresults.size()<4){
				webUtil.setTextBoxValue("Tgl_firstname", "d");
				datahook=webUtil.getDriver().findElement(datahooklocator);
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
		 
		
			
		int p=0;
		// Verify sort only for first 25 records
		for(WebElement w: searchresults){
			
			names.add(w.getText());
			if(p++>25)
				break;
			
		}
		
		for (int k=0;k<names.size();k++)
			namesnew.add(names.get(k).substring(0,3));
		
		
		flag=isSorted(namesnew);
		
		
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
	// TestCase - SearchPage - Step 9
	public boolean verifyColumnHeaders() throws InterruptedException{
		boolean flag=false;
		//Thread.sleep(1000);
		
		By headerlocator=By.xpath("//tr[@data-hook='column-headers']//th");
		localwait = new WebDriverWait(webUtil.getDriver(), 15);
		localwait.until(ExpectedConditions.visibilityOfElementLocated(headerlocator));
		
		List <WebElement> we=webUtil.getDriver().findElements(columnheaderslocator);
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
	// TestCase - SearchPage - Step 10
	public boolean verifyRowIsLinked(){
		boolean flag=false;
		
		
		webUtil.setTextBoxValue("Tgl_firstname", "a");
		webUtil.click("Home_Tgl_Search2_btn");		
		WebDriverWait localwait=new WebDriverWait(webUtil.getDriver(), 15);
		localwait.until(ExpectedConditions.visibilityOfElementLocated(firstrowxpath));		
		WebElement firstrow=webUtil.getDriver().findElement(firstrownamelocator);
		String rowname=webUtil.getElement("Tgl_firstrow_name").getText();		
		firstrow.click();		
		By tableheading=By.xpath("//h2[@class='applicant-context-heading']/div");
		localwait.until(ExpectedConditions.visibilityOfElementLocated(tableheading));
		
		if(webUtil.getDriver().getCurrentUrl().contains("details"))
			flag=true;
		else
			return flag=false;
				
		String name=webUtil.getDriver().findElement(applicantnameheading).getText();		
		if(name.contains(rowname))
			flag=true;
		else
			return flag=false;
		
		return flag;
	}
	
	// TestCase - SearchPage - Step 11, 12, 13
	public boolean verifyEachFilter() throws InterruptedException{
		boolean flag=false, validofferdeadline=true;
		String s,deadline;
		int size=0,i=1,random=0, randomStatus=0;
		localwait=new WebDriverWait(webUtil.getDriver(), 15);
		String intvwdeadlineselection;
		List <WebElement>searchresults;
		String statusselection="";
		By rowdetailxpath;
		
		webUtil.getDriver().navigate().to("https://qamerlin.teachforamerica.org/ada/tgl");
		webUtil.waitForBrowserToLoadCompletely();
		WebElement InterviewDeadlinedd=webUtil.getElement("Tgl_InterviewDeadlinefilter_txt");		
		//localwait.until(ExpectedConditions.visibilityOfElementLocated(rowdetailxpath));
		rowdetailxpath=By.xpath("//tbody[@data-hook='results']/tr[1]");		
		do{
			validofferdeadline=true;
			webUtil.getDriver().navigate().refresh();
			webUtil.waitForBrowserToLoadCompletely();
			webUtil.selectByIndex("Tgl_appyear_dd", 0);
			webUtil.getElement("Tgl_InterviewDeadlinefilter_txt").click();
			while(validofferdeadline==true){
				random=r.nextInt(5);
				random++;
				Thread.sleep(500);
				InterviewDeadlinedd= webUtil.getDriver().findElement(By.xpath("//div[@class='selectize-dropdown-content']/div["+String.valueOf(random)+"]"));
				if(!InterviewDeadlinedd.getText().contains("2045"))
				validofferdeadline=false;					
			}			
			InterviewDeadlinedd.click();
			Thread.sleep(500);
			webUtil.click("Home_Tgl_Search2_btn");		
			intvwdeadlineselection=webUtil.getDriver().findElement(By.xpath("//div[@class='selectize-control multi']/div/div")).getAttribute("data-value");
			//Thread.sleep(1000);	
			try{
				localwait.until(ExpectedConditions.visibilityOfElementLocated(rowdetailxpath));
				searchresults=webUtil.getDriver().findElements(searchresultstable);
				size=searchresults.size();
				if(size<2)
					continue;				
			}catch (Exception e){			
			log.info("No records found with Interview Deadline:"+intvwdeadlineselection);
			//System.out.println("No records found with Interview Deadline:"+intvwdeadlineselection);
			//don't execute remaining code and startover again
			continue;
			//webUtil.getDriver().navigate().refresh();
			}
			int sizefirstfilter=size;
				if(sizefirstfilter>15){	
					do{			
						webUtil.getElement("Tgl_TGLStatus_txt").click();
						Thread.sleep(500);			
						randomStatus=r.nextInt(5);
						randomStatus++;			
						webUtil.getDriver().findElement(By.xpath("(//div[@class='selectize-dropdown-content'])[2]/div["+String.valueOf(randomStatus)+"]")).click();
						//webUtil.getDriver().findElement(By.xpath("//div[@data-value='COMPLETE']")).click();
						Thread.sleep(500);
						webUtil.click("Home_Tgl_Search2_btn");
						statusselection=webUtil.getDriver().findElement(By.xpath("(//div[@class='selectize-input items not-full has-options has-items'])[2]/div")).getText();		
						try{
							size=0;
							//localwait.until(ExpectedConditions.visibilityOfElementLocated(rowdetailxpath));
							searchresults=webUtil.getDriver().findElements(searchresultstable);
							size=searchresults.size();
							if(size<2){
								log.info(size+" no of records searched for interview deadline : "+intvwdeadlineselection+" and application status: "+statusselection);							
							}
							else
								break;
							
						}catch(Exception e){			
							log.info("Exception occured in results for status selection:"+ statusselection);
							//System.out.println("Exception occured in results for status selection:"+ statusselection);
							//continue;		
						}						
						webUtil.getDriver().navigate().refresh();
						webUtil.waitForBrowserToLoadCompletely();
						webUtil.selectByIndex("Tgl_appyear_dd", 0);
						webUtil.getElement("Tgl_InterviewDeadlinefilter_txt").click();
						InterviewDeadlinedd= webUtil.getDriver().findElement(By.xpath("//div[@class='selectize-dropdown-content']/div["+String.valueOf(random)+"]"));
						InterviewDeadlinedd.click();
						Thread.sleep(500);						
						webUtil.waitForBrowserToLoadCompletely();							
					}while(size<2);					
				}else{				
					webUtil.waitForBrowserToLoadCompletely();			
					webUtil.getElement("Tgl_TGLStatus_txt").click();
					Thread.sleep(500);			
					random=r.nextInt(5);
					random++;			
					webUtil.getDriver().findElement(By.xpath("(//div[@class='selectize-dropdown-content'])[2]/div["+String.valueOf(random)+"]")).click();
					//webUtil.getDriver().findElement(By.xpath("//div[@data-value='COMPLETE']")).click();
					Thread.sleep(500);
					webUtil.click("Home_Tgl_Search2_btn");
					statusselection=webUtil.getDriver().findElement(By.xpath("(//div[@class='selectize-input items not-full has-options has-items'])[2]/div")).getText();		
					try{
						size=0;
						//localwait.until(ExpectedConditions.visibilityOfElementLocated(rowdetailxpath));
						searchresults=webUtil.getDriver().findElements(searchresultstable);
						size=searchresults.size();	
						if(size<2){							
							log.info("Less than two ro no records for Status:"+ statusselection+" and deadline"+ intvwdeadlineselection);
							continue;
						}							
					}catch(Exception e){			
						log.info("Exception occured in results for status selection:"+ statusselection);
						//System.out.println("Exception occured in results for status selection:"+ statusselection);
						continue;		
					}
				}
			
		}while(size<2);		
		for (i=1;i<=size;i++){			
		WebElement rowstatus=webUtil.getDriver().findElement(By.xpath("//tbody[@data-hook='results']/tr["+String.valueOf(i)+"]/td[3]/a"));
		WebElement rowdeadline=webUtil.getDriver().findElement(By.xpath("//tbody[@data-hook='results']/tr["+String.valueOf(i)+"]/td[4]/a"));
			s=rowstatus.getText();	
			deadline=rowdeadline.getText();	
		
		if(statusselection.equals("MANAGER REVIEW"))
				statusselection="MGRREVIEW";
		if(s.contains(statusselection)&&deadline.contains(intvwdeadlineselection))
			flag=true;
		else
			return flag=false;
						
		}
		
		log.info(size+" no of records searched for interview deadline : "+intvwdeadlineselection+" and application status: "+statusselection);
		//System.out.println(size+" no of records searched for interview deadline : "+intvwdeadlineselection+ " and application status: "+statusselection);		
		// Verify First Name, Last Name and PersonID search:		
		random=r.nextInt(size);
		random++;
		String fullname=webUtil.getDriver().findElement(By.xpath("//tbody[@data-hook='results']/tr["+String.valueOf(random)+"]/td[1]/a")).getText();
		String personid=webUtil.getDriver().findElement(By.xpath("//tbody[@data-hook='results']/tr["+String.valueOf(random)+"]/td[2]/a")).getText();
		String[] name=fullname.split(",");		
		name[0]=name[0].trim();
		name[1]=name[1].trim();
		webUtil.click("Tgl_moreSearchOptionsLink");
		webUtil.waitUntilElementVisible("Home_Tgl_Search2_btn", 20);
		webUtil.setTextBoxValue("Tgl_firstname", name[1]);
		webUtil.setTextBoxValue("Tgl_lastname", name[0]);	
		webUtil.setTextBoxValue("Tgl_personid", personid);
		webUtil.click("Home_Tgl_Search2_btn");
		
		
		String fullnameresult=webUtil.getDriver().findElement(firstrowfullnamelocator).getText();
		String personidresult=webUtil.getDriver().findElement(firstrowpersonidlocator).getText();
		//to be used by other classes
		randompersonid=personidresult;
				
		if(fullname.equals(fullnameresult))
			flag=true;
		else
			{log.info("Search result doesnot match for Name");return flag=false;}
		
		if(personid.equals(personidresult))
			flag=true;
		else
			{log.info("Search result doesnot match for PersonId");return flag=false;}
		
		log.info("Searched for name:"+fullname +" and personid:"+personid);
		//System.out.println("Searched for name:"+fullname +" and personid:"+personid);
		
		return flag;
	}
	
	public static String getpersonid(){
		
		return randompersonid;
	}
	
	public boolean verifyErrorMessageForPersonID(){
		boolean flag=false;
		webUtil.click("Tgl_moreSearchOptionsLink");
		webUtil.setTextBoxValue("Tgl_personid", "abc");
		//webUtil.click("Home_Tgl_Search2_btn");
		if(webUtil.getElement("Tgl_SearchPIDValidation_lbl").getText().contains("Please enter a number."))
			flag= true;
		else
			return flag=false;	
		
		return flag;
	}	
	public SearchDetailsPageTGL clickFirstRowColumnOnSearchResults(){
		
		webUtil.click("Tgl_FirstRowColumn_TB");
		webUtil.holdOn(5);
		return new SearchDetailsPageTGL();
	}
	public void clickOnRemoveBtn(){
		webUtil.click("Tgl_Remove_btn");
	}
	public void clickOnSearchBtn(){
		webUtil.click("Tgl_Search_btn");
	}
}
