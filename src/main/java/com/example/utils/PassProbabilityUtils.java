package com.example.utils;

import com.google.common.math.BigIntegerMath;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Pass probability.
 * have two functions {@link #simplePassProbability(float, float, float, List)} and
 * {@link #scientificPassProbability(float, float, float, List)}.
 * {@link #simplePassProbability} is calculated by average score.<BR>
 * {@link #scientificPassProbability} is calculate by original score.
 * <pre>
 *     public static void main(String[] args) {
 *         List<Integer> scoreList = new ArrayList<>();
 *         Collections.addAll(scoreList, 10, 5, 5, 10, 10, 10, 2, 1, 2, 3, 2, 5, 1, 1, 1, 1, 1);
 *
 *
 *         float currentScore = 20F;
 *         float finishedScore = 30F;
 *         float passScore = 85F;
 *
 *         double passProbability = simplePassProbability(currentScore, finishedScore, passScore, scoreList);
 *         System.out.println(String.format("current score: %s，archive pass score %s probability is: %s %%", currentScore, passScore,
 *                 BigDecimal.valueOf(passProbability * 100).setScale(2, RoundingMode.HALF_UP)));
 *
 *         double passProbability2 = scientificPassProbability2(currentScore, finishedScore, passScore, scoreList);
 *         System.out.println(String.format("current score: %s，archive pass score %s probability is: %s %%", currentScore, passScore,
 *                 BigDecimal.valueOf(passProbability2 * 100).setScale(2, RoundingMode.HALF_UP)));
 *     }
 * </pre>
 *
 * @author Kenny Fang
 * @since 0.0.3
 */
public class PassProbabilityUtils {

    /**
     * Simple Pass Probability is based on how many questions you get right at least
     * (average score for each question) {@code lackScore}
     *
     * @param currentScore current score
     * @param finishedScore the sum score of finished
     * @param passScore pass score
     * @param leftScoreList how many score are left
     * @return probability of passing when all are done
     */
    public static double simplePassProbability(float currentScore, float finishedScore, float passScore,
                                                List<Integer> leftScoreList) {
        int sum = 0;
        for (Integer integer : leftScoreList) {
            sum += integer;
        }
        float lackScore = passScore - currentScore;
        double rightRate = currentScore / finishedScore;
        double wrongRate = 1 - rightRate;
        double probability = 0D;

        int size = leftScoreList.size();
        int len = (int)Math.ceil(lackScore * size / sum);
        for (int i = len; i < size + 1;  ++i) {
            probability += Math.pow(rightRate, i)
                    * Math.pow(wrongRate, size -i)
                    * countCombination(i, size).longValue();
        }

        return probability;
    }

    /**
     * Scientific Pass Probability include all combinations
     * that sum of elements is grater or equals than {@code lackScore}
     *
     * @param currentScore current score
     * @param finishedScore the sum score of finished
     * @param passScore pass score
     * @param leftScoreList how many score are left
     * @return probability of passing when all are done
     */
    public static double scientificPassProbability(float currentScore, float finishedScore, float passScore,
                                                   List<Integer> leftScoreList) {
        float lackScore = passScore - currentScore;
        double rightRate = currentScore / finishedScore;
        double wrongRate = 1 - rightRate;

        Integer[] intArr = leftScoreList.toArray(new Integer[0]);
        int[] iArr = new int[intArr.length];
        for (int i = 0; i < intArr.length; ++i) {
            iArr[i] = intArr[i];
        }
        List<int[]> combinationList = allCombinationInt(iArr);

        ListIterator<int[]> intArrIt = combinationList.listIterator();
        double probability = 0D;
        while(intArrIt.hasNext()) {
            int[] tmpIntArr = intArrIt.next();
            int total = 0;
            for (Integer i : tmpIntArr) {
                total += i;
            }

            if (total >= lackScore) {
                int length = tmpIntArr.length;
               probability += Math.pow(rightRate, length)
                        * Math.pow(wrongRate, leftScoreList.size() - length);
            }
        }

        return probability;
    }


    /**
     * combinations contains the original elements
     *
     * @param array total elements of int array
     * @param length length to select
     * @param startPosition start position
     * @param result elements of temporary int array
     * @return combinations
     */
    public static List<int[]> combinationsInt(int[] array, int length, int startPosition, int[] result){
        List<int[]> combinationList = new ArrayList<>();
        if (length == 0){
           combinationList.add(result.clone());
           return combinationList;
        }

        for (int i = startPosition; i <= array.length-length; i++){
            result[result.length - length] = array[i];
            combinationList.addAll(combinationsInt(array, length-1, i+1, result));
        }

        return combinationList;
    }

    /**
     * all combinations
     *
     * @param arr total elements of int array
     * @return all combinations of all length
     */
    public static List<int[]> allCombinationInt(int[] arr) {

        List<int[]> combinationList = new ArrayList<>();
        for (int i = 1; i < arr.length + 1; ++i) {
            combinationList.addAll(combinationsInt(arr, i, 0, new int[i]));
        }

        return combinationList;
    }

    /**
     * count combinations
     *
     * @param m quantity to select
     * @param n total of elements
     * @return combinations
     */
    public static BigInteger countCombination(int m, int n) {
        return BigIntegerMath.factorial(n)
                .divide(
                        BigIntegerMath.factorial(m)
                .multiply(BigIntegerMath.factorial(n - m))
                );
    }
}
