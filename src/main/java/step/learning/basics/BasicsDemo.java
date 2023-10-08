package step.learning.basics;

import java.util.ArrayList;
import java.util.List;

public class BasicsDemo {
    public void run() {
        arrDemo3();
    }

    private void arrDemo() {
        System.out.println( "BasicsDemo" );
        System.out.printf("Interpolated '%s' value\n", "hello");

        byte b = 10;
        short s = 100;
        int i = 10000;
        long l = 100000000;

        String str1 = "Hellow";
        String str3 = new String("Hellow");

        if (str1.equals(str3)) {
            System.out.println("str equals str3");
        }
    }
    private void arrDemo2() {
        int[] arr1 = {1, 2, 3, 4, 5};
        int[] arr2 = new int[] {1, 2, 3, 4, 5};
        for (int i = 0; i < arr1.length; i++) {
            System.out.println(arr1[i] + " ");
        }
        System.out.println();

    }

    private void arrDemo3() {
        List<Integer> list1 = new ArrayList<Integer>();
        list1.add( 10 );
        list1.add( 20 );
        list1.add( 30 );
        list1.add( 40 );
        list1.add( 50 );

        for ( Integer x: list1 ) {
            System.out.println(x + " ");
        }
        System.out.println();
    }
}
