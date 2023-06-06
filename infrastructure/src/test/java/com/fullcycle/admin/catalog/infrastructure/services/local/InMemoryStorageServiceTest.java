package com.fullcycle.admin.catalog.infrastructure.services.local;

import com.fullcycle.admin.catalog.domain.Fixture;
import com.fullcycle.admin.catalog.domain.utils.IdUtils;
import com.fullcycle.admin.catalog.domain.video.VideoMediaType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryStorageServiceTest {
    private InMemoryStorageService target = new InMemoryStorageService();


    @BeforeEach
    public void setUp() {
        this.target.reset();
    }

    @Test
    public void givenValidResource_whenCallStore_shouldStoreIt() {
        //given
        final var expectedName = IdUtils.uuid();
        final var expectedResource = Fixture.Videos.resource(VideoMediaType.VIDEO);

        //when
        target.store(expectedName, expectedResource);

        //then
        Assertions.assertEquals(expectedResource, target.storage().get(expectedName));
    }

    @Test
    public void givenValidResource_whenCallGet_shouldRetrieveIt() {
        //given
        final var expectedName = IdUtils.uuid();
        final var expectedResource = Fixture.Videos.resource(VideoMediaType.VIDEO);
        target.storage().put(expectedName, expectedResource);
        //when
        final var actualResource = target.get(expectedName).get();
        //then
        Assertions.assertEquals(expectedResource, actualResource);
    }


    @Test
    public void givenInvalidResource_whenCallGet_shouldBeEmpty() {
        //given
        final var expectedName = IdUtils.uuid();
        final var expectedResource = Fixture.Videos.resource(VideoMediaType.VIDEO);
        //when
        final var actualResource = target.get(expectedName);
        //then
        Assertions.assertTrue(actualResource.isEmpty());
    }

    @Test
    public void givenValidPrefix_whenCallList_shouldRetrieveAll() {
        //given
        final var expectedNames = List.of(
                "video_" + IdUtils.uuid(),
                "video_" + IdUtils.uuid(),
                "video_" + IdUtils.uuid()
        );
        final var all = new ArrayList<>(expectedNames);
        all.add("image_" + IdUtils.uuid());
        all.add("image_" + IdUtils.uuid());

        all.forEach(it -> target.storage().put(it, Fixture.Videos.resource(VideoMediaType.VIDEO)));

        Assertions.assertEquals(5, target.storage().size());
        //when
        final var actualResource = target.list("video");
        //then
        Assertions.assertTrue(expectedNames.size() == actualResource.size() && expectedNames.containsAll(actualResource));
    }


    @Test
    public void givenValidNames_whenCallDelete_shouldDeleteAll() {
        //given
        final var videos = List.of(
                "video_" + IdUtils.uuid(),
                "video_" + IdUtils.uuid(),
                "video_" + IdUtils.uuid()
        );
        final var expectedNames = Set.of(
                "image_" + IdUtils.uuid(),
                "image_" + IdUtils.uuid()
        );

        final var all = new ArrayList<>(videos);
        all.addAll(expectedNames);

        all.forEach(it -> target.storage().put(it, Fixture.Videos.resource(VideoMediaType.VIDEO)));

        Assertions.assertEquals(5, target.storage().size());
        //when
        target.deleteAll(videos);
        //then
        final var actualKeys = target.storage().keySet();
        Assertions.assertEquals(2, target.storage().size());

        Assertions.assertTrue(expectedNames.size() == actualKeys.size() && expectedNames.containsAll(actualKeys));
        Assertions.assertEquals(expectedNames, target.storage().keySet());

    }

}