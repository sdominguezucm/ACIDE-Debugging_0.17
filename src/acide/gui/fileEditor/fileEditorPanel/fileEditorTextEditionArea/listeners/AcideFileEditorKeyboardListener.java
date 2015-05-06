/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 * 
 * Copyright (C) 2007-2013  
 * Authors:
 * 		- Fernando Sáenz Pérez (Team Director).
 *      - Version from 0.1 to 0.6:
 *      	- Diego Cardiel Freire.
 *			- Juan José Ortiz Sánchez.
 *          - Delfín Rupérez Cañas.
 *      - Version 0.7:
 *          - Miguel Martín Lázaro.
 *      - Version 0.8:
 *      	- Javier Salcedo Gómez.
 *      - Version from 0.9 to 0.11:
 *      	- Pablo Gutiérrez García-Pardo.
 *      	- Elena Tejeiro Pérez de Ágreda.
 *      	- Andrés Vicente del Cura.
 *      - Version from 0.12 to 0.15
 *      	- Semíramis Gutiérrez Quintana
 *      	- Juan Jesús Marqués Ortiz
 *      	- Fernando Ordás Lorente
 *      - Version from 0.16 to 0.17
 *      	- Sergio Domínguez Fuentes
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
package acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.listeners;

import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Cursor;

import acide.gui.mainWindow.AcideMainWindow;

/**
 * ACIDE - A Configurable IDE file editor text edition area keyboard listener.
 * 
 * @version 0.11
 * @see FocusListener
 */
public class AcideFileEditorKeyboardListener extends KeyAdapter {

	int arr[] = new int[2];
	
	private AcideMainWindow acideWindow;

	public AcideFileEditorKeyboardListener() {
		this.acideWindow = AcideMainWindow.getInstance();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent keyEvent) {

		// If it is F5
		if (keyEvent.getKeyCode() == KeyEvent.VK_F9){
			acideWindow.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			// Sends the file content to the console
			AcideMainWindow.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanel().getPopupMenu()
					.getSendFileContentToConsoleMenuItem().doClick();
			
		
			}
		acideWindow.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
	
	}
}
