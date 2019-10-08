/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twitter.timeline;

import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import org.omnifaces.util.Faces;

@SessionScoped
@ManagedBean(name = "MBLogin")
public class MBLogin implements Serializable {

    private objUsuario usuario = new objUsuario();
    private FacesMessage growl = null;
    private String usr;
    private String pas;

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPas() {
        return pas;
    }

    public void setPas(String pas) {
        this.pas = pas;
    }

    public objUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(objUsuario usuario) {
        this.usuario = usuario;
    }

    public MBLogin() {
    }

    public void ingresar() throws IOException {

        usuario.setNombre(usr);
        usuario.setPass(pas);
        usuario.setUsuario(usr);
        Faces.getExternalContext().getFlash().setKeepMessages(true);
        Faces.redirect("index.jsf");

    }

}
