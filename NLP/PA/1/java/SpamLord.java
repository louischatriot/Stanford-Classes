// CS124 HW1 SpamLord
import java.util.regex.*;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.io.*;
import org.apache.commons.lang3.StringEscapeUtils;
        
public class SpamLord {

  /*
   * You do not need to modify anything in the Contact class.
   * This class encapsulates the basic information associated with
   * an e-mail or phone number for this assignment.  It has three
   * data members:
   *    filename // the name of the file in which the contact item was found
   *    type     // the type of contact information found: either "e" or "p"
   *    value    // the actual string representatino of the e-mail or phone number
   *             // see assignment description for details
   * you can ignore the other functions which are just necessary for correct
   * behavior when used an element of a java.uitl.Set
   */
  static class Contact implements Comparable<Contact>{
    private String fileName;
    private String type;
    private String value;
    public Contact() {}
    public Contact(String fileName,String type,String value) {
      this.fileName = fileName;
      this.type = type;
      // automatically change value to lower case upon construction;
      this.value = value.toLowerCase();
    }
    public String getFileName() {return fileName;}
    public String getType() {return type;}
    public String getValue() {return value;}
    @Override
    public boolean equals(Object o) {
      Contact c = (Contact) o;
      return (fileName.equals(c.fileName) && type.equals(c.type) && value.equals(c.value));
    }
    @Override
    public int hashCode() {
      return 31*fileName.hashCode() + 17*type.hashCode() + value.hashCode();
    }
    public int compareTo(Contact c) {
      int fileNameCmp = fileName.compareTo(c.fileName);
      if (fileNameCmp != 0) {
        return fileNameCmp;
      }
      int typeCmp = type.compareTo(c.type);
      if (typeCmp != 0) {
        return typeCmp;
      }
      return value.compareTo(c.value);
    }
    @Override public String toString() {
      return fileName + "\t" + type + "\t" + value;
    }
  }
  
  // Example pattern for extracting e-mail addresses
  private Pattern myFirstPattern = Pattern.compile("([A-Za-z][A-Za-z0-9.\\-_]*)[ ]?(@)[ ]?([A-Za-z][A-Za-z0-9\\-_.]*\\.[A-Za-z]+)");
  private Pattern pattern2 = Pattern.compile("([A-Za-z][A-Za-z0-9.\\-_]*)[ ]*WHERE[ ]*([A-Za-z][A-Za-z0-9.\\-_]*)[ ]*DOM[ ]*([A-Za-z]+)");
  private Pattern pattern3 = Pattern.compile("([A-Za-z][A-Za-z0-9.\\-_]*)[ ]*\\(at\\)[ ]*([A-Za-z][A-Za-z0-9\\-_.]*\\.[A-Za-z]+)");
  private Pattern pattern4 = Pattern.compile("([A-Za-z0-9\\-_]+( do?t [A-Za-z0-9\\-_]+)*)[ ]*at[ ]*([A-Za-z]+( do?t [A-Za-z]+)* do?t (edu|com|org|net|gov))");
  private Pattern pattern7 = Pattern.compile("(([A-Za-z0-9]-)+)@-(([A-Za-z0-9]-)+\\.(-[A-Za-z0-9])+)");
  private Pattern pattern5 = Pattern.compile("obfuscate\\( ?['\"](.*)['\"] ?, ?['\"](.*)['\"] ?\\)");
  private Pattern pattern51 = Pattern.compile("([A-Za-z0-9.\\-_]+) \\(followed by (“|\"|”|')@([A-Za-z0-9.\\-_]+\\.[A-Za-z]+)(“|\"|”|')\\)");
  private Pattern pattern8 = Pattern.compile("([A-Za-z][A-Za-z0-9.\\-_]*)[ ]*at[ ]*([A-Za-z]+(;[A-Za-z]+)*;(edu|com|org|net|gov))");
  private Pattern pattern9 = Pattern.compile("(pal at cs stanford edu|lam at cs.stanford.edu)");

  private Pattern pattern11 = Pattern.compile("\\(?([0-9]{3,3})\\)?[ \\-]([0-9]{3,3})[ \\-]([0-9]{4,4})");
  private Pattern pattern12 = Pattern.compile("\\(([0-9]{3,3})\\)[ \\-]?([0-9]{3,3})[ \\-]([0-9]{4,4})");


  /* 
   * TODO
   * This should return a list of Contact objects found in the input. 
   * You can change anything internal to this function but make sure you
   * leave the interface (arguments and return value) unchanged because
   * it will be directly called by the submission script.
   */  
  public List<Contact> processFile(String fileName, BufferedReader input) {
    List<Contact> contacts = new ArrayList<Contact>();
    // for each line
    Matcher m;
    String email, phone;

    try {
      for(String line = input.readLine(); line != null; line = input.readLine()) {
    //if (fileName.equals("balaji")) { System.out.println(line); }
    //
        //line = line.replaceAll("&#x40;", "@");
        //line = line.replaceAll("&#64;", "@");
        //line = line.replaceAll("&#x20;", " ");
        //line = line.replaceAll("&#32;", " ");
        //line = line.replaceAll("&#x2E;", ".");
        //line = line.replaceAll("&#46;", ".");
        //line = line.replaceAll("&#x96;", "-");
        //line = line.replaceAll("&#150;", "-");
        //line = line.replaceAll("&#x97;", "-");
        //line = line.replaceAll("&#151;", "-");

        line = StringEscapeUtils.unescapeHtml4(line);

        m = myFirstPattern.matcher(line);
        while(m.find()) {
          //System.out.println("trouve");
          email = m.group(1) + "@" + m.group(3);
          Contact contact = new Contact(fileName,"e",email);
          contacts.add(contact);
        }


        m = pattern2.matcher(line);
        while(m.find()) {
          email = m.group(1) + "@" + m.group(2) + "." + m.group(3);
          Contact contact = new Contact(fileName,"e",email);
          contacts.add(contact);
        }


        m = pattern3.matcher(line);
        while(m.find()) {
          email = m.group(1) + "@" + m.group(2);
          Contact contact = new Contact(fileName,"e",email);
          contacts.add(contact);
        }

        m = pattern4.matcher(line);
        while(m.find()) {
          email = m.group(1).replaceAll(" do?t ", ".") + "@" + m.group(3).replaceAll(" do?t ", ".");
          Contact contact = new Contact(fileName,"e",email);
          contacts.add(contact);
        }



        m = pattern7.matcher(line);
        while(m.find()) {
          email = m.group(1).replaceAll("-", "") + "@" + m.group(3).replaceAll("-", "");
          Contact contact = new Contact(fileName,"e",email);
          contacts.add(contact);
        }
        m = pattern5.matcher(line);
        while(m.find()) {
          email = m.group(2) + "@" + m.group(1);
          Contact contact = new Contact(fileName,"e",email);
          contacts.add(contact);
        }

        m = pattern51.matcher(line);
        while(m.find()) {
          email = m.group(1) + "@" + m.group(3);
          Contact contact = new Contact(fileName,"e",email);
          contacts.add(contact);
        }
        m = pattern9.matcher(line);
        while(m.find()) {
          email = m.group(1).replaceAll(" at ", "@");
          email = email.replaceAll(" ", ".");
          Contact contact = new Contact(fileName,"e",email);
          contacts.add(contact);
        }


        m = pattern8.matcher(line);
        while(m.find()) {
          email = m.group(1) + "@" + m.group(2).replaceAll(";", ".");
          Contact contact = new Contact(fileName,"e",email);
          contacts.add(contact);
        }

        m = pattern11.matcher(line);
        while(m.find()) {
          phone = m.group(1) + "-" + m.group(2) + "-" + m.group(3);
          Contact contact = new Contact(fileName,"p",phone);
          contacts.add(contact);
        }

        m = pattern12.matcher(line);
        while(m.find()) {
          phone = m.group(1) + "-" + m.group(2) + "-" + m.group(3);
          Contact contact = new Contact(fileName,"p",phone);
          contacts.add(contact);
        }
      }
      input.close();
    } catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
    return contacts;
  }

  /*
   * You should not need to edit this, nor should you alter it's interface
   * because it will also be called direclty by the submission  program
   */
  public List<Contact> processDir(String dirName) {
    List<Contact> contacts = new ArrayList<Contact>();
    for(File f: new File(dirName).listFiles()) {
      if (f.getName().startsWith(".")) 
        continue;
      try {
        BufferedReader input = new BufferedReader(new FileReader(f));
        contacts.addAll(processFile(f.getName(), input));
      } catch(IOException e) {
        e.printStackTrace();
        System.exit(1);
      }
    }
    return contacts;
  }

  /*
   * You should not need to edit this function
   * It simply reads in a tsv gold file and returns a list of
   * Contacts
   */
  private List<Contact> loadGold(String goldPath) {
    List<Contact> gold = new ArrayList<Contact>();
    try {
      BufferedReader input = new BufferedReader(new FileReader(goldPath));
      
      String[] toks;
      for(String line = input.readLine(); line != null; line = input.readLine()) {
        toks = line.split("\t");
        Contact contact = new Contact(toks[0],toks[1],toks[2]);
        gold.add(contact);
      }
      input.close();

    } catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
    return gold;
  }

  /*
   * You should not need to edit this.
   * This is just a utility function which turns a Set into
   * a sorted list for convenience when looking at the output.
   */
  private List<Contact> asSortedList(Set<Contact> set) {
    Contact[] c = new Contact[0];
    List<Contact> list = Arrays.asList(set.toArray(c));
    Collections.sort(list);
    return list;
  }

  /*
   * You should not need to edit this.
   * This takes in two Lists of Contacts and prints out the intersection
   * and differences, which can be thought of as true positives, false
   * positives and false negatives.
   */
  private void score(List<Contact> guesses, List<Contact> gold) {
    Set<Contact> guess_set = new HashSet<Contact>();
    guess_set.addAll(guesses);
    Set<Contact> gold_set = new HashSet<Contact>();
    gold_set.addAll(gold);

    Set<Contact> tp = new HashSet<Contact>(guess_set);
    System.out.println("guess_set.size()="+guess_set.size()+"\tgold_set.size()="+gold_set.size());
    tp.retainAll(gold_set);
    List<Contact> tp_list = asSortedList(tp);
    System.out.println("True Positives (" + tp_list.size() +")\t###############################");
    for (Contact contact : tp_list) {
      System.out.println(contact);
    }

    Set<Contact> fp = new HashSet<Contact>(guess_set);
    fp.removeAll(gold_set);
    List<Contact> fp_list = asSortedList(fp);
    System.out.println("False Positives (" + fp_list.size() +")\t###############################");
    for (Contact contact : fp_list) {
      System.out.println(contact);
    }

    Set<Contact> fn = new HashSet<Contact>(gold_set);
    fn.removeAll(guess_set);
    List<Contact> fn_list = asSortedList(fn);
    System.out.println("False Negatives (" + fn_list.size() +")\t###############################");
    for (Contact contact : fn_list) {
      System.out.println(contact);
    }

    System.out.println("Summary: tp=" + tp.size() + "\tfp=" + fp.size() + "\tfn=" + fn.size());
  }

  /* 
   * main takes a directory and a file with the Gold contacts.
   * it processes each file in the directory, extracting any contacts
   * and compares them to the contacts listed in the gold file
   */
  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println("usage:\tSpamLord <data_dir> <gold_file>");
      System.exit(0);
    }
    SpamLord vader = new SpamLord();
    List<Contact> guesses = vader.processDir(args[0]);
    List<Contact> gold = vader.loadGold(args[1]);
    vader.score(guesses,gold);
  }
}
