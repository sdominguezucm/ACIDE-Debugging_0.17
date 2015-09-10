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
 *      - Version 0.17
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
package acide.gui.debugPanel.debugSQLPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.debugSQLPanel.listeners.AcideDebugSQLPanelFirstNodeListener;
import acide.gui.debugPanel.debugSQLPanel.listeners.AcideDebugSQLPanelColorNodeListener;
import acide.gui.debugPanel.debugSQLPanel.listeners.AcideDebugSQLPanelGrayNodeListener;
import acide.gui.debugPanel.debugSQLPanel.listeners.AcideDebugSQLPanelRedNodeListener;
import acide.gui.debugPanel.debugSQLPanel.listeners.AcideDebugSQLPanelGreenNodeListener;
import acide.gui.debugPanel.debugSQLPanel.listeners.AcideDebugSQLPanelLastNodeListener;
import acide.gui.debugPanel.debugSQLPanel.listeners.AcideDebugSQLPanelNexNodeListener;
import acide.gui.debugPanel.debugSQLPanel.listeners.AcideDebugSQLPanelPreviousNodeListener;
import acide.gui.debugPanel.debugSQLPanel.listeners.AcideDebugSQLPanelRefreshListener;
import acide.gui.debugPanel.debugSQLPanel.listeners.AcideDebugSQLPanelShowLabelsListener;
import acide.gui.debugPanel.debugSQLPanel.listeners.AcideDebugSQLPanelViewBoxListener;
import acide.gui.debugPanel.debugSQLPanel.listeners.AcideDebugSQLPanelZoomInListener;
import acide.gui.debugPanel.debugSQLPanel.listeners.AcideDebugSQLPanelZoomOutListener;
import acide.gui.debugPanel.debugSQLPanel.listeners.AcideDebugSQLPanelZoomSpinnerListener;
import acide.gui.debugPanel.utils.AcideDebugPanelHighLighter;
import acide.gui.graphUtils.DirectedWeightedGraph;
import acide.gui.graphUtils.Node;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.DesDatabaseManager;

/**
 * 
 * ACIDE - A Configurable IDE debug SQL panel.
 * 
 * @version 0.15
 * @see JPanel
 */
public class AcideDebugSQLPanel extends JPanel {

	/**
	 * ACIDE - A Configurable IDE debug canvas selected Debug node color.
	 */
	private Color _selectedDebugNodeColor = Color.YELLOW;
	/**
	 * ACIDE - A Configurable IDE debug SQL panel class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE debug SQL panel debug canvas.
	 */
	public static AcideDebugCanvas _canvas;
	/**
	 * ACIDE - A Configurable IDE debug SQL panel button panel.
	 */
	private JPanel _mainButtonPanel;
	/**
	 * ACIDE - A Configurable IDE debug SQL panel zoom spinner.
	 */
	private static JSpinner _zoomSpinner;
	/**
	 * ACIDE - A Configurable IDE debug SQL panel show label check box.
	 */
	private JCheckBox _showLabelsMenuItem;
	/**
	 * ACIDE - A Configurable IDE debug SQL panel show rules check box.
	 */
	private JCheckBox _showSQLMenuItem;

	/**
	 * ACIDE - A Configurable IDE debug SQL panel view box.
	 */
	private JComboBox _viewBox;
	/**
	 * ACIDE - A Configurable IDE debug SQL panel query.
	 */
	private String _query;
	/**
	 * ACIDE - A Configurable IDE debug SQL panel high lighter.
	 */
	private AcideDebugPanelHighLighter _highLighter;
	/**
	 * ACIDE - A Configurable IDE debug SQL panel Color Node.
	 */
	private JMenuItem _colorNode;
	/**
	 * ACIDE - A Configurable IDE debug SQL panel Color Node.
	 */
	private JMenuItem _colorNodeRed;
	/**
	 * ACIDE - A Configurable IDE debug SQL panel Color Node.
	 */
	private JMenuItem _colorNodeGreen;
	/**
	 * ACIDE - A Configurable IDE debug SQL panel Color Node.
	 */
	private JMenuItem _colorNodeGray;
	
	/**
	 * ACIDE - A Configurable IDE debug datalog panel to the first button icon
	 */
	private final static ImageIcon TO_THE_FIRST_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/menu/double_left_arrow.png");
	/**
	 * ACIDE - A Configurable IDE debug datalog panel to the last button icon
	 */
	private final static ImageIcon TO_THE_LAST_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/menu/double_right_arrow.png");
	/**
	 * ACIDE - A Configurable IDE debug SQL panel to the right button icon
	 */
	private final static ImageIcon TO_THE_RIGHT_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/menu/right_arrow.png");
	/**
	 * ACIDE - A Configurable IDE debug SQL panel to the left button icon
	 */
	private final static ImageIcon TO_THE_LEFT_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/menu/left_arrow.png");
	/**
	 * ACIDE - A Configurable IDE debug SQL panel add button icon
	 */
	private final static ImageIcon ADD_IMAGE = new ImageIcon(
			"./resources/icons/graph/add.png");
	/**
	 * ACIDE - A Configurable IDE debug SQL panel minus button icon
	 */
	private final static ImageIcon MINUS_IMAGE = new ImageIcon(
			"./resources/icons/graph/minus.png");
	/**
	 * ACIDE - A Configurable IDE debug SQL panel refresh button icon
	 */
	private final static ImageIcon REFRESH_IMAGE = new ImageIcon(
			"./resources/icons/panels/refresh.png");

	// builds the refresh button
	public static JButton refreshSQL = new JButton();

	public JPopupMenu _popUp = null;

	public AcideDebugSQLPanel() {
		// Sets the layout of the panel
		setLayout(new BorderLayout());
		// Builds the canvas
		buildCanvas();
		// Builds the button panel
		buildButtons();
		// Sets the ACIDE - A Configurable IDE explorer panel listeners
		setListeners();

		// Adds the canvas
		add(_canvas, BorderLayout.CENTER);
		// Adds the button panel
		add(_mainButtonPanel, BorderLayout.SOUTH);

		setHighLighter(new AcideDebugPanelHighLighter());
		//Inits the popUp panel
		popUpInit();

		}

	/**
	 * Builds the buttons for the ACIDE - A Configurable IDE debug SQL panel.
	 */
	private void buildButtons() {
		// builds the button panel
		_mainButtonPanel = new JPanel();
		// adds the layout to the button panel
		_mainButtonPanel.setLayout(new BorderLayout());
		// creates the sub button panel 1
		JPanel subButtonPanel1 = new JPanel();
		// adds the layout to the sub button panel
		subButtonPanel1.setLayout(new FlowLayout());
		// adds the sub button panel to the main button panel
		_mainButtonPanel.add(subButtonPanel1, BorderLayout.NORTH);
		refreshSQL.setIcon(REFRESH_IMAGE);
		refreshSQL.setPreferredSize(new Dimension((int) (1.5 * refreshSQL
				.getIcon().getIconWidth()), (int) refreshSQL.getPreferredSize()
				.getHeight()));
		// adds the action listener to the refresh button
		refreshSQL.addActionListener(new AcideDebugSQLPanelRefreshListener());
		// sets tooltip button
		refreshSQL.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2044"));
		// unable the button
		refreshSQL.setEnabled(false);
		// adds the refresh button
		subButtonPanel1.add(refreshSQL);
		// creates the spinner model for the zoom spinner
		SpinnerModel model = new SpinnerNumberModel(
				(int) _canvas.getZoom() * 100, 0, Integer.MAX_VALUE, 1);
		// creates the zoom spinner with the model
		_zoomSpinner = new JSpinner(model);
		((JSpinner.DefaultEditor) _zoomSpinner.getEditor()).getTextField()
				.setColumns(4);
		// adds the action listener to the zoom spinner
		_zoomSpinner
				.addChangeListener(new AcideDebugSQLPanelZoomSpinnerListener());
		subButtonPanel1.add(_zoomSpinner);
		// creates the zoom in button
		JButton zoomin = new JButton(ADD_IMAGE);
		zoomin.setPreferredSize(new Dimension((int) (1.5 * zoomin.getIcon()
				.getIconWidth()), (int) zoomin.getPreferredSize().getHeight()));
		// adds the zoom in action listener
		zoomin.addActionListener(new AcideDebugSQLPanelZoomInListener());
		// adds the zoom in button to the button panel
		subButtonPanel1.add(zoomin);
		// creates the zoom out button
		JButton zoomout = new JButton(MINUS_IMAGE);
		zoomout.setPreferredSize(new Dimension((int) (1.5 * zoomout.getIcon()
				.getIconWidth()), (int) zoomout.getPreferredSize().getHeight()));
		// adds the zoom out action listener
		zoomout.addActionListener(new AcideDebugSQLPanelZoomOutListener());
		// adds the zoom out button to the button panel
		subButtonPanel1.add(zoomout);
		// builds the show labels check box
		_showLabelsMenuItem = new JCheckBox();
		// sets the default selected option
		_showLabelsMenuItem.setSelected(true);
		// sets the text of the show labels check box
		_showLabelsMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2263"));
		_showLabelsMenuItem.setFont(_showLabelsMenuItem.getFont().deriveFont(
				10f));
		// adds the action listener to the show labels check box
		_showLabelsMenuItem
				.addActionListener(new AcideDebugSQLPanelShowLabelsListener());
		// adds the show labels check box to the button panel
		subButtonPanel1.add(_showLabelsMenuItem);
		// creates the sub button panel 2
		JPanel subButtonPanel2 = new JPanel();
		// adds the layout to the sub button panel
		subButtonPanel2.setLayout(new FlowLayout());
		// adds the sub button panel to the main button panel
		_mainButtonPanel.add(subButtonPanel2, BorderLayout.SOUTH);
		// Builds the view label
		JLabel viewLabel = new JLabel(AcideLanguageManager.getInstance()
				.getLabels().getString("s2287"));
		viewLabel.setFont(viewLabel.getFont().deriveFont(10f));
		subButtonPanel2.add(viewLabel);
		// Builds the views combo box
		_viewBox = new JComboBox();
		_viewBox.addItem("          ");
		// Adds the listeners to the views combo box
		_viewBox.addActionListener(new AcideDebugSQLPanelViewBoxListener());
		_viewBox.addFocusListener(new FocusAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.awt.event.FocusAdapter#focusGained(java.awt.event.FocusEvent
			 * )
			 */
			@Override
			public void focusGained(FocusEvent e) {

				// Puts the wait cursor
				// AcideDebugSQLPanel.acideWindow.setCursor(new
				// Cursor(Cursor.WAIT_CURSOR));

				// Gets the views from the database
				LinkedList<String> l = DesDatabaseManager.getInstance()
						.executeCommand("/tapi /list_views");
				ArrayList<String> views = new ArrayList<String>();
				views.add("          ");
				// Parses the output from the database
				for (String s : l) {
					// Checks if the output is an error output
					if (s.equals("$error")) {
						// Resets the list of views
						views = new ArrayList<String>();
						views.add(AcideLanguageManager.getInstance()
								.getLabels().getString("s2287"));
						break;
					}
					// Checks if the output has ended
					if (s.equals("$eot"))
						break;
					// Adds the actual's view name to the list
					views.add(s);
				}
				// Gets the debug datalog panel list of views
				JComboBox viewBox = AcideMainWindow.getInstance()
						.getDebugPanel().getDebugSQLPanel().getViewBox();
				// Removes the previous views
				viewBox.removeAllItems();
				// Adds the new views
				for (String item : views)
					viewBox.addItem(item);
				viewBox.setPopupVisible(false);
				viewBox.setPopupVisible(true);

				// Puts the default cursor
				// AcideDebugSQLPanel.acideWindow.setCursor(new
				// Cursor(Cursor.DEFAULT_CURSOR));

			}
		});
		subButtonPanel2.add(_viewBox);
		// Creates the first node button
		JButton firstNodeButton = new JButton(TO_THE_FIRST_IMAGE);
		firstNodeButton.setPreferredSize(new Dimension(
				(int) (1.5 * firstNodeButton.getIcon().getIconWidth()),
				(int) firstNodeButton.getPreferredSize().getHeight()));
		// adds the action listener to the first node button
		firstNodeButton
				.addActionListener(new AcideDebugSQLPanelFirstNodeListener());
		// adds the firs node button to the button panel
		subButtonPanel2.add(firstNodeButton);
		// creates the previous node button
		JButton prevNodeButton = new JButton(TO_THE_LEFT_IMAGE);
		prevNodeButton.setPreferredSize(new Dimension(
				(int) (1.5 * prevNodeButton.getIcon().getIconWidth()),
				(int) prevNodeButton.getPreferredSize().getHeight()));
		// adds the action listener to the previous node button
		prevNodeButton
				.addActionListener(new AcideDebugSQLPanelPreviousNodeListener());
		// adds the previous node button to the button panel
		subButtonPanel2.add(prevNodeButton);
		// creates the next node button
		JButton nextNodeButton = new JButton(TO_THE_RIGHT_IMAGE);
		nextNodeButton.setPreferredSize(new Dimension(
				(int) (1.5 * nextNodeButton.getIcon().getIconWidth()),
				(int) nextNodeButton.getPreferredSize().getHeight()));
		// adds the action listener to the next node button
		nextNodeButton
				.addActionListener(new AcideDebugSQLPanelNexNodeListener());
		// adds the next node button to the button panel
		subButtonPanel2.add(nextNodeButton);
		// Creates the last node button
		JButton lastNodeButton = new JButton(TO_THE_LAST_IMAGE);
		lastNodeButton.setPreferredSize(new Dimension(
				(int) (1.5 * lastNodeButton.getIcon().getIconWidth()),
				(int) lastNodeButton.getPreferredSize().getHeight()));
		// adds the action listener to the last node button
		lastNodeButton
				.addActionListener(new AcideDebugSQLPanelLastNodeListener());
		// adds the last node button to the button panel
		subButtonPanel2.add(lastNodeButton);
		// Builds the show rules check box
		_showSQLMenuItem = new JCheckBox();
		// sets the default selected option
		_showSQLMenuItem.setSelected(false);
		// sets the text of the show rules check box
		_showSQLMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
				.getString("s2141"));

		_showSQLMenuItem.setFont(_showSQLMenuItem.getFont().deriveFont(10f));
		// showRulesMenuItem.addActionListener(arg0)
		subButtonPanel2.add(_showSQLMenuItem);

	}

	/**
	 * Builds the canvas of the the ACIDE - A Configurable IDE debug SQL panel.
	 */
	private void buildCanvas() {
		_canvas = new AcideDebugCanvas();
		_canvas.setBounds(this.getBounds());
		_canvas.setVisible(true);
		_canvas.setSelectedNodeColor(_selectedDebugNodeColor);
		_canvas.repaint();
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel debug SQL panel
	 * canvas.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel debug SQL panel
	 *         canvas.
	 */
	public AcideDebugCanvas getCanvas() {
		return _canvas;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug SQL panel
	 * canvas.
	 * 
	 * @param canvas
	 *            new value to set.
	 */
	public void setCanvas(AcideDebugCanvas canvas) {
		_canvas = canvas;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel debug SQL panel view
	 * box.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel debug SQL panel view
	 *         box.
	 */
	public JComboBox getViewBox() {
		return _viewBox;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug SQL panel view
	 * box.
	 * 
	 * @param viewBox
	 *            new value to set.
	 */
	public void setViewBox(JComboBox viewBox) {
		_viewBox = viewBox;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel debug SQL panel zoom
	 * spinner.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel debug SQL panel zoom
	 *         spinner.
	 */
	public static JSpinner getZoomSpinner() {
		return _zoomSpinner;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug SQL panel zoom
	 * spinner.
	 * 
	 * @param zoomSpinner
	 *            new value to set.
	 */
	public void setZoomSpinner(JSpinner zoomSpinner) {
		_zoomSpinner = zoomSpinner;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel debug SQL panel show
	 * labels item.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel debug SQL panel show
	 *         labels item.
	 */
	public JCheckBox getShowLabelsMenuItem() {
		return _showLabelsMenuItem;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug SQL panel show
	 * labels item.
	 * 
	 * @param showLabelsMenuItem
	 *            new value to set.
	 */
	public void setShowLabelsMenuItem(JCheckBox showLabelsMenuItem) {
		this._showLabelsMenuItem = showLabelsMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel debug SQL panel show
	 * SQL item.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel debug SQL panel show
	 *         SQL item.
	 */
	public JCheckBox getShowSQLMenuItem() {
		return _showSQLMenuItem;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug SQL panel show
	 * SQL item.
	 * 
	 * @param showSQLsMenuItem
	 *            new value to set.
	 */
	public void setShowSQLMenuItem(JCheckBox showSQLsMenuItem) {
		this._showSQLMenuItem = showSQLsMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE debug panel debug SQL panel query.
	 * 
	 * @return the ACIDE - A Configurable IDE debug panel debug SQL panel query.
	 */
	public String getQuery() {
		return _query;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE debug SQL panel query.
	 * 
	 * @param query
	 *            new value to set.
	 */
	public void setQuery(String query) {
		this._query = query;
	}

	/**
	 * Returns the the ACIDE - A Configurable IDE debug SQL panel highligther.
	 * 
	 * @return the the ACIDE - A Configurable IDE trace datalog highligther.
	 */
	public AcideDebugPanelHighLighter getHighLighter() {
		return _highLighter;
	}

	/**
	 * Sets a new value to the the the ACIDE - A Configurable IDE trace sql
	 * panel highligther.
	 * 
	 * @param highLighter
	 *            the new value to set.
	 */
	public void setHighLighter(AcideDebugPanelHighLighter highLighter) {
		this._highLighter = highLighter;
	}

	/**
	 * Initialization the popUp panel for the ACIDE - A Configurable IDE debug SQL panel.
	 */
	private void popUpInit() {
		// we create the popUp menu
		_popUp = new JPopupMenu();
		// Option unknow node
		_colorNodeGray = new JMenuItem(AcideLanguageManager.getInstance().getLabels().getString("s2322"));
		_colorNodeGray.addActionListener(new AcideDebugSQLPanelGrayNodeListener());
		_popUp.add(_colorNodeGray);				
		// Option non valid node
		_colorNodeRed = new JMenuItem(AcideLanguageManager.getInstance().getLabels().getString("s2319"));
		_colorNodeRed.addActionListener(new AcideDebugSQLPanelRedNodeListener());
		_popUp.add(_colorNodeRed);
		// option valid node
		_colorNodeGreen = new JMenuItem(AcideLanguageManager.getInstance().getLabels().getString("s2320"));
		_colorNodeGreen.addActionListener(new AcideDebugSQLPanelGreenNodeListener());
		_popUp.add(_colorNodeGreen);
		// option change comun color nodes
		_colorNode = new JMenuItem(AcideLanguageManager.getInstance().getLabels().getString("s2321"));
		_colorNode.addActionListener(new AcideDebugSQLPanelColorNodeListener());
		_popUp.add(_colorNode);
		
		
	}

	public void this_mousePressed(MouseEvent e) {
		showPopupMenu(e);

	}

	
	public void this_mouseReleased(MouseEvent e) {
		
		// Gets the graph of the canvas
		DirectedWeightedGraph graph = _canvas.get_graph();
		// Gets the nodes of the graph
		ArrayList<Node> nodes = graph.get_nodes();
		// Searches if a node has been clicked
		for (Node n : nodes) {
			if (e.getX() >= n.getX()
					&& e.getX() <= n.getX() + (int) (_canvas.getNodeSize() * _canvas.getZoom())
					&& e.getY() >= n.getY()
					&& e.getY() <= n.getY() + (int) (_canvas.getNodeSize() * _canvas.getZoom())) {
				// Shows PopUp menu
				showPopupMenu(e);
				// Gets the selected node name
				String selected = n.getLabel();
				// Gets the highlighter
				AcideDebugPanelHighLighter highLighter = AcideMainWindow.getInstance().getDebugPanel().getTraceDatalogPanel().getHighLighter();
				// Resets the highlights
				highLighter.resetLines();
				highLighter.unHighLight();
				// Highlights the lines corresponding to the new selected node
				highLighter.highLight(selected);
				
				}
		}
	
	}

	private void showPopupMenu(MouseEvent e) {

		if (e.isPopupTrigger()) { 
			// we show the popUp in the position of mouse
			_popUp.show(e.getComponent(), e.getX(), e.getY());
		}

	}

	class AcideDegugSQLPanelClickListener extends MouseAdapter {
		private AcideDebugSQLPanel adaptee;

		AcideDegugSQLPanelClickListener(AcideDebugSQLPanel adaptee) {
			this.adaptee = adaptee;
		}

		public void mousePressed(MouseEvent e) {
			adaptee.this_mousePressed(e);
		}

		public void mouseReleased(MouseEvent e) {
			adaptee.this_mouseReleased(e);
		}
	}

	/**
	 * Sets the ACIDE - A Configurable IDE database panel listeners.
	 */
	private void setListeners() {

		// Sets the ACIDE - A Configurable IDE database panel _popUp menu listener
		_canvas.addMouseListener(new AcideDegugSQLPanelClickListener(this));
				
	}

}
