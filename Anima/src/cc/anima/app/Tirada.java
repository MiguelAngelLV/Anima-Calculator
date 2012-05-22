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

import android.content.res.Resources;
import cc.anima.ui.R;

public class Tirada {
	
	public int getPifia() {
		return pifia;
	}


	public int getAbierta() {
		return abierta;
	}


	public boolean isCapicua() {
		return capicua;
	}


	private int pifia;
	private int abierta;
	private int resultado;
	private boolean capicua;
		
	private String log;
	private Resources res;
	
	
	public Tirada(Resources res) {
		pifia = 3;
		abierta = 90;
		capicua = true;
		log = new String();	
		
		this.res = res;

	}


	public void lanzar() {
		Random r = new Random();
		int valor = r.nextInt(100)+1;
		if (valor <= pifia) {
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
			
			if (capicua && isCapicua(valor)) {
				log += res.getString(R.string.tirada_capicua) + "\n";
				int c = r.nextInt(10)+1;
				if (c == valor%10) {
					log += String.format(res.getString(R.string.tirada_capicua_si), valor%10, c) + "\n";
					valor = 100;
				} else {
					log += String.format(res.getString(R.string.tirada_capicua_no), valor%10, c) + "\n";
				}
			}
			
			if (valor >= abierta) {
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
	
	
	private boolean isCapicua(int n) {
		if (n < 10) return false;
		
		return n/10 == n%10;
	}
			
	
	public String getLog() {
		return log;
	}

	public void setPifia(int pifia) {
		this.pifia = pifia;
	}


	public void setAbierta(int abierta) {
		this.abierta = abierta;
	}


	public void setCapicua(boolean capicua) {
		this.capicua = capicua;
	}

	
	public int getResultado() {
		return resultado;
	}




}
