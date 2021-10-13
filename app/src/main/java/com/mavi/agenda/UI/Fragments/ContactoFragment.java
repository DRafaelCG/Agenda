package com.mavi.agenda.UI.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.mavi.agenda.DB.AppDB;
import com.mavi.agenda.Modelos.Contacto;
import com.mavi.agenda.R;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

public class ContactoFragment extends Fragment{
    /*Declaramos las variables que vamos a utilizar*/
    View view;
    AppDB db;
    TextInputLayout tilNombre, tilTelefono, tilFechaNac, tilNota;
    Button bttnAgregar, bttnEditar;
    long idContacto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contacto, container, false);
        /*Asignamos las variables con sus respectivos controles*/
        tilNombre = (TextInputLayout) view.findViewById(R.id.tilNombre);
        tilTelefono = (TextInputLayout)view.findViewById(R.id.tilTelefono);
        tilFechaNac = (TextInputLayout)view.findViewById(R.id.tilFechaNac);
        tilNota = (TextInputLayout)view.findViewById(R.id.tilNota);
        bttnAgregar = (Button)view.findViewById(R.id.btnAgregar);
        bttnEditar = (Button)view.findViewById(R.id.btnEditar);
        /*Asignamos la db a su única instancia*/
        db = Room.databaseBuilder(getActivity(), AppDB.class, "produccion")
                .allowMainThreadQueries()
                .build();
        bttnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Validamos que los campos no vengan vacios*/
                if(TextUtils.isEmpty(tilNombre.getEditText().getText()) ||
                TextUtils.isEmpty(tilTelefono.getEditText().getText()) ||
                TextUtils.isEmpty(tilFechaNac.getEditText().getText())){
                    muestraMensaje("Faltan datos del contacto!");
                }else {
                    /*Agreamos el contacto a la base con los datos que se capturaron*/
                    Contacto c = new Contacto();
                    c.setNombre(tilNombre.getEditText().getText().toString());
                    c.setTelefono(tilTelefono.getEditText().getText().toString());
                    c.setFechaNac(tilFechaNac.getEditText().getText().toString());
                    c.setNota(tilNota.getEditText().getText().toString());
                    long insertado = db.contactoDAO().insert(c);
                    muestraMensaje("Contacto agendado con id: " + insertado);
                    listarContactos();
                }
            }
        });
        bttnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cuando viene el primer on click del boton sólo ponemos los campos editables t le cambiamos el texto al boton
                tilNombre.setEnabled(true);
                tilTelefono.setEnabled(true);
                tilFechaNac.setEnabled(true);
                tilNota.setEnabled(true);
                /*Despues en el segundo click ya viene con el texto actualizar y actualizamos los datos del contacto*/
                if(bttnEditar.getText().toString().matches("Actualizar")){
                    if(idContacto > 0) {
                        Contacto c = new Contacto();
                        c.setId(idContacto);
                        c.setNombre(tilNombre.getEditText().getText().toString());
                        c.setTelefono(tilTelefono.getEditText().getText().toString());
                        c.setFechaNac(tilFechaNac.getEditText().getText().toString());
                        c.setNota(tilNota.getEditText().getText().toString());
                        db.contactoDAO().updateEntidad(c);
                        muestraMensaje("Contacto actualizado");
                        bttnEditar.setText("Editar");
                        listarContactos();
                    }
                }
                bttnEditar.setText("Actualizar");
            }
        });
        /*Obtenemos los valores que se enviaron desde otro fragment y mostramos el contacto tomando como referencia su id*/
        Bundle b = getArguments();
        if(b != null){
            idContacto = b.getLong("idContacto");
            obtenerDatosDB(idContacto);
        }
        return view;
    }

    private void muestraMensaje(String mensaje){
        Toast.makeText(getActivity().getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }

    private void listarContactos(){
        /*Cambiamos el fragmento actual para mostrar el listado de Contactos*/
        Fragment f = new AgendaListFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.frameFragments, f);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void obtenerDatosDB(long id){
        /*Obtenemos los datos de la bd*/
         db = Room.databaseBuilder(getActivity(), AppDB.class, "produccion")
                .allowMainThreadQueries()
                .build();
         /*Obtenemos el contacto por id para actualizar sus datos*/
        Contacto c = db.contactoDAO().obtenerContactoById(id);
        if(c != null){
            /*Si el contacto existe en la base retomamos sus datos para lectura*/
            tilNombre.getEditText().setText(c.getNombre());
            tilNombre.setEnabled(false);
            tilTelefono.getEditText().setText(c.getTelefono());
            tilTelefono.setEnabled(false);
            tilFechaNac.getEditText().setText(c.getFechaNac());
            tilFechaNac.setEnabled(false);
            tilNota.getEditText().setText(c.getNota());
            tilNota.setEnabled(false);
            bttnAgregar.setVisibility(View.INVISIBLE);
            bttnEditar.setVisibility(View.VISIBLE);
        }

    }


}