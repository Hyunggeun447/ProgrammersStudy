package com.kdt.progmrs.kdt.order;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
public class HamcrestAssertionTests {

    @Test
    @DisplayName("TESTS")
    public void hamcrestTest() throws Exception {
        assertEquals(2, 1 + 1);
        assertThat(1 + 1, equalTo(2));
        assertThat(1 + 1, is(2));
        assertThat(1 + 1, anyOf(is(1), is(2)));

        assertNotEquals(1, 1 + 1);
        assertThat(1 + 1, not(equalTo(1)));

    }

    @Test
    @DisplayName("컬렉션에 대한 메쳐 테스트")
    public void hamcrestListMatcherTest() throws Exception {

        List<Integer> integers = List.of(2, 3, 4);
        assertThat(integers, hasSize(3));
        assertThat(integers, everyItem(greaterThan(1)));
        assertThat(integers, containsInAnyOrder(3, 4, 2));
        assertThat(integers, hasItem(greaterThanOrEqualTo(2)));
    }
}
