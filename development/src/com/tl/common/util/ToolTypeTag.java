package com.tl.common.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tl.common.context.SystemInstance;
//import com.tl.resource.business.ToolTypeService;
//import com.tl.resource.business.UserInfoService;
//import com.tl.resource.business.dto.ToolTypeDto;

/**
 * ¹¤¾ßtag
 * 
 * @author tyy
 * 
 */
@SuppressWarnings("serial")
public class ToolTypeTag extends TagSupport {

	private String typeId = null;

	//private ToolTypeService toolTypeService = null;

	@Override
	public int doStartTag() throws JspException {

		JspWriter out = pageContext.getOut();
		StringBuffer toolTypeName = getToolType(typeId);

		String str[] = toolTypeName.toString().split(";");

		try {
			out.print(reverse(str));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return EVAL_PAGE;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

//	public ToolTypeService getToolTypeService() {
//		return toolTypeService;
//	}

//	public void setToolTypeService(ToolTypeService toolTypeService) {
//		this.toolTypeService = toolTypeService;
//	}

	public StringBuffer getToolType(String typeId) {

		StringBuffer str = new StringBuffer();

//		ToolTypeDto toolTypeDto = null;
//
//		try {
//
//			toolTypeService = (ToolTypeService) SystemInstance.getInstance()
//					.getBean("toolTypeService");
//
//			toolTypeDto = toolTypeService.getToolTypeByTypeId(typeId);
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}

//		str.append(toolTypeDto.getTypeName());
//
//		if (toolTypeDto.getParentCode().equals("0")) {
//			return str;
//		} else {
//
//			str.append(";");
//
//			return str.append(getToolType(toolTypeDto.getParentCode())
//					.toString());
//
//		}
		return str;
	}

	public String reverse(String s[]) {
		int length = s.length;
		StringBuffer result = new StringBuffer(length);
		for (int i = length - 1; i >= 0; i--) {
			if (i == 0) {
				result.append(s[i]);
			} else {
				result.append(s[i] + "-->");
			}

		}
		return result.toString();
	}

}
