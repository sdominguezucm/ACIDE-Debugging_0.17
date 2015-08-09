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
 *      - Version from 0.12 to 0.16
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
package acide.gui.menuBar.configurationMenu.debugPanelMenu.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

import acide.gui.graphCanvas.AcideGraphCanvas;
import acide.gui.mainWindow.AcideMainWindow;
/**
 * ACIDE - A Configurable IDE show labels menu item listener.
 * 
 * @see ActionListener
 */
public class AcideDebugPanelShowLabelsMenuItemListener implements ActionListener {
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		action(actionEvent);

	}
	
	public static void action(ActionEvent actionEvent){		
		// Gets the new value of the checkBox
		boolean selected = ((JCheckBoxMenuItem)actionEvent
				.getSource()).isSelected();
		// Updates the value of ShowinLabels in the canvas and repaint
		AcideMainWindow.getInstance().getDebugPanel().getTraceDatalogPanel().getCanvas().setShowingLabels(selected);
		AcideMainWindow.getInstance().getDebugPanel().getTraceDatalogPanel().getCanvas().repaint();
		AcideMainWindow.getInstance().getDebugPanel().getTraceSQLPanel().getCanvas().setShowingLabels(selected);
		AcideMainWindow.getInstance().getDebugPanel().getTraceSQLPanel().getCanvas().repaint();
		AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().getCanvas().setShowingLabels(selected);
		AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().getCanvas().repaint();
		
		AcideMainWindow.getInstance()
		.getMenu().getConfigurationMenu()
		.getDebugPanelMenu().get_showLabelsMenuItem().setSelected(selected);
		
		AcideMainWindow.getInstance()
		.getDebugPanel().getTraceDatalogPanel().getShowLabelsMenuItem()
		.setSelected(selected);
		AcideMainWindow.getInstance()
		.getDebugPanel().getTraceSQLPanel().getShowLabelsMenuItem()
		.setSelected(selected);
	}

}
