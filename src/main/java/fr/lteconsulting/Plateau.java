package fr.lteconsulting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;

public class Plateau
{
	private Piece[][] damier;
	private int largeur;
	private int hauteur;

	private final static String NOM_FICHIER = "plateau.data";

	private final static int MARQUEUR = 0x3f3f3f3f;

	// Lire le fichier NOM_FICHER
	// et créer une instance de Plateau avec les pieces
	public static Plateau chargerPlateau()
	{
		// VERIFIER SI LE FICHIER EXISTE
		// S'IL N'EXISTE PAS ON RETOURNE null
		File file = new File( NOM_FICHIER );
		if( !file.exists() )
			return null;

		if( "!".equals( Saisie.saisie( "Une partie précédente a été sauvegardée, voulez vous l'utiliser? (! pour non, autre chose sinon" ) ) )
			return null;

		FileInputStream fis = null;

		try
		{
			// OUVRIR LE FICHIER (FileInputStream)
			fis = new FileInputStream( file );

			// ON CREE UN ObjectInputStream
			FileDataReader ois = new FileDataReader( fis );

			// LIRE LE FICHIER ET RECONSTRUIRE LE PLATEAU
			// (SI LE MARQUEUR != MARQUEUR, retourner null)
			int marqueur = ois.readInt();
			if( marqueur != MARQUEUR )
				return null;

			// (SI LA VERSION != 0, retourner null)
			int version = ois.readInt();
			if( version != 0 )
				return null;

			// LIRE LARGEUR ET HAUTEUR
			int largeur = ois.readInt();
			int hauteur = ois.readInt();

			// CREER LE PLATEAU
			Plateau plateau = new Plateau( largeur, hauteur );

			// ON LIT LES PIECES DANS L'ORDRE
			for( int j = 0; j < hauteur; j++ )
			{
				for( int i = 0; i < largeur; i++ )
				{
					byte b = ois.readByte();
					if( b == 0 )
						continue;

					Piece piece = new Piece( (char) b );
					plateau.placerPiece( piece, i, j );
				}
			}

			// RETOURNER LE PLATEAU
			return plateau;
		}
		catch( Exception e )
		{
			return null;
		}
		finally
		{
			try
			{
				if( fis != null )
					fis.close();
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
		}
	}

	public static void enregistrerPlateau( Plateau plateau )
	{
		try
		{
			FileOutputStream fos = new FileOutputStream( new File( NOM_FICHIER ) );
			FileDataWriter oos = new FileDataWriter( fos );

			oos.writeInt( MARQUEUR );
			oos.writeInt( 0 );
			oos.writeInt( plateau.largeur );
			oos.writeInt( plateau.hauteur );
			for( int j = 0; j < plateau.hauteur; j++ )
			{
				for( int i = 0; i < plateau.largeur; i++ )
				{
					Piece piece = plateau.damier[i][j];
					if( piece == null )
						oos.writeByte( 0 );
					else
						oos.writeByte( piece.getDisplayChar() );
				}
			}

			fos.close();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		// ENREGISTRER LES DONNEES DANS NOTRE FORMAT
	}

	public Plateau( int largeur, int hauteur )
	{
		this.largeur = largeur;
		this.hauteur = hauteur;

		damier = new Piece[largeur][hauteur];
	}

	public void placerPiece( Piece piece, int x, int y )
	{
		damier[x][y] = piece;
	}

	public Piece getPiece( int x, int y )
	{
		return damier[x][y];
	}

	public int getLargeur()
	{
		return largeur;
	}

	public int getHauteur()
	{
		return hauteur;
	}

	public void afficher()
	{
		for( int j = 0; j < hauteur; j++ )
		{
			String ligne = "";
			for( int i = 0; i < largeur; i++ )
			{
				Piece piece = damier[i][j];
				if( piece == null )
					ligne += ".";
				else
					ligne += piece.getDisplayChar();
			}
			System.out.println( ligne );
		}
	}

	public void initialiserHasard()
	{
		// parcourir toutes les cases
		// parfois on va ne rien mettre
		// parfois on va mettre un O
		// parfois on va mettre un X
		for( int i = 0; i < largeur; i++ )
		{
			for( int j = 0; j < hauteur; j++ )
			{
				Random random = new Random();
				int de = random.nextInt( 3 );
				switch( de )
				{
					case 1:
						damier[i][j] = new Piece( 'O' );
						break;

					case 2:
						damier[i][j] = new Piece( 'X' );
						break;
				}
			}
		}
	}
}
