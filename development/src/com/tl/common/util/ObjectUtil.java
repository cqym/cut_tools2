package com.tl.common.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

public class ObjectUtil {

  public static List copyListElementsPropertys(List<Object> origList, Class destClass) throws InstantiationException, IllegalAccessException {
    // TODO Auto-generated method stub
    List rtList = new ArrayList();
    for (Object it : origList) {
      Object dest = destClass.newInstance();
      copyObjectPropertys(it, dest);
      rtList.add(dest);
    }
    return rtList;
  }

  public static void copyObjectPropertys(Object orig, Object dest) {
    // TODO Auto-generated method stub
    try {
      BeanUtils.copyProperties(dest, orig);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
