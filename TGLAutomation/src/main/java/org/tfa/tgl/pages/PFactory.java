package org.tfa.tgl.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.framework.core.WebDriverUtil;

public class PFactory extends BaseTestMethods{
	
	WebDriverUtil webUtil=WebDriverUtil.getObject();
	protected By firstrowlocator=By.xpath("//tbody[@data-hook='results']/tr[1]/td/a");
	protected By searchresultstable=By.xpath("//tbody[@data-hook='results']//tr");
	protected By statusvalidations = By.xpath("//div[@class='application-status-input-wrapper']//validation-message");
	protected By firstrownamelocator=By.xpath("//tbody[@data-hook='results']/tr[1]/td/a");
	protected WebDriverWait explicitwait;
	
	public PFactory() {
		explicitwait= new WebDriverWait(webUtil.getDriver(), 20);
	}

}