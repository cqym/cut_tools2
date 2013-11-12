//package com.tl.test;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import org.htmlparser.Parser;
//import org.htmlparser.filters.TagNameFilter;
//import org.htmlparser.tags.TableColumn;
//import org.htmlparser.tags.TableRow;
//import org.htmlparser.tags.TableTag;
//import org.htmlparser.util.NodeList;
//
///**
// * ??????????????IMEI???
// * 
// * @author Administrator
// * 
// */
//public class InternationalNumberPlanReader {
//	private FileWriter fw_verder = null;// ?????
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//
//		new InternationalNumberPlanReader().loadIMEIInfo();
//	}
//
//	/**
//	 * ????????????
//	 */
//	public void loadIMEIInfo() {
//		String str[] = { "35" , "44", "97", "98", "99", "49", "50", "51", "52", "10", "33", "31",
//		 "30", "91", "45", "01", "54", "53", "86" };
//
//		try {
//			for (int i = 0; i < str.length; i++) {
//
//				fw_verder = new FileWriter("E:/work/imei/euro/euro_" + str[i]
//						+ ".csv");
//
//				getMobileType(str[i], fw_verder);
//
//				fw_verder.close();
//			}
//
//		} catch (IOException e) {
//
//			e.printStackTrace();
//		} finally {
//			try {
//				fw_verder.close();
//			} catch (IOException e) {
//
//				e.printStackTrace();
//			}
//		}
//
//	}
//
//	/**
//	 * ????http://www.numberingplans.com/?page=plans&sub=imeinr&alpha_2_input=35&
//	 * current_page=23????????????????
//	 * 
//	 * @param vender
//	 */
//	public void getMobileType(String no, FileWriter fw) {
//
//		String url = "http://www.numberingplans.com/?page=plans&sub=imeinr&alpha_2_input="
//				+ no + "&current_page=1";
//		Parser parser = null;
//		NodeList list = null;
//		int startPage = 1;
//		int endPage = 1;
//		try {
//			parser = new Parser(url);
//			// ???????????????????????
//			list = parser.parse(new TagNameFilter("a"));
//			for (int i = 0; i < list.size(); i++) {
//				org.htmlparser.tags.LinkTag linkTag = (org.htmlparser.tags.LinkTag) list
//						.elementAt(i);
//
//				if (linkTag.getChildrenHTML().startsWith("last")) {
//					String href = linkTag.getAttribute("href");
//
//					String[] params = href.split("&");
//					for (int j = 0; j < params.length; j++) {
//						if (params[j].startsWith("current_page=")) {
//							endPage = Integer.parseInt(params[j].replaceAll(
//									"current_page=", ""));
//						}
//					}
//					break;
//				}
//			}
//			//System.out.println(endPage);
//			// ????????table?е????
//			for (int k = startPage; k <= endPage; k++) {
//				url = "http://www.numberingplans.com/?page=plans&sub=imeinr&alpha_2_input="
//						+ no + "&current_page=" + k;
//				parser = new Parser(url);
//				list = parser.parse(new TagNameFilter("table"));
//
//				for (int i = 0; i < list.size(); i++) {
//					TableTag node = (TableTag) list.elementAt(i);
//					List<IMEIInfo> m = null;
//
//					if ("AutoNumber1".equals(node.getAttribute("id"))) {// ??????
//
//						m = parseTableHtml(node);
//
//					}
//					if (m != null) {
//						for (Iterator iterator = m.iterator(); iterator
//								.hasNext();) {
//							IMEIInfo info = (IMEIInfo) iterator.next();
//							fw.write(info.toString() + "\n");
//						}
//					}
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	private List<IMEIInfo> parseTableHtml(TableTag table) {
//
//		List<IMEIInfo> m = new ArrayList<IMEIInfo>();
//		for (int j = 2; j < table.getRowCount(); j++) {
//			TableRow row = table.getRow(j);
//			TableColumn[] columns = row.getColumns();
//			if (columns.length >= 3) {
//				TableColumn col = columns[0];
//				String tac = col.getStringText().replaceAll("<(.)*>", "");
//				col = columns[1];
//				String factory = col.getStringText().replaceAll("<(.)*>","");
//														
//				col = columns[2];
//				String type = col.getStringText().replaceAll("<(.)*>", "");
//				IMEIInfo dp = new IMEIInfo();
//				dp.setTac(tac);
//				dp.setMenufactory(factory);
//
//				dp.setBrandAndModel(type);
//				m.add(dp);
//			}
//		}
//		return m;
//	}
//
//	/**
//	 * ?????url???????+???棬???????
//	 * 
//	 * @param url
//	 * @return
//	 */
//	private String formatModTypeURL(String url) {
//		String[] str = url.split(" ");
//		String retStr = "";
//		for (int i = 0; i < str.length; i++) {
//			if (retStr.length() > 0)
//				retStr += "+";
//			try {
//				retStr += URLEncoder.encode(str[i], "GBK");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}
//		return retStr;
//	}
//
//	class IMEIInfo {
//		private String tac;
//		private String menufactory;
//
//		private String brandAndModel;
//
//		public String getTac() {
//			return tac;
//		}
//
//		public void setTac(String tac) {
//			this.tac = tac;
//		}
//
//		public String getMenufactory() {
//			return menufactory;
//		}
//
//		public void setMenufactory(String menufactory) {
//			this.menufactory = menufactory;
//		}
//
//		public String getBrandAndModel() {
//			return brandAndModel;
//		}
//
//		public void setBrandAndModel(String brandAndModel) {
//			this.brandAndModel = brandAndModel;
//		}
//
//		@Override
//		public String toString() {
//
//			return getTac() + "," + getMenufactory() + "," + getBrandAndModel();
//		}
//
//	}
//}
