package org.cc.util;

import java.io.IOException;
import java.util.Stack;
import org.cc.XMLObject;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler implements LexicalHandler {

	private final static int STATE_CDATA = 1;
	private Stack<XMLObject> xRS = new Stack<XMLObject>();
	private XMLObject xParent = null;
	private int xState = 0;
	private Locator xLocator;

	public InputSource resolveEntity(String publicId, String systemId)
			throws IOException, SAXException {
		return null;
	}

	public void notationDecl(String name, String publicId, String systemId)
			throws SAXException {
		System.out.println("name=" + name);
		System.out.println("publicId=" + publicId);
		System.out.println("systemId=" + systemId);

	}

	public void unparsedEntityDecl(String name, String publicId,
			String systemId, String notationName) throws SAXException {
		System.out.println("name" + name);
	}

	// //////////////////////////////////////////////////////////////////
	// Default implementation of ContentHandler interface.
	// //////////////////////////////////////////////////////////////////
	/**
	 * Receive a Locator object for document events.
	 *
	 * <p>
	 * By default, do nothing. Application writers may override this method in a subclass if they wish to store the locator for
	 * use with other document events.
	 * </p>
	 *
	 * @param locator A locator for all SAX document events.
	 * @see org.xml.sax.ContentHandler#setDocumentLocator
	 * @see org.xml.sax.Locator
	 */
	public void setDocumentLocator(Locator locator) {
		xLocator = locator;

	}

	/**
	 * Receive notification of the beginning of the document.
	 *
	 * <p>
	 * By default, do nothing. Application writers may override this method in a subclass to take specific actions at the
	 * beginning of a document (such as allocating the root node of a tree or creating an output file).
	 * </p>
	 *
	 * @exception org.xml.sax.SAXException Any SAX exception, possibly wrapping another exception.
	 * @see org.xml.sax.ContentHandler#startDocument
	 */
	public void startDocument() throws SAXException {
		xParent = new XMLObject("$doc");
	}

	/**
	 * Receive notification of the end of the document.
	 *
	 * <p>
	 * By default, do nothing. Application writers may override this method in a subclass to take specific actions at the end of
	 * a document (such as finalising a tree or closing an output file).
	 * </p>
	 *
	 * @exception org.xml.sax.SAXException Any SAX exception, possibly wrapping another exception.
	 * @see org.xml.sax.ContentHandler#endDocument
	 */
	public void endDocument() throws SAXException {
		//System.out.println("*******剖析文件結束*******");
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		//System.out.println("\n字首對應: " + prefix +" 開始!"+ "  它的URI是:" + uri);
		//System.out.println(xLocator.getLineNumber() + prefix + uri);
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		System.out.println("end process prefix mapping");
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		String tag = (uri == null || uri.equals("")) ? qName : localName;
		xRS.push(xParent);
		XMLObject elem = new XMLObject(tag);
		int len = attributes.getLength();
		for (int i = 0; i < len; i++) {
			String name = attributes.getQName(i);
			String value = attributes.getValue(i);
			elem.put("@" + name, value);
		}
		xParent = elem;
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		String tag = (uri == null || uri.equals("")) ? qName : localName;
		if (tag.equals(xParent.tag())) {
			XMLObject child = xParent;
			xParent = xRS.pop();
			xParent.add_child(tag, child.normal());
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException {
		switch (xState) {
			case STATE_CDATA:
				xParent.put("$cdata", new String(ch, start, length));
				break;
			default:
				String text = new String(ch, start, length);

				if (text.trim().length() != 0) {
					xParent.put("$text", text);
				}
		}

	}

	/**
	 * Receive notification of ignorable whitespace in element content.
	 *
	 * <p>
	 * By default, do nothing. Application writers may override this method to take specific actions for each chunk of ignorable
	 * whitespace (such as adding data to a node or buffer, or printing it to a file).
	 * </p>
	 *
	 * @param ch The whitespace characters.
	 * @param start The start position in the character array.
	 * @param length The number of characters to use from the character array.
	 * @exception org.xml.sax.SAXException Any SAX exception, possibly wrapping another exception.
	 * @see org.xml.sax.ContentHandler#ignorableWhitespace
	 */
	public void ignorableWhitespace(char ch[], int start, int length)
			throws SAXException {
		//System.err.println("<!-- cr --!>");
	}

	/**
	 * Receive notification of a processing instruction.
	 *
	 * <p>
	 * By default, do nothing. Application writers may override this method in a subclass to take specific actions for each
	 * processing instruction, such as setting status variables or invoking other methods.
	 * </p>
	 *
	 * @param target The processing instruction target.
	 * @param data The processing instruction data, or null if none is supplied.
	 * @exception org.xml.sax.SAXException Any SAX exception, possibly wrapping another exception.
	 * @see org.xml.sax.ContentHandler#processingInstruction
	 */
	public void processingInstruction(String target, String data)
			throws SAXException {
		System.out.println("processingInstruction" + target + ":" + data);
	}

	/**
	 * Receive notification of a skipped entity.
	 *
	 * <p>
	 * By default, do nothing. Application writers may override this method in a subclass to take specific actions for each
	 * processing instruction, such as setting status variables or invoking other methods.
	 * </p>
	 *
	 * @param tag The tag of the skipped entity.
	 * @exception org.xml.sax.SAXException Any SAX exception, possibly wrapping another exception.
	 * @see org.xml.sax.ContentHandler#processingInstruction
	 */
	public void skippedEntity(String name) throws SAXException {
		// no op
	}

	// //////////////////////////////////////////////////////////////////
	// Default implementation of the ErrorHandler interface.
	// //////////////////////////////////////////////////////////////////
	/**
	 * Receive notification of a parser warning.
	 *
	 * <p>
	 * The default implementation does nothing. Application writers may override this method in a subclass to take specific
	 * actions for each warning, such as inserting the message in a log file or printing it to the console.
	 * </p>
	 *
	 * @param e The warning information encoded as an exception.
	 * @exception org.xml.sax.SAXException Any SAX exception, possibly wrapping another exception.
	 * @see org.xml.sax.ErrorHandler#warning
	 * @see org.xml.sax.SAXParseException
	 */
	public void warning(SAXParseException e) throws SAXException {
		// no op
	}

	/**
	 * Receive notification of a recoverable parser error.
	 *
	 * <p>
	 * The default implementation does nothing. Application writers may override this method in a subclass to take specific
	 * actions for each error, such as inserting the message in a log file or printing it to the console.
	 * </p>
	 *
	 * @param e The warning information encoded as an exception.
	 * @exception org.xml.sax.SAXException Any SAX exception, possibly wrapping another exception.
	 * @see org.xml.sax.ErrorHandler#warning
	 * @see org.xml.sax.SAXParseException
	 */
	public void error(SAXParseException e) throws SAXException {
		// no op
	}

	/**
	 * Report a fatal XML parsing error.
	 *
	 * <p>
	 * The default implementation throws a SAXParseException. Application writers may override this method in a subclass if they
	 * need to take specific actions for each fatal error (such as collecting all of the errors into a single report): in any
	 * case, the application must stop all regular processing when this method is invoked, since the document is no longer
	 * reliable, and the parser may no longer report parsing events.
	 * </p>
	 *
	 * @param e The error information encoded as an exception.
	 * @exception org.xml.sax.SAXException Any SAX exception, possibly wrapping another exception.
	 * @see org.xml.sax.ErrorHandler#fatalError
	 * @see org.xml.sax.SAXParseException
	 */
	public void fatalError(SAXParseException e) throws SAXException {
		throw e;
	}

	public XMLObject getDocument() {
		return this.xParent;
	}

	public void comment(char[] ch, int start, int length) throws SAXException {
              // by pass comment 
		//xParent.put("$comment", new String(ch, start, length));
	}

	public void endCDATA() throws SAXException {
		xState = 0;
	}

	public void endDTD() throws SAXException {
		// TODO Auto-generated method stub

	}

	public void endEntity(String name) throws SAXException {
		// TODO Auto-generated method stub

	}

	public void startCDATA() throws SAXException {
		xState = STATE_CDATA;
	}

	public void startDTD(String name, String publicId, String systemId) throws SAXException {
		System.out.println("Start DTD" + name);

	}

	public void startEntity(String name) throws SAXException {
		//System.out.println("Start Entity:" + tag);

	}
}
