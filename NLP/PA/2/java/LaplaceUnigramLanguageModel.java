import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.AbstractMap;
import java.util.HashMap;

public class LaplaceUnigramLanguageModel implements LanguageModel {

  protected Set<String> words; // set of words that occur in training
  protected HashMap<String, Integer> wordCounts;
  double totalNumberOfWordsSeen;
  
  /** Initialize your data structures in the constructor. */
  public LaplaceUnigramLanguageModel() {
    words = new HashSet<String>();
    wordCounts = new HashMap<String, Integer>();
    totalNumberOfWordsSeen = 1.0;
  }
  
  public LaplaceUnigramLanguageModel(HolbrookCorpus corpus) {
    words = new HashSet<String>();
    wordCounts = new HashMap<String, Integer>();
    totalNumberOfWordsSeen = 1.0;
    train(corpus);

    //System.out.println("Test add1");
    //System.out.println(wordCounts.toString());
    //addOneToWordCount("maison");
    //addOneToWordCount("chat");
    //addOneToWordCount("mois");
    //addOneToWordCount("bouffe");
    //System.out.println(wordCounts.toString());
    //addOneToWordCount("chat");
    //addOneToWordCount("mois");
    //System.out.println(wordCounts.toString());
    //System.out.println(wordCounts.size());
  }

  public void addOneToWordCount(String key) {
    if (wordCounts.containsKey(key)) {
      wordCounts.put(key, wordCounts.get(key) + 1);
    } else {
      wordCounts.put(key, 1);
    }
  }

  /** Takes a corpus and trains your language model. 
    * Compute any counts or other corpus statistics in this function.
    */
  public void train(HolbrookCorpus corpus) {
    for(Sentence sentence : corpus.getData()) { // iterate over sentences
      for(Datum datum : sentence) { // iterate over words
        String word = datum.getWord(); // get the actual word
        words.add(word);
        addOneToWordCount(word);
        totalNumberOfWordsSeen += 1.0;
      }
    }
  }

  public double wordProba(String word) {
    double result = 0.0;
    double thisWordcount = 0.0;

    if (wordCounts.containsKey(word)) {
      thisWordcount = (double)wordCounts.get(word) * 1.0;
    }

    result = (thisWordcount + 1) / (totalNumberOfWordsSeen + words.size());

    return result;
  }

  /** Takes a list of strings as argument and returns the log-probability of the 
    * sentence using your language model. Use whatever data you computed in train() here.
    */
  public double score(List<String> sentence) {
    double score = 0.0;
    for(String word : sentence) { // iterate over words in the sentence
      score += Math.log(wordProba(word));
    }
    // NOTE: a simpler method would be just score = sentence.size() * - Math.log(words.size()).
    // we show the 'for' loop for insructive purposes.
    return score;
  }
}
