/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesardarinel.escuela;

import java.beans.Statement;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;

/**
 *
 * @author ACAP1831
 */
public class Main {

    public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException {
        System.out.println("init");
        
        Nomina info = new Nomina();
       // info.Conexion("031-0206139-1");
 //       System.out.println(CedulaUtil.validarCedula("470-0-1"));
            for (int b = 0; b < 1000; b++) {
                System.out.println(b);
              for (int a = 0; a < 10; a++) {
                  System.out.println(a);
                for (int i = 0; i < 10000000; i++) {
                    String c=String.format("%03d",b)+"-"+String.format("%07d",i)+"-"+Integer.toString(a);
                    if (CedulaUtil.validarCedula(c)){
            //            System.out.println(c);
                        info.Conexion(c);
                    }
                }
            }
        }
    }
}
