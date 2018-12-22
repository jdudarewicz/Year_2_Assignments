/**
 * Methods removeIfDivisible and sort for IntList.java.
 */

public class IntListMethods {
    private static final int ARRAYSIZE = 1000000; //starts at size optimized for test size. Will grow when required if supplied a larger number
    private static int currentSize = 0; //tracks current size of array in use
    private static int[] pointerlist = new int[ARRAYSIZE]; //list of pointers to keep track of elements when broken up for mergesort implementation
    public static String getAuthorName() {
        return "Dudarewicz, Jacob";
    }

    public static String getRyersonID() {
        return "#########";
    }

    /**
     * Removes any key node pair that is divisible by an integer, given as k
     * @param n is the head node of the list
     * @param k the integer to divide by
     * @return the head node of the updated list
     */
    public static int removeIfDivisible(int n, int k) {
        int firstNode = 0; //the integer to return
        int previous = 0; //the last node passed over, once the first node is established
        boolean firstSet = false; //false until the first node is established

        //Loop through once
        while(n != 0) {
            //Before a first element is established, it keep removing the head node key pair
            if(!firstSet && IntList.getKey(n) % k == 0) {
                int nextTmp = IntList.setNext(n, 0);
                IntList.release(n);
                n = nextTmp;
                //Once a suitable head element is found, saves first node to return later
            } else if(!firstSet && IntList.getKey(n) % k != 0) {
                previous = n;
                firstNode = n;
                n = IntList.getNext(n);
                firstSet = true;
                //Main removal, unallocates node to indicate it can be overwritten, connects removed nodes previous next to the removed nodes next node.
            } else if(IntList.getKey(n) % k == 0) {
                int nextTmp = IntList.getNext(n);
                IntList.setNext(previous, IntList.getNext(n));
                IntList.setNext(n, 0);
                IntList.release(n);
                n = nextTmp;
                //Continue through the list if there is nothing to remove and the first element has already been established
            } else {
                previous = n;
                n = IntList.getNext(n);
            }
        }

        //returns the head of the restructured chain
        return firstNode;
    }

    /**
     * Sorts linked list, using bottom up implementation of mergesort. Requires building another array but saves time having to find the middle
     * element in each step of recursion
     * @param n is the head node of the current chain
     * @return the head node of the sorted chain
     */
    public static int sort(int n) {
        //splitting list into size one "sorted" elements, pointing to them in the pointerlist array
        while(n != 0) {
            if(currentSize == pointerlist.length) {
                grow();
            }
            pointerlist[currentSize] = n;
            currentSize++;
            n = IntList.setNext(n, 0);
        }

        //Outside loop controls the step size
        for(int i = 1; i < currentSize; i += i) {
            //inside loop calls merge for every 2 lists, seperated inbetween eachother by i indicies
            for(int k = 0; k < currentSize - i; k += i + i) {
                if(pointerlist[k] == 0) {
                    break; //breaks out of step for list lengths that are less than the size of pointer array (ie 20 element list vs size 1 million array)
                }
                pointerlist[k] = merge(pointerlist[k], pointerlist[k + i]); //merges both lists, left list becomes new head for sorted list
                pointerlist[k + i] = 0; //sets pointer to zero
            }
        }
        
        currentSize = 0;
        return pointerlist[0];
    }

    /**
     * Merges lists together by sorting them, using while loop instead of recursive call
     * @param l is the head of the left (arbitrary direction identifier, easier for visualization of algorithm) list
     * @param r is the head of the right list
     * @return the head of the sorted chain
     */
    private static int merge(int l, int r) {
        int toReturn = 0; //the head element to return
        int lastAdded = 0; //the element last added to the chain

        //Finds the first element to use as the head of the new chain
        if(IntList.getKey(l) <= IntList.getKey(r)) {
            toReturn = l;
            lastAdded = l;
            l = IntList.setNext(l, 0);
        } else {
            toReturn = r;
            lastAdded = r;
            r = IntList.setNext(r, 0);
        }

        //Potentially hits up to l+r-1 loops, but in better cases only needs to loop through either the left or right list completely. Climbs through both lists
        //at the same time to connect either left or right node to lastAdded node depending on if it is smaller than the other node. This can be done in one
        //pass because both lists are already in ascending order
        while(l != 0 && r != 0) {
            if(IntList.getKey(l) <= IntList.getKey(r)) {
                IntList.setNext(lastAdded, l);
                lastAdded = l;
                l = IntList.setNext(l, 0);
            } else {
                IntList.setNext(lastAdded, r);
                lastAdded = r;
                r = IntList.setNext(r, 0);
            }
        }

        //Connects the remaining left or right chain depending on which still hasnt been added. This can be done because both lists are already in ascending order,
        //meaning that any left over chains are guarenteed to have greater or equal values to the last added node.
        if(l != 0) {
            IntList.setNext(lastAdded, l);
        } else {
            IntList.setNext(lastAdded, r);
        }

        //returns head of sorted chain
        return toReturn;
    }

    //grows array when needed, 10 times its original length
    private static void grow() {
        int[] newlist = new int[pointerlist.length * 10]; //makes new list
        for(int i = 0; i < pointerlist.length; i++) { //copies items
            newlist[i] = pointerlist[i];
        }
        pointerlist = newlist; //points pointerlist to new array, old list should be cleaned up by garbage collector
        newlist = null; //sets newlist to null
    }
}
