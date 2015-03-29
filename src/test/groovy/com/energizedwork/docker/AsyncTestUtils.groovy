package com.energizedwork.docker


trait AsyncTestUtils {

    float timeoutInSeconds = 5
    float intervalInSeconds = 0.5

    static TestCondition condition(String name, Closure check) {
        new TestCondition(name: name, check: check)
    }

    boolean waitFor(Closure check) {
        waitFor(new TestCondition(check: check))
    }

    boolean waitFor(String name, Closure check) {
        waitFor(new TestCondition(name: name, check: check))
    }

    boolean waitFor(TestCondition condition) {
        def loops = Math.ceil(timeoutInSeconds / intervalInSeconds)
        boolean pass = condition.check()
        def currentIndex = 0
        while (!pass && currentIndex++ < loops) {
            Thread.sleep intervalInMillis
            pass = condition.check()
        }
        if (currentIndex >= loops) {
            throw new AssertionError("[$condition.name] did not pass in $timeoutInSeconds seconds")
        }
        true
    }

    Long getIntervalInMillis() {
        intervalInSeconds * 1000
    }

}

class TestCondition {

    String name
    Closure check

    String getName() {
        name ?: 'condition'
    }

}
