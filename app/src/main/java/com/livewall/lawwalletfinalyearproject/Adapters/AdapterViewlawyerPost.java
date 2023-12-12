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
import com.livewall.lawwalletfinalyearproject.LaywerActivity.LawyerCommenthere;
import com.livewall.lawwalletfinalyearproject.ModelClass.CommentModelClass;
import com.livewall.lawwalletfinalyearproject.R;
import com.livewall.lawwalletfinalyearproject.UserActivity.Commenthere;

import java.util.ArrayList;

public class AdapterViewlawyerPost extends RecyclerView.Adapter<AdapterViewlawyerPost.Viewholder> {
    ArrayList<CommentModelClass> data;
    Context context;

    public AdapterViewlawyerPost(ArrayList<CommentModelClass> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterViewlawyerPost.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rvlayoutviewpost,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewlawyerPost.Viewholder holder, int position) {
        final CommentModelClass temp = data.get(position);
        holder.name.setText(temp.getUsername());
        holder.comment.setText(temp.getComment());
//        String itemPosition = String.valueOf(holder.getAdapterPosition());
        Glide.with(context.getApplicationContext()).load(temp.getProfileimage()).into(holder.profileimage);
        Glide.with(context.getApplicationContext()).load(temp.getPostimage()).into(holder.postimage);
        holder.commenthereimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, LawyerCommenthere.class);
//                intent.putExtra("name",temp.getUsername());
//                intent.putExtra("image",temp.getProfileimage());
//                intent.putExtra("ID",temp.getUserID());
                  intent.putExtra("postkey",temp.getPostkey());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView profileimage,postimage,commenthereimage;
        TextView name,comment;
        CardView cardView;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            profileimage=itemView.findViewById(R.id.postuserimageID);
            commenthereimage=itemView.findViewById(R.id.commenthereID);
            name=itemView.findViewById(R.id.usernamepost);
            comment=itemView.findViewById(R.id.commentmsgID);
            postimage=itemView.findViewById(R.id.viewpostimageID);
//            cardView=itemView.findViewById(R.id.carviewpostID);

        }
    }
}
