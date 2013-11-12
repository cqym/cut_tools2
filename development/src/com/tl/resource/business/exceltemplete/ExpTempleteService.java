package com.tl.resource.business.exceltemplete;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.exception.ParsePropertyException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.dao.pojo.TExpTemplete;

public interface ExpTempleteService {
  public void insertTemplete(TExpTemplete instance);

  public void updateTemplete(TExpTemplete instance);

  public void deleteTempleteById(String id);

  public void deleteTempleteByIds(String[] id);

  public TExpTemplete getTExpTempleteById(String id);

  public PaginationSupport getTExpTempletes(Map<String, String> mparams, int startIndex, int pageSize);

  public void expertExce(String bussinessId, String templeteId, HttpServletResponse response, String basePath) throws InstantiationException,
    IllegalAccessException, ClassNotFoundException, IOException, ParsePropertyException, InvalidFormatException;

  public void expertListExce(Map para, String tempId, HttpServletResponse response, String realPath) throws InstantiationException,
    IllegalAccessException, ClassNotFoundException, IOException, ParsePropertyException, InvalidFormatException;
}
