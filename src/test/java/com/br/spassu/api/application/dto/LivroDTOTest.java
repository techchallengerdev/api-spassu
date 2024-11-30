package com.br.spassu.api.application.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class LivroDTOTest {
    private static final Integer LIVRO_ID = 1;
    private static final String TITULO = "Clean Code";
    private static final String EDITORA = "Alta Books";
    private static final Integer NUMERO_EDICAO = 1;
    private static final String ANO_PUBLICACAO = "2008";
    private static final Set<Integer> IDS_AUTORES = Set.of(1, 2);
    private static final Set<Integer> IDS_ASSUNTOS = Set.of(1);

    private Validator validator;
    private LivroDTO livroDTO;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        livroDTO = criarLivroDTO();
    }

    @Test
    @DisplayName("Deve criar LivroDTO válido")
    void deveCriarLivroDTOValido() {
        // given
        LivroDTO dto = new LivroDTO();
        dto.setId(1);
        dto.setTitulo("Clean Code");
        dto.setEditora("Alta Books");
        dto.setNumeroEdicao(1);
        dto.setAnoPublicacao("2008");
        dto.setIdsAutores(new HashSet<>());
        dto.setIdsAssuntos(new HashSet<>());

        // when
        var violations = validator.validate(dto);

        // then
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve validar título obrigatório")
    void deveValidarTituloObrigatorio() {
        // given
        LivroDTO dto = new LivroDTO();
        dto.setId(1);

        // when
        var violations = validator.validate(dto);

        // then
        assertThat(violations)
                .hasSize(1)
                .anySatisfy(violation -> {
                    assertThat(violation.getPropertyPath().toString()).isEqualTo("titulo");
                    assertThat(violation.getMessage()).isEqualTo("Título é obrigatório");
                });
    }

    @Test
    @DisplayName("Deve validar tamanhos máximos")
    void deveValidarTamanhosMaximos() {
        LivroDTO dto = new LivroDTO();
        dto.setTitulo("a".repeat(41));
        dto.setEditora("b".repeat(41));
        dto.setAnoPublicacao("12345");

        var violations = validator.validate(dto);

        assertThat(violations).hasSize(3);
    }

    private void assertThatLivroEstaCorreto(LivroDTO livroDTO) {
        assertThat(livroDTO)
                .satisfies(dto -> {
                    assertThat(dto.getId()).isEqualTo(LIVRO_ID);
                    assertThat(dto.getTitulo()).isEqualTo(TITULO);
                    assertThat(dto.getEditora()).isEqualTo(EDITORA);
                    assertThat(dto.getNumeroEdicao()).isEqualTo(NUMERO_EDICAO);
                    assertThat(dto.getAnoPublicacao()).isEqualTo(ANO_PUBLICACAO);
                    assertThat(dto.getIdsAutores()).containsExactlyInAnyOrderElementsOf(IDS_AUTORES);
                    assertThat(dto.getIdsAssuntos()).containsExactlyInAnyOrderElementsOf(IDS_ASSUNTOS);
                });
    }

    @Nested
    @DisplayName("Testes de Getters e Setters")
    class GettersSettersTests {
        @Test
        @DisplayName("Deve testar todos getters")
        void deveTestarGetters() {
            assertThat(livroDTO.getId()).isEqualTo(LIVRO_ID);
            assertThat(livroDTO.getTitulo()).isEqualTo(TITULO);
            assertThat(livroDTO.getEditora()).isEqualTo(EDITORA);
            assertThat(livroDTO.getNumeroEdicao()).isEqualTo(NUMERO_EDICAO);
            assertThat(livroDTO.getAnoPublicacao()).isEqualTo(ANO_PUBLICACAO);
            assertThat(livroDTO.getIdsAutores()).isEqualTo(IDS_AUTORES);
            assertThat(livroDTO.getIdsAssuntos()).isEqualTo(IDS_ASSUNTOS);
        }

        @Test
        @DisplayName("Deve testar todos setters")
        void deveTestarSetters() {
            LivroDTO dto = new LivroDTO();
            dto.setId(LIVRO_ID);
            dto.setTitulo(TITULO);
            dto.setEditora(EDITORA);
            dto.setNumeroEdicao(NUMERO_EDICAO);
            dto.setAnoPublicacao(ANO_PUBLICACAO);
            dto.setIdsAutores(IDS_AUTORES);
            dto.setIdsAssuntos(IDS_ASSUNTOS);

            assertThatLivroEstaCorreto(dto);
        }
    }

    @Nested
    @DisplayName("Testes de Equals e HashCode")
    class EqualsHashCodeTests {
        @Test
        @DisplayName("Deve validar equals e hashCode")
        void deveValidarEqualsEHashCode() {
            // given
            LivroDTO livro1 = criarLivroDTO();
            LivroDTO livro2 = criarLivroDTO();
            LivroDTO livroDiferente = new LivroDTO();

            // then
            assertThat(livro1)
                    .isEqualTo(livro2)
                    .hasSameHashCodeAs(livro2)
                    .isNotEqualTo(livroDiferente)
                    .doesNotHaveSameHashCodeAs(livroDiferente);
        }

        @Test
        @DisplayName("Deve validar equals com null e outra classe")
        void deveValidarEqualsComNullEOutraClasse() {
            assertThat(livroDTO == null).isFalse();
            assertThat(livroDTO.equals(new Object())).isFalse();
        }

        @Test
        @DisplayName("Deve ser igual a si mesmo")
        void deveSerIgualASiMesmo() {
            // given
            LivroDTO livro = criarLivroDTO();

            // then
            assertThat(livro)
                    .isEqualTo(livro)
                    .hasSameHashCodeAs(livro);
        }

        @Test
        @DisplayName("Deve ser igual a outro livro com mesmos valores")
        void deveSerIgualAOutroLivroComMesmosValores() {
            // given
            LivroDTO livro1 = criarLivroDTO();
            LivroDTO livro2 = criarLivroDTO();

            // then
            assertThat(livro1)
                    .isEqualTo(livro2)
                    .hasSameHashCodeAs(livro2);
        }

        @Test
        @DisplayName("Não deve ser igual a null")
        void naoDeveSerIgualANull() {
            // given
            LivroDTO livro = criarLivroDTO();

            // then
            assertThat(livro).isNotEqualTo(null);
        }

        @Test
        @DisplayName("Não deve ser igual a outra classe")
        void naoDeveSerIgualAOutraClasse() {
            // given
            LivroDTO livro = criarLivroDTO();
            Object outroObjeto = new Object();

            // then
            assertThat(livro).isNotEqualTo(outroObjeto);
        }

        @Test
        @DisplayName("Deve ser diferente quando campos são diferentes")
        void deveSerDiferenteQuandoCamposSaoDiferentes() {
            // given
            LivroDTO livro1 = criarLivroDTO();

            LivroDTO livro2 = criarLivroDTO();
            livro2.setTitulo("Outro Título");

            LivroDTO livro3 = criarLivroDTO();
            livro3.setId(999);

            // then
            assertThat(livro1)
                    .isNotEqualTo(livro2)
                    .isNotEqualTo(livro3)
                    .doesNotHaveSameHashCodeAs(livro2)
                    .doesNotHaveSameHashCodeAs(livro3);
        }
    }

    private LivroDTO criarLivroDTO() {
        LivroDTO dto = new LivroDTO();
        dto.setId(LIVRO_ID);
        dto.setTitulo(TITULO);
        dto.setEditora(EDITORA);
        dto.setNumeroEdicao(NUMERO_EDICAO);
        dto.setAnoPublicacao(ANO_PUBLICACAO);
        dto.setIdsAutores(IDS_AUTORES);
        dto.setIdsAssuntos(IDS_ASSUNTOS);
        return dto;
    }

    @Nested
    @DisplayName("Testes de ToString")
    class ToStringTests {

        @Test
        @DisplayName("Deve gerar toString com todos os campos")
        void deveGerarToStringComTodosCampos() {
            // given
            LivroDTO livro = criarLivroDTO();

            // when
            String toString = livro.toString();

            // then
            assertThat(toString)
                    .contains(LIVRO_ID.toString())
                    .contains(TITULO)
                    .contains(EDITORA)
                    .contains(NUMERO_EDICAO.toString())
                    .contains(ANO_PUBLICACAO)
                    .contains(IDS_AUTORES.toString())
                    .contains(IDS_ASSUNTOS.toString());
        }

        @Test
        @DisplayName("Deve gerar toString com campos nulos")
        void deveGerarToStringComCamposNulos() {
            // given
            LivroDTO livro = new LivroDTO();

            // when
            String toString = livro.toString();

            // then
            assertThat(toString)
                    .contains("id=null")
                    .contains("titulo=null")
                    .contains("editora=null")
                    .contains("numeroEdicao=null")
                    .contains("anoPublicacao=null")
                    .contains("idsAutores=null")
                    .contains("idsAssuntos=null");
        }
    }
}