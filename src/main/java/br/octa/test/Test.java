package br.octa.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.poi.ss.formula.functions.Today;

import br.octa.utils.BlowFishUtils;
import br.octa.utils.ConfigUtils;
import br.octa.utils.KsUtils;
import br.octa.utils.PreRequesitos;
import br.octa.utils.Sys;
import br.octa.view.validate.Validator;

public class Test {

	public static String gKey(String cnpj) {
		try {
			String cSerial = Sys.getCPUS();
			String mSerial = Sys.getMS();
			return BlowFishUtils.cript(cSerial + "_" + mSerial + "_" + cnpj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void setKey(String chave) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("bin/p.bifrost", "UTF-8");
		writer.println(chave);
		writer.close();
	}

	public static String readk() {
		try {
			return new String(Files.readAllBytes(Paths.get("bin/p.bifrost")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static boolean validateK(String cnpj1) throws ParseException, IOException {
		String chave = BlowFishUtils.decript(readk());
		String[] blocks = chave.split("_");
		String cSerial = blocks[0];
		String mSerial = blocks[1];
		String cnpj = blocks[2];
		String dateKey = blocks[3];

		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		Date date = formatter.parse(dateKey);
		Date today = new Date();
		if (cSerial.equals(Sys.getCPUS())
				&& mSerial.equals(Sys.getMS())
				&& today.compareTo(date) < 0) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ParseException,
			IOException {
//
//		System.out.println("23.122.275/0001-01".replaceAll("[-+.^:,/]", ""));
//		String cnpj = "23.122.275/0001-01";
//		String cnpjFormatado  = String.format("%s.%s.%s/%s-%s", cnpj.substring(0, 2), cnpj.substring(3, 6),cnpj.substring(7,10),cnpj.substring(11,15),cnpj.substring(16,18) );
//		System.out.println(cnpjFormatado);
////		String chave = "BFEBFBFF00040651_123490EN400015_24144943000156_05022016";
////		System.out.println(gKey("24144943000156"));
//		System.out.println(BlowFishUtils.decript("dnh7fg9zbEY9Df2tZo+2W0UVj+QE3uFe17KuCXBfhWkB46LwwX8ia7dPqGr2W2oSsXXNoADJ8hklBolKF89UpA=="));
//		setKey(BlowFishUtils.cript("BFEBFBFF00040651_123490EN400015_24144943000156_11052016"));
		PreRequesitos.verificar();
	}

}
