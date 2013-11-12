package com.tl.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

public class StringTest {
	public static void main(String[] args) {
//		String content = "ArrayList";
//		List<String> list = new ArrayList<String>();
//		String reg = "[A-Z]rr+";
//		try {
//			 PatternCompiler compiler = new Perl5Compiler();
//			 org.apache.oro.text.regex.Pattern pattern = compiler.compile(reg.toString(),Perl5Compiler.CASE_INSENSITIVE_MASK);
//			 PatternMatcher matcher = new Perl5Matcher();
//			 if(matcher.contains(content, pattern)){
//				 MatchResult ma = matcher.getMatch();
//				 int c = ma.groups();
//				 for (int i = 0; i < c; i++) {  
//				    list.add(ma.group(i));
//				 }
//			 }else{
//				 //System.out.println(reg.toString() + " 匹配失败！");
//			 }
//			 //System.out.println(list);
//		} catch (MalformedPatternException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String sql = "1233,fsdfds,fafa";
		System.out.println(sql.replaceAll(",", "','"));
		
	}
}
