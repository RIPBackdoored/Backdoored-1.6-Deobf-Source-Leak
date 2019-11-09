package com.fasterxml.jackson.core.format;

import com.fasterxml.jackson.core.*;
import java.util.*;
import java.io.*;

public class DataFormatDetector
{
    public static final int DEFAULT_MAX_INPUT_LOOKAHEAD = 64;
    protected final JsonFactory[] _detectors;
    protected final MatchStrength _optimalMatch;
    protected final MatchStrength _minimalMatch;
    protected final int _maxInputLookahead;
    
    public DataFormatDetector(final JsonFactory... detectors) {
        this(detectors, MatchStrength.SOLID_MATCH, MatchStrength.WEAK_MATCH, 64);
    }
    
    public DataFormatDetector(final Collection<JsonFactory> detectors) {
        this((JsonFactory[])detectors.toArray(new JsonFactory[detectors.size()]));
    }
    
    public DataFormatDetector withOptimalMatch(final MatchStrength optMatch) {
        if (optMatch == this._optimalMatch) {
            return this;
        }
        return new DataFormatDetector(this._detectors, optMatch, this._minimalMatch, this._maxInputLookahead);
    }
    
    public DataFormatDetector withMinimalMatch(final MatchStrength minMatch) {
        if (minMatch == this._minimalMatch) {
            return this;
        }
        return new DataFormatDetector(this._detectors, this._optimalMatch, minMatch, this._maxInputLookahead);
    }
    
    public DataFormatDetector withMaxInputLookahead(final int lookaheadBytes) {
        if (lookaheadBytes == this._maxInputLookahead) {
            return this;
        }
        return new DataFormatDetector(this._detectors, this._optimalMatch, this._minimalMatch, lookaheadBytes);
    }
    
    private DataFormatDetector(final JsonFactory[] detectors, final MatchStrength optMatch, final MatchStrength minMatch, final int maxInputLookahead) {
        super();
        this._detectors = detectors;
        this._optimalMatch = optMatch;
        this._minimalMatch = minMatch;
        this._maxInputLookahead = maxInputLookahead;
    }
    
    public DataFormatMatcher findFormat(final InputStream in) throws IOException {
        return this._findFormat(new InputAccessor.Std(in, new byte[this._maxInputLookahead]));
    }
    
    public DataFormatMatcher findFormat(final byte[] fullInputData) throws IOException {
        return this._findFormat(new InputAccessor.Std(fullInputData));
    }
    
    public DataFormatMatcher findFormat(final byte[] fullInputData, final int offset, final int len) throws IOException {
        return this._findFormat(new InputAccessor.Std(fullInputData, offset, len));
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        final int len = this._detectors.length;
        if (len > 0) {
            sb.append(this._detectors[0].getFormatName());
            for (int i = 1; i < len; ++i) {
                sb.append(", ");
                sb.append(this._detectors[i].getFormatName());
            }
        }
        sb.append(']');
        return sb.toString();
    }
    
    private DataFormatMatcher _findFormat(final InputAccessor.Std acc) throws IOException {
        JsonFactory bestMatch = null;
        MatchStrength bestMatchStrength = null;
        for (final JsonFactory f : this._detectors) {
            acc.reset();
            final MatchStrength strength = f.hasFormat(acc);
            if (strength != null) {
                if (strength.ordinal() >= this._minimalMatch.ordinal()) {
                    if (bestMatch == null || bestMatchStrength.ordinal() < strength.ordinal()) {
                        bestMatch = f;
                        bestMatchStrength = strength;
                        if (strength.ordinal() >= this._optimalMatch.ordinal()) {
                            break;
                        }
                    }
                }
            }
        }
        return acc.createMatcher(bestMatch, bestMatchStrength);
    }
}
