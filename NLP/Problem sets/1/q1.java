class q1 {
  static void doesItMatch(String theRegex, String toLookin) {
    if (toLookin.matches(theRegex)) {
      System.out.println("Match found");
    } else {
      System.out.println("No match found");
    }
  }

  public static void main(String[] args) {
    doesItMatch("[a-z]+'[a-z]+", "cat's");
    doesItMatch("[a-z]+'[a-z]+", "he'd");
    doesItMatch("[a-z]+'[a-z]+", "We'd");
    doesItMatch("[a-z]+'[a-z]+", "isn't");


  }
}
