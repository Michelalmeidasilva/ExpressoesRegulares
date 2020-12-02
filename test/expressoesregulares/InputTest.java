package expressoesregulares;

import expressoesregulares.Input;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
    
public class InputTest {

    // input test
    String test1 = "1*";
    String test2 = "1|0";
    String test3 = "0|1*";
    String test4 = "(0|1)*";
    String test5 = "10.11";
    String test6 = "(((0|1).10).(((0|1).(1.1)).1)*)";
    String test7 = "(0|1*).(0|1)*";

    // output expected
    ArrayList<String> test1Expected = new ArrayList<String>(Arrays.asList("ε", "1", "11", "111", "1111"));
    ArrayList<String> test2Expected = new ArrayList<String>(Arrays.asList("ε", "0", "1"));
    ArrayList<String> test3Expected = new ArrayList<String>(Arrays.asList("ε", "0", "1", "11", "111", "1111"));
    ArrayList<String> test4Expected = new ArrayList<String>(Arrays.asList("ε", "0", "00", "000", "0000", "0001", "001", "0010", "0011", "01", "010", "0100", "0101", "011", "0110", "0111", "1", "10", "100", "1000", "1001", "101", "1010", "1011", "11", "110", "1100", "1101", "111", "1110", "1111"));
    ArrayList<String> test5Expected = new ArrayList<String>(Arrays.asList("ε", "1011"));
    ArrayList<String> test6Expected = new ArrayList<String>(Arrays.asList("ε", "0", "010", "0100", "0101", "1", "11", "110", "1100", "1101"));
    ArrayList<String> test7Expected = new ArrayList<String>(Arrays.asList("ε", "0", "00", "000", "0000", "00000", "00001", "0001", "00010", "00011", "001", "0010", "00100", "00101", "0011", "00110", "00111", "01", "010", "0100", "01000", "01001", "0101", "01010", "01011", "011", "0110", "01100", "01101", "0111", "01110", "01111", "1", "10", "100", "1000", "10000", "10001", "1001", "10010", "10011", "101", "1010", "10100", "10101", "1011", "10110", "10111", "11", "110", "1100", "11000", "110000", "110001", "11001", "110010", "110011", "1101", "11010", "110100", "110101", "11011", "110110", "110111", "111", "1110", "11100", "111000", "1110000", "1110001", "111001", "1110010", "1110011", "11101", "111010", "1110100", "1110101", "111011", "1110110", "1110111", "1111", "11110", "111100", "1111000", "11110000", "11110001", "1111001", "11110010", "11110011", "111101", "1111010", "11110100", "11110101", "1111011", "11110110", "11110111", "11111", "111110", "1111100", "11111000", "11111001", "1111101", "11111010", "11111011", "111111", "1111110", "11111100", "11111101", "1111111", "11111110", "11111111"));

    @Before
    public void setup() {

    }

    @Test
    public void executeTest1() {
        Assert.assertEquals(test1Expected, Input.testCalc(test1));
    }

    @Test
    public void executeTest2() {
        assertEquals(test2Expected, Input.testCalc(test2));
    }

    @Test
    public void executeTest3() {
        assertEquals(test3Expected, Input.testCalc(test3));
    }

    @Test
    public void executeTest4() {
        assertEquals(test4Expected, Input.testCalc(test4));
    }

    @Test
    public void executeTest5() {
        assertEquals(test5Expected, Input.testCalc(test5));
    }

    @Test
    public void executeTest6() {
        assertEquals(test6Expected, Input.testCalc(test6));
    }

    @Test
    public void executeTest7() {
        assertEquals(test7Expected, Input.testCalc(test7));
    }

}
    