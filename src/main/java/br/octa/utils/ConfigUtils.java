package br.octa.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import br.inf.portalfiscal.security.Security;
import br.octa.server.server.sap.Server;
import br.octa.view.BifrostView;

public class ConfigUtils {

	public static String getArquivoCertificado() {
		return PropertyUtils.getValue("arquivo_certificado");
	}

	public static void setCNPJ(String cnpj) {
		PropertyUtils.setPropertyAndStore("cnpj", cnpj.replaceAll("[-+.^:,/]", ""));
	}

	public static void setChave(String chave) {
		PropertyUtils.setPropertyAndStore("chave", chave);
	}

	public static String getChave() {
		return PropertyUtils.getValue("chave");
	}

	public static String getCNPJ() {
		return PropertyUtils.getValue("cnpj");
	}

	public static void setArquivoCertificado(String valor) {
		PropertyUtils.setPropertyAndStore("arquivo_certificado", valor);
	}

	private static String getSenha() {
		return PropertyUtils.getValue("senha_certificado");
	}

	public static void setSenhaCerticado(String valor) {
		criptografiaSenha(valor);
	}

	public static String getNFeCacerts() {
		return PropertyUtils.getValue("nfecacerts");
	}

	public static String getCompanyName() {
		return PropertyUtils.getValue("company_name");
	}

	public static String getThreads(String name) {
		return PropertyUtils.getValue(name);
	}

	public static String getTpAmb() {
		return PropertyUtils.getValue("sefaz.ambiente");
	}

	public static String getUF() {
		return PropertyUtils.getValue("codigo.ibege.uf");
	}

	public static String getConsultaVersaoDados() {
		return PropertyUtils.getValue("sefaz.consulta_destinatario.versao_dados");
	}

	public static String getEvnioEventoVersaoDados() {
		return PropertyUtils.getValue("sefaz.envio_evento.versao_dados");
	}

	public static String getFolderLog() {
		return PropertyUtils.getValue("log.sefaz.folder");
	}

	public static String setSenhaCertificado(String value) {
		return criptografiaSenha(value);
	}

	public static String getSenhaCertificado() {
		String senha = getSenha();
		if (senha.startsWith("ENC(") && senha.endsWith(")")) {
			return BlowFishUtils.decript(senha.substring(4, senha.length() - 1));
		}
		return "";
	}

	public static String criptografiaSenha(String senha) {
		if (senha != null) {
			if (!senha.equals("")) {
				criptToFile(senha);
			}
		}
		return senha;
	}

	private static void criptToFile(String senha) {
		PropertyUtils.setPropertyAndStore("senha_certificado",
				"ENC(" + BlowFishUtils.cript(senha).replace("\n", "") + ")");
	}

	public static boolean checkRequirements() {
		File certificado = new File(ConfigUtils.getArquivoCertificado());
		File caCert = new File("certificados/keystore/NFeCacerts");
		File ABAP_AS_WITH_POOL = new File("ABAP_AS_WITH_POOL.jcoDestination");
		File ABAP_AS_WITHOUT_POOL = new File("ABAP_AS_WITHOUT_POOL.jcoDestination");
		File SERVER = new File("SERVER.jcoServer");
		File sapjco3dll = new File("C:\\WINDOWS\\System32\\sapjco3.dll");
		
		if (Security.wihteHabbit()) {

			if (!sapjco3dll.exists()) {
				String msg = "Impossível iniciar o servidor, sapjco3.dll não encontrada, verifique a configuração";
				BifrostView.setLogServer(msg);
				throw new RuntimeException(msg);
			}

			
			if (!certificado.exists()) {
				String msg = "Impossível iniciar o servidor, certificado não instalado, verifique a configuração";
				BifrostView.setLogServer(msg);
				throw new RuntimeException(msg);
			}

			if (!caCert.exists()) {
				String msg = "Impossível iniciar o servidor, certificado não instalado, verifique a configuração";
				BifrostView.setLogServer(msg);
				throw new RuntimeException(msg);
			}

			if (!SERVER.exists()) {
				String msg = "Impossível iniciar o servidor, configure a conexão com o SAP, verifique a configuração";
				BifrostView.setLogServer(msg);
				throw new RuntimeException(msg);
			}
			if (!ABAP_AS_WITH_POOL.exists()) {
				String msg = "Impossível iniciar o servidor, configure a conexão com o SAP, verifique a configuração";
				BifrostView.setLogServer(msg);
				throw new RuntimeException(msg);
			}
			if (!ABAP_AS_WITHOUT_POOL.exists()) {
				String msg = "Impossível iniciar o servidor, configure a conexão com o SAP, verifique a configuração";
				BifrostView.setLogServer(msg);
				throw new RuntimeException(msg);
			}
			return true;

		} else {
			JOptionPane.showMessageDialog(null,
					"Olá \n Esse programa ainda não foi registrado \n Entre em contatno com a www.klustter.com.br");
			throw new RuntimeException("Programa não registrado!");
		}

	}
}
