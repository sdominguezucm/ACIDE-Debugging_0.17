/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2014  
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
package acide.gui.databasePanel.utils;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * ACIDE - A customized Transferable
 * 
 * @version 0.15
 * @see Transferable
 */
@SuppressWarnings("deprecation")
public class DataViewTableSelection implements Transferable {

	// This DataFlavor object is used when we transfer the content of a JTable directly
	public static DataFlavor dataViewTableFlavor =
	    new DataFlavor(String[][].class, "String Matrix");
	  
	// These are the data flavors we support
	protected static DataFlavor[] supportedFlavors = {
		dataViewTableFlavor, // Transfer as a dataViewTableSelection object
	    DataFlavor.stringFlavor,      // Transfer as a String object
	    DataFlavor.plainTextFlavor,   // Transfer as a stream of Unicode text
	  };

	  String[][] matrix;                    // The matrix we encapsulate and transfer
	
	/** Create a new TransferableMatrix that encapsulates the specified color */
	public DataViewTableSelection(String[][] matrix) { this.matrix = matrix; }

	
	/** 
	* Transfer the data.  Given a specified DataFlavor, return an Object
	* appropriate for that flavor.  Throw UnsupportedFlavorException if we
	* don't support the requested flavor.
	*/
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if (flavor.equals(dataViewTableFlavor)) return matrix;
	    else if (flavor.equals(DataFlavor.stringFlavor)) return matrix.toString();
	    else if (flavor.equals(DataFlavor.plainTextFlavor))
	      return new ByteArrayInputStream(matrix.toString().getBytes("Unicode"));
	    else throw new UnsupportedFlavorException(flavor);
	}

	/** Return a list of DataFlavors we can support */
	@Override
	public DataFlavor[] getTransferDataFlavors(){
		return supportedFlavors;
	}

	/** Check whether a specified DataFlavor is available */
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		if (flavor.equals(dataViewTableFlavor) || 
		        flavor.equals(DataFlavor.stringFlavor) ||
		        flavor.equals(DataFlavor.plainTextFlavor)) return true;
		    return false;
	}

}
