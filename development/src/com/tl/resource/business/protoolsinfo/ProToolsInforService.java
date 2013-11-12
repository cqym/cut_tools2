package com.tl.resource.business.protoolsinfo;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.TreeDto;
import com.tl.resource.dao.pojo.TProductBrand;
import com.tl.resource.dao.pojo.TProductSort;
import com.tl.resource.dao.pojo.TProductSource;
import com.tl.resource.dao.pojo.TProductToolsInfor;

public interface ProToolsInforService {
  public void insertProTools(TProductToolsInfor proToolsInfo);

  public List<TProductToolsInfor> getProToolsByPage(PaginationSupport pageInfo);

  public List<TProductToolsInfor> getProToolsByParId(String parId);

  public List<TreeDto> getProTreeByPage(PaginationSupport pageInfo);

  public int getProToolsTotal();

  public String getId(String parId);

  public TreeDto getProTreeById(String id);

  /**
   * 根据ID删除产品信息
   * @param id
   */
  public void deleteProTools(String id);

  /**
   * 批量删除产品信息
   * @param list
   */
  public void deleteProTools(List<TreeDto> list);

  /**
   * 根据ID获取产品信息
   */
  public TProductToolsInfor getProToolsById(String id);

  /**
   * 修改产品信息
   * @param proTools
   * @param sycQuoToosInfor 
   */
  public void updateProToolsById(TProductToolsInfor proTools, String sycQuoToosInfor) throws Exception;

  /**
   * 根据搜索条件获取产品信息
   */
  public List<TreeDto> getProToolsBySearch(Map<String, Object> parmMap);

  /**
   * 根据搜索条件获取产品信息总数
   * @param parmMap
   * @return
   */
  public int getProToolsTotal(Map<String, Object> parmMap);

  /**
   * 根据ID获取工具信息
   * @param id
   * @return
   */
  public TreeDto getProductToolsInfoById(String id);

  /**
  * 根据组别编号获取组别(ftl2009-11-25)
  * @param sortCode
  * @return
  */
  public List<TProductSort> getProSortBySortCode(String sortCode);

  /**
     * 根据名称获取品牌
     * @param name 品牌名称
     * @return
     */
  public List<TProductBrand> getProBrandByName(String name);

  /**
     * 根据名称获取产品来源
     * @param name 来源名称
     * @return
     */
  public List<TProductSource> getProSourceByName(String name);

  /**
   * 修改非标产品
   * @param jsonArray
   */
  public void updateNonStandPro(JSONArray jsonArray, String sycQuoToosInfor) throws Exception;

  /**
   * 根据产品牌号 名称 品牌 来源 查找产品
   * @param treeDto
   * @return
   */
  public List<TreeDto> getTreeDto(TreeDto treeDto);

  public List<TProductToolsInfor> addNonStandPro(JSONArray array) throws Exception;

  /**
   * 根据来源获取品牌
   * @param sorceName
   * @return
   */
  public List<TProductBrand> getProBrandBySorce(String sourceName, String brandName);

  /**
  * 获取品牌对应的组别
  */
  public List<TProductSort> getProSortByBrand(String brand);

  /**
     * 获取工具信息(ftl 2009-12-23)
     * @param parmMap
     * @return
     */
  public List<TreeDto> getToolsBySearch(Map<String, Object> parmMap);

  /**
   * 获取工具信息不级联子节点(ftl 2009-12-24)
   * @param parmMap
   * @return
   */
  public List<TreeDto> getToolsByRootNode(Map<String, Object> parmMap);

  /**
   * 获取工具信息级联子节点(ftl 2009-12-24)
   * @param parmMap
   * @return
   */
  public List<TreeDto> getToolsWithChildren(Map<String, Object> parmMap);

  /**
  * 取得历史面价对应年度(ftl 2010-01-13)
   * @param productBrand 
  * @return
  */
  List getHistoryYear(String productBrand);

  /**
     * 获取全部来源(ftl 2010-01-20)
     * @return
     */
  public List<TProductSource> getProSourceByAll();

  public PaginationSupport findSupToolsList(Map<String, Object> mparams, String startIndex, String pageSize);

  /**
     * 根据名称 来源 获取品牌
     * @param name 品牌名称
     * @return
     */
  public List<TProductBrand> getBrand4import(TreeDto treeDto);

  /**
  * 根据 组别编号及品牌 获取组别
  * @param parmMap
  * @return
  */
  public List<TProductSort> getProSort4Import(TreeDto treeDto);

  public void deleteTools(TreeDto treeDto);

  public List<TreeDto> getTools4Export(HttpServletRequest request, HttpServletResponse response);

  boolean isToolsBeEditedQuotation(String toolsId);
}
