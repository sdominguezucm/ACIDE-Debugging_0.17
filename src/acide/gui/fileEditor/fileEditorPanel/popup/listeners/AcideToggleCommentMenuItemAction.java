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
package acide.gui.fileEditor.fileEditorPanel.popup.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

import acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.utils.AcideTextComponent;
import acide.gui.mainWindow.AcideMainWindow;

/**
 * ACIDE - A Configurable IDE file editor panel popup menu toggle comment menu item
 * action listener.
 * 
 * @version 0.13
 * @see ActionListener
 */
public class AcideToggleCommentMenuItemAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent action) {
				
		String remarkSymbol =  AcideMainWindow
				.getInstance().getFileEditorManager()
				.getSelectedFileEditorPanel().getStyledDocument().getLexiconConfiguration().getRemarksManager().getSymbol();
				
		AcideTextComponent target = AcideMainWindow
						.getInstance().getFileEditorManager()
						.getSelectedFileEditorPanel().getActiveTextEditionArea();
		
		if (target == null)
			return;
		if ((!target.isEditable()) || (!target.isEnabled())) {
			UIManager.getLookAndFeel().provideErrorFeedback(target);
			return;
		}
		
		try {
			if (target.getSelectedText()!=null){
					int num = getNumberLinesSelected(target);
					for (int i=0; i<num;i++)
					{
						int start = getStartPosition(target, getFirstLine(target)+i);
						int startNoSpaces = getStartPositionNoSpaces(target, getFirstLine(target)+i);
						int end = getEndPosition(target);
						
							if (!getCurrentLine(target, getFirstLine(target)+i).startsWith(remarkSymbol) && !getCurrentLine(target, getFirstLine(target)+i).equals("\n")&&(startNoSpaces!=end))
								target.getDocument().insertString(start, remarkSymbol, null);
							else 
								{if (!getCurrentLine(target, getFirstLine(target)+i).equals("\n")&&(startNoSpaces!=end)) target.getDocument().remove(start, remarkSymbol.length());}
							
					}
			}
			else {
				int start = getStartPosition(target);
					if (!getCurrentLine(target).startsWith(remarkSymbol)&& !getCurrentLine(target).equals("\n"))
						target.getDocument().insertString(start, remarkSymbol, null);
					else
						{if (!getCurrentLine(target).equals("\n")) target.getDocument().remove(start, remarkSymbol.length());}
				
				}
				
		} catch (BadLocationException e) {
				e.printStackTrace();
			}
		
	}
	
	private int getNumberLinesSelected(AcideTextComponent target) {
		Document doc = target.getDocument();
		Element rootElement = doc.getDefaultRootElement();
		int selectionStart = target.getSelectionStart();
		int selectionEnd = target.getSelectionEnd();
		int firstLine = rootElement.getElementIndex(selectionStart);
		int lastLine = rootElement.getElementIndex(selectionEnd);
		int length = lastLine - firstLine;
		return length+1 ;
	}


	private int getStartPosition(AcideTextComponent target){
		
		
		Document doc = target.getDocument();
		Element rootElement = doc.getDefaultRootElement();
		int selectionStart = target.getSelectionStart();
		int line = rootElement.getElementIndex(selectionStart);

		int start = rootElement.getElement(line).getStartOffset();
		
		return start;
	}
	
	private int getStartPosition(AcideTextComponent target, int numLine){
			
			Document doc = target.getDocument();
			Element rootElement = doc.getDefaultRootElement();
	
			int start = rootElement.getElement(numLine).getStartOffset();
			
			return start;
		}
	
	private int getEndPosition(AcideTextComponent target){
		int selectionEnd = target.getSelectionEnd();

		return selectionEnd;
	}
	
private int getStartPositionNoSpaces(AcideTextComponent target, int numLine){
		
		Document doc = target.getDocument();
		Element rootElement = doc.getDefaultRootElement();

		int start = rootElement.getElement(numLine).getStartOffset();
		return start;
	}
	
	private int getFirstLine(AcideTextComponent target){
	
		Document doc = target.getDocument();
		Element rootElement = doc.getDefaultRootElement();
		int selectionStart = target.getSelectionStart();
		int firstLine = rootElement.getElementIndex(selectionStart);
		
		return firstLine;
	}
	
	
	public String getCurrentLine(AcideTextComponent target){
		// Determine which line we are on
		Document doc = target.getDocument();
		Element rootElement = doc.getDefaultRootElement();
		int selectionStart = target.getSelectionStart();
		int line = rootElement.getElementIndex(selectionStart);
		// Get the text for this line
		int start = rootElement.getElement(line).getStartOffset();
		int end = rootElement.getElement(line).getEndOffset();
		int length = end - start;
		String text = "";
		try {
			text = doc.getText(start, length);
		} catch (BadLocationException e) {
			
			e.printStackTrace();
		}
		return text;
	}
	
	public String getCurrentLine(AcideTextComponent target, int numLine){
		Document doc = target.getDocument();
		Element rootElement = doc.getDefaultRootElement();
		// Get the text for this line
		int start = rootElement.getElement(numLine).getStartOffset();
		int end = rootElement.getElement(numLine).getEndOffset();
		int length = end - start;
		String text = "";
		try {
			text = doc.getText(start, length);
		} catch (BadLocationException e) {
			
			e.printStackTrace();
		}
		return text;
	}
}
