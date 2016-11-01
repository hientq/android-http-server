/**************************************************
 * Android Web Server
 * Based on JavaLittleWebServer (2008)
 * <p/>
 * Copyright (c) Piotr Polak 2008-2016
 **************************************************/

package ro.polak.webserver;

import java.util.List;

/**
 * Server configuration
 *
 * @author Piotr Polak piotr [at] polak [dot] ro
 * @since 201509
 */
public interface ServerConfig {

    /**
     * Returns base path
     *
     * @return
     */
    String getBasePath();

    /**
     * Returns document root path
     *
     * @return
     */
    String getDocumentRootPath();

    /**
     * Returns server temp path
     *
     * @return
     */
    String getTempPath();

    /**
     * Returns the listen port
     *
     * @return
     */
    int getListenPort();

    /**
     * Returns the servlet mapped extension
     *
     * @return
     */
    String getServletMappedExtension();

    /**
     * Returns the mime type mapping
     *
     * @return
     */
    MimeTypeMapping getMimeTypeMapping();

    /**
     * Returns the number of maximum allowed threads
     *
     * @return
     */
    int getMaxServerThreads();

    /**
     * Returns whether the server should keep the connections alive
     *
     * @return
     */
    boolean isKeepAlive();

    /**
     * Returns error 404 file path
     *
     * @return
     */
    String getErrorDocument404Path();

    /**
     * Returns the error 403 file path
     *
     * @return
     */
    String getErrorDocument403Path();

    /**
     * Returns the directory index
     *
     * @return
     */
    List<String> getDirectoryIndex();
}
