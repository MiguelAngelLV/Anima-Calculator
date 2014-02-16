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
package cc.anima.app;

import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import cc.anima.ui.R;

public class Tirada {
	
	public int getPifia() {
		return pifia;
	}


	public int getAbierta() {
		return abierta;
	}


	public boolean isCapicuas() {
		return capicuas;
	}


	private int pifia;
	private boolean pifias;
	private boolean abiertas;
	private int abierta;
	private int resultado;
	private boolean capicuas;
		
	private String log;
	private Context context;
	
	
	public Tirada(Context context) {
		SharedPreferences shared = context.getSharedPreferences("Anima", Context.MODE_PRIVATE);
	
		log = new String();	
		this.context = context;
		pifia = shared.getInt("pifia", 3);
		abierta = shared.getInt("abierta", 90);
		pifias = shared.getBoolean("pifias", true);
		abiertas = shared.getBoolean("abiertas", true);
		capicuas = shared.getBoolean("capicuas", true);
		
	
	}


	public void lanzar() {
		
		Resources res = context.getResources();
		
		Random r = new Random();
		int valor = r.nextInt(100)+1;
		
		if (valor <= pifia && pifias) {
			log = String.format(res.getString(R.string.tirada), valor) + "\n";
			log += res.getString(R.string.tirada_pifia) + "\n";
			int pifia = r.nextInt(100)+1;
			
			if (valor == 1) log += String.format(res.getString(R.string.pifia_1), pifia+=15) + "\n";
			if (valor == 2) log += String.format(res.getString(R.string.pifia_2), pifia) + "\n";
			if (valor > 2)  log += String.format(res.getString(R.string.pifia_3), pifia-=15) + "\n";
			resultado = -pifia;
			return;
		}
		
		log = "";
		int total = 0;
		boolean isAbierta;
		do {
			
			isAbierta = false;
			log += String.format(res.getString(R.string.tirada_resultado), valor) + "\n";
			
			if (valor/10 == valor%10 && capicuas) {
				int c = r.nextInt(10)+1;
				log += res.getString(R.string.tirada_capicua) + "\n";		
				if (c == valor%10) {
					log += String.format(res.getString(R.string.tirada_capicua_si), valor%10, c) + "\n";
					valor = 100;
				} else {
					log += String.format(res.getString(R.string.tirada_capicua_no), valor%10, c) + "\n";
				}
			}
			
			if (valor >= abierta && abiertas) {
				isAbierta = true;
				log += res.getString(R.string.tirada_abierta) + "\n";
				if (abierta < 100) abierta++;
			} 
			
			total += valor;
			valor = r.nextInt(100)+1;
			
		} while (isAbierta);
		
		log += String.format(res.getString(R.string.resultado), total);
		
		resultado = total;
		
	}
	
			
	
	public String getLog() {
		return log;
	}

	public void setPifia(int pifia) {
		this.pifia = pifia;
		save();
	}
	
	public boolean isPifias() {
		return pifias;
	}
	
	public boolean isAbiertas() {
		return abiertas;
	}
	
	public void setAbiertas(boolean abiertas) {
		this.abiertas = abiertas;
		save();
	}
	
	public void setPifias(boolean pifias) {
		this.pifias = pifias;
		save();
	}


	public void setAbierta(int abierta) {
		this.abierta = abierta;
		save();

	}


	public void setCapicuas(boolean capicuas) {
		this.capicuas = capicuas;
		save();
	}

	
	public int getResultado() {
		return resultado;
	}
	
	
	private void save() {
		SharedPreferences.Editor editor = context.getSharedPreferences("Anima", Context.MODE_PRIVATE).edit();
		editor.putInt("pifia", pifia);
		editor.putInt("abierta", abierta);
		editor.putBoolean("pifias", pifias);
		editor.putBoolean("capicuas", capicuas);
		editor.putBoolean("abiertas", abiertas);
		editor.commit();
		
	}








}
