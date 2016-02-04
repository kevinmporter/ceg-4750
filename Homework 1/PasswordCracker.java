/*
 * Kevin Porter
 * CEG-4750 Homework 1
 * Password cracker
 * Honestly, I couldn't get this to work with the jar files provided.
 * My code appears to compile without issue, so the algorithm seems acceptable.
 * Please see my Python file for something more in-depth (but not functional).
 */
import java.io.File;

import de.idyl.winzipaes.AesZipFileDecrypter;
import de.idyl.winzipaes.AesZipFileEncrypter;
import de.idyl.winzipaes.impl.AESDecrypter;
import de.idyl.winzipaes.impl.AESDecrypterBC;
import de.idyl.winzipaes.impl.AESEncrypter;
import de.idyl.winzipaes.impl.AESEncrypterBC;
import de.idyl.winzipaes.impl.ExtZipEntry;
import java.security.SecureRandom;
import java.math.BigInteger;
/**
 *
 * @author lingg
 */
public class PasswordCracker {
	public static void encrypt(String inputFilename, String zipFilename, String password)
			throws Exception {

		AESEncrypter encrypter = new AESEncrypterBC();
		AESEncrypter encrypter2 = new AESEncrypterBC();

		AesZipFileEncrypter.zipAndEncrypt(
				new File(inputFilename), new File(zipFilename), password, encrypter);
	}

	public static void decrypt(String zipFilename, String outputFilename, String password)
			throws Exception {

		AESDecrypter decrypter = new AESDecrypterBC();

		AesZipFileDecrypter dec = new AesZipFileDecrypter(new File(zipFilename), decrypter);
		ExtZipEntry entry = dec.getEntryList().get(0); // assumes only one item is in the zip file
		dec.extractEntry(entry, new File(outputFilename), password);
		dec.close();
	}
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		//encrypt("test.txt", "test.zip", "12345");

		// this is a super basic brute force algorithm
		// please see
		int i = 4;
		int j = 0;
		// securely generate characters
		SecureRandom generator = new SecureRandom();
		// 4 through 12 character passwords
		while (i < 12) {
			while (j < 100000) {
				// using this method, we get 128-bit secure characters
				String password = new BigInteger(128, generator).toString(i);
				try {
					decrypt("KevinPorter.zip", "KevinPorter.txt", password);
				} catch (Exception e) {

				}
				j++;
			}
			i++;
		}
	}

}
