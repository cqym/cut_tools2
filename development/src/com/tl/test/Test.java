package com.tl.test;

import net.sf.json.JSONObject;


public class Test {
	public static String changeToBig(double value){
	    char[] hunit={'拾','佰','仟'};
	    char[] vunit={'万','亿'};
	    char[] digit={'零','壹','贰','叁','肆','伍','陆','柒','捌','玖'};
	    long midVal = (long)(value*100);                                      //转化成整形
	    String valStr=String.valueOf(midVal);                                //转化成字符串
	    String head=valStr.substring(0,valStr.length()-2);               //取整数部分
	    String rail=valStr.substring(valStr.length()-2);                     //取小数部分

	    String prefix="";                                                                 //整数部分转化的结果
	    String suffix="";                                                                  //小数部分转化的结果
	    //处理小数点后面的数
	    if(rail.equals("00")){                                                            //如果小数部分为0
	      suffix="整";
	    } else{
	      suffix=digit[rail.charAt(0)-'0']+"角"+digit[rail.charAt(1)-'0']+"分"; //否则把角分转化出来
	    }
	    //处理小数点前面的数
	    char[] chDig=head.toCharArray();                                       //把整数部分转化成字符数组
	    char zero='0';                                                                      //标志'0'表示出现过0
	    byte zeroSerNum = 0;                                                         //连续出现0的次数
	    for(int i=0;i<chDig.length;i++){
	    int idx=(chDig.length-i-1)%4;                                             //取段内位置
	      int vidx=(chDig.length-i-1)/4;                                             //取段位置
	      if(chDig[i]=='0'){                                                                 //如果当前字符是0
	        zeroSerNum++;                                                                //连续0次数递增
	        if(zero == '0'){                                                                  //标志
	          zero=digit[0];
	        } else if(idx==0 && vidx >0 &&zeroSerNum < 4){
	          prefix += vunit[vidx-1];
	          zero='0';
	        }
	        continue;
	      }
	      zeroSerNum = 0;                                    //连续0次数清零
	      if(zero != '0') {                                       //如果标志不为0,则加上,例如万,亿什么的
	        prefix+=zero;
	        zero='0';
	      }
	      prefix+=digit[chDig[i]-'0'];                      //转化该数字表示
	      if(idx > 0) prefix += hunit[idx-1];                 
	      if(idx==0 && vidx>0){
	        prefix+=vunit[vidx-1];                          //段结束位置应该加上段名如万,亿
	      }
	    }

	    if(prefix.length() > 0) prefix += '圆';          //如果整数部分存在,则有圆的字样
	    return prefix+suffix;                                  //返回正确表示
	  }

	public static void main(String[] args) {
//		String logoPath = "/upload/100113175538500481a646eb463b7aaf.JPG";
//		logoPath = logoPath.replaceAll("\\/", "\\\\");
		String v = "{proTools : {\"id\":\"root\",\"text\":\"Tasks\",\"children\":[{\"id\":\"0044730\",\"amount\":0,\"brandCode\":\"R217.79-1226.RE-CC09.2A\",\"brandCodeHistory\":\"\",\"createDateStr\":\"2010-07-14 16:05\",\"currencyName\":\"\",\"iconCls\":\"task\",\"leaf\":false,\"memo\":\"2010年7月14日 西航406\",\"parentId\":\"root\",\"productBrand\":\"万威制造\",\"productCode\":\"WW10-0044730\",\"productName\":\"刀杆\",\"productSortCode\":\"WW10\",\"productSortId\":\"xxxxx\",\"productSource\":\"自制\",\"productUnit\":\"把\",\"rebate\":0,\"salePrice\":0,\"salePriceDate\":\"null\",\"slaveFile\":\"\",\"stockPrice\":0,\"stockPriceDate\":\"[object Object]\",\"toolsId\":\"0044730\",\"children\":[{\"id\":\"xnode-373\",\"amount\":\"2\",\"brandCode\":\"C04008-T15P\",\"brandCodeHistory\":\"\",\"createDateStr\":\"2009-10-12 11:42\",\"currencyName\":\"\",\"iconCls\":\"task\",\"leaf\":true,\"memo\":\"\",\"parentId\":\"root\",\"productBrand\":\"SECO\",\"productCode\":\"C60-0018924\",\"productName\":\"备件\",\"productSortCode\":\"C60\",\"productSortId\":\"75\",\"productSource\":\"采购\",\"productUnit\":\"个\",\"rebate\":0,\"salePrice\":0,\"salePriceDate\":\"null\",\"slaveFile\":\"\",\"stockPrice\":0,\"stockPriceDate\":\"[object Object]\",\"toolsId\":\"0018924\"},{\"id\":\"xnode-388\",\"amount\":\"1\",\"brandCode\":\"T15P-2\",\"brandCodeHistory\":\"\",\"createDateStr\":\"2009-10-12 11:42\",\"currencyName\":\"\",\"iconCls\":\"task\",\"leaf\":true,\"memo\":\"\",\"parentId\":\"root\",\"productBrand\":\"SECO\",\"productCode\":\"C60-0022993\",\"productName\":\"备件\",\"productSortCode\":\"C60\",\"productSortId\":\"75\",\"productSource\":\"采购\",\"productUnit\":\"个\",\"rebate\":0,\"salePrice\":0,\"salePriceDate\":\"null\",\"slaveFile\":\"\",\"stockPrice\":0,\"stockPriceDate\":\"[object Object]\",\"toolsId\":\"0022993\"}]}]}}";
		JSONObject jsonObj = JSONObject.fromObject(v);
	}
}
