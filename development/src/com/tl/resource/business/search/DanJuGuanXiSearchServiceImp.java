package com.tl.resource.business.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.TDeliveryInforDAO;
import com.tl.resource.dao.TOrderInforDAO;
import com.tl.resource.dao.TOutStockInforDAO;
import com.tl.resource.dao.TProductArrivalInforDAO;
import com.tl.resource.dao.TQuotationInforDAO;
import com.tl.resource.dao.pojo.TContractInfor;
import com.tl.resource.dao.pojo.TContractInforExample;
import com.tl.resource.dao.pojo.TDeliveryInfor;
import com.tl.resource.dao.pojo.TDeliveryInforExample;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TOrderInforExample;
import com.tl.resource.dao.pojo.TOutStockInfor;
import com.tl.resource.dao.pojo.TOutStockInforExample;
import com.tl.resource.dao.pojo.TProductArrivalInfor;
import com.tl.resource.dao.pojo.TProductArrivalInforExample;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TQuotationInforExample;

public class DanJuGuanXiSearchServiceImp implements DanJuGuanXiSearchService {
  public static final String DAN_JU_TYPE_QUO = "quo";

  public static final String DAN_JU_TYPE_CON = "con";

  public static final String DAN_JU_TYPE_ORDER = "order";

  public static final String DAN_JU_TYPE_OUT = "out";

  public static final String DAN_JU_TYPE_DELIVERY = "del";

  public static final String DAN_JU_TYPE_ARRIVAL = "arri";

  private TQuotationInforDAO quotationInforDAO;

  private TContractInforDAO contractInforDAO;

  private TOrderInforDAO orderInforDAO;

  private TOutStockInforDAO outStockInforDAO;

  private TDeliveryInforDAO deliveryInforDAO;

  private TProductArrivalInforDAO productArrivalInforDAO;

  @Override
  public Map<String, List<Object>> findDanJuListByCode(String code) {
    Map<String, List<Object>> result = new HashMap<String, List<Object>>();
    List quoList = getQuotationInforByCode(code);
    TContractInfor contractInfor = null;
    if (quoList.size() > 0) {//当前输入的是报价单号
      List<Object> newQuoList = new ArrayList<Object>();
      newQuoList.addAll(quoList);
      for (Iterator iterator = quoList.iterator(); iterator.hasNext();) {
        TQuotationInfor quoPo = (TQuotationInfor) iterator.next();
        if (quoPo.getContractInforId() != null) {
          contractInfor = contractInforDAO.selectByPrimaryKey(quoPo.getContractInforId());//如果已生成合同，则获取合同
        }
        if (quoPo.getImpToQuoCode() != null) {//如果有对应转正，或者对应预订报价单
          String[] quoIds = quoPo.getImpToQuoCode().split("\\,");
          for (int i = 0; i < quoIds.length; i++) {
            TQuotationInfor quoTemp = quotationInforDAO.selectByPrimaryKey(quoIds[i]);
            if (quoTemp != null) {
              newQuoList.add(quoTemp);
              if (quoTemp.getContractInforId() != null) {
                contractInfor = contractInforDAO.selectByPrimaryKey(quoTemp.getContractInforId());//如果已生成合同，则获取合同
              }
            }
          }
        }
        result.put(DAN_JU_TYPE_QUO, newQuoList);//此处报价单应该是所有与当前报价单有关联的报价单，并且包括当前报价单
      }
    } else {
      List conList = getContractInforByCode(code);
      if (conList.size() > 0) {//如果当前输入的是合同编号
        contractInfor = ((TContractInfor) (conList.get(0)));
      } else {
        List<TOrderInfor> orderList = getOrderInforByCode(code);
        if (orderList.size() > 0) {//如果code是订单编号
          List<TContractInfor> contractList = getContractInforByCode(orderList.get(0).getContractCode());
          if (contractList.size() > 0) {
            contractInfor = contractList.get(0);
          }
        } else {
          List<TOutStockInfor> outList = getOutStockByCode(code);
          if (outList.size() > 0) {//如果code是出库单号
            TOutStockInfor outInfor = outList.get(0);
            List<TContractInfor> contrList = getContractInforByCode(outInfor.getContractCode());
            if (contrList.size() > 0) {
              contractInfor = contrList.get(0);
            }
          } else {
            List<TDeliveryInfor> delList = getDeliveryByCode(code);
            if (delList.size() > 0) {//如果code是交货单编号
              List<TContractInfor> contrList = getContractInforByCode(delList.get(0).getContractCode());
              if (contrList.size() > 0) {
                contractInfor = contrList.get(0);
              }
            } else {
              List<TProductArrivalInfor> arrList = getArrivalByCode(code);
              if (arrList.size() > 0) {//如果code是入库单号
                List<TContractInfor> contrList = getContractInforByCode(arrList.get(0).getContractCode());
                if (contrList.size() > 0) {
                  contractInfor = contrList.get(0);
                }
              } else {
                throw new RuntimeException("在系统中未找到编号为【" + code + "】的单据。");
              }
            }
          }
        }
      }
    }

    String contractId = "1tm";
    String contractCode = "1tm";
    if (contractInfor != null) {
      contractCode = contractInfor.getContractCode();
      contractId = contractInfor.getId();
    }

    TQuotationInforExample quoExample = new TQuotationInforExample();
    quoExample.createCriteria().andContractInforIdEqualTo(contractId);
    quoList = quotationInforDAO.selectByExample(quoExample);
    List<Object> currBjYdbjs = result.get(DAN_JU_TYPE_QUO);
    if (currBjYdbjs != null && currBjYdbjs.size() > 0) {
      ArrayList<TQuotationInfor> newQuoList = new ArrayList<TQuotationInfor>();
      for (Iterator iterator2 = currBjYdbjs.iterator(); iterator2.hasNext();) {
        TQuotationInfor temp = (TQuotationInfor) iterator2.next();
        boolean hasQuo = false;
        for (Iterator iterator = quoList.iterator(); iterator.hasNext();) {
          TQuotationInfor htBj = (TQuotationInfor) iterator.next();
          if (htBj.getQuotationCode().equals(temp.getQuotationCode())) {
            hasQuo = true;
            break;
          }
        }
        if (!hasQuo) {
          newQuoList.add(temp);
        }
      }
      quoList.addAll(newQuoList);
    }
    result.put(DAN_JU_TYPE_QUO, quoList);

    ArrayList<String> quoCodeList = new ArrayList<String>();
    ArrayList<String> contractIdList = new ArrayList<String>();
    for (Iterator iterator = quoList.iterator(); iterator.hasNext();) {
      TQuotationInfor quo = (TQuotationInfor) iterator.next();
      quoCodeList.add(quo.getQuotationCode());
      contractIdList.add(quo.getContractInforId() == null ? "1te" : quo.getContractInforId());
    }

    TContractInforExample conExample = new TContractInforExample();
    conExample.createCriteria().andIdIn(contractIdList);
    List conList = contractInforDAO.selectByExample(conExample);
    result.put(DAN_JU_TYPE_CON, conList);

    TOrderInforExample orderExample = new TOrderInforExample();
    orderExample.createCriteria().andContractCodeEqualTo(contractCode);
    orderExample.or(orderExample.createCriteria().andQuotationCodeIn(quoCodeList));
    List orderList = orderInforDAO.selectByExample(orderExample);
    result.put(DAN_JU_TYPE_ORDER, orderList);

    TOutStockInforExample outExample = new TOutStockInforExample();
    outExample.createCriteria().andContractCodeEqualTo(contractCode);
    outExample.or(outExample.createCriteria().andQuotationCodeIn(quoCodeList));
    List outList = outStockInforDAO.selectByExample(outExample);
    result.put(DAN_JU_TYPE_OUT, outList);

    TDeliveryInforExample deliveryExample = new TDeliveryInforExample();
    deliveryExample.createCriteria().andContractCodeEqualTo(contractCode);
    deliveryExample.or(deliveryExample.createCriteria().andQuotationCodeIn(quoCodeList));
    List deliList = deliveryInforDAO.selectByExample(deliveryExample);
    result.put(DAN_JU_TYPE_DELIVERY, deliList);

    TProductArrivalInforExample arrivalExample = new TProductArrivalInforExample();
    arrivalExample.createCriteria().andContractCodeEqualTo(contractCode);
    arrivalExample.or(arrivalExample.createCriteria().andQuotationCodeIn(quoCodeList));
    List arriList = productArrivalInforDAO.selectByExample(arrivalExample);
    result.put(DAN_JU_TYPE_ARRIVAL, arriList);
    return result;
  }

  private List getArrivalByCode(String code) {
    TProductArrivalInforExample arrExample = new TProductArrivalInforExample();
    arrExample.createCriteria().andArrivalCodeEqualTo(code);
    List arrList = productArrivalInforDAO.selectByExample(arrExample);
    return arrList;
  }

  private List getDeliveryByCode(String code) {
    TDeliveryInforExample DelExample = new TDeliveryInforExample();
    DelExample.createCriteria().andDeliveryCodeEqualTo(code);
    List delList = deliveryInforDAO.selectByExample(DelExample);
    return delList;
  }

  private List getOutStockByCode(String code) {
    TOutStockInforExample outExample = new TOutStockInforExample();
    outExample.createCriteria().andOutStockCodeEqualTo(code);
    List outList = outStockInforDAO.selectByExample(outExample);
    return outList;
  }

  private List getOrderInforByCode(String code) {
    TOrderInforExample orderExample = new TOrderInforExample();
    orderExample.createCriteria().andOrderCodeEqualTo(code);
    List orderList = orderInforDAO.selectByExample(orderExample);
    return orderList;
  }

  private List getContractInforByCode(String code) {
    TContractInforExample conExample = new TContractInforExample();
    conExample.createCriteria().andContractCodeEqualTo(code);
    List conList = contractInforDAO.selectByExample(conExample);
    return conList;
  }

  private List getQuotationInforByCode(String code) {
    TQuotationInforExample quoExample = new TQuotationInforExample();
    quoExample.createCriteria().andQuotationCodeEqualTo(code);
    List quoList = quotationInforDAO.selectByExample(quoExample);
    return quoList;
  }

  public TQuotationInforDAO getQuotationInforDAO() {
    return quotationInforDAO;
  }

  public void setQuotationInforDAO(TQuotationInforDAO quotationInforDAO) {
    this.quotationInforDAO = quotationInforDAO;
  }

  public TContractInforDAO getContractInforDAO() {
    return contractInforDAO;
  }

  public void setContractInforDAO(TContractInforDAO contractInforDAO) {
    this.contractInforDAO = contractInforDAO;
  }

  public TOrderInforDAO getOrderInforDAO() {
    return orderInforDAO;
  }

  public void setOrderInforDAO(TOrderInforDAO orderInforDAO) {
    this.orderInforDAO = orderInforDAO;
  }

  public TOutStockInforDAO getOutStockInforDAO() {
    return outStockInforDAO;
  }

  public void setOutStockInforDAO(TOutStockInforDAO outStockInforDAO) {
    this.outStockInforDAO = outStockInforDAO;
  }

  public TDeliveryInforDAO getDeliveryInforDAO() {
    return deliveryInforDAO;
  }

  public void setDeliveryInforDAO(TDeliveryInforDAO deliveryInforDAO) {
    this.deliveryInforDAO = deliveryInforDAO;
  }

  public TProductArrivalInforDAO getProductArrivalInforDAO() {
    return productArrivalInforDAO;
  }

  public void setProductArrivalInforDAO(TProductArrivalInforDAO productArrivalInforDAO) {
    this.productArrivalInforDAO = productArrivalInforDAO;
  }

}
