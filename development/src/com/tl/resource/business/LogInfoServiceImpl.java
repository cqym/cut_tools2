package com.tl.resource.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.tl.common.util.PageInfo;
import com.tl.resource.business.dto.LogInfoDto;
import com.tl.resource.dao.LogInfoDao;
import com.tl.resource.dao.pojo.LogInfo;

public class LogInfoServiceImpl implements LogInfoService {

	private LogInfoDao logInfoDao;

	public void deleteLog(String logId) throws Exception {

	}

	public List<LogInfoDto> getLogInfoWithPage(PageInfo pageInfo)
			throws Exception {

		return convertToDto(logInfoDao.getLogInfoWithPage(pageInfo));
	}

	public void commitLog(LogInfoDto logInfoDto) throws Exception {
		logInfoDao.insertLogInfo(convertToPojo(logInfoDto));
	}

	private List<LogInfoDto> convertToDto(List<LogInfo> origin)
			throws Exception {
		LogInfoDto logInfoDto;

		if (null == origin)
			return null;

		List<LogInfoDto> target = new ArrayList<LogInfoDto>();

		for (int i = 0; i < origin.size(); i++) {
			LogInfo logInfo = origin.get(i);
			logInfoDto = new LogInfoDto();
			BeanUtils.copyProperties(logInfoDto, logInfo);

			target.add(logInfoDto);
		}

		return target;
	}

	private LogInfo convertToPojo(LogInfoDto logInfoDto) throws Exception {
		LogInfo logInfo = new LogInfo();

		BeanUtils.copyProperties(logInfo, logInfoDto);

		return logInfo;
	}

	public LogInfoDao getLogInfoDao() {
		return logInfoDao;
	}

	public void setLogInfoDao(LogInfoDao logInfoDao) {
		this.logInfoDao = logInfoDao;
	}

}
