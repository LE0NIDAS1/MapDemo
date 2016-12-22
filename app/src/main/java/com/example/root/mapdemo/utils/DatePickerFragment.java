package com.example.root.mapdemo.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.root.mapdemo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

/**
 * Created by leoeg on 17/11/2016.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);



        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user

        String[] days = new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
        "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
//        String dayOfWeek = days[c.get(Calendar.DAY_OF_WEEK)];

        TextView tv1= (TextView) getActivity().findViewById(R.id.textview1);
        TextView tv2= (TextView) getActivity().findViewById(R.id.year1);
        TextView tv3= (TextView) getActivity().findViewById(R.id.day1);
        TextView tv4= (TextView) getActivity().findViewById(R.id.dayOfWeek1);
        TextView tv5= (TextView) getActivity().findViewById(R.id.month1);


        tv2.setText(String.valueOf(view.getYear()));
        tv3.setText(String.valueOf(view.getDayOfMonth()));
        tv5.setText(days[view.getMonth()]);

        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
        Date date = new Date(view.getYear(), view.getMonth(), view.getDayOfMonth()-1);
        String dayOfWeek = simpledateformat.format(date);
        tv4.setText(String.valueOf(dayOfWeek));

        Formatter fmt = new Formatter();
        fmt.format("%02d",view.getDayOfMonth());
        Formatter fm2 = new Formatter();
        fm2.format("%02d",view.getMonth()+1);
        tv1.setText(fmt+"/"+(fm2)+"/"+view.getYear());
    }
}
