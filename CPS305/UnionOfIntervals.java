import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class IntervalUnion {
    private ArrayList<Integer> unionList;

    private IntervalUnion() {
        unionList = new ArrayList<Integer>();
    }

    private IntervalUnion(int start, int end) {
        unionList = new ArrayList<Integer>();
        unionList.add(start);
        unionList.add(end);
    }
    
    public static IntervalUnion create(int start, int end) {
        return new IntervalUnion(start, end);
    }

    public static String getAuthorName() {
        return "Dudarewicz, Jacob";
    }

    public static String getRyersonID() {
        return "#########"; //Removed for upload
    }

    @Override public String toString() { 
        StringBuilder builder = new StringBuilder();

        builder.append('[');

        for(int i = 1; i < unionList.size(); i += 2) {
            if((int)unionList.get(i) == (int)unionList.get(i - 1)) {
                builder.append(unionList.get(i));
                if(i + 1 != unionList.size()){
                    builder.append(',');
                }
            } else if((int)unionList.get(i) != (int)unionList.get(i - 1)) {
                builder.append(unionList.get(i - 1));
                builder.append('-');
                builder.append(unionList.get(i));
                
                if(i + 1 != unionList.size()){
                    builder.append(',');
                }
            }
        }
        builder.append(']');

        return builder.toString();
    }

    @Override public boolean equals(Object other) {
        if(other == null || !(other instanceof IntervalUnion)) {
            return false;
        }
        return this.unionList.equals(((IntervalUnion) other).unionList);
    }

    public boolean contains(int x) {
        for(int i = 1; i < unionList.size(); i += 2) {
            if(unionList.get(i) == x || unionList.get(i - 1) == x) {
                return true;
            }
            if(unionList.get(i - 1) < x && unionList.get(i) > x) {
                return true;
            }
        }
        return false;
    }

    @Override public int hashCode() {
        int prime = 6243703, toReturn = 1;
        toReturn = toReturn * prime + unionList.hashCode();
        toReturn = toReturn * prime + unionList.size();

        return toReturn;
    }

    public IntervalUnion union(IntervalUnion other) {
        IntervalUnion toReturn = new IntervalUnion();

        if(this.unionList.isEmpty() && !other.unionList.isEmpty()) {
            for(int i = 0; i < other.unionList.size(); i++) {
                toReturn.unionList.add(other.unionList.get(i));
            }
        }
        else if(!this.unionList.isEmpty() && other.unionList.isEmpty()) {
            for(int i = 0; i < this.unionList.size(); i++) {
                toReturn.unionList.add(this.unionList.get(i));
            }
        }
        else if (!this.unionList.isEmpty() && !other.unionList.isEmpty()){
            ArrayList<Integer> tempArray = sortIntervalArrays(this.unionList, other.unionList);
            toReturn.unionList.add(tempArray.get(0));
            int max = -1;
            int element1 = 0;
            int element2 = 0;
            
            for(int i = 2; i < tempArray.size(); i += 2) {
                element1 = tempArray.get(i - 1);
                element2 = tempArray.get(i);

                if(element2 < element1) {
                    if(max == -1) {
                        max = element1;
                    } else {
                        if(max < element1) {
                            max = element1;
                        }
                    }
                } 
                else if(element1 + 1 < element2 && max + 1 < element2 && element2 - 1 != toReturn.unionList.get(toReturn.unionList.size() - 1)) {
                    toReturn.unionList.add(Math.max(element1,max));
                    toReturn.unionList.add(element2);
                    max = -1;
                }
            }

            toReturn.unionList.add(Math.max(tempArray.get(tempArray.size() - 1), max)); 
        }
        return toReturn;
    }

    public IntervalUnion intersection(IntervalUnion other) {
        IntervalUnion toReturn = new IntervalUnion();

        if(!this.unionList.isEmpty() && !other.unionList.isEmpty()) {
            ArrayList<Integer> tempArray = sortIntervalArrays(this.unionList, other.unionList);
            int min = tempArray.get(0);
            int max = tempArray.get(1);
            int element1 = 0;
            int element2 = 0;

            for(int i = 3; i < tempArray.size(); i+=2) {
                element1 = tempArray.get(i-1);
                element2 = tempArray.get(i);

                if(element1 >= min && element2 < max) {
                    toReturn.unionList.add(element1);
                    toReturn.unionList.add(element2);
                    min = element2;
                } else if(element1 >= min && max >= element1 && element2 >= max) {
                    toReturn.unionList.add(element1);
                    toReturn.unionList.add(max);
                    min = max + 1;
                    max = element2;
                } else if(min >= max || element1 > max && element2 > max) {
                    min = element1;
                    max = element2;
                }
            }
        }
        return toReturn;
    }

    public int getPieceCount() {
        return unionList.size() / 2;
    }

    private ArrayList<Integer> sortIntervalArrays(ArrayList<Integer> a1, ArrayList<Integer> a2) {
        ArrayList<Integer> toReturn = new ArrayList<Integer>();

        int index1 = 0, index2 = 0;
        while(index1 < a1.size() && index2 < a2.size()) {
            if(a1.get(index1) <= a2.get(index2)) {
                toReturn.add(a1.get(index1));
                toReturn.add(a1.get(index1 + 1));
                index1 += 2;
            }
            else {
                toReturn.add(a2.get(index2));
                toReturn.add(a2.get(index2 + 1));
                index2 += 2;
            }
        }

        if(index1 < a1.size() || index2 < a2.size()) {
            ArrayList<Integer> larger = index1 < a1.size() ? a1 : a2;
            int largerIndex = index1 < a1.size() ? index1 : index2;

            while(largerIndex < larger.size()) {
                toReturn.add(larger.get(largerIndex));
                largerIndex += 1;
            }
        }
        
        return toReturn;
    }
}
