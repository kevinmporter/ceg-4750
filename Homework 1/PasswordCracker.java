/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordcracker;
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
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        //encrypt("test.txt", "test.zip", "12345");
	int i = 4;
	int j = 12;
	SecureRandom generator = new SecureRandom();
	while (i < 12) {
		while (j < 100000) {
			String password = new BigInteger(128, generator).toString(i);
			decrypt("KevinPorter.zip", "KevinPorter.txt", password);
		}
	}
    }

}




