package com.dldriver.driver.network;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    //private static final String baseURl = /*"http://192.168.1.114:3000/";*/"https://abrlaundryapp.herokuapp.com/";
   // private static final String baseURl = "http://192.168.1.123:3000/";/*"https://abrlaundryapp.herokuapp.com/";*/
      private static final String baseURl = "http://148.72.64.138:3007/";
    private static OkHttpClient sHttpClient;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getUnsafeOkHttpClient())
                    .build();
        }
        return retrofit;

    }

    private static void addHeader(final String token) {
        OkHttpClient.Builder lBuilder = new OkHttpClient.Builder();
        lBuilder.connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);
        lBuilder.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("Authorization", token)
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });
        sHttpClient = lBuilder.build();
    }

    public static Retrofit getClientToken(String token) {
        addHeader(token);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURl)
                    .client(sHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;

    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier((hostname, session) -> true);

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
