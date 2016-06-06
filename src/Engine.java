import java.util.Random;

public class Engine
{
	private Element[][] tabla;
	
	public Engine()
	{
		init();
	}
	
	public void init()
	{
		tabla = new Element[9][9];
		
		napraviAndSetPrazno();
		setRandomMine();
		setBrojevi();
	}
	
	private void napraviAndSetPrazno()
	{
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				tabla[i][j] = new Element(Oznaka.PRAZNO);
	}
	
	private void setRandomMine()
	{
		int i = 0, x, y; 
		Random rand = new Random();
		
		while (i < 10)
		{
			x = rand.nextInt(9);
			y = rand.nextInt(9);
			
			if (tabla[x][y].getOznaka() == Oznaka.PRAZNO)
			{
				tabla[x][y].setOznaka(Oznaka.MINA);
				i++;
			}
		}		
	}

	public Element getTabla(int i, int j)
	{
		return tabla[i][j];
	}
	
	private void setBrojevi()
	{
		
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
			{
				if (tabla[i][j].getOznaka() != Oznaka.MINA)
				{
					int br = 0;
					
					if (i - 1 >= 0 && tabla[i - 1][j].getOznaka() == Oznaka.MINA) br++;
					if (i + 1 < 9  && tabla[i + 1][j].getOznaka() == Oznaka.MINA) br++;
					if (j - 1 >= 0 && tabla[i][j - 1].getOznaka() == Oznaka.MINA) br++;
					if (j + 1 < 9  && tabla[i][j + 1].getOznaka() == Oznaka.MINA) br++;
					
					if (i - 1 >= 0 && j - 1 >= 0 && tabla[i - 1][j - 1].getOznaka() == Oznaka.MINA) br++;
					if (i - 1 >= 0 && j + 1 < 9  && tabla[i - 1][j + 1].getOznaka() == Oznaka.MINA) br++;
					if (i + 1 < 9  && j - 1 >= 0 && tabla[i + 1][j - 1].getOznaka() == Oznaka.MINA) br++;
					if (i + 1 < 9  && j + 1 < 9  && tabla[i + 1][j + 1].getOznaka() == Oznaka.MINA) br++;
					
					if (br > 0)
					{
						tabla[i][j].setOznaka(Oznaka.BROJ);
						tabla[i][j].setBroj(br);
					}
				}
			}
	}

	public void postaviVidljivost(int i, int j, MyButton[][] dugmici)
	{
		dugmici[i][j].setVidljivo(true);
		
		if (tabla[i][j].getOznaka() == Oznaka.PRAZNO)
		{
			if (i - 1 >= 0 && !dugmici[i - 1][j].isVidljivo()) postaviVidljivost(i - 1, j, dugmici);
			if (i + 1 < 9  && !dugmici[i + 1][j].isVidljivo()) postaviVidljivost(i + 1, j, dugmici);
			if (j - 1 >= 0 && !dugmici[i][j - 1].isVidljivo()) postaviVidljivost(i, j - 1, dugmici);
			if (j + 1 < 9  && !dugmici[i][j + 1].isVidljivo()) postaviVidljivost(i, j + 1, dugmici);
			
			
			if (i - 1 >= 0 && j - 1 >= 0 && !dugmici[i - 1][j - 1].isVidljivo()) postaviVidljivost(i - 1, j - 1, dugmici);
			if (i - 1 >= 0 && j + 1 < 9  && !dugmici[i - 1][j + 1].isVidljivo()) postaviVidljivost(i - 1, j + 1, dugmici);
			if (i + 1 < 9  && j - 1 >= 0 && !dugmici[i + 1][j - 1].isVidljivo()) postaviVidljivost(i + 1, j - 1, dugmici);
			if (i + 1 < 9  && j + 1 < 9  && !dugmici[i + 1][j + 1].isVidljivo()) postaviVidljivost(i + 1, j + 1, dugmici);
		}
	}
	
	public boolean daLiSiPobedio(MyButton[][] dugmici)
	{
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
			{
				if ((!dugmici[i][j].isVidljivo() && tabla[i][j].getOznaka() != Oznaka.MINA) || 
						(dugmici[i][j].isVidljivo() && tabla[i][j].getOznaka() == Oznaka.MINA))
					return false;
			}
		
		return true;
	}
}
