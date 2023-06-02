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
    private View.OnClickListener listener;

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
        String idLine=lineList.get(position).getIdLine();
        String nameLine=lineList.get(position).getNameLine();
        String symbol=lineList.get(position).getSymbol();
        String firstStop=lineList.get(position).getFirstStop();
        String lastStop=lineList.get(position).getLastStop();
        String mDepSchedule=lineList.get(position).getfDepSchedule();
        String aDepSchedule=lineList.get(position).getsDepSchedule();
        String firstName=lineList.get(position).getFirstName();
        String firstSurname=lineList.get(position).getFirstSurname();
        String secondSurname=lineList.get(position).getSecondSurname();
        String plate=lineList.get(position).getPlate();
        holder.ruta.setText("RUTA - "+nameLine.toUpperCase());
        holder.simbolo.setText(symbol);
        holder.paraderos.setText((firstStop+" - "+lastStop).toUpperCase());
        holder.salida_m.setText("Primera Salida - "+mDepSchedule);
        holder.salida_t.setText("Segunda Salida - "+aDepSchedule);
        holder.chofer.setText("Chofer: "+firstName+" "+firstSurname+" "+secondSurname);
        holder.placa.setText("Placa: "+plate);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(holder.itemView.getContext(), RouteActivity.class);
                intent.putExtra("idLine",idLine);
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
