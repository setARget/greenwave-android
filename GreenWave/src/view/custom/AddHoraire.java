package view.custom;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.Time;

public class AddHoraire extends TimePickerDialog{

	public AddHoraire(Context context, int theme, OnTimeSetListener callBack,
			int hourOfDay, int minute, boolean is24HourView) {
		super(context, theme, callBack, hourOfDay, minute, is24HourView);
		// TODO Auto-generated constructor stub
		Time t = new Time();
		t.setToNow();
		this.updateTime(t.hour, t.minute);
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// OnClick posait problème car il appelait le listener en double
		// On le redefinit pour ne plus avoir cet appel
	}

}
