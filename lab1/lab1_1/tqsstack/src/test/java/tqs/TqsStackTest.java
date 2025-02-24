package tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;
import java.util.NoSuchElementException;

public class TqsStackTest {
    static final Logger log = getLogger(lookup().lookupClass());
    
    TqsStack<Object> mySut;

    @BeforeEach
    public void init() {
        mySut = new TqsStack<>();
    }

    // A stack is empty on construction
    @Test
    public void testEmptyStack() {
        log.debug("testEmptyStack");

        // exercise:
        boolean result = mySut.isEmpty();

        // verify:
        assert(result);
    }
    
    // A stack has size 0 on construction
    @Test
    public void testSizeZero() {
        log.debug("testEmptyStack");

        // exercise:
        int result = mySut.size();

        // verify:
        assert(result == 0);
    }

    // After n > 0 pushes to an empty stack, the stack is not empty and its size is n
    @Test
    public void checkSizeAfterPush(){
        log.debug("checkSizeAfterPush");

        // exercise:
        mySut.push("a");
        mySut.push(1);
        mySut.push(true);

        // verify:
        assert(mySut.size() == 3);
    }

    // If one pushes x then pops, the value popped is x
    @Test
    public void pushThenPop() {
        log.debug("pushThenPop");

        // exercise:
        mySut.push("a");
        Object popped = mySut.pop();

        // verify:
        assert(popped == "a");
    }

    // If one pushes x then peeks, the value returned is x, but the size stays the same
    @Test
    public void pushThenPeek() {
        log.debug("pushThenPeek");

        // exercise:
        mySut.push("b");
        Object peeked = mySut.peek();

        // verify:
        assert(peeked == "b");
        assert(mySut.size() == 1);
    }

    // If the size is n, then after n pops, the stack is empty and has a size 0
    @Test
    public void popWholeStack() {
        log.debug("popWholeStack");

        // exercise:
        mySut.push(1);
        mySut.push(2);
        mySut.pop();
        mySut.pop();

        // verify:
        assert(mySut.isEmpty());
        assert(mySut.size() == 0);
    }

    // Popping from an empty stack throws a NoSuchElementException
    @Test
    public void PoppingEmptyNoSuchElementException() {
        log.debug("NoSuchElementException");

        try {
            mySut.pop();
            assert(false);
        } catch (Exception e) {
            assert(e instanceof NoSuchElementException);
        }
    }

    // Peeking into an empty stack throws a NoSuchElementException
    @Test
    public void PeekingEmptyNoSuchElementException() {
        log.debug("NoSuchElementException");

        try {
            mySut.peek();
            assert(false);
        } catch (Exception e) {
            assert(e instanceof NoSuchElementException);
        }
    }

    // For bounded stacks only, pushing onto a full stack throws an IllegalStateException
    @Test
    public void PushingFullIllegalStateException() {
        log.debug("IllegalStateException");

        TqsStack<Object> mySut_bounded = new TqsStack<>(3);

        // exercise:
        mySut_bounded.push(1);
        mySut_bounded.push(2);
        mySut_bounded.push(3);

        try {
            mySut_bounded.push(4);
            assert(false);
        } catch (Exception e) {
            assert(e instanceof IllegalStateException);
        }
    }

    // Test the popTopN method
    @Test
    public void testPopTopN() {
        log.debug("testPopTopN");

        // exercise:
        mySut.push(1);
        mySut.push(2);
        mySut.push(3);
        mySut.push(4);
        mySut.push(5);
        mySut.push(6);
        mySut.push(7);
        mySut.push(8);
        mySut.push(9);
        mySut.push(10);

        Object popped = mySut.popTopN(3);

        // verify:
        assert(popped == 3);
        assert(mySut.size() == 7);
    }
}
