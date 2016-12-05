package fr.lteconsulting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class Display extends JPanel
{
	abstract protected void onUpdate();

	abstract protected void onMouseClicked( int x, int y );

	abstract protected void onPaint( Graphics g );

	private static final long serialVersionUID = 7195359356261678183L;

	private Timer timer = new Timer( 1000 / 25, ( e ) -> {
		onUpdate();
		repaint();
	} );

	public void display()
	{
		JFrame frame = new JFrame( "Display panel" );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.add( this );
		frame.pack();
		frame.setVisible( true );
	}

	public Display()
	{
		setBackground( Color.BLACK );
		setPreferredSize( new Dimension( 400, 300 ) );
		setDoubleBuffered( true );

		timer.start();

		addMouseListener( new MouseAdapter()
		{
			@Override
			public void mousePressed( MouseEvent e )
			{
				onMouseClicked( e.getX(), e.getY() );
			}
		} );
	}

	@Override
	protected void paintComponent( Graphics g )
	{
		super.paintComponent( g );

		onPaint( g );
	}
}
