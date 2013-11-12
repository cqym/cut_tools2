package com.tl.resource.business.notepad;

import java.util.List;
import java.util.Map;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.NotepadDto;
import com.tl.resource.business.dto.NotepadTypeDto;

public interface NotepadService {

  public void addNodepadType(NotepadTypeDto dto);

  public void deletNodepadType(String id);

  public void updateNodepadType(NotepadTypeDto dto);

  public List<NotepadTypeDto> getAllNodepads();

  public void addNodepad(NotepadDto dto);

  public void deleteNodepad(String id);

  public void updateNodepad(NotepadDto dto);

  public PaginationSupport getNotepads(Map<String, Object> params, int startIndex, int pageSize);

  public void addNoticeNotepad(NotepadDto dto);

}
