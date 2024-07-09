package EMS_Util;

public class TimestampIdGenerator {
    private static int lastTimestamp = (int) (System.currentTimeMillis() / 1000);

    public synchronized static int generateId() {
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        if (timestamp == lastTimestamp) {
            timestamp++;
        }
        lastTimestamp = timestamp;
        return timestamp;
    }
}
