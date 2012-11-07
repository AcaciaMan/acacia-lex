/*
 * Copyright 2012 Acacia Man
 * The program is distributed under the terms of the GNU General Public License
 * 
 * This file is part of acacia-lex.
 *
 * acacia-lex is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * acacia-lex is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with acacia-lex.  If not, see <http://www.gnu.org/licenses/>.
 */ 

package test.common;

import java.nio.CharBuffer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class FlipBuffer {

    public FlipBuffer() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}


/**
 * Test the effects of buffer flipping.
 *
 */
 @Test
    public void testCharBuffer(){
    CharBuffer cb = CharBuffer.allocate(15);

    cb.put("Hello World23");
    println(cb);

    cb.flip();
    println(cb);

   // cb.clear();
   // println(cb);

    cb.put("Hello World");
    println(cb);

    cb.flip();
    println(cb);

    cb.clear();
    println(cb);


    assertTrue(true);
  }

  private static void println(CharBuffer cb) {
    System.out.println("pos=" + cb.position() + ", limit=" + cb.limit() + ": '" + cb + "'");
  }
}

