import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class SocialNetworkingApp {
    static SocialGraph sGraph = new SocialGraph();

    /**
     * Returns a social network as defined in the file 'filename'.
     * See assignment handout on the expected file format.
     * @param filename filename of file containing social connection data
     * @return
     * @throws FileNotFoundException if file does not exist
     */
    public static SocialGraph loadConnections(String filename) throws FileNotFoundException {
    	File newFile = new File(filename);
        Scanner sc = new Scanner(newFile);
        while(sc.hasNext()){
        	String nextLine = sc.nextLine();
        	String[] names = nextLine.split("\\s+");
        	for(String a : names){
            	if(!sGraph.getAllVertices().contains(a))
            		sGraph.addVertex(a);
        	}
        	String firstName = names[0];
        	names[0] = null;
        	for(String a : names){
        		if(a != null)
        			sGraph.addEdge(firstName, a);
        	}
        }
        sc.close();
        return sGraph;
    }

    static Scanner stdin = new Scanner(System.in);
    static SocialGraph graph;
    static String prompt = ">> ";  // Command prompt

    /**
     * Access main menu options to login or exit the application.
     * 
     * THIS METHOD HAS BEEN IMPLEMENTED FOR YOU.
     */
    public static void enterMainMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.print(prompt);
            String[] tokens = stdin.nextLine().split(" ");
            String cmd = tokens[0];
            String person = (tokens.length > 1 ? tokens[1] : null);

            switch(cmd) {
                case "login":
                    System.out.println("Logged in as " + person);
                    enterSubMenu(person);
                    System.out.println("Logged out");
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }
    }

    /**
     * Access submenu options to view the social network from the perspective
     * of currUser. Assumes currUser exists in the network.
     * @param currUser
     */
    public static void enterSubMenu(String currUser) {

        // Define the set of commands that have no arguments or one argument
        String commands =
                "friends fof logout print\n" +
                "connection friend unfriend";
        Set<String> noArgCmds = new HashSet<String>
                (Arrays.asList(commands.split("\n")[0].split(" ")));
        Set<String> oneArgCmds = new HashSet<String>
                (Arrays.asList(commands.split("\n")[1].split(" ")));

        boolean logout = false;
        while (!logout) {
            System.out.print(prompt);

            // Read user commands
            String[] tokens = stdin.nextLine().split(" ");
            String cmd = tokens[0];
            String otherPerson = (tokens.length > 1 ? tokens[1] : null);

            // Reject erroneous commands
            // You are free to do additional error checking of user input, but
            // this isn't necessary to receive a full grade.
            if (tokens.length == 0) continue;
            if (!noArgCmds.contains(cmd) && !oneArgCmds.contains(cmd)) {
                System.out.println("Invalid command");
                continue;
            }
            if (oneArgCmds.contains(cmd) && otherPerson == null) {
                System.out.println("Did not specify person");
                continue;
            }

            switch(cmd) {

            case "connection": {
                int size = sGraph.getPathBetween(currUser, otherPerson).size();
                if(size == 1){
                	System.out.println("You are not connected to "+otherPerson+".");
                }
                else{
                	System.out.println(sGraph.getPathBetween(currUser, otherPerson));
                }
                break;
            }

            case "friends": {
                System.out.println(sGraph.getNeighbors(currUser));
                break;
            }

            case "fof": {
            	ArrayList<String> fof = new ArrayList<String>();
                for(String s : sGraph.getNeighbors(currUser))
                	for(String st : sGraph.getNeighbors(s))
                		if(!fof.contains(st))
                			fof.add(st);
                System.out.println(fof);
                	
                break;
            }

            case "friend": {
            	if(sGraph.getNeighbors(currUser).contains(otherPerson))
            		System.out.println("You are already friends with " + otherPerson);
            	else{
            		sGraph.addEdge(currUser, otherPerson);
            		System.out.println("You are now friends with "+otherPerson);
            	}
                break;
            }

            case "unfriend": {
            	if(!sGraph.getNeighbors(currUser).contains(otherPerson))
            		System.out.println("You are already not friends with "+otherPerson);
            	else{
                    sGraph.removeEdge(currUser, otherPerson);
                    System.out.println("You are no longer friends with "+otherPerson);
            	}
                break;
            }

            case "logout":
                logout = true;
                break;
            }  // End switch
        }
    }

    /**
     * Commandline interface for a social networking application.
     *
     * THIS METHOD HAS BEEN IMPLEMENTED FOR YOU.
     *
     * @param args
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java SocialNetworkingApp <filename>");
            return;
        }
        try {
            graph = loadConnections(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        enterMainMenu();

    }

}
