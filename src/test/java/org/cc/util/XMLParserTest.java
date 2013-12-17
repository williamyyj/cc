/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cc.util;

import java.io.File;
import org.cc.XMLObject;
import org.junit.Test;

/**
 *
 * @author william
 */
public class XMLParserTest {
	
	public XMLParserTest() {
	}
	

	@Test
	public void test_all() throws Exception {
        XMLParser p = new XMLParser();
        XMLObject xml = p.parse(new File("src/test/data/ebook_001.xml"),"BIG5");
        System.out.println(xml.toString("\t"));
	}
	
}
