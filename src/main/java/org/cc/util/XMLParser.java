package org.cc.util;


import java.io.*;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.cc.XMLObject;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class XMLParser {
	private SAXParser _parser;
	private String _xpath;
	private String _dtd;

	/* ------------------------------------------------------------ */
	/**
	 * Construct
	 */
	
	public XMLParser() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		//boolean validating_dft = factory.getClass().toString().startsWith("org.apache.xerces.");
		//String validating_prop = System.getProperty("org.mortbay.xml.XmlParser.Validating", validating_dft ? "true" : "false");
		setValidating(false);
	}

	public XMLParser(boolean validating) {
		setValidating(validating);
	}

	/* ------------------------------------------------------------ */
	public void setValidating(boolean validating) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(validating);
			_parser = factory.newSAXParser();

			try {
				if (validating)
					_parser.getXMLReader().setFeature(
							"http://apache.org/xml/features/validation/schema",
							validating);
			} catch (Exception e) {
				e.printStackTrace();
			}

			_parser.getXMLReader().setFeature(
					"http://xml.org/sax/features/validation", validating);
			_parser.getXMLReader().setFeature(
					"http://xml.org/sax/features/namespaces", validating);
			_parser.getXMLReader().setFeature(
					"http://xml.org/sax/features/namespace-prefixes",
					validating);
		} catch (Exception e) {
			throw new Error(e.toString());
		}
	}

	/* ------------------------------------------------------------ */
	/**
	 * @p name
	 * @p entity
	 */
	public synchronized void redirectEntity(String name, URL entity) {
		// if (entity != null) _redirectMap.put(name, entity);
	}

	/* ------------------------------------------------------------ */
	/**
	 * 
	 * @return Returns the xpath.
	 */
	public String getXpath() {
		return _xpath;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Set an XPath A very simple subset of xpath is supported to select a
	 * partial tree. Currently only path like "/node1/nodeA | /node1/nodeB" are
	 * supported.
	 * 
	 * @p xpath
	 *            The xpath to set.
	 */
	public void setXpath(String xpath) {
		// _xpath = xpath;
		// StringTokenizer tok = new StringTokenizer(xpath, "| ");
		// while (tok.hasMoreTokens())
		// _xpaths = LazyList.add(_xpaths, tok.nextToken());
	}

	/* ------------------------------------------------------------ */
	public String getDTD() {
		return _dtd;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Add a ContentHandler. Add an additional _content handler that is
	 * triggered on a tag name. SAX events are passed to the ContentHandler
	 * provided from a matching start element to the corresponding end element.
	 * Only a single _content handler can be registered against each tag.
	 * 
	 * @p trigger
	 *            Tag local or q name.
	 * @p observer
	 *            SAX ContentHandler
	 */
	public synchronized void addContentHandler(String trigger,
			ContentHandler observer) {

	}

	/* ------------------------------------------------------------ */

	/* ------------------------------------------------------------ */
	/**
	 * Parse InputStream.
	 */
	public  XMLObject parse(InputSource in) throws IOException,SAXException {
		_dtd = null;
		XMLHandler handler = new XMLHandler();
		XMLReader reader = _parser.getXMLReader();
		
		reader.setContentHandler(handler);
		reader.setErrorHandler(handler);
		reader.setEntityResolver(handler);
		reader.setFeature("http://xml.org/sax/features/namespaces", false);
		reader.setFeature("http://xml.org/sax/features/namespace-prefixes",false); 
		reader.setProperty("http://xml.org/sax/properties/lexical-handler",handler);
		_parser.parse(in, handler);
		// if (handler._error != null)
		// throw handler._error;
		// Node doc = (Node) handler._top.get(0);
		// handler.clear();
		return handler.getDocument();
	}
    
        public XMLObject parse(Reader reader) throws IOException,SAXException {
            return parse(new InputSource(reader));
        }
        
        public XMLObject parse(File f, String enc) throws IOException,SAXException {
            return parse(new InputSource(new InputStreamReader(new FileInputStream(f),enc)));
        }

    public static void main(String[] args) throws Exception{
        XMLParser p = new XMLParser();
        XMLObject xml = p.parse(new File("resources/sod/test/ebook_001.xml"),"BIG5");
        System.out.println(xml.json("\t"));
    }

}
