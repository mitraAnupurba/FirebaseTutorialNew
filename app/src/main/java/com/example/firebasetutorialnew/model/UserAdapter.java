package com.example.firebasetutorialnew.model;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firebasetutorialnew.DetailsActivity;
import com.example.firebasetutorialnew.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {


    private Context mContext;
    private List<User> mDatalist;
    public static final String USER_KEY = "user key";
    public UserAdapter(Context mContext, List<User> mDatalist){
        this.mContext = mContext;
        this.mDatalist = mDatalist;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        final User user = mDatalist.get(position);
        myViewHolder.textView.setText(user.getName()+"   "+user.getAge()   );

        myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = user.getUid();
                Intent intent=new Intent(mContext, DetailsActivity.class);
                intent.putExtra(USER_KEY,userId);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatalist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public MyViewHolder(View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);

        }

    }
}
