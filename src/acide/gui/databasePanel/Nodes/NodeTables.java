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
package acide.gui.databasePanel.Nodes;

import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.databasePanel.popup.AcideDataBasePanelTablesPopupMenu;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE nodetables.
 * 
 * @version 0.16
 * @see DefaultMutableTreeNode
 */
public class NodeTables extends AcideDataBaseNodes {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NodeTables(String text,DefaultTreeModel _treeModel) {
		super(text,_treeModel);
	}

	@Override
	public boolean update() {
		this.setUserObject(AcideLanguageManager.getInstance().getLabels().getString("s2028"));		
		if(isBuilt()){
			setAllowsChildren(false);			
			AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();

			LinkedList<String> tables = AcideDatabaseManager.getInstance().getTables(getParent().toString());
			
			if (AcideDatabaseManager.getInstance().currentDB().equals("$des")){
				
				Vector<String> cleanTables = clearList(tables);
				Vector<String> cleanTypeTables = clearOnlyTypes(tables);
				
				setAllowsChildren(true);	
				
				for(int j=0; j< cleanTables.size(); j++){
						NodeTable tableN =null;
						if (panel.isNameTables()) tableN = new NodeTable(cleanTables.get(j),_treeModel);
							else if (panel.isNameFieldsTables()) tableN = new NodeTable(cleanTypeTables.get(j),_treeModel);
								else if(panel.isNameFieldsTypesTables()) tableN = new NodeTable(tables.get(j),_treeModel);
						
						tableN.setAllowsChildren(false);
						_treeModel.insertNodeInto(tableN, this, this.getChildCount());
						
				}
			} else {
					setAllowsChildren(true);	
				
					for(int j=0; j< tables.size(); j++){
							NodeTable tableN = new NodeTable(tables.get(j),_treeModel);
							tableN.setAllowsChildren(false);
							_treeModel.insertNodeInto(tableN, this, this.getChildCount());
					}
				}
			_treeModel.nodeStructureChanged(this);
					
		}		
		return true;
	}
	

	private Vector<String> clearList(LinkedList<String> tables) {
		Vector<String> result = new  Vector<String>();
		for(int childId = 0; childId < tables.size(); childId++){
			String childS = tables.get(childId);		
			if(childS.contains("("))
				childS = childS.substring(0,childS.indexOf("("));
			result.add(childS);
		}
		return result;
	}
	
	private Vector<String> clearOnlyTypes(LinkedList<String> tables) {
		Vector<String> result = new  Vector<String>();
		for(int childId = 0; childId < tables.size(); childId++){
			String childClear = "";
			String[] child = tables.get(childId).split(",");
			for(int i=0;i<child.length;i++){
				if (i==(child.length-1)) childClear = childClear + child[i].substring(0, child[i].indexOf(":"))+")";
				else childClear = childClear + child[i].substring(0, child[i].indexOf(":"))+",";
			}
			result.add(childClear);
			
		}
		return result;
	}

	@Override
	public JPopupMenu getPopUp() {
		return AcideDataBasePanelTablesPopupMenu.getInstance();
	}
}
