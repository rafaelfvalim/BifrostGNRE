package br.inf.portalfiscal.security;

import java.io.IOException;
import java.text.ParseException;

import br.octa.utils.KsUtils;

public class Security {

	public static boolean wihteHabbit() {
		try {
			return KsUtils.validateK();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
