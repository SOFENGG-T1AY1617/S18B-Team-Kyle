package com.example.avggo.attendancechecker.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avggo.attendancechecker.R;
import com.example.avggo.attendancechecker.model.Attendance;
import com.example.avggo.attendancechecker.model.ListItem;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by avggo on 10/12/2016.
 */

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceHolder>{

    private List<Attendance> listData;
    private LayoutInflater inflater;

    private ItemClickCallback itemClickCallback;

    public void setListData(ArrayList listData) {
        this.listData = listData;
    }

    public interface ItemClickCallback{
        void onItemClick(int p);
        void onSecondaryClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback){
        this.itemClickCallback = itemClickCallback;
    }

    public AttendanceAdapter(List<Attendance> listData, Context c){
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public AttendanceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new AttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(AttendanceHolder holder, int position) {
        Attendance item = listData.get(position);
        holder.title.setText(item.getRoom());
        holder.subTitle.setText(item.getFname());
        int i = R.drawable.bulos;
        Log.d("TEST", Integer.toString(i));
        holder.thumbnail.setImageResource(R.drawable.bulos);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class AttendanceHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title;
        private TextView subTitle;
        private CircleImageView thumbnail;
        private View container;

        public AttendanceHolder(View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.lbl_item_text);
            subTitle = (TextView)itemView.findViewById(R.id.lbl_item_sub_title);
            thumbnail = (CircleImageView) itemView.findViewById(R.id.im_item_icon);
            container = itemView.findViewById(R.id.cont_item_root);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.cont_item_root){
                itemClickCallback.onItemClick(getAdapterPosition());
            }
            else{
                itemClickCallback.onSecondaryClick(getAdapterPosition());
            }
        }
    }
}
