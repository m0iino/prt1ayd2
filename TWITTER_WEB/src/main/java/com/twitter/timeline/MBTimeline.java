/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twitter.timeline;

import com.twitter.utils.objConstantes;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Kenia
 */
@ManagedBean(name = "MBTimeline")
@SessionScoped
@ViewScoped
public class MBTimeline implements Serializable {

    private objUsuario usuario = new objUsuario();
    private FacesMessage msg = null;
    private FacesMessage msgP = null;
    private List<objTweet> lstTweets = new ArrayList();
    private String tablaUrl;
    private String username;
    private String contenido;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public FacesMessage getMsg() {
        return msg;
    }

    public void setMsg(FacesMessage msg) {
        this.msg = msg;
    }

    public FacesMessage getMsgP() {
        return msgP;
    }

    public void setMsgP(FacesMessage msgP) {
        this.msgP = msgP;
    }

    public List<objTweet> getLstTweets() {
        return lstTweets;
    }

    public void setLstTweets(List<objTweet> lstTweets) {
        this.lstTweets = lstTweets;
    }

    public MBTimeline() {
    }

    @PostConstruct
    public void postCon() {
        /* if ((!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().isEmpty()) && (FacesContext.getCurrentInstance().getExternalContext().getSessionMap() != null)) {
             usuario = (objUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        }*/
        final int timeout = 400000;//1200000;//300,000 5 minutos
        String output = "";
        try {

            objConstantes cons = new objConstantes();

            Locale locale = Locale.getDefault();
            ResourceBundle datos = ResourceBundle.getBundle("LogIn", locale);

            String urlmp = datos.getString("url");
            urlmp += "getTweets/";

            String json = "{\n"
                    //  + "  \"token\": \"" + usuario.getToken() + "\",\n"
                    + "  \"key\": \"" + cons.getKey() + "\"\n"
                    + "}";

            URL targetUrl = new URL(urlmp);

            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");

            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(json.getBytes("UTF-8"));
            outputStream.flush();

            if (httpConnection.getResponseCode() != 202) {
                msgP = new FacesMessage(FacesMessage.SEVERITY_INFO, "Timeline", "No es posible mostrar los tweets");
                FacesContext.getCurrentInstance().addMessage(null, msgP);

                throw new RuntimeException("Failed : HTTP error code : "
                        + httpConnection.getResponseCode());

            } else {
                //OBTENER EL RESPONSE EL UTF-8
                BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                        (httpConnection.getInputStream()), "UTF8"));

                output = responseBuffer.readLine();

                JSONObject obj = new JSONObject(output);

                String msgPs = obj.getString("msg");
                boolean error = obj.getBoolean("error");

                if (!error) {
                    msgP = new FacesMessage(FacesMessage.SEVERITY_INFO, "Timeline", msgPs);
                    FacesContext.getCurrentInstance().addMessage(null, msgP);
                    JSONArray tweets = obj.getJSONArray("detalle");
                    for (int i = 0; i < tweets.length(); i++) {
                        JSONObject producto = tweets.getJSONObject(i);
                        objTweet objTweet = new objTweet();
                        objTweet.setUser(producto.getString("username"));
                        objTweet.setContenido(producto.getString("contenido"));
                        //   objTweet.setFecha(producto.getString("fecha"));
                        //   objTweet.setHora(producto.getString("hora"));

                        lstTweets.add(objTweet);
                    }

                } else {
                    msgP = new FacesMessage(FacesMessage.SEVERITY_WARN, "Timeline", msgPs);
                    FacesContext.getCurrentInstance().addMessage(null, msgP);
                }

            }

            httpConnection.disconnect();

        } catch (Exception ex) {
            ex.printStackTrace();
            msgP = new FacesMessage(FacesMessage.SEVERITY_WARN, "Timeline", "No es posible mostrar el timeline");
            FacesContext.getCurrentInstance().addMessage(null, msgP);
        }
    }

    public void modificar(objTweet tip) {

        String output;

        try {

            objConstantes cons = new objConstantes();

            Locale locale = Locale.getDefault();
            ResourceBundle datos = ResourceBundle.getBundle("LogIn", locale);

            String urlmp = datos.getString("url");
            urlmp += "insertTweet/";
            URL targetUrl = new URL(urlmp);

            //Request del servicio
            String json = "{\n"
                    + "  \"key\": \"" + cons.getKey() + "\",\n"
                    + "\"user\": \"" + username + "\",\n"
                    + "\"tweet\": \"" + contenido + "\"}";

            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");

            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(json.getBytes("UTF-8"));
            outputStream.flush();

            if (httpConnection.getResponseCode() != 202) {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No es posible actualizar la información");
                FacesContext.getCurrentInstance().addMessage("msg", msg);

                throw new RuntimeException("Failed : HTTP error code : "
                        + httpConnection.getResponseCode());

            } else {
                BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                        (httpConnection.getInputStream()), "UTF8"));

                output = responseBuffer.readLine();

                JSONObject obj = new JSONObject(output);

                String msgs = obj.getString("msg");
                boolean error = obj.getBoolean("error");

                if (!error) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Datos actualizados con exito");
                    FacesContext.getCurrentInstance().addMessage("msg", msg);

                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", msgs);
                    FacesContext.getCurrentInstance().addMessage("msg", msg);
                }

            }

            httpConnection.disconnect();

        } catch (Exception ex) {
            ex.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No es posible actualizar la información");
            FacesContext.getCurrentInstance().addMessage("msg", msg);
        }

    }

}
