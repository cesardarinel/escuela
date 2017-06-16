/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesardarinel.escuela;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
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
    public void Conexion(String Cedula) throws IOException, KeyManagementException, NoSuchAlgorithmException {
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
        for (Element link : links) {
            if (link.text().contains("Encontrado")) {
                System.out.printf("%s \n", link.text());
               break;
            }else{
              System.out.printf("%s \n", doc.body());
            }
        }
        cookies = response.cookies();
    }
}
