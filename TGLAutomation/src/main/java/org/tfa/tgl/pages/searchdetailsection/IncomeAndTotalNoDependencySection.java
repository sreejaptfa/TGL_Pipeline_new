package org.tfa.tgl.pages.searchdetailsection;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.tfa.tgl.pages.searchdetails.SearchDetailsPage;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class IncomeAndTotalNoDependencySection{

	Logger log = Logger.getLogger("rootLogger");
		private TGLWebUtil webUtil = TGLWebUtil.getObject();
	private SearchDetailsPage searchDetailsPage = new SearchDetailsPage();
	
	public boolean verifyMessageValidationMessageForStatusChangeToComplete(String selectType) {
		boolean flag = false;
		searchDetailsPage.selectTGLStatusDD("Complete");
		String actText = webUtil.getElement("Tgl_ApplicantValidMessage_Lk").getText();
		String expText = "Status cannot be changed to complete if Income or Total # of Dependents is blank.";
		switch (selectType) {
		case "No Message":
			if (!actText.contains(expText)) {
				flag = true;
			}break;
		case "Valid Message":
				if (actText.contains(expText)) {
				flag = true;
			}break;
		default:
			break;
		}
		return flag;
		
	}

}
