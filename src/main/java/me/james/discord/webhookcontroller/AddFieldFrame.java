package me.james.discord.webhookcontroller;

import java.awt.*;
import javax.swing.*;

/**
 * Created by James on 4/28/2017.
 */
public class AddFieldFrame extends JDialog
{
    private JTextField name = new JTextField();
    private JTextArea value = new JTextArea();
    private JCheckBox inline = new JCheckBox( "Inline" );
    private JButton addBtn = new JButton( "OK" );

    public AddFieldFrame( EmbedContent embed )
    {
        super( Start.FRAME, true );
        setSize( 340, 210 );
        setLocationRelativeTo( null );
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        setTitle( "Add Embed Field" );
        setResizable( false );
        setLayout( null );
        name.setSize( 250, 20 );
        name.setLocation( 75, 3 );
        value.setSize( 250, 120 );
        value.setLocation( 75, 25 );
        value.setLineWrap( true );
        value.setFont( Font.getFont( "Arial" ) );
        inline.setSize( 75, 20 );
        inline.setLocation( 75, 145 );
        JLabel nameLabel = new JLabel( "Name" );
        JLabel valueLabel = new JLabel( "Value" );
        nameLabel.setSize( 40, 20 );
        nameLabel.setLocation( 10, 3 );
        valueLabel.setSize( 60, 20 );
        valueLabel.setLocation( 10, 25 );
        addBtn.setSize( 75, 26 );
        addBtn.setLocation( 250, 151 );
        addBtn.addActionListener( e ->
        {
            if ( name.getText().isEmpty() || value.getText().isEmpty() )
                return;
            embed.appendField( name.getText(), value.getText(), inline.isSelected() );
            dispose();
            new AddFieldFrame( embed );
        } );
        add( addBtn );
        add( name );
        add( value );
        add( inline );
        add( nameLabel );
        add( valueLabel );
        setVisible( true );
    }
}
