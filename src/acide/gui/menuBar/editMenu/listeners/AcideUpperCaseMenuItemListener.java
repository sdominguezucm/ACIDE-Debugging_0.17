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
 *      -Version from 0.12 to 0.16
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
package acide.gui.menuBar.editMenu.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.utils.AcideTextComponent;
import acide.gui.mainWindow.AcideMainWindow;

/**
 * ACIDE - A Configurable IDE upper case menu item listener.
 * 
 * @version 0.14
 * @see ActionListener
 */
public class AcideUpperCaseMenuItemListener implements ActionListener{
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		action(actionEvent);
	
	}

	public static void action(ActionEvent event) {
		AcideTextComponent target = AcideMainWindow
				.getInstance().getFileEditorManager()
				.getSelectedFileEditorPanel().getActiveTextEditionArea();
		try {
		
			if (target.getSelectedText()!=null){
				String text = target.getSelectedText();
				
				if (isTextLowerCase(text)){
					String newText = text.toUpperCase();
					int start = target.getSelectionStart();
					int end = target.getSelectionEnd();
				
					target.getDocument().remove(start, text.length());
					target.getDocument().insertString(start,newText, null);
					target.select(start, end);
				}
			}
			else { 
				int start = target.getSelectionStart();
				int end = target.getSelectionEnd();
	
				int left = javax.swing.text.Utilities.getWordStart(target, start);
		        int right = javax.swing.text.Utilities.getWordEnd(target, end);
	
		        Document doc = target.getDocument();
		        String word = doc.getText(left, right-left);
		        
		        if (isLowerCase(word)){
				        String newWord = word.toUpperCase();
				        int position = target.getCaretPosition();//Store the current caret position
				        
				        target.setSelectionStart(left); // restore previous position/selection
				        target.setSelectionEnd(right);
				        target.replaceSelection(newWord);
				        target.setCaretPosition(position);//Set the caret to its original position
				 	}
				 }
			} catch (BadLocationException e) {
					e.printStackTrace();
			}
		
	}
	
	/**
	 * Checks if a word is lower case
	 * @param word
	 * @return
	 */
	private static boolean isLowerCase(String word){
		boolean found = false;
		int  i = 0;
		while (!(found) && i<word.length()){
			found = Character.isLowerCase(word.charAt(i));
			i++;
		}
		return found;
		
	}

	/**
	 * Checks if a text is lower case
	 * @param text
	 * @return
	 */
	private static boolean isTextLowerCase(String text){
		boolean found = false;
		StringTokenizer stringTokenizer = new StringTokenizer(text);
		while ((!found) && stringTokenizer.hasMoreTokens()){
			String word = stringTokenizer.nextToken();
			found = isLowerCase(word);
		}
		return found;
		
	}
}
