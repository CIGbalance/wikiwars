package wikiwars.watson;


import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class NLProcessor {

    NaturalLanguageUnderstanding service;

    public NLProcessor() {
        service = new NaturalLanguageUnderstanding(
                "2018-03-16",
                "56e32079-1ebb-41af-882e-ed8e90fdcb50",
                "b1qmkh6h0sgt"
        );
        service.setEndPoint( "https://gateway-fra.watsonplatform.net/natural-language-understanding/api");
    }

    public List<DataPoint> processText(String time, String text) {
        // TODO: Silly example code
        AnalysisResults response = requestWatson(text);
        System.out.println(response);
        return parseResponse(time, response);
    }
    
    public List<DataPoint> parseResponse(String time, AnalysisResults response){
        List<DataPoint> parsedResult = new ArrayList<DataPoint>();
        List<EntitiesResult> entities = response.getEntities();
        for(EntitiesResult entity: entities){
            EmotionScores emo = entity.getEmotion();
            if(emo!=null){
                double[] emotions = {emo.getJoy(), emo.getAnger(), emo.getDisgust(), emo.getSadness(), emo.getFear()};
                double sentiment = entity.getSentiment().getScore();
                String name = entity.getText();
                parsedResult.add(new DataPoint(time, name, sentiment, emotions)); 
            }
        }
        double sentiment =  response.getSentiment().getDocument().getScore();
        EmotionScores emo = response.getEmotion().getDocument().getEmotion();
        double[] emotions = {emo.getJoy(), emo.getAnger(), emo.getDisgust(), emo.getSadness(), emo.getFear()};
        parsedResult.add(new DataPoint(time, "document", sentiment, emotions));
        return parsedResult;
    }
    
    public AnalysisResults requestWatson(String text){
        EntitiesOptions entitiesOptions = new EntitiesOptions.Builder()
            .sentiment(true)
            .emotion(true)
            .build();

        SentimentOptions sentimentOptions = new SentimentOptions.Builder()
                .build();
        
        EmotionOptions emotionOptions = new EmotionOptions.Builder()
                .build();

        Features features = new Features.Builder()
                .entities(entitiesOptions)
                .sentiment(sentimentOptions)
                .emotion(emotionOptions)
                .build();

        AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                .text(text)
                .features(features)
                .build();

        AnalysisResults response = service
                .analyze(parameters)
                .execute();
        
        return response;
    }
}
