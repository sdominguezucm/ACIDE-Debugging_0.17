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
 * ACIDE - A Configurable IDE capitalize menu item listener.
 * 
 * @version 0.14
 * @see ActionListener
 */
public class AcideCapitalizeMenuItemListener implements ActionListener{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		action(e);
	}

	public static void action(ActionEvent event) {
		AcideTextComponent target = AcideMainWindow
				.getInstance().getFileEditorManager()
				.getSelectedFileEditorPanel().getActiveTextEditionArea();
		try {
		
			if (target.getSelectedText()!=null){
				String text = target.getSelectedText();
				
				if (!isTextCapitalized(text)){
					int start = target.getSelectionStart();
					int end = target.getSelectionEnd();
					
					target.getDocument().remove(start, text.length());
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
		        
		        if (!isCapitalized(word)){
				        
		        		int position = target.getCaretPosition();//Store the current caret position

				        target.setSelectionStart(left); // restore previous position/selection
				        target.setSelectionEnd(right);
				        target.replaceSelection(capitalizeWord(word));
				        target.setCaretPosition(position);//Set the caret to its original position
				 	}
				 }
			} catch (BadLocationException e) {
					e.printStackTrace();
			}
		
	}
	
	/**
	 * Checks if a word is already capitalized.
	 * @param word
	 */
	private static boolean isCapitalized(String word){
		boolean found = false;
		int  i = 1;
		while (!(found) && i<word.length()){
			found = Character.isUpperCase(word.charAt(i));
			i++;
		}
		found= Character.isUpperCase(word.charAt(0))&&!found;
		return found;
		
	}
	
	/**
	 * Checks if a text is already capitalized.
	 * @param text
	 */
	private static boolean isTextCapitalized(String text){
		boolean found = true;
		StringTokenizer stringTokenizer = new StringTokenizer(text);
		while ((found) && stringTokenizer.hasMoreTokens()){
			String word = stringTokenizer.nextToken();
			found = isCapitalized(word);
		}
		return found;
		
	}
	
	/**
	 * Capitalizes a word
	 * @param word
	 * @return newWord
	 * 			capitalized word
	 */
	private static String capitalizeWord(String word){
		String tail = word.substring(1);
        String newWord = word.substring(0, 1).toUpperCase()+tail.toLowerCase();
        return newWord;
	}

}
