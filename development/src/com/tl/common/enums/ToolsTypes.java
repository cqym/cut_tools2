package com.tl.common.enums;

/**
 * 产品类别
 * @author cll
 *
 */
public enum ToolsTypes {
  jiJia((byte) 1, "机加"), zhengYing((byte) 2, "整硬"), daoPian((byte) 3, "刀片"), xiuMo((byte) 4, "修磨");

  private final byte value;

  private final String label;

  private ToolsTypes(byte v, String label) {
    this.value = v;
    this.label = label;
  }

  public Byte value() {
    return this.value;
  }

  public String toString() {
    return name() + "," + value();
  }

  public static ToolsTypes of(final Byte value) {
    switch (value) {
    case 1:
      return jiJia;
    case 2:
      return zhengYing;
    case 3:
      return daoPian;
    case 4:
      return xiuMo;
    }
    return null;
  }

  public static Byte label2Id(final String value) {
    if (value == null) {
      return null;
    }
    if (jiJia.label.equals(value)) {
      return jiJia.value();
    } else if (zhengYing.label.equals(value)) {
      return zhengYing.value();
    } else if (daoPian.label.equals(value)) {
      return daoPian.value();
    } else if (xiuMo.label.equals(value)) {
      return xiuMo.value();
    }
    return null;
  }
}
