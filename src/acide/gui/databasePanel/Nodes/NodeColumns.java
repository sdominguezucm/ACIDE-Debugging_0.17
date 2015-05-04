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
package acide.gui.databasePanel.Nodes;

import java.util.LinkedList;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;


/**
 * ACIDE - A Configurable IDE node columns.
 * 
 * @version 0.16
 * @see DefaultMutableTreeNode
 */
public class NodeColumns extends AcideDataBaseNodes {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NodeColumns(String text,DefaultTreeModel _treeModel) {
		super(text,_treeModel);
	}
	
	public boolean update(){
		this.setUserObject(AcideLanguageManager.getInstance().getLabels().getString("s2030")); 		
		if(isBuilt()){
			setAllowsChildren(true);
			Vector<String> childs = getChilds();
			
			String name = getParent().toString();
			if (name.contains("("))
				name = name.substring(0,name.indexOf("("));
				
			LinkedList<String> columns = AcideDatabaseManager.getInstance().getViewFields(getParent().getParent().getParent().toString(),name);
			for(int j=0; j< columns.size(); j++){
				String col = columns.get(j);
				if(!childs.contains(col)){ //a�adimos
					DefaultMutableTreeNode colN = new DefaultMutableTreeNode(col);
					colN.setAllowsChildren(false);
					_treeModel.insertNodeInto(colN, this, j);//To preserve the original order of the elements		
					
				}//si ya estaba no hacemos nada
				childs.remove(col);
			}
			if(childs.size()>0){ //borramos las que sobran
				for(int j=0;j<childs.size(); j++){
					String child = childs.get(j);
					_treeModel.removeNodeFromParent(getChild(child));
				}
			}
		}
		return true;
	}
}
