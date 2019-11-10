package org.reflections.vfs;

import com.google.common.base.*;
import org.reflections.util.*;

static final class Vfs$1 implements Predicate<File> {
    final /* synthetic */ String val$packagePrefix;
    final /* synthetic */ Predicate val$nameFilter;
    
    Vfs$1(final String val$packagePrefix, final Predicate val$nameFilter) {
        this.val$packagePrefix = val$packagePrefix;
        this.val$nameFilter = val$nameFilter;
        super();
    }
    
    @Override
    public boolean apply(final File file) {
        final String path = file.getRelativePath();
        if (path.startsWith(this.val$packagePrefix)) {
            final String filename = path.substring(path.indexOf(this.val$packagePrefix) + this.val$packagePrefix.length());
            return !Utils.isEmpty(filename) && this.val$nameFilter.apply(filename.substring(1));
        }
        return false;
    }
    
    @Override
    public /* bridge */ boolean apply(final Object o) {
        return this.apply((File)o);
    }
}