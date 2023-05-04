package com.fullcycle.admin.catalog.application.video.create;

import com.fullcycle.admin.catalog.application.Fixture;
import com.fullcycle.admin.catalog.application.UseCaseTest;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberGateway;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberID;
import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.exceptions.InternalErrorException;
import com.fullcycle.admin.catalog.domain.exceptions.NotificationException;
import com.fullcycle.admin.catalog.domain.genre.GenreGateway;
import com.fullcycle.admin.catalog.domain.genre.GenreID;
import com.fullcycle.admin.catalog.domain.video.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Year;
import java.util.*;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class CreateVideoUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateVideoUseCase useCase;

    @Mock
    private VideoGateway videoGateway;

    @Mock
    private CategoryGateway categoryGateway;

    @Mock
    private CastMemberGateway castMemberGateway;

    @Mock
    private GenreGateway genreGateway;

    @Mock
    private MediaResourceGateway mediaResourceGateway;


    @Override
    protected List<Object> getMocks() {
        return List.of(videoGateway, categoryGateway, castMemberGateway, genreGateway, mediaResourceGateway);
    }


    @Test
    public void givenAValidCommand_whenCallsCreateVideo_shouldReturnVideoId() {
        //given
        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchYear = Year.of(Fixture.year());
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.of(Fixture.Categories.aulas().getId());
        final var expectedGenres = Set.of(Fixture.Genres.tech().getId());
        final var expectedMembers = Set.of(Fixture.CastMembers.wesley().getId(), Fixture.CastMembers.vicente().getId());
        final Resource expectedVideo = Fixture.Videos.resource(Resource.Type.VIDEO);
        final Resource expectedTrailer = Fixture.Videos.resource(Resource.Type.TRAILER);
        final Resource expectedBanner = Fixture.Videos.resource(Resource.Type.BANNER);
        final Resource expectedThumb = Fixture.Videos.resource(Resource.Type.THUMBNAIL);
        final Resource expectedThumbHalf = Fixture.Videos.resource(Resource.Type.THUMBNAIL_HALF);

        final var aCommand = CreateVideoCommand.with(expectedTitle, expectedDescription, expectedLaunchYear.getValue(), expectedDuration, expectedOpened, expectedPublished, expectedRating.getName(), asString(expectedCategories), asString(expectedGenres), asString(expectedMembers), expectedVideo, expectedTrailer, expectedBanner, expectedThumb, expectedThumbHalf);

        when(categoryGateway.existsById(any())).thenReturn(new ArrayList<>(expectedCategories));
        when(castMemberGateway.existsById(any())).thenReturn(new ArrayList<>(expectedMembers));
        when(genreGateway.existsById(any())).thenReturn(new ArrayList<>(expectedGenres));
        mockImageMedia();
        mockAudioVideoMedia();
        when(videoGateway.create(any())).thenAnswer(returnsFirstArg());
        //when

        final var actualResult = useCase.execute(aCommand);
        //then

        Assertions.assertNotNull(actualResult);
        Assertions.assertNotNull(actualResult.id());

        verify(videoGateway).create(argThat(actualVideo -> Objects.equals(expectedTitle, actualVideo.getTitle()) && Objects.equals(expectedDescription, actualVideo.getDescription()) && Objects.equals(expectedLaunchYear, actualVideo.getLaunchedAt()) && Objects.equals(expectedDuration, actualVideo.getDuration()) && Objects.equals(expectedOpened, actualVideo.getOpened()) && Objects.equals(expectedPublished, actualVideo.getPublished()) && Objects.equals(expectedRating, actualVideo.getRating()) && Objects.equals(expectedCategories, actualVideo.getCategories()) && Objects.equals(expectedGenres, actualVideo.getGenres()) && Objects.equals(expectedMembers, actualVideo.getCastMembers()) && Objects.equals(expectedVideo.name(), actualVideo.getVideo().get().name()) && Objects.equals(expectedTrailer.name(), actualVideo.getTrailer().get().name()) && Objects.equals(expectedBanner.name(), actualVideo.getBanner().get().name()) && Objects.equals(expectedThumb.name(), actualVideo.getThumbnail().get().name()) && Objects.equals(expectedThumbHalf.name(), actualVideo.getThumbnailHalf().get().name())));

    }

    @Test
    public void givenAValidCommandWithoutCategories_whenCallsCreateVideo_shouldReturnVideoId() {
        //given
        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchYear = Year.of(Fixture.year());
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.<CategoryID>of();
        final var expectedGenres = Set.of(Fixture.Genres.tech().getId());
        final var expectedMembers = Set.of(Fixture.CastMembers.wesley().getId(), Fixture.CastMembers.vicente().getId());
        final Resource expectedVideo = Fixture.Videos.resource(Resource.Type.VIDEO);
        final Resource expectedTrailer = Fixture.Videos.resource(Resource.Type.TRAILER);
        final Resource expectedBanner = Fixture.Videos.resource(Resource.Type.BANNER);
        final Resource expectedThumb = Fixture.Videos.resource(Resource.Type.THUMBNAIL);
        final Resource expectedThumbHalf = Fixture.Videos.resource(Resource.Type.THUMBNAIL_HALF);

        final var aCommand = CreateVideoCommand.with(expectedTitle, expectedDescription, expectedLaunchYear.getValue(), expectedDuration, expectedOpened, expectedPublished, expectedRating.getName(), asString(expectedCategories), asString(expectedGenres), asString(expectedMembers), expectedVideo, expectedTrailer, expectedBanner, expectedThumb, expectedThumbHalf);


        when(castMemberGateway.existsById(any())).thenReturn(new ArrayList<>(expectedMembers));
        when(genreGateway.existsById(any())).thenReturn(new ArrayList<>(expectedGenres));
        mockImageMedia();
        mockAudioVideoMedia();
        when(videoGateway.create(any())).thenAnswer(returnsFirstArg());
        //when

        final var actualResult = useCase.execute(aCommand);
        //then

        Assertions.assertNotNull(actualResult);
        Assertions.assertNotNull(actualResult.id());

        verify(videoGateway).create(argThat(actualVideo -> Objects.equals(expectedTitle, actualVideo.getTitle()) && Objects.equals(expectedDescription, actualVideo.getDescription()) && Objects.equals(expectedLaunchYear, actualVideo.getLaunchedAt()) && Objects.equals(expectedDuration, actualVideo.getDuration()) && Objects.equals(expectedOpened, actualVideo.getOpened()) && Objects.equals(expectedPublished, actualVideo.getPublished()) && Objects.equals(expectedRating, actualVideo.getRating()) && Objects.equals(expectedCategories, actualVideo.getCategories()) && Objects.equals(expectedGenres, actualVideo.getGenres()) && Objects.equals(expectedMembers, actualVideo.getCastMembers()) && Objects.equals(expectedVideo.name(), actualVideo.getVideo().get().name()) && Objects.equals(expectedTrailer.name(), actualVideo.getTrailer().get().name()) && Objects.equals(expectedBanner.name(), actualVideo.getBanner().get().name()) && Objects.equals(expectedThumb.name(), actualVideo.getThumbnail().get().name()) && Objects.equals(expectedThumbHalf.name(), actualVideo.getThumbnailHalf().get().name())));
    }


    @Test
    public void givenAValidCommandWithoutGenres_whenCallsCreateVideo_shouldReturnVideoId() {
        //given
        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchYear = Year.of(Fixture.year());
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.of(Fixture.Categories.aulas().getId());
        final var expectedGenres = Set.<GenreID>of();
        final var expectedMembers = Set.of(Fixture.CastMembers.wesley().getId(), Fixture.CastMembers.vicente().getId());
        final Resource expectedVideo = Fixture.Videos.resource(Resource.Type.VIDEO);
        final Resource expectedTrailer = Fixture.Videos.resource(Resource.Type.TRAILER);
        final Resource expectedBanner = Fixture.Videos.resource(Resource.Type.BANNER);
        final Resource expectedThumb = Fixture.Videos.resource(Resource.Type.THUMBNAIL);
        final Resource expectedThumbHalf = Fixture.Videos.resource(Resource.Type.THUMBNAIL_HALF);

        final var aCommand = CreateVideoCommand.with(expectedTitle, expectedDescription, expectedLaunchYear.getValue(), expectedDuration, expectedOpened, expectedPublished, expectedRating.getName(), asString(expectedCategories), asString(expectedGenres), asString(expectedMembers), expectedVideo, expectedTrailer, expectedBanner, expectedThumb, expectedThumbHalf);
        when(categoryGateway.existsById(any())).thenReturn(new ArrayList<>(expectedCategories));
        when(castMemberGateway.existsById(any())).thenReturn(new ArrayList<>(expectedMembers));
        mockImageMedia();
        mockAudioVideoMedia();
        when(videoGateway.create(any())).thenAnswer(returnsFirstArg());
        //when

        final var actualResult = useCase.execute(aCommand);
        //then

        Assertions.assertNotNull(actualResult);
        Assertions.assertNotNull(actualResult.id());

        verify(videoGateway).create(argThat(actualVideo -> Objects.equals(expectedTitle, actualVideo.getTitle()) && Objects.equals(expectedDescription, actualVideo.getDescription()) && Objects.equals(expectedLaunchYear, actualVideo.getLaunchedAt()) && Objects.equals(expectedDuration, actualVideo.getDuration()) && Objects.equals(expectedOpened, actualVideo.getOpened()) && Objects.equals(expectedPublished, actualVideo.getPublished()) && Objects.equals(expectedRating, actualVideo.getRating()) && Objects.equals(expectedCategories, actualVideo.getCategories()) && Objects.equals(expectedGenres, actualVideo.getGenres()) && Objects.equals(expectedMembers, actualVideo.getCastMembers()) && Objects.equals(expectedVideo.name(), actualVideo.getVideo().get().name()) && Objects.equals(expectedTrailer.name(), actualVideo.getTrailer().get().name()) && Objects.equals(expectedBanner.name(), actualVideo.getBanner().get().name()) && Objects.equals(expectedThumb.name(), actualVideo.getThumbnail().get().name()) && Objects.equals(expectedThumbHalf.name(), actualVideo.getThumbnailHalf().get().name())));
    }


    @Test
    public void givenAValidCommandWithoutCastMembers_whenCallsCreateVideo_shouldReturnVideoId() {
        //given
        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchYear = Year.of(Fixture.year());
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.of(Fixture.Categories.aulas().getId());
        final var expectedGenres = Set.of(Fixture.Genres.tech().getId());
        final var expectedMembers = Set.<CastMemberID>of();
        final Resource expectedVideo = Fixture.Videos.resource(Resource.Type.VIDEO);
        final Resource expectedTrailer = Fixture.Videos.resource(Resource.Type.TRAILER);
        final Resource expectedBanner = Fixture.Videos.resource(Resource.Type.BANNER);
        final Resource expectedThumb = Fixture.Videos.resource(Resource.Type.THUMBNAIL);
        final Resource expectedThumbHalf = Fixture.Videos.resource(Resource.Type.THUMBNAIL_HALF);

        final var aCommand = CreateVideoCommand.with(expectedTitle, expectedDescription, expectedLaunchYear.getValue(), expectedDuration, expectedOpened, expectedPublished, expectedRating.getName(), asString(expectedCategories), asString(expectedGenres), asString(expectedMembers), expectedVideo, expectedTrailer, expectedBanner, expectedThumb, expectedThumbHalf);

        when(categoryGateway.existsById(any())).thenReturn(new ArrayList<>(expectedCategories));
        when(genreGateway.existsById(any())).thenReturn(new ArrayList<>(expectedGenres));
        mockImageMedia();
        mockAudioVideoMedia();
        when(videoGateway.create(any())).thenAnswer(returnsFirstArg());
        //when

        final var actualResult = useCase.execute(aCommand);
        //then

        Assertions.assertNotNull(actualResult);
        Assertions.assertNotNull(actualResult.id());

        verify(videoGateway).create(argThat(actualVideo -> Objects.equals(expectedTitle, actualVideo.getTitle()) && Objects.equals(expectedDescription, actualVideo.getDescription()) && Objects.equals(expectedLaunchYear, actualVideo.getLaunchedAt()) && Objects.equals(expectedDuration, actualVideo.getDuration()) && Objects.equals(expectedOpened, actualVideo.getOpened()) && Objects.equals(expectedPublished, actualVideo.getPublished()) && Objects.equals(expectedRating, actualVideo.getRating()) && Objects.equals(expectedCategories, actualVideo.getCategories()) && Objects.equals(expectedGenres, actualVideo.getGenres()) && Objects.equals(expectedMembers, actualVideo.getCastMembers()) && Objects.equals(expectedVideo.name(), actualVideo.getVideo().get().name()) && Objects.equals(expectedTrailer.name(), actualVideo.getTrailer().get().name()) && Objects.equals(expectedBanner.name(), actualVideo.getBanner().get().name()) && Objects.equals(expectedThumb.name(), actualVideo.getThumbnail().get().name()) && Objects.equals(expectedThumbHalf.name(), actualVideo.getThumbnailHalf().get().name())));

    }

    @Test
    public void givenAValidCommandWithoutResources_whenCallsCreateVideo_shouldReturnVideoId() {
        //given
        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchYear = Year.of(Fixture.year());
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.of(Fixture.Categories.aulas().getId());
        final var expectedGenres = Set.of(Fixture.Genres.tech().getId());
        final var expectedMembers = Set.of(Fixture.CastMembers.wesley().getId(), Fixture.CastMembers.vicente().getId());
        final Resource expectedVideo = null;
        final Resource expectedTrailer = null;
        final Resource expectedBanner = null;
        final Resource expectedThumb = null;
        final Resource expectedThumbHalf = null;

        final var aCommand = CreateVideoCommand.with(expectedTitle, expectedDescription, expectedLaunchYear.getValue(), expectedDuration, expectedOpened, expectedPublished, expectedRating.getName(), asString(expectedCategories), asString(expectedGenres), asString(expectedMembers), expectedVideo, expectedTrailer, expectedBanner, expectedThumb, expectedThumbHalf);

        when(categoryGateway.existsById(any())).thenReturn(new ArrayList<>(expectedCategories));
        when(castMemberGateway.existsById(any())).thenReturn(new ArrayList<>(expectedMembers));
        when(genreGateway.existsById(any())).thenReturn(new ArrayList<>(expectedGenres));
        when(videoGateway.create(any())).thenAnswer(returnsFirstArg());
        //when

        final var actualResult = useCase.execute(aCommand);
        //then

        Assertions.assertNotNull(actualResult);
        Assertions.assertNotNull(actualResult.id());

        verify(videoGateway).create(argThat(actualVideo -> Objects.equals(expectedTitle, actualVideo.getTitle()) && Objects.equals(expectedDescription, actualVideo.getDescription()) && Objects.equals(expectedLaunchYear, actualVideo.getLaunchedAt()) && Objects.equals(expectedDuration, actualVideo.getDuration()) && Objects.equals(expectedOpened, actualVideo.getOpened()) && Objects.equals(expectedPublished, actualVideo.getPublished()) && Objects.equals(expectedRating, actualVideo.getRating()) && Objects.equals(expectedCategories, actualVideo.getCategories()) && Objects.equals(expectedGenres, actualVideo.getGenres()) && Objects.equals(expectedMembers, actualVideo.getCastMembers()) && actualVideo.getVideo().isEmpty() && actualVideo.getTrailer().isEmpty() && actualVideo.getBanner().isEmpty() && actualVideo.getThumbnail().isEmpty() && actualVideo.getThumbnailHalf().isEmpty()));

    }

    @Test
    public void givenANullTitle_whenCallsCreateVideo_shouldReturnDomainException() {
        //given
        final var expectedErrorMessage = "'title' should not be null";
        final var expectedErrorCount = 1;
        final String expectedTitle = null;
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchYear = Year.of(Fixture.year());
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.<CategoryID>of();
        final var expectedGenres = Set.<GenreID>of();
        final var expectedMembers = Set.<CastMemberID>of();
        final Resource expectedVideo = null;
        final Resource expectedTrailer = null;
        final Resource expectedBanner = null;
        final Resource expectedThumb = null;
        final Resource expectedThumbHalf = null;

        final var aCommand = CreateVideoCommand.with(expectedTitle, expectedDescription, expectedLaunchYear.getValue(), expectedDuration, expectedOpened, expectedPublished, expectedRating.getName(), asString(expectedCategories), asString(expectedGenres), asString(expectedMembers), expectedVideo, expectedTrailer, expectedBanner, expectedThumb, expectedThumbHalf);


        //when

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });
        //then

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(categoryGateway, times(0)).existsById(any());
        verify(castMemberGateway, times(0)).existsById(any());
        verify(genreGateway, times(0)).existsById(any());
        verify(mediaResourceGateway, times(0)).storeAudioVideo(any(), any());
        verify(mediaResourceGateway, times(0)).storeImage(any(), any());
        verify(videoGateway, times(0)).create(any());

    }


    @Test
    public void givenAEmptyTitle_whenCallsCreateVideo_shouldReturnDomainException() {
        //given
        final var expectedErrorMessage = "'title' should not be empty";
        final var expectedErrorCount = 1;
        final var expectedTitle = "";
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchYear = Year.of(Fixture.year());
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.<CategoryID>of();
        final var expectedGenres = Set.<GenreID>of();
        final var expectedMembers = Set.<CastMemberID>of();
        final Resource expectedVideo = null;
        final Resource expectedTrailer = null;
        final Resource expectedBanner = null;
        final Resource expectedThumb = null;
        final Resource expectedThumbHalf = null;

        final var aCommand = CreateVideoCommand.with(expectedTitle, expectedDescription, expectedLaunchYear.getValue(), expectedDuration, expectedOpened, expectedPublished, expectedRating.getName(), asString(expectedCategories), asString(expectedGenres), asString(expectedMembers), expectedVideo, expectedTrailer, expectedBanner, expectedThumb, expectedThumbHalf);


        //when

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });
        //then

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(categoryGateway, times(0)).existsById(any());
        verify(castMemberGateway, times(0)).existsById(any());
        verify(genreGateway, times(0)).existsById(any());
        verify(mediaResourceGateway, times(0)).storeAudioVideo(any(), any());
        verify(mediaResourceGateway, times(0)).storeImage(any(), any());
        verify(videoGateway, times(0)).create(any());

    }


    @Test
    public void givenANullRating_whenCallsCreateVideo_shouldReturnDomainException() {
        //given
        final var expectedErrorMessage = "'rating' should not be null";
        final var expectedErrorCount = 1;
        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchYear = Year.of(Fixture.year());
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final String expectedRating = null;
        final var expectedCategories = Set.<CategoryID>of();
        final var expectedGenres = Set.<GenreID>of();
        final var expectedMembers = Set.<CastMemberID>of();
        final Resource expectedVideo = null;
        final Resource expectedTrailer = null;
        final Resource expectedBanner = null;
        final Resource expectedThumb = null;
        final Resource expectedThumbHalf = null;

        final var aCommand = CreateVideoCommand.with(
                expectedTitle,
                expectedDescription,
                expectedLaunchYear.getValue(),
                expectedDuration, expectedOpened,
                expectedPublished,
                expectedRating,
                asString(expectedCategories),
                asString(expectedGenres),
                asString(expectedMembers),
                expectedVideo,
                expectedTrailer,
                expectedBanner,
                expectedThumb,
                expectedThumbHalf
        );


        //when

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });
        //then

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(categoryGateway, times(0)).existsById(any());
        verify(castMemberGateway, times(0)).existsById(any());
        verify(genreGateway, times(0)).existsById(any());
        verify(mediaResourceGateway, times(0)).storeAudioVideo(any(), any());
        verify(mediaResourceGateway, times(0)).storeImage(any(), any());
        verify(videoGateway, times(0)).create(any());

    }

    @Test
    public void givenAnInvalidRating_whenCallsCreateVideo_shouldReturnDomainException() {
        //given
        final var expectedErrorMessage = "'rating' should not be null";
        final var expectedErrorCount = 1;
        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchYear = Year.of(Fixture.year());
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = "INVALID_RATING";
        final var expectedCategories = Set.<CategoryID>of();
        final var expectedGenres = Set.<GenreID>of();
        final var expectedMembers = Set.<CastMemberID>of();
        final Resource expectedVideo = null;
        final Resource expectedTrailer = null;
        final Resource expectedBanner = null;
        final Resource expectedThumb = null;
        final Resource expectedThumbHalf = null;

        final var aCommand = CreateVideoCommand.with(
                expectedTitle,
                expectedDescription,
                expectedLaunchYear.getValue(),
                expectedDuration, expectedOpened,
                expectedPublished,
                expectedRating,
                asString(expectedCategories),
                asString(expectedGenres),
                asString(expectedMembers),
                expectedVideo,
                expectedTrailer,
                expectedBanner,
                expectedThumb,
                expectedThumbHalf
        );


        //when

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });
        //then

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(categoryGateway, times(0)).existsById(any());
        verify(castMemberGateway, times(0)).existsById(any());
        verify(genreGateway, times(0)).existsById(any());
        verify(mediaResourceGateway, times(0)).storeAudioVideo(any(), any());
        verify(mediaResourceGateway, times(0)).storeImage(any(), any());
        verify(videoGateway, times(0)).create(any());

    }


    @Test
    public void givenANullLaunchYear_whenCallsCreateVideo_shouldReturnDomainException() {
        //given
        final var expectedErrorMessage = "'launchedAt' should not be null";
        final var expectedErrorCount = 1;
        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final Integer expectedLaunchYear = null;
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.<CategoryID>of();
        final var expectedGenres = Set.<GenreID>of();
        final var expectedMembers = Set.<CastMemberID>of();
        final Resource expectedVideo = null;
        final Resource expectedTrailer = null;
        final Resource expectedBanner = null;
        final Resource expectedThumb = null;
        final Resource expectedThumbHalf = null;

        final var aCommand = CreateVideoCommand.with(
                expectedTitle,
                expectedDescription,
                expectedLaunchYear,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating.getName(),
                asString(expectedCategories),
                asString(expectedGenres),
                asString(expectedMembers),
                expectedVideo,
                expectedTrailer,
                expectedBanner,
                expectedThumb,
                expectedThumbHalf
        );


        //when

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });
        //then

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(categoryGateway, times(0)).existsById(any());
        verify(castMemberGateway, times(0)).existsById(any());
        verify(genreGateway, times(0)).existsById(any());
        verify(mediaResourceGateway, times(0)).storeAudioVideo(any(), any());
        verify(mediaResourceGateway, times(0)).storeImage(any(), any());
        verify(videoGateway, times(0)).create(any());

    }


    @Test
    public void givenAValidCommand_whenCallsCreateVideoAndSomeCategoriesDoesNotExists_shouldReturnDomainException() {
        //given
        final var aulaId = Fixture.Categories.aulas().getId();

        final var expectedErrorMessage = "Some categories could not be found: %s".formatted(aulaId.getValue());
        final var expectedErrorCount = 1;
        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchYear = Fixture.year();
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.of(aulaId);
        final var expectedGenres = Set.of(Fixture.Genres.tech().getId());
        final var expectedMembers = Set.of(Fixture.CastMembers.wesley().getId());
        final Resource expectedVideo = null;
        final Resource expectedTrailer = null;
        final Resource expectedBanner = null;
        final Resource expectedThumb = null;
        final Resource expectedThumbHalf = null;

        final var aCommand = CreateVideoCommand.with(
                expectedTitle,
                expectedDescription,
                expectedLaunchYear,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating.getName(),
                asString(expectedCategories),
                asString(expectedGenres),
                asString(expectedMembers),
                expectedVideo,
                expectedTrailer,
                expectedBanner,
                expectedThumb,
                expectedThumbHalf
        );


        when(categoryGateway.existsById(any())).thenReturn(new ArrayList<>());
        when(castMemberGateway.existsById(any())).thenReturn(new ArrayList<>(expectedMembers));
        when(genreGateway.existsById(any())).thenReturn(new ArrayList<>(expectedGenres));

        //when

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });
        //then

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(categoryGateway, times(1)).existsById(any());
        verify(castMemberGateway, times(1)).existsById(any());
        verify(genreGateway, times(1)).existsById(any());
        verify(mediaResourceGateway, times(0)).storeAudioVideo(any(), any());
        verify(mediaResourceGateway, times(0)).storeImage(any(), any());
        verify(videoGateway, times(0)).create(any());

    }


    @Test
    public void givenAValidCommand_whenCallsCreateVideoAndSomeGenresDoesNotExists_shouldReturnDomainException() {
        //given
        final var techId = Fixture.Genres.tech().getId();

        final var expectedErrorMessage = "Some genres could not be found: %s".formatted(techId.getValue());
        final var expectedErrorCount = 1;

        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchYear = Fixture.year();
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.of(Fixture.Categories.aulas().getId());
        final var expectedGenres = Set.of(techId);
        final var expectedMembers = Set.of(Fixture.CastMembers.wesley().getId());
        final Resource expectedVideo = null;
        final Resource expectedTrailer = null;
        final Resource expectedBanner = null;
        final Resource expectedThumb = null;
        final Resource expectedThumbHalf = null;

        final var aCommand = CreateVideoCommand.with(
                expectedTitle,
                expectedDescription,
                expectedLaunchYear,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating.getName(),
                asString(expectedCategories),
                asString(expectedGenres),
                asString(expectedMembers),
                expectedVideo,
                expectedTrailer,
                expectedBanner,
                expectedThumb,
                expectedThumbHalf
        );


        when(categoryGateway.existsById(any())).thenReturn(new ArrayList<>(expectedCategories));
        when(castMemberGateway.existsById(any())).thenReturn(new ArrayList<>(expectedMembers));
        when(genreGateway.existsById(any())).thenReturn(new ArrayList<>());

        //when

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });
        //then

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(categoryGateway, times(1)).existsById(any());
        verify(castMemberGateway, times(1)).existsById(any());
        verify(genreGateway, times(1)).existsById(any());
        verify(mediaResourceGateway, times(0)).storeAudioVideo(any(), any());
        verify(mediaResourceGateway, times(0)).storeImage(any(), any());
        verify(videoGateway, times(0)).create(any());

    }


    @Test
    public void givenAValidCommand_whenCallsCreateVideoAndSomeCastMembersDoesNotExists_shouldReturnDomainException() {
        //given
        final var vicenteID = Fixture.CastMembers.vicente().getId();

        final var expectedErrorMessage = "Some cast members could not be found: %s".formatted(vicenteID.getValue());
        final var expectedErrorCount = 1;

        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchYear = Fixture.year();
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.of(Fixture.Categories.aulas().getId());
        final var expectedGenres = Set.of(Fixture.Genres.tech().getId());
        final var expectedMembers = Set.of(vicenteID);
        final Resource expectedVideo = null;
        final Resource expectedTrailer = null;
        final Resource expectedBanner = null;
        final Resource expectedThumb = null;
        final Resource expectedThumbHalf = null;

        final var aCommand = CreateVideoCommand.with(
                expectedTitle,
                expectedDescription,
                expectedLaunchYear,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating.getName(),
                asString(expectedCategories),
                asString(expectedGenres),
                asString(expectedMembers),
                expectedVideo,
                expectedTrailer,
                expectedBanner,
                expectedThumb,
                expectedThumbHalf
        );


        when(categoryGateway.existsById(any())).thenReturn(new ArrayList<>(expectedCategories));
        when(castMemberGateway.existsById(any())).thenReturn(new ArrayList<>());
        when(genreGateway.existsById(any())).thenReturn(new ArrayList<>(expectedGenres));

        //when

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });
        //then

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(categoryGateway, times(1)).existsById(any());
        verify(castMemberGateway, times(1)).existsById(any());
        verify(genreGateway, times(1)).existsById(any());
        verify(mediaResourceGateway, times(0)).storeAudioVideo(any(), any());
        verify(mediaResourceGateway, times(0)).storeImage(any(), any());
        verify(videoGateway, times(0)).create(any());

    }


    @Test
    public void givenAValidCommand_whenCallsCreateVideoThrowsException_shouldCallClearResources() {
        //given
        final var expectedErrorMessage = "An error on create video was observed [videoId:";
        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchYear = Year.of(Fixture.year());
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.of(Fixture.Categories.aulas().getId());
        final var expectedGenres = Set.of(Fixture.Genres.tech().getId());
        final var expectedMembers = Set.of(Fixture.CastMembers.wesley().getId(), Fixture.CastMembers.vicente().getId());
        final Resource expectedVideo = Fixture.Videos.resource(Resource.Type.VIDEO);
        final Resource expectedTrailer = Fixture.Videos.resource(Resource.Type.TRAILER);
        final Resource expectedBanner = Fixture.Videos.resource(Resource.Type.BANNER);
        final Resource expectedThumb = Fixture.Videos.resource(Resource.Type.THUMBNAIL);
        final Resource expectedThumbHalf = Fixture.Videos.resource(Resource.Type.THUMBNAIL_HALF);

        final var aCommand = CreateVideoCommand.with(expectedTitle, expectedDescription, expectedLaunchYear.getValue(), expectedDuration, expectedOpened, expectedPublished, expectedRating.getName(), asString(expectedCategories), asString(expectedGenres), asString(expectedMembers), expectedVideo, expectedTrailer, expectedBanner, expectedThumb, expectedThumbHalf);

        when(categoryGateway.existsById(any())).thenReturn(new ArrayList<>(expectedCategories));
        when(castMemberGateway.existsById(any())).thenReturn(new ArrayList<>(expectedMembers));
        when(genreGateway.existsById(any())).thenReturn(new ArrayList<>(expectedGenres));
        mockImageMedia();
        mockAudioVideoMedia();
        when(videoGateway.create(any())).thenThrow(new RuntimeException("Internal Server Error"));
        //when

        final var actualException = Assertions.assertThrows(InternalErrorException.class, () -> useCase.execute(aCommand));
        //then

        Assertions.assertNotNull(actualException);
        Assertions.assertTrue(actualException.getMessage().startsWith(expectedErrorMessage));

        verify(mediaResourceGateway).clearResources(any());

    }


    private void mockImageMedia() {
        when(mediaResourceGateway.storeImage(any(), any())).thenAnswer(t -> {
            final var resource = t.getArgument(1, Resource.class);
            return ImageMedia.with(UUID.randomUUID().toString(), resource.name(), "/img");
        });
    }

    private void mockAudioVideoMedia() {
        when(mediaResourceGateway.storeAudioVideo(any(), any())).thenAnswer(t -> {
            final var resource = t.getArgument(1, Resource.class);
            return AudioVideoMedia.with(UUID.randomUUID().toString(), resource.name(), "/img", "", MediaStatus.PENDING);
        });
    }


}
