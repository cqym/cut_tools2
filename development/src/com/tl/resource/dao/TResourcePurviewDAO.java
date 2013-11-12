package com.tl.resource.dao;

import java.util.List;

import com.tl.resource.dao.pojo.TResourcePurview;
import com.tl.resource.dao.pojo.TResourcePurviewExample;

public interface TResourcePurviewDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_resource_purview
     *
     * @ibatorgenerated Fri Dec 25 14:16:06 CST 2009
     */
    int countByExample(TResourcePurviewExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_resource_purview
     *
     * @ibatorgenerated Fri Dec 25 14:16:06 CST 2009
     */
    int deleteByExample(TResourcePurviewExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_resource_purview
     *
     * @ibatorgenerated Fri Dec 25 14:16:06 CST 2009
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_resource_purview
     *
     * @ibatorgenerated Fri Dec 25 14:16:06 CST 2009
     */
    void insert(TResourcePurview record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_resource_purview
     *
     * @ibatorgenerated Fri Dec 25 14:16:06 CST 2009
     */
    void insertSelective(TResourcePurview record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_resource_purview
     *
     * @ibatorgenerated Fri Dec 25 14:16:06 CST 2009
     */
    List selectByExample(TResourcePurviewExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_resource_purview
     *
     * @ibatorgenerated Fri Dec 25 14:16:06 CST 2009
     */
    TResourcePurview selectByPrimaryKey(String id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_resource_purview
     *
     * @ibatorgenerated Fri Dec 25 14:16:06 CST 2009
     */
    int updateByExampleSelective(TResourcePurview record, TResourcePurviewExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_resource_purview
     *
     * @ibatorgenerated Fri Dec 25 14:16:06 CST 2009
     */
    int updateByExample(TResourcePurview record, TResourcePurviewExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_resource_purview
     *
     * @ibatorgenerated Fri Dec 25 14:16:06 CST 2009
     */
    int updateByPrimaryKeySelective(TResourcePurview record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_resource_purview
     *
     * @ibatorgenerated Fri Dec 25 14:16:06 CST 2009
     */
    int updateByPrimaryKey(TResourcePurview record);
    
    
}