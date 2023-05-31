package com.fullcycle.admin.catalog.infrastructure.video.persistence;

import com.fullcycle.admin.catalog.domain.video.Rating;
import com.fullcycle.admin.catalog.domain.video.Video;
import com.fullcycle.admin.catalog.domain.video.VideoID;

import javax.persistence.*;
import java.time.Instant;
import java.time.Year;
import java.util.Optional;
import java.util.UUID;

@Table(name = "videos")
@Entity(name = "Video")
public class VideoJpaEntity {
    @Id
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "title", length = 4000)
    private String description;

    @Column(name = "year_launched", nullable = false)
    private int yearLaunched;


    @Column(name = "opened", nullable = false)
    private boolean opened;

    @Column(name = "published", nullable = false)
    private boolean published;

    @Column(name = "rating")
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Column(name = "duration", precision = 2)
    private double duration;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private AudioMediaVideoJpaEntity video;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "trailer_id", referencedColumnName = "id")
    private AudioMediaVideoJpaEntity trailer;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "banner_id", referencedColumnName = "id")
    private ImageMediaJpaEntity banner;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "thumbnail_id", referencedColumnName = "id")
    private ImageMediaJpaEntity thumbnail;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "trailer_half_id", referencedColumnName = "id")
    private ImageMediaJpaEntity thumbnailHalf;


    public VideoJpaEntity() {
    }

    public VideoJpaEntity(
            final UUID id,
            final String title,
            final String description,
            final int yearLaunched,
            final boolean opened,
            final boolean published,
            final Rating rating,
            final double duration,
            final Instant createdAt,
            final Instant updatedAt,
            final AudioMediaVideoJpaEntity video,
            final AudioMediaVideoJpaEntity trailer,
            final ImageMediaJpaEntity banner,
            final ImageMediaJpaEntity thumbnail,
            final ImageMediaJpaEntity thumbnailHalf
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.yearLaunched = yearLaunched;
        this.opened = opened;
        this.published = published;
        this.rating = rating;
        this.duration = duration;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.video = video;
        this.trailer = trailer;
        this.banner = banner;
        this.thumbnail = thumbnail;
        this.thumbnailHalf = thumbnailHalf;
    }

    public Video toAggregate() {
        return Video.with(
                VideoID.from(getId()),
                getTitle(),
                getDescription(),
                Year.of(getYearLaunched()),
                getDuration(),
                getRating(),
                isOpened(),
                isPublished(),
                getCreatedAt(),
                getUpdatedAt(),
                Optional.ofNullable(getBanner()).map(ImageMediaJpaEntity::toDomain).orElse(null),
                Optional.ofNullable(getThumbnail()).map(ImageMediaJpaEntity::toDomain).orElse(null),
                Optional.ofNullable(getThumbnailHalf()).map(ImageMediaJpaEntity::toDomain).orElse(null),
                Optional.ofNullable(getTrailer()).map(AudioMediaVideoJpaEntity::toDomain).orElse(null),
                Optional.ofNullable(getVideo()).map(AudioMediaVideoJpaEntity::toDomain).orElse(null),
                null,
                null,
                null
        );

    }


    public static VideoJpaEntity from(final Video aVideo) {
        return new VideoJpaEntity(
                UUID.fromString(aVideo.getId().getValue()),
                aVideo.getTitle(),
                aVideo.getDescription(),
                aVideo.getLaunchedAt().getValue(),
                aVideo.getOpened(),
                aVideo.getPublished(),
                aVideo.getRating(),
                aVideo.getDuration(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt(),
                aVideo.getVideo().map(AudioMediaVideoJpaEntity::from).orElse(null),
                aVideo.getTrailer().map(AudioMediaVideoJpaEntity::from).orElse(null),
                aVideo.getBanner().map(ImageMediaJpaEntity::from).orElse(null),
                aVideo.getThumbnail().map(ImageMediaJpaEntity::from).orElse(null),
                aVideo.getThumbnailHalf().map(ImageMediaJpaEntity::from).orElse(null)
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYearLaunched() {
        return yearLaunched;
    }

    public void setYearLaunched(int yearLaunched) {
        this.yearLaunched = yearLaunched;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public AudioMediaVideoJpaEntity getVideo() {
        return video;
    }

    public void setVideo(AudioMediaVideoJpaEntity video) {
        this.video = video;
    }

    public AudioMediaVideoJpaEntity getTrailer() {
        return trailer;
    }

    public void setTrailer(AudioMediaVideoJpaEntity trailer) {
        this.trailer = trailer;
    }

    public ImageMediaJpaEntity getBanner() {
        return banner;
    }

    public void setBanner(ImageMediaJpaEntity banner) {
        this.banner = banner;
    }

    public ImageMediaJpaEntity getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageMediaJpaEntity thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ImageMediaJpaEntity getThumbnailHalf() {
        return thumbnailHalf;
    }

    public void setThumbnailHalf(ImageMediaJpaEntity thumbnailHalf) {
        this.thumbnailHalf = thumbnailHalf;
    }
}
