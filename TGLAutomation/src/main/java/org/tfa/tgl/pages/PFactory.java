package org.tfa.tgl.pages;

import org.openqa.selenium.By;

public class PFactory {
	
	protected By firstrowlocator=By.xpath("//tbody[@data-hook='results']/tr[1]/td/a");
	protected By searchresultstable=By.xpath("//tbody[@data-hook='results']//tr");
	
	public PFactory() {
		
	}

}
