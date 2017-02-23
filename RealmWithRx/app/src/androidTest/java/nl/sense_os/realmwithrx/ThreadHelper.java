package nl.sense_os.realmwithrx;

import android.support.annotation.Nullable;

/**
 * Created by panjiyudasetya on 2/22/17.
 */

public class ThreadHelper {
    static class Notifier {
        private boolean accepted;

        public Notifier() {
            reset();
        }

        public void reset() {
            this.accepted = false;
        }

        public void accept() {
            this.accepted = true;
        }

        public boolean isAccepted() {
            return accepted;
        }
    }

    /**
     * It will hold the main thread until x-minutes
     * @param minutes X-minutes
     * @param notifier Conditional accepted notifier
     */
    public static void waitUntil(int minutes, @Nullable Notifier notifier) throws Exception {
        final int A_SECOND = 1000;
        final int A_MINUTE = 10 * A_SECOND;
        final int MAX_WAITING_TIME = minutes * A_MINUTE;
        int TIME_COUNTER = 0;
        do {
            TIME_COUNTER += A_SECOND;
            Thread.sleep(A_SECOND);
        } while (!(notifier == null ? false : notifier.isAccepted()) && TIME_COUNTER < MAX_WAITING_TIME);
    }
}
