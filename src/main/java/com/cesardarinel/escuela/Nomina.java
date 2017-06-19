/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesardarinel.escuela;

import com.cesardarinel.entidades.Profesor;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author ACAP1831
 */
public class Nomina {

    String requestUrl = "http://forms.minerd.gob.do/consnomina/ConsRrhhNominas.aspx";
    String useragent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36";

    private void proxy() throws KeyManagementException, NoSuchAlgorithmException {
        String proxyHost = "acapsrv11d";
        String proxyPort = "3128";
        System.setProperty("http.proxyHost", proxyHost);
        System.setProperty("http.proxyPort", proxyPort);
        System.setProperty("https.proxyHost", proxyHost);
        System.setProperty("https.proxyPort", proxyPort);
        System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1");
        enableSSLSocket();
    }

    private void enableSSLSocket() throws KeyManagementException, NoSuchAlgorithmException {
        HttpsURLConnection.setDefaultHostnameVerifier((String hostname, SSLSession session) -> true);
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, new X509TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
    }

    /**
     *
     * @throws IOException
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */
    public void Conexion(String Cedula) throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException {
        proxy();
        Connection.Response response = Jsoup.connect(requestUrl)
                .userAgent(useragent)
                .method(Connection.Method.GET)
                .execute();
        Document doc = Jsoup.parse(response.body());

        Map<String, String> allFields = new HashMap<>();
        allFields.put("__VIEWSTATE", doc.getElementById("__VIEWSTATE").attr("value"));
        allFields.put("__VIEWSTATEGENERATOR", doc.getElementById("__VIEWSTATEGENERATOR").attr("value"));
        allFields.put("__EVENTVALIDATION", doc.getElementById("__EVENTVALIDATION").attr("value"));
        allFields.put("buspers_cedulan", Cedula);
        allFields.put("buscar", doc.getElementById("buscar").attr("value"));

        Map<String, String> cookies = response.cookies();
        response = Jsoup.connect(requestUrl)
                .userAgent(useragent)
                .data(allFields)
                .followRedirects(false)
                .cookies(cookies)
                .method(Connection.Method.POST)
                .execute();

        doc = Jsoup.parse(response.body());
        Elements links = doc.select("h4");
        //  for (Element link : links) {
        if (links.get(0).text().contains("Encontrado")) {
            //.out.printf("%s \n", links.get(0).text());
        } else {
            //System.out.printf("%s \n", doc.body());
            Elements info = doc.select("td");
            Profesor nuevoProfe = new Profesor();
            for (Element infos : info) {
                if (infos.text().contains("Cédula")) {
                    //.out.printf("%s \n", infos.nextElementSibling().text());
                    nuevoProfe.setCedula(infos.nextElementSibling().text());
                }
                if (infos.text().contains("Nombre")) {
                    //.out.printf("%s \n", infos.nextElementSibling().text());
                    nuevoProfe.setNombre(infos.nextElementSibling().text());
                }
                if (infos.text().contains("Apellidos")) {
                    //.out.printf("%s \n", infos.nextElementSibling().text());
                    nuevoProfe.setApellidos(infos.nextElementSibling().text());
                }
                if (infos.text().contains("Fecha")) {
                    //.out.printf("%s \n", infos.nextElementSibling().text());

                    nuevoProfe.setFechadeIngreso(infos.nextElementSibling().text());
                }
                if (infos.text().contains("Título")) {
                    //.out.printf("%s \n", infos.nextElementSibling().text());
                    nuevoProfe.setTitulo(infos.nextElementSibling().text());
                }
                if (infos.text().contains("Cuenta")) {
                    //.out.printf("%s \n", infos.nextElementSibling().text());
                    nuevoProfe.setCuentaBancaria(infos.nextElementSibling().text());
                }
                if (infos.text().contains("Empleado")) {
                    //.out.printf("%s \n", infos.nextElementSibling().text());
                    nuevoProfe.setEmpleadodeCarrera(infos.nextElementSibling().text());
                }
                if (infos.text().contains("Asignado")) {
                    //.out.printf("%s \n", infos.nextElementSibling().text());
                    nuevoProfe.setAsignadoa(infos.nextElementSibling().text());
                    break;
                }
            }

            try {

                // create a mysql database connection
                String myDriver = "com.mysql.jdbc.Driver";
                String myUrl = "jdbc:mysql://localhost/Escuela";
                Class.forName(myDriver);
                java.sql.Connection conn = DriverManager.getConnection(myUrl, "root", "");

                // the mysql insert statement
                String query = " insert into Profesor ( Cedula ,  Nombre ,  Apellido ,  FechadeIngreso ,  Titulo ,  CuentaBancaria ,  EmpleadodeCarrera ,  Asignadoa )"
                        + " values (?, ?, ?, ?, ?, ?, ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, nuevoProfe.getCedula());
                preparedStmt.setString(2, nuevoProfe.getNombre());
                preparedStmt.setString(3, nuevoProfe.getApellidos());
                preparedStmt.setString(4, nuevoProfe.getFechadeIngreso());
                preparedStmt.setString(5, nuevoProfe.getTitulo());
                preparedStmt.setString(6, nuevoProfe.getCuentaBancaria());
                preparedStmt.setString(7, nuevoProfe.getEmpleadodeCarrera());
                preparedStmt.setString(8, nuevoProfe.getAsignadoa());
                // execute the preparedstatement
                preparedStmt.execute();

                conn.close();
            } catch (Exception e) {
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }
        }
    }
}
