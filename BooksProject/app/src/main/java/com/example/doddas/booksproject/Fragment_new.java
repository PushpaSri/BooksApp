package com.example.doddas.booksproject;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doddas.booksproject.PojoClasses.ImageLinks;
import com.example.doddas.booksproject.PojoClasses.VolumeInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_new extends Fragment  {

   JSONArray items=null;
   Context context;
    JSONObject real_item;
   List<VolumeInfo> info=new ArrayList();
   RecyclerView recyclerView;
   VolumeInfo volumeInfo;
    public Fragment_new() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public Fragment_new(MainActivity mainActivity, JSONArray item) {
        context=mainActivity;
        items=item;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_fragment_new, container, false);
        if(items!=null) {
            Log.i("size of","len:"+items.length());
            for (int i = 0; i < items.length(); i++) {
                try {
                    volumeInfo = new VolumeInfo();

                    ImageLinks imageLinks = new ImageLinks();
                    JSONObject real_item = items.getJSONObject(i);
                    JSONObject volumeInf = null;
                    if(real_item.has("volumeInfo")) {
                         volumeInf = real_item.getJSONObject("volumeInfo");
                    }
                    String title = volumeInf.getString("title");
                    String[] string_array;
                        JSONArray authors_array ;
                        if(volumeInf.has("authors")) {
                            authors_array = volumeInf.getJSONArray("authors");
                            string_array = new String[authors_array.length()];
                            for (int j = 0; j < authors_array.length(); j++) {
                                string_array[j] = authors_array.getString(j);
                            }
                        }
                            else{
                                string_array = new String[1];
                                string_array[0] = "NULL";
                            }
                    JSONObject image = null;
                        if(volumeInf.has("imageLinks")) {
                            image = volumeInf.getJSONObject("imageLinks");
                        }
                        if(image.has("thumbnail")) {
                            imageLinks.setSmallThumbnail(image.getString("thumbnail"));
                        }
                    volumeInfo.setAuthors(Arrays.asList(string_array));
                    volumeInfo.setImageLinks(imageLinks);
                    volumeInfo.setTitle(title);
                    if(volumeInf.has("description")){
                    volumeInfo.setDescription(volumeInf.getString("description"));}
                    else
                        volumeInfo.setDescription("NO DESCRIPTION TO SHOW");
                    if(volumeInf.has("publisher")){
                    volumeInfo.setPublisher(volumeInf.getString("publisher"));}
                    else
                        volumeInfo.setPublisher("no Publisher");
                    if(volumeInf.has("publishedDate")){
                    volumeInfo.setPublishedDate(volumeInf.getString("publishedDate"));}
                    else
                        volumeInfo.setPublishedDate("NO DATE");
                    info.add(volumeInfo);
                    Log.i("in Fragment","i"+i);

                    } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.i("length of ","le:"+info.size());
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MyAdapter(context, info));

        return v;
    }


}
