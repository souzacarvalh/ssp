package org.ffsc.ssp.web.beans.common;

import org.primefaces.context.RequestContext;

public class PopupHelper {
	
	private PopupHelper() {}
	
	public static void closeCurrentDialog() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}
}