/*
 * Created on 2005-4-15
 *
 * Summary of regular-expression constructs										������ʽ�ṹ��飺
 * Construct Matches
 *	Characters																	�ַ�
 *		x The character x															x   �ַ� x
 *		\\ The backslash character													\\  ��б��
 *		\0n The character with octal value 0n (0 <= n <= 7)							\0n     ʮ������ (0 <= n <= 7)
 *		\0nn The character with octal value 0nn (0 <= n <= 7)						\0nn    ʮ������ 0nn (0 <= n <= 7)
 *		\0mnn The character with octal value 0mnn (0 <= m <= 3, 0 <= n <= 7)		\0mnn   ʮ������ 0mnn (0 <= m <= 3, 0 <= n <= 7)
 *		\xhh The character with hexadecimal value 0xhh								\xhh    ʮ������� 0xhh
 *		\\uhhhh The character with hexadecimal value 0xhhhh						\\uhhhh  ʮ������� 0xhhhh
 *		\t The tab character ('\u0009')												\t  �Ʊ�� ('\u0009')
 *		\n The newline (line feed) character ('\u000A')								\n  ���з� ('\u000A')
 *		\r The carriage-return character ('\u000D')									\r  �س��� ('\u000D')
 *		\f The form-feed character ('\u000C')										\f  The form-feed character ('\u000C')
 *		\a The alert (bell) character ('\u0007')									\a  The alert (bell) character ('\u0007')
 *		\e The escape character ('\u001B')											\e  esc��� ('\u001B')
 *		\cx The control character corresponding to x								\cx     x ��Ӧ�Ŀ��Ʒ�
 *
 *	 Character classes															�ַ���
 *	 [abc] a, b, or c (simple class)								 				[abc]       a, b, �� c (���ַ�)
 *	 [^abc] Any character except a, b, or c (negation)								[^abc]      ���� a, b, �� c ֮��������ַ�(��)
 *	 [a-zA-Z] a through z or A through Z, inclusive (range)							[a-zA-Z]    ��a �� z �� ��A �� Z������a,z,A,Z��(��Χ)
 *	 [a-d[m-p]] a through d, or m through p: [a-dm-p] (union)						[a-d[m-p]]  ��a �� d, �� ��m �� p: [a-dm-p] (����)
 *	 [a-z&&[def]] d, e, or f (intersection)											[a-z&&[def]]    d, e, �� f (����)
 *	 [a-z&&[^bc]] a through z, except for b and c: [ad-z] (subtraction)				[a-z&&[^bc]]    ��a �� z, �� b �� c ����: [ad-z] (�Ӽ�)
 *	 [a-z&&[^m-p]] a through z, and not m through p: [a-lq-z](subtraction)			[a-z&&[^m-p]]   ��a �� z, �������� m �� p: [a-lq-z](�Ӽ�)
 *
 * 	Predefined character classes												Ԥ�����ַ�����
 *	. Any character (may or may not match line terminators)							 .   �����ַ� (Ҳ���ܲ������н����)
 *	\d A digit: [0-9]																 \d  ����: [0-9]
 *	\D A non-digit: [^0-9]															 \D  ������: [^0-9]
 *	\s A whitespace character: [ \t\n\x0B\f\r]										 \s  ���ַ�: [ \t\n\x0B\f\r]
 *	\S A non-whitespace character: [^\s]											 \S  �ǿ��ַ�: [^\s]
 *	\w A word character: [a-zA-Z_0-9]												 \w  �����ַ�: [a-zA-Z_0-9]
 *	\W A non-word character: [^\w]													 \W  �ǵ����ַ�: [^\w]
 *
 *	POSIX character classes (US-ASCII only)										  POSIX �ַ��� (US-ASCII only)
 *	\p{Lower} A lower-case alphabetic character: [a-z]								\p{Lower}   Сд��ĸ�ַ�: [a-z]
 *	\p{Upper} An upper-case alphabetic character:[A-Z]								\p{Upper}   ��д��ĸ�ַ�:[A-Z]
 *	\p{ASCII} All ASCII:[\x00-\x7F]													\p{ASCII}   ���� ASCII:[\x00-\x7F]
 *	\p{Alpha} An alphabetic character:[\p{Lower}\p{Upper}]							\p{Alpha}   ������ĸ�ַ�:[\p{Lower}\p{Upper}]
 *	\p{Digit} A decimal digit: [0-9]												\p{Digit}   ʮ������: [0-9]
 *	\p{Alnum} An alphanumeric character:[\p{Alpha}\p{Digit}]						\p{Alnum}   �����ַ�:[\p{Alpha}\p{Digit}]
 *	\p{Punct} Punctuation: One of !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~					\p{Punct}   �����: ���� !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~
 *	\p{Graph} A visible character: [\p{Alnum}\p{Punct}]								\p{Graph}   �����ַ�: [\p{Alnum}\p{Punct}]
 *	\p{Print} A printable character: [\p{Graph}]									\p{Print}   �ɴ�ӡ�ַ�: [\p{Graph}]
 *	\p{Blank} A space or a tab: [ \t]												\p{Blank}   �ո���Ʊ��: [ \t]
 *	\p{Cntrl} A control character: [\x00-\x1F\x7F]									\p{Cntrl}   �����ַ�: [\x00-\x1F\x7F]
 *	\p{XDigit} A hexadecimal digit: [0-9a-fA-F]										\p{XDigit}  ʮ�������: [0-9a-fA-F]
 *	\p{Space} A whitespace character: [ \t\n\x0B\f\r]								\p{Space}   ���ַ�: [ \t\n\x0B\f\r]
 *
 *	Classes for Unicode blocks and categories									  Unicode �ַ���
 *	\p{InGreek} A character in the Greek block (simple block)						\p{InGreek}     ϣ�����ֵ��ַ� (simple block)
 *	\p{Lu} An uppercase letter (simple category)									\p{Lu}      ��д��ĸ (simple category)
 *	\p{Sc} A currency symbol														\p{Sc}      ���ҷ��
 *	\P{InGreek} Any character except one in the Greek block (negation)				\P{InGreek}     ��ϣ�������ַ���������ַ� (negation)
 *	[\p{L}&&[^\p{Lu}]]  Any letter except an uppercase letter (subtraction)			[\p{L}&&[^\p{Lu}]]  ���д��ĸ��������ַ� (subtraction)
 *
 *	Boundary matchers															 �߽�ƥ����
 *	^ The beginning of a line														^   һ�еĿ�ʼ
 *	$ The end of a line																$  һ�еĽ���
 *	\b A word boundary																\b  ���ʱ߽�
 *	\B A non-word boundary															\B  �ǵ��ʱ߽�
 *	\A The beginning of the input													\A  ����Ŀ�ʼ
 *	\G The end of the previous match												\G  ��ǰƥ��Ľ���
 *	\Z The end of the input but for the final terminator, if any					\Z  The end of the input but for the final terminator, if any
 *	\z The end of the input															\z  ����Ľ���
 *
 *	Greedy quantifiers															Greedy quantifiers ̰��ƥ�����ʣ�Greedy quantifiers ������֪������ĶԲ��ԣ�
 *	X? X, once or not at all													   	X?          X�����ֻ����һ��  (�����ַ�"?"��{0,1}����ȵ�)
 *	X* X, zero or more times													   	X*          X�����ֻ���ֶ��  (�����ַ�"*"��{0,}����ȵ�)
 *	X+ X, one or more times														   	X+          X���ٳ���һ��      (�����ַ�"+"�� {1,}����ȵ�)
 *	X{n} X, exactly n times														   	X{n}        X����n��
 *	X{n,} X, at least n times													   	X{n,}       X���ٳ���n��
 *	X{n,m} X, at least n but not more than m times								   	X{n,m}      X���ٳ���n�Σ������ᳬ��m��
 *
 *	Reluctant quantifiers														   Reluctant quantifiers
 *	X?? X, once or not at all													   	X??         X, �����ֻ����һ��
 *	X*? X, zero or more times													   	X*?         X, �����ֻ���ֶ��
 *	X+? X, one or more times													   	X+?         X, ���ٳ���һ��
 *	X{n}? X, exactly n times													   	X{n}?       X, ����n��
 *	X{n,}? X, at least n times													   	X{n,}?      X, ���ٳ���n��
 *	X{n,m}? X, at least n but not more than m times								   	X{n,m}?     X, ���ٳ���n�Σ������ᳬ��m��
 *
 *	Possessive quantifiers														   	Possessive quantifiers
 *	X?+ X, once or not at all													   	X?+     X, �����ֻ����һ��
 *	X*+ X, zero or more times													   	X*+         X, �����ֻ���ֶ��
 *	X++ X, one or more times													   	X++         X, ���ٳ���һ��
 *	X{n}+ X, exactly n times													   	X{n}+       X, ����n��
 *	X{n,}+ X, at least n times													   	X{n,}+      X, ���ٳ���n��
 *	X{n,m}+ X, at least n but not more than m times								   	X{n,m}+     X, ���ٳ���n�Σ������ᳬ��m��
 *
 *  Logical operators                                                             �߼������
 *	XY X followed by Y                                                               XY  Y����X����
 *	X|Y Either X or Y                                                                	X|Y     X �� Y
 *	(X) X, as a capturing group                                                      	(X)     X, as a capturing group
 *
 *	Back references                                                                  	��������
 *	\n Whatever the nth capturing group matched                                      	\n  Whatever the nth capturing group matched
 *
 *	Quotation                                                                        Quotation
 *	\ Nothing, but quotes the following character                                    	\   ���ú�����ַ�
 *	\Q Nothing, but quotes all characters until \E                                   	\Q  �������е��ַ�ֱ�� \E ����
 *	\E Nothing, but ends quoting started by \Q                                       	\E  ������ \Q ��ʼ������
 *
 *	Special constructs (non-capturing)                                               Special constructs (non-capturing)
 *	(?:X) X, as a non-capturing group                                                	(?:X)           X, as a non-capturing group
 *	(?idmsux-idmsux)  Nothing, but turns match flags on - off                        	(?idmsux-idmsux)    ƥ���־����
 *	(?idmsux-idmsux:X)   X, as a non-capturing group with the given flags on - off   	(?idmsux-idmsux:X)      X, as a non-capturing group with the given flags on
 *	(?=X) X, via zero-width positive lookahead	- off
 *	(?!X) X, via zero-width negative lookahead                                       	(?=X)           X, via zero-width positive lookahead
 *	(?<=X) X, via zero-width positive lookbehind                                     	(?!X)           X, via zero-width negative lookahead
 *	(?<!X) X, via zero-width negative lookbehind                                     	(?<=X)          X, via zero-width positive lookbehind
 *	(?>X) X, as an independent, non-capturing group                                  	(?<!X)          X, via zero-width negative lookbehind
 *	(?>X) X, as an independent, non-capturing group
 *
 *	Backslashes, escapes, and quoting
 *		��б���ַ�('\')����ת�壬��������ı��ж������������������Ļ����ܻ����
 *		���塣��ˣ����ʽ\\ƥ��
 *		������б�ܣ����ʽ\{ƥ�䵥�������š�
 *		���ѷ�б�ܷ���û�ж���ת�ƹ�����κ���ĸ���ǰ�涼�ᷢ�������Щ��������
 *		���Ժ��������ʽ����չ����б�ܿ��Է����κ�
 *		����ĸ���ǰ�棬��ʹ��û�ж���ת�幹��Ҳ���ᷢ�����
 *		��java���Թ淶��ָ������java�������Է��еķ�б���Ǳ�Ҫ�ģ���������Unicodeת
 *		�壬����������ͨ���ַ�ת�塣��ˣ�
 *		Ϊ�˱���������ʽ�������ԣ���java�ַ���Ҫд������б�ܡ����磬��������ʽ
 *		���ַ�'\b'����˸�'\\b'���?�ʱ߽硣'\(hello\)'����Ч�ģ����һ�������
 *		ʱ�����������
 *		'\\(hello\\)'��ƥ��(hello)��
 *
 *	Character Classes
 *
 *	     �ַ�����Գ����������ַ����ڲ������ҿ����ɲ���������������(&&)��ɡ�������
 *		������ǣ����е������ַ�϶����������в����������ٳ��ֹ�һ�Ρ�
 *		�����Ľ�����������������ͬʱ���ֵ������ַ�
 *
 *		�ַ������������ȼ����£����Ӹߵ��ͣ�
 *			1     ����ת��      \x
 *			2     ����      [...]
 *			3     ��Χ      a-z
 *			4     ����      [a-e][i-u]
 *			5     ����      [a-z&&[aeiou]]
 *
 *			��ע������ַ������Ч�ַ����磬���ַ����У�������ʽ.ʧȥ������ر���
 *			����-�����Ԫ�ַ�ķ�Χָʾ��
 *
 *		Line terminators
 *
 *			�н������һ���������ַ����У�������ʶ�����ַ����е�һ�еĽ������ж�����Ϊ
 *			���н����
 *
 *			���з�      ('\n'),
 *			�س����з�  ("\r\n"),
 *			�س���      ('\r'),
 *			��һ��      ('\u0085'),
 *			�зָ���    ('\u2028'), ��
 *			�ηָ���    ('\u2029).
 *
 *			������ UNIX_LINES ģʽ��Ψһ���н������ǻ��з�
 *			�����ָ���� DOTALL ��־������������ʽ.ƥ���κ��ַ�ֻ���н������⡣
 *			ȷʡ���ʱ���������������У�������ʽ^��$�����н����ֻƥ�俪ʼ�ͽ���
 *			������ MULTILINE ģʽ����^ƥ������Ŀ�ʼ�������н����֮�󣬳����������
 *			�Ľ���
 *			��MULTILINE ģʽ�£�$ƥ�������н����֮ǰ�����������Ľ���
 *
 *		Groups and capturing
 *
 *			���鲶��ͨ������ҵ�˳�򣬸�����ŵ��������������磬�ڱ��ʽ((A)(B(C)))��
 *			�����ĸ��飺
 *			1     ((A)(B(C)))
 *			2     (A)
 *			3     (B(C))
 *			4     (C)
 *
 *			0����������ʽ��
 *			���鲶��֮���������������Ϊ��ƥ�����У��������е�ÿһ�������ƥ�������
 *			�ж��ᱻ����������ͨ��������ã�������������п����ں���ı��ʽ�б��ٴ�ʹ��
 *			��
 *			���ң���ƥ����������Ժ󻹿���ͨ��ƥ���������ҵ���
 *			��һ����������ı����񵽵�����ͨ���Ǳ��������������������ƥ��Ķ��е��Ӷ�
 *			�С����һ�����鱻�ڶ�����ֵ����ʹʧ�ܣ������һ�α������ֵҲ�ᱻ����������
 *			���磬
 *			���ʽ(a(b)?)+ƥ��"aba"��"b"��Ϊ�ӷ��顣�ڿ�ʼƥ���ʱ����ǰ����������붼
 *			�������
 *			��(?��ʼ�ķ�������ȫ�ģ����貶��ķ��鲻�Ჶ���κ��ı���Ҳ��������������
 *
 *		Unicode support
 *
 *			Unicode Technical Report #18: Unicode Regular Expression Guidelinesͨ����΢���﷨�ı�ʵ���˸����ε�֧�֡�
 *			��java�����У���\u2014 �����ת�����У�java���Թ淶�У�3.3�ṩ�˴��?��
 *			��
 *			Ϊ�˱���ʹ�ô��ļ�����̶�ȡ��unicodeת���ַ�������ʽ������Ҳֱ��ʵ������
 *			��ת�ơ���ˣ��ַ�"\u2014"��"\\u2014"��Ȼ����ȣ����Ǳ����ͬһ��ģʽ������
 *			ƥ��
 *			ʮ�������0x2014��
 *
 *			��Perl�У�unicode��ͷ��౻д��\p,\P�����������prop���ԣ�\p{prop}����ƥ�䣬
 *			��\P{prop}������ƥ�䡣��ͨ��ǰ׺Inָ������Ϊ��nMongolian֮�С�
 *			����ͨ�������ǰ׺Isָ���� \p{L} �� \p{IsL} ������ Unicode ��ĸ����ͷ������
 *			��ʹ�����ַ�����ڲ����ⲿ��
 *
 *			The Unicode Standard, Version 3.0ָ����֧�ֵĿ�ͷ��ࡣ��������ڵ�14�º� Unicode Character
 *			Database�е� Blocks-3.txt �ļ����壬
 *			���ո��޳��ˡ�����Basic Latin"�����  "BasicLatin"����������ֱ�������88ҳ
 *			����4-5��
 *
 */
package com.tl.common.util;

import java.util.*;

import org.apache.oro.text.regex.*;

/**
 * ����: ʹ��������ʽ��֤��ݻ���ȡ���,���еķ���ȫΪ��̬��<br/> ������ҽ�����ඨ�����һ������ ������ֻ��static
 * �����ǲ�newȥ����ģ�Ҳ���ò��������ˣ�<br/> ����Ϊ��һ�� regexpHash(HashMapʵ��)��Ϊ�˿��Զ�̬
 * ��������ʽ����֪���ǲ������̫����:) ����
 * 
 * <pre>
 * ��Ҫ����:1. isHardRegexpValidate(String source, String regexp)
 *             ��ִ�Сд���е������ʽ����
 *          2. isSoftRegexpValidate(String source, String regexp)
 *             ����ִ�Сд�������ʽ����
 *          3. getHardRegexpMatchResult(String source, String regexp)
 *             ������Ҫ��������(��Сд���е������ʽ����)
 *          4. getSoftRegexpMatchResult(String source, String regexp)
 *             ������Ҫ��������(����ִ�Сд�������ʽ����)
 *          5  getHardRegexpArray(String source, String regexp)
 *             ������Ҫ��������(��Сд���е������ʽ����)
 *          6. getSoftRegexpMatchResult(String source, String regexp)
 *             ������Ҫ��������(����ִ�Сд�������ʽ����)
 *          7.  getBetweenSeparatorStr(final String originStr,final char leftSeparator,final char rightSeparator)
 *             �õ�ָ���ָ����м���ַ�ļ���
 *             
 * �����ʽĿǰ�� 25��
 *           1.ƥ��ͼ��              icon_regexp;
 *  2 ƥ��email��ַ         email_regexp;
 *  3 ƥ��ƥ�䲢��ȡurl      url_regexp;
 *  4 ƥ�䲢��ȡhttp        http_regexp;
 *  5.ƥ������              date_regexp;
 *  6 ƥ��绰              phone_regexp;
 *  7 ƥ�����֤           ID_card_regexp;
 *  8 ƥ���ʱ����          ZIP_regexp
 *  9. �����������ַ��ƥ�� (�ַ��в�������� ��ѧ�η���&circ; �����' ˫���&quot; �ֺ�; ����, ñ��:
 *  ��ѧ����- �Ҽ�����&gt; �������&lt;  ��б��\ ���ո�,�Ʊ��,�س����
 *  non_special_char_regexp;
 *  10 ƥ��Ǹ����������� + 0)           non_negative_integers_regexp;
 *  11  ƥ�䲻������ķǸ����������� &gt; 0)    non_zero_negative_integers_regexp;
 *  12 ƥ��������             positive_integer_regexp;
 *  13  ƥ������������� + 0��         non_positive_integers_regexp;
 *  14 ƥ�为����            negative_integers_regexp;
 *  15. ƥ������             integer_regexp;
 *  16 ƥ��Ǹ������������ + 0��           non_negative_rational_numbers_regexp
 *  17. ƥ�������              positive_rational_numbers_regexp
 *  18 ƥ������������� + 0��   non_positive_rational_numbers_regexp;
 *  19 ƥ�为������            negative_rational_numbers_regexp;
 *  20 .ƥ�両����             rational_numbers_regexp;
 *  21. ƥ����26��Ӣ����ĸ��ɵ��ַ�          letter_regexp;
 *  22. ƥ����26��Ӣ����ĸ�Ĵ�д��ɵ��ַ�     upward_letter_regexp;
 *  23 ƥ����26��Ӣ����ĸ��Сд��ɵ��ַ�      lower_letter_regexp
 *  24 ƥ�������ֺ�26��Ӣ����ĸ��ɵ��ַ�      letter_number_regexp;
 *  25  ƥ�������֡�26��Ӣ����ĸ�����»�����ɵ��ַ�
 *  letter_number_underline_regexp;
 * </pre>
 * 
 */
@SuppressWarnings("unchecked")
public final class Regexp {

	/** �����������Ӧ�ָ��� */
	static final Set SEPARATOR_SET = new TreeSet();
	{
		SEPARATOR_SET.add("(");
		SEPARATOR_SET.add(")");
		SEPARATOR_SET.add("[");
		SEPARATOR_SET.add("]");
		SEPARATOR_SET.add("{");
		SEPARATOR_SET.add("}");
		SEPARATOR_SET.add("<");
		SEPARATOR_SET.add(">");
	}

	/** ��Ÿ��������ʽ(��key->value����ʽ) */
	public static HashMap regexpHash = new HashMap();

	/** ��Ÿ��������ʽ(��key->value����ʽ) */
	public static List matchingResultList = new ArrayList();

	private Regexp() {
	}

	/**
	 * ���� Regexp ʵ��
	 * 
	 * @return
	 */
	public static Regexp getInstance() {
		return new Regexp();
	}

	/**
	 * ƥ��ͼ�� <br>
	 * 
	 * ��ʽ: /���·��/�ļ���.��׺ (��׺Ϊgif,dmp,png)
	 * 
	 * ƥ�� : /forum/head_icon/admini2005111_ff.gif �� admini2005111.dmp<br>
	 * 
	 * ��ƥ��: c:/admins4512.gif
	 * 
	 */
	public static final String icon_regexp = "^(/{0,1}\\w){1,}\\.(gif|dmp|png|jpg)$|^\\w{1,}\\.(gif|dmp|png|jpg)$";
	/**
	 * ƥ��email��ַ <br>
	 * 
	 * ��ʽ: XXX@XXX.XXX.XX
	 * 
	 * ƥ�� : foo@bar.com �� foobar@foobar.com.au <br>
	 * 
	 * ��ƥ��: foo@bar �� $$$@bar.com
	 * 
	 */
	public static final String email_regexp = "(?:\\w[-._\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3}$)";

	/**
	 * ƥ��ƥ�䲢��ȡurl <br>
	 * 
	 * ��ʽ: XXXX://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX
	 * 
	 * ƥ�� : http://www.suncer.com ��news://www<br>
	 * 
	 * ��ȡ(MatchResult matchResult=matcher.getMatch()): matchResult.group(0)=
	 * http://www.suncer.com:8080/index.html?login=true matchResult.group(1) =
	 * http matchResult.group(2) = www.suncer.com matchResult.group(3) = :8080
	 * matchResult.group(4) = /index.html?login=true
	 * 
	 * ��ƥ��: c:\window
	 * 
	 */
	public static final String url_regexp = "(\\w+)://([^/:]+)(:\\d*)?([^#\\s]*)";

	/**
	 * ƥ�䲢��ȡhttp <br>
	 * 
	 * ��ʽ: http://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX �� ftp://XXX.XXX.XXX ��
	 * https://XXX
	 * 
	 * ƥ�� : http://www.suncer.com:8080/index.html?login=true<br>
	 * 
	 * ��ȡ(MatchResult matchResult=matcher.getMatch()): matchResult.group(0)=
	 * http://www.suncer.com:8080/index.html?login=true matchResult.group(1) =
	 * http matchResult.group(2) = www.suncer.com matchResult.group(3) = :8080
	 * matchResult.group(4) = /index.html?login=true
	 * 
	 * ��ƥ��: news://www
	 * 
	 */
	public static final String http_regexp = "(http|https|ftp)://([^/:]+)(:\\d*)?([^#\\s]*)";

	/**
	 * ƥ������ <br>
	 * 
	 * ��ʽ(��λ��Ϊ0): XXXX-XX-XX �� XXXX XX XX �� XXXX-X-X <br>
	 * 
	 * ��Χ:1900--2099 <br>
	 * 
	 * ƥ�� : 2005-04-04 <br>
	 * 
	 * ��ƥ��: 01-01-01
	 * 
	 */
	public static final String date_regexp = "^((((19){1}|(20){1})d{2})|d{2})[-\\s]{1}[01]{1}d{1}[-\\s]{1}[0-3]{1}d{1}$";// ƥ������

	/**
	 * ƥ��绰 <br>
	 * 
	 * ��ʽΪ: 0XXX-XXXXXX(10-13λ��λ����Ϊ0) ��0XXX XXXXXXX(10-13λ��λ����Ϊ0) �� <br>
	 * (0XXX)XXXXXXXX(11-14λ��λ����Ϊ0) �� XXXXXXXX(6-8λ��λ��Ϊ0) ��
	 * XXXXXXXXXXX(11λ��λ��Ϊ0) <br>
	 * 
	 * ƥ�� : 0371-123456 �� (0371)1234567 �� (0371)12345678 �� 010-123456 ��
	 * 010-12345678 �� 12345678912 <br>
	 * 
	 * ��ƥ��: 1111-134355 �� 0123456789
	 * 
	 */
	public static final String phone_regexp = "^(?:0[0-9]{2,3}[-\\s]{1}|\\(0[0-9]{2,4}\\))[0-9]{6,8}$|^[1-9]{1}[0-9]{5,7}$|^[1-9]{1}[0-9]{10}$";

	/**
	 * ƥ�����֤ <br>
	 * 
	 * ��ʽΪ: XXXXXXXXXX(10λ) �� XXXXXXXXXXXXX(13λ) �� XXXXXXXXXXXXXXX(15λ) ��
	 * XXXXXXXXXXXXXXXXXX(18λ) <br>
	 * 
	 * ƥ�� : 0123456789123 <br>
	 * 
	 * ��ƥ��: 0123456
	 * 
	 */
	public static final String ID_card_regexp = "^\\d{10}|\\d{13}|\\d{15}|\\d{18}$";

	/**
	 * ƥ���ʱ���� <br>
	 * 
	 * ��ʽΪ: XXXXXX(6λ) <br>
	 * 
	 * ƥ�� : 012345 <br>
	 * 
	 * ��ƥ��: 0123456
	 * 
	 */
	public static final String ZIP_regexp = "^[0-9]{6}$";// ƥ���ʱ����

	/**
	 * �����������ַ��ƥ�� (�ַ��в�������� ��ѧ�η���^ �����' ˫���" �ֺ�; ����, ñ��: ��ѧ����- �Ҽ�����> �������<
	 * ��б��\ ���ո�,�Ʊ��,�س���� )<br>
	 * 
	 * ��ʽΪ: x �� һ��һ�ϵ��ַ� <br>
	 * 
	 * ƥ�� : 012345 <br>
	 * 
	 * ��ƥ��: 0123456
	 * 
	 */
	public static final String non_special_char_regexp = "^[^'\"\\;,:-<>\\s].+$";// ƥ���ʱ����

	/**
	 * ƥ��Ǹ����������� + 0)
	 */
	public static final String non_negative_integers_regexp = "^\\d+$";

	/**
	 * ƥ�䲻������ķǸ����������� > 0)
	 */
	public static final String non_zero_negative_integers_regexp = "^[1-9]+\\d*$";

	/**
	 * 
	 * ƥ��������
	 * 
	 */
	public static final String positive_integer_regexp = "^[0-9]*[1-9][0-9]*$";

	/**
	 * 
	 * ƥ������������� + 0��
	 * 
	 */
	public static final String non_positive_integers_regexp = "^((-\\d+)|(0+))$";

	/**
	 * 
	 * ƥ�为����
	 * 
	 */
	public static final String negative_integers_regexp = "^-[0-9]*[1-9][0-9]*$";

	/**
	 * 
	 * ƥ������
	 * 
	 */
	public static final String integer_regexp = "^-?\\d+$";

	/**
	 * 
	 * ƥ��Ǹ������������ + 0��
	 * 
	 */
	public static final String non_negative_rational_numbers_regexp = "^\\d+(\\.\\d+)?$";

	/**
	 * 
	 * ƥ�������
	 * 
	 */
	public static final String positive_rational_numbers_regexp = "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";

	/**
	 * 
	 * ƥ������������� + 0��
	 * 
	 */
	public static final String non_positive_rational_numbers_regexp = "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";

	/**
	 * 
	 * ƥ�为������
	 * 
	 */
	public static final String negative_rational_numbers_regexp = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$";

	/**
	 * 
	 * ƥ�両����
	 * 
	 */
	public static final String rational_numbers_regexp = "^(-?\\d+)(\\.\\d+)?$";

	/**
	 * 
	 * ƥ����26��Ӣ����ĸ��ɵ��ַ�
	 * 
	 */
	public static final String letter_regexp = "^[A-Za-z]+$";

	/**
	 * 
	 * ƥ����26��Ӣ����ĸ�Ĵ�д��ɵ��ַ�
	 * 
	 */
	public static final String upward_letter_regexp = "^[A-Z]+$";

	/**
	 * 
	 * ƥ����26��Ӣ����ĸ��Сд��ɵ��ַ�
	 * 
	 */
	public static final String lower_letter_regexp = "^[a-z]+$";

	/**
	 * 
	 * ƥ�������ֺ�26��Ӣ����ĸ��ɵ��ַ�
	 * 
	 */
	public static final String letter_number_regexp = "^[A-Za-z0-9]+$";

	/**
	 * 
	 * ƥ�������֡�26��Ӣ����ĸ�����»�����ɵ��ַ�
	 * 
	 */
	public static final String letter_number_underline_regexp = "^\\w+$";

	/**
	 * ��������ʽ (��key->value����ʽ�洢)
	 * 
	 * @param regexpName
	 *            �������ʽ��� `
	 * @param regexp
	 *            �������ʽ����
	 */
	public void putRegexpHash(String regexpName, String regexp) {
		regexpHash.put(regexpName, regexp);
	}

	/**
	 * �õ������ʽ���� (ͨ��key����ȡ��value[�����ʽ����])
	 * 
	 * @param regexpName
	 *            �����ʽ���
	 * 
	 * @return �����ʽ����
	 */
	public String getRegexpHash(String regexpName) {
		if (regexpHash.get(regexpName) != null) {
			return ((String) regexpHash.get(regexpName));
		} else {
			//System.out.println("��regexpHash��û�д������ʽ");
			return "";
		}
	}

	/**
	 * ��������ʽ��ŵ�Ԫ
	 */
	public void clearRegexpHash() {
		regexpHash.clear();
		return;
	}

	/**
	 * ��Сд���е������ʽ����
	 * 
	 * @param source
	 *            �����Դ�ַ�
	 * 
	 * @param regexp
	 *            ����������ʽ
	 * 
	 * @return ���Դ�ַ���Ҫ�󷵻���,���򷵻ؼ� ��:
	 *         Regexp.isHardRegexpValidate("ygj@suncer.com.cn",email_regexp) ������
	 */
	public static boolean isHardRegexpValidate(String source, String regexp) {
		try {
			// ���ڶ��������ʽ����ģ������
			PatternCompiler compiler = new Perl5Compiler();

			// �����ʽ�Ƚ��������
			PatternMatcher matcher = new Perl5Matcher();

			// ʵ���С��Сд���е������ʽģ��
			Pattern hardPattern = compiler.compile(regexp);

			// ����������
			return matcher.contains(source, hardPattern);

		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ����ִ�Сд�������ʽ����
	 * 
	 * @param source
	 *            �����Դ�ַ�
	 * 
	 * @param regexp
	 *            ����������ʽ
	 * 
	 * @return ���Դ�ַ���Ҫ�󷵻���,���򷵻ؼ�
	 *         Regexp.isHardRegexpValidate("ygj@suncer.com.cn",email_regexp) ������
	 */
	public static boolean isSoftRegexpValidate(String source, String regexp) {
		try {
			// ���ڶ��������ʽ����ģ������
			PatternCompiler compiler = new Perl5Compiler();

			// �����ʽ�Ƚ��������
			PatternMatcher matcher = new Perl5Matcher();

			// ʵ����ִ�Сд�������ʽģ��
			Pattern softPattern = compiler.compile(regexp,
					Perl5Compiler.CASE_INSENSITIVE_MASK);

			// ����������ֵ֤
			return matcher.contains(source, softPattern);

		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ������Ҫ��������(��Сд���е������ʽ����)
	 * 
	 * @param source
	 *            �����Դ�ַ�
	 * 
	 * @param regexp
	 *            ����������ʽ
	 * 
	 * @return ���Դ�ַ���Ҫ�󷵻���Ҫ��������,���򷵻� "null"�ؼ��� <br>
	 *         �� : MatchResult matchResult =
	 *         getHardRegexpMatchResult("http://www.suncer.com:8080/index.html?login=true",Regexp.url_regexp)
	 *         �õ�ȡ����ƥ��ֵΪ matchResult.group(0)=
	 *         http://www.suncer.com:8080/index.html?login=true
	 *         matchResult.group(1) = http matchResult.group(2) = www.suncer.com
	 *         matchResult.group(3) = :8080 matchResult.group(4) =
	 *         /index.html?login=true
	 * 
	 */
	public static MatchResult getHardRegexpMatchResult(String source,
			String regexp) {
		try {
			// ���ڶ��������ʽ����ģ������
			PatternCompiler compiler = new Perl5Compiler();

			// �����ʽ�Ƚ��������
			PatternMatcher matcher = new Perl5Matcher();

			// ʵ���С��Сд���е������ʽģ��
			Pattern hardPattern = compiler.compile(regexp);

			// �����������ȷ,����ȡ����������
			if (matcher.contains(source, hardPattern)) {
				// MatchResult result=matcher.getMatch();
				return matcher.getMatch();
			}
		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ������Ҫ��������(����ִ�Сд�������ʽ����)
	 * 
	 * @param source
	 *            �����Դ�ַ�
	 * 
	 * @param regexp
	 *            ����������ʽ
	 * 
	 * @return ���Դ�ַ���Ҫ�󷵻���Ҫ��������,���򷵻� "null"�ؼ��� �� : MatchResult matchResult =
	 *         getHardRegexpMatchResult("http://www.suncer.com:8080/index.html?login=true",Regexp.url_regexp)
	 *         �õ�ȡ����ƥ��ֵΪ matchResult.group(0)=
	 *         http://www.suncer.com:8080/index.html?login=true
	 *         matchResult.group(1) = http matchResult.group(2) = www.suncer.com
	 *         matchResult.group(3) = :8080 matchResult.group(4) =
	 *         /index.html?login=true
	 */
	public static MatchResult getSoftRegexpMatchResult(String source,
			String regexp) {
		try {
			// ���ڶ��������ʽ����ģ������
			PatternCompiler compiler = new Perl5Compiler();

			// �����ʽ�Ƚ��������
			PatternMatcher matcher = new Perl5Matcher();

			// ʵ����ִ�Сд�������ʽģ��
			Pattern softPattern = compiler.compile(regexp,
					Perl5Compiler.CASE_INSENSITIVE_MASK);

			// �����������ȷ,����ȡ����������
			if (matcher.contains(source, softPattern)) {
				// MatchResult result=matcher.getMatch();
				return matcher.getMatch();
			}

		} catch (MalformedPatternException e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * ������Ҫ��������(��Сд���е������ʽ����)
	 * 
	 * @param source
	 *            �����Դ�ַ�
	 * 
	 * @param regexp
	 *            ����������ʽ
	 * 
	 * @return ���Դ�ַ���Ҫ�󷵻���Ҫ��������,{@link #getHardRegexpMatchResult(String, String) ʹ�÷����μ�getHardRegexpMatchResult(String, String)}
	 */
	public static ArrayList getHardRegexpArray(String source, String regexp) {
		List tempList = new ArrayList();

		try {
			// ���ڶ��������ʽ����ģ������
			PatternCompiler compiler = new Perl5Compiler();

			// ʵ���С��Сд���е������ʽģ��
			Pattern hardPattern = compiler.compile(regexp);

			// �����ʽ�Ƚ��������
			PatternMatcher matcher = new Perl5Matcher();

			// �����������ȷ,����ȡ����������
			if (matcher.contains(source, hardPattern)) {
				// ���ȡ��ֵ�������ʽ�Ƚ����������
				MatchResult matchResult = matcher.getMatch();
				for (int i = 0; i < matchResult.length()
						&& matchResult.group(i) != null; i++) {
					tempList.add(i, matchResult.group(i));
				}
			}
		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}
		return (ArrayList) tempList;
	}

	/**
	 * ������Ҫ��������(����ִ�Сд�������ʽ����)
	 * 
	 * @param source
	 *            �����Դ�ַ�
	 * 
	 * @param regexp
	 *            ����������ʽ
	 * 
	 * @return ���Դ�ַ���Ҫ�󷵻���Ҫ��������{@link #getHardRegexpMatchResult(String, String) ʹ�÷����μ�getHardRegexpMatchResult(String, String)}
	 */
	public static ArrayList getSoftRegexpArray(String source, String regexp) {
		List tempList = new ArrayList();

		try {
			// ���ڶ��������ʽ����ģ������
			PatternCompiler compiler = new Perl5Compiler();
			// �����ʽ�Ƚ��������
			PatternMatcher matcher = new Perl5Matcher();
			// ʵ����ִ�Сд�������ʽģ��
			Pattern softPattern = compiler.compile(regexp,
					Perl5Compiler.CASE_INSENSITIVE_MASK);

			if (matcher.contains(source, softPattern)) {
				// �����������ȷ,����ȡ����������
				MatchResult matchResult = matcher.getMatch();
				for (int i = 0; i < matchResult.length()
						&& matchResult.group(i) != null; i++) {
					tempList.add(i, matchResult.group(i));
				}
			}
		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}
		return (ArrayList) tempList;
	}

	/**
	 * <pre>
	 * �õ�ָ���ָ����м���ַ�ļ���,
	 *              ˵��: 1.�ָ���Ϊ ()��[]��{}��&lt;&gt; �е�һ��
	 *                        2.�õ��ļ����� ASCII ����������
	 *              ��       String originStr=&quot;((([a]+[b])/[c])-24)+[d]&quot;;
	 *              ��          getStrBetweenSeparator(originStr,&quot;[&quot;,&quot;]&quot;));
	 *                           ���ؽ������ĸ�Ԫ��  [a, b, c, d],
	 *                          �� ASCII ����������
	 * </pre>
	 * 
	 * @param originStr
	 *            Ҫ��ȡ��Դ�ַ�
	 * @param leftSeparator
	 *            ��ߵķָ���
	 * @param rightSeparator
	 *            �ұߵķָ���
	 * @return ָ���ָ����м���ַ�ļ���
	 */
	public static Set getBetweenSeparatorStr(final String originStr,
			final char leftSeparator, final char rightSeparator) {
		Set variableSet = new TreeSet();
		if (StringHelper.isEmpty(originStr)) {
			return variableSet;
		}
		String sTempArray[] = originStr.split("(\\" + leftSeparator + ")");
		for (int i = 1; i < sTempArray.length; i++) {
			int endPosition = sTempArray[i].indexOf(rightSeparator);
			String sTempVariable = sTempArray[i].substring(0, endPosition);
			variableSet.add(sTempVariable);
		}
		return variableSet;
	}

	@SuppressWarnings("unused")
	public static void main(String a[]) {

		PatternCompiler compiler = new Perl5Compiler();
		PatternMatcher matcher = new Perl5Matcher();
		MatchResult matchResult = matcher.getMatch();

		String email = "wuzhi2000@hotmail.com.cn";
		String email_regexp = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
		String url = "http://www";
		String url_regexp = "^(?:http|https|ftp)://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$";// ƥ��url
		String date = "1111-1-9";
		String date_regexp = "^[1-9]{1}[0-9]{0,3}[-\\s][0-9]{1,2}[-\\s][0-9]{1,2}$";// ƥ������
		String phone = "010-1234567";
		String phone_regexp = "^(?:0[0-9]{2,4}[-\\s]{1}|\\(0[0-9]{2,4}\\))[0-9]{6,8}$|^[0-9]{6,8}$";// ƥ��绰
		String icon = "/he//fff/aaaq34.gif";
		String icon_regexp = "^(/{0,1}\\w){1,}\\.(gif|dmp|png)$|^\\w{1,}\\.(gif|dmp|png)$";
		String name = "\"^";
		String number = "023";
		String pic = "forum/head_icons/anoymous20050428125334.jpg";
		//System.out
			//	.println(Regexp.isSoftRegexpValidate(pic, Regexp.icon_regexp));

		/***********************************************************************
		 * 
		 **********************************************************************/

		try {

			PatternCompiler compiler1 = new Perl5Compiler();
			PatternMatcher matcher1 = new Perl5Matcher();
			MatchResult matchResult1 = matcher1.getMatch();

			// һ����ȡhtml��ǩ���Ե�����
			String regexpForFontTag = "<\\s*font\\s+([^>]*)\\s*>";
			String regexpForFontAttrib = "([a-z]+)\\s*=\\s*\"([^\"]+)\"";

			String html = " <font face=\"Arial, serif\" size=\"+2\" color=\"red\">";
			//System.out.println(regexpForFontTag);
			//System.out.println(regexpForFontAttrib);
			//System.out.println(html);

			Pattern p1 = compiler1.compile(regexpForFontTag,
					Perl5Compiler.CASE_INSENSITIVE_MASK);
			Pattern p2 = compiler1.compile(regexpForFontAttrib,
					Perl5Compiler.CASE_INSENSITIVE_MASK);
			//System.out.println(matcher1.contains(html, p1));
			if (matcher1.contains(html, p1)) {
				MatchResult result = matcher1.getMatch();
				//System.out.println(result.group(1));
				String attribs = result.group(1);

				PatternMatcherInput input = new PatternMatcherInput(attribs);
				//System.out.println(matcher1.contains(input, p2));
				while (matcher1.contains(input, p2)) {
					result = matcher1.getMatch();
					//System.out.println(result.group(1) + " : "
					//		+ result.group(2));
				}
			}

		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}

		/***********************************************************************
		 * һ����ȡhttp������ String
		 **********************************************************************/
		try {
			PatternCompiler compiler2 = new Perl5Compiler();
			PatternMatcher matcher2 = new Perl5Matcher();
			MatchResult matchResult2 = matcher2.getMatch();
			String http = "http://www.suncer.com:8080/index.html?login=true";
			String http_regexp = "(\\w+)://([^/:]+)(:\\d*)?([^#\\s]*)";
			Pattern p1 = compiler2.compile(http_regexp,
					Perl5Compiler.CASE_INSENSITIVE_MASK);
			//System.out.println(matcher2.contains(http, p1));
			if (matcher2.contains(http, p1)) {
				MatchResult result = matcher2.getMatch();
				//System.out.println(result.group(1));
				String attribs = result.group(1);

				for (int i = 0; i < result.length() && result.group(i) != null; i++) {
					//System.out.println(i + " : " + result.group(i));
				}
			}
		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}

		// һ����ȡ�ַ��е������� [ ] ����ַ������

		String expression = "((([a]+[b])/[c])-24)+[d]";
		//System.out.println(getBetweenSeparatorStr(expression, '[', ']'));

	}

}