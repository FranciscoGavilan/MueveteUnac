package com.example.mueveteunac2.viewUser.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mueveteunac2.R;
import com.example.mueveteunac2.viewUser.model.Line;
import com.example.mueveteunac2.viewUser.view.viewLine.viewRoute.RouteActivity;

import java.util.List;

public class LineAdapter extends RecyclerView.Adapter<LineAdapter.ViewHolder>{
    private LayoutInflater inflater;
    private List<Line> lineList;
    private OnLineClickListener listener;

    public void setLineList(Context context, List<Line> lineList){
        this.inflater=LayoutInflater.from(context);
        this.lineList = lineList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.line,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String lineName=lineList.get(position).getLineName();
        String lineSymbol=lineList.get(position).getLineSymbol();
        String stopFirst=lineList.get(position).getStopFirst();
        String stopLast=lineList.get(position).getStopLast();
        String route1stTurnId=lineList.get(position).getRoute1stTurnId();
        String route1stSchedule=lineList.get(position).getRoute1stSchedule();
        String route2ndTurnId=lineList.get(position).getRoute2ndTurnId();
        String route2ndSchedule=lineList.get(position).getRoute2ndSchedule();
        String driverName=lineList.get(position).getDriverName();
        String driverLastname=lineList.get(position).getDriverLastname();
        String busPlate=lineList.get(position).getBusPlate();
        holder.ruta.setText("RUTA - "+lineName.toUpperCase());
        holder.simbolo.setText(lineSymbol);
        if(route1stTurnId!=null && route2ndTurnId!=null){
            if(route1stTurnId.equals("TM") && route2ndTurnId.equals("TT")){
                holder.paraderos.setText((stopFirst+" - "+stopLast +" (Viceversa)").toUpperCase());
                holder.salida_m.setText("Salida Paradero - "+route1stSchedule);
                holder.salida_t.setText("Salida U. Callao - "+route2ndSchedule);
            }else if(route1stTurnId.equals("TT") && route2ndTurnId.equals("TN")){
                holder.paraderos.setText((stopFirst+" (Inicio) - "+stopLast+" (Fin)").toUpperCase());
                holder.salida_m.setText("Primera Salida - "+route1stSchedule);
                holder.salida_t.setText("Segunda Salida - "+route2ndSchedule);
            }
        }
        holder.chofer.setText("Chofer: "+driverName+" "+driverLastname);
        holder.placa.setText("Placa: "+busPlate);
    }

    @Override
    public int getItemCount() {
        if (lineList == null) {
            return 0;
        } else {
            return lineList.size();
        }
    }

    public interface OnLineClickListener {
        void onLineClick(Line line);
    }

    public void setOnLineClickListener(OnLineClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView ruta,simbolo,paraderos,salida_m,salida_t,chofer,placa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ruta=itemView.findViewById(R.id.ruta);
            simbolo=itemView.findViewById(R.id.simbolo);
            paraderos=itemView.findViewById(R.id.paraderos);
            salida_m=itemView.findViewById(R.id.salida_m);
            salida_t=itemView.findViewById(R.id.salida_t);
            chofer=itemView.findViewById(R.id.chofer);
            placa=itemView.findViewById(R.id.placa);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Line line = lineList.get(position);
                    listener.onLineClick(line);
                }
            }
        }
    }
}
