/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static android.R.attr.id;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    private ArrayList<String> wordList;               //n
    private HashMap<String,ArrayList<String>> lettersToWord;
    private HashSet<String> wordSet;
    private HashMap<Integer,ArrayList<String>> sizeToWord;
    private int wordLength =DEFAULT_WORD_LENGTH;
    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        wordList=new ArrayList<String>();            //n
        wordSet=new HashSet<>();                      //n
        lettersToWord=new HashMap<>();
        sizeToWord=new HashMap<>();//n
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);               //n
            wordSet.add(word);                //n
            String sortedWord = sortLetters(word);   //n
            if (lettersToWord.containsKey(sortedWord)) {                        //n
                ArrayList<String> listWords = lettersToWord.get(sortedWord);
                listWords.add(word);
                lettersToWord.put(sortedWord, listWords);
            } else {                                                            //n
                ArrayList<String> listWords = new ArrayList<>();
                listWords.add(word);
                lettersToWord.put(sortedWord, listWords);
            }
            if (sizeToWord.containsKey(word.length())) {                        //n
                ArrayList<String> listWords = sizeToWord.get(word.length());
                listWords.add(word);
                sizeToWord.put(word.length(), listWords);
            }else {                                                            //n
                ArrayList<String> listWords = new ArrayList<>();
                listWords.add(word);
                sizeToWord.put(word.length(), listWords);
            }


        }
    }

    public boolean isGoodWord(String word, String base) {

        //return true;
        return (wordSet.contains(word) && !word.contains(base));

    }

    public List<String> getAnagrams(String targetWord) {
        //ArrayList<String> result = new ArrayList<String>();
        //String sortedTword=sortLetters(targetWord);
        //for(String wword : wordList)                        //n
        //{
        //    if(wword.length()==4) {
          //      if (sortLetters(wword).equals(sortedTword))
            //        result.add(wword);
           // }
       // }
        return lettersToWord.get(sortLetters(targetWord));
        //return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        char[] alphabets = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        for (char letter : alphabets) {
            if (lettersToWord.containsKey(sortLetters(word + letter))) {
                ArrayList<String> tempArray = lettersToWord.get(sortLetters(word + letter));
                for(String anagramw : tempArray)
                {
                    if(isGoodWord(anagramw,word))    //new
                       result.add(anagramw);
                }
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {

        int len=0;int index;
        ArrayList<String> temp = sizeToWord.get(wordLength);
        while(len<MIN_NUM_ANAGRAMS)
        {
            int sizeArrayLength = temp.size();
            int wordLocate = new Random().nextInt(sizeArrayLength);
            String word = temp.get(wordLocate);
            len = getAnagramsWithOneMoreLetter(word).size();
            if (len >= MIN_NUM_ANAGRAMS) {
                if (wordLength < MAX_WORD_LENGTH) wordLength++;
                return word;
            } else {
                temp.remove(wordLocate);
            }
           // index=random.nextInt(wordList.size());
           // String word=wordList.get(index);
           // len=lettersToWord.get(sortLetters(word)).size();
           // if(len>=MIN_NUM_ANAGRAMS)
             //   return word;
        }
        return "Hello";
    }

    private String sortLetters(String word)
    {
        char[] w_l=word.toCharArray();
        Arrays.sort(w_l);
        String sortedWord=new String(w_l);
        return sortedWord;

    }
}
