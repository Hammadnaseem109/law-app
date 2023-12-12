package com.livewall.lawwalletfinalyearproject.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.livewall.lawwalletfinalyearproject.AdminActivity.Fragment.ShowuserandLawyerDetailsFragment;
import com.livewall.lawwalletfinalyearproject.AdminActivity.Fragment.ViewBookingDetailsFragment;
import com.livewall.lawwalletfinalyearproject.LaywerActivity.Fragment.ViewBookAppointmentsFragment;
import com.livewall.lawwalletfinalyearproject.ModelClass.BookAppiontmentModelClass;
import com.livewall.lawwalletfinalyearproject.R;

import java.util.ArrayList;


public class ViewBookingAdapter extends RecyclerView.Adapter<ViewBookingAdapter.ViewHolder> {
    ArrayList<BookAppiontmentModelClass> list;
    Context context;
    String stringg;

    public ViewBookingAdapter(ArrayList<BookAppiontmentModelClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewBookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewappointmentlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBookingAdapter.ViewHolder holder, int position) {
       BookAppiontmentModelClass mbook=list.get(position);

     holder.view.setText(mbook.getName());
     holder.phone.setText(mbook.getPhonenumber());

      holder.cardView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              AppCompatActivity activity = (AppCompatActivity) view.getContext();
              Bundle args = new Bundle();
              ViewBookingDetailsFragment nextFrag= new ViewBookingDetailsFragment();
              args.putString("bookname",mbook.getName());
              args.putString("phonenumber",mbook.getPhonenumber());
              args.putString("msg",mbook.getMessage());
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
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView view,phone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardviewID);
            imageView=itemView.findViewById(R.id.circleImageView2);
            view=itemView.findViewById(R.id.bookingtextviewID);
            phone=itemView.findViewById(R.id.phone0000000);

        }
    }
}
