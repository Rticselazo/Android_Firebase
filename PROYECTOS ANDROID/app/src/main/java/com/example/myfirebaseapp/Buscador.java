package com.example.myfirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import com.example.myfirebaseapp.Adapter.AdapterPersona;
import com.example.myfirebaseapp.model.Persona;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Buscador extends AppCompatActivity {
    DatabaseReference ref ;
    ArrayList<Persona> list;
    RecyclerView rv;
    AdapterPersona adapter;
SearchView seachView;
    LinearLayoutManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
    ref = FirebaseDatabase.getInstance().getReference().child("Persona");
    rv = findViewById(R.id.rv);
    seachView =findViewById(R.id.seach);
    lm = new LinearLayoutManager(this);
    rv.setLayoutManager(lm);

    list = new ArrayList<>();
        adapter = new AdapterPersona(list);
rv.setAdapter(adapter);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
           for(DataSnapshot snapshot : dataSnapshot.getChildren()){
               Persona ms =  snapshot.getValue(Persona.class);
               list.add(ms);
           }
           adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
               buscar(s);
                return false;
            }
        });

    }

    private void buscar(String s) {
    ArrayList<Persona>mylista = new ArrayList<>();
    for (Persona obj: list){
        if(obj.getNombre().toLowerCase().contains(s.toLowerCase())) {
          mylista.add(obj);
        }
        }

    AdapterPersona adapterPersona = new AdapterPersona(mylista);
    rv.setAdapter(adapter);
*/
    }

}

