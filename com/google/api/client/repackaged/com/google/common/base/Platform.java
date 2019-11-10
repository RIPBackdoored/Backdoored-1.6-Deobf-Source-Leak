package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;
import java.lang.ref.*;
import javax.annotation.*;
import java.util.*;
import java.util.logging.*;
import java.util.regex.*;

@GwtCompatible(emulated = true)
final class Platform
{
    private static final Logger logger;
    private static final PatternCompiler patternCompiler;
    
    private Platform() {
        super();
    }
    
    static long systemNanoTime() {
        return System.nanoTime();
    }
    
    static CharMatcher precomputeCharMatcher(final CharMatcher matcher) {
        return matcher.precomputedInternal();
    }
    
    static <T extends Enum<T>> Optional<T> getEnumIfPresent(final Class<T> enumClass, final String value) {
        final WeakReference<? extends Enum<?>> ref = Enums.getEnumConstants(enumClass).get(value);
        return (ref == null) ? Optional.absent() : Optional.of(enumClass.cast(ref.get()));
    }
    
    static String formatCompact4Digits(final double value) {
        return String.format(Locale.ROOT, "%.4g", value);
    }
    
    static boolean stringIsNullOrEmpty(@Nullable final String string) {
        return string == null || string.isEmpty();
    }
    
    static CommonPattern compilePattern(final String pattern) {
        Preconditions.checkNotNull(pattern);
        return Platform.patternCompiler.compile(pattern);
    }
    
    static boolean usingJdkPatternCompiler() {
        return Platform.patternCompiler instanceof JdkPatternCompiler;
    }
    
    private static PatternCompiler loadPatternCompiler() {
        return new JdkPatternCompiler();
    }
    
    private static void logPatternCompilerError(final ServiceConfigurationError e) {
        Platform.logger.log(Level.WARNING, "Error loading regex compiler, falling back to next option", e);
    }
    
    static {
        logger = Logger.getLogger(Platform.class.getName());
        patternCompiler = loadPatternCompiler();
    }
    
    private static final class JdkPatternCompiler implements PatternCompiler
    {
        private JdkPatternCompiler() {
            super();
        }
        
        @Override
        public CommonPattern compile(final String pattern) {
            return new JdkPattern(Pattern.compile(pattern));
        }
        
        JdkPatternCompiler(final Platform$1 x0) {
            this();
        }
    }
}
