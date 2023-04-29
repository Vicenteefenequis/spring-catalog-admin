package com.fullcycle.admin.catalog.domain.video;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudioVideoMediaTest {

    @Test
    public void givenValidParams_whenCallsNewAudioVideo_ShouldReturnInstance() {
        //given
        final var expectedCheckSum = "abc";
        final var expectedName = "Banner.png";
        final var expectedRawLocation = "/images/ac";
        final var expectedEncodedLocation = "/images/ac";
        final var expectedStatus = MediaStatus.PENDING;
        //when

        final var actualImage = AudioVideoMedia.with(expectedCheckSum, expectedName, expectedRawLocation, expectedEncodedLocation, expectedStatus);
        //then
        Assertions.assertNotNull(actualImage);
        Assertions.assertEquals(expectedCheckSum, actualImage.checksum());
        Assertions.assertEquals(expectedName, actualImage.name());
        Assertions.assertEquals(expectedRawLocation, actualImage.rawLocation());
        Assertions.assertEquals(expectedEncodedLocation, actualImage.encodedLocation());
        Assertions.assertEquals(expectedStatus, actualImage.status());
    }


    @Test
    public void givenTwoAudioVideWithSameChecksumAndLocation_whenCallsEquals_ShouldReturnTrue() {
        //given
        final var expectedCheckSum = "abc";
        final var expectedRawLocation = "/images/ac";

        final var img1 = AudioVideoMedia.with(expectedCheckSum, "Random", expectedRawLocation, "", MediaStatus.PENDING);
        final var img2 = AudioVideoMedia.with(expectedCheckSum, "Simple", expectedRawLocation, "", MediaStatus.PENDING);
        //then

        Assertions.assertEquals(img1, img2);
        Assertions.assertNotSame(img1, img2);
    }


    @Test
    public void givenInvalidParams_whenCallsWith_shouldReturnError() {
        Assertions.assertThrows(NullPointerException.class, () -> AudioVideoMedia.with(null, "Random", "/videos", "/videos", MediaStatus.PENDING));
        Assertions.assertThrows(NullPointerException.class, () -> AudioVideoMedia.with("abc", null, "/videos", "/videos", MediaStatus.PENDING));
        Assertions.assertThrows(NullPointerException.class, () -> AudioVideoMedia.with("abc", "Random", null, "/videos", MediaStatus.PENDING));
        Assertions.assertThrows(NullPointerException.class, () -> AudioVideoMedia.with("abc", "Random", "/videos", null, MediaStatus.PENDING));
        Assertions.assertThrows(NullPointerException.class, () -> AudioVideoMedia.with("abc", "Random", "/videos", "/videos", null));
    }


}