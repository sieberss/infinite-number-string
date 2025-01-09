import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

import org.junit.Test;

public class InfiniteDigitalStringTest {


    private static Object[][] DATA = new Object[][] {
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
            new Object[] {"0910",       2927L,       "1009-1010"},
            new Object[] {"0991",       2617L,       "909-910"},
            new Object[] {"09910",      2617L,       "909-910"},
            new Object[] {"09991",      35286L,      "9099-9100"}};

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
}
