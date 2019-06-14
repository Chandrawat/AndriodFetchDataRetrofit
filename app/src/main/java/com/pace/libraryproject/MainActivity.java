package com.pace.libraryproject;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String mImageDownload ="https://cdn.shopifycloud.com/hatchful-web/assets/313d73fa42f04a46213abc6267b4d074.png";
    ImageView mImageView;
    TextView mName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.image);
        mName =findViewById(R.id.name);

    }


    public void onRandomUserClick(View view) {
        PersonHttpClient.getInstance().fetchRandomUser("json", new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    String responseAsString = response.body().string();

                    JSONObject jsonObject = new JSONObject(responseAsString);

                    JSONArray jsonArray =  jsonObject.optJSONArray("results");

                    JSONObject person = jsonArray.optJSONObject(0);

                    JSONObject nameJson = person.optJSONObject("name");

                    String firstName = nameJson.optString("first");
                    String lastName = nameJson.optString("last");

                    JSONObject imageJson = person.optJSONObject("picture");

                    String mediumImage = imageJson.optString("medium");
                    mName.setText(firstName + " " +lastName);
                    Picasso.get().load(mediumImage).fit().centerCrop().into(mImageView);


                }catch (Exception e){
                        e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
