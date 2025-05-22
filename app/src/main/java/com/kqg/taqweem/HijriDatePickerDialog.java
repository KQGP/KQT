package com.kqg.taqweem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

public class HijriDatePickerDialog extends AlertDialog {

    private final NumberPicker monthPicker;
    private final NumberPicker yearPicker;
    private final NumberPicker dayPicker;
    private final OnDateSetListener listener;

    public interface OnDateSetListener {
        void onDateSet(int hijriYear, int hijriMonth, int hijriDay);
    }

    public HijriDatePickerDialog(Context context, OnDateSetListener listener,
                                 int hijriYear, int hijriMonth, int hijriDay) {
        super(context);
        this.listener = listener;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.hijri_date_picker, null);
        setView(view);

        monthPicker = view.findViewById(R.id.picker_month);
        yearPicker = view.findViewById(R.id.picker_year);
        dayPicker = view.findViewById(R.id.picker_day);

        // Set up month picker
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setDisplayedValues(HijriDateConverter.hijriMonths);
        monthPicker.setValue(hijriMonth);

        // Set up year picker
        yearPicker.setMinValue(1300); // Minimum Hijri year
        yearPicker.setMaxValue(1500); // Maximum Hijri year
        yearPicker.setValue(hijriYear);

        // Set up day picker
        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(30); // Max days in Hijri month
        dayPicker.setValue(hijriDay);

        setButton(BUTTON_POSITIVE, context.getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onDateSet(
                                    yearPicker.getValue(),
                                    monthPicker.getValue(),
                                    dayPicker.getValue());
                        }
                    }
                });
        setButton(BUTTON_NEGATIVE, context.getString(android.R.string.cancel),
                (DialogInterface.OnClickListener) null);
    }
}