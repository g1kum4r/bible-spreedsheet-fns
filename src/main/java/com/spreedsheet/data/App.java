package com.spreedsheet.data;

import java.io.IOException;

public class App {

	public static void main(String[] args) {
		//read spreed file
		
		System.out.println("-----------START------------");
		XSpreadsheetReadWrite spreadsheetReadWrite = new XSpreadsheetReadWrite();
		try {
			spreadsheetReadWrite.readXlsFiles("KJB.xlsx");
//			spreadsheetReadWrite.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("-----------END------------");
		
		//manipulate the data
		//read the consonants, vowels, and total letters
		//matches rows, numbers of vowesl and consonants
		//manipulate the data
		
		//write the spreedsheet file
	}

}
