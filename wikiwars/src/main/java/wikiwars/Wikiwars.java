/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikiwars;

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;

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



        //get Watson response
        NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
                "2018-03-16",
                "56e32079-1ebb-41af-882e-ed8e90fdcb50",
                "b1qmkh6h0sgt"
        );
        service.setEndPoint( "https://gateway-fra.watsonplatform.net/natural-language-understanding/api");

        String text = "I love all baby kittens.";

        EntitiesOptions entitiesOptions = new EntitiesOptions.Builder()
                .sentiment(true)
                .limit(2)
                .build();

        SentimentOptions sentimentOptions = new SentimentOptions.Builder()
                .build();

        Features features = new Features.Builder()
                .entities(entitiesOptions)
                .sentiment(sentimentOptions)
                .build();

        AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                .text(text)
                .features(features)
                .build();

        AnalysisResults response = service
                .analyze(parameters)
                .execute();
        System.out.println(response);
        

        //output to .csv

        //start R Script

    }

}
