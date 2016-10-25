/**
 * Copyright 2007 DFKI GmbH.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * This file is part of MARY TTS.
 *
 * MARY TTS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package marytts.unitselection.io;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HelpGUI {
	private final JEditorPane editPane;

	public HelpGUI(InputStream fileIn) {
		editPane = new JEditorPane();
		editPane.setContentType("text/html; charset=UTF-8");
		try {
			editPane.read(new InputStreamReader(fileIn, "UTF-8"), null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not read file : " + e.getMessage());
		}
		editPane.setPreferredSize(new Dimension(700, 500));
		editPane.setEditable(false);
	}

	public HelpGUI(String text) {
		editPane = new JEditorPane();
		editPane.setPreferredSize(new Dimension(700, 500));
		editPane.setContentType("text/html; charset=UTF-8");
		editPane.setText(text);
		editPane.setEditable(false);
	}

	/**
	 * Show a frame displaying the help file.
	 * 
	 * 
	 * @return true, if no error occurred
	 */
	public boolean display() {
		final JFrame frame = new JFrame("Help");
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints gridC = new GridBagConstraints();
		frame.getContentPane().setLayout(gridBagLayout);

		gridC.gridx = 0;
		gridC.gridy = 0;
		// resize scroll pane:
		gridC.weightx = 1;
		gridC.weighty = 1;
		gridC.fill = GridBagConstraints.HORIZONTAL;
		JScrollPane scrollPane = new JScrollPane(editPane);
		scrollPane.setPreferredSize(editPane.getPreferredSize());
		gridBagLayout.setConstraints(scrollPane, gridC);
		frame.getContentPane().add(scrollPane);
		gridC.gridy = 1;
		// do not resize buttons:
		gridC.weightx = 0;
		gridC.weighty = 0;
		JButton exitButton = new JButton("Quit");
		exitButton.setMnemonic(KeyEvent.VK_Q);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(exitButton);
		gridBagLayout.setConstraints(buttonPanel, gridC);
		frame.getContentPane().add(buttonPanel);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				frame.setVisible(false);
			}
		});
		frame.pack();
		frame.setVisible(true);
		do {
			try {
				Thread.sleep(10); // OK, this is ugly, but I don't mind today...
			} catch (InterruptedException e) {
				return false;
			}
		} while (frame.isVisible());

		frame.dispose();
		return true;
	}

}
