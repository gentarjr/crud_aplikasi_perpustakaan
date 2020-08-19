package com.tutorial;

import java.io.IOException;
import java.util.Scanner;

import com.crud.Operasi;
import com.crud.Utility;

public class Main {
	public static void main(String[] args) throws IOException {
		Scanner inputUser = new Scanner(System.in);
		String pilihanUser;
		boolean isLanjutkan = true;
		while (isLanjutkan) {
			Utility.clearScreen();
			System.out.println("====APLIKASI PERPUSTAKAAN====\n");
			System.out.println("1. Lihat Data Buku");
			System.out.println("2. Cari Data Buku");
			System.out.println("3. Tambah Data Buku");
			System.out.println("4. Ubah Data Buku");
			System.out.println("5. Hapus Data Buku");

			System.out.print("\n\nPilihan Anda : ");
			pilihanUser = inputUser.next();

			switch (pilihanUser) {
			case "1":
				System.out.println("\n================");
				System.out.println("DAFTAR DATA BUKU");
				System.out.println("================");
				Operasi.tampilBuku();
				break;
			case "2":
				System.out.println("\n================");
				System.out.println("CARI DAFTAR BUKU");
				System.out.println("================");
				Operasi.cariBuku();
				break;
			case "3":
				System.out.println("\n================");
				System.out.println("TAMBAH DATA BUKU");
				System.out.println("================");
				Operasi.tambahBuku();
				Operasi.tampilBuku();
				break;
			case "4":
				System.out.println("\n==============");
				System.out.println("UBAH DATA BUKU");
				System.out.println("==============");
				Operasi.updateBuku();
				break;
			case "5":
				System.out.println("\n===============");
				System.out.println("HAPUS DATA BUKU");
				System.out.println("===============");
				Operasi.deleteBuku();
				break;
			default:
				System.err.println("Input Anda Tidak Ditemukan\nSilahkan Pilih [1-5]");
			}
			isLanjutkan = Utility.getYesorNo("Apakah anda ingin melanjutkan");
		}
	}
}
