/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikiwars.wikiloader;

/**
 *
 * @author vv
 */
public class Tester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String text;
        text = "North Korea, officially the Democratic People's Republic of Korea (abbreviated as DPRK, PRK, DPR Korea, or Korea DPR), is a country in East Asia constituting the northern part of the Korean Peninsula. Pyongyang is the capital and largest city. To the north and northwest, the country is bordered by China and by Russia along the Amnok (known as the Yalu in China) and Tumen rivers; it is bordered to the south by South Korea, with the heavily fortified Korean Demilitarized Zone (DMZ) separating the two. Nevertheless, North Korea, like its southern counterpart, claims to be the legitimate government of the entire peninsula and adjacent islands. Both North Korea and South Korea became members of the United Nations in 1991.\nIn 1910, Korea was annexed by Imperial Japan. After the Japanese surrender at the end of World War II in 1945, Korea was divided into two zones, with the north occupied by the Soviet Union and the south occupied by the United States. Negotiations on reunification failed, and in 1948, separate governments were formed: the socialist Democratic People's Republic of Korea in the north, and the capitalist Republic of Korea in the south. An invasion initiated by North Korea led to the Korean War (1950–1953). The Korean Armistice Agreement brought about a ceasefire, but no peace treaty was signed.\nNorth Korea officially describes itself as a self-reliant, socialist state, and formally holds elections. Various media outlets have called it Stalinist, particularly noting the elaborate cult of personality around Kim Il-sung and his family. The Workers' Party of Korea (WPK), led by a member of the ruling family, holds power in the state and leads the Democratic Front for the Reunification of the Fatherland of which all political officers are required to be members. Juche, an ideology of national self-reliance, was introduced into the constitution in 1972. The means of production are owned by the state through state-run enterprises and collectivized farms. Most services such as healthcare, education, housing and food production are subsidized or state-funded. From 1994 to 1998, North Korea suffered a famine that resulted in the deaths of between 240,000 and 420,000 people, and the population continues to suffer malnutrition. North Korea follows Songun, or \"military-first\" policy. It is the country with the highest number of military and paramilitary personnel, with a total of 9,495,000 active, reserve and paramilitary personnel. Its active duty army of 1.21 million is the fourth largest in the world, after China, the United States and India. It possesses nuclear weapons.\nInternational organizations have assessed that human rights violations in North Korea are commonplace and have no parallel in the contemporary world.";
        System.out.println(text);
        int cutoff = text.indexOf("== See also ==");
        cutoff = text.lastIndexOf("In 1910");
        System.out.println(cutoff);
        text = text.substring(0, cutoff);
        System.out.println(text);
        
    }
    
}
