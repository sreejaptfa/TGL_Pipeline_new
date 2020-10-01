package org.tfa.tgl.utilities.web;

import org.tfa.framework.core.Constants;

public class TGLConstants implements Constants
{
	
	public static final String TFACT_DATE_FORMAT="M/d/yyyy";
	public static final String TFACT_DATE_TIMESTAMP_FORMAT="M_d_yyyy hh_mm_ss";
	public static final String TESTCASE_ID_PATTERN="tgl\\d{1,5}";

	
	public String getTestIDPattern() {
		return TESTCASE_ID_PATTERN;
	}

}
