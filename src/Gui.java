import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


public class Gui extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private Engine engine = new Engine();
	private MyButton[][] dugmici;
	private JButton nova;
	private JLabel vreme;
	private Timer timer;
	private int sec = 0;
	private int min = 0;
	
	public Gui(String title)
	{
		super(title);
		init();
	}
	
	private void init()
	{
		setNorth();
		setCenter();
		setWindow();
		
		setTimer();
		addListeners();
		refreshGui();
	}
	
	private void setNorth()
	{
		JPanel gore = new JPanel(new GridLayout(1, 3, 5, 5));
		gore.setSize(new Dimension(278, 40));
		
		JLabel vreme1 = new JLabel("");
		vreme1.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
		gore.add(vreme1);
		
		JPanel sever = new JPanel(new FlowLayout());
		
		nova = new JButton();
		nova.setPreferredSize(new Dimension(40, 40));
		nova.setBackground(Color.LIGHT_GRAY);
		nova.setIcon(new ImageIcon(getClass().getResource("Smile.png")));
		
		sever.add(nova);
		gore.add(sever);
		
		vreme = new JLabel("00:00");
		vreme.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
		gore.add(vreme);
		
		getContentPane().add(gore, BorderLayout.NORTH);
	}
		
	private void setCenter()
	{
		JPanel centar = new JPanel(new GridLayout(9, 9, 1, 1));
		dugmici = new MyButton[9][9];
		
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
			{
				dugmici[i][j] = new MyButton(i, j, false);
				dugmici[i][j].setPreferredSize(new Dimension(30, 30));
				dugmici[i][j].setBackground(Color.LIGHT_GRAY);
				
				centar.add(dugmici[i][j]);
			}
		
		getContentPane().add(centar, BorderLayout.CENTER);
	}
		
	private void setWindow()
	{
		setSize(270, 310);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		pack();
	}
	
	private void refreshGui()
	{
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
			{
				if (dugmici[i][j].isVidljivo())
				{
					if (engine.getTabla(i, j).getOznaka() == Oznaka.MINA)
						dugmici[i][j].setIcon(new ImageIcon(getClass().getResource("MINA.png")));
					else if (engine.getTabla(i, j).getOznaka() == Oznaka.BROJ)
						dugmici[i][j].setIcon(new ImageIcon(getClass().getResource(engine.getTabla(i, j).getBroj() + ".png")));
					else
						dugmici[i][j].setIcon(null);
						
					dugmici[i][j].setBackground(Color.WHITE);				
				}
				else dugmici[i][j].setBackground(Color.LIGHT_GRAY);
			}
		
		if (engine.daLiSiPobedio(dugmici))
		{
			timer.stop();
			nova.setIcon(new ImageIcon(getClass().getResource("SmileWin.png")));
			JOptionPane.showMessageDialog(null, "Pobedio si!", "Kraj igre!", 3);
		}
	}
	
	private void addListeners()
	{
		nova.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				resetVreme();
				timer.start();
				engine.init();
				setVidljivostOfAll(false);
				nova.setIcon(new ImageIcon(getClass().getResource("Smile.png")));
				refreshGui();
			} 
		});
		
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
			{
				dugmici[i][j].addMouseListener(new MouseListener()
				{
					
					public void mouseReleased(MouseEvent e)
					{
						MyButton dugme = (MyButton)e.getSource(); 
						int x, y;
						
						x = dugme.getI();
						y = dugme.getJ();
						
						if (SwingUtilities.isLeftMouseButton(e))
						{
							if (dugme.getIcon() == null)
							{
								if (engine.getTabla(x, y).getOznaka() == Oznaka.MINA)
								{
									timer.stop();
									setVidljivostOfAll(true);
									refreshGui();
									dugme.setBackground(Color.RED);
									nova.setIcon(new ImageIcon(getClass().getResource("SadSmile.png")));
									JOptionPane.showMessageDialog(null, "Izgubili ste!", "Kraj igre!", 0);									
									
								}
								else if (engine.getTabla(x, y).getOznaka() == Oznaka.BROJ)
								{
									dugme.setVidljivo(true);
									refreshGui();
								}
								else
								{
									engine.postaviVidljivost(x, y, dugmici);
									refreshGui();
								}		
							}
						}
						else if(SwingUtilities.isRightMouseButton(e))
						{
							if (!dugme.isVidljivo())
							{
								if (dugme.getIcon() == null)
									dugme.setIcon(new ImageIcon(getClass().getResource("flag.png")));
								else
									dugme.setIcon(null);
							}
					    }
						
					}
					
					public void mousePressed(MouseEvent e)
					{
						// TODO Auto-generated method stub
						
					}
					
					public void mouseExited(MouseEvent e)
					{
						// TODO Auto-generated method stub
						
					}
					
					public void mouseEntered(MouseEvent e)
					{
						// TODO Auto-generated method stub
						
					}

					public void mouseClicked(MouseEvent e)
					{
						// TODO Auto-generated method stub
						
					}
				});
				
			}
	}
	
	private void setVidljivostOfAll(boolean vidljivost)
	{
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
			{
				dugmici[i][j].setVidljivo(vidljivost);
				dugmici[i][j].setIcon(null);
			}
				
	}
	
	private void setTimer()
	{
		timer = new Timer(1000, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String s = "";
				
				if (min < 10) s += "0" + min + ":";
				else s += min + ":";
				
				if (sec < 10) s += "0" + sec;
				else s += sec;
				
				sec++;
				if (sec == 60) 
				{
					min++;
					sec = 0;
				}
 					
				vreme.setText(s);
			}
		});
		
		timer.start();
	}
	
	private void resetVreme()
	{
		sec = 0;
		min = 0;
	}
	
}
