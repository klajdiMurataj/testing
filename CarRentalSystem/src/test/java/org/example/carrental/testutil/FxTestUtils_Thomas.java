package org.example.carrental.testutil;

import javafx.application.Platform;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public final class FxTestUtils_Thomas {
    private static volatile boolean started = false;

    private FxTestUtils_Thomas() {}

    public static void initFx() {
        if (started) return;
        started = true;

        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }

        try {
            boolean ok = latch.await(10, TimeUnit.SECONDS);
            if (!ok) throw new RuntimeException("JavaFX did not initialize in time");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("JavaFX init interrupted", e);
        }
    }

    public static void runOnFxAndWait(Runnable action) {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try { action.run(); }
            finally { latch.countDown(); }
        });

        try {
            boolean finished = latch.await(10, TimeUnit.SECONDS);
            if (!finished) throw new RuntimeException("FX task timed out (did not finish in time)");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("FX task interrupted", e);
        }
    }

    public static <T> T supplyOnFxAndWait(Supplier<T> supplier) {
        AtomicReference<T> ref = new AtomicReference<>();
        runOnFxAndWait(() -> ref.set(supplier.get()));
        return ref.get();
    }
}
