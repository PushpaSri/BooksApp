package com.example.doddas.booksproject;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.doddas.booksproject.PojoClasses.VolumeInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<String> {
    int LOADER_NO = 1;
    JSONArray item;
    Boolean save=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v4.app.LoaderManager loaderManager = getSupportLoaderManager();
        android.support.v4.content.Loader<String> loader = loaderManager.getLoader(LOADER_NO);
        Bundle b = new Bundle();
        b.putString(getString(R.string.url), "https://www.googleapis.com/books/v1/volumes?q=java");
        if(loader==null)
            loaderManager.initLoader(LOADER_NO, b, this);
        else
            loaderManager.restartLoader(LOADER_NO,b,this);

    }


    @NonNull
    @Override
    public android.support.v4.content.Loader<String> onCreateLoader(int id, @Nullable final Bundle args) {
        return new android.support.v4.content.AsyncTaskLoader<String>(this)
        {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(args==null)
                    return;
                forceLoad();
            }

            @Nullable
            @Override
            public String loadInBackground() {
                String url = args.getString(getString(R.string.url));
                Log.i("url",url);
                if (url == null)
                    return null;
                else {
                    try {
                        URL Url = new URL(url);
                        HttpURLConnection connection = (HttpURLConnection) Url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.connect();
                        InputStream is = connection.getInputStream();
                        StringBuilder builder = new StringBuilder();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                        return builder.toString();

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<String> loader, String data) {
        Log.i("log","log");
        try {
            JSONObject jsonObject=new JSONObject(data);
             item=jsonObject.getJSONArray("items");
             Log.i("create","yes");
              callFragment(this,item);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void callFragment(MainActivity mainActivity, JSONArray item) {
        Fragment_new fragment_new=new Fragment_new(mainActivity,item);
        getFragmentManager().beginTransaction().replace(R.id.fragment_display,fragment_new).commit();
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<String> loader) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        save=true;
        outState.putBoolean("save",save);
    }
}
