package com.fullcycle.admin.catalog.domain.video;

import com.fullcycle.admin.catalog.domain.resource.Resource;

import java.util.Optional;

public interface MediaResourceGateway {
    AudioVideoMedia storeAudioVideo(VideoID anId, VideoResource aResource);

    ImageMedia storeImage(VideoID anId, VideoResource aResource);

    Optional<Resource> getResource(VideoID anId, VideoMediaType aType);

    void clearResources(VideoID anId);
}
