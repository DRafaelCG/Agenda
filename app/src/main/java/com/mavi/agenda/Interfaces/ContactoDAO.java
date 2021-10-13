package com.mavi.agenda.Interfaces;

import com.mavi.agenda.Modelos.Contacto;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ContactoDAO {
    //Declaramos las operaciones para la db

    //Cuenta los registros de la tabla
    @Query("SELECT COUNT(*) FROM " + Contacto.TABLE_NAME)
    int count();

    /*Selecciona todos los registros de la tabla */
    @Query("SELECT * FROM " + Contacto.TABLE_NAME)
    List<Contacto> getAllContactos();

    /*Inserta un registro completo*/
    @Insert
    long insert(Contacto contacto);

    //Elimina un registro por id
    @Query("DELETE FROM " + Contacto.TABLE_NAME + " WHERE " + Contacto.COLUMN_ID + " = :id")
    int deleteById(long id);

    //Actualiza un registro
    @Update
    int updateEntidad(Contacto objContacto);

    @Query("SELECT * FROM " + Contacto.TABLE_NAME + " WHERE " + Contacto.COLUMN_ID + " = :id")
    Contacto obtenerContactoById(long id);

}
