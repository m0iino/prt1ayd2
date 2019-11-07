/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twitter.utilidad;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kenia
 */
public class conexionDb {

    public static Connection getConn() throws ClassNotFoundException {
        Connection conn = null;
        try {

            String connString = "jdbc:mysql://db:3306/twitter?verifyServerCertificate=false&useSSL=false";
            String user = "root";
            String pass = "root";
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(connString, user, pass);
        } catch (SQLException ex) {
            Logger.getLogger(conexionDb.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Err -->" + ex.toString());
        }

        return conn;
    }
}
