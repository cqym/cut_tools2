package com.tl.common.interceptor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;

import com.tl.common.filter.HttpSessionFilter;
import com.tl.common.util.GenerateSerial;
import com.tl.resource.business.LogInfoService;
import com.tl.resource.business.dto.LogInfoDto;

@SuppressWarnings("unchecked")
public class LoggingInterceptor {

	private LogInfoService logInfoService;
	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private String dto = "";

	/**
	 * 保存日志信息
	 * 
	 * @param joinpoint
	 * @return
	 */
	public void writeLog(JoinPoint joinPoint) {

		boolean methodFlag = true;
		String serviceName = "";
		int logType = 0;

		LogInfoDto logInfoDto = new LogInfoDto();
		HttpSession session = HttpSessionFilter.sessionLocal.get();

		String ip = (String) session.getAttribute("userIp");
		String logUser = (String) session.getAttribute("account");

		logInfoDto.setLogId(GenerateSerial.getUUID());
		logInfoDto.setLogDate(new Timestamp(new Date().getTime()));

		if (!"".equals(ip))
			logInfoDto.setIp(ip);
		if (!"".equals(logUser))
			logInfoDto.setLogUser(logUser);

		try {
			StringBuffer content = new StringBuffer("");
			StringBuffer detail = new StringBuffer("");
			Object[] dtoType = joinPoint.getArgs();
			String methodName = joinPoint.getSignature().getName();

			if (!"".equals(methodName)) {
				if (methodName.startsWith("insert")) {
					logType = 1;
					serviceName = methodName.replace("insert", "");
				} else if (methodName.startsWith("update")) {
					logType = 2;
					serviceName = methodName.replace("update", "");
				} else if (methodName.startsWith("delete")) {
					logType = 3;
					serviceName = methodName.replace("delete", "");
				} else
					methodFlag = false;
			}
			content.append(getOprateMethod(logType));

			if (methodFlag) {
				for (int i = 0; i < dtoType.length; i++) {
					if (null != dtoType[i]) {
						String simpleName = dtoType[i].getClass()
								.getSimpleName().replace("Dto", "");
						if (!"".equals(serviceName)
								&& serviceName.equalsIgnoreCase(simpleName)) {
							if (!"".equals(dto))
								dto += ",";
							dto += dtoType[i];
						}
					}
				}
			}
			content.append(dto);

			logInfoDto.setLogType(logType);
			logInfoDto.setContent(content.toString());

			detail.append(dateFormat.format(logInfoDto.getLogDate()) + " ");
			detail.append(logInfoDto.getLogUser() + " ");
			detail.append(logInfoDto.getContent());

			logInfoDto.setNote(detail.toString());

			if (methodFlag)
				logInfoService.commitLog(logInfoDto);

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private String getOprateMethod(int type) {
		String oprateName = "";

		switch (type) {
		case 1:
			oprateName = "添加";
			break;
		case 2:
			oprateName = "修改";
			break;

		case 3:
			oprateName = "删除";
			break;
		}

		return oprateName;
	}

	public LogInfoService getLogInfoService() {
		return logInfoService;
	}

	public void setLogInfoService(LogInfoService logInfoService) {
		this.logInfoService = logInfoService;
	}

}
