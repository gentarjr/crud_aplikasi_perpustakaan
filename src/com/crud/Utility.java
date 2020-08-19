package com.crud;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Year;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Utility {

	protected static boolean cekDataBuku(String[] keywords, boolean isDisplay) throws IOException {

		FileReader fileInput = new FileReader("database.txt");
		BufferedReader bufferInput = new BufferedReader(fileInput);

		String data = bufferInput.readLine();
		boolean isExists = false;
		int nomorData = 0;
		if (isDisplay) {
			System.out.println("\n| No |\tTahun |\tPenulis             |\tPenerbit            |\tJudul Buku ");
			System.out.println("----------------------------------------------------------------------------");
		}
		while (data != null) {
			isExists = true;
			for (String keyword : keywords) {
				isExists = isExists && data.toLowerCase().contains(keyword.toLowerCase());
			}
			if (isExists) {
				if (isDisplay) {
					nomorData++;
					StringTokenizer stringToken = new StringTokenizer(data, ",");
					stringToken.nextToken();
					System.out.printf("| %2d ", nomorData);
					System.out.printf("|\t%4s  ", stringToken.nextToken());
					System.out.printf("|\t%-20s", stringToken.nextToken());
					System.out.printf("|\t%-20s", stringToken.nextToken());
					System.out.printf("|\t%s", stringToken.nextToken());
					System.out.print("\n");
				} else {
					break;
				}
			}

			data = bufferInput.readLine();
		}
		fileInput.close();
		bufferInput.close();
		return isExists;
	}

	protected static String ambilTahun() throws IOException {
		boolean tahunValid = false;

		Scanner inputUser = new Scanner(System.in);
		String tahunInput = inputUser.nextLine();
		while (!tahunValid) {
			try {
				Year.parse(tahunInput);
				tahunValid = true;
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println("Format Yang Anda Masukkan Salah");
				System.out.print("Silahkan Masukkan tahun terbit lagi\t: ");
				tahunValid = false;
				tahunInput = inputUser.nextLine();
			}
		}

		return tahunInput;
	}

	protected static long getPrimaryKey(String penulis, String tahun) throws IOException {
		FileReader fileInput = new FileReader("database.txt");
		BufferedReader bufferInput = new BufferedReader(fileInput);

		long entry = 0;
		String data = bufferInput.readLine();
		Scanner dataScanner;
		String primaryKey;

		while (data != null) {
			dataScanner = new Scanner(data);
			dataScanner.useDelimiter(",");
			primaryKey = dataScanner.next();
			dataScanner = new Scanner(primaryKey);
			dataScanner.useDelimiter("_");

			penulis = penulis.replaceAll("\\s", "");

			if (penulis.equalsIgnoreCase(dataScanner.next()) && tahun.contentEquals(dataScanner.next())) {
				entry = dataScanner.nextInt();
			}
			data = bufferInput.readLine();
		}
		fileInput.close();
		bufferInput.close();
		return entry;
	}

	public static boolean getYesorNo(String message) {
		Scanner inputUser = new Scanner(System.in);

		System.out.print("\n" + message + " (y/n)? ");
		String pilihanUser = inputUser.next();
		while (!pilihanUser.equalsIgnoreCase("y") && !pilihanUser.equalsIgnoreCase("n")) {
			System.err.println("Pilihan Anda bukan y atau n");
			System.out.print("\n" + message + " (y/n)?");
			pilihanUser = inputUser.next();
		}

		return pilihanUser.equalsIgnoreCase("y");
	}

	public static void clearScreen() {
		try {
			if (System.getProperty("os.name").contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				System.out.print("\033\143");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("tidak bisa Clear Screen");
		}
	}

}
