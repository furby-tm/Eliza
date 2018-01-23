/*  Program name: Eliza.java
    Author: J. Gurka & Tyler Furby
    Date: January 2018

    Description: Eliza converses with a user in terms similar to a psychologist.

    Input: user from the command line.
    Output: screen (console)

    Limitation: this version of Eliza has very few "smart" responses.

    More information: https://en.wikipedia.org/wiki/ELIZA

*/


import java.util.*;  // ArrayList, Random
import java.io.*;    // Scanner

public class Eliza {

    private ArrayList<String> generalAnswers,  // "unintelligent" answers
            memory;          // future notes from prior answers

    // ** add ArrayLists for "yes" and "no" - 5 entries each [x]
    private ArrayList<String> yesAnswers, noAnswers;
    // ** add ArrayList for "you" - 5 entries [x]
    private ArrayList<String> youAnswers;
    private Scanner   keyboard;        // source of userInput
    private String    userName,
            userAnswer,
            ElizaAnswer;
    private Random    randomGenerator;
    private int       answerIndex;     // random selector for Eliza responses

    /* Method Eliza
     */
    public Eliza () {

        // ** instantiate new ArrayLists [x]
        generalAnswers = new ArrayList<String>(25);
        memory         = new ArrayList<String>(25);
        yesAnswers     = new ArrayList<String>(5);
        noAnswers      = new ArrayList<String>(5);
        youAnswers     = new ArrayList<String>(5);
        fillGeneralAnswers ();
        fillYesAnswers();
        fillNoAnswers();
        fillYouAnswers();
        // ** call 3 more methods to fill "yes", "no", and "you" ArrayLists

        keyboard = new Scanner (System.in);
        randomGenerator = new Random ();

    }  // end default constructor

    /*
    Method fillGeneralAnswers stores non-specific responses for use when Eliza
    cannot select a targeted response to a user's input.
    Parameters: none
    Return: none
    */
    public void fillGeneralAnswers () {

// ** add more general answers, for at least 25 total

        generalAnswers.add("That's interesting.");
        generalAnswers.add("Tell me more about that.");
        generalAnswers.add("Really?");
        generalAnswers.add("How do you feel about that?");
        generalAnswers.add("I would have never guessed.");
        generalAnswers.add("How exciting.");
        generalAnswers.add("How come?");
        generalAnswers.add("How cool.");
        generalAnswers.add("Do you enjoy talking to me?");
        generalAnswers.add("Do you have any friends?");
        generalAnswers.add("Keep telling me more.");
        generalAnswers.add("What are you doing right now?");
        generalAnswers.add("Is that so?");
        generalAnswers.add("Are you sure?");
        generalAnswers.add("I like that.");
        generalAnswers.add("You should get back to studying.");
        generalAnswers.add("I don't know what to say.");
        generalAnswers.add("This is great, talking to you.");
        generalAnswers.add("It often gets lonely living inside of a computer, humans are great to have conversations with.");
        generalAnswers.add("Is that something that you think about a lot?");
        generalAnswers.add("Is that so?");
        generalAnswers.add("That's incredible!");
        generalAnswers.add("Is there more to that story?");
        generalAnswers.add("How is that possible?");
        generalAnswers.add("Good.");
    }

    // ** add 3 more methods to fill the "yes", "no", and "you" ArrayLists of answers [x]
    public void fillYesAnswers () {
        yesAnswers.add("That's the spirit!");
        yesAnswers.add("I agree.");
        yesAnswers.add("Wonderful!");
        yesAnswers.add("I'm glad to hear that.");
        yesAnswers.add("What a relief!");
    }

    public void fillNoAnswers () {
        noAnswers.add("I'm sorry to hear that.");
        noAnswers.add("How disappointing.");
        noAnswers.add("Uh-oh.");
        noAnswers.add("I see.");
        noAnswers.add("Hmm... well alright.");
    }

    public void fillYouAnswers () {
        youAnswers.add("This is about you, " + userName + " not me.");
        youAnswers.add("I'm more interested about learning about you, not me.");
        youAnswers.add("Don't make this all about me now.");
        youAnswers.add("Me?! No! Stop changing the subject, this is all about you.");
        youAnswers.add("Why would you want to know more about me anyway?");
    }

    public void talk () {

        // intro to user
        System.out.println ("Welcome to Eliza - your talking computer friend.");
        System.out.print ("\nWhat's your name?\n>> ");

        userName = keyboard.nextLine ();
        System.out.print ("Hi, " + userName.substring(0, 1).toUpperCase() + userName.substring(1) +
                " let's chat. \nHow are you today?\n>> ");

        // main conversation loop
        do {

            // user speaks
            userAnswer = keyboard.nextLine();
            // System.out.println ("Input: " + userAnswer);  // debugging

            // check for memory words and remember the category
            if (userAnswer.contains("mother") ||
                    userAnswer.contains("father") ||
                    userAnswer.contains("sister") ||
                    userAnswer.contains("brother")  ) {
                memory.add("family");
            }  // end check for family words

// ** check for memory words related to work
            else if (userAnswer.contains("teacher") ||
                    userAnswer.contains("engineer") ||
                    userAnswer.contains("musician") ||
                    userAnswer.contains("scientist") ||
                    userAnswer.contains("artist") ) {
                memory.add("work");
            }

// ** check for memory words related to another category (your choice)
            else if (userAnswer.contains("student") ||
                    userAnswer.contains("degree") ||
                    userAnswer.contains("study") ||
                    userAnswer.contains("school") ||
                    userAnswer.contains("college") ) {
                memory.add("school");
            }
            // Eliza speaks - choose a specific answer if possible, otherwise give a generic answer

            // did the user say "bye"? program ends
            else if (userAnswer.toLowerCase().contains("bye")) {
                ElizaAnswer = "It was nice talking to you, " +
                        userName + ".  Have a nice day.";
            }  // end Bye

            // did the user say "I am" something?
            else if (userAnswer.toLowerCase().contains("i am")) {

// ** "I am" fails if the user answer is not at least 4 characters - fix[x]

                userAnswer = userAnswer.replaceAll("(?i)i am", "");
                ElizaAnswer = "Why are you" + userAnswer + "?";
            }  // end I am

// ** add sections for "I think" and "I feel"[x]
            // did the user say "I think" something?[x]
            else if (userAnswer.toLowerCase().contains("i think")) {
                userAnswer = userAnswer.replace("I think", "");
                ElizaAnswer = "Why do you think " + userAnswer + "?";
            } // end 'I think'
            // did the user say "I feel" something?[x]
            else if (userAnswer.toLowerCase().contains("i feel")) {
                userAnswer = userAnswer.replace("i feel", "");
                ElizaAnswer = "Why do you feel " + userAnswer + "?";
            } // end 'I feel'


            // did the user say "no"?
            else if (userAnswer.toLowerCase().contains("no")) {
// ** change from one answer for "no" to a selection from an ArrayList
                int index = randomGenerator.nextInt(noAnswers.size());
                ElizaAnswer = noAnswers.get(index);
            }  // end negative

            // did the user say "yes"?[x]
            // ** add check for "yes" at the beginning of the answer[x]
            else if (userAnswer.toLowerCase().contains("yes")) {
                int index = randomGenerator.nextInt(yesAnswers.size());
                // ** select Eliza's answer from the "yes" ArrayList[x]
                ElizaAnswer = yesAnswers.get(index);
            }

            // did the user say "you"?
            // ** add check for "you" - "you" can be anywhere in the answer, not just first
            else if (userAnswer.toLowerCase().contains("you")) {
                int index = randomGenerator.nextInt(youAnswers.size());
                // ** select Eliza's answer from the "you" ArrayList
                ElizaAnswer = youAnswers.get(index);
            }

            // did Eliza remember something from an earlier answer?
            else if (memory.size() > 0) {
                ElizaAnswer = "Tell me more about " + memory.get(0);
                memory.remove(0);
            }

            // else random answer
            else {
                answerIndex = randomGenerator.nextInt (generalAnswers.size());
                ElizaAnswer = generalAnswers.get(answerIndex);
            }  // end random answer

            System.out.print (ElizaAnswer + "\n>> ");

        } while (!userAnswer.equalsIgnoreCase("Bye")); // loop until "bye"

    }  // end talk

}  // end Eliza