package tests.utility;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class LoggerCuriosity
{
    public static final Logger log = LoggerFactory.getLogger(Logger.class);

    public static void info(String message)
    {
        log.info(message);
    }

    public static void error(String message)
    {
        log.error(message);
    }

    public static void debug(String message)
    {
        log.debug(message);
    }


}
