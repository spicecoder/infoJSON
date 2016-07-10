/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infojson;

//import static infojson.ContextObject.ordinalIndexOf;
import java.util.ArrayList;

/**
 *
 * @author pronabpal
 */
public class ArrayCheck {
    
    //ArrayList contextArray = new ArrayList();
    ArrayList<String> contextArray =  new ArrayList<String>() {{
    add("data.0.created_time.@2010-08-02T21:27:44+0000");
    add("data.0.actions.1.name.@Like");
    add("data.0.from.id.@X12");
    add("data.1.updated_time.@2010-08-02T21:27:44+0000");
}}; 
   public static int ordinalIndexOf(String str, String s, int n)
{
    int pos = str.indexOf(s, 0);
    while (n-- > 0 && pos != -1)
        pos = str.indexOf(s, pos+1);
    return pos;
}  
 //then if jth dot poin data give,give me all j+1th dot point
 public ArrayList  getdatatilldot(int ii) { //return null;
 int al = contextArray.size();
 ArrayList rl =new ArrayList();
 for (int ll =0;ll<al;ll++)
 {
   String cc = (String)contextArray.get(ll)  ;
  int pos =   ordinalIndexOf(cc,".",ii);
  rl.add(cc.substring(0,pos));
 
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
  String xc = cc.substring(0,pos);
  String lw = null;
  if (xc.contains("."))
  { lw = xc.substring(xc.lastIndexOf(".")+1);}
  else lw = xc;
 if( !rl.contains(lw)){ rl.add(lw);}
 
 }
  
  
 return rl;
       
 
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
   if (cx.substring(0,ic ).equalsIgnoreCase(cc))
   
   {String lw =  cx.substring(ic)   ;
      nw =   lw.substring(0,lw.indexOf("."));
   }
  if (nw != null)  {  if( !rl.contains(nw)){ rl.add(nw);}}
 }
 return rl;
 
 }
 
 
 
 
 
 
 public static void main(String[] args)  {
ArrayCheck ar = new ArrayCheck();

//System.out.println( ar.getdatatilldot(0));
//System.out.println( ar.getdatatilldot(2));
//System.out.println( ar.uniqueValues(0));
//System.out.println( ar.uniqueValues(1));
//System.out.println( ar.uniqueValues(2));
//System.out.println("count:"+  ar.getMaxDotCounts() );
//System.out.println( "matched"+ ar.getMatched("data.0."));
 } 
    
    
}
