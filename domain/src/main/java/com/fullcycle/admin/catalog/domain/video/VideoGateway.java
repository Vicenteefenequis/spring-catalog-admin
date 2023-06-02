package com.fullcycle.admin.catalog.domain.video;

import com.fullcycle.admin.catalog.domain.pagination.Pagination;

import java.util.Optional;

public interface VideoGateway {
    Video create(Video aVideo);
    void deleteById(VideoID anId);
    Optional<Video> findById(VideoID anId);
    Video update(Video aVideo);
    Pagination<VideoPreview> findAll(VideoSearchQuery aQuery);
}
