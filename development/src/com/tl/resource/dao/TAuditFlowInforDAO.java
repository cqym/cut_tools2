package com.tl.resource.dao;

import com.tl.resource.dao.pojo.TAuditFlowInfor;
import com.tl.resource.dao.pojo.TAuditFlowInforExample;
import java.util.List;

public interface TAuditFlowInforDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_flow_infor
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    int countByExample(TAuditFlowInforExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_flow_infor
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    int deleteByExample(TAuditFlowInforExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_flow_infor
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_flow_infor
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    void insert(TAuditFlowInfor record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_flow_infor
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    void insertSelective(TAuditFlowInfor record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_flow_infor
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    List selectByExample(TAuditFlowInforExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_flow_infor
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    TAuditFlowInfor selectByPrimaryKey(String id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_flow_infor
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    int updateByExampleSelective(TAuditFlowInfor record, TAuditFlowInforExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_flow_infor
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    int updateByExample(TAuditFlowInfor record, TAuditFlowInforExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_flow_infor
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    int updateByPrimaryKeySelective(TAuditFlowInfor record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_flow_infor
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    int updateByPrimaryKey(TAuditFlowInfor record);
    
    public List<com.tl.resource.audit.dto.TAuditFlowInforDto> getAuditInfor(String auditTypeId);
}