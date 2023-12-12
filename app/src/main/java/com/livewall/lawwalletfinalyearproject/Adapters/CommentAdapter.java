package com.livewall.lawwalletfinalyearproject.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.livewall.lawwalletfinalyearproject.AdminActivity.Fragment.ViewBookingDetailsFragment;
import com.livewall.lawwalletfinalyearproject.ModelClass.BookAppiontmentModelClass;
import com.livewall.lawwalletfinalyearproject.ModelClass.GetCommentandPostModelClass;
import com.livewall.lawwalletfinalyearproject.R;

import java.util.ArrayList;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    ArrayList<GetCommentandPostModelClass> list;
    Context context;

    public CommentAdapter(ArrayList<GetCommentandPostModelClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.commenthererv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
     final  GetCommentandPostModelClass mbook=list.get(position);
      holder.tvview.setText(mbook.getUsername());
      holder.tvcomment.setText(mbook.getUserComment());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvview,tvcomment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvview=itemView.findViewById(R.id.textView4);
            tvcomment=itemView.findViewById(R.id.getcommenthere);
        }
    }
}
