package com.mavi.agenda.UI;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.mavi.agenda.Modelos.Contacto;
import com.mavi.agenda.R;
import com.mavi.agenda.UI.Fragments.AgendaListFragment;
import com.mavi.agenda.UI.Fragments.ContactoFragment;

public class AgendaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Ejecutamos una tarea asincrona que no tarda nada por que hay pocos registros*/
        new openFragmentTask().execute();
    }

    private class openFragmentTask extends AsyncTask<Void, Void, Void>{
        private final ProgressDialog dialog = new ProgressDialog(AgendaActivity.this, R.style.MyTheme_DayNight);
        @Override
        protected void onPreExecute(){
            dialog.setMessage("Obteniendo datos de la base...");
            dialog.setIcon(R.drawable.ic_launcher_background);
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Void doInBackground(Void... voids){
            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            if(dialog.isShowing()){
                iniciaFragment(new AgendaListFragment());
                dialog.dismiss();
            }
        }
    }

    private void iniciaFragment(Fragment fragment){
        /*Iniciamos el fragment inicial con un fragment manager para manejar Los cambios entre estos*/

        FragmentTransaction transaction = this.getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.frameFragments, fragment);
        /*Agregamos el fragment a una pila para que se pueda regresar*/
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void agregarContacto(View view) {
        /*Dependiendo de la opcion seleccionada se muestra el fragment*/
        int id = view.getId();
        switch (id){
            case R.id.fabAgregarContacto:
                iniciaFragment(new ContactoFragment());
                break;
            case R.id.fabListado:
                iniciaFragment(new AgendaListFragment());
        }
    }
}