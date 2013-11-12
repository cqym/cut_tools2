package com.tl.resource.web.audit;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.resource.audit.IAuditFlowDefinedService;
import com.tl.resource.audit.dto.TAuditFlowDetailDto;
import com.tl.resource.audit.dto.TAuditFlowInforDto;

public class AuditFlowDefinedManageAction extends DispatchAction{
		IAuditFlowDefinedService auditFlowDefinedService;
		public ActionForward updateAuditFlowInfor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();

			String inforsJson = request.getParameter("infors");
			//System.out.println(inforsJson);
			JSONArray json = JSONArray.fromObject(inforsJson);
			Object[] arr = json.toArray();
			for (int i = 0; i < arr.length; i++) {
				JSONObject jsonobj = JSONObject.fromObject(arr[i]);
				TAuditFlowInforDto dto = (TAuditFlowInforDto) JSONObject.toBean(jsonobj,TAuditFlowInforDto.class);
				if(dto.getId() == null || "".equals(dto.getId())){
					auditFlowDefinedService.createAuditInfor(dto);
				}else{
					auditFlowDefinedService.updateAuditInfor(dto); 	
				}
			}
			
			out.println(true);
			out.flush();
			out.close();
			return null;
		}
		public ActionForward updateAuditFlowDetailInfor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();

			String inforsJson = request.getParameter("infors");
			//System.out.println(inforsJson);
			JSONArray json = JSONArray.fromObject(inforsJson);
			Object[] arr = json.toArray();
			for (int i = 0; i < arr.length; i++) {
				JSONObject jsonobj = JSONObject.fromObject(arr[i]);
				TAuditFlowDetailDto dto = (TAuditFlowDetailDto) JSONObject.toBean(jsonobj,TAuditFlowDetailDto.class);
				if(dto.getId() == null || "".equals(dto.getId())){
					auditFlowDefinedService.createFlowDetail(dto);
				}else{
					auditFlowDefinedService.updateFlowDetail(dto); 	
				}
			}
			
			out.println(true);
			out.flush();
			out.close();
			return null;
		}
		public ActionForward deleteAuditFlowDetailInfor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();

			String inforsJson = request.getParameter("ids");
			System.out.println(inforsJson);
			JSONArray json = JSONArray.fromObject(inforsJson);
			Object[] arr = json.toArray();
			for (int i = 0; i < arr.length; i++) {
				//JSONObject jsonobj = JSONObject.fromObject(arr[i]);
				//String id = (String) JSONObject.toBean(jsonobj,String.class);
				auditFlowDefinedService.deleteFlowDetail((String) arr[i]); 	
			}
			
			out.println(true);
			out.flush();
			out.close();
			return null;
		}
		public IAuditFlowDefinedService getAuditFlowDefinedService() {
			return auditFlowDefinedService;
		}
		public void setAuditFlowDefinedService(
				IAuditFlowDefinedService auditFlowDefinedService) {
			this.auditFlowDefinedService = auditFlowDefinedService;
		}
		
		
}
