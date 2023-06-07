package com.example.mueveteunac2.viewUser.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mueveteunac2.R;
import com.example.mueveteunac2.viewUser.model.Line;
import com.example.mueveteunac2.viewUser.view.viewLine.viewRoute.RouteActivity;

import java.util.List;

public class LineAdapter extends RecyclerView.Adapter<LineAdapter.ViewHolder> implements View.OnClickListener{
    LayoutInflater inflater;
    List<Line> lineList;
    View.OnClickListener listener;

    public void setLineList(Context context, List<Line> lineList){
        this.inflater=LayoutInflater.from(context);
        this.lineList = lineList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.line,parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String lineId=lineList.get(position).getLineId();
        String lineName=lineList.get(position).getLineName();
        String lineSymbol=lineList.get(position).getLineSymbol();
        String stopFirst=lineList.get(position).getStopFirst();
        String stopLast=lineList.get(position).getStopLast();
        String route1stSchedule=lineList.get(position).getRoute1stSchedule();
        String route2ndSchedule=lineList.get(position).getRoute2ndSchedule();
        String driverName=lineList.get(position).getDriverName();
        String driverLastname=lineList.get(position).getDriverLastname();
        String busPlate=lineList.get(position).getBusPlate();
        holder.ruta.setText("RUTA - "+lineName.toUpperCase());
        holder.simbolo.setText(lineSymbol);
        holder.paraderos.setText((stopFirst+" - "+stopLast).toUpperCase());
        holder.salida_m.setText("Primera Salida - "+route1stSchedule);
        holder.salida_t.setText("Segunda Salida - "+route2ndSchedule);
        holder.chofer.setText("Chofer: "+driverName+" "+driverLastname);
        holder.placa.setText("Placa: "+busPlate);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(holder.itemView.getContext(), RouteActivity.class);
                intent.putExtra("idLine",lineId);
                intent.putExtra("shift","Mañana");
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (lineList == null) {
            return 0;
        } else {
            return lineList.size();
        }
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ruta,simbolo,paraderos,salida_m,salida_t,chofer,placa;

        Context context;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            ruta=itemView.findViewById(R.id.ruta);
            simbolo=itemView.findViewById(R.id.simbolo);
            paraderos=itemView.findViewById(R.id.paraderos);
            salida_m=itemView.findViewById(R.id.salida_m);
            salida_t=itemView.findViewById(R.id.salida_t);
            chofer=itemView.findViewById(R.id.chofer);
            placa=itemView.findViewById(R.id.placa);

        }
    }
}
