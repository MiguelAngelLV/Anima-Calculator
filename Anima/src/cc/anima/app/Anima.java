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


import android.content.res.Resources;
import cc.anima.ui.R;

public class Anima {


	private int ataque;
	private int defensa;
	
	private int ta;
	private int danio;
	private int nDefensa;
	
	private int modAtaque;
	private int modDefensa;
	
	private int tiradaAtaque;
	private int tiradaDefensa;
	
	private Resources res;
	
	public Anima(Resources res){
		ta = 0;
		danio = 0;
		ataque = 0;
		defensa = 0;
		nDefensa = 1;
		modAtaque = 0;
		modDefensa = 0;
		tiradaAtaque = 0;
		tiradaDefensa = 0;
		
		this.res = res;
	}
	
	
	public void setAtaque(int ataque){
		this.ataque = ataque;
	}
	
	public void setDefensa(int defensa){
		this.defensa = defensa;
	}
	
	public void setTiradaAtaque(int tiradaAtaque) {
		this.tiradaAtaque = tiradaAtaque;
	}
	
	public void setTiradaDefensa(int tiradaDefensa) {
		this.tiradaDefensa = tiradaDefensa;
	}
	

	public int getAtaqueTotal() {
		return Math.max(modAtaque + ataque + tiradaAtaque, 0);
	}
	
	public int getDefensaTotal() {
		
		if (defensa + tiradaAtaque>= 0)
			return Math.max(modDefensa + defensa + tiradaDefensa + getPenalizadorNDefensa(), 0);
		else
			return modDefensa + tiradaDefensa + defensa + getPenalizadorNDefensa();
	}
	
	public String getResultado() {
		int total = getAtaqueTotal() - getDefensaTotal();
		
		if (total == 0)
			return res.getString(R.string.ceronada);
		
		if (total < 0)
			return String.format(res.getString(R.string.contraataque), (-total/10)*5);
		
		int abs = total - (20 + ta*10); 
		int p = (abs/10)*10;
		if (p > 0)
			return String.format(res.getString(R.string.totaldanio), p, '%',(int)Math.ceil((p*danio)/100f));
		else
			return res.getString(R.string.defensiva);
	}
	
	public void setTA(int ta) {
		if (ta > 0)
			this.ta = ta;
		else
			this.ta = 0;
	}
	
	public void setDanio(int danio) {
		if (danio > 0)
			this.danio = danio;
		else
			this.danio = 0;
	}
	
	public int getTA(){
		return ta;
	}
	
	public int getDanio(){
		return danio;
	}


	public int getModAtaque() {
		return modAtaque;
	}


	public int getModDefensa() {
		return modDefensa;
	}


	public void modificarAtaque(int value) {
		modAtaque += value;
	}
	
	public void modificarDefensa(int value) {
		modDefensa += value;
	}


	public void setNDefensa(int nDefensa) {
		this.nDefensa = nDefensa;
	}
	
	
	public int getPenalizadorNDefensa() {
		switch (nDefensa) {
		case 1: return 0;
		case 2: return -30;
		case 3: return -50;
		case 4: return -70;
		default: return -90;
		}
	}
}
