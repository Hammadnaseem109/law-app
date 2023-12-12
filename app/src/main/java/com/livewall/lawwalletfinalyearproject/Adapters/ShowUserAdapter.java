package com.livewall.lawwalletfinalyearproject.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.livewall.lawwalletfinalyearproject.AdminActivity.Fragment.ShowuserandLawyerDetailsFragment;
import com.livewall.lawwalletfinalyearproject.ModelClass.AddUserDetailToRealtym;
import com.livewall.lawwalletfinalyearproject.R;

import java.util.ArrayList;

public class ShowUserAdapter extends RecyclerView.Adapter<ShowUserAdapter.ViewHolder> {
    ArrayList<AddUserDetailToRealtym> datalist;
    Context context;

    public ShowUserAdapter(ArrayList<AddUserDetailToRealtym> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
    }

    @NonNull
    @Override
    public ShowUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.showuserlistlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowUserAdapter.ViewHolder holder, int position) {
     final AddUserDetailToRealtym temp=datalist.get(position);
        holder.name.setText(temp.getName());
        holder.city.setText(temp.getCnic());
        Glide.with(context.getApplicationContext()).load(temp.getImageurl()).placeholder(R.drawable.progressbarimage).into(holder.profileimage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Bundle args = new Bundle();
                ShowuserandLawyerDetailsFragment nextFrag= new ShowuserandLawyerDetailsFragment();
                args.putString("uid", temp.getUserID());
                nextFrag.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayoutid, nextFrag)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView profileimage;
        TextView name,city;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileimage=itemView.findViewById(R.id.createprofileimageid);
            name=itemView.findViewById(R.id.userprofilenameid);
            city=itemView.findViewById(R.id.cityid);
            cardView=itemView.findViewById(R.id.cardprofileid);

        }
    }
}
