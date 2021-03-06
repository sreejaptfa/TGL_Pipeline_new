package org.tfa.tgl.pages.searchdetailsection;

import org.tfa.tgl.pages.searchdetails.SearchDetailsPage;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class IncomeAndTotalNoDependencySection{

	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	private SearchDetailsPage searchDetailsPage = new SearchDetailsPage();
	
	public boolean verifyValidationMessageForStatusChange(String selectType,String selectStatus) {
		boolean flag = false;
		searchDetailsPage.selectTGLStatusDD(selectStatus);
		webUtil.holdOn(1);
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
