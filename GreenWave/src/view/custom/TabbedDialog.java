package view.custom;

import android.app.Activity;
import android.app.Dialog;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.wavon.greenwave.R;

import control.Globale;
import datas.utility.UpdateTimes;

/**
 * Dialog with bus times
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.1
 */
public class TabbedDialog extends Dialog{
	
	private TableLayout table;
	private RelativeLayout r;
	
	private Activity a;
	
	private RadioButton sens1;
	private RadioButton sens2;
	private RadioGroup group;
	
	public TabbedDialog(Activity a, String title) {
		super(a);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_info);
		this.a=a;
		setTitle(title);
		
       initInterface();
       attachReactions();		    
	}
	
	private void updateTimes(String text){
		text.replace("Je vais à ", "");
 		table.removeAllViews();
		new UpdateTimes(table, group, r, this).execute(text);
	}
	
	private void initInterface(){
 	
			//Cursor c = Globale.engine.getCurrentArretSelected().getHoraires(Globale.engine.getCurrentLigneSelected());
			table = (TableLayout) findViewById(R.id.table);
			
			r = (RelativeLayout) findViewById(R.id.loadingPanel);
			
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) r.getLayoutParams();
			params.addRule(RelativeLayout.BELOW, R.id.group);

			CharSequence s1 = Globale.engine.getLigneCourante().getDirection1();
			CharSequence s2 = Globale.engine.getLigneCourante().getDirection2();
			
			sens1 = (RadioButton) findViewById(R.id.sens1);
			sens1.setText("Je vais à "+s1);
			sens2 = (RadioButton) findViewById(R.id.sens2);
			sens2.setText("Je vais à "+s2);
			group = (RadioGroup) findViewById(R.id.group);
			
	}
	
	private void attachReactions(){
		group.setOnCheckedChangeListener(new  RadioGroup.OnCheckedChangeListener() { 
            public  void  onCheckedChanged(RadioGroup group, 
                    int  checkedId) { 
            	int id = group.getCheckedRadioButtonId();
            	if(id == R.id.sens1){
            		// 1er cas donc premier sens
            		updateTimes(Globale.engine.getLigneCourante().getDirection1()); 
            	}
            	else if (id == R.id.sens2){
            		// 2eme cas c'est donc le deuxième sens
            		updateTimes(Globale.engine.getLigneCourante().getDirection2()); 
            	}
            } 
        }); 
	}
}
