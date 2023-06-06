package com.fullcycle.admin.catalog.application.video.update;

import com.fullcycle.admin.catalog.domain.Identifier;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberGateway;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberID;
import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.exceptions.DomainException;
import com.fullcycle.admin.catalog.domain.exceptions.InternalErrorException;
import com.fullcycle.admin.catalog.domain.exceptions.NotFoundException;
import com.fullcycle.admin.catalog.domain.exceptions.NotificationException;
import com.fullcycle.admin.catalog.domain.genre.GenreGateway;
import com.fullcycle.admin.catalog.domain.genre.GenreID;
import com.fullcycle.admin.catalog.domain.validation.Error;
import com.fullcycle.admin.catalog.domain.validation.ValidationHandler;
import com.fullcycle.admin.catalog.domain.validation.handler.Notification;
import com.fullcycle.admin.catalog.domain.video.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.fullcycle.admin.catalog.domain.video.VideoMediaType.*;

public class DefaultUpdateVideoUseCase extends UpdateVideoUseCase {

    private final VideoGateway videoGateway;
    private final CategoryGateway categoryGateway;
    private final CastMemberGateway castMemberGateway;
    private final GenreGateway genreGateway;
    private final MediaResourceGateway mediaResourceGateway;

    public DefaultUpdateVideoUseCase(
            final VideoGateway videoGateway,
            final CategoryGateway categoryGateway,
            final CastMemberGateway castMemberGateway,
            final GenreGateway genreGateway,
            final MediaResourceGateway mediaResourceGateway
    ) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
        this.mediaResourceGateway = Objects.requireNonNull(mediaResourceGateway);
    }

    @Override
    public UpdateVideoOutput execute(final UpdateVideoCommand aCommand) {
        final var aVideoID = VideoID.from(aCommand.id());
        final var aRating = Rating.of(aCommand.rating()).orElse(null);
        final var aLaunchedYear = aCommand.launchedAt() != null ? Year.of(aCommand.launchedAt()) : null;
        final var categories = toIdentifier(aCommand.categories(), CategoryID::from);
        final var genres = toIdentifier(aCommand.genres(), GenreID::from);
        final var members = toIdentifier(aCommand.members(), CastMemberID::from);

        final var aVideo = this.videoGateway.findById(aVideoID).orElseThrow(notFoundException(aVideoID));

        final var notification = Notification.create();
        notification.append(validateCategories(categories));
        notification.append(validateGenres(genres));
        notification.append(validateMembers(members));

        aVideo.update(
                aCommand.title(),
                aCommand.description(),
                aLaunchedYear,
                aCommand.duration(),
                aRating,
                aCommand.opened(),
                aCommand.published(),
                categories,
                genres,
                members
        );

        aVideo.validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Could not update Aggregate Video", notification);
        }

        return UpdateVideoOutput.from(update(aCommand, aVideo));
    }

    private Video update(UpdateVideoCommand aCommand, Video aVideo) {
        final var anId = aVideo.getId();

        try {
            final var aVideoMedia = aCommand.getVideo()
                    .map(it -> this.mediaResourceGateway.storeAudioVideo(anId, VideoResource.with(it, VIDEO)))
                    .orElse(null);

            final var aTrailerMedia = aCommand.getTrailer()
                    .map(it -> this.mediaResourceGateway.storeAudioVideo(anId, VideoResource.with(it, TRAILER)))
                    .orElse(null);

            final var aBannerMedia = aCommand.getBanner()
                    .map(it -> this.mediaResourceGateway.storeImage(anId, VideoResource.with(it, BANNER)))
                    .orElse(null);

            final var aThumbnail = aCommand.getThumbnail()
                    .map(it -> this.mediaResourceGateway.storeImage(anId, VideoResource.with(it, THUMBNAIL)))
                    .orElse(null);

            final var aThumbnailHalf = aCommand.getThumbnailHalf()
                    .map(it -> this.mediaResourceGateway.storeImage(anId, VideoResource.with(it, THUMBNAIL_HALF)))
                    .orElse(null);

            return this.videoGateway.update(aVideo
                    .setVideo(aVideoMedia)
                    .setTrailer(aTrailerMedia)
                    .setBanner(aBannerMedia)
                    .setThumbnail(aThumbnail)
                    .setThumbnailHalf(aThumbnailHalf)
            );
        } catch (final Throwable t) {
            throw InternalErrorException.with("An error on create video was observed [videoId:%s]".formatted(anId.getValue()),
                    t
            );
        }
    }

    private Supplier<DomainException> notFoundException(VideoID aVideoID) {
        return () -> NotFoundException.with(Video.class, aVideoID);
    }

    private ValidationHandler validateCategories(final Set<CategoryID> ids) {
        return validateAggregate("categories", ids, categoryGateway::existsById);
    }


    private ValidationHandler validateGenres(final Set<GenreID> ids) {
        return validateAggregate("genres", ids, genreGateway::existsById);
    }

    private ValidationHandler validateMembers(final Set<CastMemberID> ids) {
        return validateAggregate("cast members", ids, castMemberGateway::existsById);
    }


    private <T extends Identifier> ValidationHandler validateAggregate(
            final String aggregate,
            final Set<T> ids,
            final Function<Iterable<T>, List<T>> existsByIds
    ) {
        final var notification = Notification.create();

        if (ids == null || ids.isEmpty()) {
            return notification;
        }

        final var retrievedIds = existsByIds.apply(ids);

        if (ids.size() != retrievedIds.size()) {
            final var missingIds = new ArrayList<>(ids);
            missingIds.removeAll(retrievedIds);

            final var missingIdsMessage = missingIds.stream().map(Identifier::getValue).collect(Collectors.joining(", "));

            notification.append(new Error("Some %s could not be found: %s".formatted(aggregate, missingIdsMessage)));
        }

        return notification;
    }

    private <T> Set<T> toIdentifier(final Set<String> ids, final Function<String, T> mapper) {
        return ids.stream()
                .map(mapper)
                .collect(Collectors.toSet());
    }
}
