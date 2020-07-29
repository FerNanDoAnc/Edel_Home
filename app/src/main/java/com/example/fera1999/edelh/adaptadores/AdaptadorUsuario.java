package com.example.fera1999.edelh.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fera1999.edelh.R;
import com.example.fera1999.edelh.clases.Usuario;

import java.util.ArrayList;

public class AdaptadorUsuario extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Usuario> listausuario;

    public AdaptadorUsuario(Context context, int layout, ArrayList<Usuario> listausuario) {
        this.context = context;
        this.layout = layout;
        this.listausuario = listausuario;
    }

    @Override
    public int getCount() {
        return listausuario.size();
    }

    @Override
    public Object getItem(int position) {
        return listausuario.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView tvnombreusuario, tvultimasesion;
    }



    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.tvnombreusuario=(TextView) row.findViewById(R.id.tvnombreusuario);
            holder.tvultimasesion=(TextView) row.findViewById(R.id.tvultimasesion);

            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Usuario usuario = listausuario.get(position);

        holder.tvnombreusuario.setText(usuario.getNombre());
        holder.tvultimasesion.setText(usuario.getLastlogin());
        System.out.println("LISTA: "+listausuario);


        return row;
    }
}