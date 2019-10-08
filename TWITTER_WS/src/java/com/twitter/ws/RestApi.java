/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twitter.ws;

import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

/**
 *
 * @author Kenia
 */
@ApplicationPath("ws")
public class RestApi extends Application{
    @Context ServletContext ctx;
}
