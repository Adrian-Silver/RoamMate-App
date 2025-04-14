package com.example.roammate.data.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton class for Retrofit API client
 */
public class ApiClient {
    private static final String BASE_URL = "https://api.geoapify.com/";
    private static final String API_KEY = "YOUR_API_KEY"; // Replace with your actual API key

    // Keep a single instance of Retrofit
    private static Retrofit retrofit = null;

    /**
     * Get Retrofit client instance
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            // Create logging interceptor
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Create OkHttp client with logging
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            // Create Retrofit instance
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

    /**
     * Get API key
     */
    public static String getApiKey() {
        return API_KEY;
    }
}

