package br.octa.helpers;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import br.inf.portalfiscal.nfe.ObjectFactory;
import br.inf.portalfiscal.nfe.TEnvEvento;
import br.octa.enums.TipoInterfaceEnum;
import br.octa.utils.CertificarXMLUtils;
import br.octa.view.BifrostView;

public class EnvConfRecebtoHelper {
	final static Logger logger = Logger.getLogger(EnvConfRecebtoHelper.class);
	
	public static String toXML(TEnvEvento envEvento) {
		info("Gerando XML EnvConfRecebto");
		try {
			return strValueOf(envEvento);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String toXMlAssinado(TEnvEvento envEvento) throws JAXBException, Exception{
		CertificarXMLUtils certificado = new CertificarXMLUtils();
		return certificado.assinaXML(strValueOf(envEvento), "infEvento",TipoInterfaceEnum.ENVCONFRECEBTO);
	}

	public static String strValueOf(TEnvEvento envEvento) throws JAXBException {
		info("Criando XML do Objeto TEnvEvento ");
		JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe");
		Marshaller marshaller = context.createMarshaller();
		JAXBElement<TEnvEvento> element = new ObjectFactory().createEnvEvento(envEvento);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

		StringWriter sw = new StringWriter();
		marshaller.marshal(element, sw);
		String sw_aux = sw.toString();
		/*
		 * Corre��o do erro de namespace
		 */
		sw_aux = sw_aux.replaceAll("xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"" , "");
		return sw_aux;
	}

	public static TEnvEvento createEnvConfRecebtoFromXMl(String xmlEnvEvento) {
		info("Gerando envento em lote da mensagem");
		try {
			JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<TEnvEvento> element = (JAXBElement<TEnvEvento>) unmarshaller
					.unmarshal(new StringReader(xmlEnvEvento));
			logger.debug("Parse return of webservice to object sucessful!");
			return element.getValue();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	public static void info(String msg) {
		Logger.getLogger(EnvConfRecebtoHelper.class).info(msg);
		BifrostView.setLogServer(msg);
	}
}
