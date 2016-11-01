package org.bitbucket.ntakimura.android.logger;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import android.util.Log;

/**
 * Android Logger.
 *
 * @author takimura
 */
public class AndroidHandler extends Handler {

    /**
     * Log Tag.
     */
    private String mTag = null;

    /**
     * Constructor.
     */
    public AndroidHandler() {
        this(null);
    }

    /**
     * Constructor with tag.
     * 
     * @param tag Log Tag
     */
    public AndroidHandler(final String tag) {
        setTag(tag);
    }

    @Override
    public void close() {
    }

    @Override
    public void flush() {
    }

    @Override
    public void publish(final LogRecord record) {
        if (record.getLevel().equals(Level.FINEST)) {
            Log.v(mTag, getFormatter().format(record));
        }
        if (record.getLevel().equals(Level.FINER)) {
            Log.v(mTag, getFormatter().format(record));
        }
        if (record.getLevel().equals(Level.FINE)) {
            Log.d(mTag, getFormatter().format(record));
        }
        if (record.getLevel().equals(Level.INFO)) {
            Log.i(mTag, getFormatter().format(record));
        }
        if (record.getLevel().equals(Level.WARNING)) {
            Log.w(mTag, getFormatter().format(record));
        }
        if (record.getLevel().equals(Level.SEVERE)) {
            Log.e(mTag, getFormatter().format(record));
        }
    }

    /**
     * Set Log Tag.
     * @param tag Log Tag
     */
    public void setTag(final String tag) {
        mTag = tag;
    }

}
