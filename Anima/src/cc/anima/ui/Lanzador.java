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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import cc.anima.app.Tirada;

public class Lanzador {
		
	private TextView log;
	private Tirada tirada;
	private TextView pifia;
	private TextView abierta;
	
	private CheckBox pifias;
	private CheckBox capicuas;
	private CheckBox abiertas;
	
	private EditText campo;
	private TextView resultado;
	
	private AlertDialog dialog;
	

	
	public Lanzador(EditText campo, Context context) {
		this.campo = campo;
		tirada = new Tirada(context.getResources());
				
		LayoutInflater layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = layout.inflate(R.layout.lanzador, null);

	
		log			= (TextView) view.findViewById(R.lanzador.log);
		pifia 		= (TextView) view.findViewById(R.lanzador.pifia);
		abierta		= (TextView) view.findViewById(R.lanzador.abierta);
		
		pifias 		= (CheckBox) view.findViewById(R.lanzador.pifias);
		abiertas	= (CheckBox) view.findViewById(R.lanzador.abiertas);
		capicuas	= (CheckBox) view.findViewById(R.lanzador.capicuas);
		
		resultado	= (TextView) view.findViewById(R.lanzador.resultado);
			
		
		pifia.setText("3");
		abierta.setText("90");
	
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(view);
		builder.setNeutralButton(R.string.usar, null);
		builder.setPositiveButton(R.string.lanzar, null);
		
		dialog = builder.create();
		
		
	}
	
	public void show() {
		tirada.lanzar();
		log.setText(tirada.getLog());
		resultado.setText(""+tirada.getResultado());
		
		dialog.show();
		dialog.findViewById(android.R.id.button2).setOnClickListener(usar);
		dialog.findViewById(android.R.id.button1).setOnClickListener(lanzar);
	}
		 
	
	private View.OnClickListener usar = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			campo.setText(resultado.getText());
			dialog.dismiss();
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


