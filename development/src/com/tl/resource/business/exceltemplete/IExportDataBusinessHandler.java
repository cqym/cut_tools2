package com.tl.resource.business.exceltemplete;

import java.util.Map;

public interface IExportDataBusinessHandler {
  public Map<String, Object> getBusinessData(String id);
  public String getMainCode();
}
