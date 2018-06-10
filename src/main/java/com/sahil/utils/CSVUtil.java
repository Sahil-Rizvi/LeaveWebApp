package com.sahil.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sahil.models.Response;

public class CSVUtil {

	
	private static final String FILE_SEPARATOR = ",";
	
	/*
	public static HashMap<Date,String> CSVReader(String fileName) throws IOException, ParseException{
		
		FileReader fileReader = new FileReader("/home/bvadmin/"+fileName+".csv");
		
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		bufferedReader.readLine(); // ignoring column names
		String line = null;
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		HashMap<Date,String> hashMap = new LinkedHashMap<>();
		while((line = bufferedReader.readLine())!=null){
			String[] data = line.split(FILE_SEPARATOR);
			System.out.println(data[0]);
			Date date = simpleDateFormat.parse(data[0]);
			String occasion = data[1];
			hashMap.put(date,occasion);
		}
		
		bufferedReader.close();
		fileReader.close();
		
		hashMap.entrySet().stream().forEach(e->System.out.println(" Date --> "+e.getKey() 
		+ " Occasion --> "+e.getValue()));
		
		return hashMap;
			
	}
	
	*/
	public static HashMap<Date,String> CSVReader(String filePath) throws IOException, ParseException{
		FileReader fileReader = new FileReader(filePath);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		bufferedReader.readLine(); // ignoring column names
		String line = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		HashMap<Date,String> hashMap = new LinkedHashMap<>();
		while((line = bufferedReader.readLine())!=null){
			String[] data = line.split(FILE_SEPARATOR);
			Date date = simpleDateFormat.parse(data[0]);
			String occasion = data[1];
			hashMap.put(date,occasion);
		}
		bufferedReader.close();
		fileReader.close();
		return hashMap;	
	}
	
	
	public static HashMap<String,String> CSVReaderWithDateInString(String filePath) throws IOException, ParseException{
		FileReader fileReader = new FileReader(filePath);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		HashMap<String,String> hashMap = new LinkedHashMap<>();
		while((line = bufferedReader.readLine())!=null){
			String[] data = line.split(FILE_SEPARATOR);
			hashMap.put(data[0],data[1]);
		}
		bufferedReader.close();
		fileReader.close();
		return hashMap;	
	}

	public static Response validateCSVFile(MultipartFile multipartFile){
		   Response response = new Response();
		   response.setCode(1);
		   response.setMessage("Error");
		   if (!multipartFile.isEmpty()) {
		        try {
		            byte[] bytes = multipartFile.getBytes();
		            
		            if(bytes!=null){
		            	String completeData = new String(bytes);
		            	String header = completeData.split("\n")[0];
		            	System.out.println("header:"+header);
			            if(!StringUtils.isEmpty(header)){
			            	String[] columnNames = header.split(",");
			            	System.out.println("column names");
			            	for(String columnName:columnNames){
			            		System.out.println(columnName);
			            	}
			            	
			            	if(null!=columnNames && columnNames.length==2){
			            		if( (columnNames[0].toLowerCase().startsWith("date") && columnNames[1].toLowerCase().equalsIgnoreCase("festival")) 
			            				|| (columnNames[1].toLowerCase().startsWith("date") && columnNames[0].toLowerCase().equalsIgnoreCase("festival"))){
			            			response.setCode(0);
			            			response.setMessage("Success");
			            			return response;
			            		}
			            	}
			            }
			            else{
			            	System.out.println("Invalid Header");
			            	response.setMessage("Invalid Header");
			            }
		            }
		            
		        }
		        catch(Exception e){
		        	System.out.println(e.getMessage());
		        	response.setMessage("Exception Occured");
        			return response;
		        }
		   }
		        
		return response;
	}

	
	
	
	
	public static void CSVWriter(HashMap<Date,String> map) throws IOException{
	
		FileWriter fileWriter = new FileWriter("/home/bvadmin/abc.csv");
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		if(!CollectionUtils.isEmpty(map)){
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);	
			for(Map.Entry<Date,String> me : map.entrySet()){
				bufferedWriter.write(simpleDateFormat.format(me.getKey()));
				bufferedWriter.write(",");
				bufferedWriter.write(me.getValue());
				bufferedWriter.newLine();
			}
			
			bufferedWriter.close();
		}
		fileWriter.close();
	}
	
	
	
	
	public static void main(String[] args) throws IOException, ParseException{
		
		String fileName = "holidays";
		HashMap<Date,String> map =  CSVReader(fileName);
		CSVWriter(map);
		
	}
	
}
