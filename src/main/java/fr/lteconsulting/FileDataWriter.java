package fr.lteconsulting;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class FileDataWriter
{
	private final OutputStream stream;

	public FileDataWriter( OutputStream stream )
	{
		this.stream = stream;
	}

	public void writeInt( int value ) throws IOException
	{
		ByteBuffer buffer = ByteBuffer.allocate( 4 );

		buffer.putInt( value );

		stream.write( buffer.array() );
	}

	public void writeByte( int value ) throws IOException
	{
		ByteBuffer buffer = ByteBuffer.allocate( 1 );

		buffer.put( (byte) (value & 0xff) );

		stream.write( buffer.array() );
	}
}
