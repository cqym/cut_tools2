package com.tl.resource.web.manage.form;

import org.apache.struts.action.ActionForm;

public class MediaFormatActionForm extends ActionForm {

	private static final long serialVersionUID = 8503604739933630825L;

	private String formatId;

	private String formatName;

	private String note;

	public String getFormatId() {
		return formatId;
	}

	public void setFormatId(String formatId) {
		this.formatId = formatId;
	}

	public String getFormatName() {
		return formatName;
	}

	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
