package com.fullcycle.admin.catalog.domain.video;

import com.fullcycle.admin.catalog.domain.castmember.CastMemberID;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.exceptions.DomainException;
import com.fullcycle.admin.catalog.domain.genre.GenreID;
import com.fullcycle.admin.catalog.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.Set;

public class VideoValidatorTest {

    @Test
    public void givenNullTitle_whenCallsValidate_shouldReceiveError() {
        //given
        final String expectedTitle = null;
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

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'title' should not be null";

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
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());


        //when

        final var actualError = Assertions.assertThrows(
                DomainException.class,
                validator::validate
        );

        //then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
    }

    @Test
    public void givenEmptyTitle_whenCallsValidate_shouldReceiveError() {
        //given
        final var expectedTitle = "";
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

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'title' should not be empty";

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
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());


        //when

        final var actualError = Assertions.assertThrows(
                DomainException.class,
                validator::validate
        );

        //then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
    }

    @Test
    public void givenTitleWithLengthGreaterThan255_whenCallsValidate_shouldReceiveError() {
        //given
        final var expectedTitle = "A certificação de metodologias que nos auxiliam a lidar com a expansão dos mercados mundiais desafia a capacidade de equalização dos métodos utilizados na avaliação de resultados." +
                "Assim mesmo, o desafiador cenário globalizado exige a precisão e a definição das novas proposições.";
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

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'title' must be between 1 and 255 characters";

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
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());


        //when

        final var actualError = Assertions.assertThrows(
                DomainException.class,
                validator::validate
        );

        //then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
    }


    @Test
    public void givenEmptyDescription_whenCallsValidate_shouldReceiveError() {
        //given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = "";

        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'description' should not be empty";

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
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());


        //when

        final var actualError = Assertions.assertThrows(
                DomainException.class,
                validator::validate
        );

        //then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
    }

    @Test
    public void givenDescriptionWithLengthGreaterThan4000_whenCallsValidate_shouldReceiveError() {
        //given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = """
                                Assim mesmo, o aumento do diálogo entre os diferentes setores produtivos não pode mais se dissociar das novas proposições. O que temos que ter sempre em mente é que a determinação clara de objetivos acarreta um processo de reformulação e modernização das regras de conduta normativas. A nível organizacional, a contínua expansão de nossa atividade oferece uma interessante oportunidade para verificação de todos os recursos funcionais envolvidos. O incentivo ao avanço tecnológico, assim como a hegemonia do ambiente político facilita a criação das diversas correntes de pensamento.
                                Todavia, o comprometimento entre as equipes estimula a padronização dos índices pretendidos. Pensando mais a longo prazo, o consenso sobre a necessidade de qualificação aponta para a melhoria dos modos de operação convencionais. Desta maneira, o desenvolvimento contínuo de distintas formas de atuação desafia a capacidade de equalização dos conhecimentos estratégicos para atingir a excelência. Todas estas questões, devidamente ponderadas, levantam dúvidas sobre se a percepção das dificuldades pode nos levar a considerar a reestruturação das condições financeiras e administrativas exigidas.
                                Ainda assim, existem dúvidas a respeito de como a mobilidade dos capitais internacionais obstaculiza a apreciação da importância das diretrizes de desenvolvimento para o futuro. Acima de tudo, é fundamental ressaltar que a crescente influência da mídia possibilita uma melhor visão global do orçamento setorial. Neste sentido, a constante divulgação das informações auxilia a preparação e a composição dos paradigmas corporativos. Podemos já vislumbrar o modo pelo qual a expansão dos mercados mundiais causa impacto indireto na reavaliação do remanejamento dos quadros funcionais. No entanto, não podemos esquecer que a competitividade nas transações comerciais assume importantes posições no estabelecimento do levantamento das variáveis envolvidas.
                                Caros amigos, a valorização de fatores subjetivos talvez venha a ressaltar a relatividade dos níveis de motivação departamental. Do mesmo modo, a necessidade de renovação processual agrega valor ao estabelecimento do fluxo de informações. Nunca é demais lembrar o peso e o significado destes problemas, uma vez que a execução dos pontos do programa ainda não demonstrou convincentemente que vai participar na mudança das posturas dos órgãos dirigentes com relação às suas atribuições. As experiências acumuladas demonstram que o julgamento imparcial das eventualidades maximiza as possibilidades por conta do sistema de formação de quadros que corresponde às necessidades. É claro que o surgimento do comércio virtual prepara-nos para enfrentar situações atípicas decorrentes do sistema de participação geral.
                                Gostaria de enfatizar que o desafiador cenário globalizado representa uma abertura para a melhoria de alternativas às soluções ortodoxas. No mundo atual, o acompanhamento das preferências de consumo apresenta tendências no sentido de aprovar a manutenção do impacto na agilidade decisória. O cuidado em identificar pontos críticos na estrutura atual da organização nos obriga à análise do investimento em reciclagem técnica.
                                A certificação de metodologias que nos auxiliam a lidar com a consulta aos diversos militantes cumpre um papel essencial na formulação da gestão inovadora da qual fazemos parte. É importante questionar o quanto o fenômeno da Internet estende o alcance e a importância do retorno esperado a longo prazo. Não obstante, a revolução dos costumes afeta positivamente a correta previsão das direções preferenciais no sentido do progresso.
                                 O empenho em analisar a consolidação das estruturas faz parte de um processo de gerenciamento das condições inegavelmente apropriadas. Evidentemente, o novo modelo estrutural aqui preconizado garante a contribuição de um grupo importante na determinação do processo de comunicação como um todo. Percebemos, cada vez mais, que a adoção de políticas descentralizadoras exige a precisão e a definição dos métodos utilizados na avaliação de resultados.
                                Por conseguinte, a complexidade dos estudos efetuados promove a alavancagem das formas de ação. A prática cotidiana prova que o início da atividade geral de formação de atitudes é uma das consequências dos procedimentos normalmente adotados. Por outro lado, o entendimento das metas propostas deve passar por modificações independentemente dos relacionamentos verticais entre as hierarquias. É claro que o aumento do diálogo entre os diferentes setores produtivos prepara-nos para enfrentar situações atípicas decorrentes das novas proposições.
                                 A nível organizacional, a crescente influência da mídia agrega valor ao estabelecimento da gestão inovadora da qual fazemos parte. Neste sentido, o consenso sobre a necessidade de qualificação oferece uma interessante oportunidade para verificação do sistema de participação geral. O que temos que ter sempre em mente é que o novo modelo estrutural aqui preconizado acarreta um processo de reformulação e modernização dos conhecimentos estratégicos para atingir a excelência. Todavia, a expansão dos mercados mundiais apresenta tendências no sentido de aprovar a manutenção dos índices pretendidos. É importante questionar o quanto a mobilidade dos capitais internacionais causa impacto indireto na reavaliação de todos os recursos funcionais envolvidos.
                                Desta maneira, a revolução dos costumes talvez venha a ressaltar a relatividade do impacto na agilidade decisória. A prática cotidiana prova que a percepção das dificuldades pode nos levar a considerar a reestruturação das direções preferenciais no sentido do progresso. No entanto, não podemos esquecer que a constante divulgação das informações assume importantes posições no estabelecimento dos métodos utilizados na avaliação de resultados. Assim mesmo, a determinação clara de objetivos desafia a capacidade de equalização do orçamento setorial.
                                Caros amigos, a complexidade dos estudos efetuados representa uma abertura para a melhoria do processo de comunicação como um todo. O cuidado em identificar pontos críticos no comprometimento entre as equipes estimula a padronização dos relacionamentos verticais entre as hierarquias. Pensando mais a longo prazo, a contínua expansão de nossa atividade obstaculiza a apreciação da importância do levantamento das variáveis envolvidas.
                                Acima de tudo, é fundamental ressaltar que a estrutura atual da organização é uma das consequências dos níveis de motivação departamental. Do mesmo modo, o fenômeno da Internet garante a contribuição de um grupo importante na determinação do fluxo de informações. Nunca é demais lembrar o peso e o significado destes problemas, uma vez que o desenvolvimento contínuo de distintas formas de atuação ainda não demonstrou convincentemente que vai participar na mudança das condições inegavelmente apropriadas.
                                Gostaria de enfatizar que o julgamento imparcial das eventualidades maximiza as possibilidades por conta do sistema de formação de quadros que corresponde às necessidades. Todas estas questões, devidamente ponderadas, levantam dúvidas sobre se o surgimento do comércio virtual promove a alavancagem de alternativas às soluções ortodoxas. Ainda assim, existem dúvidas a respeito de como a necessidade de renovação processual auxilia a preparação e a composição das regras de conduta normativas. Podemos já vislumbrar o modo pelo qual a execução dos pontos do programa cumpre um papel essencial na formulação do retorno esperado a longo prazo. No mundo atual, a valorização de fatores subjetivos nos obriga à análise do investimento em reciclagem técnica.
                                A certificação de metodologias que nos auxiliam a lidar com a consolidação das estruturas faz parte de um processo de gerenciamento das formas de ação. Não obstante, a competitividade nas transações comerciais possibilita uma melhor visão global das condições financeiras e administrativas exigidas. Evidentemente, o entendimento das metas propostas não pode mais se dissociar do remanejamento dos quadros funcionais. O empenho em analisar a consulta aos diversos militantes estende o alcance e a importância das posturas dos órgãos dirigentes com relação às suas atribuições.
                                Por outro lado, a adoção de políticas descentralizadoras facilita a criação dos modos de operação convencionais. Percebemos, cada vez mais, que a hegemonia do ambiente político aponta para a melhoria das diretrizes de desenvolvimento para o futuro. Por conseguinte, o desafiador cenário globalizado exige a precisão e a definição das diversas correntes de pensamento.
                                As experiências acumuladas demonstram que o início da atividade geral de formação de atitudes afeta positivamente a correta previsão dos procedimentos normalmente adotados. O incentivo ao avanço tecnológico, assim como o acompanhamento das preferências de consumo deve passar por modificações independentemente dos paradigmas corporativos. É claro que o aumento do diálogo entre os diferentes setores produtivos prepara-nos para enfrentar situações atípicas decorrentes das regras de conduta normativas. A nível organizacional, a crescente influência da mídia ainda não demonstrou convincentemente que vai participar na mudança da gestão inovadora da qual fazemos parte.
                                O que temos que ter sempre em mente é que a consolidação das estruturas oferece uma interessante oportunidade para verificação do levantamento das variáveis envolvidas. Todavia, a percepção das dificuldades acarreta um processo de reformulação e modernização dos modos de operação convencionais. Neste sentido, a complexidade dos estudos efetuados obstaculiza a apreciação da importância dos índices pretendidos. Desta maneira, a mobilidade dos capitais internacionais representa uma abertura para a melhoria do sistema de formação de quadros que corresponde às necessidades. A prática cotidiana prova que o desafiador cenário globalizado talvez venha a ressaltar a relatividade do investimento em reciclagem técnica.
                                Por conseguinte, o comprometimento entre as equipes é uma das consequências dos conhecimentos estratégicos para atingir a excelência. Nunca é demais lembrar o peso e o significado destes problemas, uma vez que a contínua expansão de nossa atividade agrega valor ao estabelecimento das diretrizes de desenvolvimento para o futuro. Assim mesmo, a expansão dos mercados mundiais desafia a capacidade de equalização de todos os recursos funcionais envolvidos. Caros amigos, o novo modelo estrutural aqui preconizado deve passar por modificações independentemente do processo de comunicação como um todo.
                                O cuidado em identificar pontos críticos na necessidade de renovação processual possibilita uma melhor visão global das condições financeiras e administrativas exigidas. As experiências acumuladas demonstram que o consenso sobre a necessidade de qualificação aponta para a melhoria do sistema de participação geral. O empenho em analisar a estrutura atual da organização exige a precisão e a definição dos níveis de motivação departamental.
                                Gostaria de enfatizar que o fenômeno da Internet garante a contribuição de um grupo importante na determinação do fluxo de informações. No entanto, não podemos esquecer que a revolução dos costumes nos obriga à análise das condições inegavelmente apropriadas. Por outro lado, o julgamento imparcial das eventualidades maximiza as possibilidades por conta das direções preferenciais no sentido do progresso.
                                No mundo atual, a consulta aos diversos militantes estimula a padronização de alternativas às soluções ortodoxas. Pensando mais a longo prazo, a hegemonia do ambiente político pode nos levar a considerar a reestruturação das posturas dos órgãos dirigentes com relação às suas atribuições. Evidentemente, a execução dos pontos do programa cumpre um papel essencial na formulação do retorno esperado a longo prazo. Todas estas questões, devidamente ponderadas, levantam dúvidas sobre se a adoção de políticas descentralizadoras estende o alcance e a importância dos métodos utilizados na avaliação de resultados.
                                A certificação de metodologias que nos auxiliam a lidar com a determinação clara de objetivos faz parte de um processo de gerenciamento das formas de ação. Não obstante, o entendimento das metas propostas apresenta tendências no sentido de aprovar a manutenção dos relacionamentos verticais entre as hierarquias. Podemos já vislumbrar o modo pelo qual o surgimento do comércio virtual não pode mais se dissociar do remanejamento dos quadros funcionais. Acima de tudo, é fundamental ressaltar que a constante divulgação das informações auxilia a preparação e a composição do impacto na agilidade decisória.
                                Do mesmo modo, a valorização de fatores subjetivos facilita a criação das novas proposições. Percebemos, cada vez mais, que o início da atividade geral de formação de atitudes promove a alavancagem do orçamento setorial. O incentivo ao avanço tecnológico, assim como a competitividade nas transações comerciais assume importantes posições no estabelecimento das diversas correntes de pensamento.
                                Ainda assim, existem dúvidas a respeito de como o desenvolvimento contínuo de distintas formas de atuação afeta positivamente a correta previsão dos procedimentos normalmente adotados. É importante questionar o quanto o acompanhamento das preferências de consumo causa impacto indireto na reavaliação dos paradigmas corporativos. Todavia, a consulta aos diversos militantes faz parte de um processo de gerenciamento dos procedimentos normalmente adotados. Ainda assim, existem dúvidas a respeito de como o desafiador cenário globalizado agrega valor ao estabelecimento das diretrizes de desenvolvimento para o futuro. O que temos que ter sempre em mente é que a consolidação das estruturas facilita a criação do investimento em reciclagem técnica.
                                No entanto, não podemos esquecer que a percepção das dificuldades acarreta um processo de reformulação e modernização das condições inegavelmente apropriadas. Neste sentido, o desenvolvimento contínuo de distintas formas de atuação é uma das consequências dos níveis de motivação departamental. Desta maneira, a crescente influência da mídia estende o alcance e a importância do sistema de formação de quadros que corresponde às necessidades.
                                A prática cotidiana prova que a execução dos pontos do programa oferece uma interessante oportunidade para verificação das condições financeiras e administrativas exigidas. Pensando mais a longo prazo, o novo modelo estrutural aqui preconizado auxilia a preparação e a composição dos conhecimentos estratégicos para atingir a excelência. O cuidado em identificar pontos críticos na valorização de fatores subjetivos nos obriga à análise das posturas dos órgãos dirigentes com relação às suas atribuições. Assim mesmo, a expansão dos mercados mundiais possibilita uma melhor visão global de todos os recursos funcionais envolvidos.
                                Caros amigos, o comprometimento entre as equipes deve passar por modificações independentemente do impacto na agilidade decisória. É claro que a mobilidade dos capitais internacionais apresenta tendências no sentido de aprovar a manutenção do levantamento das variáveis envolvidas. A certificação de metodologias que nos auxiliam a lidar com o entendimento das metas propostas obstaculiza a apreciação da importância da gestão inovadora da qual fazemos parte. A nível organizacional, a estrutura atual da organização cumpre um papel essencial na formulação das formas de ação. O empenho em analisar o fenômeno da Internet representa uma abertura para a melhoria do fluxo de informações.
                                Do mesmo modo, a revolução dos costumes estimula a padronização do sistema de participação geral. Não obstante, o julgamento imparcial das eventualidades prepara-nos para enfrentar situações atípicas decorrentes das direções preferenciais no sentido do progresso. As experiências acumuladas demonstram que o início da atividade geral de formação de atitudes talvez venha a ressaltar a relatividade dos índices pretendidos. Acima de tudo, é fundamental ressaltar que a hegemonia do ambiente político desafia a capacidade de equalização de alternativas às soluções ortodoxas.
                                Evidentemente, a necessidade de renovação processual exige a precisão e a definição das regras de conduta normativas. O incentivo ao avanço tecnológico, assim como a adoção de políticas descentralizadoras garante a contribuição de um grupo importante na determinação dos modos de operação convencionais. Por conseguinte, o consenso sobre a necessidade de qualificação maximiza as possibilidades por conta das diversas correntes de pensamento. No mundo atual, a determinação clara de objetivos causa impacto indireto na reavaliação dos relacionamentos verticais entre as hierarquias.
                                Podemos já vislumbrar o modo pelo qual o surgimento do comércio virtual ainda não demonstrou convincentemente que vai participar na mudança do orçamento setorial. Percebemos, cada vez mais, que a constante divulgação das informações aponta para a melhoria dos paradigmas corporativos. Todas estas questões, devidamente ponderadas, levantam dúvidas sobre se o aumento do diálogo entre os diferentes setores produtivos não pode mais se dissociar das novas proposições. É importante questionar o quanto o acompanhamento das preferências de consumo promove a alavancagem do remanejamento dos quadros funcionais.
                                Gostaria de enfatizar que a complexidade dos estudos efetuados pode nos levar a considerar a reestruturação dos métodos utilizados na avaliação de resultados. Nunca é demais lembrar o peso e o significado destes problemas, uma vez que a competitividade nas transações comerciais afeta positivamente a correta previsão do retorno esperado a longo prazo. Por outro lado, a contínua expansão de nossa atividade assume importantes posições no estabelecimento do processo de comunicação como um todo. Todavia, a mobilidade dos capitais internacionais faz parte de um processo de gerenciamento dos procedimentos normalmente adotados.
                                Por outro lado, o desafiador cenário globalizado estende o alcance e a importância da gestão inovadora da qual fazemos parte. Evidentemente, o julgamento imparcial das eventualidades acarreta um processo de reformulação e modernização das condições financeiras e administrativas exigidas. Pensando mais a longo prazo, a constante divulgação das informações maximiza as possibilidades por conta das condições inegavelmente apropriadas. Neste sentido, a revolução dos costumes não pode mais se dissociar do investimento em reciclagem técnica.
                                Desta maneira, a crescente influência da mídia cumpre um papel essencial na formulação do levantamento das variáveis envolvidas. Acima de tudo, é fundamental ressaltar que o surgimento do comércio virtual facilita a criação dos níveis de motivação departamental. Do mesmo modo, a contínua expansão de nossa atividade assume importantes posições no estabelecimento das diversas correntes de pensamento. O cuidado em identificar pontos críticos na valorização de fatores subjetivos nos obriga à análise dos índices pretendidos. Não obstante, a adoção de políticas descentralizadoras prepara-nos para enfrentar situações atípicas decorrentes de todos os recursos funcionais envolvidos.
                                Percebemos, cada vez mais, que o comprometimento entre as equipes talvez venha a ressaltar a relatividade do remanejamento dos quadros funcionais. O empenho em analisar a consolidação das estruturas apresenta tendências no sentido de aprovar a manutenção das novas proposições. A certificação de metodologias que nos auxiliam a lidar com o entendimento das metas propostas agrega valor ao estabelecimento do sistema de participação geral. Nunca é demais lembrar o peso e o significado destes problemas, uma vez que a determinação clara de objetivos representa uma abertura para a melhoria das formas de ação. As experiências acumuladas demonstram que a estrutura atual da organização obstaculiza a apreciação da importância das regras de conduta normativas.
                                Caros amigos, o desenvolvimento contínuo de distintas formas de atuação promove a alavancagem dos modos de operação convencionais. O incentivo ao avanço tecnológico, assim como a percepção das dificuldades possibilita uma melhor visão global das direções preferenciais no sentido do progresso. Todas estas questões, devidamente ponderadas, levantam dúvidas sobre se o consenso sobre a necessidade de qualificação deve passar por modificações independentemente do orçamento setorial. A prática cotidiana prova que a hegemonia do ambiente político desafia a capacidade de equalização de alternativas às soluções ortodoxas.
                                Ainda assim, existem dúvidas a respeito de como a complexidade dos estudos efetuados auxilia a preparação e a composição das diretrizes de desenvolvimento para o futuro. Podemos já vislumbrar o modo pelo qual a execução dos pontos do programa garante a contribuição de um grupo importante na determinação do fluxo de informações. Por conseguinte, a consulta aos diversos militantes é uma das consequências dos conhecimentos estratégicos para atingir a excelência. O que temos que ter sempre em mente é que o fenômeno da Internet causa impacto indireto na reavaliação do processo de comunicação como um todo.
                                Assim mesmo, a expansão dos mercados mundiais ainda não demonstrou convincentemente que vai participar na mudança das posturas dos órgãos dirigentes com relação às suas atribuições. No mundo atual, o novo modelo estrutural aqui preconizado afeta positivamente a correta previsão dos paradigmas corporativos. A nível organizacional, a necessidade de renovação processual oferece uma interessante oportunidade para verificação do impacto na agilidade decisória. É importante questionar o quanto o acompanhamento das preferências de consumo estimula a padronização do sistema de formação de quadros que corresponde às necessidades. No entanto, não podemos esquecer que o aumento do diálogo entre os diferentes setores produtivos pode nos levar a considerar a reestruturação dos métodos utilizados na avaliação de resultados.
                                É claro que o início da atividade geral de formação de atitudes aponta para a melhoria do retorno esperado a longo prazo. Gostaria de enfatizar que a competitividade nas transações comerciais exige a precisão e a definição dos relacionamentos verticais entre as hierarquias. É importante questionar o quanto a valorização de fatores subjetivos faz parte de um processo de gerenciamento dos procedimentos normalmente adotados. Por outro lado, a contínua expansão de nossa atividade é uma das consequências das condições inegavelmente apropriadas. Percebemos, cada vez mais, que o julgamento imparcial das eventualidades acarreta um processo de reformulação e modernização do orçamento setorial.
                                Pensando mais a longo prazo, a constante divulgação das informações desafia a capacidade de equalização do fluxo de informações. Do mesmo modo, a execução dos pontos do programa não pode mais se dissociar das diversas correntes de pensamento. Acima de tudo, é fundamental ressaltar que o surgimento do comércio virtual pode nos levar a considerar a reestruturação do levantamento das variáveis envolvidas. Por conseguinte, a crescente influência da mídia afeta positivamente a correta previsão do processo de comunicação como um todo.
                                A certificação de metodologias que nos auxiliam a lidar com o desafiador cenário globalizado cumpre um papel essencial na formulação da gestão inovadora da qual fazemos parte. Todas estas questões, devidamente ponderadas, levantam dúvidas sobre se a necessidade de renovação processual maximiza as possibilidades por conta dos modos de operação convencionais. Neste sentido, o aumento do diálogo entre os diferentes setores produtivos auxilia a preparação e a composição das condições financeiras e administrativas exigidas.
                                Evidentemente, a mobilidade dos capitais internacionais deve passar por modificações independentemente do remanejamento dos quadros funcionais. Ainda assim, existem dúvidas a respeito de como a consulta aos diversos militantes garante a contribuição de um grupo importante na determinação das novas proposições. É claro que a expansão dos mercados mundiais exige a precisão e a definição do sistema de participação geral.
                                Nunca é demais lembrar o peso e o significado destes problemas, uma vez que a determinação clara de objetivos obstaculiza a apreciação da importância das formas de ação. As experiências acumuladas demonstram que a estrutura atual da organização representa uma abertura para a melhoria das direções preferenciais no sentido do progresso. Podemos já vislumbrar o modo pelo qual o consenso sobre a necessidade de qualificação talvez venha a ressaltar a relatividade de todos os recursos funcionais envolvidos. No entanto, não podemos esquecer que a percepção das dificuldades possibilita uma melhor visão global dos paradigmas corporativos.
                                O que temos que ter sempre em mente é que o início da atividade geral de formação de atitudes assume importantes posições no estabelecimento das posturas dos órgãos dirigentes com relação às suas atribuições. A prática cotidiana prova que o acompanhamento das preferências de consumo prepara-nos para enfrentar situações atípicas decorrentes de alternativas às soluções ortodoxas. Desta maneira, a complexidade dos estudos efetuados causa impacto indireto na reavaliação dos conhecimentos estratégicos para atingir a excelência.
                                Caros amigos, a hegemonia do ambiente político promove a alavancagem dos relacionamentos verticais entre as hierarquias. O empenho em analisar a consolidação das estruturas nos obriga à análise do sistema de formação de quadros que corresponde às necessidades. O incentivo ao avanço tecnológico, assim como o desenvolvimento contínuo de distintas formas de atuação estimula a padronização das diretrizes de desenvolvimento para o futuro.
                                No mundo atual, a revolução dos costumes ainda não demonstrou convincentemente que vai participar na mudança dos níveis de motivação departamental. Assim mesmo, o novo modelo estrutural aqui preconizado estende o alcance e a importância do investimento em reciclagem técnica. A nível organizacional, o entendimento das metas propostas oferece uma interessante oportunidade para verificação do impacto na agilidade decisória. Todavia, o fenômeno da Internet apresenta tendências no sentido de aprovar a manutenção dos índices pretendidos. O cuidado em identificar pontos críticos na adoção de políticas descentralizadoras facilita a criação dos métodos utilizados na avaliação de resultados.
                                Não obstante, o comprometimento entre as equipes aponta para a melhoria do retorno esperado a longo prazo. Gostaria de enfatizar que a competitividade nas transações comerciais agrega valor ao estabelecimento das regras de conduta normativas. Desta maneira, a constante divulgação das informações faz parte de um processo de gerenciamento de alternativas às soluções ortodoxas. É importante questionar o quanto o comprometimento entre as equipes desafia a capacidade de equalização dos procedimentos normalmente adotados. No entanto, não podemos esquecer que a adoção de políticas descentralizadoras não pode mais se dissociar do orçamento setorial.
                                O incentivo ao avanço tecnológico, assim como a execução dos pontos do programa estende o alcance e a importância do retorno esperado a longo prazo. Do mesmo modo, a revolução dos costumes afeta positivamente a correta previsão das diversas correntes de pensamento. Por conseguinte, a necessidade de renovação processual pode nos levar a considerar a reestruturação dos índices pretendidos.
                                Todavia, o fenômeno da Internet garante a contribuição de um grupo importante na determinação do processo de comunicação como um todo. Nunca é demais lembrar o peso e o significado destes problemas, uma vez que a percepção das dificuldades ainda não demonstrou convincentemente que vai participar na mudança de todos os recursos funcionais envolvidos. Por outro lado, a complexidade dos estudos efetuados apresenta tendências no sentido de aprovar a manutenção do remanejamento dos quadros funcionais. Neste sentido, o início da atividade geral de formação de atitudes assume importantes posições no estabelecimento do fluxo de informações.
                                Evidentemente, a mobilidade dos capitais internacionais facilita a criação do impacto na agilidade decisória. Todas estas questões, devidamente ponderadas, levantam dúvidas sobre se a competitividade nas transações comerciais nos obriga à análise dos conhecimentos estratégicos para atingir a excelência. É claro que a valorização de fatores subjetivos exige a precisão e a definição das condições inegavelmente apropriadas. No mundo atual, a determinação clara de objetivos obstaculiza a apreciação da importância das formas de ação.
                                Caros amigos, a estrutura atual da organização acarreta um processo de reformulação e modernização dos paradigmas corporativos. Podemos já vislumbrar o modo pelo qual o consenso sobre a necessidade de qualificação talvez venha a ressaltar a relatividade das direções preferenciais no sentido do progresso. Percebemos, cada vez mais, que o desafiador cenário globalizado maximiza as possibilidades por conta das novas proposições. O cuidado em identificar pontos críticos no entendimento das metas propostas auxilia a preparação e a composição das posturas dos órgãos dirigentes com relação às suas atribuições. A prática cotidiana prova que o acompanhamento das preferências de consumo estimula a padronização dos métodos utilizados na avaliação de resultados.
                                Ainda assim, existem dúvidas a respeito de como a consulta aos diversos militantes causa impacto indireto na reavaliação da gestão inovadora da qual fazemos parte. Pensando mais a longo prazo, a hegemonia do ambiente político deve passar por modificações independentemente das condições financeiras e administrativas exigidas. O empenho em analisar o surgimento do comércio virtual prepara-nos para enfrentar situações atípicas decorrentes do sistema de formação de quadros que corresponde às necessidades. A nível organizacional, o desenvolvimento contínuo de distintas formas de atuação cumpre um papel essencial na formulação das diretrizes de desenvolvimento para o futuro. O que temos que ter sempre em mente é que a expansão dos mercados mundiais representa uma abertura para a melhoria dos níveis de motivação departamental.
                                Acima de tudo, é fundamental ressaltar que o aumento do diálogo entre os diferentes setores produtivos promove a alavancagem dos relacionamentos verticais entre as hierarquias. As experiências acumuladas demonstram que o novo modelo estrutural aqui preconizado oferece uma interessante oportunidade para verificação do investimento em reciclagem técnica. Assim mesmo, a crescente influência da mídia possibilita uma melhor visão global do levantamento das variáveis envolvidas.
                                A certificação de metodologias que nos auxiliam a lidar com a contínua expansão de nossa atividade é uma das consequências dos modos de operação convencionais. Não obstante, a consolidação das estruturas aponta para a melhoria do sistema de participação geral. Gostaria de enfatizar que o julgamento imparcial das eventualidades agrega valor ao estabelecimento das regras de conduta normativas. A nível organizacional, o consenso sobre a necessidade de qualificação ainda não demonstrou convincentemente que vai participar na mudança das condições inegavelmente apropriadas.
                                É importante questionar o quanto a estrutura atual da organização acarreta um processo de reformulação e modernização do processo de comunicação como um todo. No entanto, não podemos esquecer que a consolidação das estruturas não pode mais se dissociar das condições financeiras e administrativas exigidas. Todas estas questões, devidamente ponderadas, levantam dúvidas sobre se a execução dos pontos do programa causa impacto indireto na reavaliação do sistema de formação de quadros que corresponde às necessidades. Desta maneira, a necessidade de renovação processual desafia a capacidade de equalização das diversas correntes de pensamento. Neste sentido, a revolução dos costumes pode nos levar a considerar a reestruturação do remanejamento dos quadros funcionais.
                                Por outro lado, a mobilidade dos capitais internacionais agrega valor ao estabelecimento do impacto na agilidade decisória. Ainda assim, existem dúvidas a respeito de como o fenômeno da Internet faz parte de um processo de gerenciamento das regras de conduta normativas. Assim mesmo, a contínua expansão de nossa atividade estimula a padronização dos índices pretendidos.
                                Acima de tudo, é fundamental ressaltar que a consulta aos diversos militantes garante a contribuição de um grupo importante na determinação da gestão inovadora da qual fazemos parte. Gostaria de enfatizar que a crescente influência da mídia maximiza as possibilidades por conta das novas proposições. Caros amigos, a hegemonia do ambiente político cumpre um papel essencial na formulação dos conhecimentos estratégicos para atingir a excelência. A prática cotidiana prova que a valorização de fatores subjetivos afeta positivamente a correta previsão do fluxo de informações.
                                Não obstante, a determinação clara de objetivos promove a alavancagem das formas de ação. O incentivo ao avanço tecnológico, assim como a complexidade dos estudos efetuados prepara-nos para enfrentar situações atípicas decorrentes das diretrizes de desenvolvimento para o futuro. O cuidado em identificar pontos críticos no julgamento imparcial das eventualidades oferece uma interessante oportunidade para verificação das direções preferenciais no sentido do progresso. Percebemos, cada vez mais, que o surgimento do comércio virtual exige a precisão e a definição das posturas dos órgãos dirigentes com relação às suas atribuições.
                                A certificação de metodologias que nos auxiliam a lidar com o aumento do diálogo entre os diferentes setores produtivos estende o alcance e a importância dos níveis de motivação departamental. Por conseguinte, o acompanhamento das preferências de consumo apresenta tendências no sentido de aprovar a manutenção dos métodos utilizados na avaliação de resultados. Nunca é demais lembrar o peso e o significado destes problemas, uma vez que o início da atividade geral de formação de atitudes auxilia a preparação e a composição de alternativas às soluções ortodoxas.
                                Pensando mais a longo prazo, a competitividade nas transações comerciais talvez venha a ressaltar a relatividade do orçamento setorial. As experiências acumuladas demonstram que o comprometimento entre as equipes facilita a criação do retorno esperado a longo prazo. Do mesmo modo, o desenvolvimento contínuo de distintas formas de atuação assume importantes posições no estabelecimento do levantamento das variáveis envolvidas. O que temos que ter sempre em mente é que a expansão dos mercados mundiais representa uma abertura para a melhoria do investimento em reciclagem técnica.
                                Podemos já vislumbrar o modo pelo qual o desafiador cenário globalizado deve passar por modificações independentemente dos relacionamentos verticais entre as hierarquias. O empenho em analisar o novo modelo estrutural aqui preconizado obstaculiza a apreciação da importância dos procedimentos normalmente adotados. Todavia, o entendimento das metas propostas possibilita uma melhor visão global dos paradigmas corporativos. No mundo atual, a percepção das dificuldades é uma das consequências dos modos de operação convencionais. Evidentemente, a adoção de políticas descentralizadoras aponta para a melhoria do sistema de participação geral.
                                É claro que a constante divulgação das informações nos obriga à análise de todos os recursos funcionais envolvidos. Assim mesmo, a necessidade de renovação processual ainda não demonstrou convincentemente que vai participar na mudança das condições inegavelmente apropriadas. No mundo atual, o surgimento do comércio virtual promove a alavancagem dos relacionamentos verticais entre as hierarquias.
                                Acima de tudo, é fundamental ressaltar que a consolidação das estruturas cumpre um papel essencial na formulação dos paradigmas corporativos. Não obstante, a execução dos pontos do programa nos obriga à análise do processo de comunicação como um todo. Desta maneira, a constante divulgação das informações desafia a capacidade de equalização das diversas correntes de pensamento. A prática cotidiana prova que o fenômeno da Internet maximiza as possibilidades por conta do remanejamento dos quadros funcionais.
                                Todas estas questões, devidamente ponderadas, levantam dúvidas sobre se a estrutura atual da organização oferece uma interessante oportunidade para verificação das novas proposições. Caros amigos, a crescente influência da mídia faz parte de um processo de gerenciamento das regras de conduta normativas. O que temos que ter sempre em mente é que o julgamento imparcial das eventualidades assume importantes posições no estabelecimento de alternativas às soluções ortodoxas. Gostaria de enfatizar que a contínua expansão de nossa atividade garante a contribuição de um grupo importante na determinação da gestão inovadora da qual fazemos parte.
                                Podemos já vislumbrar o modo pelo qual o aumento do diálogo entre os diferentes setores produtivos é uma das consequências do impacto na agilidade decisória. Ainda assim, existem dúvidas a respeito de como a consulta aos diversos militantes acarreta um processo de reformulação e modernização dos conhecimentos estratégicos para atingir a excelência. Percebemos, cada vez mais, que o entendimento das metas propostas possibilita uma melhor visão global de todos os recursos funcionais envolvidos. É claro que a determinação clara de objetivos agrega valor ao estabelecimento do retorno esperado a longo prazo.
                                A nível organizacional, a complexidade dos estudos efetuados prepara-nos para enfrentar situações atípicas decorrentes das diretrizes de desenvolvimento para o futuro. O cuidado em identificar pontos críticos no acompanhamento das preferências de consumo pode nos levar a considerar a reestruturação dos índices pretendidos. Evidentemente, o início da atividade geral de formação de atitudes estende o alcance e a importância do sistema de participação geral.
                                A certificação de metodologias que nos auxiliam a lidar com o desenvolvimento contínuo de distintas formas de atuação causa impacto indireto na reavaliação dos níveis de motivação departamental. Por conseguinte, a hegemonia do ambiente político exige a precisão e a definição do orçamento setorial. Nunca é demais lembrar o peso e o significado destes problemas, uma vez que a mobilidade dos capitais internacionais auxilia a preparação e a composição do sistema de formação de quadros que corresponde às necessidades. O incentivo ao avanço tecnológico, assim como a competitividade nas transações comerciais talvez venha a ressaltar a relatividade do investimento em reciclagem técnica. As experiências acumuladas demonstram que o novo modelo estrutural aqui preconizado estimula a padronização do levantamento das variáveis envolvidas.
                                Do mesmo modo, a revolução dos costumes facilita a criação das direções preferenciais no sentido do progresso. No entanto, não podemos esquecer que o consenso sobre a necessidade de qualificação representa uma abertura para a melhoria dos procedimentos normalmente adotados. Neste sentido, o desafiador cenário globalizado deve passar por modificações independentemente dos métodos utilizados na avaliação de resultados.
                                Todavia, o comprometimento entre as equipes afeta positivamente a correta previsão das posturas dos órgãos dirigentes com relação às suas atribuições. Pensando mais a longo prazo, a valorização de fatores subjetivos obstaculiza a apreciação da importância das formas de ação. É importante questionar o quanto a percepção das dificuldades não pode mais se dissociar dos modos de operação convencionais. O empenho em analisar a adoção de políticas descentralizadoras aponta para a melhoria das condições financeiras e administrativas exigidas.
                                Por outro lado, a expansão dos mercados mundiais apresenta tendências no sentido de aprovar a manutenção do fluxo de informações. Por outro lado, a necessidade de renovação processual é uma das consequências de todos os recursos funcionais envolvidos. As experiências acumuladas demonstram que o início da atividade geral de formação de atitudes nos obriga à análise do orçamento setorial. Acima de tudo, é fundamental ressaltar que a expansão dos mercados mundiais afeta positivamente a correta previsão dos níveis de motivação departamental.
                                Assim mesmo, a execução dos pontos do programa cumpre um papel essencial na formulação das direções preferenciais no sentido do progresso. Desta maneira, o desenvolvimento contínuo de distintas formas de atuação facilita a criação das diversas correntes de pensamento. Por conseguinte, o fenômeno da Internet desafia a capacidade de equalização do remanejamento dos quadros funcionais.
                                Todas estas questões, devidamente ponderadas, levantam dúvidas sobre se a percepção das dificuldades aponta para a melhoria da gestão inovadora da qual fazemos parte. Podemos já vislumbrar o modo pelo qual a contínua expansão de nossa atividade pode nos levar a considerar a reestruturação do levantamento das variáveis envolvidas. É claro que a valorização de fatores subjetivos possibilita uma melhor visão global de alternativas às soluções ortodoxas.
                """;

        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'description' must be between 1 and 4000 characters";

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
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());


        //when

        final var actualError = Assertions.assertThrows(
                DomainException.class,
                validator::validate
        );

        //then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
    }

    @Test
    public void givenNullLaunchedAt_whenCallsValidate_shouldReceiveError() {
        //given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = "A description";

        final Year expectedLaunchedAt = null;
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'launchedAt' should not be null";

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
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());


        //when

        final var actualError = Assertions.assertThrows(
                DomainException.class,
                validator::validate
        );

        //then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
    }

    @Test
    public void givenNullRating_whenCallsValidate_shouldReceiveError() {
        //given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = "A description";

        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final Rating expectedRating = null;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'rating' should not be null";

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
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());


        //when

        final var actualError = Assertions.assertThrows(
                DomainException.class,
                validator::validate
        );

        //then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
    }

}
