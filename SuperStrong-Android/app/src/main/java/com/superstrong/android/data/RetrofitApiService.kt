package com.superstrong.android.data

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.io.ByteArrayInputStream
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*


public interface BackendApiService {
    @POST("api/login")
    fun login(@Body encryptedData: EncryptedData): Call<JsonObject>

    // Payment1 -> Payment2 넘어갈 때 POST
    @POST("api/send_input")
    fun payment1(@Body encryptedData: EncryptedPayment): Call<JsonObject>

    //Payment1 -> Payment2 넘어갈 때 GET
    @POST("api/send_input")
    fun paymentRsp1(@Body payInfoRsp1: PayInfoRsp1): Call<JsonObject>

    // Payment2 -> Payment3 넘어갈 때 POST
    @POST("api/send")
    fun payment2(@Body encryptedData: EncryptedPayment): Call<JsonObject>


    @POST("api/register")
    @Headers("Content-Type: application/json")
    suspend fun signUp(@Body body: E2eReq): Response<E2eRes>

    /*@POST("api/auth")
    @Headers("Content-Type: application/json")
    fun emailAuth(@Body body: authCode): Call<UserData>*/

    @POST("api/register/auth")
    @Headers("Content-Type: application/json")
    suspend fun emailAuth(@Body body: E2eReq): Response<E2eRes>

    @POST("api/chpass")
    @Headers("Content-Type: application/json")
    suspend fun chPass(@Body body: Password): Response<ChpassReponse>

    @POST("api/chpass/newPass")
    @Headers("Content-Type: application/json")
    suspend fun newPass(@Body body: Password): Response<ChpassReponse>

    @POST("api/main_asset")
    @Headers("Content-Type: application/json")
    suspend fun getBalance(@Body body: E2eReq2): Response<E2eRes>

    @POST("/api/main_history")
    @Headers("Content-Type: application/json")
    suspend fun getHistory(@Body body: E2eReq2): Response<E2eRes>
}

class SingleCertTrustManager(private val cert: X509Certificate) : X509TrustManager {

    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        // Do nothing
    }

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        chain?.let {
            Log.d("ddddddddddddddddddddddddddddddd",cert.toString())
            Log.d("ddddddddddddddddddddddddddddddd",it[0].toString())
            if (it.size == 1 && it[0] == cert) {
                Log.d("sssssssssssssssssssssssss","same same same smae")
                return
            }
        }
        throw CertificateException("Invalid server certificate")
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return arrayOf(cert)
    }
}

object RetrofitInstance {
    private const val BASE_URL = "https://ec2-43-201-205-63.ap-northeast-2.compute.amazonaws.com"
    private val gson: Gson = GsonBuilder().create()


    private const val certString = "-----BEGIN CERTIFICATE-----\n" +
            "MIIDdTCCAl2gAwIBAgIEMX6TrDANBgkqhkiG9w0BAQsFADBrMQswCQYDVQQGEwJr\n" +
            "bzEQMA4GA1UECBMHVW5rbm93bjEQMA4GA1UEBxMHVW5rbm93bjEUMBIGA1UEChML\n" +
            "c3VwZXJzdHJvbmcxEDAOBgNVBAsTB1Vua25vd24xEDAOBgNVBAMTB1Vua25vd24w\n" +
            "HhcNMjMwNDIwMTQzNDQ1WhcNMjMwNzE5MTQzNDQ1WjBrMQswCQYDVQQGEwJrbzEQ\n" +
            "MA4GA1UECBMHVW5rbm93bjEQMA4GA1UEBxMHVW5rbm93bjEUMBIGA1UEChMLc3Vw\n" +
            "ZXJzdHJvbmcxEDAOBgNVBAsTB1Vua25vd24xEDAOBgNVBAMTB1Vua25vd24wggEi\n" +
            "MA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC/clu6ODehkWJDXt/pCvnEya/z\n" +
            "wgpe9RHR8sWIjbbuq2xEl5Swy5fcND03LM97nr2I1UsXtez7p5iD+iQLSPzLroRy\n" +
            "GjVfvNoDq2s+WCQ50RNr+Kc09I81pfARsXLM2kKwPYkBKu4y6h8OtE2cd1KUa6nn\n" +
            "065RFUJyvshpRUazXOC0qjhggTv4gBfsANf/BbkyE8ERvm0Bm/mYXaoQeivPCgj5\n" +
            "hnR2VGL+vN7C0Z/xeRKE5UEO4FSC8fDLpJmficnywUX8pyWhTyQaBCnOO8bmwdTV\n" +
            "XkPh/ggzakvT4NRTgxRafloR/kfBTE8/BsQ0aW15p5/DA+i3w2jL3EjTtYWNAgMB\n" +
            "AAGjITAfMB0GA1UdDgQWBBTNoKmYI+nGLCFmX84IZcQ7vTnCyDANBgkqhkiG9w0B\n" +
            "AQsFAAOCAQEAY9mLgta2zmrAJQiSaLsA3/8anK91yNho+0l/N1ikfDd4sLmFt073\n" +
            "027CTlRNHtDovpdnvN10XyOpu9xE52DSwPPCluQsNYdyOvNnf53IEC+MjTpOnBiQ\n" +
            "XVkP+932BlAQxYOqKLsdj9Sc3o+HRF6bIfSzMgYwrhE5QJgkA5Fm59ZR3I/Elw5b\n" +
            "4yCJrIh69B0YEM+kUKde+7Ttkm6qP99WnKfdnhK4fyvt1EYu9QpR3xz0XE81dpnW\n" +
            "4fV1k87KQz/yskpkPbMxkzv2kiRkZLTYdsna89UacwMYqskEUV9xO70TVPLlJ7ft\n" +
            "uLUn8kacAAOjOtv7e2ANAQVY6XZuBNnsIQ==\n" +
            "-----END CERTIFICATE-----"


    private val certInputStream = ByteArrayInputStream(certString.toByteArray())

    lateinit var certFactory: CertificateFactory
    lateinit var cert: X509Certificate
    lateinit var trustManager: X509TrustManager
    lateinit var sslContext: SSLContext
    lateinit var client: OkHttpClient
    lateinit var retrofit: Retrofit
    lateinit var backendApiService: BackendApiService
    init {
        certFactory = CertificateFactory.getInstance("X.509")
        cert = certFactory.generateCertificate(certInputStream) as X509Certificate



        trustManager = SingleCertTrustManager(cert)
        sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, arrayOf(trustManager), SecureRandom())

        client = OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustManager)
            .hostnameVerifier { _, _ -> true }
            .build()



        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        backendApiService = retrofit.create(BackendApiService::class.java)
        }
    }

/*object RetrofitInstance {
    private const val BASE_URL = "https://ec2-43-201-205-63.ap-northeast-2.compute.amazonaws.com"
    private val gson: Gson = GsonBuilder().create()

    lateinit var  certificatePinner: CertificatePinner
    lateinit var  sslSocketFactory: SSLSocketFactory
    lateinit var  okHttpClient: OkHttpClient


    private lateinit var client :OkHttpClient
    lateinit var  retrofit : Retrofit
    lateinit var backendApiService: BackendApiService



    class TrustAllCerts : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    }
    init{

        certificatePinner = CertificatePinner.Builder()
            .add("ec2-43-201-205-63.ap-northeast-2.compute.amazonaws.com", "sha256/RjIwOEFBOUI2NDYwQUU2RDY3NEI4QTgyQUM3MTg5MDEwNjcyMjJEQjc0N0YzODFBMTNGODE1NjFGNTNGM0FBMA==")
            .build()

        sslSocketFactory = SSLContext.getDefault().socketFactory

        okHttpClient = OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .sslSocketFactory(sslSocketFactory, TrustAllCerts())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        backendApiService = retrofit.create(BackendApiService::class.java)
    }
}*/

class RetrofitApiService {

}

