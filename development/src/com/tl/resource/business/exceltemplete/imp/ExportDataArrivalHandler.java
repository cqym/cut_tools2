package com.tl.resource.business.exceltemplete.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tl.common.context.SystemInstance;
import com.tl.resource.business.dto.ProductArrivalDetailDto;
import com.tl.resource.business.exceltemplete.IExportDataBusinessHandler;
import com.tl.resource.dao.TCompanyInforDAO;
import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.TOrderDetailDAO;
import com.tl.resource.dao.TProductArrivalDetailDAO;
import com.tl.resource.dao.TProductArrivalInforDAO;
import com.tl.resource.dao.TQuotationInforDAO;
import com.tl.resource.dao.TQuotationProductDetailDAO;
import com.tl.resource.dao.pojo.TCompanyInfor;
import com.tl.resource.dao.pojo.TContractInfor;
import com.tl.resource.dao.pojo.TOrderDetail;
import com.tl.resource.dao.pojo.TProductArrivalDetailExample;
import com.tl.resource.dao.pojo.TProductArrivalInfor;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TQuotationInforExample;
import com.tl.resource.dao.pojo.TQuotationProductDetail;

public class ExportDataArrivalHandler implements IExportDataBusinessHandler {
  private TProductArrivalInforDAO arrInfoDao = (TProductArrivalInforDAO) SystemInstance.getInstance().getBean("TProductArrivalInforDAO");

  private TProductArrivalDetailDAO arrProductDao = (TProductArrivalDetailDAO) SystemInstance.getInstance().getBean("TProductArrivalDetail");

  private TCompanyInforDAO tcompanyInforDAO = (TCompanyInforDAO) SystemInstance.getInstance().getBean("TCompanyInforDAO");

  private TContractInforDAO contractInforDAO = (TContractInforDAO) SystemInstance.getInstance().getBean("contractInforDAO");

  private TQuotationInforDAO quoInfoDAO = (TQuotationInforDAO) SystemInstance.getInstance().getBean("TQuotationInfoDAO");

  private TOrderDetailDAO orderDetailDao = (TOrderDetailDAO) SystemInstance.getInstance().getBean("TOrderDetailDAOImpl");

  private TQuotationProductDetailDAO quotationProductDetailDAO = (TQuotationProductDetailDAO) SystemInstance.getInstance().getBean(
    "TQuotationProductDetailDAO");

  private TProductArrivalInfor arrivalInfor;

  @Override
  public Map<String, Object> getBusinessData(String id) {
    Map<String, Object> model = new HashMap<String, Object>();
    arrivalInfor = arrInfoDao.selectByPrimaryKey(id);
    Integer arrivalType = arrivalInfor.getArrivalType();
    String arrTypeText = "";
    if (arrivalType != null) {
      if (arrivalType == 1 || arrivalType == 3) {
        arrTypeText = "合同";
      } else if (arrivalType == 2 || arrivalType == 4) {
        arrTypeText = "储备";
      } else if (arrivalType == 5) {
        arrTypeText = "预订";
      } else if (arrivalType == 6) {
        arrTypeText = "试刀";
      }
    } else {
      arrTypeText = "直接";
    }
    model.put("arrivalTypeName", arrTypeText);
    model.put("arrivalInfor", arrivalInfor);
    List<ProductArrivalDetailDto> detailList = new ArrayList<ProductArrivalDetailDto>();
    if (arrivalType == 0) {
      TProductArrivalDetailExample detailExp = new TProductArrivalDetailExample();
      detailExp.createCriteria().andProductArrivalInforIdEqualTo(id);
      detailList = arrProductDao.selectByExample(detailExp);
    } else {
      detailList = arrProductDao.getArrivalDetail(id);
    }
    for (Iterator iterator = detailList.iterator(); iterator.hasNext();) {
      ProductArrivalDetailDto productArrivalDetailDto = (ProductArrivalDetailDto) iterator.next();

      TOrderDetail orderDetail = orderDetailDao.selectByPrimaryKey(productArrivalDetailDto.getOrderDetailId());
      if (orderDetail != null) {
        productArrivalDetailDto.setOrderPrice(orderDetail.getPrice());
        String productId = orderDetail.getContractProductDetailId();
        if (productId != null && productId.length() > 32) {
          productId = productId.substring(0, 32);
        }

        TQuotationProductDetail qpdetailpo = quotationProductDetailDAO.selectByPrimaryKey(productId);
        if (qpdetailpo != null) {
          productArrivalDetailDto.setSalePrice(qpdetailpo.getNetPrice());
        }
      }
    }
    model.put("detailList", detailList);
    if (arrivalType == 1 || arrivalType == 3) {
      TContractInfor contractInfor = contractInforDAO.getContractByCode(arrivalInfor.getContractCode());
      TCompanyInfor comInfo = tcompanyInforDAO.getCompanyByName(contractInfor.getSellerName());
      model.put("companyInor", comInfo);
    } else if (arrivalType == 5 || arrivalType == 6) {
      TQuotationInforExample example = new TQuotationInforExample();
      example.createCriteria().andQuotationCodeEqualTo(arrivalInfor.getQuotationCode());
      List quoList = quoInfoDAO.selectByExample(example);//(arrivalInfor.getContractCode());
      if (quoList != null && quoList.size() > 0) {
        TQuotationInfor arri = (TQuotationInfor) quoList.get(0);
        TCompanyInfor comInfo = tcompanyInforDAO.getCompanyByName(arri.getSellerName());
        model.put("companyInor", comInfo);
      }

    }

    return model;
  }

  @Override
  public String getMainCode() {
    // TODO Auto-generated method stub
    return arrivalInfor.getArrivalCode();
  }

}
