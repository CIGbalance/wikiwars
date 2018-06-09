package wikiwars.watson;


import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;

public class NLProcessorImpl implements NLProcessor {

    NaturalLanguageUnderstanding service;

    public NLProcessorImpl() {
        service = new NaturalLanguageUnderstanding(
                "2018-03-16",
                "56e32079-1ebb-41af-882e-ed8e90fdcb50",
                "b1qmkh6h0sgt"
        );
        service.setEndPoint( "https://gateway-fra.watsonplatform.net/natural-language-understanding/api");
    }

    @Override
    public String[][] processText(String text) {
        // TODO: Silly example code
        String[][] result = new String[1][1];

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

        // return actual result as something useful
        return result;
    }
}
