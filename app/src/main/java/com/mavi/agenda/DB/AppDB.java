package com.mavi.agenda.DB;

import com.mavi.agenda.Interfaces.ContactoDAO;
import com.mavi.agenda.Modelos.Contacto;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contacto.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    //Obtenemos las operaciones que se pueden realizar de la interfaz DAO
    public abstract ContactoDAO contactoDAO();

    //Creamos un sigleton(única instancia) de la base
    private static AppDB sInstancia;

}
