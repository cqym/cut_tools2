package com.tl.resource.audit;

import java.util.List;

import com.tl.resource.audit.dto.TAuditContentDefDto;

public interface IAuditContentService {
  List<TAuditContentDefDto> findAuditDetailAuditContentList(String auditDetailId);

  List<TAuditContentDefDto> findAllAuditContentList();

  void saveAuditDetailAuditContent(String auditDetailId, List<String> ids);

  public List<TAuditContentDefDto> getAuditFlowContent(String auditFlowId);

  List<TAuditContentDefDto> findHistoryContentList(String busId);

  List<TAuditContentDefDto> findAllAuditContentList(String busId);

  List<TAuditContentDefDto> findWaitAuditContent(String busId, String userId);
}
