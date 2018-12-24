package com.example.doddas.booksproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doddas.booksproject.PojoClasses.VolumeInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailedActivity extends AppCompatActivity {
   List<VolumeInfo> data;
   TextView t1,t2,t3,t4,t5;
   ImageView i1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        i1=(ImageView)findViewById(R.id.cover);
        t1=(TextView)findViewById(R.id.title1);
        t2=(TextView)findViewById(R.id.author1);
        t3=(TextView)findViewById(R.id.publisher);
        t4=(TextView)findViewById(R.id.publisherDate);
        t5=(TextView)findViewById(R.id.description);
        data= getIntent().getParcelableArrayListExtra("data");
        String image=getIntent().getStringExtra("image");
        int pos=getIntent().getIntExtra("pos",0);
        Log.i("title",data.get(pos).getTitle());
        t1.setText(data.get(pos).getTitle());
        for(int i=0;i<data.get(pos).getAuthors().size();i++)
        t2.append(data.get(pos).getAuthors().get(i)+",");
        t3.setText(String.valueOf(data.get(pos).getPublisher()));
        t4.setText(String.valueOf(data.get(pos).getPublishedDate()));
        t5.setText(data.get(pos).getDescription().toString());
        //Log.i("image",""+data.get(pos).getImageLinks().getSmallThumbnail());
        Picasso.with(this).load(image).into(i1);
        //i1.setImage(data.get(pos).getImageLinks().getSmallThumbnail());

    }
}
