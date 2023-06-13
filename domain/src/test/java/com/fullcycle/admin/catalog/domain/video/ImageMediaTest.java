package com.fullcycle.admin.catalog.domain.video;

import com.fullcycle.admin.catalog.domain.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ImageMediaTest extends UnitTest {

    @Test
    public void givenValidParams_whenCallsNewImage_ShouldReturnInstance() {
        //given
        final var expectedCheckSum = "abc";
        final var expectedName = "Banner.png";
        final var expectedLocation = "/images/ac";
        //when

        final var actualImage = ImageMedia.with(expectedCheckSum, expectedName, expectedLocation);
        //then
        Assertions.assertNotNull(actualImage);
        Assertions.assertEquals(expectedCheckSum, actualImage.checksum());
        Assertions.assertEquals(expectedName, actualImage.name());
        Assertions.assertEquals(expectedLocation, actualImage.location());
    }


    @Test
    public void givenTwoImagesWithSameChecksumAndLocation_whenCallsEquals_ShouldReturnTrue() {
        //given
        final var expectedCheckSum = "abc";
        final var expectedLocation = "/images/ac";

        final var img1 = ImageMedia.with(expectedCheckSum, "Random", expectedLocation);
        final var img2 = ImageMedia.with(expectedCheckSum, "Simple", expectedLocation);
        //then

        Assertions.assertEquals(img1, img2);
        Assertions.assertNotSame(img1, img2);
    }


    @Test
    public void givenInvalidParams_whenCallsWith_shouldReturnError() {
        Assertions.assertThrows(NullPointerException.class, () -> ImageMedia.with(null, "Random", "/images/ac"));
        Assertions.assertThrows(NullPointerException.class, () -> ImageMedia.with("abc", null, "/images/ac"));
        Assertions.assertThrows(NullPointerException.class, () -> ImageMedia.with("abc", "Random", null));
    }

}