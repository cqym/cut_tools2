package com.tl.resource.business.notepad;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.NotepadDto;
import com.tl.resource.business.dto.NotepadTypeDto;
import com.tl.resource.dao.TNotepadDAO;
import com.tl.resource.dao.TNotepadTypeDAO;
import com.tl.resource.dao.pojo.TNotepad;
import com.tl.resource.dao.pojo.TNotepadExample;
import com.tl.resource.dao.pojo.TNotepadType;
import com.tl.resource.dao.pojo.TNotepadExample.Criteria;

public class NotepadServiceImp implements NotepadService {

  private TNotepadTypeDAO notepadTypeDAO;

  private TNotepadDAO notepadDAO;

  @Override
  public void addNodepad(NotepadDto dto) {
    TNotepad record = new TNotepad();
    try {
      BeanUtils.copyProperties(record, dto);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    record.setEditTime(new Date());
    record.setId(GenerateSerial.getUUID());
    notepadDAO.insert(record);
  }

  @Override
  public void addNodepadType(NotepadTypeDto dto) {
    TNotepadType record = new TNotepadType();
    try {
      BeanUtils.copyProperties(record, dto);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    record.setId(GenerateSerial.getUUID());
    notepadTypeDAO.insert(record);
  }

  @Override
  public void deletNodepadType(String id) {
    TNotepadExample example = new TNotepadExample();
    example.createCriteria().andTypeIdEqualTo(id);
    int c = notepadDAO.countByExample(example);
    if (c == 0) {
      notepadTypeDAO.deleteByPrimaryKey(id);
    }
  }

  @Override
  public void deleteNodepad(String id) {
    // TODO Auto-generated method stub
    notepadDAO.deleteByPrimaryKey(id);
  }

  @Override
  public List<NotepadTypeDto> getAllNodepads() {
    TNotepadExample example = new TNotepadExample();
    List<TNotepad> list = notepadDAO.selectByExample(example);
    List<NotepadTypeDto> rtList = new ArrayList<NotepadTypeDto>();
    try {
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        TNotepad po = (TNotepad) iterator.next();
        NotepadTypeDto dto = new NotepadTypeDto();
        BeanUtils.copyProperties(dto, po);
        rtList.add(dto);
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
    return null;
  }

  @Override
  public PaginationSupport getNotepads(Map<String, Object> params, int startIndex, int pageSize) {
    TNotepadExample example = new TNotepadExample();
    Criteria c = example.createCriteria();
    if (params.get("title") != null) {
      c.andTitleLike("%" + params.get("title") + "%");
    }
    if (params.get("memo") != null) {
      c.andMemoLike("%" + params.get("memo") + "%");
    }
    if (params.get("userId") != null) {
      c.andUserIdEqualTo(params.get("userId").toString());
    }
    if (params.get("typeId") != null) {
      c.andTypeIdEqualTo(params.get("typeId").toString());
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      if (params.get("startDate") != null && params.get("endDate") != null) {
        Date v1 = sdf.parse(params.get("startDate").toString());
        Date v2 = sdf.parse(params.get("endDate").toString());
        c.andEditTimeBetween(v1, v2);
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
    int totalCount = notepadDAO.countByExample(example);
    example.setOrderByClause("edit_time desc");
    example.setStartIndex(startIndex);
    example.setPageSize(pageSize);
    List<TNotepad> list = notepadDAO.selectByExample(example);
    List<NotepadDto> listDto = new ArrayList<NotepadDto>();
    try {
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        TNotepad notepad = (TNotepad) iterator.next();
        NotepadDto e = new NotepadDto();
        BeanUtils.copyProperties(e, notepad);
        listDto.add(e);
      }
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    PaginationSupport ps = new PaginationSupport(listDto, totalCount, pageSize, startIndex);
    return ps;
  }

  @Override
  public void updateNodepad(NotepadDto dto) {
    TNotepad record = new TNotepad();
    try {
      BeanUtils.copyProperties(record, dto);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    notepadDAO.updateByPrimaryKeySelective(record);
  }

  @Override
  public void updateNodepadType(NotepadTypeDto dto) {
    TNotepadType record = new TNotepadType();
    try {
      BeanUtils.copyProperties(record, dto);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    notepadTypeDAO.updateByPrimaryKeySelective(record);
  }

  public TNotepadTypeDAO getNotepadTypeDAO() {
    return notepadTypeDAO;
  }

  public void setNotepadTypeDAO(TNotepadTypeDAO notepadTypeDAO) {
    this.notepadTypeDAO = notepadTypeDAO;
  }

  public TNotepadDAO getNotepadDAO() {
    return notepadDAO;
  }

  public void setNotepadDAO(TNotepadDAO notepadDAO) {
    this.notepadDAO = notepadDAO;
  }

  @Override
  public void addNoticeNotepad(NotepadDto dto) {
    // TODO Auto-generated method stub
    TNotepad record = new TNotepad();
    try {
      BeanUtils.copyProperties(record, dto);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    record.setId(GenerateSerial.getUUID());
    notepadDAO.insert(record);
  }

}
