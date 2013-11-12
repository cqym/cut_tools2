package com.tl.resource.dao;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.dao.pojo.TInvoiceDetail;
import com.tl.resource.dao.pojo.TInvoiceDetailExample;
import java.util.List;
import java.util.Map;

public interface TInvoiceDetailDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_invoice_detail
     *
     * @ibatorgenerated Tue Dec 22 16:09:56 CST 2009
     */
    int countByExample(TInvoiceDetailExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_invoice_detail
     *
     * @ibatorgenerated Tue Dec 22 16:09:56 CST 2009
     */
    int deleteByExample(TInvoiceDetailExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_invoice_detail
     *
     * @ibatorgenerated Tue Dec 22 16:09:56 CST 2009
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_invoice_detail
     *
     * @ibatorgenerated Tue Dec 22 16:09:56 CST 2009
     */
    void insert(TInvoiceDetail record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_invoice_detail
     *
     * @ibatorgenerated Tue Dec 22 16:09:56 CST 2009
     */
    void insertSelective(TInvoiceDetail record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_invoice_detail
     *
     * @ibatorgenerated Tue Dec 22 16:09:56 CST 2009
     */
    List selectByExample(TInvoiceDetailExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_invoice_detail
     *
     * @ibatorgenerated Tue Dec 22 16:09:56 CST 2009
     */
    TInvoiceDetail selectByPrimaryKey(String id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_invoice_detail
     *
     * @ibatorgenerated Tue Dec 22 16:09:56 CST 2009
     */
    int updateByExampleSelective(TInvoiceDetail record, TInvoiceDetailExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_invoice_detail
     *
     * @ibatorgenerated Tue Dec 22 16:09:56 CST 2009
     */
    int updateByExample(TInvoiceDetail record, TInvoiceDetailExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_invoice_detail
     *
     * @ibatorgenerated Tue Dec 22 16:09:56 CST 2009
     */
    int updateByPrimaryKeySelective(TInvoiceDetail record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_invoice_detail
     *
     * @ibatorgenerated Tue Dec 22 16:09:56 CST 2009
     */
    int updateByPrimaryKey(TInvoiceDetail record);
    
    public PaginationSupport viewInvoiceDetail(Map params, int startIndex,
			int pageSize);
    public PaginationSupport viewOrderInvoiceDetail(Map params, int startIndex,
			int pageSize);
}