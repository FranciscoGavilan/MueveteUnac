package com.example.mueveteunac2.viewUser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mueveteunac2.viewUser.model.Stop;
import com.example.mueveteunac2.R;

import java.util.Arrays;
import java.util.List;

public class StopAdapter extends RecyclerView.Adapter<StopAdapter.ViewHolder>{
    private LayoutInflater inflater;
    private List<Stop> stopList;
    private OnButtonStopClickListener buttonListener;
    private OnStopClickListener clickListener;
    private boolean[] expandedItems;
    private int selectedItemPosition = RecyclerView.NO_POSITION;

    public void setStopList(Context context, List<Stop> stopList){
        this.inflater=LayoutInflater.from(context);
        this.stopList=stopList;
        expandedItems=new boolean[stopList.size()];
        Arrays.fill(expandedItems, false);
    }

    @NonNull
    @Override
    public StopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.stop,parent,false);
        return new StopAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StopAdapter.ViewHolder holder, int position) {
        String stopName=stopList.get(position).getStopName();
        Integer stopOrder=stopList.get(position).getStopOrder();
        holder.nombre_paradero.setText(stopName);
        /*if(stopOrder.equals(1)){
            holder.vwTopStop.setVisibility(View.GONE);
        }else if(stopOrder.equals(stopList.size())){
            holder.vwBottomStop.setVisibility(View.GONE);
            holder.vwBelowStop.setVisibility(View.GONE);
        }*/
        if (expandedItems[position]) {
            // Mostrar el contenido adicional
            holder.additionalStopContent.setVisibility(View.VISIBLE);
        } else {
            // Ocultar el contenido adicional
            holder.additionalStopContent.setVisibility(View.GONE);
        }
    }

    public void toggleItemExpansion(int position) {
        int previousSelectedItemPosition = selectedItemPosition;
        selectedItemPosition = position;
        expandedItems[position] = !expandedItems[position];
        notifyItemChanged(position);
        if (previousSelectedItemPosition != RecyclerView.NO_POSITION) {
            expandedItems[previousSelectedItemPosition] = !expandedItems[previousSelectedItemPosition];
            notifyItemChanged(previousSelectedItemPosition);
        }

    }

    @Override
    public int getItemCount() {
        if (stopList == null) {
            return 0;
        } else {
            return stopList.size();
        }
    }

    public interface OnButtonStopClickListener {
        void onButtonStopClick(Stop stop);
    }

    public interface OnStopClickListener {
        void onStopClick(Stop stop);
    }

    public void setOnButtonStopClickListener(OnButtonStopClickListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    public void setOnStopClickListener(OnStopClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nombre_paradero;
        Button btnStopRealTime;
        View vwTopStop,vwBottomStop,vwBelowStop;
        LinearLayout additionalStopContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre_paradero=itemView.findViewById(R.id.whereabouts);
            vwTopStop=itemView.findViewById(R.id.vwTopStop);
            vwBottomStop=itemView.findViewById(R.id.vwBottomStop);
            additionalStopContent=itemView.findViewById(R.id.additionalStopContent);
            vwBelowStop=itemView.findViewById(R.id.vwBelowStop);
            btnStopRealTime=itemView.findViewById(R.id.btnStopRealTime);
            itemView.setOnClickListener(this);
            btnStopRealTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buttonListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Stop stop = stopList.get(position);
                            buttonListener.onButtonStopClick(stop);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Stop stop = stopList.get(position);
                    clickListener.onStopClick(stop);
                    toggleItemExpansion(position);
                }
            }
        }
    }

}

