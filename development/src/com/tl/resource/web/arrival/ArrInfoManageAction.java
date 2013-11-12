package com.tl.resource.web.arrival;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.LoginInforUtil;
import com.tl.common.util.RegexUtils;
import com.tl.resource.business.arrival.ArrivalService;
import com.tl.resource.business.dto.ArrivalInforDto;
import com.tl.resource.business.dto.ProductArrivalDetailDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.manage.BillsCodeDefService;
import com.tl.resource.business.outStock.OutStockService;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TProductArrivalDetail;
import com.tl.resource.dao.pojo.TProductArrivalInfor;
import com.tl.resource.dao.pojo.TSuppliersInfor;

/**
 * 到货单管理Action
 * @author ftl
 *
 */
public class ArrInfoManageAction extends DispatchAction {
  //到货单管理Service
  private ArrivalService arrivalService;

  private BillsCodeDefService billsCodeDefService;

  private OutStockService outStockService;

  public ArrivalService getArrivalService() {
    return arrivalService;
  }

  public void setArrivalService(ArrivalService arrivalService) {
    this.arrivalService = arrivalService;
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String method = request.getParameter("method");

    if ("getArrInfoBySearch".equals(method)) {
      return getArrInfoBySearch(mapping, form, request, response);
    } else if ("addArrInfo".equals(method)) {
      return addArrInfo(mapping, form, request, response);
    } else if ("getArrDetail".equals(method)) {
      return getArrDetail(mapping, form, request, response);
    } else if ("modifyArrival".equals(method)) {
      return modifyArrival(mapping, form, request, response);
    } else if ("deleteArrPro".equals(method)) {
      return deleteArrPro(mapping, form, request, response);
    } else if ("deleteArrInfo".equals(method)) {
      return deleteArrInfo(mapping, form, request, response);
    } else if ("getArrInfoById".equals(method)) {
      return getArrInfoById(mapping, form, request, response);
    } else if ("arrivalSubmit".equals(method)) {
      return arrivalSubmit(mapping, form, request, response);
    } else if ("allArrival".equals(method)) {
      return allArrival(mapping, form, request, response);
    } else if ("getArrDetailByView".equals(method)) {
      return getArrDetailByView(mapping, form, request, response);
    } else if ("getDirectArrDetail".equals(method)) {
      return getDirectArrDetail(mapping, form, request, response);
    } else if ("getDirectArr".equals(method)) {
      return getDirectArr(mapping, form, request, response);
    } else if ("directArrAudit".equals(method)) {
      return directArrAudit(mapping, form, request, response);
    } else if ("invalidSubmit".equals(method)) {
      return invalidSubmit(mapping, form, request, response);
    } else {
      return null;
    }
  }

  //作废入库单
  private ActionForward invalidSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    response.setContentType("text/html;charset=utf-8");
    String arrInfoIds = request.getParameter("arrInfoId");
    UserDto userDto = null;
    if (LoginInforUtil.getLoginInfor(request) != null) {
      userDto = LoginInforUtil.getLoginInfor(request).getUser();
    }

    JSONArray array = JSONArray.fromObject(arrInfoIds);
    Iterator<String> iterator = array.iterator();
    int num = arrivalService.invalidArrivalInfo(iterator);
    String resultStr = "{success : false, msg : '入库单作废失败！'}";

    if (num > 0) {
      resultStr = "{success : true, msg : '入库单作废成功！'}";
    }

    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();

    return null;
  }

  //直接入库，确认入库
  private ActionForward directArrAudit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    response.setContentType("text/html;charset=utf-8");
    String arrInfoIds = request.getParameter("arrInfoId");
    UserDto userDto = null;
    if (LoginInforUtil.getLoginInfor(request) != null) {
      userDto = LoginInforUtil.getLoginInfor(request).getUser();
    }

    JSONArray array = JSONArray.fromObject(arrInfoIds);

    String resultStr = arrivalService.directArrivalAudit(array, userDto);

    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();

    return null;
  }

  //获取直接入库单
  private ActionForward getDirectArr(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    response.setContentType("text/html;charset=utf-8");
    String arrivalId = request.getParameter("arrivalId");

    TProductArrivalInfor arrivalInfo = arrivalService.getDirectArrInfoById(arrivalId);

    String resultJson = JSONObject.fromObject(arrivalInfo).toString();
    String resultStr = "{ success : true, data : " + resultJson + "}";
    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();
    return null;
  }

  //获取直接报价单详细
  private ActionForward getDirectArrDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    response.setContentType("text/html;charset=utf-8");
    String arrivalId = request.getParameter("arrivalId");

    List<TProductArrivalDetail> list = arrivalService.getDirectArrDetail(arrivalId);
    String jsonStr = JSONArray.fromObject(list).toString();

    String resultStr = "{orderProducts : " + jsonStr + "}";
    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();
    return null;
  }

  private ActionForward getArrDetailByView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    response.setContentType("text/html;charset=utf-8");
    String arrivalId = request.getParameter("arrivalId");

    List<ProductArrivalDetailDto> list = arrivalService.getArrivalDetailByView(arrivalId);
    String jsonStr = JSONArray.fromObject(list).toString();

    String resultStr = "{orderProducts : " + jsonStr + "}";
    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();
    return null;
  }

  //全部交清
  private ActionForward allArrival(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    response.setContentType("text/html;charset=utf-8");
    String orderIds = request.getParameter("orderId");
    String resultStr = "{success : true, msg : '确认入库全部交清提交成功！'}";

    JSONArray array = JSONArray.fromObject(orderIds);
    Iterator<String> iterator = array.iterator();
    while (iterator.hasNext()) {
      String orderId = iterator.next();
      TOrderInfor orderInfo = new TOrderInfor();
      orderInfo.setId(orderId);
      orderInfo.setStatus(5);
      try {
        arrivalService.updateOrderByIdSelective(orderInfo);
      } catch (Exception e) {
        e.printStackTrace();
        resultStr = "{success : false, msg : '确认入库全部交清提交失败！'}";
      }
    }
    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();
    return null;
  }

  //确认到货
  private ActionForward arrivalSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    response.setContentType("text/html;charset=utf-8");
    String arrInfoIds = request.getParameter("arrInfoId");
    UserDto userDto = null;
    if (LoginInforUtil.getLoginInfor(request) != null) {
      userDto = LoginInforUtil.getLoginInfor(request).getUser();
    }

    JSONArray array = JSONArray.fromObject(arrInfoIds);

    String resultStr = arrivalService.arrivalSubmit(array, userDto);

    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();

    return null;
  }

  private ActionForward getArrInfoById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setContentType("text/html;charset=utf-8");
    String arrivalId = request.getParameter("arrivalId");

    ArrivalInforDto arrivalInfo = arrivalService.getArrInfoWithOrderType(arrivalId);

    String resultJson = JSONObject.fromObject(arrivalInfo).toString();
    String resultStr = "{ success : true, data : " + resultJson + "}";
    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();
    return null;
  }

  private ActionForward deleteArrInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setContentType("text/html;charset=utf-8");
    String arrInfoIds = request.getParameter("arrInfoId");
    String resultStr = "{success : true, msg : '删除入库单成功！'}";

    try {
      JSONArray array = JSONArray.fromObject(arrInfoIds);
      Iterator<String> iterator = array.iterator();
      while (iterator.hasNext()) {
        String arrInfoId = iterator.next();
        if (arrInfoId != null && !"".equals(arrInfoId)) {
          List<ProductArrivalDetailDto> list = arrivalService.getArrivalDetail(arrInfoId);
          if (list != null && list.size() > 0) {
            arrivalService.deleteArrivalDetail(list);
          }

          TProductArrivalInfor arrInfo = arrivalService.getArrivalInfoById(arrInfoId);
          if (ArrivalInforDto.ARRIVAL_TYPE_CONTRACT.equals(arrInfo.getArrivalType())
            || ArrivalInforDto.ARRIVAL_TYPE_CONTRACT_SELF.equals(arrInfo.getArrivalType())) {
            arrivalService.updateContractDetailArrivalAmount(arrInfo.getContractCode());
          } else if (ArrivalInforDto.ARRIVAL_TYPE_SCHEDLE.equals(arrInfo.getArrivalType())
            || ArrivalInforDto.ARRIVAL_TYPE_SCHEDLE_SELF.equals(arrInfo.getArrivalType())
            || ArrivalInforDto.ARRIVAL_TYPE_TRY.equals(arrInfo.getArrivalType())
            || ArrivalInforDto.ARRIVAL_TYPE_TRY_SELF.equals(arrInfo.getArrivalType())) {
            arrivalService.updateQuotationDetailArrivalAmount(arrInfo.getQuotationCode());
          }
          if (ArrivalInforDto.ARRIVAL_TYPE_SCHEDLE.equals(arrInfo.getArrivalType())
            || ArrivalInforDto.ARRIVAL_TYPE_SCHEDLE_SELF.equals(arrInfo.getArrivalType())) {
            arrivalService.sycContractArrivalDetail(arrInfo.getQuotationCode());
          }
          arrivalService.deleteArrivalInfo(arrInfoId);

          outStockService.deleteOutStockInforDto(arrInfoId);//删除入库也删除对应的出库
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      resultStr = "{success : false, msg : '删除入库单失败！'}";
    }

    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();

    return null;
  }

  private ActionForward deleteArrPro(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setContentType("text/html;charset=utf-8");
    String arrProId = request.getParameter("arrProId");
    String resultStr = "{success : true, msg : '删除产品成功！'}";
    String arrInfoid = null;
    if (arrProId != null && !"".equals(arrProId)) {
      try {
        JSONArray jsonArray = JSONArray.fromObject(arrProId);
        if (jsonArray != null && jsonArray.size() > 0) {
          Iterator<JSONObject> iterator = jsonArray.iterator();
          while (iterator.hasNext()) {
            JSONObject idJsonObj = iterator.next();
            String detailId = idJsonObj.getString("id");
            if (arrInfoid == null) {
              TProductArrivalDetail detail = new TProductArrivalDetail();
              detail.setId(detailId);
              detail = arrivalService.getArrivalDetailById(detail);
              if (detail != null) {
                arrInfoid = detail.getProductArrivalInforId();
              }
            }
            arrivalService.deleteArrivalDetail(detailId);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        resultStr = "{success : false, msg : '删除产品失败！'}";
      }
      if (arrInfoid != null) {
        TProductArrivalInfor arrInfo = arrivalService.getArrivalInfoById(arrInfoid);
        if (ArrivalInforDto.ARRIVAL_TYPE_CONTRACT.equals(arrInfo.getArrivalType())
          || ArrivalInforDto.ARRIVAL_TYPE_CONTRACT_SELF.equals(arrInfo.getArrivalType())) {
          arrivalService.updateContractDetailArrivalAmount(arrInfo.getContractCode());
        } else if (ArrivalInforDto.ARRIVAL_TYPE_SCHEDLE.equals(arrInfo.getArrivalType())
          || ArrivalInforDto.ARRIVAL_TYPE_SCHEDLE_SELF.equals(arrInfo.getArrivalType())
          || ArrivalInforDto.ARRIVAL_TYPE_TRY.equals(arrInfo.getArrivalType())
          || ArrivalInforDto.ARRIVAL_TYPE_TRY_SELF.equals(arrInfo.getArrivalType())) {
          arrivalService.updateQuotationDetailArrivalAmount(arrInfo.getQuotationCode());
        }
        if (ArrivalInforDto.ARRIVAL_TYPE_SCHEDLE.equals(arrInfo.getArrivalType())
          || ArrivalInforDto.ARRIVAL_TYPE_SCHEDLE_SELF.equals(arrInfo.getArrivalType())) {
          arrivalService.sycContractArrivalDetail(arrInfo.getQuotationCode());
        }
      }
    } else {
      resultStr = "{success : false, msg : '请选择产品！'}";
    }
    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();
    return null;
  }

  //递归删除产品信息
  private void deleteArrivalPro(TProductArrivalDetail arrDetail) {
    List<TProductArrivalDetail> list = arrDetail.getChildren();
    if (list != null && list.size() > 0) {
      for (TProductArrivalDetail arrPro : list) {
        List<TProductArrivalDetail> children = arrPro.getChildren();
        if (children != null && children.size() > 0) {
          deleteArrivalPro(arrPro);
        }

        arrivalService.deleteArrivalDetail(arrPro.getId());
      }
    }
    arrivalService.deleteArrivalDetail(arrDetail.getId());
  }

  //修改到货单信息
  private ActionForward modifyArrival(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {

    response.setContentType("text/html;charset=utf-8");
    String arrForm = request.getParameter("arrForm");
    String arrProduct = request.getParameter("arrProduct");
    String resultStr = "";

    try {
      if (arrForm != null && !"".equals(arrForm)) {
        JSONObject jsonObject = JSONObject.fromObject(arrForm);
        TProductArrivalInfor arrInfo = (TProductArrivalInfor) JSONObject.toBean(jsonObject, TProductArrivalInfor.class);
        if ("".equals(arrInfo.getSupplierId()))
          arrInfo.setSupplierId(null);
        if ("".equals(arrInfo.getSupplierName()))
          arrInfo.setSupplierName(null);
        arrInfo.setUserName(null);
        arrInfo.setArrivalCode(null);
        arrInfo.setUpdateDate(new Date());
        arrivalService.updateArrivalInfo(arrInfo);

        if (arrProduct != null && !"".equals(arrProduct)) {
          JSONArray array = JSONArray.fromObject(arrProduct);
          if (array != null && array.size() > 0) {
            Iterator<JSONObject> iterator = array.iterator();
            while (iterator.hasNext()) {
              JSONObject proObj = iterator.next();

              TProductArrivalDetail arrDetail = (TProductArrivalDetail) JSONObject.toBean(proObj, TProductArrivalDetail.class);

              //如果产品已经在数据库中，则执行修改，否则进行新增操作
              if (arrivalService.getArrivalDetailById(arrDetail) != null) {
                arrDetail = arrivalService.getArrivalDetailById(arrDetail);
                if (proObj.has("arrivalAmount"))
                  arrDetail.setArrivalAmount(BigDecimal.valueOf(proObj.getDouble("arrivalAmount")));
                if (proObj.has("productMoney"))
                  arrDetail.setProductMoney(BigDecimal.valueOf(proObj.getDouble("productMoney")));
                if (proObj.has("memo"))
                  arrDetail.setMemo(proObj.getString("memo"));
                if (proObj.has("price"))
                  arrDetail.setPrice(new BigDecimal(proObj.getDouble("price")));
                if (proObj.has("memo"))
                  arrDetail.setProductPosition(proObj.getString("productPosition"));
                if (proObj.has("reserveCode"))
                  arrDetail.setReserveCode(proObj.getString("reserveCode"));
                arrivalService.updateArrivalDetail(arrDetail);
              } else {
                arrDetail.setOrderDetailId(arrDetail.getId());//订单明细ID
                arrDetail.setId(GenerateSerial.getUUID());//到货单明细ID
                arrDetail.setProductArrivalInforId(arrInfo.getId());//到货单ID

                arrivalService.insertArrProduct(arrDetail);
              }
            }
          }
        }

        // TProductArrivalInfor arrInfo = arrivalService.getArrivalInfoById(arrInfo.getId());
        if (ArrivalInforDto.ARRIVAL_TYPE_CONTRACT.equals(arrInfo.getArrivalType())
          || ArrivalInforDto.ARRIVAL_TYPE_CONTRACT_SELF.equals(arrInfo.getArrivalType())) {
          arrivalService.updateContractDetailArrivalAmount(arrInfo.getContractCode());
        } else if (ArrivalInforDto.ARRIVAL_TYPE_SCHEDLE.equals(arrInfo.getArrivalType())
          || ArrivalInforDto.ARRIVAL_TYPE_SCHEDLE_SELF.equals(arrInfo.getArrivalType())
          || ArrivalInforDto.ARRIVAL_TYPE_TRY.equals(arrInfo.getArrivalType())
          || ArrivalInforDto.ARRIVAL_TYPE_TRY_SELF.equals(arrInfo.getArrivalType())) {
          arrivalService.updateQuotationDetailArrivalAmount(arrInfo.getQuotationCode());
        }
        if (ArrivalInforDto.ARRIVAL_TYPE_SCHEDLE.equals(arrInfo.getArrivalType())
          || ArrivalInforDto.ARRIVAL_TYPE_SCHEDLE_SELF.equals(arrInfo.getArrivalType())) {
          arrivalService.sycContractArrivalDetail(arrInfo.getQuotationCode());
        }

        /*String orderId = arrInfo.getOrderInforId();
        Integer arrTotal = this.arrivalService.getCanArrivalPro(orderId);
        boolean flag = false;
        if(arrTotal == 0) {
        	flag = true;
        }*/

        resultStr = "{success : true, msg : '修改入库单成功！'}";
      }
    } catch (Exception e) {
      e.printStackTrace();
      resultStr = "{success : false, msg : '修改入库单失败！'}";
    }

    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();
    return null;
  }

  //递归修改产品
  private void updateChildrenProtools(JSONObject proObj, TProductArrivalDetail arrDetail) {
    JSONArray arr = proObj.getJSONArray("children");
    Iterator<JSONObject> iterator = arr.iterator();
    while (iterator.hasNext()) {
      JSONObject child = iterator.next();
      TProductArrivalDetail childPO = (TProductArrivalDetail) JSONObject.toBean(child, TProductArrivalDetail.class);

      JSONArray arr2 = null;
      try {
        arr2 = child.getJSONArray("children");
      } catch (Exception e) {
        childPO.setLeaf(1);
      }

      arrivalService.updateArrivalDetail(childPO);

      if (arr2 != null && arr2.size() > 0) {
        updateChildrenProtools(child, childPO);
      } else {
        //System.out.println("ddd:");
      }

    }
  }

  //获取到货产品详细
  private ActionForward getArrDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setContentType("text/html;charset=utf-8");
    String arrivalId = request.getParameter("arrivalId");

    List<ProductArrivalDetailDto> list = arrivalService.getArrivalDetailByView(arrivalId);
    for (ProductArrivalDetailDto dto : list) {
      BigDecimal hasAmount = dto.getHasArrivalAmount();
      hasAmount = hasAmount.subtract(dto.getArrivalAmount());
      dto.setHasArrivalAmount(hasAmount);
    }
    String jsonStr = JSONArray.fromObject(list).toString();

    String resultStr = "{orderProducts : " + jsonStr + "}";
    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();
    return null;
  }

  //添加到货信息
  private ActionForward addArrInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/html;charset=utf-8");
    String arrForm = request.getParameter("arrForm");
    String arrProduct = request.getParameter("arrProduct");
    String resultStr = "";

    UserDto userDto = null;
    if (LoginInforUtil.getLoginInfor(request) != null) {
      userDto = LoginInforUtil.getLoginInfor(request).getUser();
    }
    try {
      if (arrForm != null && !"".equals(arrForm)) {
        JSONObject jsonObject = JSONObject.fromObject(arrForm);

        TProductArrivalInfor arrInfo = (TProductArrivalInfor) JSONObject.toBean(jsonObject, TProductArrivalInfor.class);
        TSuppliersInfor suppliers = arrivalService.getSupplierById(arrInfo.getSupplierId());
        String arrInfoId = GenerateSerial.getUUID();
        arrInfo.setOrderInforId(arrInfo.getId());
        arrInfo.setId(arrInfoId);
        arrInfo.setUserId(userDto.getId());
        arrInfo.setUserName(userDto.getUserName());
        arrInfo.setEditDate(new Date());
        arrInfo.setStatus(0);
        String arrCode = "ZJRK";
        if (suppliers != null) {
          arrCode = suppliers.getSupplierCode();
        }
        arrInfo.setArrivalCode(billsCodeDefService.getBillCode(buildCodeType(arrInfo.getArrivalType()), arrCode, null, null));
        arrivalService.insertArrivalInfo(arrInfo);

        if (arrProduct != null && !"".equals(arrProduct)) {
          JSONArray array = JSONArray.fromObject(arrProduct);

          if (array != null && array.size() > 0) {
            Iterator<JSONObject> iterator = array.iterator();
            while (iterator.hasNext()) {
              JSONObject proObj = iterator.next();
              TProductArrivalDetail arrDetail = (TProductArrivalDetail) JSONObject.toBean(proObj, TProductArrivalDetail.class);
              arrDetail.setOrderDetailId(arrDetail.getId());//订单明细ID
              arrDetail.setId(GenerateSerial.getUUID());//到货单明细ID
              arrDetail.setProductArrivalInforId(arrInfo.getId());//到货单ID
              //arrDetail.setOrderAmount(BigDecimal.valueOf(proObj.getDouble(""))));
              arrivalService.insertArrProduct(arrDetail);
            }
          }
        }
        /*
        if (ArrivalInforDto.ARRIVAL_TYPE_CONTRACT.equals(arrInfo.getArrivalType())
          || ArrivalInforDto.ARRIVAL_TYPE_CONTRACT_SELF.equals(arrInfo.getArrivalType())) {
          arrivalService.updateContractDetailArrivalAmount(arrInfo.getContractCode());
        } else if (ArrivalInforDto.ARRIVAL_TYPE_SCHEDLE.equals(arrInfo.getArrivalType())
          || ArrivalInforDto.ARRIVAL_TYPE_SCHEDLE_SELF.equals(arrInfo.getArrivalType())
          || ArrivalInforDto.ARRIVAL_TYPE_TRY.equals(arrInfo.getArrivalType())
          || ArrivalInforDto.ARRIVAL_TYPE_TRY_SELF.equals(arrInfo.getArrivalType())) {
          arrivalService.updateQuotationDetailArrivalAmount(arrInfo.getQuotationCode());
        }
        */
        /*String orderId = arrInfo.getOrderInforId();
        Integer arrTotal = this.arrivalService.getCanArrivalPro(orderId);
        boolean flag = false;
        if(arrTotal == 0) {
        	flag = true;
        }*/
        resultStr = "{success : true, msg : '添加入库单成功！', id : '" + arrInfoId + "'}";
      }
    } catch (Exception e) {
      e.printStackTrace();
      resultStr = "{success : false, msg : '添加入库单失败！'}";
    }

    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();

    return null;
  }

  //迪归保存到货单详细信息
  private void addChildrenProtools(JSONObject proTools, TProductArrivalDetail arrDetail) {
    JSONArray arr = proTools.getJSONArray("children");
    Iterator<JSONObject> iterator = arr.iterator();
    while (iterator.hasNext()) {
      JSONObject child = iterator.next();

      TProductArrivalDetail childPO = (TProductArrivalDetail) JSONObject.toBean(child, TProductArrivalDetail.class);
      childPO.setOrderDetailId(childPO.getId());//订单明细ID
      childPO.setId(GenerateSerial.getUUID());//到货明细ID
      childPO.setParentToolsId(arrDetail.getId());//到货明细父ID
      childPO.setProductArrivalInforId(arrDetail.getProductArrivalInforId());//到货单ID

      JSONArray arr2 = null;
      try {
        arr2 = child.getJSONArray("children");
      } catch (Exception e) {
        childPO.setLeaf(1);
      }

      arrivalService.insertArrProduct(childPO);

      if (arr2 != null && arr2.size() > 0) {
        addChildrenProtools(child, childPO);
      } else {
        //System.out.println("ddd:");
      }
    }
  }

  //获取到货单信息
  private ActionForward getArrInfoBySearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {

    response.setContentType("text/html;charset=utf-8");
    String searchStr = request.getParameter("searchStr");
    String orderType = request.getParameter("orderType");
    String startStr = request.getParameter("start");
    String limitStr = request.getParameter("limit");
    String sort = request.getParameter("sort");
    String dir = request.getParameter("dir");
    String year = request.getParameter("year");
    UserDto userDto = null;
    if (LoginInforUtil.getLoginInfor(request) != null) {
      userDto = LoginInforUtil.getLoginInfor(request).getUser();
    }

    String userId = "";
    if (userDto != null) {
      userId = userDto.getId();
    }

    Integer start = 0;
    Integer limit = 0;
    if (startStr != null && !"".equals(startStr)) {
      start = Integer.parseInt(startStr);
    }

    if (limitStr != null && !"".equals(limitStr)) {
      limit = Integer.parseInt(limitStr);
    }
    Integer oType = Integer.parseInt(orderType);
    Integer[] type = null;
    if (oType == 1) {
      type = new Integer[] { 1, 3 };
    } else if (oType == 2) {
      type = new Integer[] { 2, 4 };
    } else if (oType == 5) {
      type = new Integer[] { 5, 7 };
    } else if (oType == 6) {
      type = new Integer[] { 6, 8 };
    } else if (oType == 0) {
      //直接入库
      type = new Integer[] { 0, -1 };
    }
    String resultStr = getArrInfo(searchStr, type, start, limit, userId, sort, dir, year);
    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();

    return null;
  }

  private String getArrInfo(String searchStr, Integer[] orderType, Integer start, Integer limit, String userId, String sort, String dir, String year) {
    //参数Map
    Map<String, Object> paramMap = new HashMap<String, Object>();
    StringBuffer orderTypeStr = new StringBuffer();

    if (orderType != null) {
      for (int i = 0; i < orderType.length; i++) {
        orderTypeStr.append(orderType[i].toString());
        if (i < orderType.length - 1) {
          orderTypeStr.append(",");
        }
      }
    }
    paramMap.put("year", year);
    if (null != searchStr && !"".equals(searchStr)) {
      JSONObject searchJson = JSONObject.fromObject(searchStr);
      Integer status = null;
      if ("全部".equals(searchJson.getString("status")) || "".equals(searchJson.getString("status"))) {
        status = null;
      } else {
        status = Integer.valueOf(searchJson.getString("status"));
      }

      //到货单号
      paramMap.put("arrivalCode", searchJson.getString("arrivalCode"));
      //采购单号
      paramMap.put("orderCode", searchJson.getString("orderCode"));
      //开始日期
      paramMap.put("beginDate", searchJson.getString("beginDate"));
      //结束日期
      paramMap.put("endDate", searchJson.getString("endDate"));
      paramMap.put("status", status);

      if (searchJson.has("memo")) {
        paramMap.put("memo", searchJson.getString("memo"));
      }

      if (searchJson.has("quotationCode"))
        paramMap.put("quotationCode", searchJson.getString("quotationCode"));
      if (searchJson.has("contractCode"))
        paramMap.put("contractCode", searchJson.getString("contractCode"));
      paramMap.put("start", start == null ? 0 : start);
      paramMap.put("limit", limit == null ? 20 : limit);

      paramMap.put("userId", userId);
      paramMap.put("resourceType", 4);

      if ("editDateString".equals(sort)) {
        sort = "editDate";
      }
      paramMap.put("sort", RegexUtils.toDataBaseColName(sort));
      paramMap.put("dir", dir);
    } else {
      paramMap.put("start", start == null ? 0 : start);
      paramMap.put("limit", limit == null ? 20 : limit);
      paramMap.put("userId", userId);
    }

    //到货单总数
    int total = 0;
    //到货单列表
    List<TProductArrivalInfor> list = new ArrayList<TProductArrivalInfor>();

    if (orderTypeStr.toString().indexOf('0') == -1) {
      paramMap.put("orderType", orderTypeStr);
      total = arrivalService.getArrInfoTotalBySearch(paramMap);
      list = arrivalService.getArrivalInfoBySearch(paramMap);
    } else {
      //直接入库
      paramMap.put("arrivalType", orderTypeStr);
      total = arrivalService.getDirectArrTotalBySearch(paramMap);
      list = arrivalService.getDirectArrivalBySearch(paramMap);
    }

    String jsonStr = JSONArray.fromObject(list).toString();

    //返回前台字符串
    String resultStr = "{totalProperty : " + total + ", arrivalInfo : " + jsonStr + "}";

    return resultStr;
  }

  private String buildCodeType(Integer arrType) {
    String codeType = "";
    switch (arrType) {
    case 0:
      codeType = "17";//直接入库
      break;
    case 1:
      codeType = "05";//合同入库
      break;
    case 3:
      codeType = "05";//合同入库
      break;
    case 2:
      codeType = "14";//储备入库
      break;
    case 4:
      codeType = "14";//储备入库
      break;
    case 5:
      codeType = "15";//预订入库
      break;
    case 7:
      codeType = "15";//预订入库
      break;
    case 6:
      codeType = "16";//试刀入库
      break;
    case 8:
      codeType = "16";//试刀入库
      break;
    }
    return codeType;
  }

  public BillsCodeDefService getBillsCodeDefService() {
    return billsCodeDefService;
  }

  public void setBillsCodeDefService(BillsCodeDefService billsCodeDefService) {
    this.billsCodeDefService = billsCodeDefService;
  }

  public OutStockService getOutStockService() {
    return outStockService;
  }

  public void setOutStockService(OutStockService outStockService) {
    this.outStockService = outStockService;
  }

}
