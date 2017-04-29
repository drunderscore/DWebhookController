package me.james.discord.webhookcontroller;

import java.awt.*;
import java.util.*;

class EmbedField
{
    private String name, value;
    private boolean inline;

    EmbedField( String name, String value, boolean inline )
    {
        this.name = name;
        this.value = value;
        this.inline = inline;
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }

    public boolean isInline()
    {
        return inline;
    }
}

public class EmbedContent
{
    private String title, desc;
    private Color col;
    private ArrayList<EmbedField> fields = new ArrayList<>();
    private String url;

    public EmbedContent( String title, String desc, Color col )
    {
        this.title = title;
        this.desc = desc;
        this.col = col;
    }

    public EmbedContent withURL( String url )
    {
        this.url = url;
        return this;
    }

    public EmbedContent appendField( String name, String value, boolean inline )
    {
        fields.add( new EmbedField( name, value, inline ) );
        return this;
    }

    public EmbedField[] getEmbededFields()
    {
        return fields.toArray( new EmbedField[fields.size()] );
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return desc;
    }

    public Color getColor()
    {
        return col;
    }

    public String getURL()
    {
        return url;
    }
}