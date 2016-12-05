package fr.lteconsulting;

public class Joueur
{
	private String name;
	private char displayChar;

	public Joueur( String name, char displayChar )
	{
		this.name = name;
		this.displayChar = displayChar;
	}

	public String getName()
	{
		return name;
	}
	
	public char getDisplayChar()
	{
		return displayChar;
	}
}
