package com.example.fera1999.edelh.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fera1999.edelh.R;
import com.example.fera1999.edelh.clases.Switches;
import com.example.fera1999.edelh.clases.Usuario;

import java.util.ArrayList;

public class AdaptadorSwitches extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Switches> listaSwtiches;

    public AdaptadorSwitches(Context context, int layout, ArrayList<Switches> listaSwtiches) {
        this.context = context;
        this.layout = layout;
        this.listaSwtiches = listaSwtiches;
    }

    @Override
    public int getCount() {
        return listaSwtiches.size();
    }

    @Override
    public Object getItem(int position) {
        return listaSwtiches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView txtPlace, txtBulbState;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtPlace= row.findViewById(R.id.txtPlace);
            holder.txtBulbState= row.findViewById(R.id.txtBulbState);

            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }
        Switches switches = listaSwtiches.get(position);
        holder.txtPlace.setText(switches.getplace());
        holder.txtBulbState.setText(switches.getbulbState());


        return row;
    }
}