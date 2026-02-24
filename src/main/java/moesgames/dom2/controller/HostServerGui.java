package moesgames.dom2.controller;

import java.awt.*;
import java.io.*;

import javax.swing.*;

public class HostServerGui extends JFrame {
	ServerWithConnections server;

	public HostServerGui() throws HeadlessException {
		super();
		server = new ServerWithConnections(2002);
		server.setGui(this);
		server.start();
		
		setVisible(true);
		setTitle("host server for games");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				server.shutdown();
			}
		});
	}

	public class ServerPanel extends JPanel {

		public ServerPanel() {
			super();
			
			setLayout(new BorderLayout());
//			add(new JComponent() {
//				@Override
//				public void paint(Graphics g) {
//					g.drawString(System.out.toString(), 14, 24);
//					super.paint(g);
//				}
//			}, BorderLayout.CENTER);
			JTextArea ta = new JTextArea();
	        ta.setText(server.getOutput());


	        add( new JScrollPane( ta ),BorderLayout.CENTER);

	        pack();setVisible(true);
		}

	}

	public void refresh() {
		setContentPane(new ServerPanel());
		setSize(350, 150);
	}

}
