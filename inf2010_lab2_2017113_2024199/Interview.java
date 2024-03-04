package tp2;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

public class Interview {
    /**
     * TIME COMPLEXITY POINTS
     * Worst Case : O(n^2)
     * > O(n^2) => 0
     *
     * Average Case
     * O(n) => 3.5
     * O(n log n) => 2
     * O(n^2) => 0.5
     * > O(n^2) => 0
     *
     * Finds all pairs within values which sum up to targetSum
     * @param values All possible values that can form a pair (can contain duplicates)
     * @param targetSum Pairs should add up to this
     * @return A collection containing all valid pairs with no permutations, but all combinations (empty collection if none found)
     */
    public Collection<MatchingPair> matchingPairs(Collection<Integer> values, Integer targetSum) {
        HashMap<Integer,Integer> map = new HashMap<Integer,Integer>(values.size());
        Collection<MatchingPair> validMatchingPairs = new LinkedList<MatchingPair>();
        for (Integer nbr : values) {
            if (map.containsKey(targetSum-nbr)) {
                Integer nbr2 = targetSum-nbr;
                if (nbr2 != null) {
                    for(int i = 0; i < map.get(targetSum - nbr); i++) {
                        validMatchingPairs.add(new MatchingPair(nbr,nbr2));
                    }
                }
            }
            if (map.containsKey(nbr)) {
                //Integer counter = new Integer(map.get(nbr));
                Integer counter = map.get(nbr);
                //map.remove(nbr);
                map.put(nbr, ++counter);
            }
            else {
                map.put(nbr,1);
            }
        }

        return validMatchingPairs;
    }
}
