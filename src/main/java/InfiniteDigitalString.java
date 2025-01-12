import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InfiniteDigitalString {

    public static long findPosition(final String s) {
        long result = Long.MAX_VALUE; //
        String upto1004 = IntStream.range(1, 1005).mapToObj(Integer::toString).collect(Collectors.joining());
        int index1004 = upto1004.indexOf(s);
        /** case 1: found number in string of 1 to 1004 */
        if (index1004 != -1) return index1004;
        /** case 2: search for series of numbers in s. Save this index and wait if it can be further reduced. */
        long seriesIndex = findIndexForSeries(s);
        if (seriesIndex != -1)
            result = seriesIndex;
        /** case 3: move of leading nines to the end may find lower number */
        result = getManipulationResult(getLeadingNinesValue(s), s, result);
        /** case 4: rotation of number may find lower index */
        result = getManipulationResult(getRotatedValue(s), s, result);
        /** case 5: when start and end digits are equal, may belong to two successive numbers */
        result = getManipulationResult(getBeginEqualsEndValue(s), s, result);
        /** case 6: start 1 lower than end, start can be end of lower number */
        result = getManipulationResult(getBeginOneLowerThanEndValue(s), s, result);
        /** case 7 : leading nines can be end of lower number */
        result = getManipulationResult(getLeadingNinesValue(s), s, result);
        return result;
    }

    public static long getRotatedValue(String s){
        long result = Long.MAX_VALUE;
        for (int i=0; i< s.length(); i++){
            if (s.charAt(i) != '0'){
                String rotated = s.substring(i) + s.substring(0, i);
                long value = Long.parseLong(rotated);
                if ((rotated + (value+1) ) .contains(s))
                    result = Math.min(result, Long.parseLong(rotated));
            }
        }
        return result;
    }

    public static long getLeadingNinesValue(String s) {
        String nines = getLeadingNines(s);
        if (nines.isEmpty())
            // no leading nines
            return Long.MAX_VALUE;
        int nineCount = nines.length();
        String end = s.substring(nineCount);
        if (end.isEmpty() || end.charAt(0) == '0'){
            // one nine must remain at beginning
            nines = nines.substring(1);
            nineCount--;
            end = "9" + end;
        }
        if (nines.isEmpty())
            // no nine left to shift to end
            return Long.MAX_VALUE;
        long endValue = Long.parseLong(end);
        int remainingNines = nineCount;
        // omit zeros at end if covered by nines at beginning (
        while (endValue % 10 == 0 && remainingNines > 0){
            endValue /= 10;
            remainingNines--;
        }
        end = Long.toString(endValue - 1);
        // number with nines at the end is followed by number without these nines
        return Long.parseLong(end + nines);
    }

    private static String getLeadingNines(String s) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length() && s.charAt(i) == '9'; i++)
            result.append("9");
        return result.toString();
    }

    private static long getManipulationResult(long cuttedValue, String s, long result) {
        int pos;
        if (cuttedValue != Long.MAX_VALUE) {
            pos = getIndexInSeries(cuttedValue, cuttedValue + 1, s);
            long indexAfterRepetitionHandling = getNumberIndex(cuttedValue) + pos;
            if (indexAfterRepetitionHandling < result)
                return indexAfterRepetitionHandling;
        }
        return result;
    }

    private static long findIndexForSeries(String s) {
        int l = s.length();
        int digits = 4;  // start with 4 digit numbers, as lower have been checked at start
        long position = -1;
        while (digits < l && position == -1){
            System.out.println(position + " " + digits);
            // check if s can be constructed by a series of numbers with fewer digits
            position = findIndexForSeries(s, digits);
            digits++;
        }
        // either loop terminated earlier or position is still -1
        return position;
    }

    public static long findIndexForSeries(String s, int digits){
        long value;
        String part;
        long position = -1;
        for (int i = 0; i < digits && i + digits <= s.length(); i++){
            part = s.substring(i, i + digits);
            value = Long.parseLong(part);
            // a series of n zeros cannot be constructed by n digit numbers -> stop searching
            if (value == 0){
                return -1;
            }
            if (s.charAt(i) == '0')
                // this cannot be the start of an n digit number
                continue;
            // build potential number series and check if s is contained
            long start = (i == 0) ? value : value - 1;
            long end = start + s.length() / digits + 1;
            int seriesPosition = getIndexInSeries(start, end, s);
            if (seriesPosition != -1)
                return getNumberIndex(start) + seriesPosition;
        }
        return position;
    }

    /* gets the index in the infinite string where the provided number is placed */
    public static long getNumberIndex(long number){
        int length = Long.toString(number).length();
        if (length == 1) return number - 1;
        long factor = 1;
        long index = 0;
        for (int digits = 1; digits < length; digits++){
            index += 9 * factor * digits;
            factor *= 10;
        }
        return index + length * (number - factor);
    }

    public static long getValueToIndex(long result){
        if (result <= 9) return result + 1;
        long factor = 1;
        long curIndex = 0;
        long oldIndex = 0;
        int digits = 1;
        while (curIndex <= result){
            oldIndex = curIndex;
            curIndex += 9 * factor * digits;
            digits++;
            factor *= 10;
        }
        return (result - oldIndex) / (digits -1) + factor / 10 ;
    }

    /* checks if the string representation of the number series contains the input string
    and returns its position*/
    public static int getIndexInSeries(long from, long to, String input){
        StringBuilder sb = new StringBuilder();
        for (long number = from; number <= to; number++){
            sb.append(number);
        }
        return sb.toString().indexOf(input);
    }

    public static String getCommonBeginAndEnd(String s){
        String common = "";
        int l = s.length();
        for (int i = 1; i * 2 < l; i++){
            String begin = s.substring(0, i);
            String middle = s.substring(i, l-i);
            String end = s.substring(l-i);
            if (begin.equals(end) && !onlyNines(middle))
                common = begin;
        }
        return common;
    }

    public static String getBeginOneLowerThanEnd(String s){
        String result = "";
        int l = s.length();
        for (int i = 1; i * 2 < l; i++){
            String begin = s.substring(0, i);
            String end = s.substring(l-i);
            if (Long.parseLong(end) - Long.parseLong(begin) == 1) {
                result = begin;
            }
        }
        return result;
    }

    public static long getBeginEqualsEndValue(String number) {
        String begin = getCommonBeginAndEnd(number);
        if (begin.isEmpty())
            // different begin and end, so no cut possible
            return Long.MAX_VALUE;
        int length = number.length();
        int beginLength = begin.length();
        String middle = number.substring(beginLength, length - beginLength);
        String newNumber = begin + middle;
        long value = Long.MAX_VALUE;
        for (int i = 0; i < newNumber.length(); i++) {
            if (newNumber.charAt(0) != '0' && successorMatches(newNumber, number))
                value = Math.min(value, Long.parseLong(newNumber));
            newNumber = newNumber.substring(1) + newNumber.charAt(0);
        }
        return value;
    }

    private static boolean successorMatches(String part, String number) {
        String successor;
        long value;
        if (part.charAt(0) == '0') {
            value = Long.parseLong("1" + part);
            successor = Long.toString(value + 1).substring(1);
        }
        else {
            value = Long.parseLong(part);
            successor = Long.toString(value + 1);
        }
        return (part + successor).contains(number);
    }

    public static long getBeginOneLowerThanEndValue(String number) {
        String begin = getBeginOneLowerThanEnd(number);
        int length = number.length();
        int beginLength = begin.length();
        String middle = number.substring(beginLength, length - beginLength);
        String nines = getLeadingNines(middle);
        int ninesCount = nines.length();
        if (begin.isEmpty() || middle.charAt(0) == '0')
            // no match or number would begin with 0, so no cut possible
            return Long.MAX_VALUE;
        // one nine must stay if new number would start with 0 (0 after nines or no digits left)
        if (number.charAt(beginLength + ninesCount) == '0'
                || (number.charAt(0) == '0' && nines.equals(middle)) ) {
            nines = nines.substring(1);
        }
        String toMove = begin + nines;
        String newNumber = number.substring(toMove.length(), length - begin.length()) + toMove;
        return Long.parseLong(newNumber);
    }

    private static boolean onlyNines(String number) {
        for (int i = 0; i < number.length(); i++){
            if (number.charAt(i) != '9')
                return false;
        }
        return true;
    }

}
