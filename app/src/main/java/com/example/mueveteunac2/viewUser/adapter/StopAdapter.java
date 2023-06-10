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
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class StopAdapter extends RecyclerView.Adapter<StopAdapter.ViewHolder> implements View.OnClickListener{
    LayoutInflater inflater;
    List<Stop> stopList;

    View.OnClickListener listener;
    public void setStopList(Context context, List<Stop> stopList){
        this.inflater=LayoutInflater.from(context);
        this.stopList=stopList;
    }
    @NonNull
    @Override
    public StopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.stop,parent,false);
        view.setOnClickListener(this);
        return new StopAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StopAdapter.ViewHolder holder, int position) {
        String stopId=stopList.get(position).getStopId();
        String stopName=stopList.get(position).getStopName();
        Integer stopOrder=stopList.get(position).getStopOrder();
        holder.nombre_paradero.setText(stopName);
        if(stopOrder==1){
            holder.vwUpStop.setVisibility(View.GONE);
        }else if(stopOrder==stopList.size()){
            holder.vwDownStop.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(holder.itemView.getContext(), StopActivity.class);
                intent.putExtra("stopId",stopId);
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (stopList == null) {
            return 0;
        } else {
            return stopList.size();
        }
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre_paradero;
        /*ImageButton btnObservarparadero;*/
        View vwUpStop,vwDownStop;

        Context context;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre_paradero=itemView.findViewById(R.id.whereabouts);
            vwUpStop=itemView.findViewById(R.id.vwUpStop);
            vwDownStop=itemView.findViewById(R.id.vwDownStop);
            /*btnObservarparadero=itemView.findViewById(R.id.btnObservarparadero);*/

        }


    }

}

