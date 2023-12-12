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
import com.livewall.lawwalletfinalyearproject.LaywerActivity.ReadLawyerActivity;
import com.livewall.lawwalletfinalyearproject.ModelClass.BookingLawyerCLass;
import com.livewall.lawwalletfinalyearproject.ModelClass.SharePdfModelClass;
import com.livewall.lawwalletfinalyearproject.R;
import com.livewall.lawwalletfinalyearproject.UserActivity.ChatBoxActivity;
import com.livewall.lawwalletfinalyearproject.UserActivity.ReadPdfActivity;

import java.util.List;

public class ViewuploaedPdfAdapter extends RecyclerView.Adapter<ViewuploaedPdfAdapter.Viewholder> {
    List<SharePdfModelClass> data;
    Context context;

    public ViewuploaedPdfAdapter(List<SharePdfModelClass> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewuploaedPdfAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpdfrvlayout,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewuploaedPdfAdapter.Viewholder holder, int position) {
        final SharePdfModelClass temp=data.get(position);
        holder.name.setText(temp.getPdfname());
//        Glide.with(context.getApplicationContext()).load(temp.getImage()).into(holder.profileimage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ReadLawyerActivity.class);
//                intent.putExtra("name",temp.getName());
//                intent.putExtra("image",temp.getImage());
//                intent.putExtra("LawID",temp.getLawyerID());
                 intent.putExtra("pdfurl",temp.getPdfurl());
                 intent.putExtra("ID",temp.getCurentusername());
                 intent.putExtra("pdfname",temp.getPdfname());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        TextView name;
        CardView cardView;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
//            profileimage=itemView.findViewById(R.id.imageviewbooklawID);
            name=itemView.findViewById(R.id.getnamepdfID);
//            spec=itemView.findViewById(R.id.specilayweyid);
            cardView=itemView.findViewById(R.id.cardID);
        }
    }
}
