//package com.br.spassu.api.application.usecase.autor;
//
//import com.br.spassu.api.application.dto.AutorDTO;
//import com.br.spassu.api.application.mapper.AutorMapper;
//import com.br.spassu.api.domain.entity.Autor;
//import com.br.spassu.api.domain.repository.AutorRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//@DisplayName("Testes para CriarAutorUseCase")
//class CriarAutorUseCaseTest {
//
//    @Mock
//    private AutorRepository autorRepository;
//
//    @Mock
//    private AutorMapper autorMapper;
//
//    @InjectMocks
//    private CriarAutorUseCase criarAutorUseCase;
//
//    @Nested
//    @DisplayName("Testes de sucesso")
//    class SucessoTests {
//
//        @Test
//        @DisplayName("Deve criar autor com sucesso")
//        void deveCriarAutorComSucesso() {
//            // given
//            AutorDTO dto = new AutorDTO();
//            dto.setNome("Robert C. Martin");
//
//            Autor autor = new Autor();
//            autor.setNome("Robert C. Martin");
//
//            Autor autorSalvo = new Autor();
//            autorSalvo.setCodigo(1);
//            autorSalvo.setNome("Robert C. Martin");
//
//            AutorDTO dtoSalvo = new AutorDTO();
//            dtoSalvo.setId(1);
//            dtoSalvo.setNome("Robert C. Martin");
//
//            when(autorMapper.toDomain(dto)).thenReturn(autor);
//            when(autorRepository.save(autor)).thenReturn(autorSalvo);
//            when(autorMapper.toDTO(autorSalvo)).thenReturn(dtoSalvo);
//
//            // when
//            AutorDTO resultado = criarAutorUseCase.execute(dto);
//
//            // then
//            assertThat(resultado)
//                    .isNotNull()
//                    .satisfies(r -> {
//                        assertThat(r.getId()).isEqualTo(1);
//                        assertThat(r.getNome()).isEqualTo("Robert C. Martin");
//                    });
//
//            verify(autorMapper).toDomain(dto);
//            verify(autorRepository).save(any(Autor.class));
//            verify(autorMapper).toDTO(any(Autor.class));
//        }
//    }
//}
