package com.spreedsheet.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XSpreadsheetReadWrite {

	public void readXlsFiles(String fileName) throws IOException {
		File file = new File(fileName);
		if (file.exists()) {
			evaluteGenesisSheet(file);
		} else {
			System.out.println("FILE NOT FOUND AT LOCATION: " + file.getAbsolutePath());
		}

	}

	private void evaluteGenesisSheet(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		// creating workbook instance that refers to .xls file
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		// creating a Sheet object to retrieve the object
		XSSFSheet sheet = wb.getSheetAt(0);

		if (wb.getSheet("evalute") != null) {
			wb.removeSheetAt(wb.getSheetIndex("evalute"));
		}

		XSSFSheet evaluteSheet = wb.createSheet("evalute");
		// evaluating cell type
		// iteration over row using for each loop
		for (Row row : sheet) {
			// iteration over cell using for each loop
			String genesis = null;
			for (Cell cell : row) {
				int[] arr = null;
				switch (cell.getAddress().getColumn()) {
				case 0:
					genesis = cell.getStringCellValue();
					writeGenesisKey(evaluteSheet, cell);
					break;
				case 1:
					// return the array consisting, number of consonants, vowels, letters
					// [number of consonants, number of vowels, number of letters, ]
					arr = extractLettersConsonantsAndVowels(cell);
					writeTheEvaluteToNewSpreedSheetValue(evaluteSheet, genesis, cell.getRowIndex(), arr);
					break;
				}
			}
			System.out.println(row.getRowNum() + "/" + sheet.getLastRowNum());
		}
		System.out.println();
		fis.close();
		FileOutputStream fos = new FileOutputStream(file);
		wb.write(fos);

	}
	
	private HashMap<EvaluteRow, String> evaluteRows = new HashMap<EvaluteRow, String>();

	private void writeTheEvaluteToNewSpreedSheetValue(XSSFSheet evaluteSheet, String genesis, int row, int[] arr) {
		XSSFRow createRow = evaluteSheet.getRow(row);
		createRow.createCell(1, CellType.NUMERIC).setCellValue(arr[0]);
		createRow.createCell(2, CellType.NUMERIC).setCellValue(arr[1]);
		createRow.createCell(3, CellType.NUMERIC).setCellValue(arr[2]);
//		to find the match of vowels and consonants in genesis, 
		EvaluteRow ev = new EvaluteRow(genesis, arr[1], arr[0], arr[2]);

		if(evaluteRows.get(ev) != null) {
			System.out.println(ev);
			createRow.createCell(4, CellType.STRING).setCellValue(evaluteRows.get(ev));
			evaluteRows.put(ev, evaluteRows.get(ev).concat(",".concat(genesis)));
		}
		else {
			evaluteRows.put(ev, genesis);
		}


	}

	private List<Cell> searchTheMatch(XSSFSheet evaluteSheet, int row, int[] arr) {
		List<Cell> list = new ArrayList<Cell>();
		int i = --row;
		while (i >= 0) {
			XSSFRow xssfRow = evaluteSheet.getRow(i);
			if (xssfRow.getCell(1).getNumericCellValue() == arr[0]) {
				if (xssfRow.getCell(2).getNumericCellValue() == arr[1]) {
					list.add(xssfRow.getCell(0));
				}
			}
			i--;
		}
		return list;
	}

	private int[] extractLettersConsonantsAndVowels(Cell cell) {
		String phrase = cell.getStringCellValue();
		if (phrase != null && !phrase.isEmpty()) {
			int vowels = 0;
			int consonants = 0;
			int letters = 0;
			phrase = phrase.toLowerCase();
			for (int i = 0; i < phrase.length(); ++i) {
				char ch = phrase.charAt(i);
				if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
					vowels++;
					letters++;
				} else if ((ch >= 'a' && ch <= 'z')) {
					consonants++;
					letters++;
				}
			}
			return new int[] { consonants, vowels, letters };
		}
		return null;
	}

	private void writeGenesisKey(XSSFSheet evaluteSheet, Cell cell) {
		XSSFRow row = evaluteSheet.createRow(cell.getRowIndex());
		XSSFCell keyCell = row.createCell(0, CellType.STRING);
		keyCell.setCellValue(cell.getStringCellValue());
//		System.out.println("cell: " + keyCell.getStringCellValue());
	}

}
