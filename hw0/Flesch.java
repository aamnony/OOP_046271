package hw0;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Flesch {
    private static final String SYLLABLES = "aeiouy";

    /**
     * @effects Calculates the Flesch reading ease grade of a given file.
     * 
     * @param filePath
     *            The path to the file.
     * @return The Flesch reading ease grade of the given file.
     * @throws FileNotFoundException
     *             If given filePath doesn't point to a file.
     */
    public static double getReadingEase(String filePath) throws FileNotFoundException {
        int sentenceCount = 0;
        int wordCount = 0;
        int syllableCount = 0;

        // Count sentences in the file.
        Scanner scanner = new Scanner(new File(filePath), "UTF-8");
        scanner.useDelimiter("[.,;?!]");

        while (scanner.hasNext()) {
            String sentence = scanner.next();
            if (!sentence.trim().isEmpty()) {
                sentenceCount++;
            }
        }

        scanner.close();

        // Count words and vowels in the file.
        scanner = new Scanner(new File(filePath), "UTF-8");
        // Use regex to match any character other than ()*&^%$#@!_+-=[]{}?;:',.
        scanner.useDelimiter("[\\ \\(\\)\\*\\&\\^\\%\\$\\#\\@\\!\\_\\+\\-\\=\\[\\]\\{\\}\\?\\;\\:\\'\\,\\.]");

        while (scanner.hasNext()) {
            String word = scanner.next();
            if (word.isEmpty()) {
                continue;
            }
            int wordSyllableCount = 0;
            wordCount++;

            boolean prevSyllable = false;
            for (int i = 0; i < word.length(); i++) {
                char c = Character.toLowerCase(word.charAt(i));
                if ((i == word.length() - 1) && (c == 'e')) {
                    // Last character of the word is 'e', don't treat it as a syllable.
                    continue;
                }
                boolean currSyllable = (SYLLABLES.indexOf(c) >= 0);
                if (currSyllable && !prevSyllable) {
                    wordSyllableCount++;
                }
                prevSyllable = currSyllable;
            }
            syllableCount += (wordSyllableCount == 0) ? 1 : wordSyllableCount;
        }
        scanner.close();

        return getReadingEaseGrade(sentenceCount, wordCount, syllableCount);
    }

    /**
     * @effects Calculates the Flesch reading ease grade of a given set of
     *          parameters describing a text.
     * 
     * @param sentenceCount
     *            The number of sentences in the text.
     * @param wordCount
     *            The number of words in the text.
     * @param syllableCount
     *            The number of syllables in the text.
     * @return The Flesch reading ease grade of the given file.
     */
    private static double getReadingEaseGrade(int sentenceCount, int wordCount, int syllableCount) {
        if (sentenceCount == 0 || wordCount == 0) {
            return 100;
        }
        final double syllables = (double) syllableCount;
        final double words = (double) wordCount;
        final double sentences = (double) sentenceCount;
        return 206.835 - 84.6 * (syllables / words) - 1.015 * (words / sentences);
    }

    /**
     * @effects Displays the Flesch reading ease grade of a given file.
     * 
     * @param args
     *            [0] - The path to the file.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Not enough arguments!");
            return;
        }

        try {
            double fleschReadingEase = Flesch.getReadingEase(args[0]);
            System.out.printf("Flesch reading ease grade of \"%s\" is %.2f\n", args[0], fleschReadingEase);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
