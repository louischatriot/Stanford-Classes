import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class FeatureFactory {

  public static HashMap<String, Boolean> firstNames;
  public static HashMap<String, Boolean> names;
  public static HashMap<String, Boolean> countries;
  public static HashMap<String, Boolean> trainNames;
  public static HashMap<String, Boolean> big;

  /** Add any necessary initialization steps for your features here.
   *  Using this constructor is optional. Depending on your
   *  features, you may not need to intialize anything.
   */
  public FeatureFactory() {
    firstNames = new HashMap<String, Boolean>();
    names = new HashMap<String, Boolean>();
    countries = new HashMap<String, Boolean>();
    trainNames = new HashMap<String, Boolean>();
    big = new HashMap<String, Boolean>();



    try {
      parseFile(firstNames, "../data/male.txt");
      parseFile(firstNames, "../data/female.txt");
      parseFile(names, "../data/common.names");
      parseFile(countries, "../data/countries.1");
      parseFile(trainNames, "../data/train.names");     // Checking if rest of algorithm really works
      parseFile(big, "../data/big.txt");     // Checking if rest of algorithm really works


    } catch (Exception e) {
      System.out.println("fuck ...");  // Shouldn't happen anyway
    }

  }


  public void parseFile(HashMap<String, Boolean> hm, String fileName) {
    try {
      BufferedReader in = new BufferedReader(new FileReader(fileName));

      for (String line = in.readLine(); line != null; line = in.readLine()) {
        hm.put(line.toLowerCase(), true);
      }
    }  catch (Exception e) {
      System.out.println("fuck ...");  // Shouldn't happen anyway

    }
  }


    /**
     * Words is a list of the words in the entire corpus, previousLabel is the label
     * for position-1 (or O if it's the start of a new sentence), and position
     * is the word you are adding features for. PreviousLabel must be the
     * only label that is visible to this method. 
     */
    private List<String> computeFeatures(List<String> words,
        String previousLabel, int position) {

      List<String> features = new ArrayList<String>();

      String currentWord = words.get(position);

      // Baseline Features  
      features.add("word=" + currentWord);
      //features.add("prevLabel=" + previousLabel);
      //features.add("word=" + currentWord + ", prevLabel=" + previousLabel);


      if (Character.isUpperCase(currentWord.charAt(0))) {    // && this.firstNames.containsKey(currentWord.toLowerCase())) {
        features.add("case=Title");
        //features.add("caseTitle and prevLabel=" + previousLabel);
        if (previousLabel.equals("PERSON")) {
          features.add("prevPERSON and case title");
        } else if ((position >= 1) && (words.get(position - 1).equals("."))) {
          features.add("dotonebefore");
        }
      } else {
        features.add("casenottitle");
      }


    if (currentWord.equals("van") || currentWord.equals("der") || currentWord.equals("de")) {
      features.add("particule");
    } else {

    }


    if (this.big.containsKey(currentWord)) {
      features.add("normal word");
    } else {
      features.add("not a normal word");
    }

    //if (currentWord.length() >= 5) {
      //features.add("longword");
    //} else {
      //features.add("shortword");
    //}

    //features.add("Word length=" + currentWord.length());

    //if ((position >= 1) && (position <= words.size() - 2) && (Character.isUpperCase(words.get(position - 1).charAt(0)) || Character.isUpperCase(words.get(position + 1).charAt(0)))) {
      //features.add("clooooose" );
    //} else {
      //features.add("notclose");
    //}


      //if ((position >= 10) && (words.get(position - 1).equals("\"") ||words.get(position - 2).equals("\"") ||words.get(position - 3).equals("\"") ||words.get(position - 4).equals("\"") ||words.get(position - 5).equals("\"")  || words.get(position - 6).equals("\"") ||words.get(position - 7).equals("\"") ||words.get(position - 8).equals("\"") ||words.get(position - 9).equals("\"") ||words.get(position - 10).equals("\"")    )) {
        //features.add("quotenear");
      //} else {
        //features.add("noquote");
      //}


//if (previousLabel.equals("O")) {
  //features.add("O before ");
//} else {
  //features.add("PERSON before ");
//}


//Pattern p = Pattern.compile("[a-z]*");
 //Matcher m = p.matcher(currentWord.toLowerCase());
 //boolean b = m.matches();

//if (b) {
  //features.add("notnumber");
//} else {
  //features.add("withnumber");
//}

      //if (position >= 1) {
        ////if (words.get(position - 1).equals(".")) {
          //features.add("Previous word" + words.get(position - 1));
        ////}
      //}

      //if (position >= 1) {
        //if (words.get(position - 1).equals(".")) {
          //features.add("dotBefore");
        //}
      //}

      if (this.firstNames.containsKey(currentWord.toLowerCase())) {
        //if ((! currentWord.toLowerCase().equals("will")) && (! currentWord.toLowerCase().equals("may")) && (! currentWord.toLowerCase().equals("price"))) {
          //System.out.println(currentWord);
          features.add("firstName");
        //}
      } else {
        features.add("notfirstname");
      }

      //if (this.names.containsKey(currentWord.toLowerCase())) {
          //features.add("Common name");
      //}

      //if (this.trainNames.containsKey(currentWord.toLowerCase())) {
          //features.add("train names");
      //}

      if (this.countries.containsKey(currentWord.toLowerCase())) {
          features.add("Country");
      }

      //if (Character.isLowerCase(currentWord.charAt(0))) {
      //features.add("case=lower");
      //}

      //if (position >= 1) {
        //features.add("double" + words.get(position - 1) + currentWord);
      //}

      //if ((position >= 1) && (position <= words.size() - 2)) {
      //if (words.get(position + 1).equals("said") && words.get(position - 1).equals("\"")) {
      //features.add("quotesaid");
      //}
      //}

      //if (position <= words.size()-2) {
      //if (words.get(position + 1).equals("said") || words.get(position + 1).equals("told")) {
      //features.add("talkedto");
      //}
      //}


      /** Warning: If you encounter "line search failure" error when
       *  running the program, considering putting the baseline features
       *  back. It occurs when the features are too sparse. Once you have
       *  added enough features, take out the features that you don't need. 
       */

      // TODO: Add your features here 

      return features;
    }


    /** Do not modify this method **/
    public List<Datum> readData(String filename) throws IOException {

      List<Datum> data = new ArrayList<Datum>();
      BufferedReader in = new BufferedReader(new FileReader(filename));

      for (String line = in.readLine(); line != null; line = in.readLine()) {
        if (line.trim().length() == 0) {
          continue;
        }
        String[] bits = line.split("\\s+");
        String word = bits[0];
        String label = bits[1];

        Datum datum = new Datum(word, label);
        data.add(datum);
      }

      return data;
    }

    /** Do not modify this method **/
    public List<Datum> readTestData(String ch_aux) throws IOException {

      List<Datum> data = new ArrayList<Datum>();

      for (String line : ch_aux.split("\n")) {
        if (line.trim().length() == 0) {
          continue;
        }
        String[] bits = line.split("\\s+");
        String word = bits[0];
        String label = bits[1];

        Datum datum = new Datum(word, label);
        data.add(datum);
      }

      return data;
    }

    /** Do not modify this method **/
    public List<Datum> setFeaturesTrain(List<Datum> data) {
      // this is so that the feature factory code doesn't accidentally use the
      // true label info
      List<Datum> newData = new ArrayList<Datum>();
      List<String> words = new ArrayList<String>();

      for (Datum datum : data) {
        words.add(datum.word);
      }

      String previousLabel = "O";
      for (int i = 0; i < data.size(); i++) {
        Datum datum = data.get(i);

        Datum newDatum = new Datum(datum.word, datum.label);
        newDatum.features = computeFeatures(words, previousLabel, i);
        newDatum.previousLabel = previousLabel;
        newData.add(newDatum);

        previousLabel = datum.label;
      }

      return newData;
    }

    /** Do not modify this method **/
    public List<Datum> setFeaturesTest(List<Datum> data) {
      // this is so that the feature factory code doesn't accidentally use the
      // true label info
      List<Datum> newData = new ArrayList<Datum>();
      List<String> words = new ArrayList<String>();
      List<String> labels = new ArrayList<String>();
      Map<String, Integer> labelIndex = new HashMap<String, Integer>();

      for (Datum datum : data) {
        words.add(datum.word);
        if (labelIndex.containsKey(datum.label) == false) {
          labelIndex.put(datum.label, labels.size());
          labels.add(datum.label);
        }
      }

      // compute features for all possible previous labels in advance for
      // Viterbi algorithm
      for (int i = 0; i < data.size(); i++) {
        Datum datum = data.get(i);

        if (i == 0) {
          String previousLabel = "O";
          datum.features = computeFeatures(words, previousLabel, i);

          Datum newDatum = new Datum(datum.word, datum.label);
          newDatum.features = computeFeatures(words, previousLabel, i);
          newDatum.previousLabel = previousLabel;
          newData.add(newDatum);

        } else {
          for (String previousLabel : labels) {
            datum.features = computeFeatures(words, previousLabel, i);

            Datum newDatum = new Datum(datum.word, datum.label);
            newDatum.features = computeFeatures(words, previousLabel, i);
            newDatum.previousLabel = previousLabel;
            newData.add(newDatum);
          }
        }

      }

      return newData;
    }

    /** Do not modify this method **/
    public void writeData(List<Datum> data, String filename)
      throws IOException {


      FileWriter file = new FileWriter(filename + ".json", false);


      for (int i = 0; i < data.size(); i++) {
        try {
          JSONObject obj = new JSONObject();
          Datum datum = data.get(i);
          obj.put("_label", datum.label);
          obj.put("_word", base64encode(datum.word));
          obj.put("_prevLabel", datum.previousLabel);

          JSONObject featureObj = new JSONObject();

          List<String> features = datum.features;
          for (int j = 0; j < features.size(); j++) {
            String feature = features.get(j).toString();
            featureObj.put("_" + feature, feature);
          }
          obj.put("_features", featureObj);
          obj.write(file);
          file.append("\n");
        } catch (JSONException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      file.close();
    }

    /** Do not modify this method **/
    private String base64encode(String str) {
      Base64 base = new Base64();
      byte[] strBytes = str.getBytes();
      byte[] encBytes = base.encode(strBytes);
      String encoded = new String(encBytes);
      return encoded;
    }

  }
