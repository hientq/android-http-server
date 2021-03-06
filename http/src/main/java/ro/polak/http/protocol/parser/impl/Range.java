/**************************************************
 * Android Web Server
 * Based on JavaLittleWebServer (2008)
 * <p/>
 * Copyright (c) Piotr Polak 2017-2017
 **************************************************/
package ro.polak.http.protocol.parser.impl;

/**
 * Represents HTTP range.
 *
 * @author Piotr Polak piotr [at] polak [dot] ro
 * @since 201702
 */
public class Range {

    private long from;
    private long to;

    public Range() {
    }

    public Range(long from, long to) {
        this.from = from;
        this.to = to;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }
}
