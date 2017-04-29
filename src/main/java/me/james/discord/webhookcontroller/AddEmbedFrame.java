package me.james.discord.webhookcontroller;

import java.awt.*;
import javax.swing.*;

/**
 * Created by James on 4/28/2017.
 */
public class AddEmbedFrame extends JDialog
{
    private JTextField title = new JTextField();
    private JTextArea description = new JTextArea();
    private JTextField url = new JTextField();
    private JButton addBtn = new JButton( "OK" );
    private JButton colorBtn = new JButton( "Set color..." );
    private Color col = null;

    public AddEmbedFrame()
    {
        super( Start.FRAME, true );
        setSize( 340, 230 );
        setLocationRelativeTo( null );
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        setTitle( "Add Embed" );
        setResizable( false );
        setLayout( null );
        title.setSize( 250, 20 );
        title.setLocation( 75, 3 );
        url.setSize( 250, 20 );
        url.setLocation( 75, 25 );
        description.setSize( 250, 120 );
        description.setLocation( 75, 48 );
        description.setLineWrap( true );
        description.setFont( Font.getFont( "Arial" ) );
        JLabel titleLabel = new JLabel( "Title" );
        JLabel urlLabel = new JLabel( "URL" );
        JLabel descLabel = new JLabel( "Description" );
        titleLabel.setSize( 40, 20 );
        titleLabel.setLocation( 10, 3 );
        urlLabel.setSize( 60, 20 );
        urlLabel.setLocation( 10, 25 );
        descLabel.setSize( 60, 20 );
        descLabel.setLocation( 10, 45 );
        addBtn.setSize( 75, 26 );
        addBtn.setLocation( 250, 171 );
        colorBtn.setSize( 95, 26 );
        colorBtn.setLocation( 153, 171 );
        colorBtn.addActionListener( e -> col = JColorChooser.showDialog( null, "Choose a color", Color.RED ) );
        addBtn.addActionListener( e ->
        {
            if ( title.getText().isEmpty() || description.getText().isEmpty() )
                return;
            EmbedContent embed = new EmbedContent( title.getText(), description.getText(), col );
            if ( !url.getText().isEmpty() )
                embed.withURL( url.getText() );
            ControllerFrame.embeds.add( embed );
            dispose();
            new AddFieldFrame( embed );
        } );
        add( addBtn );
        add( colorBtn );
        add( title );
        add( description );
        add( url );
        add( titleLabel );
        add( urlLabel );
        add( descLabel );
        setVisible( true );
    }
}
