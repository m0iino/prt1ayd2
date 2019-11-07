/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twitter.ws;

import com.twitter.metodo.logic_methods;
import com.twitter.obj.globales;
import com.twitter.obj.objTweet;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;

/**
 *
 * @author Kenia
 */
@Path("/post")
public class get_logic {

    public static final String ENCODING = ";charset=utf-8";
    JSONObject json = new JSONObject();
    globales go = new globales();

    @POST
    @Path("/getTweets/")
    @Consumes(MediaType.APPLICATION_JSON + ENCODING)
    @Produces(MediaType.APPLICATION_JSON + ENCODING)
    public Response getTweets(objTweet obj) {//variable
        try {
            if (go.key.contentEquals(obj.getKey())) {
                json = logic_methods.getTweets(obj);
            } else {
                json.put("error", true);
                json.put("msg", "No valida key");
            }
        } catch (Exception e) {
            json.put("error", true);
            json.put("msg", "No valida servicio");
        }
        return Response.status(Response.Status.ACCEPTED).header("Content-Type", "application/json;charset=UTF-8").entity(json.toJSONString()).type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/getTweetsUsuario/")
    @Consumes(MediaType.APPLICATION_JSON + ENCODING)
    @Produces(MediaType.APPLICATION_JSON + ENCODING)
    public Response getTweetsUsuario(objTweet obj) {//variable
        try {
            if (go.key.contentEquals(obj.getKey())) {
                json = logic_methods.getTweetsUsuario(obj);
            } else {
                json.put("error", true);
                json.put("msg", "No valida key");
            }
        } catch (Exception e) {
            json.put("error", true);
            json.put("msg", "No valida servicio");
        }
        return Response.status(Response.Status.ACCEPTED).header("Content-Type", "application/json;charset=UTF-8").entity(json.toJSONString()).type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/insertTweet/")
    @Consumes(MediaType.APPLICATION_JSON + ENCODING)
    @Produces(MediaType.APPLICATION_JSON + ENCODING)
    public Response insertTweet(objTweet obj) {//variable
        try {
            if (go.key.contentEquals(obj.getKey())) {
                json = logic_methods.insertTweet(obj);
            } else {
                json.put("error", true);
                json.put("msg", "No valida key");
            }
        } catch (Exception e) {
            json.put("error", true);
            json.put("msg", "No valida servicio");
        }
        return Response.status(Response.Status.ACCEPTED).header("Content-Type", "application/json;charset=UTF-8").entity(json.toJSONString()).type(MediaType.APPLICATION_JSON).build();
    }
}
