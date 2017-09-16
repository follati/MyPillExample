package com.mypill.mypill.Clases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Programador on 03/09/2017.
 */

public class DataSource {
    private SQLiteDatabase sqLiteDatabase;
    private DataBase dataBase;

    public DataSource(Context context) {
        dataBase = new DataBase(context);
    }

    public void open() throws SQLException {
        sqLiteDatabase = dataBase.getReadableDatabase();
    }

    public void close() {
        dataBase.close();
    }

    public int NewIdTratamiento() {
        String sql = "select * from " + DataBase.TABLE_TRATAMIENTO;
        List lista1 = new ArrayList();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        int id =0;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
                lista1.add(id);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return id + 1; //lleva el último valor
    }

    public int NewIdHistorial() {
        String sql = "select * from " + DataBase.TABLE_HISTORIAL;
        List lista1 = new ArrayList();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        int id =0;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
                lista1.add(id);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return id + 1; //lleva el último valor
    }

    public long Insert_Tratamiento(Tratamiento tratamiento)  {
        ContentValues values = new ContentValues();
        values.put(DataBase.TRAMIENTO_COLUMN_ID,tratamiento.getId_tratamiento());
        values.put(DataBase.TRAMIENTO_COLUMN_MEDICAMENTO,tratamiento.getMedicamento());
        values.put(DataBase.TRAMIENTO_COLUMN_HORA,tratamiento.getHora());
        values.put(DataBase.TRAMIENTO_COLUMN_MINUTO,tratamiento.getMinuto());
        values.put(DataBase.TRAMIENTO_COLUMN_METODO_,tratamiento.getMetodo());
        values.put(DataBase.TRAMIENTO_COLUMN_DURACION,tratamiento.getDuracion());
        values.put(DataBase.TRAMIENTO_COLUMN_REPETICION,tratamiento.getRepeticion());

        long insert=0;
        try {
            insert = sqLiteDatabase.insert(DataBase.TABLE_TRATAMIENTO, null, values);

        }catch (Exception ex){

            String a=ex.getMessage();
        }
        return insert;
    }

    public long Insert_Historial(Historial historial)  {
        ContentValues values = new ContentValues();
        values.put(DataBase.HISTORIAL_COLUMN_TRATAMIENTO,historial.getId_tratamiento());
        values.put(DataBase.HISTORIAL_COLUMN_HORA,historial.getHora());
        values.put(DataBase.HISTORIAL_COLUMN_MINUTO,historial.getMinuto());
        values.put(DataBase.HISTORIAL_COLUMN_FECHA,historial.getFecha());
        values.put(DataBase.HISTORIAL_COLUMN_ESTADO,historial.getEstado());

        long insert=0;
        try {
            insert = sqLiteDatabase.insert(DataBase.TABLE_HISTORIAL, null, values);

        }catch (Exception ex){

            String a=ex.getMessage();
        }
        return insert;
    }

    public List<Tratamiento> GetTratamientos(){
        List<Tratamiento> list = new ArrayList<Tratamiento>();
        String query =("SELECT * FROM "+DataBase.TABLE_TRATAMIENTO);
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                Tratamiento tratamiento = new Tratamiento();
                tratamiento.setId_tratamiento(cursor.getInt(0));
                tratamiento.setMedicamento(cursor.getString(1));
                tratamiento.setMetodo(cursor.getString(2));
                tratamiento.setDuracion(cursor.getString(3));
                tratamiento.setRepeticion(cursor.getString(4));
                tratamiento.setHora(cursor.getInt(5));
                tratamiento.setMinuto(cursor.getInt(6));
                list.add(tratamiento);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    public List<Historial> GetHistorial(int Idtratamiento){
        List<Historial> list = new ArrayList<Historial>();
        String query =("SELECT * FROM "+DataBase.TABLE_HISTORIAL+ " where id_tratamiento = "+Idtratamiento);
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                Historial historial = new Historial();
                historial.setId_historial(cursor.getInt(0));
                historial.setId_tratamiento(cursor.getInt(1));
                historial.setHora(cursor.getInt(2));
                historial.setMinuto(cursor.getInt(3));
                historial.setFecha(cursor.getString(4));
                historial.setEstado(cursor.getString(5));
                list.add(historial);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    public List<Historial> GetHistorial(){
        List<Historial> list = new ArrayList<Historial>();
        String query =("SELECT * FROM "+DataBase.TABLE_HISTORIAL);
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                Historial historial = new Historial();
                historial.setId_historial(cursor.getInt(0));
                historial.setId_tratamiento(cursor.getInt(1));
                historial.setHora(cursor.getInt(2));
                historial.setMinuto(cursor.getInt(3));
                historial.setFecha(cursor.getString(4));
                historial.setEstado(cursor.getString(5));
                list.add(historial);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    public String EliminarTratamiento(int Idtratamiento){
        String mensaje;
        try {
            String query = "DELETE From "+DataBase.TABLE_TRATAMIENTO+" WHERE "+DataBase.TRAMIENTO_COLUMN_ID+" = "+Idtratamiento;
            sqLiteDatabase.execSQL(query);
            EliminarHistorial(Idtratamiento);
            mensaje="Tratamiento eliminada corretamente";
            return mensaje;
        }
        catch (Exception e) {
            mensaje=" Existió un problema, intente más tarde";
            return mensaje;
        }
    }

    public String EliminarHistorial(int Idtratamiento){
        String mensaje = "";
        try {
            sqLiteDatabase.execSQL("Delete From "+DataBase.TABLE_HISTORIAL+"  where "+DataBase.HISTORIAL_COLUMN_TRATAMIENTO+" = "+Idtratamiento);
            return mensaje;
        }
        catch (Exception e) {
            return mensaje;
        }
    }

    public void ActualizarHistorial(int Idhistorial, String estado)
    {
        try{
            sqLiteDatabase.execSQL("update "+DataBase.TABLE_HISTORIAL+" set "+DataBase.HISTORIAL_COLUMN_ESTADO+" = '"+estado+"' where "+DataBase.HISTORIAL_COLUMN_ID+" = "+Idhistorial+"");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
