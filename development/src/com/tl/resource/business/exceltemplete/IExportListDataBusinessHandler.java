package com.tl.resource.business.exceltemplete;

import java.util.Map;

public interface IExportListDataBusinessHandler {
  public Map<String, Object> getBusinessData(Map para);

  public String getMainCode();
}
