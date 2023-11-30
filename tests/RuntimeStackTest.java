package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Stack;
import java.util.Vector;

import org.junit.jupiter.api.Test;

import interpreter.RuntimeStack;

public class RuntimeStackTest {

  @Test
  public void testPush() {
    Stack<Integer> framePointers = new Stack<>();
    Vector<Integer> runStack = new Vector<>();

    RuntimeStack stack = new RuntimeStack(framePointers, runStack);
    stack.push(77);
    stack.push(1892743);
    stack.push(42);

    assertEquals(77, runStack.get(0));
    assertEquals(42, runStack.get(2));
    assertEquals(1892743, runStack.get(1));
  }

  @Test
  public void testPushBoxedInt() {
    Stack<Integer> framePointers = new Stack<>();
    Vector<Integer> runStack = new Vector<>();

    RuntimeStack stack = new RuntimeStack(framePointers, runStack);
    stack.push(Integer.valueOf(77));
    stack.push(Integer.valueOf(1892743));
    stack.push(Integer.valueOf(42));

    assertEquals(77, runStack.get(0));
    assertEquals(42, runStack.get(2));
    assertEquals(1892743, runStack.get(1));
  }

  @Test
  public void testPeek() {
    Stack<Integer> framePointers = new Stack<>();
    Vector<Integer> runStack = new Vector<>();

    RuntimeStack stack = new RuntimeStack(framePointers, runStack);
    stack.push(1);
    stack.push(42);
    stack.push(99);

    assertEquals(99, stack.peek());
  }

  @Test
  public void testPop() {
    Stack<Integer> framePointers = new Stack<>();
    Vector<Integer> runStack = new Vector<>();

    RuntimeStack stack = new RuntimeStack(framePointers, runStack);
    stack.push(12);
    stack.push(423);
    stack.push(9);

    assertEquals(9, stack.pop());
    assertEquals(423, stack.pop());
  }

  @Test
  public void testNewFrameAtZeroOffset() {
    Stack<Integer> framePointers = new Stack<>();
    Vector<Integer> runStack = new Vector<>();

    RuntimeStack stack = new RuntimeStack(framePointers, runStack);
    stack.push(42);
    stack.push(7);
    stack.push(3);

    stack.newFrameAt(0);

    assertEquals(3, framePointers.pop());
    assertEquals(0, framePointers.pop());
  }

  @Test
  public void testNewFrameAtOneFrame() {
    Stack<Integer> framePointers = new Stack<>();
    Vector<Integer> runStack = new Vector<>();

    RuntimeStack stack = new RuntimeStack(framePointers, runStack);
    stack.push(42);
    stack.push(7);
    stack.push(3);

    stack.newFrameAt(2);

    assertEquals(1, framePointers.pop());
    assertEquals(0, framePointers.pop());
  }

  @Test
  public void testNewFrameAtManyFrames() {
    Stack<Integer> framePointers = new Stack<>();
    Vector<Integer> runStack = new Vector<>();

    RuntimeStack stack = new RuntimeStack(framePointers, runStack);

    for (int i = 0; i < 25; i++) {
      stack.push(i);
    }

    stack.newFrameAt(5);

    for (int i = 25; i < 50; i++) {
      stack.push(i);
    }

    stack.newFrameAt(7);

    for (int i = 50; i < 100; i++) {
      stack.push(i);
    }

    stack.newFrameAt(2);

    assertEquals(98, framePointers.pop());
    assertEquals(43, framePointers.pop());
    assertEquals(20, framePointers.pop());
    assertEquals(0, framePointers.pop());
  }

  @Test
  public void testPopFrameTwoFrames() {
    Stack<Integer> framePointers = new Stack<>();
    Vector<Integer> runStack = new Vector<>();

    RuntimeStack stack = new RuntimeStack(framePointers, runStack);

    for (int i = 0; i < 25; i++) {
      stack.push(i);
    }

    stack.newFrameAt(5);

    for (int i = 25; i < 50; i++) {
      stack.push(i);
    }

    stack.popFrame();

    assertEquals(0, framePointers.pop());
  }

  @Test
  public void testPopFrameManyFrames() {
    Stack<Integer> framePointers = new Stack<>();
    Vector<Integer> runStack = new Vector<>();

    RuntimeStack stack = new RuntimeStack(framePointers, runStack);

    for (int i = 0; i < 25; i++) {
      stack.push(i);
    }

    stack.newFrameAt(5);

    for (int i = 25; i < 50; i++) {
      stack.push(i);
    }

    stack.newFrameAt(7);

    for (int i = 50; i < 100; i++) {
      stack.push(i);
    }

    stack.newFrameAt(2);

    stack.popFrame();
    stack.popFrame();
    stack.popFrame();

    assertEquals(0, framePointers.pop());
  }

  @Test
  public void testStoreMainFrameOnly() {
    Stack<Integer> framePointers = new Stack<>();
    Vector<Integer> runStack = new Vector<>();

    RuntimeStack stack = new RuntimeStack(framePointers, runStack);
    stack.push(99);
    stack.push(12);
    stack.push(42);

    int result = stack.store(1);

    assertEquals(2, runStack.size());
    assertEquals(42, runStack.get(1));
    assertEquals(99, runStack.get(0));
    assertEquals(42, result);
  }

  @Test
  public void testStoreManyFrames() {
    Stack<Integer> framePointers = new Stack<>();
    Vector<Integer> runStack = new Vector<>();

    RuntimeStack stack = new RuntimeStack(framePointers, runStack);
    stack.push(99);
    stack.push(12);
    stack.push(42);
    stack.push(5);
    stack.push(100);

    stack.newFrameAt(2);

    int result = stack.store(0);

    assertEquals(4, runStack.size());
    assertEquals(42, runStack.get(2));
    assertEquals(12, runStack.get(1));
    assertEquals(100, runStack.get(3));
    assertEquals(99, runStack.get(0));
    assertEquals(100, result);
  }

  @Test
  public void testLoadMainFrameOnly() {
    Stack<Integer> framePointers = new Stack<>();
    Vector<Integer> runStack = new Vector<>();

    RuntimeStack stack = new RuntimeStack(framePointers, runStack);
    stack.push(99);
    stack.push(12);
    stack.push(42);

    int result = stack.load(1);

    assertEquals(4, runStack.size());
    assertEquals(42, runStack.get(2));
    assertEquals(12, runStack.get(1));
    assertEquals(12, runStack.get(3));
    assertEquals(99, runStack.get(0));
    assertEquals(12, result);
  }

  @Test
  public void testLoadManyFrames() {
    Stack<Integer> framePointers = new Stack<>();
    Vector<Integer> runStack = new Vector<>();

    RuntimeStack stack = new RuntimeStack(framePointers, runStack);
    stack.push(99);
    stack.push(12);
    stack.push(42);
    stack.push(5);
    stack.push(100);

    stack.newFrameAt(2);

    int result = stack.load(0);

    assertEquals(6, runStack.size());

    assertEquals(5, runStack.get(5));
    assertEquals(100, runStack.get(4));
    assertEquals(5, runStack.get(3));
    assertEquals(42, runStack.get(2));
    assertEquals(12, runStack.get(1));
    assertEquals(99, runStack.get(0));
    assertEquals(5, result);
  }

  @Test
  public void testToStringEmpty() {
    RuntimeStack runStack = new RuntimeStack();

    assertEquals("[]", runStack.toString());
  }

  @Test
  public void testToStringSingleValue() {
    RuntimeStack runStack = new RuntimeStack();
    runStack.push(42);

    assertEquals("[42]", runStack.toString());
  }

  @Test
  public void testToStringManyValues() {
    RuntimeStack runStack = new RuntimeStack();
    runStack.push(42);
    runStack.push(38);
    runStack.push(76);

    assertEquals("[42,38,76]", runStack.toString());
  }

  @Test
  public void testToStringManyFrames() {
    RuntimeStack runStack = new RuntimeStack();
    runStack.push(42);
    runStack.push(38);
    runStack.push(76);
    runStack.newFrameAt(1);
    runStack.push(1);
    runStack.push(3);
    runStack.push(2);
    runStack.push(4);
    runStack.push(6);
    runStack.push(5);
    runStack.newFrameAt(3);

    assertEquals("[42,38] [76,1,3,2] [4,6,5]", runStack.toString());
  }
}