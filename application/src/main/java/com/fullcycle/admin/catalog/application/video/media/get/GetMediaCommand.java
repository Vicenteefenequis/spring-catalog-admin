package com.fullcycle.admin.catalog.application.video.media.get;

public record GetMediaCommand(
        String videoId,
        String mediaType
) {
    public static GetMediaCommand with(String videoId, String mediaType) {
        return new GetMediaCommand(videoId, mediaType);
    }
}
