//import java.util.List;

//public class StupidBackoffLanguageModel implements LanguageModel {

  //[>* Initialize your data structures in the constructor. <]
  //public StupidBackoffLanguageModel(HolbrookCorpus corpus) {
    //train(corpus);
  //}

  /** Takes a corpus and trains your language model. 
    * Compute any counts or other corpus statistics in this function.
    */
  //public void train(HolbrookCorpus corpus) {

  //}

  /** Takes a list of strings as argument and returns the log-probability of the 
    * sentence using your language model. Use whatever data you computed in train() here.
    */
  //public double score(List<String> sentence) {
    //return 0.0;
  //}
//}









import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.AbstractMap;
import java.util.HashMap;

public class StupidBackoffLanguageModel implements LanguageModel {
  protected Set<String> words; // set of words that occur in training
  protected HashMap<String, HashMap<String, Integer>> wordCounts;
  protected HashMap<String, Integer> unigramCounts;
  double totalNumberOfWordsSeen;
  
  /** Initialize your data structures in the constructor. */
  public StupidBackoffLanguageModel() {
    words = new HashSet<String>();
    totalNumberOfWordsSeen = 0.0;
  }
  
  public StupidBackoffLanguageModel(HolbrookCorpus corpus) {
    unigramCounts = new HashMap<String, Integer>();
    wordCounts = new HashMap<String, HashMap<String, Integer>>();
    words = new HashSet<String>();
    totalNumberOfWordsSeen = 0.0;
    train(corpus);


  }

  public void addOneToWordCount(String word1, String word2) {
    wordCounts.get(word1).put(word2, wordCounts.get(word1).get(word2) + 1);
  }

  /** Takes a corpus and trains your language model. 
    * Compute any counts or other corpus statistics in this function.
    */
  public void train(HolbrookCorpus corpus) {
    HashMap<String, Integer> temp;
    
    for(Sentence sentence : corpus.getData()) { // iterate over sentences
      for(Datum datum : sentence) { // iterate over words
        String word = datum.getWord(); // get the actual word
        words.add(word);
        totalNumberOfWordsSeen += 1.0;
      }
    }

    for (String wordIMinusOne : words) {
      unigramCounts.put(wordIMinusOne, 0);

      temp = new HashMap<String, Integer>();

      for (String wordI : words) {
        temp.put(wordI, 0);
      }

      wordCounts.put(wordIMinusOne, temp);
    }

    boolean isFirst = true;
    String previousWord = "";
    for(Sentence sentence : corpus.getData()) { // iterate over sentences
      isFirst = true;

      for(Datum datum : sentence) { // iterate over words
        String word = datum.getWord(); // get the actual word

        // Fill in unigram counts
        unigramCounts.put(word, unigramCounts.get(word) + 1);

        // Fill in wordCounts (=bigram counts)
        if (isFirst) {
          previousWord = word;
          isFirst = false;
        } else {
          addOneToWordCount(previousWord, word);
          previousWord = word;
        }
      }
    }


  }


  public double uniWP(String word) {
    double result = 0.0;
    double wordUnigramCount = 0.0;

    if (unigramCounts.containsKey(word)) {
      wordUnigramCount = (double)unigramCounts.get(word) * 1.0;
    }

    result = (wordUnigramCount + 1.0) / (totalNumberOfWordsSeen + words.size());

    return result;
  }

  public double wordProba(String previousWord, String word) {
    double result = 0.0;
    double previousWordUnigramCount = 0.0;
    double wordBigramCount = 0.0;

    if (unigramCounts.containsKey(previousWord)) {
      previousWordUnigramCount = (double)unigramCounts.get(previousWord) * 1.0;
    }

    if (wordCounts.containsKey(word) && wordCounts.containsKey(previousWord)) {
      wordBigramCount = (double)wordCounts.get(previousWord).get(word) * 1.0;
    }

    if (wordBigramCount > 0.0) {
      result = wordBigramCount / previousWordUnigramCount;
    } else {
      result = 0.4 * uniWP(word);
    }


    //result = (previousWordUnigramCount + 1) / (totalNumberOfWordsSeen + words.size());

    return result;
  }

  /** Takes a list of strings as argument and returns the log-probability of the 
    * sentence using your language model. Use whatever data you computed in train() here.
    */
  public double score(List<String> sentence) {
    double score = 0.0;

    String previousWord = "";
    boolean isFirst = true;
    for(String word : sentence) {
      if (isFirst) {
        isFirst = false;
        previousWord = word;
        score += Math.log(uniWP(word));
      } else {
        score += Math.log(wordProba(previousWord, word));
        previousWord = word;
      }
    }

    //for (String word : sentence) {
      //score += Math.log(wordProba(word, ""));
    //}


    return score;
  }

}
