package security


import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.io.IOException;
import java.security.KeyPair;
import javax.xml.bind.DatatypeConverter;

object Encode {

  val argon2 = Argon2Factory.create();

  @throws(classOf[IOException])
  def compress(hash: String): Array[Byte] = {
    val compress: Array[Byte] = GZIPCompression.compress(hash);
    return compress
  }

  @throws(classOf[Exception])
  def encrypt(keypair: KeyPair, hash: String): Array[Byte] = {

    val cipherText: Array[Byte] = Encryption.do_RSAEncryption(hash, keypair);

    val newHash: String = DatatypeConverter.printHexBinary(cipherText);

    return compress(newHash);
  }

  @throws(classOf[Exception])
  def decompress(hash: Array[Byte]) = GZIPCompression.decompress(hash);

  @throws(classOf[Exception])
  def decrypt(keypair: KeyPair, hash: Array[Byte]): String = {

      val dcompress = decompress(hash);

      return Encryption.do_RSADecryption(DatatypeConverter.parseHexBinary(dcompress), keypair);
  }

  def hashpw(keypair: KeyPair, pass: String): String = {

    val passwordChars = pass.toCharArray();

    val stored = argon2.hash(22, 65536, 1, passwordChars);

    argon2.wipeArray(passwordChars);

    try {

      val newHash: Array[Byte] = encrypt(keypair, stored);

      return DatatypeConverter.printHexBinary(newHash);

    } catch {
      case e: Exception => {

      return "";
      }
    }

  }

  def verify(keypair: KeyPair, pass :String, hash: String): Boolean = {

      val hashArray = DatatypeConverter.parseHexBinary(hash);

      try{

        val newHash = decrypt(keypair, hashArray);

        return argon2.verify(newHash, pass.toCharArray());

    } catch {
      case e: Exception => {
        return false;
      }
    }
  }
}
