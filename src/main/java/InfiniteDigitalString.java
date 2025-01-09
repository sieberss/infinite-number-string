public class InfiniteDigitalString {

    public static long findPosition(final String s) {
        int l = s.length();
        if (l == 0) return 0;  // empty string
        long value = Long.parseLong(s);

        // zeros only, then s appears first in 1000..., position is 1 after index of that number
        if (value == 0){
            value = Long.parseLong("1" + s);
            return getNumberIndex(value) + 1;
        }

        /** single digit numbers are easy to find */
        if (l == 1) {
            return value - 1;
        }

        /* nines only: 99..9 can be constructed from 89..9 and 90..0.
          Position is 1 after index of 89..9  */
        if (Long.valueOf(value + 1).toString().length() > l){
            value -= (value + 1) / 10;
            return getNumberIndex(value) + 1;
        }

        int digits = 1;
        long position = -1;
        while (digits < l && position == -1){
            System.out.println(position + " " + digits);
            // check if s can be constructed by a series of numbers with fewer digits
            position = findPosition(s, digits);
            digits++;
        }

        // found s in series: return result
        if (position != -1) return position;

        return getPositionAfterTreatingLeadingZeros(s, value);
    }



    public static long getPositionAfterTreatingLeadingZeros(String s, long value){
        int end = s.length() - 1;

        // count leading zeros
        int leading = 0;
        while (s.charAt(leading) == '0'){
            leading++;
        }

        // count trailing zeros, either at end or before a 1 at the end
        boolean endsWith1 = s.charAt(end) == '1';
        int trailing = 0;
        if (endsWith1) end--;
        while (end >= trailing && s.charAt(end - trailing) == '0') {
            trailing++;
        }

      /* determine what digits have to be added in order to make
        leading 0..00 matched by trailing 0..01 */
        int zerosToAdd = 0;
        boolean oneToAdd = false;
        if (!endsWith1 && leading > 0 && leading >= trailing) {
            zerosToAdd = (leading > trailing) ? leading - trailing - 1 : 0;
            oneToAdd = true;
        }
        if (endsWith1) {
            if (trailing + 1 < leading || leading == s.length() - 1) {
                // trailing 0..01 does not cover leading 00..00, have to add 00..01
                zerosToAdd = leading - 1;
                oneToAdd = true;
            }
            else { // leading 0..00 covered by trailing 0..01
                zerosToAdd = 0;
                oneToAdd = false;
            }
        }
        /* change value according to added digits*/
        for (int i = 0; i < zerosToAdd; i++)
            value *= 10;
        if (oneToAdd)
            value = 10 * value;
        System.out.println("Value is : " + value);

        /* calculate position of the modified value */
        long numberIndex = getNumberIndex(value);
        if (leading == 0)
            // s equal to value representation
            return numberIndex;
        else
            // value ends with last leading 0
            return numberIndex + Long.toString(value).length() - leading;

    }

    public static long findPosition(String s, int digits){
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
            long end = start + s.length() / digits;
            int seriesPosition = getIndexInSeries(start, end, s);
            System.out.printf("s: %s, start: %d, end: %d, position: %d \n", s, start, end, seriesPosition);
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
        System.out.printf("oldindex: %d, result: %d, factor: %d, digits: %d \n", oldIndex, result, factor, digits);
        return result == curIndex ? factor -1: (result - oldIndex) / (digits -1) + factor / 10 ;
    }

    /* checks if the string represenation of the number series contains the input string
    and returns its position*/
    public static int getIndexInSeries(long from, long to, String input){
        StringBuilder sb = new StringBuilder();
        for (long number = from; number <= to; number++){
            sb.append(Long.toString(number));
        }
        return sb.toString().indexOf(input);
    }
}