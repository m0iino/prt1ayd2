/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twitter.metodo;

import com.twitter.obj.globales;
import com.twitter.obj.objTweet;
import com.twitter.utilidad.conexionDb;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

/**
 *
 * @author Kenia
 */
public class logic_methods {

    public static final String ENCODING = ";charset=utf-8";
    JSONObject json = new JSONObject();
    globales go = new globales();

    public static JSONObject getTweets(objTweet obj) throws SQLException, ParseException, ClassNotFoundException {

        Connection Conn = null;
        JSONObject json = new JSONObject();
        JSONObject objJson = new JSONObject();
        List<JSONObject> lstjsonx = new ArrayList<>();

        Statement prstm = null;
        ResultSet result = null;
        String query = "";

        try {

            Conn = conexionDb.getConn();

            /*  query = "select UID_USER from LOGIN_TOKEN where TOKEN='" + obj.getToken() + "'";
            //System.out.println("valida token => " + query);
            prstm = Conn.createStatement();
            result = prstm.executeQuery(query);
            String top = "";
            while (result.next()) {*/
            query = "SELECT USERNAME USERNAME,\n"
                    + "CONTENIDO CONTENIDO,\n"
                    + "ID ID,\n"
                    + "FECHA FECHA,\n"
                    + "HORA HORA\n"
                    + "FROM tweet\n"
                    + "ORDER BY ID";

            prstm = Conn.createStatement();
            result = prstm.executeQuery(query);
            while (result.next()) {

                objJson.put("username", result.getString("USERNAME"));
                objJson.put("contenido", result.getString("CONTENIDO"));

                lstjsonx.add(objJson);
                objJson = new JSONObject();
            }

            json.put("detalle", lstjsonx);
            json.put("error", false);
            json.put("msg", "listado de tweets");

            //    }
            result.close();
            prstm.close();
        } catch (SQLException e) {
            json.put("error", true);
            json.put("msg", "Ocurrió un error en la aplicación");
        } finally {
            try {
                Conn.close();
            } catch (SQLException e) {
                Conn = null;
            }
            result = null;
            prstm = null;
        }
        return json;
    }

    public static JSONObject insertTweet(objTweet obj) throws SQLException, ClassNotFoundException {

        Connection Conn = null;
        JSONObject json = new JSONObject();

        Statement prstm = null;
        Statement prstm2 = null;
        ResultSet result = null;
        int result2 = 0;
        String query = "";
        String query2 = "";
        String usuario = "";
        try {

            Conn = conexionDb.getConn();

            query2 = "Insert into tweet (\n"
                    + "CONTENIDO,\n"
                    + "USERNAME,\n"
                    + "FECHA,\n"
                    + "HORA) \n"
                    + "values (\n"
                    + "'" + obj.getTweet() + "',\n"
                    + "'" + obj.getUser() + "',\n"
                    + "sysdate(),\n"
                    + "sysdate())";

            System.out.println("inserta tweet => " + query2);
            prstm2 = Conn.createStatement();
            result2 = prstm2.executeUpdate(query2);
            if (result2 < 1) {
                json.put("error", true);
                json.put("msg", "Error al actualizar");
            } else {
                json.put("error", false);
                json.put("msg", "Tweet publicado");
            }

            result.close();
            prstm.close();

        } catch (SQLException e) {
            json.put("error", true);
            json.put("msg", "Ocurrió un error en la aplicación");
        } finally {
            try {
                Conn.close();
            } catch (SQLException e) {
                Conn = null;
            }
            result = null;
            prstm = null;
             return json;
        }
       
    }

}
