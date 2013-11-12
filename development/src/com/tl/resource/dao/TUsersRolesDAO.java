package com.tl.resource.dao;

import com.tl.resource.dao.pojo.TUsersRolesExample;
import com.tl.resource.dao.pojo.TUsersRolesKey;
import java.util.List;

public interface TUsersRolesDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_users_roles
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	int countByExample(TUsersRolesExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_users_roles
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	int deleteByExample(TUsersRolesExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_users_roles
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	int deleteByPrimaryKey(TUsersRolesKey key);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_users_roles
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	void insert(TUsersRolesKey record);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_users_roles
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	void insertSelective(TUsersRolesKey record);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_users_roles
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	List selectByExample(TUsersRolesExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_users_roles
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	int updateByExampleSelective(TUsersRolesKey record,
			TUsersRolesExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_users_roles
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	int updateByExample(TUsersRolesKey record, TUsersRolesExample example);
}