package com.mavi.agenda.Config;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mavi.agenda.DB.AppDB;
import com.mavi.agenda.Modelos.Contacto;
import com.mavi.agenda.R;
import com.mavi.agenda.UI.Fragments.AgendaListFragment;
import com.mavi.agenda.UI.Fragments.ContactoFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ContactoViewHolder>{
    /*Creamos las variables que se van a utilizar*/
    List<Contacto> contactos;
    private int rowLayout;
    private Context context;
    AppDB db;

    public ContactoAdapter(List<Contacto> contactos, int rowLayout, Context context){
        this.contactos = contactos;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactoAdapter.ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoAdapter.ContactoViewHolder holder, int position){
        holder.tvNombre.setText(contactos.get(position).getNombre());
        holder.tvTelefono.setText(contactos.get(position).getTelefono());
        holder.tvCumpleanios.setText(contactos.get(position).getFechaNac());
        holder.tvNota.setText(contactos.get(position).getNota());
        holder.cVContactos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View view = LayoutInflater.from(context).inflate(R.layout.dialogo, null);
                builder.setView(view);
                builder.setMessage("Va a eliminar el registro, desea continuar?");
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarContacto(contactos.get(position).getId());
                        contactos.remove(position);
                        notifyItemRemoved(position);
                        dialog.dismiss();
                        AgendaListFragment fragment = new AgendaListFragment();
                        ((FragmentActivity)v.getContext()).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameFragments, fragment)
                                .commit();
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
        holder.cVContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("idContacto", contactos.get(position).getId());
                ContactoFragment fragment = new ContactoFragment();
                fragment.setArguments(bundle);
                ((FragmentActivity)v.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameFragments, fragment)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount(){
        return contactos.size();
    }

    class ContactoViewHolder extends RecyclerView.ViewHolder{
        /*Declaramos las vatiables del viewholder*/
        LinearLayout contactosLayout;
        TextView tvNombre;
        TextView tvTelefono;
        TextView tvCumpleanios;
        TextView tvNota;
        CardView cVContactos;

        public ContactoViewHolder(View itemView){
            /*Asignamos las variables a sus respectivos controles*/
            super(itemView);
            contactosLayout = (LinearLayout) itemView.findViewById(R.id.layout_contactos);
            tvNombre = (TextView)itemView.findViewById(R.id.tvNombre);
            tvTelefono = (TextView)itemView.findViewById(R.id.tvTelefono);
            tvCumpleanios = (TextView)itemView.findViewById(R.id.tvCumpleanios);
            tvNota = (TextView)itemView.findViewById(R.id.tvNota);
            cVContactos = (CardView)itemView.findViewById(R.id.cVContactos);
        }
    }

    private void eliminarContacto(long id){
        db = Room.databaseBuilder(context, AppDB.class, "produccion")
                .allowMainThreadQueries()
                .build();
        db.contactoDAO().deleteById(id);
        Toast.makeText(context, "Contacto eliminado", Toast.LENGTH_LONG).show();
    }
}
