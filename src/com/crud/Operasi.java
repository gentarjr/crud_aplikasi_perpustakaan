package com.crud;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Operasi {

	public static void tampilBuku() throws IOException {
		FileReader fileInput;
		BufferedReader bufferInput;
		System.out.println("\n| No |\tTahun |\tPenulis             |\tPenerbit            |\tJudul Buku ");
		System.out.println("----------------------------------------------------------------------------");
		try {
			fileInput = new FileReader("database.txt");
			bufferInput = new BufferedReader(fileInput);
			String data = bufferInput.readLine();
			int nomorData = 0;
			while (data != null) {
				nomorData++;
				StringTokenizer stringToken = new StringTokenizer(data, ",");
				stringToken.nextToken();
				System.out.printf("| %2d ", nomorData);
				System.out.printf("|\t%4s  ", stringToken.nextToken());
				System.out.printf("|\t%-20s", stringToken.nextToken());
				System.out.printf("|\t%-20s", stringToken.nextToken());
				System.out.printf("|\t%s", stringToken.nextToken());
				System.out.print("\n");
				data = bufferInput.readLine();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Database Tidak Ditemukan");
			System.err.println("Silahkan tambah data terlebih dahulu");
			tambahBuku();
			return;
		}
		bufferInput.close();
		fileInput.close();
	}

	public static void cariBuku() throws IOException {
		try {
			File file = new File("database.txt");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Database Tidak Ditemukan");
			System.err.println("Silahkan Tambah Data Terlebih Dahulu");
			tambahBuku();
		}
		Scanner inputUser = new Scanner(System.in);
		System.out.print("Masukkan Kata Kunci : ");
		String cariString = inputUser.nextLine();
		String[] keywords = cariString.split("\\s+");

		Utility.cekDataBuku(keywords, true);
	}

	public static void tambahBuku() throws IOException {

		FileWriter fileOutput = new FileWriter("database.txt", true);
		BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

		Scanner inputUser = new Scanner(System.in);
		String penulis, judul, penerbit, tahun;

		System.out.print("Masukkan Nama Penulis\t: ");
		penulis = inputUser.nextLine();
		System.out.print("Masukkan Judul Penulis\t: ");
		judul = inputUser.nextLine();
		System.out.print("Masukkan Nama Penerbit\t: ");
		penerbit = inputUser.nextLine();
		System.out.print("Masukkan Tahun Terbit\t: ");
		tahun = Utility.ambilTahun();

		// 2012,fiersa besari,media kita,jejak langkah
		String[] keywords = { tahun + "," + penulis + "," + penerbit + "," + judul };
		boolean isExists = Utility.cekDataBuku(keywords, false);
		if (!isExists) {
			long nomorEntry = Utility.getPrimaryKey(penulis, tahun) + 1;

			String penulisTanpaSpasi = penulis.replaceAll("\\s", "");
			String primaryKey = penulisTanpaSpasi + "_" + tahun + "_" + nomorEntry;
			System.out.println("\nData yang anda masukkan adalah");
			System.out.println("--------------------------------");
			System.out.println("Nama Penulis\t : " + penulis);
			System.out.println("Judul Buku\t : " + judul);
			System.out.println("Nama Penerbit\t : " + penerbit);
			System.out.println("Tahun Terbit\t : " + tahun);

			boolean isTambah = Utility.getYesorNo("Apakah Anda ingin menambah data tersebut? ");
			if (isTambah) {
				bufferOutput.write(primaryKey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);
				bufferOutput.newLine();
				bufferOutput.flush();
			}
		} else {
			System.err.println("Buku yang Anda Masukkan sudah tersedia di Database dengan data berikut:");
			Utility.cekDataBuku(keywords, true);
		}
		fileOutput.close();
		bufferOutput.close();
	}

	public static void deleteBuku() throws IOException {

		File database = new File("database.txt");
		FileReader fileInput = new FileReader(database);
		BufferedReader bufferInput = new BufferedReader(fileInput);

		File tempDB = new File("tempDB.txt");
		FileWriter fileOutput = new FileWriter(tempDB);
		BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

		tampilBuku();

		Scanner inputUser = new Scanner(System.in);
		System.out.print("\nMasukkan Nomor Buku yang akan dihapus: ");
		int deleteNum = inputUser.nextInt();

		boolean isFound = false;
		int entryCounts = 0;

		String data = bufferInput.readLine();

		while (data != null) {
			entryCounts++;
			boolean isDelete = false;
			StringTokenizer st = new StringTokenizer(data, ",");
			if (deleteNum == entryCounts) {
				System.out.println("\nData yang ingn anda hapus adalah : ");
				System.out.println("-------------------------------------");
				System.out.println("Referensi\t: " + st.nextToken());
				System.out.println("Tahun\t\t: " + st.nextToken());
				System.out.println("Penulis\t\t: " + st.nextToken());
				System.out.println("penerbit\t: " + st.nextToken());
				System.out.println("Judul\t\t: " + st.nextToken());

				isDelete = Utility.getYesorNo("Apakah anda yakin ingin menghapus?");
				isFound = true;
			}

			if (isDelete) {
				System.out.println("Data berhasil dihapus");
			} else {
				bufferOutput.write(data);
				bufferOutput.newLine();
			}

			data = bufferInput.readLine();
		}

		if (!isFound) {
			System.err.println("Buku tidak ditemukan");
		}

		bufferOutput.flush();

		bufferOutput.close();
		fileOutput.close();
		bufferInput.close();
		fileInput.close();

		database.delete();
		tempDB.renameTo(database);
	}

	public static void updateBuku() throws IOException {
		File database = new File("database.txt");
		FileReader fileInput = new FileReader(database);
		BufferedReader bufferInput = new BufferedReader(fileInput);

		File tempDB = new File("tempDB.txt");
		FileWriter fileOutput = new FileWriter(tempDB);
		BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

		tampilBuku();

		Scanner userInput = new Scanner(System.in);
		System.out.print("\nMasukkan nomor Buku yang mau diupdate : ");
		int updateNum = userInput.nextInt();

		String data = bufferInput.readLine();
		int entryCount = 0;
		while (data != null) {
			entryCount++;

			StringTokenizer st = new StringTokenizer(data, ",");

			if (updateNum == entryCount) {
				System.out.println("\nData yang ingin anda Update adalah");
				System.out.println("------------------------------------");
				System.out.println("Referensi\t : " + st.nextToken());
				System.out.println("Tahun\t\t : " + st.nextToken());
				System.out.println("Penulis\t\t : " + st.nextToken());
				System.out.println("Penerbit\t : " + st.nextToken());
				System.out.println("Judul\t\t : " + st.nextToken());

				String[] fieldData = { "tahun", "penulis", "penerbit", "judul" };
				String[] tempData = new String[4];
				st = new StringTokenizer(data, ",");
				String originalData = st.nextToken();
				for (int i = 0; i < fieldData.length; i++) {
					boolean isUpdate = Utility.getYesorNo("Apakah anda ingin merubah " + fieldData[i]);
					originalData = st.nextToken();
					if (isUpdate) {
						if (fieldData[i].equalsIgnoreCase("tahun")) {
							System.out.print("Masukkan "+fieldData[i]+" baru: ");
							tempData[i] = Utility.ambilTahun();
						} else {
							userInput = new Scanner(System.in);
							System.out.print("\nMasukkan " + fieldData[i] + " baru: ");
							tempData[i] = userInput.nextLine();
						}
					} else {
						tempData[i] = originalData;
					}
				}
				st = new StringTokenizer(data, ",");
				st.nextToken();
				System.out.println("\nData baru anda adalah");
				System.out.println("------------------------------------");
				System.out.println("Tahun\t\t : " + st.nextToken() + "-->" + tempData[0]);
				System.out.println("Penulis\t\t : " + st.nextToken() + "-->" + tempData[1]);
				System.out.println("Penerbit\t : " + st.nextToken() + "-->" + tempData[2]);
				System.out.println("Judul\t\t : " + st.nextToken() + "-->" + tempData[3]);

				boolean isUpdate = Utility.getYesorNo("Apakah anda yakin ingin mengupdate data tersebut?");
				if (isUpdate) {
					boolean isExist = Utility.cekDataBuku(tempData, false);
					if (isExist) {
						System.err.println("data buku sudah ada di Database");
						bufferOutput.write(data);
					} else {
						String tahun = tempData[0];
						String penulis = tempData[1];
						String penerbit = tempData[2];
						String judul = tempData[3];

						long noEntry = Utility.getPrimaryKey(penulis, tahun) + 1;
						String penulisTanpaSpasi = penulis.replaceAll("\\s+", "");
						String primaryKey = penulisTanpaSpasi + "_" + tahun + "_" + noEntry;

						bufferOutput.write(primaryKey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);
					}
				} else {
					bufferOutput.write(data);
				}
			} else {
				bufferOutput.write(data);
			}
			bufferOutput.newLine();
			data = bufferInput.readLine();
		}
		bufferOutput.flush();

		bufferOutput.close();
		fileOutput.close();
		bufferInput.close();
		fileInput.close();
		database.delete();
		tempDB.renameTo(database);

	}

}
