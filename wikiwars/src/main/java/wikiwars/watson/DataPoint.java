/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikiwars.watson;

import java.util.Arrays;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;
import java.time.Instant;

/**
 *
 * @author vv
 */
public class DataPoint {
    private String time;
    private String entity;
    private double sentiment;
    private double[] emotions;
    
    public DataPoint(String time, String entity, double sentiment, double[] emotions){
        this.time=time;
        this.entity=entity;
        this.sentiment = sentiment;
        this.emotions=emotions;
    }
    
    public String toString(){
        String sep=",";
        String value = this.time+",";
        value += this.entity+",";
        value += this.sentiment+",";
        String tmp = Arrays.toString(emotions);
        tmp =tmp.replace("[", "");
        tmp = tmp.replace("]", "");
        value +=tmp;
        return value;
    }
    
}
