package br.octa.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import br.octa.view.BifrostView;
import br.octa.view.Ts;

public class KsUtils {
	public static String gCh(String cnpj) {
		try {
			String cS = Sys.getCPUS();
			String mS = Sys.getMS();
			return BlowFishUtils.cript(mS + "_" + cnpj.replaceAll("[-+.^:,/]", ""));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean setKey(String key) throws FileNotFoundException, UnsupportedEncodingException {

		String chave = BlowFishUtils.decript(key);
		int counter = chave.split("_").length;
		if (counter == 3) {
			PrintWriter writer = new PrintWriter("bin/p.bifrost", "UTF-8");
			writer.println(key);
			writer.close();
			return true;
		} else {
			JOptionPane.showMessageDialog(null, Ts.MENSAGEM_CHAVE_REGISTRO_INVALIDA);
			throw new RuntimeException(Ts.MENSAGEM_CHAVE_REGISTRO_INVALIDA);
		}
	}

	public static String readk() {
		try {
			return new String(Files.readAllBytes(Paths.get("bin/p.bifrost")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean validateK() throws ParseException, IOException {
		String key = readk().trim();
		String chave = BlowFishUtils.decript(key);
		int counter = chave.split("_").length;
		if (counter == 3) {
			String[] blocks = chave.split("_");
			String mS = blocks[0];
			String pj = blocks[1];
			String dateKey = blocks[2];

			SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
			Date date = formatter.parse(dateKey);
			Date today = new Date();
			if (pj.equals(ConfigUtils.getCNPJ()) && mS.equals(Sys.getMS()) && today.compareTo(date) < 0) {
				return true;
			}
			return false;
		} else {
			BifrostView.setLogServer("Programa não registrado!");
			throw new RuntimeException("Programa não registrado");
		}
	}

	public static boolean chkdskRegistro() throws ParseException, IOException {
		String key = readk().trim();
		String chave = BlowFishUtils.decript(key);
		int counter = chave.split("_").length;
		if (counter == 3) {
			String[] blocks = chave.split("_");
			String mS = blocks[0];
			String pj = blocks[1];
			String dateKey = blocks[2];

			SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
			Date date = formatter.parse(dateKey);
			Date today = new Date();
			if (pj.equals(ConfigUtils.getCNPJ()) && mS.equals(Sys.getMS()) && today.compareTo(date) < 0) {
				return true;
			}
		} 
			return false;
	}

}
