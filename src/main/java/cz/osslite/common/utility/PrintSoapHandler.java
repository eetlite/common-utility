package cz.osslite.common.utility;

import java.io.ByteArrayOutputStream;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler for print INBOUND/OUTBOUND SOAP messages
 */
public class PrintSoapHandler implements SOAPHandler<SOAPMessageContext> {

	private static final Logger log = LoggerFactory.getLogger(PrintSoapHandler.class);

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		String prefix = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY) ? "OUT_BOUND" : "IN_BOUND";

		SOAPMessage soapMsg = context.getMessage();
		try {
			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			soapMsg.writeTo(byteOutput);
			String soapMsgString = new String(byteOutput.toByteArray());
			log.trace("{}: {}", prefix, soapMsgString);
		} catch (Exception e) {
			log.trace("{}: couldn't write SOAP MESSAGE", prefix);
		}

		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		log.debug("Server : handleFault()......");
		return true;
	}

	@Override
	public void close(MessageContext context) {
		log.debug("Server : close()......");
	}

}