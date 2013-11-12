package com.tl.resource.dao;

import com.tl.resource.dao.pojo.TProductSource;
import com.tl.resource.dao.pojo.TProductSourceExample;
import java.util.List;

public interface TProductSourceDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_product_source
     *
     * @ibatorgenerated Wed Nov 25 18:10:59 CST 2009
     */
    int countByExample(TProductSourceExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_product_source
     *
     * @ibatorgenerated Wed Nov 25 18:10:59 CST 2009
     */
    int deleteByExample(TProductSourceExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_product_source
     *
     * @ibatorgenerated Wed Nov 25 18:10:59 CST 2009
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_product_source
     *
     * @ibatorgenerated Wed Nov 25 18:10:59 CST 2009
     */
    void insert(TProductSource record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_product_source
     *
     * @ibatorgenerated Wed Nov 25 18:10:59 CST 2009
     */
    void insertSelective(TProductSource record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_product_source
     *
     * @ibatorgenerated Wed Nov 25 18:10:59 CST 2009
     */
    List selectByExample(TProductSourceExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_product_source
     *
     * @ibatorgenerated Wed Nov 25 18:10:59 CST 2009
     */
    TProductSource selectByPrimaryKey(String id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_product_source
     *
     * @ibatorgenerated Wed Nov 25 18:10:59 CST 2009
     */
    int updateByExampleSelective(TProductSource record, TProductSourceExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_product_source
     *
     * @ibatorgenerated Wed Nov 25 18:10:59 CST 2009
     */
    int updateByExample(TProductSource record, TProductSourceExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_product_source
     *
     * @ibatorgenerated Wed Nov 25 18:10:59 CST 2009
     */
    int updateByPrimaryKeySelective(TProductSource record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_product_source
     *
     * @ibatorgenerated Wed Nov 25 18:10:59 CST 2009
     */
    int updateByPrimaryKey(TProductSource record);
    
    /**
     * 根据名称获取产品来源
     * @param name 来源名称
     * @return
     */
    public List<TProductSource> getProSourceByName(String name);
    
    /**
     * 获取全部来源(ftl 2010-01-20)
     * @return
     */
    public List<TProductSource> getProSourceByAll();
}