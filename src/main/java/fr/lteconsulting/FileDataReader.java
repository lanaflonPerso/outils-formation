package fr.lteconsulting;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class FileDataReader
{
	private final InputStream stream;

	public FileDataReader( InputStream stream )
	{
		this.stream = stream;
	}

	public int readInt() throws IOException
	{
		byte[] buffer = new byte[4];

		int nbRead = stream.read( buffer );

		if( nbRead != 4 )
			throw new RuntimeException( "Cannot read 4 bytes from the input stream!" );

		return ByteBuffer.wrap( buffer ).getInt();
	}

	public byte readByte() throws IOException
	{
		byte[] buffer = new byte[1];

		int nbRead = stream.read( buffer );

		if( nbRead != 1 )
			throw new RuntimeException( "Cannot read 1 byte from the input stream!" );

		return buffer[0];
	}
}
