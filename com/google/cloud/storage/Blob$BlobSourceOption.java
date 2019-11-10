package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import java.security.*;
import com.google.common.io.*;

public static class BlobSourceOption extends Option
{
    private static final long serialVersionUID = 214616862061934846L;
    
    private BlobSourceOption(final StorageRpc.Option rpcOption) {
        super(rpcOption, null);
    }
    
    private BlobSourceOption(final StorageRpc.Option rpcOption, final Object value) {
        super(rpcOption, value);
    }
    
    private Storage.BlobSourceOption toSourceOptions(final BlobInfo blobInfo) {
        switch (this.getRpcOption()) {
            case IF_GENERATION_MATCH: {
                return Storage.BlobSourceOption.generationMatch(blobInfo.getGeneration());
            }
            case IF_GENERATION_NOT_MATCH: {
                return Storage.BlobSourceOption.generationNotMatch(blobInfo.getGeneration());
            }
            case IF_METAGENERATION_MATCH: {
                return Storage.BlobSourceOption.metagenerationMatch(blobInfo.getMetageneration());
            }
            case IF_METAGENERATION_NOT_MATCH: {
                return Storage.BlobSourceOption.metagenerationNotMatch(blobInfo.getMetageneration());
            }
            case CUSTOMER_SUPPLIED_KEY: {
                return Storage.BlobSourceOption.decryptionKey((String)this.getValue());
            }
            case USER_PROJECT: {
                return Storage.BlobSourceOption.userProject((String)this.getValue());
            }
            default: {
                throw new AssertionError((Object)"Unexpected enum value");
            }
        }
    }
    
    private Storage.BlobGetOption toGetOption(final BlobInfo blobInfo) {
        switch (this.getRpcOption()) {
            case IF_GENERATION_MATCH: {
                return Storage.BlobGetOption.generationMatch(blobInfo.getGeneration());
            }
            case IF_GENERATION_NOT_MATCH: {
                return Storage.BlobGetOption.generationNotMatch(blobInfo.getGeneration());
            }
            case IF_METAGENERATION_MATCH: {
                return Storage.BlobGetOption.metagenerationMatch(blobInfo.getMetageneration());
            }
            case IF_METAGENERATION_NOT_MATCH: {
                return Storage.BlobGetOption.metagenerationNotMatch(blobInfo.getMetageneration());
            }
            case USER_PROJECT: {
                return Storage.BlobGetOption.userProject((String)this.getValue());
            }
            case CUSTOMER_SUPPLIED_KEY: {
                return Storage.BlobGetOption.decryptionKey((String)this.getValue());
            }
            default: {
                throw new AssertionError((Object)"Unexpected enum value");
            }
        }
    }
    
    public static BlobSourceOption generationMatch() {
        return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_MATCH);
    }
    
    public static BlobSourceOption generationNotMatch() {
        return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH);
    }
    
    public static BlobSourceOption metagenerationMatch() {
        return new BlobSourceOption(StorageRpc.Option.IF_METAGENERATION_MATCH);
    }
    
    public static BlobSourceOption metagenerationNotMatch() {
        return new BlobSourceOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH);
    }
    
    public static BlobSourceOption decryptionKey(final Key key) {
        final String base64Key = BaseEncoding.base64().encode(key.getEncoded());
        return new BlobSourceOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, base64Key);
    }
    
    public static BlobSourceOption decryptionKey(final String key) {
        return new BlobSourceOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, key);
    }
    
    public static BlobSourceOption userProject(final String userProject) {
        return new BlobSourceOption(StorageRpc.Option.USER_PROJECT, userProject);
    }
    
    static Storage.BlobSourceOption[] toSourceOptions(final BlobInfo blobInfo, final BlobSourceOption... options) {
        final Storage.BlobSourceOption[] convertedOptions = new Storage.BlobSourceOption[options.length];
        int index = 0;
        for (final BlobSourceOption option : options) {
            convertedOptions[index++] = option.toSourceOptions(blobInfo);
        }
        return convertedOptions;
    }
    
    static Storage.BlobGetOption[] toGetOptions(final BlobInfo blobInfo, final BlobSourceOption... options) {
        final Storage.BlobGetOption[] convertedOptions = new Storage.BlobGetOption[options.length];
        int index = 0;
        for (final BlobSourceOption option : options) {
            convertedOptions[index++] = option.toGetOption(blobInfo);
        }
        return convertedOptions;
    }
}
