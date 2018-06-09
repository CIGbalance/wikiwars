/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikiwars;

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import wikiwars.watson.NLProcessor;
import wikiwars.wikiloader.WikiLoader;
import wikiwars.wikiloader.WikiLoaderImpl;

 import org.json.simple.parser.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import wikiwars.watson.DataPoint;

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
        String csvFile = topic+".csv";
        //get data per Wiki API

        WikiLoader wl = new WikiLoaderImpl();
        NLProcessor nlp = new NLProcessor();

        Map<Instant, String> articleHistory = wl.getArticleHistory(topic);

        //get Watson response
        List<DataPoint> results = new ArrayList<>();

        for(Instant time : articleHistory.keySet()) {
            results.addAll(nlp.processText(time, articleHistory.get(time)));
        }
        
        /*NLProcessor nlp = new NLProcessor();
        Instant time = Instant.EPOCH;
        String text ="In the rugged Colorado Desert of California, there lies buried a treasure ship sailed there hundreds of years ago by either Viking or Spanish explorers. Some say this is legend; others insist it is fact. A few have even claimed to have seen the ship, its wooden remains poking through the sand like the skeleton of a prehistoric beast. Among those who say they’ve come close to the ship is small-town librarian Myrtle Botts. In 1933, she was hiking with her husband in the Anza-Borrego Desert, not far from the border with Mexico. It was early March, so the desert would have been in bloom, its washed-out yellows and grays beaten back by the riotous invasion of wildflowers. Those wildflowers were what brought the Bottses to the desert, and they ended up near a tiny settlement called Agua Caliente. Surrounding place names reflected the strangeness and severity of the land: Moonlight Canyon, Hellhole Canyon, Indian Gorge. Try Newsweek for only $1.25 per week To enter the desert is to succumb to the unknowable. One morning, a prospector appeared in the couple’s camp with news far more astonishing than a new species of desert flora: He’d found a ship lodged in the rocky face of Canebrake Canyon. The vessel was made of wood, and there was a serpentine figure carved into its prow. There were also impressions on its flanks where shields had been attached—all the hallmarks of a Viking craft. Recounting the episode later, Botts said she and her husband saw the ship but couldn’t reach it, so they vowed to return the following day, better prepared for a rugged hike. That wasn’t to be, because, several hours later, there was a 6.4 magnitude earthquake in the waters off Huntington Beach, in Southern California. Botts claimed it dislodged rocks that buried her Viking ship, which she never saw again.There are reasons to doubt her story, yet it is only one of many about sightings of the desert ship. By the time Myrtle and her husband had set out to explore, amid the blooming poppies and evening primrose, the story of the lost desert ship was already about 60 years old. By the time I heard it, while working on a story about desert conservation, it had been nearly a century and a half since explorer Albert S. Evans had published the first account. Traveling to San Bernardino, Evans came into a valley that was “the grim and silent ghost of a dead sea,” presumably Lake Cahuilla. “The moon threw a track of shimmering light,” he wrote, directly upon “the wreck of a gallant ship, which may have gone down there centuries ago.” The route Evans took came nowhere near Canebrake Canyon, and the ship Evans claimed to see was Spanish, not Norse. Others have also seen this vessel, but much farther south, in Baja California, Mexico. Like all great legends, the desert ship is immune to its contradictions: It is fake news for the romantic soul, offering passage into some ancient American dreamtime when blood and gold were the main currencies of civic life.The legend does seem, prima facie, bonkers: a craft loaded with untold riches, sailed by early-European explorers into a vast lake that once stretched over much of inland Southern California, then run aground, abandoned by its crew and covered over by centuries of sand and rock and creosote bush as that lake dried out…and now it lies a few feet below the surface, in sight of the chicken-wire fence at the back of the Desert Dunes motel, $58 a night and HBO in most rooms.Totally insane, right? Let us slink back to our cubicles and never speak of the desert ship again. Let us only believe that which is shared with us on Facebook. Let us banish forever all traces of wonder from our lives. Yet there are believers who insist that, using recent advances in archaeology, the ship can be found. They point, for example, to a wooden sloop from the 1770s unearthed during excavations at the World Trade Center site in lower Manhattan, or the more than 40 ships, dating back perhaps 800 years, discovered in the Black Sea earlier this year.";
        List<DataPoint> results = nlp.processText(time, text);*/

        //output to .csv
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(csvFile));
            writer.append("timestamp, entity, sentiment, joy, anger, disgust, sadness, fear");
            writer.newLine();
            for(DataPoint dp : results){
                writer.append(dp.toString());
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Wikiwars.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try
        {            
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("Rscript ../analysis/analysis.R  "+csvFile);
            InputStream stdin = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(stdin);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            System.out.println("<OUTPUT>");
            while ( (line = br.readLine()) != null)
                System.out.println(line);
            System.out.println("</OUTPUT>");
            int exitVal = proc.waitFor();            
            System.out.println("Process exitValue: " + exitVal);
        } catch (Throwable t)
          {
            t.printStackTrace();
        }


    }

}
