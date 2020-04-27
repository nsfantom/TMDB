package tm.fantom.tmdb.di.module;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tm.fantom.tmdb.BuildConfig;
import tm.fantom.tmdb.api.converter.DateTimeConverter;
import tm.fantom.tmdb.api.req.SimpleApi;
import tm.fantom.tmdb.repo.SharedStorage;

@Module
public final class ApiModule {
    private final Application application;
    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 15;
    private static final int READ_TIMEOUT = 15;
    private static final int CACHE_SIZE = 250 * 1024 * 1024;

    public ApiModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    SharedStorage provideSharedStorage() {
        return new SharedStorage(application);
    }

    @Provides
    @Singleton
    Context providesAppContext() {
        return application;
    }

    @Provides
    @Singleton
    Resources providesResources() {
        return application.getResources();
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DateTime.class, new DateTimeConverter())
                .create();
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {

        Cache cache = new Cache(providesAppContext().getCacheDir(), CACHE_SIZE);

        HttpLoggingInterceptor interceptorLog = new HttpLoggingInterceptor();
        interceptorLog.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .cache(cache)
                .addInterceptor(provideApiKeyInterceptor())
                .addInterceptor(interceptorLog)
                .addInterceptor(provideCacheInterceptor()) // cache override
                ;

        return okHttpClientBuilder.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(provideOkHttpClient())
                .build();
    }

    @Provides
    @Singleton
    ConnectionSpec provideConnectionSpec() {
        return new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();
    }

    @Provides
    @Singleton
    SimpleApi providesApiService(Retrofit retrofit) {
        return retrofit.create(SimpleApi.class);
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) providesAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private Interceptor provideCacheInterceptor() {
        return chain -> {
            Request originalRequest = chain.request();

            String cacheHeaderValue = isNetworkConnected()
                    ? "public, max-age=300"
                    : "public, only-if-cached, max-stale=300";
            Request request = originalRequest.newBuilder().build();
            Response response = chain.proceed(request);

            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", cacheHeaderValue)
                    .build();
        };
    }

    private Interceptor provideApiKeyInterceptor() {
        return chain -> {
            Request originalRequest = chain.request();
            HttpUrl originalHttpUrl = originalRequest.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build();

            Request.Builder requestBuilder = originalRequest.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }


}