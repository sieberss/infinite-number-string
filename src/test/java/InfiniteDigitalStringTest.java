import static org.junit.Assert.*;

import org.junit.Test;

public class InfiniteDigitalStringTest {


    private static Object[][] DATA = new Object[][] {

            new Object[] {"0910",       2927L,       "1009-1010"},
            new Object[] {"456",        3L,          "...3456..."},
            new Object[] {"454",        79L,         "...444546..."},
            new Object[] {"455",        98L,         "...545556..."},
            new Object[] {"910",        8L,          "...7891011..."},
            new Object[] {"9100",       188L,        "...9899100..."},
            new Object[] {"99100",      187L,        "...9899100..."},
            new Object[] {"00101",      190L,        "...9899100..."},
            new Object[] {"001",        190L,        "...9899100..."},
            new Object[] {"00",         190L,        "...9899100..."},
            new Object[] {"123456789",  0L,          ""},
            new Object[] {"1234567891", 0L,          ""},
            new Object[] {"123456798",  1000000071L, ""},
            new Object[] {"10",         9L,          ""},
            new Object[] {"53635",      13034L,      "3536-3537"},
            new Object[] {"040",        1091L,       "400-401"},
            new Object[] {"11",         11L,         ""},
            new Object[] {"99",         168L,        "89-90"},
            new Object[] {"667",        122L,        "66-67"},
            new Object[] {"0404",       15050L,      "4040-4041"},
            new Object[] {"949225100",  382689688L,  "49225099-49225100"},
            new Object[] {"58257860625",24674951477L,"2578606258-2578606259"},
            new Object[] {"3999589058124"  , 6957586376885L   , "589058123999-589058114000 - warum nicht 1243999589058? 1 Stelle mehr "},
            new Object[] {"555899959741198", 1686722738828503L, "119855589995974-119855589995975"},
            new Object[] {"01",         10L,         ""},
            new Object[] {"091",        170L,        "90-91"},
            new Object[] {"0991",       2617L,       "909-910"},
            new Object[] {"09910",      2617L,       "909-910"},
            new Object[] {"09991",      35286L,      "9099-9100"},
            new Object[] {"06317340",   11027334L,   "1734063-1734064"},
            new Object[] {"0585574102420",   1117935915782L,   "102420585574"}
    };

    @Test
    public void sampleTests() {
        for (Object[] t: DATA) {
            String s      = (String) t[0],
                    msg    = (String) t[2];
            long expected = (long)   t[1];
            //System.out.println("can be found at number : " + InfiniteDigitalString.getValueToIndex(expected));
            assertEquals(msg, expected, InfiniteDigitalString.findPosition(s));
        }
        assertNotEquals(-1L, InfiniteDigitalString.findPosition("040"));  // 400-101
    }

    @Test
    public void getRotatedValue_test(){
        assertEquals(375000004L, InfiniteDigitalString.getRotatedValue("000043750"));
        assertEquals(119855589995974L, InfiniteDigitalString.getRotatedValue("555899959741198"));
        assertEquals(4040, InfiniteDigitalString.getRotatedValue("0404"));
        assertEquals(1243999589058L, InfiniteDigitalString.getRotatedValue("3999589058124"));
        assertEquals(1009, InfiniteDigitalString.getRotatedValue("0910"));
    }

    @Test
    public void getValueForEqualDigits_test(){
        assertEquals(999990, InfiniteDigitalString.getValueForEqualDigits("999999"));
        assertEquals(990, InfiniteDigitalString.getValueForEqualDigits("999"));
        assertEquals(90, InfiniteDigitalString.getValueForEqualDigits("99"));
        assertEquals(222, InfiniteDigitalString.getValueForEqualDigits("2222"));
        assertEquals(222, InfiniteDigitalString.getValueForEqualDigits("22222"));
    }
    @Test
    public void getBeginEqualsEnd_Value_test() {
        // common part must be at the beginning, middle part must not be 9 only
        assertEquals(20122L, InfiniteDigitalString.getBeginEqualsEndValue("0122201")); // 0 at beginning 22012-22013
        assertEquals(291000L, InfiniteDigitalString.getBeginEqualsEndValue("9100029"));
        assertEquals(Long.MAX_VALUE, InfiniteDigitalString.getBeginEqualsEndValue("0910")); // 0 cannot be left out
        assertEquals(2578606258L, InfiniteDigitalString.getBeginEqualsEndValue("58257860625")); //2578606258-257
        assertEquals(2344789L, InfiniteDigitalString.getBeginEqualsEndValue("234478923"));
        assertEquals(1098923451L, InfiniteDigitalString.getBeginEqualsEndValue("9892345110989")); //1109892345-1109892346
        assertEquals(1117896L, InfiniteDigitalString.getBeginEqualsEndValue("789611178"));
        assertEquals(1111521L, InfiniteDigitalString.getBeginEqualsEndValue("521111152")); // 11111-52..2
        assertEquals(1879000L, InfiniteDigitalString.getBeginEqualsEndValue("879000187")); // 1879000-1870001
        assertEquals(Long.MAX_VALUE, InfiniteDigitalString.getBeginEqualsEndValue("2349234")); //2349-2350 geht nicht
        assertEquals(10101999L, InfiniteDigitalString.getBeginEqualsEndValue("101999101")); //10101999
        assertEquals(3456492L, InfiniteDigitalString.getBeginEqualsEndValue("49234564")); // 2345649-2345650
    }

    @Test
    public void getBeginOneLowerThanEndValue_test(){
        // begin one lower than end and followed by 9
        assertEquals(904354799L, InfiniteDigitalString.getBeginOneLowerThanEndValue("7999043548")); // 904354799-904354800
        assertEquals(1799999L, InfiniteDigitalString.getBeginOneLowerThanEndValue("179999918")); //1799999-1800000
        assertEquals(9099, InfiniteDigitalString.getBeginOneLowerThanEndValue("09991"));
        assertEquals(589058123999L, InfiniteDigitalString.getBeginOneLowerThanEndValue("3999589058124")); // 589058123999-589058124000
        assertEquals(51999L, InfiniteDigitalString.getBeginOneLowerThanEndValue("199952")); //51999-52000
        assertEquals(34199L, InfiniteDigitalString.getBeginOneLowerThanEndValue("199342")); // 34199-34200
        assertEquals(59917L, InfiniteDigitalString.getBeginOneLowerThanEndValue("1759918")); // Neunen an falscher Stelle
        assertEquals(2349L, InfiniteDigitalString.getBeginOneLowerThanEndValue("49235")); // 2349-2350
        assertEquals(12345999L, InfiniteDigitalString.getBeginOneLowerThanEndValue("599912346")); // 12345999-123456000
        assertEquals(56699L, InfiniteDigitalString.getBeginOneLowerThanEndValue("6995670")); //56669-56700
        assertEquals(234509L, InfiniteDigitalString.getBeginOneLowerThanEndValue("0923451"));
        assertEquals(903451L, InfiniteDigitalString.getBeginOneLowerThanEndValue("1903452"));
        assertEquals(2345671L, InfiniteDigitalString.getBeginOneLowerThanEndValue("12345672")); // number would start with 0
        assertEquals(223491L, InfiniteDigitalString.getBeginOneLowerThanEndValue("1223492")); // number would start with 0
        assertEquals(44439L, InfiniteDigitalString.getBeginOneLowerThanEndValue("394444")); // number would start with 0
    }
    @Test
    public void getNumberIndex_test(){
        assertEquals(0, InfiniteDigitalString.getNumberIndex(1L));
        assertEquals(9, InfiniteDigitalString.getNumberIndex(10L));
        assertEquals(11, InfiniteDigitalString.getNumberIndex(11L));
        assertEquals(187, InfiniteDigitalString.getNumberIndex(99L));
        assertEquals(189, InfiniteDigitalString.getNumberIndex(100L));
        assertEquals(219, InfiniteDigitalString.getNumberIndex(110L));
        assertEquals(2889, InfiniteDigitalString.getNumberIndex(1000L));
        assertEquals(167, InfiniteDigitalString.getNumberIndex(89L));
        assertEquals(15049, InfiniteDigitalString.getNumberIndex(4040L));
        assertEquals(1000000071L, InfiniteDigitalString.getNumberIndex(123456798L));
        assertEquals(11234568689L, InfiniteDigitalString.getNumberIndex(1234567980L));
    }

    @Test
    public void getValueToIndex_test(){
        assertEquals(17340063L, InfiniteDigitalString.getValueToIndex(127609398L));
        assertEquals(102420585574L, InfiniteDigitalString.getValueToIndex(1117935915782L));
        assertEquals(1734063, InfiniteDigitalString.getValueToIndex(11027334L));
        assertEquals(1, InfiniteDigitalString.getValueToIndex(0));
        assertEquals(10, InfiniteDigitalString.getValueToIndex(9));
        assertEquals(11, InfiniteDigitalString.getValueToIndex(11L));
        assertEquals(99, InfiniteDigitalString.getValueToIndex(187));
        assertEquals(100, InfiniteDigitalString.getValueToIndex(189));
        assertEquals(110, InfiniteDigitalString.getValueToIndex(219));
        assertEquals(1000, InfiniteDigitalString.getValueToIndex(2889));
        assertEquals(89, InfiniteDigitalString.getValueToIndex(167));
        for (int i=1; i< 10000; i++){
            assertEquals(i, InfiniteDigitalString.getValueToIndex(InfiniteDigitalString.getNumberIndex(i)));
        }
        assertEquals(4040L, InfiniteDigitalString.getValueToIndex(15049));
        assertEquals(123456798L, InfiniteDigitalString.getValueToIndex(1000000071L));
        assertEquals(1234567980L, InfiniteDigitalString.getValueToIndex(11234568689L));
        assertEquals(2578606258L, InfiniteDigitalString.getValueToIndex(24674951477L));
        assertEquals(3536L, InfiniteDigitalString.getValueToIndex(13034L));
    }

    @Test
    public void twoNumbersWithSameIndex_test(){

        long a = 306193912L;
        System.out.println(a);
        long b = InfiniteDigitalString.getNumberIndex(a);
        System.out.println(b);
        System.out.println(InfiniteDigitalString.getValueToIndex(b));
        a = 1234567980L;
        System.out.println(a);
        b = InfiniteDigitalString.getNumberIndex(a);
        System.out.println(b);
        System.out.println(InfiniteDigitalString.getValueToIndex(b));
    }


    @Test
    public void getIndexInSeries_test(){
        assertEquals(0, InfiniteDigitalString.getIndexInSeries(48, 49, "484"));
        assertEquals(1, InfiniteDigitalString.getIndexInSeries(48, 49, "849"));
        assertEquals(2, InfiniteDigitalString.getIndexInSeries(48, 49, "49"));
        assertEquals(-1, InfiniteDigitalString.getIndexInSeries(48, 49, "44"));
        assertEquals(4, InfiniteDigitalString.getIndexInSeries(27, 31, "293"));
        assertEquals(0, InfiniteDigitalString.getIndexInSeries(27, 31, ""));
    }

    @Test
    public void testGetCommonBeginAndEnd() {
        assertEquals("245", InfiniteDigitalString.getCommonBeginAndEnd("24567245"));
        assertEquals("111", InfiniteDigitalString.getCommonBeginAndEnd("11112111"));
        assertEquals("999", InfiniteDigitalString.getCommonBeginAndEnd("9992999"));
        assertEquals("15", InfiniteDigitalString.getCommonBeginAndEnd("15437815"));
        assertEquals("", InfiniteDigitalString.getCommonBeginAndEnd("12341234"));
        assertEquals("", InfiniteDigitalString.getCommonBeginAndEnd("512346"));
        assertEquals("5", InfiniteDigitalString.getCommonBeginAndEnd("515515"));
        assertEquals("", InfiniteDigitalString.getCommonBeginAndEnd("599995"));
        assertEquals("44", InfiniteDigitalString.getCommonBeginAndEnd("4449444")); /// not 444 because 4449-4450
    }

    @Test
    public void testGetBeginOneLowerThanEnd() {
        assertEquals("245", InfiniteDigitalString.getBeginOneLowerThanEnd("24597246"));
        assertEquals("111", InfiniteDigitalString.getBeginOneLowerThanEnd("111999112"));
        assertEquals("1", InfiniteDigitalString.getBeginOneLowerThanEnd("199992"));
        assertEquals("15", InfiniteDigitalString.getBeginOneLowerThanEnd("15937816"));
        assertEquals("", InfiniteDigitalString.getBeginOneLowerThanEnd("12341231"));
        assertEquals("", InfiniteDigitalString.getBeginOneLowerThanEnd("512351"));
        assertEquals("5", InfiniteDigitalString.getBeginOneLowerThanEnd("599516"));
    }

    @Test
    public void testGetLeadingNinesValue() {
        assertEquals(123999L, InfiniteDigitalString.getLeadingNinesValue("999124"));
        assertEquals(999L, InfiniteDigitalString.getLeadingNinesValue("999100"));
        assertEquals(90099L, InfiniteDigitalString.getLeadingNinesValue("9990100"));
        assertEquals(902409L, InfiniteDigitalString.getLeadingNinesValue("990241"));
        assertEquals(Long.MAX_VALUE, InfiniteDigitalString.getLeadingNinesValue("9007"));
        assertEquals(10019L, InfiniteDigitalString.getLeadingNinesValue("91002"));
        assertEquals(4009L, InfiniteDigitalString.getLeadingNinesValue("94010"));
        assertEquals(219999L, InfiniteDigitalString.getLeadingNinesValue("999922000"));
        assertEquals(219999L, InfiniteDigitalString.getLeadingNinesValue("9999220"));
        assertEquals(19999L, InfiniteDigitalString.getLeadingNinesValue("99920000"));
        assertEquals(999L, InfiniteDigitalString.getLeadingNinesValue("9991"));
        assertEquals(11999L, InfiniteDigitalString.getLeadingNinesValue("99912"));
    }
}
