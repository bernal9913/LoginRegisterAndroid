package com.bernalgas.finalchaval;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class calendarioPerron extends AppCompatActivity {
    DatePickerDialog picker;
    Button btnGet;
    TextView tvw;
    EditText eText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_perron);
        tvw = (TextView) findViewById(R.id.textView1);
        eText = (EditText) findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(calendarioPerron.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String str = dayOfMonth + "/" + (month+1) + "/" + year;
                        eText.setText(str);
                    }
                }, year, month, day);
                picker.show();
                btnGet = findViewById(R.id.button1);
                btnGet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str = "Selected date = " + eText.getText();
                        tvw.setText(str);
                    }
                });
            }
        });
    }
}
    /*private String getTodayDate(){
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        m +=1;
        int d = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(d,m,y);
    }
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = makeDateString(day, month, year);
                datePickerDialog.setText(date);
            }};


     */
