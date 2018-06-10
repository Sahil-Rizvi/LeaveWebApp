package com.sahil.constants;

import com.sahil.enums.FileType;

public class ConstantValues {

	public static final String DIRECTORY = "/home/sahil/lwa/uploads/";
	public static final String HOLIDAY_DIRECTORY = DIRECTORY+FileType.HOLIDAY.name().toLowerCase();
	public static final String NOTICE_DIRECTORY = DIRECTORY+FileType.NOTICE.name().toLowerCase();
	public static final String LEAVE_POLICY_DIRECTORY = DIRECTORY+FileType.LEAVE_POLICY.name().toLowerCase();
	public static final String OTHERS_DIRECTORY = DIRECTORY+FileType.OTHERS.name().toLowerCase();

	public static final String REPORT_DIRECTORY = "/home/sahil/lwa/reports/";
	public static final String HR_REPORT_DIRECTORY = REPORT_DIRECTORY+"hr";
	public static final String MANAGER_REPORT_DIRECTORY = REPORT_DIRECTORY+"manager";
	
	
}
