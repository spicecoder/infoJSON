/*
 *ContextObject is a helper class  to create context pointer
 * it is used by InfoJSON / JSONFromContexts
e.g use a json string :
 
   String inj = injson.toJSONString();
   
     ContextObject  cj  = new ContextObject ();
     cj.jsonCaster(inj);
     cj.showContexts();
will printout the lists of contexts 
only restriction it should be valid json object with a single
root

 */
package infojson;

 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author pronabpal
 */
public class ContextObject {
    HashMap domain = new HashMap()  ; 
    public  ArrayList contextArray = new ArrayList();
    // a context object holds a bunch of url like strings
    //each is called a contextpointer
    //a bunch of contextpointer can create a cue at the 
    //earliest split point
    //contextobject calculates the earliest split point
    //can return the cue
    //a context object can be instantiated by standard json 
    //string ,with the root being a JsonObject
    //
 //readJson provides a hash map with all contextpoints from a standard JSONObject
 // afew convention : the hashmap is called contextHash
 //a json name become a context pointer member with its value being the value
 //stored in the hashmap   
 //the algorithim drills through the json interatively ,at each point storing all 
    //nameable json name string being added to the contextpoint , a json array member
    //gets its name as an integer and gets added to the contextpointer head
//the iteration goes on until no more json array or json object found in the value
//entry of the hash map    
    //{"abc" :{"sujoy":{"school":"Kallol","friendsin":["year5","year8","year12"]}}}
    /*
    {
  "abc": {
    "sujoy": {
      "school": "Kallol",
      "friendsin": [
        "year5",
        "year8",
        "year12"
      ]
    }
  }
}                 
    should give :  abc.sujoy.school.friendsn.@kallol
                   abc.sujoy.school.friendsn.1.@year5
                   abc.sujoy.school.friendsn.1.@year8
                   abc.sujoy.school.friendsn.1.@year12
    
    
    */
 public ContextObject() {super(); }   
 public ContextObject(ArrayList ic)   {super(); 
 contextArray = ic;
 }
 //string till first dot is data
 //string till second dot is data.o and data.1
 //need the verbs get an array of data till jth dot point
 //then if jth dot poin data give,give me all j+1th dot point
 //start a loop for length of contextpath -from 1 
 //getdatatilldot ,for each set value if @,jsonObject if non number
 //start a json,if number then get all numbers
 //for each make json array entry get match for each json entry
 //if more than one entry there is a contradiction,when in json arry
 //we start thesame i.e. json object ,or array ,or plain value
 //
 public ArrayList  getdatatilldot(int ii) { //return null;
 int al = contextArray.size();
 ArrayList rl =new ArrayList();
 for (int ll =0;ll<al;ll++)
 {
   String cc = (String)contextArray.get(ll)  ;
  int pos =   ordinalIndexOf(cc,".",ii);
  if (pos > 0) {
  if(pos < cc.length())
  rl.add(cc.substring(0,pos)); }
 
 }
  
 return rl;
 }
//unique values after nth dot n=0 is before any dot 
 public ArrayList uniqueValues(int nn){ 
  //   if (nn > 0)
  int al = contextArray.size();
   ArrayList rl =new ArrayList();
 for (int ll =0;ll<al;ll++)
 {
   String cc = (String)contextArray.get(ll)  ;
  int pos =   ordinalIndexOf(cc,".",nn);
  if (pos > 0){
  String xc = cc.substring(0,pos);
  String lw = null;
  if (xc.contains("."))
  { lw = xc.substring(xc.lastIndexOf(".")+1);}
   else lw = xc;
 if( !rl.contains(lw)){ rl.add(lw);} }
 
 }
  
  
 return rl;
       
 
 }    
 public static int ordinalIndexOf(String str, String s, int n)
{
    int pos = str.indexOf(s, 0);
    while (n-- > 0 && pos != -1)
        pos = str.indexOf(s, pos+1);
    return pos;
}
 
 public int getMaxDotCounts() {
 int al = contextArray.size();
 int maxc = 0;
 ArrayList rl =new ArrayList();
 for (int ll =0;ll<al;ll++)
 {
   String cc = (String)contextArray.get(ll)  ;
   String[] wl = cc.split("\\.");
   
   if  (maxc < wl.length) { maxc = wl.length;}
   
 
 }
 return maxc;
 }
 public ArrayList getMatched(String cc) { 
 int al = contextArray.size();
 int ic = cc.length();
 ArrayList rl =new ArrayList();
 //String nw =null;
 for (int ll =0;ll<al;ll++)
 {
      String nw =null;
   String cx = (String)contextArray.get(ll)  ;
   if (cx.length() > ic) {
   if (cx.substring(0,ic ).equalsIgnoreCase(cc))
   
   {String lw =  cx.substring(ic)   ;
    if (lw.indexOf(".") > 0) {
      nw =   lw.substring(0,lw.indexOf("."));}
    else nw = lw;
    
   }
  if (nw != null)  {  if( !rl.contains(nw)){ rl.add(nw);}}
 } }
 return rl;
 
  
 }
 public void showContexts() { 
     if (domain.size() > 0 ){
        System.out.println("Context Displayed:"  );  
 Iterator itr = (domain.keySet()).iterator();
 while (itr.hasNext()){
     String akey = (String)itr.next();
     
String cc = akey + "." +domain.get(akey);
     
 System.out.println(cc);
 
 
 contextArray.add(cc);
     
 }
     }
     else { System.out.println("The daoin is empty");}
 }
  public void writeContexts() { 
       FileWriter fw = null;
      File out = new File("outcontext.ctx");
        try {
             fw = new FileWriter(out);
        } catch (IOException ex) {
            Logger.getLogger(ContextObject.class.getName()).log(Level.SEVERE, null, ex);
        }
     if (domain.size() > 0 ){
        System.out.println("Context Displayed:"  );  
 Iterator itr = (domain.keySet()).iterator();
 while (itr.hasNext()){
     String akey = (String)itr.next();
     
String cc = akey + "." +domain.get(akey);
     
 System.out.println(cc);
 
 
            try {
                fw.write(cc +"\n");
             }
           catch (Exception ex) {
               Logger.getLogger(ContextObject.class.getName()).log(Level.SEVERE, null, ex);
           }
            
 }
 
   
          try {
              
               fw.flush();
                fw.close();
     }
           catch (Exception ex) {
               Logger.getLogger(ContextObject.class.getName()).log(Level.SEVERE, null, ex);
           }
     }
     else { System.out.println("The daoin is empty");}
 }
public   void  jsonCaster ( String inj) {   
 Set allkeys = null;  
 JSONObject obj=null;
    try {   

JSONObject starter = new JSONObject( );
JSONParser jp = new JSONParser();
 //inj ="{\"abc\" :{\"sujoy\":{\"school\":\"Kallol\",\"friendsin\":[\"year5\",\"year8\",\"year12\"]}}}";  
   obj = (JSONObject)jp.parse(inj);
 // Object obj = jp.parse(in);
   
 
   allkeys = (Set) obj.keySet();
   int hi =  allkeys.size();
   if (hi == 0) {System.out.println("No keys found;" + hi); System.exit(99);}
   
  // domain.put(obj.get(allkeys), inj);
}
 catch (Exception ex) { System.out.println("Error in parsing input json");
         ex.printStackTrace();
         ;} 
    
//iterate over the values in hash map  domain
//while there is a JSON  value ,treat it as a new object

 Iterator itr = allkeys.iterator();
 String cpointer = "" ;
// this iterator is for the first layer only  
//

while (itr.hasNext()) { 
    String kname = (String)itr.next();
    cpointer = kname ;
    Object val = obj.get(kname);
    putObject(cpointer,val,domain);
    
}
//

 }
 
public void putJSON(String cpointer,JSONObject obj,HashMap domain ) 
 
{ 
   // go through all keys and invoke putObject for each value;
   Set allkeys = (Set) obj.keySet();
 String apointer = "";
 Iterator itr = allkeys.iterator(); 
//
while (itr.hasNext()) { 
    String kname = (String)itr.next();
    apointer = cpointer+"."+kname ;
    Object val = obj.get(kname);
    putObject(apointer,val,domain);  
    
    
    
} 
}
public void putJSONArray(String cpointer,JSONArray obj,HashMap domain ) 
 
{
  //go through all values and invoke putObjectVal for each value  
  Iterator itr =  obj.iterator();
  int ii = 0;
  while (itr.hasNext()) { 
    String kname = Integer.toString(ii);
   String  apointer = cpointer+"."+kname ;
    Object val = itr.next();
    putObject(apointer,val,domain);  
    ii = ii + 1;
    
    
    
} 
    
} 
//this can be a start of method taking cpointer and the domain object and a value
  public void putObject (String cpointer,Object val,HashMap domain) {
    if(val instanceof JSONArray) { 
    //initiaate pointer ,this pointer can be passed to iterate over a value
    //first insert the value , then pass the pointer and value to a routine
    //which does the following : 1st it breaks the value ,add a json name to the same
    //domain object with the new pointer
    // we can start domain.putjsonarray,val 
     putJSONArray(cpointer,(JSONArray)val,domain);
    }
    else
    if(val instanceof JSONObject) {
     //get all keys and val and start domainput with the current pointer  
     //    domain.putjson(cpointer, val);
     //start domain.putJSONObject
     putJSON(cpointer,(JSONObject)val,domain);
    }
    else
    {
     //this can be names as putOtherObjects so that it does not generate furthet
     //iterations  
     //  
     //   domain.put(cpointer,val);
        putOtherObjects(cpointer,val,domain);
    
    }
    
  }

 public void putOtherObjects(String cpointer,Object val,HashMap domain) 
 {
     //we expect this to be string when the input is JSONString 
     //however the algorithim is ready to work with any general object that 
     // is allowed is a random jsonObject
     String sval ="@" +val ;
    // String sval = "@" + ((String)val).replaceAll("//.", "_");
    // if (val instanceof String){val = "@" + val; }
    // else 
    //     if (val instanceof Integer){val = "@" + val; }
    // else 
    //     if (val instanceof Float){val = "@" + val; }
 domain.put(cpointer,sval);
 
 }
  
  
 public static void main(String[] ags){ 
     String inj =  "{\"abc\":\"ohmygod\"}";
     ContextObject  cj  = new ContextObject ();
     cj.jsonCaster(inj);
     cj.showContexts();
     cj.writeContexts();
     
     
     
     
 
 }
 
 
}


 