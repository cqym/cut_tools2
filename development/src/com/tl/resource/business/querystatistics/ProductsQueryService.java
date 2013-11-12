package com.tl.resource.business.querystatistics;

import java.util.Map;

import com.tl.common.util.PaginationSupport;

public interface ProductsQueryService {

  PaginationSupport getList(Map<String, Object> mparams, Integer startIndex, Integer pageSize);

  PaginationSupport getSaleBillList(Map<String, Object> mparams, Integer startIndex, Integer pageSize);

}
