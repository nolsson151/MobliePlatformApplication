package com.gcu.mpd.S1434184;

//
// Name                 Niklas Olsson
// Student ID           S1434184
// Programme of Study   Computing
//

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;


/**
 *  * Name                 Niklas Olsson
 *  * Student ID           S1434184
 *  * Programme of Study   Computing
 *  *
 *  * @author Niklas
 *  * @version 1.0
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    /**
     * Method used to override onCreateDialog create a DatePickerDialog fragment. A calendar object
     * is created and a listener year for the year, month, and day which will await input from user
     * selecting a date.
     *
     * @param savedInstanceState Loads activity state
     * @return                   DatePickerDialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * Method that will retrieve the data from the DatePickerDialog and pass the values to
     * processDatePickerResult in MainActivity that will process the values into a date to search
     * for.
     *
     * @param datePicker DatePicker object
     * @param year       Year selected
     * @param month      Month selected
     * @param day        Day selected
     */
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        MainActivity activity = (MainActivity) getActivity();
        activity.processDatePickerResult(year, month, day);
    }
}
