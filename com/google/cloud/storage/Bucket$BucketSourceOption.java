package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;

public static class BucketSourceOption extends Option
{
    private static final long serialVersionUID = 6928872234155522371L;
    
    private BucketSourceOption(final StorageRpc.Option rpcOption) {
        super(rpcOption, null);
    }
    
    private BucketSourceOption(final StorageRpc.Option rpcOption, final Object value) {
        super(rpcOption, value);
    }
    
    private Storage.BucketSourceOption toSourceOption(final BucketInfo bucketInfo) {
        switch (this.getRpcOption()) {
            case IF_METAGENERATION_MATCH: {
                return Storage.BucketSourceOption.metagenerationMatch(bucketInfo.getMetageneration());
            }
            case IF_METAGENERATION_NOT_MATCH: {
                return Storage.BucketSourceOption.metagenerationNotMatch(bucketInfo.getMetageneration());
            }
            default: {
                throw new AssertionError((Object)"Unexpected enum value");
            }
        }
    }
    
    private Storage.BucketGetOption toGetOption(final BucketInfo bucketInfo) {
        switch (this.getRpcOption()) {
            case IF_METAGENERATION_MATCH: {
                return Storage.BucketGetOption.metagenerationMatch(bucketInfo.getMetageneration());
            }
            case IF_METAGENERATION_NOT_MATCH: {
                return Storage.BucketGetOption.metagenerationNotMatch(bucketInfo.getMetageneration());
            }
            default: {
                throw new AssertionError((Object)"Unexpected enum value");
            }
        }
    }
    
    public static BucketSourceOption metagenerationMatch() {
        return new BucketSourceOption(StorageRpc.Option.IF_METAGENERATION_MATCH);
    }
    
    public static BucketSourceOption metagenerationNotMatch() {
        return new BucketSourceOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH);
    }
    
    public static BucketSourceOption userProject(final String userProject) {
        return new BucketSourceOption(StorageRpc.Option.USER_PROJECT, userProject);
    }
    
    static Storage.BucketSourceOption[] toSourceOptions(final BucketInfo bucketInfo, final BucketSourceOption... options) {
        final Storage.BucketSourceOption[] convertedOptions = new Storage.BucketSourceOption[options.length];
        int index = 0;
        for (final BucketSourceOption option : options) {
            convertedOptions[index++] = option.toSourceOption(bucketInfo);
        }
        return convertedOptions;
    }
    
    static Storage.BucketGetOption[] toGetOptions(final BucketInfo bucketInfo, final BucketSourceOption... options) {
        final Storage.BucketGetOption[] convertedOptions = new Storage.BucketGetOption[options.length];
        int index = 0;
        for (final BucketSourceOption option : options) {
            convertedOptions[index++] = option.toGetOption(bucketInfo);
        }
        return convertedOptions;
    }
}
