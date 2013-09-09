package org.cc.util;

import java.io.File;

/*
 *   在程式碼產生使用目前先使用MVEL
 *   (+ )目前先不支援
 */
public class CCELTokens extends CCBufferBase {

	public final static String node_prefix = "@{";
	public final static String node_suffix = "}";
	public final static int tk_text = 1;
	public final static int tk_indent = 2;
	public final static int tk_variable = 3;
	public final static int tk_node = 4;
	public final static int tk_num = 5;
	public final static int tk_string = 6;
	public final static int tk_bool = 7;
	public final static int tk_words = 8;

	public CCELTokens(File f, String enc) {
		super(f, enc);
	}

	public CCELTokens(String text) {
		super(text);
	}

	
	public class XOToken {

		public int p_start;
		public int p_end;
		public int line;
		public int pos;
		public String text;
	}

	public void test() {
		long ts = System.nanoTime();
		tk_init();
		char ch = 0;
		while ((ch = next()) > 0) {
			System.out.println(this);
		}
		long te = System.nanoTime();
		System.out.println("===== time : " + ((te - ts) / 1E9));
	}

	public static void main(String[] args) {
		CCELTokens tokens = new CCELTokens(new File("src/tml/bean.tml"), "UTF-8");
	    tokens.test();
	}
}
