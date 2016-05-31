package br.octa.utils;

import java.io.File;

public class PreRequesitos {

	public static void verificar(){
		File sapjco3dll = new File("C:\\WINDOWS\\system32");
		if(sapjco3dll.exists()){
			System.out.println("sapjco3dll encontrada!");
		}else {
			System.out.println("sapjco3dll não encontrada!");
		}
	}
	
}
