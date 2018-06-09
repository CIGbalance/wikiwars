package wikiwars.watson;


public interface NLProcessor {
    /**
     * Use Watson NLP to process text and return the result
     * @param text TODO: How to return the data?
     * @return
     */
    String[][] processText(String text);
}
