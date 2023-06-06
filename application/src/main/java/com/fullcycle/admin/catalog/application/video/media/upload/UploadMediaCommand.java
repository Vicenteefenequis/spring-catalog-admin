package com.fullcycle.admin.catalog.application.video.media.upload;

import com.fullcycle.admin.catalog.domain.video.VideoResource;

public record UploadMediaCommand(
        String videoId,
        VideoResource videoResource
) {
    public static UploadMediaCommand with(final String aVideoId, final VideoResource aResource) {
        return new UploadMediaCommand(aVideoId, aResource);
    }
}
