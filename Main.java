/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesardarinel.escuela;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author ACAP1831
 */
public class Main {

    public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        System.out.println("init");
  //    Nomina info = new Nomina();
  //      info.Conexion("031-0206139-1");
        System.out.println(CedulaUtil.validarCedula("470-0-1"));
            for (int b = 0; b < 1000; b++) {
              for (int a = 0; a < 10; a++) {
                for (int i = 0; i < 10000000; i++) {
                    String c=Integer.toString(b)+"-"+Integer.toString(i)+"-"+Integer.toString(a);
                    if (CedulaUtil.validarCedula(c)){
                        System.out.println(c);
                    //   info.Conexion(c);
                    }
                }
            }
        }
    }
}
