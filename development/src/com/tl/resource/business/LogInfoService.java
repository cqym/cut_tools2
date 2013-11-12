package com.tl.resource.business;

import java.util.List;

import com.tl.common.util.PageInfo;
import com.tl.resource.business.dto.LogInfoDto;

public interface LogInfoService {

	public void commitLog(LogInfoDto logInfoDto) throws Exception;

	public void deleteLog(String logId) throws Exception;

	public List<LogInfoDto> getLogInfoWithPage(PageInfo pageInfo)
			throws Exception;

}
