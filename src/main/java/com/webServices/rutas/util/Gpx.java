package com.webServices.rutas.util;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import com.webServices.rutas.model.Punto;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Gpx {
    public void generarGpx(List<Punto> puntosPrueba) throws IOException, ParserConfigurationException {
        String lat=null,lon=null,URI="C:/Users/Administrador/Desktop/Para Rutas Mapeo/Transcisa7.gpx";
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        document = implementation.createDocument(null, "gpx", null);
        
         
            for(int i=0;i<puntosPrueba.size();i++){
                lat=String.valueOf(puntosPrueba.get(i).getLatitud());
                lon=String.valueOf(puntosPrueba.get(i).getLongitud());
                try{
                    
                
                Element tpt  = document.createElement("trkpt");
                document.getDocumentElement().appendChild(tpt); 
                
                document.setXmlVersion("1.0"); 
                tpt.setAttribute("lat", lat + "");
                tpt.setAttribute("lon", lon + "");
                
            
            
            
            
            //A�adimos la casa al documento
            
         
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
     
     
     
}