package org.embulk.output.td.writer;

import org.embulk.output.td.MsgpackGZFileBuilder;
import org.embulk.spi.Column;
import org.embulk.spi.DataException;
import org.embulk.spi.PageReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.time.Instant;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestLongFieldWriter
{
    private static final String KEY_NAME = "key_name";

    @Mock
    private MsgpackGZFileBuilder builder;

    @Mock
    private PageReader reader;

    @Mock
    private Column column;

    private LongFieldWriter writer = new LongFieldWriter(KEY_NAME);

    @Test
    public void testWriteBooleanValue() throws IOException
    {
        // write 1 if the boolean value is true
        {
            when(reader.getBoolean(column)).thenReturn(true);
            writer.writeBooleanValue(builder, reader, column);
            verify(builder).writeLong(1);
        }
        // write 0 if the boolean value is false
        {
            when(reader.getBoolean(column)).thenReturn(false);
            writer.writeBooleanValue(builder, reader, column);
            verify(builder).writeLong(0);
        }
    }

    @Test
    public void testWriteLongValue() throws IOException
    {
        when(reader.getLong(column)).thenReturn(10L);
        writer.writeLongValue(builder, reader, column);
        verify(builder).writeLong(10);
    }

    @Test
    public void testWriteDoubleValue() throws IOException
    {
        when(reader.getDouble(column)).thenReturn(50.5);
        writer.writeDoubleValue(builder, reader, column);
        verify(builder).writeLong(50);
    }

    @Test
    public void testWriteStringValue() throws IOException
    {
        when(reader.getString(column)).thenReturn("100");
        writer.writeStringValue(builder, reader, column);
        verify(builder).writeLong(100);
    }

    @Test
    public void testWriteTimestampValue() throws IOException
    {
        when(reader.getTimestampInstant(column)).thenReturn(Instant.ofEpochSecond(200));
        writer.writeTimestampValue(builder, reader, column);
        verify(builder).writeLong(200);
    }

    @Test(expected = DataException.class)
    public void testWriteJsonValue()
    {
        writer.writeJsonValue(builder, reader, column);
    }
}
