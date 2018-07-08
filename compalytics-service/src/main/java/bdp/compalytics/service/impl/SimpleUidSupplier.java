package bdp.compalytics.service.impl;

import bdp.compalytics.service.UidSupplier;

import java.util.Random;

public class SimpleUidSupplier implements UidSupplier {
    /* Thread-safe. Lots of contention may cause performance problems, but we're not expecting a lot of contention.
       Not cryptographically secure, but don't really need that level of fidelity here. */
    private static final Random RANDOM = new Random();

    /* The length of the randomly generated uids. */
    private static final int LENGTH = 8;

    @Override
    public String get() {
        StringBuilder uid = new StringBuilder(LENGTH);
        byte[] randoms = new byte[LENGTH];
        RANDOM.nextBytes(randoms);
        for (int i = 0; i < LENGTH; i++) {
            int rand = Math.abs(randoms[i]) % (10 + 26 + 26);
            if (rand < 10) {
                uid.append((char) ('0' + rand));
            } else if (rand < 10 + 26) {
                uid.append((char) ('a' + (rand - 10)));
            } else {
                uid.append((char) ('A' + (rand - 10 - 26)));
            }
        }
        return uid.toString();
    }
}
