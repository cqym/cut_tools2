package com.tl.resource.business.search;

import java.util.List;
import java.util.Map;

public interface DanJuGuanXiSearchService {
  public Map<String, List<Object>> findDanJuListByCode(String code);
}
