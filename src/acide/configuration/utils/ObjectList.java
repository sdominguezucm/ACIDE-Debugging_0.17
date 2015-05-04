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
 *      - Version from 0.12 to 0.15
 *      	- Sem�ramis Guti�rrez Quintana
 *      	- Juan Jes�s Marqu�s Ortiz
 *      	- Fernando Ord�s Lorente
 *      - Version from 0.16 to 0.17
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
package acide.configuration.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import acide.configuration.menu.AcideMenuItemConfiguration;
import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.log.AcideLog;

/**
 * ACIDE - A Configurable IDE serializable object list of the lexicon of the
 * application.
 * 
 * @project ACIDE - A Configurable IDE (c).
 * @version 0.11.
 */
public class ObjectList implements Serializable {

	/**
	 * ACIDE - A Configurable IDE serializable object list serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE object array list.
	 */
	private ArrayList<Object> _list;

	/**
	 * Creates a new ACIDE - A Configurable IDE serializable object list.
	 */
	public ObjectList() {

		super();

		// Creates the list of objects
		_list = new ArrayList<Object>();
	}

	/**
	 * Creates a new ACIDE - A Configurable IDE serializable object list.
	 * @param 
	 * 		the value for _list
	 */
	public ObjectList(ObjectList copy) {
		super();
		_list = new ArrayList<Object>();
		
		Iterator<Object> it = copy.getList().iterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			if (ob.isSubmenu()){
				_list.add(new AcideMenuSubmenuConfiguration((AcideMenuSubmenuConfiguration) ob));
			}else {
				_list.add(new AcideMenuItemConfiguration((AcideMenuItemConfiguration) ob));
			}
		}
	}

	/**
	 * Returns the is empty flag.
	 * 
	 * @return The is empty flag.
	 */
	public boolean isEmpty() {
		return _list.isEmpty();
	}
	
	public ArrayList<Object> getList(){
		return _list;
	}

	/**
	 * Insert an object into the object array list at the position given as a
	 * parameter.
	 * 
	 * @param index
	 *            index to insert.
	 * @param object
	 *            object to insert.
	 */
	public void insert(int index, Object object) {
		try {
			if (_list.get(index) != null)
				_list.set(index, object);
		} catch (IndexOutOfBoundsException exception) {
			try {
				_list.add(index, object);
			} catch (IndexOutOfBoundsException exception2) {

				// Updates the log
				AcideLog.getLog().error(exception2.getMessage());
				exception2.printStackTrace();
			}
		}
	}
	
	/**
	 * Insert an object into the object array list at the position given as a
	 * parameter and shift the element currently at that position (if any)
	 *  and any subsequent elements to the right (add one to their indices). 
	 * 
	 * @param index
	 *            index to insert.
	 * @param object
	 *            object to insert.
	 */
	public void insertAt(int index, Object object){
		if (index < 0){
			_list.add(0, object);
		}else if (index > _list.size()){
			_list.add(_list.size(), object);
		} else {
			_list.add(index, object);
		}
	}

	/**
	 * Returns the object from the object array list at the index given as a
	 * parameter.
	 * 
	 * @param index
	 *            index to get.
	 * 
	 * @return the object from the object array list at the index given as a
	 *         parameter.
	 */
	public Object getObjectAt(int index) {
		try {
			return (Object) _list.get(index);
		} catch (IndexOutOfBoundsException exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();

			return null;
		}
	}

	/**
	 * Removes an element at the index of the object array list given as a
	 * parameter.
	 * 
	 * @param index
	 *            index to remove.
	 */
	public void removeAt(int index) {
		try {

			// Removes the object from the list specified by the index
			_list.remove(index);
			_list.trimToSize();
		} catch (IndexOutOfBoundsException exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}

	/**
	 * Returns the object array list size.
	 * 
	 * @return the object array list size.
	 */
	public int size() {
		return _list.size();
	}

	/**
	 * Clears the object array list.
	 */
	public void clear() {
		_list.clear();
	}

	/**
	 * Adds an object to the list.
	 * 
	 * @param object
	 *            new object to add.
	 */
	public void insert(Object object) {
		_list.add(object);
	}

	/**
	 * Sets a new value to the object in the index specified.
	 * 
	 * @param index
	 *            index to set.
	 * @param object
	 *            new value to set.
	 */
	public void setValueAt(int index, String object) {
		_list.set(index, object);
	}

	/**
	 * Sets a new value to the object given as a parameter.
	 * 
	 * @param object
	 *            new value to set.
	 */
	public void setValue(Object object) {

		if (_list.indexOf(object) != -1)
			_list.set(_list.indexOf(object), object);
	}

	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_list == null) ? 0 : _list.hashCode());
		return result;
	}


	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjectList other = (ObjectList) obj;
		if (_list == null) {
			if (other._list != null)
				return false;
		} else  if (!_list.equals(other._list))
			return false;
		return true;
	}
	
	public String toString(){
		return _list.toString();
	}
	
	public int indexOf(Object ob){
		return _list.indexOf(ob);
	}
}
