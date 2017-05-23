package org.ffsc.ssp.web.beans.common;

public enum SSPMessage {
	
	AUTHENTICATION_FAILED("validation_authentication_failed"),
	ADD_STEP_TO_TICKET_SUCCESS("ssp_message_add_progress_success"),
	ADD_STEP_TO_TICKET_FAIL("ssp_message_add_progress_fail"),
	ATTACH_CONVERSION_FAILED("message_attach_conversion_fail"),
	ATTACH_DOWNLOAD_FAILED("message_attach_download_fail"),
	CANCEL_TICKET_SUCCESS("message_cancel_ticket_success"),
	CANCEL_TICKET_FAILED("message_cancel_ticket_fail"),
	MARK_TICKET_RESOLVED_SUCCESS("message_mark_ticket_solved_success"),
	MARK_TICKET_RESOLVED_FAILED("message_mark_ticket_solved_fail"),
	OPEN_TICKET_DETAILS("message_open_ticket_details"),
	OPEN_TICKET_SUCCESS("message_open_ticket_success"),
	OPEN_TICKET_FAILED("message_open_ticket_fail"),
	OPERATION_SUCCESS("message_operation_success"),
	REQUESTS_NOT_AVAILABLE("message_requests_not_found"),
	REQUESTS_NOT_AVAILABLE_STATUS("message_requests_not_found_with_status"),
	SAVE_FEEDBACK_DETAIL("ssp_message_save_feedback_success_detail"),
	SAVE_FEEDBACK_SUCCESS("ssp_message_save_feedback_success"),
	SAVE_FEEDBACK_FAIL("ssp_message_save_feedback_fail");
	
	final String bundleKey;
	
	private SSPMessage(String bundleKey) {
		this.bundleKey = bundleKey;
	}
	
	public String getKey() {
		return bundleKey;
	}
}