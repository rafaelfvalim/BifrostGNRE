package br.octa.view.validate;

import java.util.regex.Pattern;

public class Validator {

	private static char[] aCnpj;

	/**
	 * Valida um CNPJ, atrav�s de uma string recebida;
	 *
	 * @param cnpj
	 * @return boolean
	 */
	public static boolean validaCnpj(String cnpj) {
		cnpj = cnpj.replaceAll(Pattern.compile("\\s").toString(), "");
		cnpj = cnpj.replaceAll(Pattern.compile("\\D").toString(), "");

		int soma = 0;

		if (cnpj.length() != 14) {
			return false;
		}

		aCnpj = cnpj.toCharArray();

		soma += (parseCharToInt(aCnpj[0]) * 5);
		soma += (parseCharToInt(aCnpj[1]) * 4);
		soma += (parseCharToInt(aCnpj[2]) * 3);
		soma += (parseCharToInt(aCnpj[3]) * 2);
		soma += (parseCharToInt(aCnpj[4]) * 9);
		soma += (parseCharToInt(aCnpj[5]) * 8);
		soma += (parseCharToInt(aCnpj[6]) * 7);
		soma += (parseCharToInt(aCnpj[7]) * 6);
		soma += (parseCharToInt(aCnpj[8]) * 5);
		soma += (parseCharToInt(aCnpj[9]) * 4);
		soma += (parseCharToInt(aCnpj[10]) * 3);
		soma += (parseCharToInt(aCnpj[11]) * 2);

		int d1 = soma % 11;
		d1 = d1 < 2 ? 0 : 11 - d1;

		soma = 0;
		soma += (parseCharToInt(aCnpj[0]) * 6);
		soma += (parseCharToInt(aCnpj[1]) * 5);
		soma += (parseCharToInt(aCnpj[2]) * 4);
		soma += (parseCharToInt(aCnpj[3]) * 3);
		soma += (parseCharToInt(aCnpj[4]) * 2);
		soma += (parseCharToInt(aCnpj[5]) * 9);
		soma += (parseCharToInt(aCnpj[6]) * 8);
		soma += (parseCharToInt(aCnpj[7]) * 7);
		soma += (parseCharToInt(aCnpj[8]) * 6);
		soma += (parseCharToInt(aCnpj[9]) * 5);
		soma += (parseCharToInt(aCnpj[10]) * 4);
		soma += (parseCharToInt(aCnpj[11]) * 3);
		soma += (parseCharToInt(aCnpj[12]) * 2);

		int d2 = soma % 11;
		d2 = d2 < 2 ? 0 : 11 - d2;

		if (parseCharToInt(aCnpj[12]) == d1 && parseCharToInt(aCnpj[13]) == d2) {
			return true;
		} else {
			return false;
		}
	}

	private static int parseCharToInt(char c) {
		return Integer.parseInt(Character.toString(c));
	}

	public static String fomratCNPJ(String cnpjFormatado) {
		return String.format("%s.%s.%s/%s-%s", cnpjFormatado.substring(0, 2), cnpjFormatado.substring(2, 5),
				cnpjFormatado.substring(5, 8), cnpjFormatado.substring(8, 11), cnpjFormatado.substring(12, 14));
	}
}
