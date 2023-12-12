package com.livewall.lawwalletfinalyearproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.livewall.lawwalletfinalyearproject.ModelClass.BookedModelClass;
import com.livewall.lawwalletfinalyearproject.ModelClass.BookingLawyerCLass;
import com.livewall.lawwalletfinalyearproject.R;
import com.livewall.lawwalletfinalyearproject.UserActivity.ChatBoxActivity;
import com.livewall.lawwalletfinalyearproject.UserActivity.SharePdfActivity;

import java.util.ArrayList;

public class ShowLaywerforSharePdfAdapter extends RecyclerView.Adapter<ShowLaywerforSharePdfAdapter.Viewholder> {
    ArrayList<BookingLawyerCLass> data;
    Context context;

    public ShowLaywerforSharePdfAdapter(ArrayList<BookingLawyerCLass> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ShowLaywerforSharePdfAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rvshowlawyerforchat,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowLaywerforSharePdfAdapter.Viewholder holder, int position) {
        final BookingLawyerCLass temp=data.get(position);
        holder.name.setText(temp.getName());
        Glide.with(context.getApplicationContext()).load(temp.getImage()).into(holder.profileimage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SharePdfActivity.class);
                intent.putExtra("name",temp.getName());
                intent.putExtra("image",temp.getImage());
                intent.putExtra("LawID",temp.getLawyerID());
                context.startActivity(intent);
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                Bundle args = new Bundle();
//                ShowuserandLawyerDetailsFragment nextFrag= new ShowuserandLawyerDetailsFragment();
//                args.putString("uid", temp.getLawyeruid());
//                args.putString("imageurl", temp.getLawyerimage());
//                nextFrag.setArguments(args);
//                activity.getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.framelayoutid, nextFrag)
//                        .addToBackStack(null)
//                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView profileimage;
        TextView name,spec,exper,addresse,chambernumber;
        CardView cardView;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            profileimage=itemView.findViewById(R.id.imageviewbooklawID);
            name=itemView.findViewById(R.id.nameIDlawyes);
//            spec=itemView.findViewById(R.id.specilayweyid);
            cardView=itemView.findViewById(R.id.cardviewBID);
        }
    }
}
