package me.james.discord.webhookcontroller;

import com.google.gson.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import org.apache.commons.lang3.exception.*;
import org.apache.http.*;
import org.apache.http.client.entity.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.*;
import org.apache.http.impl.client.*;
import org.apache.http.message.*;

public class WebhookUtils
{
    public static HttpResponse makeWebhookRequest( String url, String msg, boolean tts, String username ) throws IOException
    {
        if ( msg.length() < 0 || msg.length() > 2000 )
            return null;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost req = new HttpPost( url );
        ArrayList<NameValuePair> postData = new ArrayList<>();
        postData.add( new BasicNameValuePair( "content", msg ) );
        postData.add( new BasicNameValuePair( "tts", String.valueOf( tts ) ) );
        if ( username != null && !username.isEmpty() )
            postData.add( new BasicNameValuePair( "username", username ) );
        req.setEntity( new UrlEncodedFormEntity( postData ) );
        HttpResponse response = client.execute( req );
        Start.LOG.info( "Made webhook request (" + req.getMethod() + "), status: " + response.getStatusLine().toString() );
        if ( response.getStatusLine().getStatusCode() >= 300 )
            Start.LOG.warning( "Request returned non-OK status. (" + url + ")" );
        return response;
    }

    public static HttpResponse makeWebhookRequest( String url, String msg, boolean tts ) throws IOException
    {
        return makeWebhookRequest( url, msg, tts, "" );
    }

    public static HttpResponse makeWebhookRequest( String url, String msg, boolean tts, String username, EmbedContent[] content ) throws IOException
    {
        if ( msg.length() < 0 || msg.length() > 2000 )
            return null;
        JsonObject payloadJson = new JsonObject();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost req = new HttpPost( url );
        if ( !msg.isEmpty() )
            payloadJson.addProperty( "content", msg );
        if ( tts )
            payloadJson.addProperty( "tts", true );
        if ( username != null && !username.isEmpty() )
            payloadJson.addProperty( "username", username );
        // Embed content shit
        JsonArray embedJArray = new JsonArray();
        for ( EmbedContent embed : content )
            embedJArray.add( getEmbedJSON( embed ) );
        payloadJson.add( "embeds", embedJArray );
        String jsonRaw = new Gson().toJson( payloadJson );
        req.setEntity( new StringEntity( jsonRaw, ContentType.APPLICATION_JSON ) );
        HttpResponse response = client.execute( req );
        Start.LOG.info( "Made webhook request (" + req.getMethod() + "), status: " + response.getStatusLine().toString() );
        if ( response.getStatusLine().getStatusCode() >= 300 )
            Start.LOG.warning( "Request returned non-OK status. (" + url + ")" );
        return response;
    }

    private static JsonElement getEmbedJSON( EmbedContent embed )
    {
        int rgb = 0;
        if ( embed.getColor() != null )
        {
            rgb = embed.getColor().getRed();
            rgb = rgb << 8;
            rgb |= embed.getColor().getGreen();
            rgb = rgb << 8;
            rgb |= embed.getColor().getBlue();
        }
        JsonObject obj = new JsonObject();
        obj.addProperty( "title", embed.getTitle() );
        obj.addProperty( "description", embed.getDescription() );
        obj.addProperty( "type", "rich" );
        obj.addProperty( "url", embed.getURL() );
        if ( embed.getColor() != null )
            obj.addProperty( "color", rgb );
        JsonArray fieldsArray = new JsonArray();
        for ( EmbedField eField : embed.getEmbededFields() )
        {
            JsonObject fObj = new JsonObject();
            fObj.addProperty( "name", eField.getName() );
            fObj.addProperty( "value", eField.getValue() );
            fObj.addProperty( "inline", eField.isInline() );
            fieldsArray.add( fObj );
        }
        obj.add( "fields", fieldsArray );
        return obj;
    }

    public static HttpResponse makeWebhookRequest( String url, String msg, boolean tts, EmbedContent[] content ) throws IOException
    {
        return makeWebhookRequest( url, msg, tts, null, content );
    }

    public static void displayStackTrace( Exception e )
    {
        JOptionPane.showMessageDialog( Start.FRAME, ExceptionUtils.getStackTrace( e ), e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE );
    }
}
