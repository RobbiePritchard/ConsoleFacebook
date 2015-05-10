# ConsoleFacebook
cs367 final project

*Goals*

Goals of this assignment:

Familiarize yourself with common methods in a graph ADT
Implement an adjacency-list implementation of graphs and basic graph methods
Familiarize yourself with the Java implementation of HashMap and sort

*Description*

In this assignment, you will implement a simplified social network in the vein of Facebook. By harnessing the power of computer science, you will be able to create and destroy virtual relationships. The underlying data structure will be an undirected graph, where nodes represent individual people and edges represent friendship between two people. Each person is represented by a 3-letter name, where the first letter is capitalized and the other two are in small letters, e.g. “Bob”.

Note that since we've chosen an undirected graph, we are implicitly modelling social connections as commutative, i.e., X being a friend of Y implies that Y is a friend of X.

*ADJACENCY LISTS OF AN UNDIRECTED GRAPH*

You will implement the social graph using adjacency lists.

Consider the following undirected graph where characters represent vertices and lines represent edges. 

        A
       / \
      /   \
     B --- C --- D
Conceptually, its adjacency lists representation would look something like:

A -> [B, C]
B -> [A, C]
C -> [A, B, D]
D -> [C]
The -> arrow indicates a mapping from a vertex to its adjacency list; the adjacency list is composed of the vertex’s adjacent vertices. Also notice that since the graph is undirected, an edge between A and B means that A is in B's adjacency list and B is also in A's adjacency list. You will see a more concrete description in the description of the UndirectedGraph class below. 
Here’s some examples of common graph operations on an adjacency lists representation of an undirected graph.

To remove an edge - For example, to remove an edge between B and C, remove the C from B’s adjacency list and remove the B from C’s adjacency list.
To insert an edge - For example, to reinsert the edge between B and C, just do the reverse of the remove operation. Add C to B’s adjacency list and add B to C’s adjacency list. Note that the order of the vertices in an adjacency list does not matter.
To add a vertex - For example, to add a neighborless E, add a mapping between E and an empty list. Informally, that would be adding a row ‘E -> []’ to the representation above.
We choose the adjacency lists representation instead of an adjacency matrix because the former is a data-efficient way of storing sparse graphs; and social networks are usually sparse - the typical number of friends a person has is far fewer than the total population.

*HASHTABLES*

In this assignment, you will use Java HashMaps and HashSets. The underlying data structure of both of these classes is a hashtable, which we will describe briefly here. You will not need to implement hashtables in this assignment. Hashtables will be covered towards the end of the course. The purpose of the description below is to give you some intuition behind how these classes work behind the scenes, and explain why we choose them for the assignment.

Recall that in a binary search tree, it takes O(logN) time to find the location of a key in the tree. This O(logN) time comes from having to traverse the tree down from the root, node by node. In a hashtable however, we can find the location of a key in the table in O(1) time! The secret behind this speed is a function, known as a hash function, that maps any key to its location in the table. As such there is (almost) no traversal in a hashtable. We go straight to a key's location to, for instance, remove or insert the key.

The hashtable in a Hashmap stores key & value pairs, rather than just keys alone. Concretely, in our social network, it stores vertex & adjacency list pairs. As described above, the hash function allows us to locate a vertex (and its adjacency list) in constant time. Recall that time complexity becomes more of a concern as the size of the problem increases. In an app like a social networking app with many users, the constant time complexity of hashtables becomes very appealing. That is why we choose to use a HashMap to store our social network.
