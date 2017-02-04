package cz.osslite.common.utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

@Component
public class XmlBinder {

	@Autowired
	private Logger logger;
	@Autowired
	private XmlParser xmlParser;

	public void marshal(Object object, OutputStream outputStream) throws JAXBException {
		Assert.notNull(object);
		Assert.notNull(outputStream);

		// try {
		JAXBContext context = JAXBContext.newInstance(object.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		marshaller.marshal(object, outputStream);
		// } catch (JAXBException ex) {
		// logger.error("jaxb error", ex);
		// throw ex;
		// }
	}

	// public <T> T unmarshal(Node document, Class<T> type) throws JAXBException {
	// Assert.notNull(document);
	// Assert.notNull(type);
	//
	// // try {
	// JAXBContext context = JAXBContext.newInstance(type);
	// Unmarshaller unmarshaller = context.createUnmarshaller();
	// Object retObj = unmarshaller.unmarshal(document);
	// return (T) retObj;
	// // } catch (JAXBException ex) {
	// // logger.error("jaxb error", ex);
	// // throw ex;
	// // } catch (Exception ex) {
	// // logger.error("error", ex);
	// // throw ex;
	// // }
	// }

	public <T> T unmarshal(InputStream stream, Class<T> type) {
		Assert.notNull(stream);
		Assert.notNull(type);

		try {
			JAXBContext context = JAXBContext.newInstance(type);
			JAXBElement<T> retObj = context.createUnmarshaller().unmarshal(xmlParser.parse(stream), type);

			return retObj != null ? retObj.getValue() : null;
		} catch (JAXBException | SAXException | ParserConfigurationException | IOException e) {
			logger.warn("", e);
		}

		return null;
	}

}
