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
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import cc.anima.app.Tirada;
import cc.utilidades.DeviceInfo;
import cc.utilidades.EditTextEval;

public class Lanzador {
		
	private TextView log;
	private Tirada tirada;
	private EditTextEval pifia;
	private EditTextEval abierta;
	
	private CheckBox pifias;
	private CheckBox capicuas;
	private CheckBox abiertas;
	
	private EditText campo;
	private TextView resultado;
	
	private AlertDialog dialog;
	

	
	public Lanzador(EditText campo, Context context) {
		this.campo = campo;
		tirada = new Tirada(context);
				
		View view;
		LayoutInflater layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (DeviceInfo.EINK_SCREEN)
			view = layout.inflate(R.layout.lanzadoreink, null);
		else
			view = layout.inflate(R.layout.lanzador, null);
		
	
		log			= (TextView) view.findViewById(R.id.lanzador_log);
		pifia 		= (EditTextEval) view.findViewById(R.id.lanzador_pifia);
		abierta		= (EditTextEval) view.findViewById(R.id.lanzador_abierta);
		
		pifias 		= (CheckBox) view.findViewById(R.id.lanzador_pifias);
		abiertas	= (CheckBox) view.findViewById(R.id.lanzador_abiertas);
		capicuas	= (CheckBox) view.findViewById(R.id.lanzador_capicuas);
		
		resultado	= (TextView) view.findViewById(R.id.lanzador_resultado);
			
		
		pifias.setChecked(tirada.isPifias());
		abiertas.setChecked(tirada.isAbiertas());
		capicuas.setChecked(tirada.isCapicuas());
		
		pifia.setText(""+tirada.getPifia());
		abierta.setText(""+tirada.getAbierta());
	
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(view);
		builder.setNeutralButton(R.string.usar, usar);
		builder.setPositiveButton(R.string.lanzar, null);
		
		dialog = builder.create();
		
		
	}
	
	public void show() {
		tirada.lanzar();
		log.setText(tirada.getLog());
		resultado.setText(""+tirada.getResultado());
		
		dialog.show();
		dialog.findViewById(android.R.id.button1).setOnClickListener(lanzar);
	}
		 
	
	private DialogInterface.OnClickListener usar = new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int boton) {
			campo.setText(resultado.getText());
		}
	};
	
	
	private View.OnClickListener lanzar = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			tirada.setPifia(pifia.getValue());
			tirada.setAbierta(abierta.getValue());
			tirada.setPifias(pifias.isChecked());
			tirada.setAbiertas(abiertas.isChecked());
			tirada.setCapicuas(capicuas.isChecked());

			tirada.lanzar();
			log.setText(tirada.getLog());
			resultado.setText(""+tirada.getResultado());
			
		}
	};

}


