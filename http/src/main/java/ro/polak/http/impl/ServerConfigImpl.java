/**************************************************
 * Android Web Server
 * Based on JavaLittleWebServer (2008)
 * <p>
 * Copyright (c) Piotr Polak 2008-2016
 **************************************************/

package ro.polak.http.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ro.polak.http.MimeTypeMapping;
import ro.polak.http.ServerConfig;
import ro.polak.http.resource.provider.ResourceProvider;
import ro.polak.http.servlet.HttpRequestWrapper;
import ro.polak.http.utilities.ConfigReader;

/**
 * Server configuration
 *
 * @author Piotr Polak piotr [at] polak [dot] ro
 * @since 201509
 */
public class ServerConfigImpl implements ServerConfig {

    private static final String[] SUPPORTED_METHODS = new String[]{
            HttpRequestWrapper.METHOD_GET,
            HttpRequestWrapper.METHOD_POST,
            HttpRequestWrapper.METHOD_HEAD
    };
    public List<String> directoryIndex;
    private String basePath;
    private String documentRootPath;
    private String tempPath;
    private int listenPort;
    private String servletMappedExtension;
    private MimeTypeMapping mimeTypeMapping;
    private int maxServerThreads;
    private boolean keepAlive;
    private String errorDocument404Path;
    private String errorDocument403Path;
    private ResourceProvider[] resourceProviders = {};

    public ServerConfigImpl() {
        this("/httpd/temp/");
    }

    public ServerConfigImpl(String tempPath) {
        this.tempPath = tempPath;
        basePath = "/httpd/";
        documentRootPath = basePath + "www/";
        listenPort = 8080;
        servletMappedExtension = "dhtml";
        maxServerThreads = 10;
        directoryIndex = new ArrayList(Arrays.asList("Index.dhtml", "index.html", "index.htm"));
    }

    /**
     * @param basePath
     * @param tempPath
     * @return
     * @throws IOException
     */
    public static ServerConfigImpl createFromPath(String basePath, String tempPath) throws IOException {
        ConfigReader reader = new ConfigReader();
        InputStream configInputStream = new FileInputStream(basePath + "httpd.conf");
        Map<String, String> config = reader.read(configInputStream);
        try {
            configInputStream.close();
        } catch (IOException e) {
            // Close silently
        }

        ServerConfigImpl serverConfig = new ServerConfigImpl();
        serverConfig.basePath = basePath;
        serverConfig.tempPath = tempPath;
        serverConfig.documentRootPath = basePath + "www/";

        if (config.containsKey("Listen")) {
            serverConfig.listenPort = Integer.parseInt(config.get("Listen"));
        }

        if (config.containsKey("DocumentRoot")) {
            serverConfig.documentRootPath = basePath + config.get("DocumentRoot");
        }

        if (config.containsKey("MaxThreads")) {
            serverConfig.maxServerThreads = Integer.parseInt(config.get("MaxThreads"));
        }

        if (config.containsKey("KeepAlive")) {
            serverConfig.keepAlive = config.get("KeepAlive").equalsIgnoreCase("on");
        }

        if (config.containsKey("ErrorDocument404")) {
            serverConfig.errorDocument404Path = basePath + config.get("ErrorDocument404");
        }
        if (config.containsKey("ErrorDocument403")) {
            serverConfig.errorDocument403Path = basePath + config.get("ErrorDocument403");
        }

        if (config.containsKey("ServletMappedExtension")) {
            serverConfig.servletMappedExtension = config.get("ServletMappedExtension");
        }

        // Initializing mime mapping
        if (config.containsKey("MimeType")) {
            String defaultMimeType = "text/plain";
            if (config.containsKey("DefaultMimeType")) {
                defaultMimeType = config.get("DefaultMimeType");
            }

            InputStream mimeInputStream = new FileInputStream(basePath + config.get("MimeType"));
            serverConfig.mimeTypeMapping = MimeTypeMappingImpl.createFromStream(mimeInputStream, defaultMimeType);

            try {
                mimeInputStream.close();
            } catch (IOException e) {
                // Close silently
            }
        }

        // Generating index files
        if (config.containsKey("DirectoryIndex")) {
            String directoryIndexLine[] = config.get("DirectoryIndex").split(" ");
            for (int i = 0; i < directoryIndexLine.length; i++) {
                serverConfig.directoryIndex.add(directoryIndexLine[i]);
            }
        }

        if (serverConfig.mimeTypeMapping == null) {
            // Initializing an empty mime type mapping to prevent null pointer exceptions
            serverConfig.mimeTypeMapping = new MimeTypeMappingImpl();
        }

        return serverConfig;
    }

    @Override
    public String getBasePath() {
        return basePath;
    }

    @Override
    public String getDocumentRootPath() {
        return documentRootPath;
    }

    @Override
    public String getTempPath() {
        return tempPath;
    }

    @Override
    public int getListenPort() {
        return listenPort;
    }

    @Override
    public String getServletMappedExtension() {
        return servletMappedExtension;
    }

    @Override
    public MimeTypeMapping getMimeTypeMapping() {
        return mimeTypeMapping;
    }

    @Override
    public int getMaxServerThreads() {
        return maxServerThreads;
    }

    @Override
    public boolean isKeepAlive() {
        return keepAlive;
    }

    @Override
    public String getErrorDocument404Path() {
        return errorDocument404Path;
    }

    @Override
    public String getErrorDocument403Path() {
        return errorDocument403Path;
    }

    @Override
    public List<String> getDirectoryIndex() {
        return directoryIndex;
    }

    @Override
    public String[] getSupportedMethods() {
        return SUPPORTED_METHODS;
    }

    @Override
    public ResourceProvider[] getResourceProviders() {
        return resourceProviders;
    }

    public void setResourceProviders(ResourceProvider[] resourceProviders) {
        this.resourceProviders = resourceProviders;
    }
}
