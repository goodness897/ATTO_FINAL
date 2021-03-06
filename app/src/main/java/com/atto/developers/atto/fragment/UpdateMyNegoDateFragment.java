package com.atto.developers.atto.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atto.developers.atto.R;
import com.atto.developers.atto.UpdateNegoActivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateMyNegoDateFragment extends DialogFragment implements OnDateSelectedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();


    public UpdateMyNegoDateFragment() {
        // Required empty public constructor

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nego_date, container, false);
        MaterialCalendarView widget = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);
        return view;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        String selectedDate = FORMATTER.format(date.getDate());
        UpdateNegoActivity callActivity = (UpdateNegoActivity)getActivity();
        callActivity.onDateSelectValue(selectedDate);
        dismiss();

    }

}
