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
import android.os.Bundle;
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
        super.onCreate(savedInstanceState);
        anima = new Anima(getResources());   
        setContentView(R.layout.main);
        
        int ids[] = { R.ataque.ataque, R.ataque.danio, R.ataque.tirada,
        			  R.defensa.defensa, R.defensa.tirada};
        
        for (int id : ids)
        	((EditTextEval) findViewById(id)).setOnChangeEval(evaluar);


        resultado = (TextView) findViewById(R.id.resultado);
        totalAtaque = (TextView) findViewById(R.ataque.total);
        totalDefensa = (TextView) findViewById(R.defensa.total);
       
        
        ((Spinner) findViewById(R.defensa.ta)).setOnItemSelectedListener(ta);
        ((RadioGroup) findViewById(R.defensa.ndefensa)).setOnCheckedChangeListener(defensa);
        ((DialogMultiChoice) findViewById(R.ataque.modificadores)).setOnMultiChoceChange(modificadores);
        ((DialogMultiChoice) findViewById(R.defensa.modificadores)).setOnMultiChoceChange(modificadores);
        
        lanzadorAtaque 	= new  Lanzador((EditText) findViewById(R.ataque.tirada), this);
        lanzadorDefensa = new Lanzador((EditText) findViewById(R.defensa.tirada), this);
        
	}
	
	
	
	public void lanzar(View v) {		
		switch (v.getId()) {
		case R.ataque.lanzar:
			lanzadorAtaque.show();
			break;
		case R.defensa.lanzar:
			lanzadorDefensa.show();
			break;
		}
		
	}
	



	private OnChangeEval evaluar = new OnChangeEval() {
		@Override
		public void onChangeValue(View v, int value) {
			switch (v.getId()) {
			case R.ataque.ataque:
				anima.setAtaque(value);
				break;
			case R.ataque.danio:
				anima.setDanio(value);
				break;
			case R.ataque.tirada:
				anima.setTiradaAtaque(value);
				break;
			case R.defensa.defensa:
				anima.setDefensa(value);
				break;
			case R.defensa.tirada:
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
			case R.ataque.modificadores:
				if (select)
					anima.modificarAtaque(value);
				else 
					anima.modificarAtaque(-value);
				break;
	
			case R.defensa.modificadores:
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
			case R.defensa.defensa1:
				anima.setNDefensa(1);
				break;
			case R.defensa.defensa2:
				anima.setNDefensa(2);
				break;
			case R.defensa.defensa3:
				anima.setNDefensa(3);
				break;
			case R.defensa.defensa4:
				anima.setNDefensa(4);
				break;
			case R.defensa.defensa5:
				anima.setNDefensa(5);
			default:
				break;
			}
			
			resultado.setText(anima.getResultado());
			totalDefensa.setText(""+anima.getDefensaTotal());
		} 
	};
	
	

	

	
	
}