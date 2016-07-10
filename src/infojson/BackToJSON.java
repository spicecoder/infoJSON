/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infojson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/* 
 * 
 * This program recreates the JSONFile that was broken down into 
 * context paths by the program InfoJSON
 * context paths can uniquely specify every 
 * element in a JSONObject
 * the convention followed is JSON{"name":"value"}
 * is represented by name.@value, i.e. each value is 
 * preceded by "@" in the context path
 * nesting is done by dots {"name1":{name2:"value2"}}
 * will be presented as name1.name2.@value
 * json array in a json is represented by numbers
 * thus {"myjson" :["run","play","behappy"]}
 * is represented by the three context paths:
 * myjson.0.@run, myjson.1.@play ,
 * myjson.2.@behappy
 * 
 * 
 * The overall benifit is the whole json file  elements
 * can be conveniently represented by a collection of 
 * urls.
 * 
 *
 * @author pronabpal */
 
public class BackToJSON {

   ArrayList<String> COBlist =  new ArrayList<String>();
//   {{
//    add("data.0.created_time.@2010-08-02T21:27:44+0000");
//    add("data.0.actions.name.@Like");
//    add("data.0.from.id.@X12");
//    add("data.1.updated_time.@2010-08-02T21:27:44+0000");
//}};
    ContextObject cob = null;
  public   JSONObject jod = new JSONObject();
    ArrayList nl ;
    String regex = "\\d+";
    Pattern pattern = Pattern.compile(regex);
     ArrayList nums = new ArrayList(); 
     String mode =null;
    public void makeJSON () {
    
    
    //create contextObject , then we shall interact by
    //getdetailtilldot
    //and getordinalIndexOf
      cob = new ContextObject(COBlist);
     nl  = cob.uniqueValues(0);
     
    if (nl.size()  !=  1) 
    {System.out.println("no unique paraent node" + cob.getdatatilldot(0));
    
    }
    //process first node,no check for number or value
   
    String pn = (String)nl.get(0);
    
    
    
    
     GenerateWithJSON(jod,pn);
    //GeneratewithJSONARRy(ja,pn,pos)
    //possible method start,param jd ,pn,the contextpath
    //possible method start param ja,contextpath,position
   
    }  
    
  public void GenerateWithJSON(JSONObject jd,String pn) {
      //possible method start param ja,contextpath,position
     
   // jd.put(pn,"");
     jd.put(lastWord(pn),"");
    
    nl = cob.getMatched(pn+".");
     
    if (nl.size() > 0){
        Collections.sort(nl); 
        String es = (String)nl.get(0);
    if ((es.substring(0,1)).equalsIgnoreCase("@"))
         {mode ="value"; }
    else
     if(pattern.matcher(es).matches()) {
         mode = "array";
     }
     else {mode= "json"; }
     
        }
    
   if (mode.equalsIgnoreCase("value")) {
    for (int nll =0;nll <nl.size();nll++)
    { //processing the matched context vals
    String es = (String)nl.get(nll);
    if ((es.substring(0,1)).equalsIgnoreCase("@"))
        //set value  of jd at pn 
        jd.put(lastWord(pn), es.substring(1)); // if jd is ja add string 
    }}
   else 
    if (mode.equalsIgnoreCase("array")) {
    JSONArray js = new JSONArray();
    jd.put(lastWord(pn),js);
    GenerateWithJSONArray(js,nl, pn);
    
    }
     
    
    else  // we have  a string ,time for new json
    { for( int ii = 0;ii < nl.size();ii++) {
       JSONObject jjd = new JSONObject();
        jd.put(lastWord(pn),jjd);  
        jjd.put(nl.get(ii)  ,"");
        GenerateWithJSON(jjd, pn+"."+nl.get(ii));
               } 
            }
 
     
  }
     
  public void GenerateWithJSONArray(JSONArray js,ArrayList lw, String  contextpath) {
      //the json array is sitting in its own parent ,raedy to place 
      //objects -need to get them by order ,ie. sorted in contextpath
      //retain the order by collecting the index and iterate
  for (int jj =0 ; jj < lw.size() ; jj++ )
  {     
      String pn = contextpath + "." + lw.get(jj);  
     //  jd.put(lastWord(pn),"");
    
    nl = cob.getMatched(pn+"."); 
     
    if (nl.size() > 0){
        Collections.sort(nl); 
        String es = (String)nl.get(0);
    if ((es.substring(0,1)).equalsIgnoreCase("@"))
         {mode ="value"; }
    else
     if(pattern.matcher(es).matches()) {
         mode = "array";
     }
     else {mode= "json"; }
     
        }
    
   if (mode.equalsIgnoreCase("value")) {
    for (int nll =0;nll <nl.size();nll++)
    { //processing the matched context vals
    String es = (String)nl.get(nll);
    if ((es.substring(0,1)).equalsIgnoreCase("@"))
        //set value  of jd at pn 
        js.add( es.substring(1)); // if jd is ja add string 
    }
    if (mode.equalsIgnoreCase("array")) {
    JSONArray jss = new JSONArray();
    js.add(jss);
    GenerateWithJSONArray(jss,nl, pn);
    
    }
   }
    
    else  // we have  a string ,time for new json
    { for( int ii = 0;ii < nl.size();ii++) {
       JSONObject jjd = new JSONObject();
        js.add(jjd);  
        jjd.put(nl.get(ii)  ,"");
        GenerateWithJSON(jjd, pn+"."+nl.get(ii));
               } 
            }
 
    
       
  }
      
  }
  public String  lastWord(String st) { 
     int m = st.lastIndexOf(".");
     if (m == -1 ) return st;
     else 
         return st.substring(m+1);
  }
 public void  readctx(String fl ) throws IOException { 
     
      FileReader fr = null;
       try {
            fr =  new FileReader(new File(fl));
           BufferedReader bufferedReader = new BufferedReader(fr);
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				COBlist.add(line);
				
			}
			fr.close();
       
       
       
       } catch (FileNotFoundException ex) {
           Logger.getLogger(BackToJSON.class.getName()).log(Level.SEVERE, null, ex);
       }
 
 }
  
  public static void main(String[] args)  { 
  
  BackToJSON jfc = new BackToJSON();
       try {
           jfc.readctx("outcontext.ctx");
       } catch (IOException ex) {
           Logger.getLogger(BackToJSON.class.getName()).log(Level.SEVERE, null, ex);
       }
  jfc.makeJSON();
  System.out.println("the jinput :" + jfc.COBlist);
  
  System.out.println("the json out:" + jfc.jod);
  
  
  
  
  }
    
    
    
}
