/*******************************************************************************
 * Copyright 2011 Miguel Ángel López Vicente 
 *  
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *  
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *  
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package cc.anima.ui;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import cc.anima.app.Anima;
import cc.utilidades.DialogMultiChoice;
import cc.utilidades.EditTextEval;
import cc.utilidades.EditTextEval.OnChangeEval;
import cc.utilidades.OnMultiChoiceChange;

public class Principal extends Activity {
	
	
	private Anima anima;
	private TextView resultado;
	private TextView totalAtaque;
	private TextView totalDefensa;
	
	private Lanzador lanzadorAtaque;
	private Lanzador lanzadorDefensa;
	

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.main);
        inicializar();
	}
	
	public void onStart() {
		super.onStart();
		
		int orientacion = getSharedPreferences("Anima", MODE_PRIVATE).getInt("orientacion", ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);		
		setRequestedOrientation(orientacion);

	}
	
	
	
	private void inicializar() {
		anima = new Anima(getResources());   

        int ids[] = { R.id.ataque, R.id.danio, R.id.ataque_tirada,
        			  R.id.defensa, R.id.defensa_tirada};
        
        for (int id : ids)
        	((EditTextEval) findViewById(id)).setOnChangeEval(evaluar);


        resultado = (TextView) findViewById(R.id.resultado);
        totalAtaque = (TextView) findViewById(R.id.ataque_total);
        totalDefensa = (TextView) findViewById(R.id.defensa_total);
       
        
        ((Spinner) findViewById(R.id.ta)).setOnItemSelectedListener(ta);
        ((RadioGroup) findViewById(R.id.ndefensa)).setOnCheckedChangeListener(defensa);
        
        DialogMultiChoice da = (DialogMultiChoice) findViewById(R.id.ataque_modificadores_button);
        if (da == null)
        	da = (DialogMultiChoice) findViewById(R.id.ataque_modificadores_list);
              
        da.setOnMultiChoceChange(modificadores);
        
        DialogMultiChoice dd = (DialogMultiChoice) findViewById(R.id.defensa_modificadores_button);
        if (dd == null)
        	dd = (DialogMultiChoice) findViewById(R.id.defensa_modificadores_list);
        
        dd.setOnMultiChoceChange(modificadores);
        
        lanzadorAtaque 	= new  Lanzador((EditText) findViewById(R.id.ataque_tirada), this);
        lanzadorDefensa = new Lanzador((EditText) findViewById(R.id.defensa_tirada), this);
	}
	
	
	public void lanzar(View v) {		
		switch (v.getId()) {
		case R.id.ataque_lanzar:
			lanzadorAtaque.show();
			break;
		case R.id.defensa_lanzar:
			lanzadorDefensa.show();
			break;
		}
		
	}
	

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(null);
		setContentView(R.layout.main);
		inicializar();
		
	}


	private OnChangeEval evaluar = new OnChangeEval() {
		@Override
		public void onChangeValue(View v, int value) {
			switch (v.getId()) {
			case R.id.ataque:
				anima.setAtaque(value);
				break;
			case R.id.danio:
				anima.setDanio(value);
				break;
			case R.id.ataque_tirada:
				anima.setTiradaAtaque(value);
				break;
			case R.id.defensa:
				anima.setDefensa(value);
				break;
			case R.id.defensa_tirada:
				anima.setTiradaDefensa(value);
				break;
			
			}
			
			resultado.setText(anima.getResultado());
			totalAtaque.setText(""+anima.getAtaqueTotal());
			totalDefensa.setText(""+anima.getDefensaTotal());
		}
	};


	private OnMultiChoiceChange modificadores = new OnMultiChoiceChange() {
		@Override
		public void onMultiChoiceChange(View v, int position, boolean select, int value) {
			switch (v.getId()) {
			case R.id.ataque_modificadores_button:
			case R.id.ataque_modificadores_list:
				if (select)
					anima.modificarAtaque(value);
				else 
					anima.modificarAtaque(-value);
				break;
	
			case R.id.defensa_modificadores_button:
			case R.id.defensa_modificadores_list:
				if (select)
					anima.modificarDefensa(value);
				else
					anima.modificarDefensa(-value);
			default:
				break;
				
			}
			
			resultado.setText(anima.getResultado());
			totalAtaque.setText(""+anima.getAtaqueTotal());
			totalDefensa.setText(""+anima.getDefensaTotal());
			
		}
	
		
	};

	
	

	
	private OnItemSelectedListener ta = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> list, View v, int pos, long id) {
			anima.setTA(pos);
			resultado.setText(anima.getResultado());

		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {}
	};
	


	private OnCheckedChangeListener defensa = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int id) {
			switch (id) {
			case R.id.defensa1:
				anima.setNDefensa(1);
				break;
			case R.id.defensa2:
				anima.setNDefensa(2);
				break;
			case R.id.defensa3:
				anima.setNDefensa(3);
				break;
			case R.id.defensa4:
				anima.setNDefensa(4);
				break;
			case R.id.defensa5:
				anima.setNDefensa(5);
			default:
				break;
			}
			
			resultado.setText(anima.getResultado());
			totalDefensa.setText(""+anima.getDefensaTotal());
		} 
	};
	
	

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);	
	    return true;
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item) {
		Editor editor = getSharedPreferences("Anima", MODE_PRIVATE).edit();
		
		if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			editor.putInt("orientacion", ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			editor.putInt("orientacion", ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		
		editor.commit();
		
		return true;
	
	}

	
	
}