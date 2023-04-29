package com.fullcycle.admin.catalog.domain.video;

import com.fullcycle.admin.catalog.domain.AggregateRoot;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberID;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.genre.GenreID;
import com.fullcycle.admin.catalog.domain.utils.InstantUtils;
import com.fullcycle.admin.catalog.domain.validation.ValidationHandler;

import java.time.Instant;
import java.time.Year;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Video extends AggregateRoot<VideoID> {


    private String title;
    private String description;
    private Year launchedAt;
    private double duration;
    private Rating rating;

    private boolean opened;
    private boolean published;

    private Instant createdAt;
    private Instant updatedAt;

    private ImageMedia banner;
    private ImageMedia thumbnail;
    private ImageMedia thumbnailHalf;

    private AudioVideoMedia trailer;
    private AudioVideoMedia video;

    private Set<CategoryID> categories;
    private Set<GenreID> genres;
    private Set<CastMemberID> castMembers;


    protected Video(
            final VideoID anId,
            final String aTitle,
            final String aDescription,
            final Year aLaunchedAt,
            final double aDuration,
            final Rating aRating,
            final boolean wasOpened,
            final boolean wasPublished,
            final Instant aCreatedAt,
            final Instant anUpdatedAt,
            final ImageMedia aBanner,
            final ImageMedia aThumbnail,
            final ImageMedia aThumbnailHalf,
            final AudioVideoMedia aTrailer,
            final AudioVideoMedia aVideo,
            final Set<CategoryID> categories,
            final Set<GenreID> genres,
            final Set<CastMemberID> members
    ) {
        super(anId);
        this.title = aTitle;
        this.description = aDescription;
        this.launchedAt = aLaunchedAt;
        this.duration = aDuration;
        this.rating = aRating;
        this.opened = wasOpened;
        this.published = wasPublished;
        this.createdAt = aCreatedAt;
        this.updatedAt = anUpdatedAt;
        this.banner = aBanner;
        this.thumbnail = aThumbnail;
        this.thumbnailHalf = aThumbnailHalf;
        this.trailer = aTrailer;
        this.video = aVideo;
        this.categories = categories;
        this.genres = genres;
        this.castMembers = members;
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

    public String getTitle() {
        return title;
    }

    public Video setTitle(final String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Video setDescription(final String description) {
        this.description = description;
        return this;
    }

    public Year getLaunchedAt() {
        return launchedAt;
    }

    public Video setLaunchedAt(final Year launchedAt) {
        this.launchedAt = launchedAt;
        return this;
    }

    public double getDuration() {
        return duration;
    }

    public Video setDuration(final double duration) {
        this.duration = duration;
        return this;
    }

    public Rating getRating() {
        return rating;
    }

    public Video setRating(final Rating rating) {
        this.rating = rating;
        return this;
    }

    public boolean getOpened() {
        return opened;
    }

    public Video setOpened(final boolean opened) {
        this.opened = opened;
        return this;
    }

    public boolean getPublished() {
        return published;
    }

    public Video setPublished(final boolean published) {
        this.published = published;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Video setCreatedAt(final Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Video setUpdatedAt(final Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Optional<ImageMedia> getBanner() {
        return Optional.ofNullable(banner);
    }

    public Video setBanner(final ImageMedia banner) {
        this.banner = banner;
        return this;
    }

    public Optional<ImageMedia> getThumbnail() {
        return Optional.ofNullable(thumbnail);
    }

    public Video setThumbnail(final ImageMedia thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public Optional<ImageMedia> getThumbnailHalf() {
        return Optional.ofNullable(thumbnailHalf);
    }

    public Video setThumbnailHalf(final ImageMedia thumbnailHalf) {
        this.thumbnailHalf = thumbnailHalf;
        return this;
    }

    public Optional<AudioVideoMedia> getTrailer() {
        return Optional.ofNullable(trailer);
    }

    public Video setTrailer(final AudioVideoMedia trailer) {
        this.trailer = trailer;
        return this;
    }

    public Optional<AudioVideoMedia> getVideo() {
        return Optional.ofNullable(video);
    }

    public Video setVideo(final AudioVideoMedia video) {
        this.video = video;
        return this;
    }

    public Set<CategoryID> getCategories() {
        return categories != null ? Collections.unmodifiableSet(categories) : Collections.emptySet();
    }

    public Video setCategories(final Set<CategoryID> categories) {
        this.categories = categories != null ? new HashSet<>(categories) : Collections.emptySet();
        return this;
    }

    public Set<GenreID> getGenres() {
        return genres != null ? Collections.unmodifiableSet(genres) : Collections.emptySet();
    }

    public Video setGenres(final Set<GenreID> genres) {
        this.genres = genres != null ? new HashSet<>(genres) : Collections.emptySet();
        return this;
    }

    public Set<CastMemberID> getCastMembers() {
        return castMembers != null ? Collections.unmodifiableSet(castMembers) : Collections.emptySet();
    }

    public Video setCastMembers(final Set<CastMemberID> castMembers) {
        this.castMembers = castMembers != null ? new HashSet<>(castMembers) : Collections.emptySet();
        return this;
    }

    public static Video newVideo(
            final String aTitle,
            final String aDescription,
            final Year aLaunchedAt,
            final double aDuration,
            final Rating aRating,
            final boolean wasOpened,
            final boolean wasPublished,
            final Set<CategoryID> categories,
            final Set<GenreID> genres,
            final Set<CastMemberID> members
    ) {
        final var now = InstantUtils.now();
        final var anId = VideoID.unique();
        return new Video(
                anId,
                aTitle,
                aDescription,
                aLaunchedAt,
                aDuration,
                aRating,
                wasOpened,
                wasPublished,
                now,
                now,
                null,
                null,
                null,
                null,
                null,
                categories,
                genres,
                members
        );
    }


    public static Video with(
            final Video aVideo
    ) {

        return new Video(
                aVideo.getId(),
                aVideo.getTitle(),
                aVideo.getDescription(),
                aVideo.getLaunchedAt(),
                aVideo.getDuration(),
                aVideo.getRating(),
                aVideo.getOpened(),
                aVideo.getPublished(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt(),
                aVideo.getBanner().orElse(null),
                aVideo.getThumbnail().orElse(null),
                aVideo.getThumbnailHalf().orElse(null),
                aVideo.getTrailer().orElse(null),
                aVideo.getVideo().orElse(null),
                new HashSet<>(aVideo.getCategories()),
                new HashSet<>(aVideo.getGenres()),
                new HashSet<>(aVideo.getCastMembers())
        );
    }
}
