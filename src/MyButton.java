import javax.swing.JButton;

public class MyButton extends JButton
{
	private static final long serialVersionUID = 12L;

	private int i;
	private int j;
	private boolean vidljivo;
	
	public MyButton(int i, int j, boolean vidljivo)
	{
		this.i = i;
		this.j = j;
		this.vidljivo = vidljivo;
	}

	public boolean isVidljivo()
	{
		return vidljivo;
	}

	public void setVidljivo(boolean vidljivo)
	{
		this.vidljivo = vidljivo;
	}

	public int getI()
	{
		return i;
	}

	public int getJ()
	{
		return j;
	}
	
	
}
