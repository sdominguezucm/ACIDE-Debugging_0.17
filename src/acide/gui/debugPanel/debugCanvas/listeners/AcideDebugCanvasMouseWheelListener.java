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
package acide.gui.debugPanel.debugCanvas.listeners;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.graphCanvas.AcideGraphCanvas.CanvasPanel;

/**
 * ACIDE - A Configurable IDE debug canvas mouse wheel listener.
 * 
 * @version 0.15
 * @see MouseWheelListener
 */
public class AcideDebugCanvasMouseWheelListener implements MouseWheelListener {
	/**
	 * ACIDE - A Configurable IDE debug canvas mouse wheel listener target canvas.
	 */
	AcideDebugCanvas _canvas;
	/**
	 * Creathes a new ACIDE - A Configurable IDE debug canvas mouse wheel listener.
	 * @param canvas the canvas to listen
	 */
	public AcideDebugCanvasMouseWheelListener(AcideDebugCanvas canvas){
		this._canvas=canvas;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseWheelListener#mouseWheelMoved(java.awt.event.
	 * MouseWheelEvent)
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent ev) {

		// checks if the graph exists and if the control key is pressed
		if (_canvas.get_graph() != null && ev.isControlDown()) {
			// gets the coordinates of the event
			int difx = ev.getX() - _canvas.getX0();
			int dify = ev.getY() - _canvas.getY0();
			// checks the wheel rotation direction
			if (ev.getWheelRotation() < 0) {
				// zooms in the canvas and move the graph
				_canvas.zoomIn(CanvasPanel.Graph);
				int difx2 = ev.getX() - _canvas.getX0();
				int dify2 = ev.getY() - _canvas.getY0();
				_canvas.moveGraph(difx2 - difx, dify2 - dify);
			} else {
				// zooms out the canvas and move the graph
				_canvas.zoomOut(null);
				int difx2 = ev.getX() - _canvas.getX0();
				int dify2 = ev.getY() - _canvas.getY0();
				_canvas.moveGraph(difx2 - difx, dify2 - dify);
			}
			// repaint the canvas
			_canvas.repaint();
		}
	}

}
