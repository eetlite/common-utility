package cz.osslite.common.utility;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Component
public class XmlParser {

	@Autowired
	private Logger logger;

	public Document parse(InputStream inputStream) throws SAXException, IOException, ParserConfigurationException {
		Assert.notNull(inputStream);

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);

		DocumentBuilder documentBuilder;
		try {
			documentBuilder = builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException ex) {
			logger.error("parser error", ex);
			throw ex;
		}

		Document document;
		try {
			document = documentBuilder.parse(inputStream);
		} catch (IOException ex) {
			logger.error("io error", ex);
			throw ex;
		} catch (SAXException ex) {
			logger.error("sax error", ex);
			throw ex;
		}

		return document;
	}

	public Document parse(byte[] byteArray) throws SAXException, IOException, ParserConfigurationException {
		ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
		try {
			return parse(bais);
		} finally {
			try {
				bais.close();
			} catch (Exception e) {
				// just swallow
				logger.error("error closing byte array input stream", e);
			}
		}
	}// end:

}
