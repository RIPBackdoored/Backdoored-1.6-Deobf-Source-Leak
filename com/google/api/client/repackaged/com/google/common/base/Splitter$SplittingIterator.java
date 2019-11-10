package com.google.api.client.repackaged.com.google.common.base;

private abstract static class SplittingIterator extends AbstractIterator<String>
{
    final CharSequence toSplit;
    final CharMatcher trimmer;
    final boolean omitEmptyStrings;
    int offset;
    int limit;
    
    abstract int separatorStart(final int p0);
    
    abstract int separatorEnd(final int p0);
    
    protected SplittingIterator(final Splitter splitter, final CharSequence toSplit) {
        super();
        this.offset = 0;
        this.trimmer = Splitter.access$200(splitter);
        this.omitEmptyStrings = Splitter.access$300(splitter);
        this.limit = Splitter.access$400(splitter);
        this.toSplit = toSplit;
    }
    
    @Override
    protected String computeNext() {
        int nextStart = this.offset;
        while (this.offset != -1) {
            int start = nextStart;
            final int separatorPosition = this.separatorStart(this.offset);
            int end;
            if (separatorPosition == -1) {
                end = this.toSplit.length();
                this.offset = -1;
            }
            else {
                end = separatorPosition;
                this.offset = this.separatorEnd(separatorPosition);
            }
            if (this.offset == nextStart) {
                ++this.offset;
                if (this.offset < this.toSplit.length()) {
                    continue;
                }
                this.offset = -1;
            }
            else {
                while (start < end && this.trimmer.matches(this.toSplit.charAt(start))) {
                    ++start;
                }
                while (end > start && this.trimmer.matches(this.toSplit.charAt(end - 1))) {
                    --end;
                }
                if (!this.omitEmptyStrings || start != end) {
                    if (this.limit == 1) {
                        end = this.toSplit.length();
                        this.offset = -1;
                        while (end > start && this.trimmer.matches(this.toSplit.charAt(end - 1))) {
                            --end;
                        }
                    }
                    else {
                        --this.limit;
                    }
                    return this.toSplit.subSequence(start, end).toString();
                }
                nextStart = this.offset;
            }
        }
        return this.endOfData();
    }
    
    @Override
    protected /* bridge */ Object computeNext() {
        return this.computeNext();
    }
}
