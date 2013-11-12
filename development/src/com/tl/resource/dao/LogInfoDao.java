package com.tl.resource.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.tl.common.util.PageInfo;
import com.tl.resource.dao.pojo.LogInfo;

public class LogInfoDao extends SqlMapClientDaoSupport {

	@SuppressWarnings("unchecked")
	public List<LogInfo> getLogInfoWithPage(PageInfo pageInfo) throws Exception {

		return this.getSqlMapClientTemplate().queryForList(
				"getLogInfoWithPage", pageInfo);
	}

	public int getLogInfoCount() throws Exception {

		return Integer.parseInt(this.getSqlMapClientTemplate().queryForObject(
				"getLogInfoCount").toString());
	}

	public Object insertLogInfo(LogInfo logInfo) throws Exception {

		return this.getSqlMapClientTemplate().insert("insertLogInfo", logInfo);
	}

}
