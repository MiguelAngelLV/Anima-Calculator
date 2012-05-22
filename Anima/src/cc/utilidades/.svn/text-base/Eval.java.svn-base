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
package cc.utilidades;

public class Eval {
	
	public static int string(String string) {
		String numero = "";
		int total = 0;
		int signo = 1;
		
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == '+') {
				total += signo * getNumero(numero);
				signo = 1;
				numero = "";
			}
			else if (string.charAt(i) == '-') {
				total += signo * getNumero(numero);
				signo = -1;
				numero = "";
			} else
				numero += string.charAt(i);
		}
		
		return total+ signo * getNumero(numero);
			
	}
	
	private static int getNumero(String numero) {
		try {
			return Integer.parseInt(numero);
		} catch (Exception e) {
			return 0;
		}
	}

}
