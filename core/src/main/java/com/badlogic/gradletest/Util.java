package com.badlogic.gradletest;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 2014/05/29
 * Time: 11:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class Util {

    public static Integer getRandomNumberBetween(int from, int to) {
        int x = (int)Math.round(((to - from) / 2) * (Math.random())) + (int)Math.round(((to - from) / 2) * Math.random());
//        System.out.println("from: "+from+" to: "+to);
//        System.out.println("x: "+x);
        return x;
    }
    public static Integer getRandomNumberBetween(float from, float to) {
        int x = (int)Math.round(((to - from) / 2) * (Math.random())) + (int)Math.round(((to - from) / 2) * Math.random());
//        System.out.println("from: "+from+" to: "+to);
//        System.out.println("x: "+x);
        return x;
    }

    public static <T> void cleanNulls( final ArrayList<T> lst )
    {
        int pFrom = 0;
        int pTo = 0;
        final int len = lst.size();
        //copy all not-null elements towards list head
        while ( pFrom < len )
        {
            if ( lst.get( pFrom ) != null )
                lst.set( pTo++, lst.get( pFrom ) );
            ++pFrom;
        }
        //there are some elements left in the tail - clean them
        lst.subList( pTo, len ).clear();
    }
}
