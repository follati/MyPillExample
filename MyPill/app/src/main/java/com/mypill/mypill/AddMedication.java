package com.mypill.mypill;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mypill.mypill.Clases.DataSource;
import com.mypill.mypill.Clases.Historial;
import com.mypill.mypill.Clases.TimePickerFragment;
import com.mypill.mypill.Clases.Tratamiento;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddMedication extends Fragment {
    Spinner spinnerDias, spinneEstado;
    Button btnHoraToma, btnGuardar;
    EditText etMedicamento, etMetodo;
    public static int hour;
    public static int minute;
    public static DataSource dataSource;

    public AddMedication() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_medication, container, false);

        spinnerDias = (Spinner) view.findViewById(R.id.spinerDuracion);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.dias_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDias.setAdapter(adapter);
        spinnerDias.setSelection(1);

        spinneEstado = (Spinner) view.findViewById(R.id.spinerEstado);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.estado_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneEstado.setAdapter(adapter2);
        spinneEstado.setSelection(3);

        etMedicamento = (EditText) view.findViewById(R.id.edMedicina);
        etMetodo = (EditText) view.findViewById(R.id.edMetodo);

        btnHoraToma = (Button) view.findViewById(R.id.btnHoraToma);
        btnHoraToma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment  = new TimePickerFragment();
                newFragment.show(getActivity().getFragmentManager(), "timePicker");

            }
        });
        btnGuardar = (Button) view.findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dias = 0;
                int repeticiones = 0;

                try
                {
                String diasString = spinnerDias.getSelectedItem().toString();
                String repeticionesString = spinneEstado.getSelectedItem().toString();
                switch (repeticionesString)
                {
                    case "Cada hora":
                        repeticiones = 1;
                        break;

                    case "Cada día":
                        repeticiones = 24;
                        break;

                    default:
                        repeticionesString = repeticionesString.replace("Cada ","");
                        repeticionesString = repeticionesString.replace(" horas","");
                        repeticiones = 24 / Integer.parseInt(repeticionesString);
                        break;
                }

                switch (diasString)
                {
                    case "Todos los días":
                        dias = 100;
                        break;

                    default:
                        diasString = diasString.replace(" día","");
                        diasString = diasString.replace("s","");
                        dias = Integer.parseInt(diasString);
                        break;
                }
                } catch (Exception ex)
                {
                    dias = 0;
                    repeticiones = 0;
                }


                try {
                    dataSource = new DataSource(getActivity());
                    dataSource.open();
                    long result=0;
                    //Insert Tratamiento
                    Tratamiento tratamiento = new Tratamiento();
                    tratamiento.setId_tratamiento(dataSource.NewIdTratamiento());
                    tratamiento.setMedicamento(etMedicamento.getText().toString());
                    tratamiento.setMetodo(etMetodo.getText().toString());
                    tratamiento.setDuracion(spinnerDias.getSelectedItem().toString());
                    tratamiento.setRepeticion(spinneEstado.getSelectedItem().toString());
                    tratamiento.setHora(hour);
                    tratamiento.setMinuto(minute);
                    result= dataSource.Insert_Tratamiento(tratamiento);

                    Calendar cal = Calendar.getInstance();
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int month = cal.get(Calendar.MONTH);
                    int year = cal.get(Calendar.YEAR);

                    //Insert Historial
                    for (int i =0; i < dias; i++)
                    {
                        for (int j=0; j< repeticiones; j++)
                        {
                            Historial historial = new Historial();
                            historial.setId_tratamiento(tratamiento.getId_tratamiento());
                            historial.setId_tratamiento(tratamiento.getId_tratamiento());
                            historial.setMinuto(minute);

                            if (i > 0 || j > 0)
                            {
                                if ((hour + 24/repeticiones) >= 24)
                                    day++;
                                if (day >= 30) {day = 1; month++;}

                                hour = SetHora(hour, 24/repeticiones);
                            }

                            historial.setFecha(day+"/"+month+"/"+year);
                            historial.setHora(hour);
                            historial.setEstado("Pendiente");

                            result= dataSource.Insert_Historial(historial);
                        }
                    }
                    dataSource.close();
                }catch (Exception ex){
                    Toast.makeText(getActivity().getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                    dataSource.close();
                }
            }
        });

        return view;
    }

    public int SetHora(int horaInicio, int adicionales)
    {
        if ((horaInicio + adicionales) >=  24)
        {
            return (horaInicio + adicionales) - 24;
        }
        else
            return (horaInicio + adicionales);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Añadir Medicación");
    }
}
