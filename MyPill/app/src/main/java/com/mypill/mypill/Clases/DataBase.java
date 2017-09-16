package com.mypill.mypill.Clases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Programador on 03/09/2017.
 */

public class DataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_Appmypill.db";
    private static final int DATABASE_VERSION = 1;

    //Tablas
    public static final String TABLE_TRATAMIENTO="tb_tratamiento";
    public static final String TABLE_HISTORIAL="tb_cumplimiento";

    //TABLE_TRATAMIENTO
    public static final String TRAMIENTO_COLUMN_ID="id_tratamiento";
    public static final String TRAMIENTO_COLUMN_MEDICAMENTO="medicamento";
    public static final String TRAMIENTO_COLUMN_HORA="hora";
    public static final String TRAMIENTO_COLUMN_MINUTO="minuto";
    public static final String TRAMIENTO_COLUMN_METODO_="metodo";
    public static final String TRAMIENTO_COLUMN_DURACION="duracion";
    public static final String TRAMIENTO_COLUMN_REPETICION="repeticion";

    //TABLE_HISTORIAL
    public static final String HISTORIAL_COLUMN_ID="id_historial";
    public static final String HISTORIAL_COLUMN_TRATAMIENTO="id_tratamiento";
    public static final String HISTORIAL_COLUMN_HORA="hora";
    public static final String HISTORIAL_COLUMN_MINUTO="minuto";
    public static final String HISTORIAL_COLUMN_ESTADO="estado";
    public static final String HISTORIAL_COLUMN_FECHA="fecha";

    //SCRIPT CREACION TABLAS
    private static final String SCRIPT_TABLE_TRATAMIENTO ="CREATE TABLE " +TABLE_TRATAMIENTO+" ( " +
            TRAMIENTO_COLUMN_ID+" INTEGER PRIMARY KEY , " +
            TRAMIENTO_COLUMN_MEDICAMENTO+" VARCHAR(100) NULL, " +
            TRAMIENTO_COLUMN_METODO_+" VARCHAR(100) NULL, " +
            TRAMIENTO_COLUMN_DURACION+" VARCHAR(50) NULL, " +
            TRAMIENTO_COLUMN_REPETICION+" VARCHAR(50) NULL, " +
            TRAMIENTO_COLUMN_HORA+" INTEGER NULL, " +
            TRAMIENTO_COLUMN_MINUTO+" INTEGER  NULL );";

    private static final String SCRIPT_TABLE_HISTORIAL ="CREATE TABLE " +TABLE_HISTORIAL+" ( " +
            HISTORIAL_COLUMN_ID+" INTEGER PRIMARY KEY autoincrement, " +
            HISTORIAL_COLUMN_TRATAMIENTO+" INTEGER NULL, " +
            HISTORIAL_COLUMN_HORA+" INTEGER NULL, " +
            HISTORIAL_COLUMN_MINUTO+" INTEGER NULL, " +
            HISTORIAL_COLUMN_FECHA+" VARCHAR(50) NULL, " +
            HISTORIAL_COLUMN_ESTADO+" VARCHAR(20)  NULL );";



    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SCRIPT_TABLE_TRATAMIENTO);
        database.execSQL(SCRIPT_TABLE_HISTORIAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+SCRIPT_TABLE_HISTORIAL);
        db.execSQL("DROP TABLE IF EXISTS "+SCRIPT_TABLE_TRATAMIENTO);
        onCreate(db);
    }
}
