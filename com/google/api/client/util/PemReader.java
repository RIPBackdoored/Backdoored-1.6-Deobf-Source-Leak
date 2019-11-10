package com.google.api.client.util;

import java.io.*;
import java.util.regex.*;

@Beta
public final class PemReader
{
    private static final Pattern BEGIN_PATTERN;
    private static final Pattern END_PATTERN;
    private BufferedReader reader;
    
    public PemReader(final Reader reader) {
        super();
        this.reader = new BufferedReader(reader);
    }
    
    public Section readNextSection() throws IOException {
        return this.readNextSection(null);
    }
    
    public Section readNextSection(final String titleToLookFor) throws IOException {
        String title = null;
        StringBuilder keyBuilder = null;
        while (true) {
            final String line = this.reader.readLine();
            if (line == null) {
                Preconditions.checkArgument(title == null, "missing end tag (%s)", title);
                return null;
            }
            if (keyBuilder == null) {
                final Matcher m = PemReader.BEGIN_PATTERN.matcher(line);
                if (!m.matches()) {
                    continue;
                }
                final String curTitle = m.group(1);
                if (titleToLookFor != null && !curTitle.equals(titleToLookFor)) {
                    continue;
                }
                keyBuilder = new StringBuilder();
                title = curTitle;
            }
            else {
                final Matcher m = PemReader.END_PATTERN.matcher(line);
                if (m.matches()) {
                    final String endTitle = m.group(1);
                    Preconditions.checkArgument(endTitle.equals(title), "end tag (%s) doesn't match begin tag (%s)", endTitle, title);
                    return new Section(title, Base64.decodeBase64(keyBuilder.toString()));
                }
                keyBuilder.append(line);
            }
        }
    }
    
    public static Section readFirstSectionAndClose(final Reader reader) throws IOException {
        return readFirstSectionAndClose(reader, null);
    }
    
    public static Section readFirstSectionAndClose(final Reader reader, final String titleToLookFor) throws IOException {
        final PemReader pemReader = new PemReader(reader);
        try {
            return pemReader.readNextSection(titleToLookFor);
        }
        finally {
            pemReader.close();
        }
    }
    
    public void close() throws IOException {
        this.reader.close();
    }
    
    static {
        BEGIN_PATTERN = Pattern.compile("-----BEGIN ([A-Z ]+)-----");
        END_PATTERN = Pattern.compile("-----END ([A-Z ]+)-----");
    }
    
    public static final class Section
    {
        private final String title;
        private final byte[] base64decodedBytes;
        
        Section(final String title, final byte[] base64decodedBytes) {
            super();
            this.title = Preconditions.checkNotNull(title);
            this.base64decodedBytes = Preconditions.checkNotNull(base64decodedBytes);
        }
        
        public String getTitle() {
            return this.title;
        }
        
        public byte[] getBase64DecodedBytes() {
            return this.base64decodedBytes;
        }
    }
}
