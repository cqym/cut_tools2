package com.tl.common.util;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * ????: ???????????????
 * </p>
 */

public class StringHelper {

	/**
	 * 
	 * ?ж??????????
	 * 
	 */
	public static boolean isEmpty(String strOrig) {
		if (null == strOrig || "".equals(strOrig))
			return true;
		else
			return false;
	}

	/**
	 * 
	 * ???????????""
	 * 
	 * @param ?????
	 * 
	 * @return ?????????????
	 * 
	 */

	public static final String convertStringNull(String strOrig) {

		String strReturn = "";
		if (strOrig == null || strOrig.equals("null")) {
			strReturn = "";
		} else {
			strReturn = trim(strOrig.trim());
		}
		return strReturn;
	}

	/**
	 * 
	 * ??????????????????????????????????????
	 * 
	 * @param strOrigin
	 *            ?????
	 * 
	 * @param separator
	 *            ????
	 * 
	 * @return
	 * 
	 */

	public static final String[] parserString(String strOrigin, String separator) {
		try {
			StringTokenizer st;
			String strItem;
			if (strOrigin == null) {
				return null;
			}
			st = new StringTokenizer(strOrigin, separator);
			String[] returnValue = new String[st.countTokens()];
			int index = 0;
			while (st.hasMoreTokens()) {
				strItem = (String) st.nextToken();
				if (strItem != null && strItem.trim().length() != 0) {
					returnValue[index++] = strItem;
				}
			}
			return returnValue;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * ??????????????????????????????????"" *
	 * 
	 * @param strOrigin
	 *            ?????
	 * 
	 * @return ???????
	 * 
	 */
	public static String toChineseStr(String strOrigin) {

		if (strOrigin == null || strOrigin.equals("null")) {
			strOrigin = "";
		} else {
			strOrigin = strOrigin.trim();
		}
		try {
			strOrigin = new String(strOrigin.getBytes("ISO8859_1"), "GBK");
		} catch (Exception e) {
		}
		return strOrigin;
	}

	/**
	 * 
	 * ?????????????ISO8859_1?????????????????????""
	 * 
	 * @param strOrigin
	 *            strOrigin ????????????
	 * 
	 * @return
	 * 
	 */

	public static String toStandardStr(String strOrigin) {

		if (strOrigin == null || strOrigin.equals("null")) {
			strOrigin = "";
		} else {
			strOrigin = strOrigin.trim();
		}
		try {
			strOrigin = new String(strOrigin.getBytes("GBK"), "ISO8859_1");
		} catch (Exception e) {
		}

		return strOrigin;
	}

	/**
	 * 
	 * 
	 * 
	 * @param s
	 * 
	 * @param separatorSign
	 * 
	 * @return String[]
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static String[] split(String s, String separatorSign) {
		try {
			if (s == null)
				return null;
			int index = 0;

			java.util.Vector vec = new java.util.Vector();

			while (true) {
				index = s.indexOf(separatorSign, index);
				if (index < 0)
					break;
				vec.addElement(new Integer(index++));
			}

			int size = vec.size();
			if (size <= 0) {
				String[] ret = new String[1];
				ret[0] = s;
				return ret;
			}

			String[] strarr = new String[size + 1];
			Integer[] indArr = new Integer[size];
			vec.copyInto(indArr);

			// sort the index array for getting the string.
			java.util.Arrays.sort(indArr);
			int ind = 0;
			int len = strarr.length;
			for (int j = 0; j < (len - 1); j++) {
				strarr[j] = s.substring(ind, indArr[j].intValue());
				ind = indArr[j].intValue() + 1;
			}
			if (len > 0)
				strarr[len - 1] = s.substring(ind);
			return strarr;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * ?????" "??????????html???????
	 * 
	 * @param ?????
	 * 
	 * @return ?????????????
	 * 
	 */

	public static final String filterNullStringToHTMLSpace(String strOrigin) {
		String rets = "";

		if (strOrigin == null) {
			rets = "&nbsp;";
		} else if (strOrigin.length() == 0) {
			rets = "&nbsp;";
		} else {
			for (int i = 0; i < strOrigin.length(); i++) {
				if (strOrigin.charAt(i) == ' ') {
					rets += "&nbsp;";
				} else {
					rets += strOrigin.charAt(i);
				}
			}
		}

		return rets;
	}

	/**
	 * 
	 * ??????0????""???????????????""
	 * 
	 * @param strOrigin
	 *            strOrigin ????????????
	 * 
	 * @return
	 * 
	 */

	public static String convertZeroToSpace(String strOrigin) {
		if (strOrigin == null || strOrigin.equals("null")
				|| strOrigin.equals("0")) {
			strOrigin = "";
		} else {
			strOrigin = strOrigin.trim();
		}
		return strOrigin;
	}

	public static String trim(String s) {
		try {
			s = s.trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;

	}

	public static String toLowerCase(String strUp) {
		return strUp.toLowerCase();
	}

	/**
	 * 
	 * ?滻?????
	 * 
	 * @param src
	 *            ??????????
	 * 
	 * @param replace
	 *            ???滻????
	 * 
	 * @param dest
	 *            ?滻????
	 * 
	 * @return
	 * 
	 */

	public static String replaceAll(String src, String replace, String dest) {
		StringBuffer buf = new StringBuffer(src);
		String m_dest = "";

		if (dest != null) {
			m_dest = dest;
		}

		int i = 0;

		while ((i = buf.indexOf(replace, i)) != -1) {
			buf = new StringBuffer(buf.subSequence(0, i) + m_dest
					+ buf.substring(i + replace.length()));
			i += m_dest.length();
		}
		return buf.toString();
	}
	
	/**
	 * 去除字符串中的空格、回车、换行符、制表符 
	 */
	public static String replaceBlank(String str) { 
	   //Pattern p = Pattern.compile("\\s*|\t|\r|\n"); 
	   Pattern p = Pattern.compile("\\s|,|-|_*"); 
	   Matcher m = p.matcher(str); 
	   String afterStr = m.replaceAll("");
	   return afterStr;
	} 

	public static String getExtName(String s, char split){   
	   int i = s.lastIndexOf(split);   
	   int leg = s.length();   
	   return (i   >   0   ?   (i   +   1)   ==   leg   ?   "" : s.substring(i+1,s.length()) : "");   
	}  

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		//String s = "asdfas.af.afa.a.jpg";
		//System.out.println(getExtName(s, '.'));he-ll_o
		String str = "I a-m a, I am He--ll_o ok, \n n_ew line ffdsa!";
		//System.out.println(str);
		String afterStr = replaceBlank(str);
		System.out.println(afterStr);
		/*StringHelper sh = new StringHelper();
		String test = sh.filterNullStringToHTMLSpace("1 2");
		System.out.println("test===========" + test);
		String tests = " 500";
		String temp = tests.replaceFirst(" ", "1");
		//System.out.println("tests===========" + temp);*/
	}

}
