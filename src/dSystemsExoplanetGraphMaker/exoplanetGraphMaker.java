package dSystemsExoplanetGraphMaker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;

public class exoplanetGraphMaker {
	public static void print(Object x) {
		System.out.println(x);
	}
	
	public static JSONArray removeNulls(JSONArray target) {
		JSONArray ret = new JSONArray();
		for(int i = 0; i < target.size(); i++) {
			JSONObject currentJSON = (JSONObject) target.get(i);
			if(currentJSON.get("pl_orbper") != null && currentJSON.get("pl_orbeccen") != null) {
				ret.add(currentJSON);
			}
		}
		return ret;
	}
	
	public static String createJSONTextfile(JSONArray target) {
		String fileName = "jsonText.txt";
		try {
			File retFile = new File(fileName);
			if(retFile.createNewFile()) {
				print("jsonText.txt created");
			}
			FileWriter writer = new FileWriter(fileName);
			writer.write("0 " + target.size() + "\n");
			for(int i = 0; i < target.size(); i++) {
				JSONObject currentJSON = (JSONObject) target.get(i);
				Double temp =  (Double) currentJSON.get("pl_orbper");
				Double temp2 = (Double) currentJSON.get("pl_orbeccen");
				writer.write(temp.toString() + " " + temp2.toString() + "\n");
			}
			writer.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}
	
	public static String runGnu(JSONArray target) {//This creates the gnuplot script
		String dataFile = createJSONTextfile(target);
		String ret = "";
		String fileName = "myScript.plt";
		try {
			File retFile = new File(fileName);
			if(retFile.createNewFile()) {
				print("Script created");
			}
			FileWriter writer = new FileWriter(fileName);
			writer.write("set output 'scatterPlot.png'\n");
			writer.write("plot '" + dataFile + "'"); 
			writer.close();
		}catch (Exception e) {
			print("Failed to create script");
		}
		return ret;
	}
	
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Boolean commandQuit = false;
		String gnuplotFilePath = "\"C:\\Program Files\\gnuplot\\bin\\gnuplot.exe\" \"C:\\\\Users\\\\braft\\\\OneDrive\\\\Desktop\\\\test\\\\sin.plt\"";
		try {
			Runtime cp = Runtime.getRuntime();
			Process pr = cp.exec(gnuplotFilePath);
		}catch (Exception e) {
			
		}
		print("Running....");
		
		String url = args[0];
		url.replaceAll("\"", " ");//Get url and remove " "
		try {
			
			
			while(!commandQuit.equals(true)) {
				print("Enter command please");
				String command = br.readLine();
				if(command.equals("command:quit")) {
					print("status:quitting"); 
					commandQuit = true;
				}else if(command.equals("command:plot,xAxis:pl_orbper,yAxis:pl_orbeccen")) {
					String contents = Jsoup.connect(url).ignoreContentType(true).execute().body();
					JSONParser parser = new JSONParser();
					Object obj = parser.parse(contents);
					JSONArray jsonArray = (JSONArray) obj;
					JSONArray removedNulls = removeNulls(jsonArray);
					String fileName = runGnu(removedNulls);
					print(fileName);
				}else {
					print("Please enter a valid command");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
