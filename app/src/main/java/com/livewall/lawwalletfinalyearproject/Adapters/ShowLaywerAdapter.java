package com.livewall.lawwalletfinalyearproject.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.livewall.lawwalletfinalyearproject.AdminActivity.Fragment.ShowuserandLawyerDetailsFragment;
import com.livewall.lawwalletfinalyearproject.ModelClass.AddLawyerDetailToRealtym;
import com.livewall.lawwalletfinalyearproject.R;

import java.util.ArrayList;

public class ShowLaywerAdapter extends RecyclerView.Adapter<ShowLaywerAdapter.Viewholder> implements Filterable {
    ArrayList<AddLawyerDetailToRealtym> data;
    ArrayList<AddLawyerDetailToRealtym> backup;
    Context context;

    public ShowLaywerAdapter(ArrayList<AddLawyerDetailToRealtym> data, Context context) {
        this.data = data;
        backup = new ArrayList<>(data);
        this.context = context;
    }

    @NonNull
    @Override
    public ShowLaywerAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showlawyerlistlayout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowLaywerAdapter.Viewholder holder, int position) {
        final AddLawyerDetailToRealtym temp = data.get(position);
        holder.name.setText(temp.getLawyername());
        holder.spec.setText(temp.getSpec());
        Glide.with(context.getApplicationContext()).load(temp.getImageuri()).placeholder(R.drawable.progressbarimage).into(holder.profileimage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Bundle args = new Bundle();
                ShowuserandLawyerDetailsFragment nextFrag = new ShowuserandLawyerDetailsFragment();
                args.putString("uid", temp.getCurrentuid());
                args.putString("imageurl", temp.getImageuri());
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
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {

            ArrayList<AddLawyerDetailToRealtym> filtereddata = new ArrayList<>();

            if (keyword.toString().isEmpty())
                filtereddata.addAll(backup);
            else {
                for (AddLawyerDetailToRealtym obj : backup) {
                    if (obj.getLawyername().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filtereddata.add(obj);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtereddata;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            data.clear();
            data.addAll((ArrayList<AddLawyerDetailToRealtym>) filterResults.values);
            notifyDataSetChanged();

        }
    };

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView profileimage;
        TextView name, spec, exper, addresse, chambernumber;
        CardView cardView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            profileimage = itemView.findViewById(R.id.createprofileimageid);
            name = itemView.findViewById(R.id.userprofilenameid);
            spec = itemView.findViewById(R.id.specilayweyid);
            cardView = itemView.findViewById(R.id.cardprofileid);

        }
    }
}


