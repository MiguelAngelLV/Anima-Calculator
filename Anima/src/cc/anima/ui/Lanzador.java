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

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import cc.anima.app.Tirada;

public class Lanzador extends AlertDialog{
		
	private TextView log;
	private Tirada tirada;
	private TextView pifia;
	private TextView abierta;
	
	private CheckBox pifias;
	private CheckBox capicuas;
	private CheckBox abiertas;
	
	private EditText campo;
	private TextView resultado;
	

	
	public Lanzador(EditText campo, Context context) {
		super(context);
		this.campo = campo;
		tirada = new Tirada(context.getResources());

		
	}
	
	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.lanzador);
		
		log			= (TextView) findViewById(R.lanzador.log);
		pifia 		= (TextView) findViewById(R.lanzador.pifia);
		abierta		= (TextView) findViewById(R.lanzador.abierta);
		
		pifias 		= (CheckBox) findViewById(R.lanzador.pifias);
		abiertas	= (CheckBox) findViewById(R.lanzador.abiertas);
		capicuas	= (CheckBox) findViewById(R.lanzador.capicuas);
		
		resultado	= (TextView) findViewById(R.lanzador.resultado);
		
		findViewById(R.lanzador.usar).setOnClickListener(usar);
		findViewById(R.lanzador.lanzar).setOnClickListener(lanzar);
		
		
		pifia.setText("3");
		abierta.setText("90");
		
	}
	
	public void show() {
		super.show();
		tirada.lanzar();
		log.setText(tirada.getLog());
		resultado.setText(""+tirada.getResultado());
		getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

	}
		 
	
	private View.OnClickListener usar = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			campo.setText(resultado.getText());
			dismiss();
		}
	};
	
	
	private View.OnClickListener lanzar = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			if (abiertas.isChecked())
				tirada.setAbierta(Integer.parseInt(abierta.getText().toString()));
			else
				tirada.setAbierta(101);

			
			if (pifias.isChecked())
				tirada.setPifia(Integer.parseInt(pifia.getText().toString()));
			else
				tirada.setPifia(-1);
			
			tirada.setCapicua(capicuas.isChecked());
			
			tirada.lanzar();
			log.setText(tirada.getLog());
			resultado.setText(""+tirada.getResultado());
			
		}
	};

}


