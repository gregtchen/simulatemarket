import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class SimulateMarket{
   public static void main(String[] args) throws FileNotFoundException{
      
      File file = new File(args[0]); //put percentage file into same directory and call through arguments
      
      Sample model = new Sample();
      model.fillData(file);
      model.computeStats();
      System.out.println("model---------------------- \n" + model.toString() + "\n---------------------------");
      
      int simulationsOriginal = 10;
      int simulationsImproved = 10;
      System.out.println("Original-------------------");
      for(int i = 0; i < simulationsOriginal; i++){
         System.out.println(simulate(model.getData(), 100.0).toString());
      }
      System.out.println("---------------------------\nImproved-------------------");
      for(int i = 0; i < simulationsImproved; i++){
         System.out.println(simulate(model.getData(), 100.0, true).toString());
      }
      
      
      //simulate original algorithm once
      //Sample equity = simulate(model.getData(), 100.0);
      //System.out.println(equity.toString());
      
      //simulate changed algorithm once 
      //Sample equityDifferent = simulate(model.getData(), 100.0, true);
      //System.out.println(equityDifferent.toString());
   }
   
   public static Sample simulate(ArrayList<Double> data, double initial){
      Random rnd = new Random();
      Sample retval;
      ArrayList<Double> rndPercents = new ArrayList<Double>();
      ArrayList<Double> equityData = new ArrayList<Double>();
      double appreciateThis;
      double percent;
   
      for(int j = 0; j < 1000; j++){
         appreciateThis = initial;
         for(int k = 0; k < data.size(); k++){
            //finds random percent from sample's percent data list to appreciate the current value
            percent = data.get(rnd.nextInt(data.size()));
            rndPercents.add(percent);
            appreciateThis = appreciateThis + appreciateThis * percent;
         }
         equityData.add(appreciateThis);
      }
   
      
      double min = rndPercents.get(0), mean = 0.0, risk = 0.0, max = rndPercents.get(0);
      for(int i = 0; i < rndPercents.size(); i++){
         //calculate the minimum
         if(rndPercents.get(i) < min){
            min = rndPercents.get(i);
         }
         //calculate the max
         if(rndPercents.get(i) > max){
            max = rndPercents.get(i);
         }
      }
      for(int i = 0; i < equityData.size(); i++){
         mean += equityData.get(i);
      }
      mean/=equityData.size();
      //risk
      //Work out the Mean (the simple average of the numbers)
      //Then for each number: subtract the Mean and square the result
      //Then work out the mean of those squared differences.
      //Take the square root of that and we are done!
      for(int i =0; i < rndPercents.size(); i++){
         risk += Math.pow((rndPercents.get(i)-mean), 2);
      }
      risk /= rndPercents.size();
      risk /= Math.sqrt(risk);
      
      
      retval = new Sample(min, mean, risk, max, equityData, rndPercents);
      return retval;
   }
   
   public static Sample simulate(ArrayList<Double> data, double initial, boolean overloaded){
      Random rnd = new Random();
      Sample retval = new Sample();
      ArrayList<Double> rndPercents = new ArrayList<Double>();
      ArrayList<Double> equityData = new ArrayList<Double>();
      double appreciateThis;
      double percent = data.get(0);
      boolean positive = false;
      boolean neutral = false;
      boolean negative = false;

      for(int j = 0; j < 1000; j++){
         appreciateThis = initial;
         for(int k = 0; k < data.size(); k++){
            positive = false;
            neutral = false;
            negative = false;
            // checks if next percent will be positive, negative, or zero
            if(data.get(k) > 0){
               positive = true;
            }
            else if(data.get(k) < 0){
               negative = true;
            }
            else{
               neutral = true;
            }
            //keeps searching for a percent of the same sign(positive, negative, or zero)
            if(positive){
               while(positive){
                  percent = data.get(rnd.nextInt(data.size()));
                  if(percent > 0.0){
                     positive = false;
                  }
               }
            }
            if(negative){
               while(negative){
                  percent = data.get(rnd.nextInt(data.size()));
                  if(percent < 0.0){
                     negative = false;
                  }
               }
            }
            if(neutral){
               percent = 0.0;
               neutral = false;
            }

            rndPercents.add(percent);
            appreciateThis = appreciateThis + appreciateThis * percent;
         }
         equityData.add(appreciateThis);
         //System.out.println(appreciateThis); //this displays every element of equity's data
      }
      
      double min = rndPercents.get(0), mean = 0.0, risk = 0.0, max = rndPercents.get(0);
      for(int i = 0; i < rndPercents.size(); i++){
         //calculate the minimum
         if(rndPercents.get(i) < min){
            min = rndPercents.get(i);
         }
         //calculate the max
         if(rndPercents.get(i) > max){
            max = rndPercents.get(i);
         }
      }
      for(int i = 0; i < equityData.size(); i++){
         mean += equityData.get(i);
      }
      mean/=equityData.size();
      //risk
      //Work out the Mean (the simple average of the numbers)
      //Then for each number: subtract the Mean and square the result
      //Then work out the mean of those squared differences.
      //Take the square root of that and we are done!
      for(int i =0; i < rndPercents.size(); i++){
         risk += Math.pow((rndPercents.get(i)-mean), 2);
      }
      risk /= rndPercents.size();
      risk /= Math.sqrt(risk);
      
      
      retval = new Sample(min, mean, risk, max, equityData, rndPercents);

      return retval;
   }
}