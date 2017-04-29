package me.james.discord.webhookcontroller;

import java.util.logging.*;
import javax.swing.*;

public class Start
{
    public static final Logger LOG = Logger.getLogger( "WebhookController" );
    public static ControllerFrame FRAME;

    public static void main( String[] args )
    {
        try
        {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch ( ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e )
        {
            WebhookUtils.displayStackTrace( e );
            e.printStackTrace();
        }
        LOG.setUseParentHandlers( false );
        LOG.addHandler( new Handler()
        {
            @Override
            public void publish( LogRecord record )
            {
                if ( getFormatter() == null )
                {
                    setFormatter( new SimpleFormatter() );
                }

                try
                {
                    String message = getFormatter().format( record );
                    if ( record.getLevel().intValue() >= Level.WARNING.intValue() )
                    {
                        System.err.write( message.getBytes() );
                    } else
                    {
                        System.out.write( message.getBytes() );
                    }
                } catch ( Exception exception )
                {
                    reportError( null, exception, ErrorManager.FORMAT_FAILURE );
                }

            }

            @Override
            public void close() throws SecurityException {}

            @Override
            public void flush() {}
        } );
        LOG.info( "Creating controller frame..." );
        FRAME = new ControllerFrame();
    }
}
