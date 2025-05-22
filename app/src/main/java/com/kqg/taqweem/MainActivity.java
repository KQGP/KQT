package com.kqg.taqweem;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerFrom;
    private TextView resultDate, todayHijri, todayGregorian;
    private Button selectDateBtn;
    private HijriDateConverter hijriConverter;
    private Calendar selectedDate = Calendar.getInstance();
    private boolean isHijriMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        spinnerFrom = findViewById(R.id.spinnerFrom);
        resultDate = findViewById(R.id.resultDate);
        todayHijri = findViewById(R.id.todayHijri);
        todayGregorian = findViewById(R.id.todayGregorian);
        selectDateBtn = findViewById(R.id.selectDateBtn);

        // Set Arabic locale
        Locale arabicLocale = new Locale("ar");
        Locale.setDefault(arabicLocale);

        // Initialize date converter
        hijriConverter = new HijriDateConverter();

        // Setup spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.date_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);

        // Set today's dates
        setTodayDates();

        // Date picker button click listener
        selectDateBtn.setText(R.string.select_date);
        selectDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Spinner selection listener
        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isHijriMode = position == 1;
                convertDate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void showDatePicker() {
        if (isHijriMode) {
            showHijriDatePicker();
        } else {
            showGregorianDatePicker();
        }
    }

    private void showGregorianDatePicker() {
        new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        selectedDate.set(year, month, day);
                        convertDate();
                    }
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void showHijriDatePicker() {
        String[] hijriParts = hijriConverter.gregorianToHijri(
                selectedDate.get(Calendar.DAY_OF_MONTH),
                selectedDate.get(Calendar.MONTH) + 1,
                selectedDate.get(Calendar.YEAR)).split(" ");

        int hijriDay = Integer.parseInt(hijriParts[0]);
        int hijriMonth = getHijriMonthIndex(hijriParts[1]) + 1;
        int hijriYear = Integer.parseInt(hijriParts[2].replace("AH", "").trim());

        new HijriDatePickerDialog(
                this,
                new HijriDatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int hijriYear, int hijriMonth, int hijriDay) {
                        String gregorianDate = hijriConverter.hijriToGregorian(
                                hijriDay, hijriMonth, hijriYear);
                        String[] parts = gregorianDate.split("/");
                        selectedDate.set(
                                Integer.parseInt(parts[2]),
                                Integer.parseInt(parts[1]) - 1,
                                Integer.parseInt(parts[0]));
                        convertDate();
                    }
                },
                hijriYear,
                hijriMonth - 1,
                hijriDay
        ).show();
    }

    private int getHijriMonthIndex(String monthName) {
        for (int i = 0; i < HijriDateConverter.hijriMonths.length; i++) {
            if (HijriDateConverter.hijriMonths[i].equals(monthName)) {
                return i;
            }
        }
        return 0;
    }

    private void setTodayDates() {
        // Today's Gregorian date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String gregorianDate = sdf.format(Calendar.getInstance().getTime());
        todayGregorian.setText(getString(R.string.today_gregorian) + " " + gregorianDate);

        // Convert to Hijri
        String[] todayGregParts = gregorianDate.split("/");
        String hijriDate = hijriConverter.gregorianToHijri(
                Integer.parseInt(todayGregParts[0]),
                Integer.parseInt(todayGregParts[1]),
                Integer.parseInt(todayGregParts[2]));
        todayHijri.setText(getString(R.string.today_hijri) + " " + hijriDate);
    }

    private void convertDate() {
        if (isHijriMode) {
            String[] hijriParts = hijriConverter.gregorianToHijri(
                    selectedDate.get(Calendar.DAY_OF_MONTH),
                    selectedDate.get(Calendar.MONTH) + 1,
                    selectedDate.get(Calendar.YEAR)).split(" ");

            int hijriDay = Integer.parseInt(hijriParts[0]);
            int hijriMonth = getHijriMonthIndex(hijriParts[1]) + 1;
            int hijriYear = Integer.parseInt(hijriParts[2].replace("AH", "").trim());

            String gregorianDate = hijriConverter.hijriToGregorian(hijriDay, hijriMonth, hijriYear);
            resultDate.setText(getString(R.string.result) + " " + gregorianDate);
        } else {
            String hijriDate = hijriConverter.gregorianToHijri(
                    selectedDate.get(Calendar.DAY_OF_MONTH),
                    selectedDate.get(Calendar.MONTH) + 1,
                    selectedDate.get(Calendar.YEAR));
            resultDate.setText(getString(R.string.result) + " " + hijriDate);
        }
    }
}