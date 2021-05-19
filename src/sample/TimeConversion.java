package sample;

import java.sql.Timestamp;

public interface TimeConversion {
    Timestamp convertTime(Timestamp ts, String inputTimeZone, String outputTimeZone);
}
