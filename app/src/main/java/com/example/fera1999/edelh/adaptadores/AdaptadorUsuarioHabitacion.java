package com.example.fera1999.edelh.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fera1999.edelh.R;
import com.example.fera1999.edelh.clases.Usuario;
import com.example.fera1999.edelh.clases.UsuarioHabitacion;

import java.util.ArrayList;

public class AdaptadorUsuarioHabitacion extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<UsuarioHabitacion> listausuariohabitacion;

    public AdaptadorUsuarioHabitacion(Context context, int layout, ArrayList<UsuarioHabitacion> listausuariohabitacion) {
        this.context = context;
        this.layout = layout;
        this.listausuariohabitacion = listausuariohabitacion;
    }

    @Override
    public int getCount() {
        return listausuariohabitacion.size();
    }

    @Override
    public Object getItem(int position) {
        return listausuariohabitacion.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView tvnombreusuariohabitacion;
    }



    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        AdaptadorUsuarioHabitacion.ViewHolder holder = new AdaptadorUsuarioHabitacion.ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.tvnombreusuariohabitacion=(TextView) row.findViewById(R.id.tvnombreusuariohabitacion);

            row.setTag(holder);
        }
        else {
            holder = (AdaptadorUsuarioHabitacion.ViewHolder) row.getTag();
        }

        UsuarioHabitacion usuarioHabitacion = listausuariohabitacion.get(position);

        holder.tvnombreusuariohabitacion.setText(usuarioHabitacion.getUsuario());
        System.out.println("LISTA: "+listausuariohabitacion);


        return row;
    }
}