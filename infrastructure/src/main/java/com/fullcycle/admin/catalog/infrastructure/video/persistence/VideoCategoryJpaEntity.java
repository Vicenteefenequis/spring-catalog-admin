package com.fullcycle.admin.catalog.infrastructure.video.persistence;

import com.fullcycle.admin.catalog.domain.category.CategoryID;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "VideoCategory")
@Table(name = "videos_category")
public class VideoCategoryJpaEntity {
    @EmbeddedId
    private VideoCategoryID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("videoId")
    private VideoJpaEntity video;


    public VideoCategoryJpaEntity() {
    }


    private VideoCategoryJpaEntity(
            final VideoCategoryID id,
            final VideoJpaEntity video
    ) {
        this.id = id;
        this.video = video;
    }

    public static VideoCategoryJpaEntity from(final VideoJpaEntity video, final CategoryID categoryID) {
        return new VideoCategoryJpaEntity(
                VideoCategoryID.from(video.getId(), UUID.fromString(categoryID.getValue())),
                video
        );
    }


    public VideoCategoryID getId() {
        return id;
    }

    public void setId(VideoCategoryID id) {
        this.id = id;
    }

    public VideoJpaEntity getVideo() {
        return video;
    }

    public void setVideo(VideoJpaEntity video) {
        this.video = video;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoCategoryJpaEntity that = (VideoCategoryJpaEntity) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getVideo(), that.getVideo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVideo());
    }
}
