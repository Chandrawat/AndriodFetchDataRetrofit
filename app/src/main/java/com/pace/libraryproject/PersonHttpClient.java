package com.pace.libraryproject;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class PersonHttpClient {

    private static String BASE_URL = "https://randomuser.me/";
    private static PersonHttpClient mInstance = new PersonHttpClient();


    public static PersonHttpClient getInstance(){
        return mInstance;
    }

    private Retrofit mRetrofit;
    private  PersonService mService;

    private PersonHttpClient(){
        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).build();
        mService = mRetrofit.create(PersonService.class);
    }

    public void fetchRandomUser(String myFormat, Callback<ResponseBody> callback){
        mService.getRandomPerson(myFormat).enqueue(callback);
    }

    private interface PersonService{
        @GET("api")
        Call<ResponseBody> getRandomPerson(@Query("format") String format);
    }

}
