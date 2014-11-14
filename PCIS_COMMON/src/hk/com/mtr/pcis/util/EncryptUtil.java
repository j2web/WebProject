package hk.com.mtr.pcis.util;




import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class EncryptUtil {

	//private static Log log = LogFactory.getLog(EncryptUtil.class);

	public static byte KEY_BYTE[] = "123456781234567812345678".getBytes();

	public EncryptUtil() {
	}

	public static javax.crypto.SecretKey genDESKey(byte[] key_byte) throws Exception {
		SecretKey k = null;
		k = new SecretKeySpec(key_byte, "DESede");
		return k;
	}

	public static byte[] desDecrypt(javax.crypto.SecretKey key, byte[] crypt) throws Exception {
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DESede");
		cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(crypt);
	}

	public static String desDecrypt(javax.crypto.SecretKey key, String crypt) throws Exception {
		return new String(desDecrypt(key, crypt.getBytes()));
	}

	public static byte[] desEncrypt(javax.crypto.SecretKey key, byte[] src) throws Exception {
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DESede");
		cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(src);
	}

	public static String desEncrypt(javax.crypto.SecretKey key, String src) throws Exception {
		return new String(desEncrypt(key, src.getBytes()));
	}

	/** Test encrypt */
	// public static void main(String[] args) {
	//
	// byte src_byte[] = "sb123456".getBytes();
	//
	// try {
	// File file = new File("c:/encrypt_crs.txt");
	// javax.crypto.SecretKey deskey = genDESKey(KEY_BYTE);
	// byte[] encrypt = desEncrypt(deskey, src_byte);
	// FileOutputStream fos = new FileOutputStream(file);
	// fos.write(encrypt);
	//
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	//
	// }
	/** Test descrypt */
//	public static void main(String[] args) {
//
//		try {
//			File file = new File("c:/encrypt_crs.txt");
//			javax.crypto.SecretKey deskey = genDESKey(EncryptUtil.KEY_BYTE);
//
//			FileInputStream fis = new FileInputStream(file);
//			byte[] encrypt = new byte[fis.available()];
//			fis.read(encrypt, 0, fis.available());
//			byte[] decrypt = EncryptUtil.desDecrypt(deskey, encrypt);
//			String password = new String(decrypt);
//			log.debug(password);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
}
