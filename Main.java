/*
* Author Name: Xin Li
* Prgrame: Anagram
* */

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File(args[0]));

        List<String> dict = new ArrayList<String>();
        LetterInventory phase = new LetterInventory(args[1]);
        ArrayList<String> words = new ArrayList<>();

        // build dictinary in phase
        while (input.hasNextLine()) {
            String word = input.nextLine();
            if (phase.contains(word)) {
                dict.add(word);
                words.add(word);
            }
        }
        // Output phase, dictionary
        System.out.println("Phrase to scramble: " + args[1]);
        System.out.println("\nAll words found in " + args[1] + ":\n" + words);
        System.out.println("\nAnagrams for " + args[1] + ":");
        Anagram anagram = new Anagram(dict);
        // produce the anagrams
        anagram.produce(args[1], Integer.parseInt(args[2]));
    }
}

class Anagram {
    private List<String> dict;
    private Map<String, LetterInventory> wordMap;

    public Anagram(List<String> dic) {
        dict = dic;
        wordMap = new HashMap<String, LetterInventory>();
        for (String word: dict) {
            wordMap.put(word, new LetterInventory(word));
        }
    }

    /*
    * produce the anagrams
    * @ String s: the phase
    * @ count: max length of anagram
    * */
    public void produce(String s, int count) {
        if (count < 0) {
            throw new IllegalArgumentException();
        }
        List<String> subDict = new ArrayList<String>();
        LetterInventory subNode = new LetterInventory(s);
        // parse each possible word
        for (String word : dict) {
            if (subNode.contains(wordMap.get(word))) {
                subDict.add(word);
            }
        }
        List<String> combinations = new ArrayList<String>();
        // backtracking function
        produce(subNode, subDict, count, combinations);
    }

    /*
    * produce the anagram backtracking
    * @ node: current node in tree
    * @ dict: dictionary
    * @ count: max length of current anagram
    * @ phase: current phase
    * */
    private void produce(LetterInventory node, List<String> dict, int count, List<String> phases) {
        // if valid anagram found, output
        if (node.isEmpty()) {
            System.out.println(phases);
        }
        // parse node in next level of tree
        if (phases.size() < count || count == 0) {
            for (int i = 0; i < dict.size(); i++) {
                LetterInventory word = new LetterInventory(dict.get(i));
                if (node.contains(word)) {
                    phases.add(dict.get(i));
                    LetterInventory tmp = new LetterInventory(node.toString());
                    tmp.subtract(word);
                    produce(tmp, dict, count, phases);
                    phases.remove(phases.size() - 1);
                }
            }
        }
    }
}
