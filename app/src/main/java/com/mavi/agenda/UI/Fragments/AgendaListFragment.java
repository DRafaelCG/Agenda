package com.mavi.agenda.UI.Fragments;

import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mavi.agenda.Config.ContactoAdapter;
import com.mavi.agenda.DB.AppDB;
import com.mavi.agenda.Modelos.Contacto;
import com.mavi.agenda.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class AgendaListFragment extends Fragment{
    /*Declaramos las variables*/
    View view;
    AppDB db;
    List<Contacto> contactos;
    private RecyclerView recyclerView;
    private int counter = 0;
    TextView tvSinContactos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_agenda_list, container, false);
        tvSinContactos = (TextView)view.findViewById(R.id.tvSinContactosGuardados);
        recyclerView = (RecyclerView)view.findViewById(R.id.rvListarContactos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setClickable(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new obtenerContactosTask().execute();
        return view;
    }

    private class obtenerContactosTask extends AsyncTask<Void, Void, Void>{
        private final ProgressDialog dialogo = new ProgressDialog(getActivity(), ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
        @Override
        protected void onPreExecute(){
            dialogo.setMessage("Obteniendo contactos de la base...");
            dialogo.setIcon(R.drawable.ic_launcher_background);
            dialogo.show();
            dialogo.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Void doInBackground(Void...voids){
                obtenerContactodDB();
            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            dialogo.dismiss();
            if(contactos != null) {
                if (contactos.size() > 0) {
                    tvSinContactos.setWidth(0);
                    tvSinContactos.setHeight(0);
                    tvSinContactos.setVisibility(View.INVISIBLE);
                    recyclerView.setAdapter(new ContactoAdapter(contactos, R.layout.item_contacto, getActivity().getBaseContext()));
                }
            }
        }
    }

    void  obtenerContactodDB(){
        /*Obtenemos todos los contactos de la base*/
        db = Room.databaseBuilder(getActivity(), AppDB.class, "produccion")
                .allowMainThreadQueries()
                .build();
        contactos = db.contactoDAO().getAllContactos();
        //muestraMensaje("Obteniendo contactos de la Agenda");
        int registros = db.contactoDAO().count();
        if(registros == 0){
            //muestraMensaje("Aún no hay contactos en la Agenda");
        }
    }

    private void muestraMensaje(String mensaje){
        Toast.makeText(getActivity(), mensaje, Toast.LENGTH_LONG).show();
    }
}