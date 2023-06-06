package com.fullcycle.admin.catalog.domain;

import com.fullcycle.admin.catalog.domain.castmember.CastMember;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberType;
import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.domain.genre.Genre;
import com.fullcycle.admin.catalog.domain.utils.IdUtils;
import com.fullcycle.admin.catalog.domain.video.*;
import com.fullcycle.admin.catalog.domain.resource.Resource;
import com.github.javafaker.Faker;

import java.time.Year;
import java.util.Set;

import static io.vavr.API.*;

public final class Fixture {

    private static final Faker FAKER = new Faker();

    public static String name() {
        return FAKER.name().fullName();
    }

    public static Integer year() {
        return FAKER.random().nextInt(2020, 2030);
    }

    public static Double duration() {
        return FAKER.options().option(120.0, 15.5, 35.5, 10.0, 2.0);
    }

    public static boolean bool() {
        return FAKER.bool().bool();
    }

    public static String title() {
        return FAKER.options().option(
                "System Design no Mercado Livre na prática",
                "Não cometa esses erros ao trabalhar com Microsserviços",
                "Testes de Mutação. Você não testa seu software corretamente"
        );
    }


    public static Video video() {
        return Video.newVideo(
                Fixture.title(),
                Videos.description(),
                Year.of(Fixture.year()),
                Fixture.duration(),
                Videos.rating(),
                Fixture.bool(),
                Fixture.bool(),
                Set.of(Categories.aulas().getId()),
                Set.of(Genres.tech().getId()),
                Set.of(CastMembers.wesley().getId(), CastMembers.vicente().getId())
        );
    }

    public static final class Categories {
        private static final Category AULAS = Category.newCategory("Aulas", "Some description", true);
        private static final Category LIVES = Category.newCategory("Tutoriais", "Some description", true);

        public static Category aulas() {
            return AULAS.clone();
        }

        public static Category lives() {
            return LIVES.clone();
        }
    }

    public static final class CastMembers {

        private static final CastMember WESLEY = CastMember.newMember("Wesley FullCycle", CastMemberType.ACTOR);
        private static final CastMember VICENTE = CastMember.newMember("Vicente FullCycle", CastMemberType.DIRECTOR);

        public static CastMemberType type() {
            return FAKER.options().option(CastMemberType.values());
        }

        public static CastMember wesley() {
            return CastMember.with(WESLEY);
        }

        public static CastMember vicente() {
            return CastMember.with(VICENTE);
        }
    }

    public static final class Genres {
        private static final Genre TECH = Genre.newGenre("Tech", true);
        private static final Genre BUSINESS = Genre.newGenre("Business", true);

        public static Genre tech() {
            return Genre.with(TECH);
        }

        public static Genre business() {
            return Genre.with(BUSINESS);
        }
    }

    public static final class Videos {

        public static Video systemDesign() {
            return Video.newVideo(
                    Fixture.title(),
                    description(),
                    Year.of(Fixture.year()),
                    Fixture.duration(),
                    rating(),
                    Fixture.bool(),
                    Fixture.bool(),
                    Set.of(Categories.aulas().getId()),
                    Set.of(Genres.tech().getId()),
                    Set.of(CastMembers.wesley().getId(), CastMembers.vicente().getId())
            );
        }

        public static Rating rating() {
            return FAKER.options().option(Rating.values());
        }

        public static VideoMediaType mediaType() {
            return FAKER.options().option(VideoMediaType.values());
        }

        public static Resource resource(final VideoMediaType type) {
            final String contentType = Match(type).of(
                    Case($(List(VideoMediaType.VIDEO, VideoMediaType.TRAILER)::contains), "video/mp4"),
                    Case($(), "image/jpg")
            );
            final String checksum = IdUtils.uuid();
            final byte[] content = "Conteudo".getBytes();
            return Resource.with(checksum, content, contentType, type.name().toLowerCase());
        }

        public static String description() {
            return FAKER.options().option(
                    "Disclaimer: This video is not sponsored. I do use affiliate links when linking products in the video description. This does not affect your buying experience or item price but does mean I receive a small commission on items purchased using such links.",
                    "This video is sponsored by Skillshare. The first 1000 people to use this link will get a free trial of Skillshare Premium Membership: https://skl.sh/techlead05211"
            );
        }

        public static AudioVideoMedia audioVideo(final VideoMediaType type) {
            final var checksum = IdUtils.uuid();
            return AudioVideoMedia.with(
                    checksum,
                    checksum,
                    type.name().toLowerCase(),
                    "/videos/" + checksum,
                    "",
                    MediaStatus.PENDING
            );
        }

        public static ImageMedia image(final VideoMediaType type) {
            final var checksum = IdUtils.uuid();
            return ImageMedia.with(
                    checksum,
                    type.name().toLowerCase(),
                    "/images/" + checksum
            );
        }
    }
}
