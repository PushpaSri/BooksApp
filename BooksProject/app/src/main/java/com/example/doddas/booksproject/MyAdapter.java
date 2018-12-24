package com.example.doddas.booksproject;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doddas.booksproject.PojoClasses.VolumeInfo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
   List<VolumeInfo> volumeInfo;
    public MyAdapter(Context context, List<VolumeInfo> volume) {
        this.context=context;
        this.volumeInfo=volume;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     holder.t1.setText("TITLE :" +volumeInfo.get(position).getTitle());
     int i;
        Log.i("size",""+volumeInfo.get(position).getAuthors().size());
     for(i=0;i<volumeInfo.get(position).getAuthors().size();i++) {
             holder.t2.append(volumeInfo.get(position).getAuthors().get(i)+",");
             /*if(i!=volumeInfo.get(position).getAuthors().size()-1)
             {
                 holder.t2.append(",");
             }*/

     }

        Picasso.with(context).load(volumeInfo.get(position).getImageLinks().getSmallThumbnail()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return volumeInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView t1,t2;
        public ViewHolder(View itemView)
        {
            super(itemView);
            t1=(TextView)itemView.findViewById(R.id.title);
            t2=(TextView)itemView.findViewById(R.id.authors);
            imageView=(ImageView)itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context, DetailedActivity.class);
                    i.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) volumeInfo);
                    i.putExtra("pos",getAdapterPosition());
                    i.putExtra("image",volumeInfo.get(getAdapterPosition()).getImageLinks().getSmallThumbnail());
                    context.startActivity(i);
                }
            });
        }
    }
}
