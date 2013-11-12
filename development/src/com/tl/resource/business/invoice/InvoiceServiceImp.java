package com.tl.resource.business.invoice;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.InvoiceDetailDto;
import com.tl.resource.business.dto.InvoiceInforDto;
import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.TCustomersInforDAO;
import com.tl.resource.dao.TInvoiceDetailDAO;
import com.tl.resource.dao.TInvoiceInfoDAO;
import com.tl.resource.dao.TSuppliersInforDAO;
import com.tl.resource.dao.pojo.TContractInfor;
import com.tl.resource.dao.pojo.TCustomersInfor;
import com.tl.resource.dao.pojo.TCustomersInforExample;
import com.tl.resource.dao.pojo.TInvoiceDetail;
import com.tl.resource.dao.pojo.TInvoiceDetailExample;
import com.tl.resource.dao.pojo.TInvoiceInfo;
import com.tl.resource.dao.pojo.TInvoiceInfoExample;
import com.tl.resource.dao.pojo.TSuppliersInfor;

public class InvoiceServiceImp implements InvoiceService {
  private TContractInforDAO contractInforDAO;

  private TInvoiceDetailDAO invoiceDetailDAO;

  private TInvoiceInfoDAO invoiceInfoDAO;

  private TCustomersInforDAO customersInforDAO;

  private TSuppliersInforDAO suppliersInforDAO;

  @Override
  public void saveInvoiceInfor(InvoiceInforDto dto) {
    TInvoiceInfo po = new TInvoiceInfo();
    try {
      BeanUtils.copyProperties(po, dto);

      if (po != null && !"".equals(po.getId())) {
        invoiceInfoDAO.updateByPrimaryKey(po);
      } else {
        po.setId(GenerateSerial.getUUID());
        invoiceInfoDAO.insert(po);
      }
      List<InvoiceDetailDto> detail = dto.getInvoiceDetail();
      for (Iterator iterator = detail.iterator(); iterator.hasNext();) {
        InvoiceDetailDto invoiceDetailDto = (InvoiceDetailDto) iterator.next();
        TInvoiceDetail detailPo = new TInvoiceDetail();
        BeanUtils.copyProperties(detailPo, invoiceDetailDto);
        detailPo.setEditDate(new Date());
        detailPo.setAmount(invoiceDetailDto.getInvoiceAmount());
        if (StringUtils.isNotEmpty(detailPo.getId()) && !detailPo.getId().startsWith("temp")) {
          if (detailPo.getAmount().doubleValue() == 0) {
            invoiceDetailDAO.deleteByPrimaryKey(detailPo.getId());
          } else {
            invoiceDetailDAO.updateByPrimaryKey(detailPo);
          }
        } else {
          detailPo.setId(GenerateSerial.getUUID());
          detailPo.setInvoiceInfoId(po.getId());
          invoiceDetailDAO.insert(detailPo);
        }
      }
      if (dto.getInvoiceType() == 0) {
        contractInforDAO.checkSetContractOver(dto.getContractId());
        contractInforDAO.updateContractInvoiceMoney(dto.getContractId());
      }
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * invoiceType //0合同发票1订单发票
   */
  @Override
  public PaginationSupport viewInvoiceInfors(Map params, int startIndex, int pageSize) {
    if ("0".equals(params.get("invoiceType"))) {
      return invoiceDetailDAO.viewInvoiceDetail(params, startIndex, pageSize);
    }
    return invoiceDetailDAO.viewOrderInvoiceDetail(params, startIndex, pageSize);
  }

  public TInvoiceDetailDAO getInvoiceDetailDAO() {
    return invoiceDetailDAO;
  }

  public void setInvoiceDetailDAO(TInvoiceDetailDAO invoiceDetailDAO) {
    this.invoiceDetailDAO = invoiceDetailDAO;
  }

  public TInvoiceInfoDAO getInvoiceInfoDAO() {
    return invoiceInfoDAO;
  }

  public void setInvoiceInfoDAO(TInvoiceInfoDAO invoiceInfoDAO) {
    this.invoiceInfoDAO = invoiceInfoDAO;
  }

  public TCustomersInforDAO getCustomersInforDAO() {
    return customersInforDAO;
  }

  public void setCustomersInforDAO(TCustomersInforDAO customersInforDAO) {
    this.customersInforDAO = customersInforDAO;
  }

  @Override
  public InvoiceInforDto getInvoiceInfor(String contractId) {
    TInvoiceInfoExample example = new TInvoiceInfoExample();
    example.createCriteria().andContractIdEqualTo(contractId);
    List<TInvoiceInfo> list = invoiceInfoDAO.selectByExample(example);
    if (list == null || list.size() == 0)
      return null;
    InvoiceInforDto dto = new InvoiceInforDto();
    try {
      BeanUtils.copyProperties(dto, list.get(0));
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return dto;
  }

  @Override
  public TCustomersInfor getCustomerInforByCode(String code) {

    TCustomersInforExample example = new TCustomersInforExample();
    example.createCriteria().andCustomerCodeEqualTo(code);
    List<TCustomersInfor> list = customersInforDAO.selectByExample(example);
    if (list != null && list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  @Override
  public TCustomersInfor getSuppliersInforByCode(String code) {
    //		TSuppliersInforExample example = new TSuppliersInforExample();
    //		example.createCriteria().andSupplierCodeEqualTo(code);
    //		List<TSuppliersInfor> list = suppliersInforDAO.selectByExample(example);
    TSuppliersInfor tt = suppliersInforDAO.selectByPrimaryKey(code);
    if (tt != null) {
      TCustomersInfor ci = new TCustomersInfor();
      try {
        BeanUtils.copyProperties(ci, tt);
      } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      ci.setCustomerName(tt.getSupplierName());
      return ci;
    }
    return null;
  }

  public TSuppliersInforDAO getSuppliersInforDAO() {
    return suppliersInforDAO;
  }

  public void setSuppliersInforDAO(TSuppliersInforDAO suppliersInforDAO) {
    this.suppliersInforDAO = suppliersInforDAO;
  }

  public TContractInforDAO getContractInforDAO() {
    return contractInforDAO;
  }

  public void setContractInforDAO(TContractInforDAO contractInforDAO) {
    this.contractInforDAO = contractInforDAO;
  }

  @Override
  public String deleteInvoiceInfor(String id, String conDetailId) {
    //    TInvoiceDetailExample example = new TInvoiceDetailExample();
    //    example.createCriteria().andContractDetailIdEqualTo(conDetailId);
    //    int c = invoiceDetailDAO.countByExample(example);
    //if (c <= 1) {
    //  return "非复制行不能删除！";
    //    }
    TInvoiceDetail detail = invoiceDetailDAO.selectByPrimaryKey(id);
    TInvoiceInfo inv = invoiceInfoDAO.selectByPrimaryKey(detail.getInvoiceInfoId());

    invoiceDetailDAO.deleteByPrimaryKey(id);

    TInvoiceDetailExample example = new TInvoiceDetailExample();
    example.createCriteria().andInvoiceInfoIdEqualTo(inv.getId());
    int c = invoiceDetailDAO.countByExample(example);
    if (c == 0) {
      invoiceInfoDAO.deleteByPrimaryKey(inv.getId());
    }
    if (inv.getInvoiceType() == 0) {
      TContractInfor contract = contractInforDAO.selectByPrimaryKey(inv.getContractId());
      contractInforDAO.checkSetContractOver(contract.getId());
      contractInforDAO.updateContractInvoiceMoney(contract.getId());
    }
    return null;
  }

  @Override
  public String updateContractInvoiceStatus(String conId, int value) {
    // TODO Auto-generated method stub

    return contractInforDAO.updateContractInvoiceStatus(conId, value);
  }

}
