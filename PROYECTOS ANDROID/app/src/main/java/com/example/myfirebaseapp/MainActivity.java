package com.example.myfirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myfirebaseapp.model.Persona;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private List<Persona> listPerson =  new ArrayList<Persona>();
    ArrayAdapter<Persona> arrayAdapterPersona;
    EditText nomP,apeP,Corre,Pass;
    ListView listV_persona;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Persona personaSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomP = findViewById(R.id.txtNombre);
    apeP = findViewById(R.id.txtApellido);
    Corre = findViewById(R.id.txtCorreo);
    Pass = findViewById(R.id.txtContraseña);
    listV_persona = findViewById(R.id.lv_datosPersonas);

    //siempre estar el metodo iniciarfirebase en el inicio
    iniciarFirebase();
    listarDatos();

//hereda los datos en las cajas de texto
listV_persona.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        personaSelect = (Persona) parent.getItemAtPosition(position);
        nomP.setText(personaSelect.getNombre());
        apeP.setText(personaSelect.getApellidos());
        Corre.setText(personaSelect.getCorreo());
        Pass.setText(personaSelect.getPassword());
    }
});



    }

    private void listarDatos() {
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPerson.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Persona p = objSnaptshot.getValue(Persona.class);
                    listPerson.add(p);

                    arrayAdapterPersona = new ArrayAdapter<Persona>( MainActivity.this, android.R.layout.simple_list_item_1,listPerson);
                    listV_persona.setAdapter(arrayAdapterPersona);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void iniciarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();
        //agregar los datos que fueron almacenados en la memoria cache al volver conectarse a internet

        //se incializo el proceso en la clase FireBaseApp y declarado en MANIFEST
        //firebaseDatabase.setPersistenceEnabled(true);
        //
        databaseReference = firebaseDatabase.getReference();
    }
  /*  public void Siguiente (View view){
        Intent siguiente = new Intent(this, Buscador.class);
        startActivity(siguiente);
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       //se direcciona al xml de diseño
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String nombre = nomP.getText().toString();
        String apellido = apeP.getText().toString();
        String cor = Corre.getText().toString();
        String password = Pass.getText().toString();

        switch (item.getItemId()) {
            case R.id.icon_busca:{
  startActivity(new Intent(MainActivity.this,Listado.class));
         break;
            }
            //guarda
            case R.id.icon_add:{
        if (nombre.equals("")||cor.equals("")||password.equals("")||apellido.equals("")){
         validacion();
        }
        else {
            Persona p = new Persona();
            p.setUid(UUID.randomUUID().toString());
            p.setNombre(nombre);
            p.setApellidos(apellido);
            p.setCorreo(cor);
            p.setPassword(password);
            //agregado valores de clase persona al firebase
            databaseReference.child("Persona").child(p.getUid()).setValue(p);


            Toast.makeText(this, "Agregado", Toast.LENGTH_LONG).show();
         limpiarCajas();
            break;
        }
        }
        //actualiza
            case R.id.icon_save:{
                Persona p = new Persona();
                p.setUid(personaSelect.getUid());
                p.setNombre(nomP.getText().toString().trim());
                p.setApellidos(apeP.getText().toString().trim());
                p.setCorreo(Corre.getText().toString().trim());
                p.setPassword(Pass.getText().toString().trim());
databaseReference.child("Persona").child(p.getUid()).setValue(p);
                Toast.makeText(this,"Actualizado",Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            case R.id.icon_delete:{
                Persona p = new Persona();
                p.setUid(personaSelect.getUid());
                databaseReference.child("Persona").child(p.getUid()).removeValue();
                Toast.makeText(this,"Eliminado",Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            case R.id.icon_exit:{
                AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this,"sesion cerrada",Toast.LENGTH_SHORT).show();
                        finish();
                        Intent siguiente = new Intent(MainActivity.this, Login.class);
                        startActivity(siguiente);

                    }
                });
            }

            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void limpiarCajas() {
nomP.setText("");
Corre.setText("");
apeP.setText("");
Pass.setText("");

    }


    private void validacion(){
        String nombre = nomP.getText().toString();
        String apellido = apeP.getText().toString();
        String contra = Pass.getText().toString();
        String correo = Corre.getText().toString();
        if (nombre.equals("")){
            nomP.setError("Required");
        }
        else if (apellido.equals("")){
            nomP.setError("Required");
        }
        else if (contra.equals("")){
            nomP.setError("Required");
        }
        else if (correo.equals("")){
            nomP.setError("Required");
        }
    }


}
