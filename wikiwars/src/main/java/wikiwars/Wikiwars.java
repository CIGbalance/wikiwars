/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikiwars;

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;
import wikiwars.watson.NLProcessor;
import wikiwars.watson.NLProcessorImpl;
import wikiwars.wikiloader.WikiLoader;
import wikiwars.wikiloader.WikiLoaderImpl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author vv
 */
public class Wikiwars {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //input Thema
        String topic = "Foreign_relations_of_North_Korea";
        String url = "https://en.wikipedia.org/wiki/" + topic;
        //get data per Wiki API

        WikiLoader wl = new WikiLoaderImpl();
        NLProcessor nlp = new NLProcessorImpl();

        Map<Instant, String> articleHistory = wl.getArticleHistory(topic);

        //get Watson response
        List<String[][]> results = new ArrayList<>();

        for(Instant time : articleHistory.keySet()) {
            results.add(nlp.processText(articleHistory.get(time)));
        }

        //output to .csv
        //TODO results -> csv

        //start R Script

    }

}
