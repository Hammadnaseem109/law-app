package com.livewall.lawwalletfinalyearproject.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.livewall.lawwalletfinalyearproject.LaywerActivity.ChatwithuserActivityLawyer;
import com.livewall.lawwalletfinalyearproject.ModelClass.AddLawyerDetailToRealtym;
import com.livewall.lawwalletfinalyearproject.ModelClass.BookAppiontmentModelClass;
import com.livewall.lawwalletfinalyearproject.ModelClass.BookedModelClass;
import com.livewall.lawwalletfinalyearproject.R;

import java.util.ArrayList;

public class ShowSeeUSerForChatAdapter extends RecyclerView.Adapter<ShowSeeUSerForChatAdapter.Viewholder> {
    ArrayList<BookAppiontmentModelClass> data;
    Context context;

    public ShowSeeUSerForChatAdapter(ArrayList<BookAppiontmentModelClass> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ShowSeeUSerForChatAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rvshowforchat,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowSeeUSerForChatAdapter.Viewholder holder, int position) {
        final BookAppiontmentModelClass temp=data.get(position);
        holder.name.setText(temp.getName());
//        holder.spec.setText(temp.getId());
//        Glide.with(context.getApplicationContext()).load(temp.).into(holder.profileimage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                Bundle args = new Bundle();
//                ShowuserandLawyerDetailsFragment nextFrag= new ShowuserandLawyerDetailsFragment();
//                args.putString("uid", temp.getId());
//                args.putString("imageurl", temp.getName());
//                nextFrag.setArguments(args);
//                activity.getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.framelayoutid, nextFrag)
//                        .addToBackStack(null)
//                        .commit();
                Intent intent=new Intent(context, ChatwithuserActivityLawyer.class);
                intent.putExtra("uid", temp.getId());
                intent.putExtra("nameID", temp.getName());
                context.startActivity(intent);
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
//            profileimage=itemView.findViewById(R.id.createprofileimageid);
            name=itemView.findViewById(R.id.pdfnameID);
//            spec=itemView.findViewById(R.id.specilayweyid);
            cardView=itemView.findViewById(R.id.cardviewBID);
        }
    }
}
