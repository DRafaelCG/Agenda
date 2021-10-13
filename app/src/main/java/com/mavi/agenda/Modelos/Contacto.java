package com.mavi.agenda.Modelos;

import android.content.ContentValues;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = Contacto.TABLE_NAME)
public class Contacto {
    /*Declaramos el nombre de la tabla*/
    public static final String TABLE_NAME = "Agenda";
    public static final String COLUMN_NAME = "Nombre";

    //Declaramos el nombre de la columna ID
    public static final String COLUMN_ID = BaseColumns._ID;

    /*Asignamos la pripiedad de incrementar el id automaticamente*/
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;
    /*Creamos los atributos o campos de la tabla*/
    @ColumnInfo(name = "Nombre")
    private String nombre;

    @ColumnInfo(name = "Teléfono")
    private String telefono;

    @ColumnInfo(name = "FechaNac")
    private String fechaNac;

    @ColumnInfo(name = "Nota")
    private String nota;

    /*Creamos un constructor vacío para instanciar la clase en la aplicacion, no en la base*/
    @Ignore
    public Contacto(){

    }

    /*Creamos el constructor con todos los parametros*/
    public Contacto(long id, String nombre, String telefono, String fechaNac, String nota) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fechaNac = fechaNac;
        this.nota = nota;
    }

    /*Creamos los getters y los setters*/
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public static Contacto fromContentValues(ContentValues values){
        final Contacto objContacto = new Contacto();
        if (values.containsKey(COLUMN_ID)){
            objContacto.id = values.getAsLong(COLUMN_ID);
        }
        if (values.containsKey(COLUMN_NAME)){
            objContacto.setId(1);
        }
        return objContacto;
    }
}
