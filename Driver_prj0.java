/** CMPT_435L_800
 * Project 0 -- Program Trace Verification
 * Filename: Driver_prj0.java
 * Student Name: Eric Stenton
 * Due Date: January 29, 2020
 * Version 1.0
 *
 * This file contains the printStackTrace function
 * and a main function that examines as well outputs
 * if a trace from standard intput is valid or not.
 */

import java.util.Scanner;
import java.util.Stack;

/**
 * Driver_prj0
 *
 * This class implements a stack to determine the
 * validity of an inputted trace.
 */
public class Driver_prj0 {

  /** printStackTrace
   *  parameters:
   *      callStack -- stack to be printed
   *  return value: nothing
   *
   *  This function prints the stack trace. It will
   *  print items from the top to the bottom of the
   *  given stack.
   */
  public static void printStackTrace(Stack<String> callStack) {
    System.out.println("Stack trace:");

    while (!callStack.isEmpty()) {
      System.out.println(callStack.pop());
    }
  }

  /** main
   *  parameters:
   *      args -- the array of command line argument values
   *  return value: nothing
   * 
   *  This function loops through standard input to determine
   *  if it is a valid trace using a stack.
   */
  public static void main(String[] args) {
    // Here we initialize the scanner variable to read lines of input
    Scanner input = new Scanner(System.in);
    String line;
    String[] lineSplit = new String[2];

    // The callStack is used for storing the names of functions that have been
    // called and not yet returned
    Stack<String> callStack = new Stack<String>();

    // Each time we go through this while loop, we read a line of input.
    // The function hasNext() returns a boolean, which is checked by the while 
    // condition. If System.in has reached the end of the file, it will return 
    // false and the loop will exit.  Otherwise, it will return true and the 
    // loop will continue.
    int lineNumber = 0;
    int maximum_depth = 0;
    int current_depth = 0;
    String functionFromStack;

    while (input.hasNext()) {
      line = input.nextLine();
      lineNumber++;

      lineSplit[0] = line.split(" ")[0]; // Command (call, return)
      lineSplit[1] = line.split(" ")[1]; // Function either called or returned

      // Add a function to the stack
      if ( lineSplit[0].equals("call") ) {
        callStack.push(lineSplit[1]);
        current_depth++;

        // Update maximum depth if needed
        if (current_depth > maximum_depth) {
          maximum_depth = current_depth;
        }

        // Remove a function from the stack
      } else if ( lineSplit[0].equals("return") ) {

        // Check if there is a function left to return from
        if ( !callStack.isEmpty() ) {
          functionFromStack = callStack.peek();

        } else {
          System.out.println("Invalid trace at line " + Integer.toString(lineNumber));
          System.out.println("Returning from " + lineSplit[1] +  " which was not called");
          printStackTrace(callStack);
          return;
        }

        // Check if function removed is the desired one
        if ( !lineSplit[1].equals(functionFromStack) ) {
          System.out.println("Invalid trace at line " + Integer.toString(lineNumber));
          System.out.println("Returning from " + lineSplit[1] + " instead of " + functionFromStack);
          printStackTrace(callStack);
          return;
        }

        // Remove function from top of stack
        // and decrease current depth
        callStack.pop();
        current_depth--;
      }

    }

    // Check if call stack has been emptied
    if ( !callStack.isEmpty() ) {
      System.out.println("Invalid trace at line " + Integer.toString(lineNumber));
      System.out.println("Not all functions returned");
      printStackTrace(callStack);

    } else {
      System.out.println("Valid trace");
      System.out.println("Maximum call depth was " + Integer.toString(maximum_depth));
    }

    return;
  }
}