package com.example.mueveteunac2.viewUser.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mueveteunac2.viewUser.model.Stop;
import com.example.mueveteunac2.viewUser.view.viewLine.viewRoute.viewStop.StopActivity;
import com.example.mueveteunac2.R;
import java.util.ArrayList;

public class StopAdapter extends RecyclerView.Adapter<StopAdapter.ViewHolder> implements View.OnClickListener{
    LayoutInflater inflater;
    ArrayList<Stop> model;

    private View.OnClickListener listener;
    public StopAdapter(Context context, ArrayList<Stop> model){
        this.inflater=LayoutInflater.from(context);
        this.model=model;
    }
    @NonNull
    @Override
    public StopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.stop,parent,false);
        view.setOnClickListener(this);
        return new StopAdapter.ViewHolder(view);
    }

    public void setOnclickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onBindViewHolder(@NonNull StopAdapter.ViewHolder holder, int position) {
        final Stop item=model.get(position);
        String idLine=model.get(position).getIdLine();
        String idStop=model.get(position).getIdStop();
        String nameStop=model.get(position).getNameStop();
        String shift=model.get(position).getShift();
        String nameLine=model.get(position).getNameLine();
        holder.nombre_paradero.setText(nameStop);
        holder.btnObservarparadero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(holder.itemView.getContext(), StopActivity.class);
                intent.putExtra("Stop",item);
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre_paradero;
        ImageButton btnObservarparadero;

        Context context;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre_paradero=itemView.findViewById(R.id.whereabouts);
            btnObservarparadero=itemView.findViewById(R.id.btnObservarparadero);

        }


    }

}

