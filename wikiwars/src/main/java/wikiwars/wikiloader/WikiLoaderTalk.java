/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikiwars.wikiloader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author vv
 */
public class WikiLoaderTalk{

    public static void main(String[] args){
        WikiLoaderTalk l = new WikiLoaderTalk();
        l.getArticleHistory("North_Korea");
    }
    
    public List<String> separateComments(String extract, List<String> results){
        String[] res = extract.split("== [^==]* ==");
        for(String topic : res){
            String text = topic.replaceAll("(?m)^[ \t]*\r?\n", "");
            if (!"".equals(text)) {
                /*Pattern p = Pattern.compile("\\(talk\\) (.)* \\(UTC\\)");
                Matcher m = p.matcher(text);
                List<String> timestamps = new ArrayList<String>();
                while(m.find()){
                    String tmp = text.substring(m.start(), m.end()).substring(7);
                    tmp = tmp.substring(0, tmp.length()-6);
                    timestamps.add(tmp);
                }*/
                String[] resTmp = text.split("\\(talk\\) (.)* \\(UTC\\)[\\)]?");
                for(String comment : resTmp){
                    comment = comment.replaceAll("(?m)^[ \t]*\r?\n", "");
                    results.add(comment);
                    //System.out.println(comment);
                }
            }
        }
        return results;
    }
    
    public List<String> getArticleHistory(String topic) {
        List<String> results = new ArrayList<String>();

        String query = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&explaintext=true&format=json&titles=Talk:"+topic;
        String queryResponse = queryPage(query);
        String response = parseJson(queryResponse);
        
        
        results = separateComments(response, results);
        
       int talk=1;
        while(true){
            query = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&explaintext=true&format=json&titles=Talk:"+topic+"/Archive_"+talk;
            queryResponse = queryPage(query);
            response = parseJson(queryResponse);
            if(response==null){
                break;
            }else{
                results = separateComments(response, results);
            }
            talk++;
        }
        return results;
    }
    
    public String queryPage(String query){
        URLConnection urlConn = null;
        StringBuilder sb = new StringBuilder();
        InputStreamReader in = null;
        System.out.println(query);
        try {
            URL url = new URL(query);
            urlConn = url.openConnection();
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                Charset.defaultCharset());
                try (BufferedReader bufferedReader = new BufferedReader(in)) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                }
            }
            in.close();
        } catch (Exception ex) {
            Logger.getLogger(WikiLoaderTalk.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }
    
    public String parseJson(String json){
        JSONParser parser= new JSONParser();
        try {
            Object obj = parser.parse(json);
            JSONObject jsonObject= (JSONObject)obj;

            JSONObject query = (JSONObject) jsonObject.get("query");
            JSONObject pages = (JSONObject) query.get("pages");
            String index="";
            Iterator it =pages.keySet().iterator();
            while (it.hasNext())
            {
                index=(String) it.next();
            }
            JSONObject page = (JSONObject) pages.get(index);

            String extract = (String) page.get("extract");
            return extract;
        } catch (ParseException ex) {
            Logger.getLogger(WikiLoaderTalk.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex){
            return null;
        }
        return null;
    }
    
}
