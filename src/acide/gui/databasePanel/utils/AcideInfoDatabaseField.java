/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2013  
 * Authors:
 * 		- Fernando S�enz P�rez (Team Director).
 *      - Version from 0.1 to 0.6:
 *      	- Diego Cardiel Freire.
 *			- Juan Jos� Ortiz S�nchez.
 *          - Delf�n Rup�rez Ca�as.
 *      - Version 0.7:
 *          - Miguel Mart�n L�zaro.
 *      - Version 0.8:
 *      	- Javier Salcedo G�mez.
 *      - Version from 0.9 to 0.11:
 *      	- Pablo Guti�rrez Garc�a-Pardo.
 *      	- Elena Tejeiro P�rez de �greda.
 *      	- Andr�s Vicente del Cura.
 *      - Version from 0.12 to 0.16
 *      	- Sem�ramis Guti�rrez Quintana
 *      	- Juan Jes�s Marqu�s Ortiz
 *      	- Fernando Ord�s Lorente
 *      - Version 0.17
 *      	- Sergio Dom�nguez Fuentes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package acide.gui.databasePanel.utils;

/**
 * ACIDE - A Configurable IDE info data base field.
 * 
 * @version 0.11
 */
public class AcideInfoDatabaseField {

	/**
	 * ACIDE -  Data types
	 */
	public static enum types{INT,STRING};
	
	/**
	 * ACIDE - Field type  
	 */
	private types _type;
	
	/**
	 * ACIDE - Indicates if is primary key  
	 */
	private boolean _pk;
	
	/**
	 * ACIDE - Indicates if is no nullable  
	 */
	private boolean _nn;
	
	
	public types get_type() {
		return _type;
	}

	public void set_type(types _type) {
		this._type = _type;
	}

	public boolean is_pk() {
		return _pk;
	}

	public void set_pk(boolean _pk) {
		this._pk = _pk;
	}

	public boolean is_nn() {
		return _nn;
	}

	public void set_nn(boolean _nn) {
		this._nn = _nn;
	}
}
