package org.spongepowered.asm.mixin.transformer.debug;

import org.spongepowered.asm.mixin.transformer.ext.*;
import java.util.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.*;
import org.apache.commons.io.*;
import java.io.*;
import org.jetbrains.java.decompiler.main.extern.*;
import org.jetbrains.java.decompiler.util.*;
import org.jetbrains.java.decompiler.main.*;
import com.google.common.base.*;
import com.google.common.io.*;
import java.util.jar.*;

public class RuntimeDecompiler extends IFernflowerLogger implements IDecompiler, IResultSaver
{
    private static final Level[] SEVERITY_LEVELS;
    private final Map<String, Object> options;
    private final File outputPath;
    protected final Logger logger;
    
    public RuntimeDecompiler(final File outputPath) {
        super();
        this.options = (Map<String, Object>)ImmutableMap.builder().put((Object)"din", (Object)"0").put((Object)"rbr", (Object)"0").put((Object)"dgs", (Object)"1").put((Object)"asc", (Object)"1").put((Object)"den", (Object)"1").put((Object)"hdc", (Object)"1").put((Object)"ind", (Object)"    ").build();
        this.logger = LogManager.getLogger("fernflower");
        this.outputPath = outputPath;
        if (this.outputPath.exists()) {
            try {
                FileUtils.deleteDirectory(this.outputPath);
            }
            catch (IOException ex) {
                this.logger.warn("Error cleaning output directory: {}", new Object[] { ex.getMessage() });
            }
        }
    }
    
    @Override
    public void decompile(final File file) {
        try {
            final Fernflower fernflower = new Fernflower(new IBytecodeProvider() {
                private byte[] byteCode;
                final /* synthetic */ RuntimeDecompiler this$0;
                
                RuntimeDecompiler$1() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public byte[] getBytecode(final String externalPath, final String internalPath) throws IOException {
                    if (this.byteCode == null) {
                        this.byteCode = InterpreterUtil.getBytes(new File(externalPath));
                    }
                    return this.byteCode;
                }
            }, this, this.options, this);
            fernflower.getStructContext().addSpace(file, true);
            fernflower.decompileContext();
        }
        catch (Throwable ex) {
            this.logger.warn("Decompilation error while processing {}", new Object[] { file.getName() });
        }
    }
    
    @Override
    public void saveFolder(final String path) {
    }
    
    @Override
    public void saveClassFile(final String path, final String qualifiedName, final String entryName, final String content, final int[] mapping) {
        final File file = new File(this.outputPath, qualifiedName + ".java");
        file.getParentFile().mkdirs();
        try {
            this.logger.info("Writing {}", new Object[] { file.getAbsolutePath() });
            Files.write((CharSequence)content, file, Charsets.UTF_8);
        }
        catch (IOException ex) {
            this.writeMessage("Cannot write source file " + file, ex);
        }
    }
    
    @Override
    public void startReadingClass(final String className) {
        this.logger.info("Decompiling {}", new Object[] { className });
    }
    
    @Override
    public void writeMessage(final String message, final Severity severity) {
        this.logger.log(RuntimeDecompiler.SEVERITY_LEVELS[severity.ordinal()], message);
    }
    
    @Override
    public void writeMessage(final String message, final Throwable t) {
        this.logger.warn("{} {}: {}", new Object[] { message, t.getClass().getSimpleName(), t.getMessage() });
    }
    
    @Override
    public void writeMessage(final String message, final Severity severity, final Throwable t) {
        this.logger.log(RuntimeDecompiler.SEVERITY_LEVELS[severity.ordinal()], message, t);
    }
    
    @Override
    public void copyFile(final String source, final String path, final String entryName) {
    }
    
    @Override
    public void createArchive(final String path, final String archiveName, final Manifest manifest) {
    }
    
    @Override
    public void saveDirEntry(final String path, final String archiveName, final String entryName) {
    }
    
    @Override
    public void copyEntry(final String source, final String path, final String archiveName, final String entry) {
    }
    
    @Override
    public void saveClassEntry(final String path, final String archiveName, final String qualifiedName, final String entryName, final String content) {
    }
    
    @Override
    public void closeArchive(final String path, final String archiveName) {
    }
    
    static {
        SEVERITY_LEVELS = new Level[] { Level.TRACE, Level.INFO, Level.WARN, Level.ERROR };
    }
}
