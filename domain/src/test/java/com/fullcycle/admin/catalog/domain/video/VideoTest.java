package com.fullcycle.admin.catalog.domain.video;

import com.fullcycle.admin.catalog.domain.UnitTest;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberID;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.genre.GenreID;
import com.fullcycle.admin.catalog.domain.utils.InstantUtils;
import com.fullcycle.admin.catalog.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.Set;

public class VideoTest extends UnitTest {

    @Test
    public void givenAValidParams_whenCallsNewVideo_shouldInstantiate() {
        //given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = "A certificação de metodologias que nos auxiliam a lidar com a expansão dos mercados mundiais desafia a capacidade de equalização dos métodos utilizados na avaliação de resultados." +
                "Assim mesmo, o desafiador cenário globalizado exige a precisão e a definição das novas proposições.";

        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        //when
        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedRating,
                expectedOpened,
                expectedPublished,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        //then

        Assertions.assertNotNull(actualVideo);
        Assertions.assertNotNull(actualVideo.getId());
        Assertions.assertNotNull(actualVideo.getCreatedAt());
        Assertions.assertNotNull(actualVideo.getUpdatedAt());
        Assertions.assertEquals(actualVideo.getCreatedAt(), actualVideo.getUpdatedAt());
        Assertions.assertEquals(expectedTitle, actualVideo.getTitle());
        Assertions.assertEquals(expectedDescription, actualVideo.getDescription());
        Assertions.assertEquals(expectedLaunchedAt, actualVideo.getLaunchedAt());
        Assertions.assertEquals(expectedDuration, actualVideo.getDuration());
        Assertions.assertEquals(expectedOpened, actualVideo.getOpened());
        Assertions.assertEquals(expectedPublished, actualVideo.getPublished());
        Assertions.assertEquals(expectedRating, actualVideo.getRating());
        Assertions.assertEquals(expectedCategories, actualVideo.getCategories());
        Assertions.assertEquals(expectedGenres, actualVideo.getGenres());
        Assertions.assertEquals(expectedMembers, actualVideo.getCastMembers());
        Assertions.assertTrue(actualVideo.getVideo().isEmpty());
        Assertions.assertTrue(actualVideo.getTrailer().isEmpty());
        Assertions.assertTrue(actualVideo.getBanner().isEmpty());
        Assertions.assertTrue(actualVideo.getThumbnail().isEmpty());
        Assertions.assertTrue(actualVideo.getThumbnail().isEmpty());
        Assertions.assertTrue(actualVideo.getDomainEvents().isEmpty());


        Assertions.assertDoesNotThrow(() -> actualVideo.validate(new ThrowsValidationHandler()));
    }

    @Test
    public void givenValidVideo_whenCallsUpdate_shouldReturnUpdated() {
        //given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = "A certificação de metodologias que nos auxiliam a lidar com a expansão dos mercados mundiais desafia a capacidade de equalização dos métodos utilizados na avaliação de resultados." +
                "Assim mesmo, o desafiador cenário globalizado exige a precisão e a definição das novas proposições.";

        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedEvent = new VideoMediaCreated("ID", "file");
        final var expectedEventCount = 1;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var aVideo = Video.newVideo(
                "Test title",
                "Lalala description",
                Year.of(1888),
                0.0,
                Rating.AGE_10,
                false,
                false,
                Set.of(),
                Set.of(),
                Set.of()
        );

        aVideo.registerEvent(expectedEvent);
        //when

        final var actualVideo = Video.with(aVideo).update(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedRating,
                expectedOpened,
                expectedPublished,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );


        //then

        Assertions.assertNotNull(actualVideo);
        Assertions.assertNotNull(actualVideo.getId());
        Assertions.assertEquals(aVideo.getCreatedAt(), actualVideo.getCreatedAt());
        Assertions.assertTrue(aVideo.getUpdatedAt().isBefore(actualVideo.getUpdatedAt()));
        Assertions.assertEquals(expectedTitle, actualVideo.getTitle());
        Assertions.assertEquals(expectedDescription, actualVideo.getDescription());
        Assertions.assertEquals(expectedLaunchedAt, actualVideo.getLaunchedAt());
        Assertions.assertEquals(expectedDuration, actualVideo.getDuration());
        Assertions.assertEquals(expectedOpened, actualVideo.getOpened());
        Assertions.assertEquals(expectedPublished, actualVideo.getPublished());
        Assertions.assertEquals(expectedRating, actualVideo.getRating());
        Assertions.assertEquals(expectedCategories, actualVideo.getCategories());
        Assertions.assertEquals(expectedGenres, actualVideo.getGenres());
        Assertions.assertEquals(expectedMembers, actualVideo.getCastMembers());
        Assertions.assertTrue(actualVideo.getVideo().isEmpty());
        Assertions.assertTrue(actualVideo.getTrailer().isEmpty());
        Assertions.assertTrue(actualVideo.getBanner().isEmpty());
        Assertions.assertTrue(actualVideo.getThumbnail().isEmpty());
        Assertions.assertTrue(actualVideo.getThumbnailHalf().isEmpty());

        Assertions.assertEquals(expectedEventCount, actualVideo.getDomainEvents().size());
        Assertions.assertEquals(expectedEvent, actualVideo.getDomainEvents().get(0));


        Assertions.assertDoesNotThrow(() -> actualVideo.validate(new ThrowsValidationHandler()));
    }


    @Test
    public void givenValidVideo_whenCallsUpdateVideoMedia_shouldReturnUpdated() {
        //given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = "A certificação de metodologias que nos auxiliam a lidar com a expansão dos mercados mundiais desafia a capacidade de equalização dos métodos utilizados na avaliação de resultados." +
                "Assim mesmo, o desafiador cenário globalizado exige a precisão e a definição das novas proposições.";

        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var aVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedRating,
                expectedOpened,
                expectedPublished,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        //when

        final var aVideoMedia = AudioVideoMedia.with("abs", "Video.mp4", "/123/videos");

        final var expectedDomainEventSize = 1;

        final var actualVideo = Video.with(aVideo).updateVideoMedia(aVideoMedia);


        //then

        Assertions.assertNotNull(actualVideo);
        Assertions.assertNotNull(actualVideo.getId());
        Assertions.assertEquals(aVideo.getCreatedAt(), actualVideo.getCreatedAt());
        Assertions.assertTrue(aVideo.getUpdatedAt().isBefore(actualVideo.getUpdatedAt()));
        Assertions.assertEquals(expectedTitle, actualVideo.getTitle());
        Assertions.assertEquals(expectedDescription, actualVideo.getDescription());
        Assertions.assertEquals(expectedLaunchedAt, actualVideo.getLaunchedAt());
        Assertions.assertEquals(expectedDuration, actualVideo.getDuration());
        Assertions.assertEquals(expectedOpened, actualVideo.getOpened());
        Assertions.assertEquals(expectedPublished, actualVideo.getPublished());
        Assertions.assertEquals(expectedRating, actualVideo.getRating());
        Assertions.assertEquals(expectedCategories, actualVideo.getCategories());
        Assertions.assertEquals(expectedGenres, actualVideo.getGenres());
        Assertions.assertEquals(expectedMembers, actualVideo.getCastMembers());
        Assertions.assertEquals(aVideoMedia, actualVideo.getVideo().get());
        Assertions.assertTrue(actualVideo.getTrailer().isEmpty());
        Assertions.assertTrue(actualVideo.getBanner().isEmpty());
        Assertions.assertTrue(actualVideo.getThumbnail().isEmpty());
        Assertions.assertTrue(actualVideo.getThumbnailHalf().isEmpty());


        Assertions.assertEquals(expectedDomainEventSize, actualVideo.getDomainEvents().size());

        final var actualEvent = (VideoMediaCreated) actualVideo.getDomainEvents().get(0);
        Assertions.assertEquals(aVideo.getId().getValue(), actualEvent.resourceId());
        Assertions.assertEquals(aVideoMedia.rawLocation(), actualEvent.filePath());
        Assertions.assertNotNull(actualEvent.occurredOn());

        Assertions.assertDoesNotThrow(() -> actualVideo.validate(new ThrowsValidationHandler()));
    }


    @Test
    public void givenValidVideo_whenCallsUpdateTrailerMedia_shouldReturnUpdated() {
        //given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = "A certificação de metodologias que nos auxiliam a lidar com a expansão dos mercados mundiais desafia a capacidade de equalização dos métodos utilizados na avaliação de resultados." +
                "Assim mesmo, o desafiador cenário globalizado exige a precisão e a definição das novas proposições.";

        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var aVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedRating,
                expectedOpened,
                expectedPublished,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        //when

        final var aTrailerMedia = AudioVideoMedia.with("abs", "Trailer.mp4", "/123/videos");

        final var actualVideo = Video.with(aVideo).updateTrailerMedia(aTrailerMedia);

        //then

        Assertions.assertNotNull(actualVideo);
        Assertions.assertNotNull(actualVideo.getId());
        Assertions.assertEquals(aVideo.getCreatedAt(), actualVideo.getCreatedAt());
        Assertions.assertTrue(aVideo.getUpdatedAt().isBefore(actualVideo.getUpdatedAt()));
        Assertions.assertEquals(expectedTitle, actualVideo.getTitle());
        Assertions.assertEquals(expectedDescription, actualVideo.getDescription());
        Assertions.assertEquals(expectedLaunchedAt, actualVideo.getLaunchedAt());
        Assertions.assertEquals(expectedDuration, actualVideo.getDuration());
        Assertions.assertEquals(expectedOpened, actualVideo.getOpened());
        Assertions.assertEquals(expectedPublished, actualVideo.getPublished());
        Assertions.assertEquals(expectedRating, actualVideo.getRating());
        Assertions.assertEquals(expectedCategories, actualVideo.getCategories());
        Assertions.assertEquals(expectedGenres, actualVideo.getGenres());
        Assertions.assertEquals(expectedMembers, actualVideo.getCastMembers());
        Assertions.assertTrue(actualVideo.getVideo().isEmpty());
        Assertions.assertEquals(aTrailerMedia, actualVideo.getTrailer().get());
        Assertions.assertTrue(actualVideo.getBanner().isEmpty());
        Assertions.assertTrue(actualVideo.getThumbnail().isEmpty());
        Assertions.assertTrue(actualVideo.getThumbnailHalf().isEmpty());

        final var actualEvent = (VideoMediaCreated) actualVideo.getDomainEvents().get(0);
        Assertions.assertEquals(aVideo.getId().getValue(), actualEvent.resourceId());
        Assertions.assertEquals(aTrailerMedia.rawLocation(), actualEvent.filePath());
        Assertions.assertNotNull(actualEvent.occurredOn());

        Assertions.assertDoesNotThrow(() -> actualVideo.validate(new ThrowsValidationHandler()));
    }


    @Test
    public void givenValidVideo_whenCallsUpdateBannerMedia_shouldReturnUpdated() {
        //given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = "A certificação de metodologias que nos auxiliam a lidar com a expansão dos mercados mundiais desafia a capacidade de equalização dos métodos utilizados na avaliação de resultados." +
                "Assim mesmo, o desafiador cenário globalizado exige a precisão e a definição das novas proposições.";

        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var aVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedRating,
                expectedOpened,
                expectedPublished,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        //when

        final var aBannerMedia = ImageMedia.with("abs", "Trailer.mp4", "/123/videos");
        final var actualVideo = Video.with(aVideo).updateBannerMedia(aBannerMedia);


        //then

        Assertions.assertNotNull(actualVideo);
        Assertions.assertNotNull(actualVideo.getId());
        Assertions.assertEquals(aVideo.getCreatedAt(), actualVideo.getCreatedAt());
        Assertions.assertTrue(aVideo.getUpdatedAt().isBefore(actualVideo.getUpdatedAt()));
        Assertions.assertEquals(expectedTitle, actualVideo.getTitle());
        Assertions.assertEquals(expectedDescription, actualVideo.getDescription());
        Assertions.assertEquals(expectedLaunchedAt, actualVideo.getLaunchedAt());
        Assertions.assertEquals(expectedDuration, actualVideo.getDuration());
        Assertions.assertEquals(expectedOpened, actualVideo.getOpened());
        Assertions.assertEquals(expectedPublished, actualVideo.getPublished());
        Assertions.assertEquals(expectedRating, actualVideo.getRating());
        Assertions.assertEquals(expectedCategories, actualVideo.getCategories());
        Assertions.assertEquals(expectedGenres, actualVideo.getGenres());
        Assertions.assertEquals(expectedMembers, actualVideo.getCastMembers());
        Assertions.assertTrue(actualVideo.getVideo().isEmpty());
        Assertions.assertTrue(actualVideo.getTrailer().isEmpty());
        Assertions.assertEquals(aBannerMedia, actualVideo.getBanner().get());
        Assertions.assertTrue(actualVideo.getTrailer().isEmpty());
        Assertions.assertTrue(actualVideo.getThumbnail().isEmpty());
        Assertions.assertTrue(actualVideo.getThumbnailHalf().isEmpty());


        Assertions.assertDoesNotThrow(() -> actualVideo.validate(new ThrowsValidationHandler()));
    }

    @Test
    public void givenValidVideo_whenCallsUpdateThumbMedia_shouldReturnUpdated() {
        //given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = "A certificação de metodologias que nos auxiliam a lidar com a expansão dos mercados mundiais desafia a capacidade de equalização dos métodos utilizados na avaliação de resultados." +
                "Assim mesmo, o desafiador cenário globalizado exige a precisão e a definição das novas proposições.";

        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var aVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedRating,
                expectedOpened,
                expectedPublished,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        //when

        final var aThumbMedia = ImageMedia.with("abs", "Trailer.mp4", "/123/videos");
        final var actualVideo = Video.with(aVideo).updateThumbnailMedia(aThumbMedia);


        //then

        Assertions.assertNotNull(actualVideo);
        Assertions.assertNotNull(actualVideo.getId());
        Assertions.assertEquals(aVideo.getCreatedAt(), actualVideo.getCreatedAt());
        Assertions.assertTrue(aVideo.getUpdatedAt().isBefore(actualVideo.getUpdatedAt()));
        Assertions.assertEquals(expectedTitle, actualVideo.getTitle());
        Assertions.assertEquals(expectedDescription, actualVideo.getDescription());
        Assertions.assertEquals(expectedLaunchedAt, actualVideo.getLaunchedAt());
        Assertions.assertEquals(expectedDuration, actualVideo.getDuration());
        Assertions.assertEquals(expectedOpened, actualVideo.getOpened());
        Assertions.assertEquals(expectedPublished, actualVideo.getPublished());
        Assertions.assertEquals(expectedRating, actualVideo.getRating());
        Assertions.assertEquals(expectedCategories, actualVideo.getCategories());
        Assertions.assertEquals(expectedGenres, actualVideo.getGenres());
        Assertions.assertEquals(expectedMembers, actualVideo.getCastMembers());
        Assertions.assertTrue(actualVideo.getVideo().isEmpty());
        Assertions.assertTrue(actualVideo.getTrailer().isEmpty());
        Assertions.assertTrue(actualVideo.getBanner().isEmpty());
        Assertions.assertTrue(actualVideo.getThumbnailHalf().isEmpty());
        Assertions.assertEquals(aThumbMedia, actualVideo.getThumbnail().get());


        Assertions.assertDoesNotThrow(() -> actualVideo.validate(new ThrowsValidationHandler()));
    }

    @Test
    public void givenValidVideo_whenCallsUpdateThumbHalfMedia_shouldReturnUpdated() {
        //given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = "A certificação de metodologias que nos auxiliam a lidar com a expansão dos mercados mundiais desafia a capacidade de equalização dos métodos utilizados na avaliação de resultados." +
                "Assim mesmo, o desafiador cenário globalizado exige a precisão e a definição das novas proposições.";

        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var aVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedRating,
                expectedOpened,
                expectedPublished,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        //when

        final var aThumbHalfMedia = ImageMedia.with("abs", "Trailer.mp4", "/123/videos");
        final var actualVideo = Video.with(aVideo).updateThumbnailHalfMedia(aThumbHalfMedia);


        //then

        Assertions.assertNotNull(actualVideo);
        Assertions.assertNotNull(actualVideo.getId());
        Assertions.assertEquals(aVideo.getCreatedAt(), actualVideo.getCreatedAt());
        Assertions.assertTrue(aVideo.getUpdatedAt().isBefore(actualVideo.getUpdatedAt()));
        Assertions.assertEquals(expectedTitle, actualVideo.getTitle());
        Assertions.assertEquals(expectedDescription, actualVideo.getDescription());
        Assertions.assertEquals(expectedLaunchedAt, actualVideo.getLaunchedAt());
        Assertions.assertEquals(expectedDuration, actualVideo.getDuration());
        Assertions.assertEquals(expectedOpened, actualVideo.getOpened());
        Assertions.assertEquals(expectedPublished, actualVideo.getPublished());
        Assertions.assertEquals(expectedRating, actualVideo.getRating());
        Assertions.assertEquals(expectedCategories, actualVideo.getCategories());
        Assertions.assertEquals(expectedGenres, actualVideo.getGenres());
        Assertions.assertEquals(expectedMembers, actualVideo.getCastMembers());
        Assertions.assertTrue(actualVideo.getVideo().isEmpty());
        Assertions.assertTrue(actualVideo.getTrailer().isEmpty());
        Assertions.assertTrue(actualVideo.getBanner().isEmpty());
        Assertions.assertTrue(actualVideo.getThumbnail().isEmpty());
        Assertions.assertEquals(aThumbHalfMedia, actualVideo.getThumbnailHalf().get());


        Assertions.assertDoesNotThrow(() -> actualVideo.validate(new ThrowsValidationHandler()));
    }


    @Test
    public void givenValidVideo_whenCallsWith_shouldCreatedWithoutEvents() {
        //given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = "A certificação de metodologias que nos auxiliam a lidar com a expansão dos mercados mundiais desafia a capacidade de equalização dos métodos utilizados na avaliação de resultados." +
                "Assim mesmo, o desafiador cenário globalizado exige a precisão e a definição das novas proposições.";

        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        //when

        final var actualVideo = Video.with(
                VideoID.unique(),
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedRating,
                expectedOpened,
                expectedPublished,
                InstantUtils.now(),
                InstantUtils.now(),
                null,
                null,
                null,
                null,
                null,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );


        //then
        Assertions.assertNotNull(actualVideo.getDomainEvents());
    }
}
