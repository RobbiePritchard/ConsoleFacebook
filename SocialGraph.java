import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


//import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;


public class SocialGraph extends UndirectedGraph<String> {

	/**
	 * Creates an empty social graph.
	 * 
	 * DO NOT MODIFY THIS CONSTRUCTOR.
	 */
	public SocialGraph() {
		super();
	}

	/**
	 * Creates a graph from a preconstructed hashmap.
	 * This method will be used to test your submissions. You will not find this
	 * in a regular ADT.
	 * 
	 * DO NOT MODIFY THIS CONSTRUCTOR.
	 * DO NOT CALL THIS CONSTRUCTOR ANYWHERE IN YOUR SUBMISSION.
	 * 
	 * @param hashmap adjacency lists representation of a graph that has no
	 * loops and is not a multigraph.
	 */
	public SocialGraph(HashMap<String, ArrayList<String>> hashmap) {
		super(hashmap);
	}

	public Set<String> friendsOfFriends(String person) {
		if(person == null) throw new IllegalArgumentException();
		return this.getNeighbors(person);

	}

	public List<String> getPathBetween(String pFrom, String pTo) {

		if(!hashmap.containsKey(pFrom) || !hashmap.containsKey(pTo)) throw new IllegalArgumentException();

		Map<String, Boolean> visited = new HashMap<String, Boolean>();
		Map<String, String> previous = new HashMap<String, String>();
		//list for storing directions
		List<String> dir = new LinkedList<String>();
		//queue for possible paths
		Queue<String> queue = new LinkedList<String>();
		
		String current = pFrom;
		queue.add(current);
		visited.put(current, true);
		while(!queue.isEmpty()){
			//remove item in the queue
			current = queue.remove();
			if (current.equals(pTo)){
				break;
			}else{
				//for each friend, if it hasn't been visited add it to visited and and previous.
				for(String s : getNeighbors(current)){
					if(!visited.containsKey(s)){
						queue.add(s);
						visited.put(s, true);
						previous.put(s, current);
					}
				}
			}
		}
		//add previous to directions
		for(String finalListItem = pTo; finalListItem != null; finalListItem = previous.get(finalListItem)) {
			dir.add(0, finalListItem);;
		}
		return dir;


	}

	/**
	 * Returns a pretty-print of this graph in adjacency matrix form.
	 * People are sorted in alphabetical order, "X" denotes friendship.
	 * 
	 * This method has been written for your convenience (e.g., for debugging).
	 * You are free to modify it or remove the method entirely.
	 * THIS METHOD WILL NOT BE PART OF GRADING.
	 *
	 * NOTE: this method assumes that the internal hashmap is valid (e.g., no
	 * loop, graph is not a multigraph). USE IT AT YOUR OWN RISK.
	 *
	 * @return pretty-print of this graph
	 */
	public String pprint() {
		// Get alphabetical list of people, for prettiness
		List<String> people = new ArrayList<String>(this.hashmap.keySet());
		Collections.sort(people);

		// String writer is easier than appending tons of strings
		StringWriter writer = new StringWriter();

		// Print labels for matrix columns
		writer.append("   ");
		for (String person: people)
			writer.append(" " + person);
		writer.append("\n");

		// Print one line of social connections for each person
		for (String source: people) {
			writer.append(source);
			for (String target: people) {
				if (this.getNeighbors(source).contains(target))
					writer.append("  X ");
				else
					writer.append("    ");
			}
			writer.append("\n");

		}

		// Remove last newline so that multiple printlns don't have empty
		// lines in between
		String string = writer.toString();
		return string.substring(0, string.length() - 1);
	}

}



