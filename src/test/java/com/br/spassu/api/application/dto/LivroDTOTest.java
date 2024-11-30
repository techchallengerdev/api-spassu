//package com.br.spassu.api.application.dto;
//
//import jakarta.validation.Validation;
//import jakarta.validation.Validator;
//import jakarta.validation.ValidatorFactory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ExtendWith(MockitoExtension.class)
//class LivroDTOTest {
//    private static final Integer LIVRO_ID = 1;
//    private static final String TITULO = "Clean Code";
//    private static final String EDITORA = "Alta Books";
//    private static final Integer NUMERO_EDICAO = 1;
//    private static final String ANO_PUBLICACAO = "2008";
//    private static final Set<Integer> IDS_AUTORES = Set.of(1, 2);
//    private static final Set<Integer> IDS_ASSUNTOS = Set.of(1);
//
//    private Validator validator;
//
//    @BeforeEach
//    void setUp() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//    }
//
//    @Test
//    void deveCriarLivroDTOValido() {
//        LivroDTO livroDTO = criarLivroDTO();
//        var violations = validator.validate(livroDTO);
//        assertThat(violations).isEmpty();
//        assertThatLivroEstaCorreto(livroDTO);
//    }
//
//    private LivroDTO criarLivroDTO() {
//        LivroDTO livroDTO = new LivroDTO();
//        livroDTO.setId(LIVRO_ID);
//        livroDTO.setTitulo(TITULO);
//        livroDTO.setEditora(EDITORA);
//        livroDTO.setNumeroEdicao(NUMERO_EDICAO);
//        livroDTO.setAnoPublicacao(ANO_PUBLICACAO);
//        livroDTO.setIdsAutores(IDS_AUTORES);
//        livroDTO.setIdsAssuntos(IDS_ASSUNTOS);
//        return livroDTO;
//    }
//
//    private void assertThatLivroEstaCorreto(LivroDTO livroDTO) {
//        assertThat(livroDTO)
//                .satisfies(dto -> {
//                    assertThat(dto.getId()).isEqualTo(LIVRO_ID);
//                    assertThat(dto.getTitulo()).isEqualTo(TITULO);
//                    assertThat(dto.getEditora()).isEqualTo(EDITORA);
//                    assertThat(dto.getNumeroEdicao()).isEqualTo(NUMERO_EDICAO);
//                    assertThat(dto.getAnoPublicacao()).isEqualTo(ANO_PUBLICACAO);
//                    assertThat(dto.getIdsAutores()).containsExactlyInAnyOrderElementsOf(IDS_AUTORES);
//                    assertThat(dto.getIdsAssuntos()).containsExactlyInAnyOrderElementsOf(IDS_ASSUNTOS);
//                });
//    }
//}