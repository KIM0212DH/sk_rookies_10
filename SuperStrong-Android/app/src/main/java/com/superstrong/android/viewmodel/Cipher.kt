package com.superstrong.android.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import retrofit2.Callback
import java.nio.charset.Charset
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
object AES256Util1 { // 서버에서 정해준 key로 암호화 및 복호화
    //키값 32바이트: AES256(24: AES192, 16: AES128)
    var secretKey = "01234567890123456789012345678901"
    var ivBytes = "0123456789012345".toByteArray()

    //AES256 암호화
    fun aesEncode(str: String): String {
        try {
            val textBytes = str.toByteArray(charset("UTF-8"))
            val ivSpec: AlgorithmParameterSpec = IvParameterSpec(
                ivBytes
            )
            val newKey = SecretKeySpec(secretKey.toByteArray(charset("UTF-8")), "AES")
            var cipher: Cipher? = null
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec)
            return Base64.encodeToString(cipher.doFinal(textBytes), Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return str
    }

    //AES256 복호화

    fun aesDecode(str: String): String {
        try {
            val textBytes = Base64.decode(str, Base64.DEFAULT)
            val ivSpec: AlgorithmParameterSpec = IvParameterSpec(ivBytes)
            val newKey = SecretKeySpec(secretKey.toByteArray(Charset.forName("UTF-8")), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec)
            return String(cipher.doFinal(textBytes), Charset.forName("UTF-8"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return str
    }
}

object AES256Util2 {
    //키값 32바이트: AES256(24: AES192, 16: AES128)

    private lateinit var context: Context
    private lateinit var sharedPref: SharedPreferences
    private lateinit var secretKey: String
    private var serverKey = "01234567890123456789012345678901"
    //    val sharedPref = getSharedPreferences("strong", Context.MODE_PRIVATE)
//    var secretKey = sharedPref.getString("key","")
    var ivBytes = "0123456789012345".toByteArray()

    fun init(context: Context) {
        this.context = context
        this.sharedPref = context.getSharedPreferences("strong", Context.MODE_PRIVATE)
        this.secretKey = sharedPref.getString("key","") ?: ""
    }
    fun init2(key:String= serverKey){
        this.secretKey = key
    }

    //AES256 암호화
    fun aesEncode(str: String): String {
        try {
            val textBytes = str.toByteArray(charset("UTF-8"))
            val ivSpec: AlgorithmParameterSpec = IvParameterSpec(
                ivBytes
            )
            val newKey = SecretKeySpec(secretKey.toByteArray(charset("UTF-8")), "AES")
            var cipher: Cipher? = null
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec)
            return Base64.encodeToString(cipher.doFinal(textBytes), Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return str
    }

    //AES256 복호화

    fun aesDecode(str: String): String {
        try {
            val textBytes = Base64.decode(str, Base64.DEFAULT)
            val ivSpec: AlgorithmParameterSpec = IvParameterSpec(ivBytes)
            val newKey = SecretKeySpec(secretKey.toByteArray(Charset.forName("UTF-8")), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec)
            return String(cipher.doFinal(textBytes), Charset.forName("UTF-8"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return str
    }
}