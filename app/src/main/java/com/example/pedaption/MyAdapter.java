package com.example.pedaption;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Serializable {

    public ArrayList<Model> mList;
    Context context;

    public MyAdapter(Context context,ArrayList<Model> mList){
        this.mList = mList;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        Model model =  mList.get(position);
        holder.name.setText(model.getName());
        holder.age.setText("Cat age:"+model.getAge());
        String imgURL = null;
        imgURL = model.getImageUrl();
        Picasso.get().load(imgURL).into(holder.imgview);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context,Details.class);
                i.putExtra("title",mList.get(position));
                context.startActivity(i);
            }
        });

        //holder.imgview.setImageURI(Uri.parse(model.getImageUrl()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements  Serializable{
        TextView name,age;
        ImageView imgview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgview = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
        }
    }
}
