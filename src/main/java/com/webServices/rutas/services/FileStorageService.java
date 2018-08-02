package com.webServices.rutas.services;

import com.webServices.rutas.exception.FileStorageException;
import com.webServices.rutas.exception.MyFileNotFoundException;
import com.webServices.rutas.model.FileStorageProperties;
import com.webServices.rutas.model.Punto;
import com.webServices.rutas.util.Gpx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@Service
public class FileStorageService {
	private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    @SuppressWarnings({ "unchecked"})
	public Map<String, Object> processFile(MultipartFile file) throws IllegalStateException, IOException, ParserConfigurationException{
    	List<Punto> puntosSinReducir = new ArrayList<Punto>();
    	List<Punto> paradas = new ArrayList<Punto>();
    	File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile(); 
        FileOutputStream fos = new FileOutputStream(convFile); 
        fos.write(file.getBytes());
        fos.close();
        Map<String, Object> x = decodeGPX(convFile);
        
    	List<String> gpxRuta = (List<String>) x.get("ruta");
    	List<String> gpxParada = (List<String>) x.get("parada");
    	String info = "";
    	for(int i = 0; i < gpxRuta.size(); i++){
            info = gpxRuta.get(i).toString();
            String[] latlong = info.split(":");
            double latitude = Double.parseDouble(latlong[0]);
            double longitude = Double.parseDouble(latlong[1]);
            Punto q = new Punto(latitude,longitude);
            puntosSinReducir.add(q);
        }
    	for(int i = 0; i < gpxParada.size(); i++){
            info = gpxParada.get(i).toString();
            String[] latlong = info.split(":");
            double latitude = Double.parseDouble(latlong[0]);
            double longitude = Double.parseDouble(latlong[1]);
            Punto q = new Punto(latitude,longitude);
            paradas.add(q);
        }
    	System.out.println(puntosSinReducir.size());
    	List<Punto> dp = douglasPeucker(puntosSinReducir,0.000025);
    	Gpx gpx = new Gpx();
        gpx.generarGpx(dp);
        System.out.println(dp.size());
        Map<String, Object> result = new HashMap<>();
        result.put("ruta", dp);
        result.put("parada", paradas);
        return result;
    }
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
    
    private static Map<String, Object> decodeGPX(File is){
    	Map<String, Object> x = new HashMap<>();
        List<String> listRuta = new ArrayList<>();
        List<String> listParada = new ArrayList<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
          //  FileInputStream fileInputStream = new FileInputStream(is);
            Document document = documentBuilder.parse(is);
            NodeList nodelist_trkpt = document.getElementsByTagName("trkpt");
            NodeList nodelist_wpt = document.getElementsByTagName("wpt");
            for(int i = 0; i < nodelist_trkpt.getLength(); i++){

                Node node = nodelist_trkpt.item(i);
                NamedNodeMap attributes = node.getAttributes();
                

                String newLatitude = attributes.getNamedItem("lat").getTextContent();

                String newLongitude = attributes.getNamedItem("lon").getTextContent();

                String newLocationName = newLatitude + ":" + newLongitude;

                listRuta.add(newLocationName);

            }
            for(int i = 0; i < nodelist_wpt.getLength(); i++){

                Node node = nodelist_wpt.item(i);
                NamedNodeMap attributes = node.getAttributes();
                NodeList datos = node.getChildNodes();
                String newName = "";
                String newImagen = "";
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
                String newLocationName = newLatitude + ":" + newLongitude + ":" + newName + ":" + newImagen;
                System.out.println(newLocationName);
                listParada.add(newLocationName);
            }
            x.put("ruta", listRuta);
            x.put("parada", listParada);
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
    @SuppressWarnings("unused")
	public static List<Punto> douglasPeucker(List<Punto> puntos,double epsilon){
    	int contadorParticion = 0;
        int maxIndex=0;
        double maxDist=0.0;
        double distPerp;
        List<Punto> izq,der=new ArrayList<Punto>();
        
        List<Punto> filtro=new ArrayList<Punto>();
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
        	contadorParticion++;
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
    public static double distanciaPerpendicular(Punto punto,Punto lineaInicio,Punto lineaFin){
        
        double x=punto.getLatitud();
        double y=punto.getLongitud();
        
        double xi=lineaInicio.getLatitud();
        double yi=lineaInicio.getLongitud();
        
        double xf=lineaFin.getLatitud();
        double yf=lineaFin.getLongitud();
        
        double angulo=(yf-yi)/(xf-xi);
        double inter=yi-(angulo*xi);
        
        double result=Math.abs(angulo*x-y+inter)/Math.sqrt(Math.pow(angulo, 2)+1);
        
     
        return result;
        
    
    }
    
}
