package com.webServices.rutas.util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.webServices.rutas.model.Parada;

import org.springframework.data.geo.Point;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Gpx {
    public void generarGpx(List<Point> puntosPrueba) throws IOException, ParserConfigurationException {
        String lat=null,lon=null,URI="C:/Users/Administrador/Desktop/Para Rutas Mapeo/Transcisa7.gpx";
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        document = implementation.createDocument(null, "gpx", null);
        for(int i=0;i<puntosPrueba.size();i++){
            lat=String.valueOf(puntosPrueba.get(i).getX());
            lon=String.valueOf(puntosPrueba.get(i).getY());
            try{
                
            
            Element tpt  = document.createElement("trkpt");
            document.getDocumentElement().appendChild(tpt); 
            
            document.setXmlVersion("1.0"); 
            tpt.setAttribute("lat", lat + "");
            tpt.setAttribute("lon", lon + "");
	         }catch(Exception e){
	             System.err.println("Error");
	         }
         }
        guardaConFormato(document, URI);
         
    }
     
     public static void guardaConFormato(Document document, String URI) throws IOException{
        
            try {
                TransformerFactory transFact = TransformerFactory.newInstance();

                transFact.setAttribute("indent-number", new Integer(3));
                Transformer trans = transFact.newTransformer();
                trans.setOutputProperty(OutputKeys.INDENT, "yes");
                trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

                //Hacemos la transformaci�n
                StringWriter sw = new StringWriter();
                StreamResult sr = new StreamResult(sw);
                DOMSource domSource = new DOMSource(document);
                trans.transform(domSource, sr);

                //Mostrar informaci�n a guardar por consola (opcional)
                //Result console= new StreamResult(System.out);
                //trans.transform(domSource, console);
                try {
                    //Creamos fichero para escribir en modo texto
                    System.out.println("url: " + URI);
                    PrintWriter writer = new PrintWriter(new FileWriter(URI));

                    //Escribimos todo el �rbol en el fichero
                    writer.println(sw.toString());
                   
                    //Cerramos el fichero
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
            
            
        }
     
     public static Map<String, Object> decodeGPX(File is){
    	List<Point> ruta = new ArrayList<Point>();
     	List<Parada> paradas = new ArrayList<Parada>();
     	Map<String, Object> x = new HashMap<>();
     	DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
             DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
             Document document = documentBuilder.parse(is);
             NodeList nodelist_trkpt = document.getElementsByTagName("trkpt");
             NodeList nodelist_wpt = document.getElementsByTagName("wpt");
             for(int i = 0; i < nodelist_trkpt.getLength(); i++){
                 Node node = nodelist_trkpt.item(i);
                 NamedNodeMap attributes = node.getAttributes();
                 ruta.add(new Point(Double.parseDouble(attributes.getNamedItem("lat").getTextContent()),
                		 			Double.parseDouble(attributes.getNamedItem("lon").getTextContent())));
             }
             for(int i = 0; i < nodelist_wpt.getLength(); i++){
                 Node node = nodelist_wpt.item(i);
                 NamedNodeMap attributes = node.getAttributes();
                 NodeList datos = node.getChildNodes();
                 String newName = "";
                 String newImagen = "0";
                 String newLatitude = attributes.getNamedItem("lat").getTextContent();
                 String newLongitude = attributes.getNamedItem("lon").getTextContent();
                 for(int j = 0; j < datos.getLength(); j++){
                 	Node dat = datos.item(j);
                 	String etq = dat.getNodeName();
                 	if(etq.equals("name")) {
                 		newName = dat.getFirstChild().getNodeValue();
                 	}
                 	if(etq.equals("extensions")) {
                 		NodeList nodelist_ext = dat.getChildNodes();
                 		NodeList nodelist_extch = nodelist_ext.item(1).getChildNodes();
                 		Node n = nodelist_extch.item(3);
                 		if(n != null) {
                 			newImagen = n.getTextContent();
                 		}
                 	}
                 }
                 paradas.add(new Parada(newName,
                		 	newImagen!="0"?newImagen:"",
                		 	new Point(  Double.parseDouble(newLatitude),
                		 				Double.parseDouble(newLongitude)),i));
             }
             x.put("ruta", douglasPeucker(ruta,0.000025));
             x.put("parada", paradas);
         } catch (ParserConfigurationException e) {
             e.printStackTrace();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (SAXException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
         return x;
     }
     public static List<Point> douglasPeucker(List<Point> puntos,double epsilon){
         int maxIndex=0;
         double maxDist=0.0;
         double distPerp;
         List<Point> izq,der=new ArrayList<Point>();
         List<Point> filtro=new ArrayList<Point>();
         if(puntos.size()<2)
         {
         	return puntos;
         }
         for(int i=1;i<puntos.size()-1;i++){
             distPerp=distanciaPerpendicular(puntos.get(i),puntos.get(0),puntos.get(puntos.size()-1));
             if(distPerp>maxDist){
                 maxIndex=i;
                 maxDist=distPerp;
             }
         }
         if(maxDist>=epsilon){
             izq=douglasPeucker(puntos.subList(0, maxIndex),epsilon);
             der=douglasPeucker(puntos.subList(maxIndex,puntos.size()),epsilon);
             filtro.addAll(izq);
             filtro.addAll(der);
         }else{
             filtro.add(puntos.get(0));
             filtro.add(puntos.get(puntos.size()-1));
         }
         return filtro;
     }
     public static double distanciaPerpendicular(Point punto,Point lineaInicio,Point lineaFin){
         double x=punto.getX();
         double y=punto.getY();
         double xi=lineaInicio.getX();
         double yi=lineaInicio.getY();
         double xf=lineaFin.getX();
         double yf=lineaFin.getY();
         double angulo=(yf-yi)/(xf-xi);
         double inter=yi-(angulo*xi);
         double result=Math.abs(angulo*x-y+inter)/Math.sqrt(Math.pow(angulo, 2)+1);
         return result;
     }
}