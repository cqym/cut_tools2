package com.tl.resource.business.baseInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.DegreeRebateDto;
import com.tl.resource.business.dto.SuppliersBrandDto;
import com.tl.resource.dao.TAccountsInforDAO;
import com.tl.resource.dao.TCompanyInforDAO;
import com.tl.resource.dao.TCustomersDegreeDAO;
import com.tl.resource.dao.TCustomersInforDAO;
import com.tl.resource.dao.TExchangeRateDAO;
import com.tl.resource.dao.TOwnContactPersonDAO;
import com.tl.resource.dao.TProductBrandDAO;
import com.tl.resource.dao.TProductSortDAO;
import com.tl.resource.dao.TRebateDAO;
import com.tl.resource.dao.TReserveInforDAO;
import com.tl.resource.dao.TSuppliersBrandDAO;
import com.tl.resource.dao.TSuppliersInforDAO;
import com.tl.resource.dao.TTaxRateDAO;
import com.tl.resource.dao.TUserInforDAO;
import com.tl.resource.dao.pojo.TAccountsInfor;
import com.tl.resource.dao.pojo.TCompanyInfor;
import com.tl.resource.dao.pojo.TCustomersDegree;
import com.tl.resource.dao.pojo.TCustomersInfor;
import com.tl.resource.dao.pojo.TExchangeRate;
import com.tl.resource.dao.pojo.TOwnContactPerson;
import com.tl.resource.dao.pojo.TOwnContactPersonExample;
import com.tl.resource.dao.pojo.TProductBrand;
import com.tl.resource.dao.pojo.TProductSort;
import com.tl.resource.dao.pojo.TRebate;
import com.tl.resource.dao.pojo.TRebateExample;
import com.tl.resource.dao.pojo.TReserveInfor;
import com.tl.resource.dao.pojo.TSuppliersBrand;
import com.tl.resource.dao.pojo.TSuppliersInfor;
import com.tl.resource.dao.pojo.TTaxRate;
import com.tl.resource.dao.pojo.TTaxRateExample;
import com.tl.resource.dao.pojo.TUserInfor;
import com.tl.resource.dao.pojo.TUserInforExample;

public class BaseInfoServiceImpl implements BaseInfoService {

  private TCompanyInforDAO tcompanyInforDAO;

  private TCustomersDegreeDAO tcustomersDegreeDAO;

  private TCustomersInforDAO tcustomersInforDAO;

  private TSuppliersInforDAO tsuppliersInforDAO;

  private TExchangeRateDAO texchangeRateDAO;

  private TReserveInforDAO treserveInforDAO;

  private TAccountsInforDAO taccountsInforDAO;

  private TRebateDAO trebateDAO;

  private TUserInforDAO tuserInforDAO;

  private TProductBrandDAO proBrandDao;

  private TSuppliersBrandDAO suppliersBrandDAO;

  private TProductSortDAO productSortDAO;

  private TTaxRateDAO taxRateDAO;

  private TOwnContactPersonDAO clientLeaderDAO;

  public TProductSortDAO getProductSortDAO() {
    return productSortDAO;
  }

  public void setProductSortDAO(TProductSortDAO productSortDAO) {
    this.productSortDAO = productSortDAO;
  }

  public TSuppliersBrandDAO getSuppliersBrandDAO() {
    return suppliersBrandDAO;
  }

  public void setSuppliersBrandDAO(TSuppliersBrandDAO suppliersBrandDAO) {
    this.suppliersBrandDAO = suppliersBrandDAO;
  }

  public TUserInforDAO getTuserInforDAO() {
    return tuserInforDAO;
  }

  public void setTuserInforDAO(TUserInforDAO tuserInforDAO) {
    this.tuserInforDAO = tuserInforDAO;
  }

  public TCompanyInforDAO getTcompanyInforDAO() {
    return tcompanyInforDAO;
  }

  public void setTcompanyInforDAO(TCompanyInforDAO tcompanyInforDAO) {
    this.tcompanyInforDAO = tcompanyInforDAO;
  }

  public TCustomersDegreeDAO getTcustomersDegreeDAO() {
    return tcustomersDegreeDAO;
  }

  public void setTcustomersDegreeDAO(TCustomersDegreeDAO tcustomersDegreeDAO) {
    this.tcustomersDegreeDAO = tcustomersDegreeDAO;
  }

  public TCustomersInforDAO getTcustomersInforDAO() {
    return tcustomersInforDAO;
  }

  public void setTcustomersInforDAO(TCustomersInforDAO tcustomersInforDAO) {
    this.tcustomersInforDAO = tcustomersInforDAO;
  }

  public TSuppliersInforDAO getTsuppliersInforDAO() {
    return tsuppliersInforDAO;
  }

  public void setTsuppliersInforDAO(TSuppliersInforDAO tsuppliersInforDAO) {
    this.tsuppliersInforDAO = tsuppliersInforDAO;
  }

  public TExchangeRateDAO getTexchangeRateDAO() {
    return texchangeRateDAO;
  }

  public void setTexchangeRateDAO(TExchangeRateDAO texchangeRateDAO) {
    this.texchangeRateDAO = texchangeRateDAO;
  }

  public TReserveInforDAO getTreserveInforDAO() {
    return treserveInforDAO;
  }

  public void setTreserveInforDAO(TReserveInforDAO treserveInforDAO) {
    this.treserveInforDAO = treserveInforDAO;
  }

  public TAccountsInforDAO getTaccountsInforDAO() {
    return taccountsInforDAO;
  }

  public void setTaccountsInforDAO(TAccountsInforDAO taccountsInforDAO) {
    this.taccountsInforDAO = taccountsInforDAO;
  }

  public TRebateDAO getTrebateDAO() {
    return trebateDAO;
  }

  public void setTrebateDAO(TRebateDAO trebateDAO) {
    this.trebateDAO = trebateDAO;
  }

  @Override
  public void saveCompany(TCompanyInfor companyInfo) {
    tcompanyInforDAO.insert(companyInfo);
  }

  @Override
  public List<TCompanyInfor> getCompanyByPage(Map<String, Object> parmMap) {
    return tcompanyInforDAO.getCompanyByPage(parmMap);
  }

  @Override
  public int getCompanyTotal(Map<String, Object> parmMap) {
    return tcompanyInforDAO.getCompanyTotal(parmMap);
  }

  @Override
  public void deleteCompanyById(String companyId) {
    tcompanyInforDAO.deleteByPrimaryKey(companyId);
  }

  @Override
  public void updateCompany(TCompanyInfor companyInfo) {
    tcompanyInforDAO.updateByPrimaryKeySelective(companyInfo);

  }

  @Override
  public List<TCustomersDegree> getAllCustomersDegree(Map<String, Object> parmMap) {
    return tcustomersDegreeDAO.getAllCustomersDegree(parmMap);
  }

  @Override
  public TCustomersDegree getCustomersDegreeById(String customserDegreeId) {
    return tcustomersDegreeDAO.selectByPrimaryKey(customserDegreeId);
  }

  @Override
  public void addCustomersInfo(TCustomersInfor customersInfo) throws Exception {
    //TCustomersInforExample example = new TCustomersInforExample();
    //example.createCriteria().andCustomerCodeEqualTo(customersInfo.getCustomerCode());
    //.andIdNotEqualTo(customersInfo.getId());
    // int cnt = tcustomersInforDAO.countByExample(example);
    String currentCode = tcustomersInforDAO.selectNewCustomerCode(customersInfo.getCustomerFixCode());
    customersInfo.setCustomerCode(currentCode);
    customersInfo.setEditDate(new Date());
    customersInfo.setLastUpdateDate(new Date());
    tcustomersInforDAO.insert(customersInfo);

    TOwnContactPerson clientLeader = new TOwnContactPerson();
    clientLeader.setId(GenerateSerial.getUUID());
    clientLeader.setCustomerId(customersInfo.getId());
    clientLeader.setUserId(customersInfo.getOwnContactPersonId());
    clientLeaderDAO.insert(clientLeader);
    // } else {
    //  throw new Exception("客户编号重复");
    // }
  }

  @Override
  public int getCustomersCount(Map<String, Object> parmMap) {

    return tcustomersInforDAO.getCustomersCount(parmMap);
  }

  @Override
  public List<TCustomersInfor> getCustomersList(Map<String, Object> parmMap) {

    return tcustomersInforDAO.getCustomersList(parmMap);
  }

  @Override
  public void deleteCustomersById(String customersId) {
    TOwnContactPersonExample example = new TOwnContactPersonExample();
    example.createCriteria().andCustomerIdEqualTo(customersId);
    clientLeaderDAO.deleteByExample(example);
    tcustomersInforDAO.deleteByPrimaryKey(customersId);
  }

  @Override
  public void updateCustomers(TCustomersInfor customersInfo) throws Exception {
    //    TCustomersInforExample example = new TCustomersInforExample();
    //    example.createCriteria().andCustomerCodeEqualTo(customersInfo.getCustomerCode()).andIdNotEqualTo(customersInfo.getId());
    //    int cnt = tcustomersInforDAO.countByExample(example);
    //    if (cnt == 0) {
    TCustomersInfor oldCus = this.tcustomersInforDAO.selectByPrimaryKey(customersInfo.getId());
    customersInfo.setLastUpdateDate(new Date());
    tcustomersInforDAO.updateByPrimaryKeySelective(customersInfo);

    if (!oldCus.getOwnContactPersonId().equals(customersInfo.getOwnContactPersonId())) {
      TOwnContactPersonExample exam = new TOwnContactPersonExample();
      exam.createCriteria().andCustomerIdEqualTo(oldCus.getId()).andUserIdEqualTo(oldCus.getOwnContactPersonId());
      clientLeaderDAO.deleteByExample(exam);

      TOwnContactPerson clientLeader = new TOwnContactPerson();
      clientLeader.setId(GenerateSerial.getUUID());
      clientLeader.setCustomerId(customersInfo.getId());
      clientLeader.setUserId(customersInfo.getOwnContactPersonId());
      clientLeaderDAO.insert(clientLeader);
    }
    //    } else {
    //      throw new Exception("客户编号重复");
    //    }
  }

  @Override
  public List<TSuppliersInfor> getSuppliersByPage(Map<String, Object> parmMap) {
    return tsuppliersInforDAO.getSuppliersByPage(parmMap);
  }

  @Override
  public int getSuppliersTotal(Map<String, Object> parmMap) {
    return tsuppliersInforDAO.getSuppliersTotal(parmMap);
  }

  @Override
  public void deleteSupplierById(String supplierIdPar) {
    suppliersBrandDAO.deleteSupplierBrandBySupplierId(supplierIdPar);
    tsuppliersInforDAO.deleteByPrimaryKey(supplierIdPar);
  }

  @Override
  public void saveSupplier(TSuppliersInfor supplierInfo) {
    tsuppliersInforDAO.insert(supplierInfo);

  }

  @Override
  public void updateSupplier(TSuppliersInfor supplierInfor) {
    tsuppliersInforDAO.updateByPrimaryKeySelective(supplierInfor);
  }

  @Override
  public List<TExchangeRate> getExchangeByPage(Map<String, Object> parmMap) {
    return texchangeRateDAO.getExchangeByPage(parmMap);
  }

  @Override
  public int getExchangeTotal() {
    return texchangeRateDAO.getExchangeTotal();
  }

  @Override
  public void saveExchange(TExchangeRate exchangeInfo) {
    texchangeRateDAO.insert(exchangeInfo);

  }

  @Override
  public void deleteExchangeById(String exchangeId) {
    texchangeRateDAO.deleteByPrimaryKey(exchangeId);
  }

  @Override
  public void updateExchange(TExchangeRate exchangeInfo) {
    texchangeRateDAO.updateByPrimaryKeySelective(exchangeInfo);
  }

  @Override
  public List<TReserveInfor> getReserveByPage(Map<String, Object> parmMap) {
    return treserveInforDAO.getReserveByPage(parmMap);
  }

  @Override
  public int getReserveTotal(Map<String, Object> parmMap) {
    return treserveInforDAO.getReserveTotal(parmMap);
  }

  @Override
  public List<TAccountsInfor> getAccountsInfoListByByReserveId(Map<String, Object> parmMap) {

    return taccountsInforDAO.getAccountsInfoListByByReserveId(parmMap);
  }

  @Override
  public List<DegreeRebateDto> getDegreeRebateByPageAndDegreeId(Map<String, Object> parmMap) {
    return trebateDAO.getDegreeRebateByPageAndDegreeId(parmMap);
  }

  @Override
  public int getDegreeRebateTotalByDegreeId(Map<String, Object> parmMap) {
    return trebateDAO.getDegreeRebateTotalByDegreeId(parmMap);
  }

  @Override
  public void saveRebate(TRebate trebate) {
    trebateDAO.insert(trebate);

  }

  @Override
  public void updateRebate(TRebate trebate) {
    trebateDAO.updateByPrimaryKeySelective(trebate);
  }

  @Override
  public void saveCustomersDegree(TCustomersDegree customersDegreeInfo) {
    tcustomersDegreeDAO.insert(customersDegreeInfo);
  }

  @Override
  public List<TUserInfor> getAllUserInfor() {
    TUserInforExample example = null;
    return tuserInforDAO.selectByExample(example);
  }

  @Override
  public void setBenchmarkExchangeById(String exchangeId) {
    //将原有基准汇率设为非基准汇率
    texchangeRateDAO.updateOldBenchmark();
    //设置新基准汇率
    texchangeRateDAO.setBenchmarkExchangeById(exchangeId);
  }

  @Override
  public List<TProductBrand> getProductBrand(Map<String, Object> parmMap) {
    return proBrandDao.getProductBrand(parmMap);
  }

  @Override
  public Integer getProductBrandTotal(Map<String, Object> paramMap) {
    return proBrandDao.getProductBrandTotal(paramMap);
  }

  public TProductBrandDAO getProBrandDao() {
    return proBrandDao;
  }

  public void setProBrandDao(TProductBrandDAO proBrandDao) {
    this.proBrandDao = proBrandDao;
  }

  @Override
  public void deleteBrand(String brandId) {
    proBrandDao.deleteByPrimaryKey(brandId);
  }

  @Override
  public void insertBrand(TProductBrand brand) {
    proBrandDao.insertSelective(brand);
  }

  @Override
  public void updateBrand(TProductBrand brand) {
    TProductBrand oldBrand = proBrandDao.selectByPrimaryKey(brand.getId());
    proBrandDao.updateByPrimaryKeySelective(brand);

    String newBrandName = brand.getName();
    String oldBrandName = oldBrand.getName();
    if (!newBrandName.equals(oldBrandName)) {
      Map<String, Object> parmMap = new HashMap<String, Object>();
      parmMap.put("oldBrand", oldBrandName);
      parmMap.put("newBrand", newBrandName);
      proBrandDao.updateToolsByBrand(parmMap);
    }
  }

  @Override
  public void deleteBrand(JSONArray array) {
    Iterator<String> iterator = array.iterator();
    while (iterator.hasNext()) {
      String id = iterator.next();
      this.deleteBrand(id);
    }
  }

  @Override
  public List<SuppliersBrandDto> getSuppliersBrandByPageAndSupplierId(Map<String, Object> parmMap) {
    return suppliersBrandDAO.getSuppliersBrandByPageAndSupplierId(parmMap);
  }

  @Override
  public int getSuppliersBrandTotalBySupplierId(Map<String, Object> parmMap) {
    return suppliersBrandDAO.getSuppliersBrandTotalBySupplierId(parmMap);
  }

  @Override
  public void saveSuppliersBrand(TSuppliersBrand suppliersBrandInfo) {
    suppliersBrandDAO.insert(suppliersBrandInfo);
  }

  @Override
  public void deleteSupplierBrankById(String suppliersBrandIdPar) {
    suppliersBrandDAO.deleteByPrimaryKey(suppliersBrandIdPar);
  }

  @Override
  public List<TProductSort> getAllProductSort() {
    return productSortDAO.getAllProductSort();
  }

  @Override
  public void updateRebate(TCustomersDegree tcusDegree) {
    tcustomersDegreeDAO.updateByPrimaryKeySelective(tcusDegree);
    /**
     * 这里在大数据量的情况下，可能会产生性能问题
     */
    Map<String, Object> parmMap = new HashMap<String, Object>();
    parmMap.put("customerDegreeId", tcusDegree.getId());
    parmMap.put("degreeCode", tcusDegree.getDegreeCode());
    tcustomersInforDAO.updateByCustomersDegreeId(parmMap);

  }

  @Override
  public List<TProductBrand> getRunData(Map<String, Object> paramMap) {

    return proBrandDao.getRunData(paramMap);
  }

  @Override
  public boolean checkCompany(TCompanyInfor companyInfo) {

    return tcompanyInforDAO.checkCompany(companyInfo);
  }

  @Override
  public void deleteCustomerDegreeByIds(String[] ids) {
    TRebateExample example = new TRebateExample();
    for (int i = 0; i < ids.length; i++) {
      example.createCriteria().andCustomersDegreeIdEqualTo(ids[i]);
      trebateDAO.deleteByExample(example);
      example.clear();
      tcustomersDegreeDAO.deleteByPrimaryKey(ids[i]);
    }
  }

  @Override
  public void addTaxRate(TTaxRate taxRate) throws Exception {
    TTaxRateExample example = new TTaxRateExample();
    example.createCriteria().andRateEqualTo(taxRate.getRate());
    int cnt = taxRateDAO.countByExample(example);
    if (cnt == 0) {
      taxRateDAO.insert(taxRate);
    } else {
      throw new Exception("税率重复");
    }
  }

  @Override
  public void deleteTaxRate(JSONArray array) throws Exception {
    Iterator<String> iterator = array.iterator();
    while (iterator.hasNext()) {
      String id = iterator.next();
      taxRateDAO.deleteByPrimaryKey(id);
    }
  }

  @Override
  public List<TTaxRate> getTaxRate() {
    TTaxRateExample example = new TTaxRateExample();
    example.createCriteria();
    List<TTaxRate> list = taxRateDAO.selectByExample(example);
    return list;
  }

  @Override
  public void modifyTaxRate(TTaxRate taxRate) throws Exception {
    TTaxRateExample example = new TTaxRateExample();
    example.createCriteria().andRateEqualTo(taxRate.getRate()).andIdNotEqualTo(taxRate.getId());
    int cnt = taxRateDAO.countByExample(example);
    if (cnt == 0) {
      taxRateDAO.updateByPrimaryKeySelective(taxRate);
    } else {
      throw new Exception("税率重复");
    }
  }

  public TTaxRateDAO getTaxRateDAO() {
    return taxRateDAO;
  }

  public void setTaxRateDAO(TTaxRateDAO taxRateDAO) {
    this.taxRateDAO = taxRateDAO;
  }

  @Override
  public int getTaxRateCount() {
    TTaxRateExample example = new TTaxRateExample();
    example.createCriteria();
    int cnt = taxRateDAO.countByExample(example);
    return cnt;
  }

  public TOwnContactPersonDAO getClientLeaderDAO() {
    return clientLeaderDAO;
  }

  public void setClientLeaderDAO(TOwnContactPersonDAO clientLeaderDAO) {
    this.clientLeaderDAO = clientLeaderDAO;
  }

  @Override
  public List<TOwnContactPerson> getSelectedUser(Map<String, Object> parmMap) {
    return clientLeaderDAO.getSelectedUser(parmMap);
  }

  @Override
  public PaginationSupport getWillSelectUser(Map<String, Object> parmMap) {
    List<TOwnContactPerson> list = clientLeaderDAO.getWillSelectUser(parmMap);
    int cnt = this.getWillSelectUserCount(parmMap);
    PaginationSupport ps = new PaginationSupport(list, cnt, (Integer) parmMap.get("pageSize"), (Integer) parmMap.get("startIndex"));
    return ps;
  }

  @Override
  public int getWillSelectUserCount(Map<String, Object> parmMap) {
    return clientLeaderDAO.getWillSelectUserCount(parmMap);
  }

  @Override
  public void saveClientLeader(String customerId, List<String> idList) {
    TOwnContactPersonExample example = new TOwnContactPersonExample();
    example.createCriteria().andCustomerIdEqualTo(customerId);
    clientLeaderDAO.deleteByExample(example);
    for (String userId : idList) {
      TOwnContactPerson clientLeader = new TOwnContactPerson();
      clientLeader.setId(GenerateSerial.getUUID());
      clientLeader.setCustomerId(customerId);
      clientLeader.setUserId(userId);

      clientLeaderDAO.insert(clientLeader);
    }
  }
}
