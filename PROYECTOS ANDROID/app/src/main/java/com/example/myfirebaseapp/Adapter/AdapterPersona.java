package com.example.myfirebaseapp.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myfirebaseapp.R;
import com.example.myfirebaseapp.model.Persona;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterPersona extends RecyclerView.Adapter<AdapterPersona.viewholderPersona> {


    List<Persona> PersonaList;

    public AdapterPersona(List<Persona> PersonaList) {
        this.PersonaList = PersonaList;
    }

    @NonNull
    @Override
    public viewholderPersona onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

     View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.busquedalist,parent,false);
  viewholderPersona holder = new viewholderPersona(v);

  return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull viewholderPersona holder, int position) {

        Persona ps = PersonaList.get(position);
        holder.tv_nombre.setText(ps.getNombre());
        holder.tv_apellido.setText(ps.getApellidos());
        holder.tv_correo.setText(ps.getCorreo());
        holder.tv_contraseña.setText(ps.getPassword());


    }

    @Override
    public int getItemCount()
    {
            return PersonaList.size();
    }

    public class viewholderPersona extends RecyclerView.ViewHolder {
        TextView tv_nombre,tv_apellido,tv_contraseña,tv_correo;

        public viewholderPersona(@NonNull View itemView) {
            super(itemView);
       tv_nombre = itemView.findViewById(R.id.tv_nombre );
            tv_apellido = itemView.findViewById(R.id.tv_apellido );
            tv_correo = itemView.findViewById(R.id.tv_correo );
            tv_contraseña = itemView.findViewById(R.id.tv_contraseña );

        }
    }
}
