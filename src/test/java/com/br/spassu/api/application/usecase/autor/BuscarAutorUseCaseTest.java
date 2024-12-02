//package com.br.spassu.api.application.usecase.autor;
//
//import com.br.spassu.api.application.dto.AutorDTO;
//import com.br.spassu.api.application.mapper.AutorMapper;
//import com.br.spassu.api.domain.entity.Autor;
//import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
//import com.br.spassu.api.domain.repository.AutorRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class BuscarAutorUseCaseTest {
//
//    @Mock
//    private AutorRepository autorRepository;
//
//    @Mock
//    private AutorMapper autorMapper;
//
//    @InjectMocks
//    private BuscarAutorUseCase buscarAutorUseCase;
//
//    @Test
//    void deveBuscarAutorComSucesso() {
//        Integer id = 1;
//        Autor autor = criarAutor();
//        AutorDTO autorDTO = criarAutorDTO();
//
//        when(autorRepository.findByCodigo(id)).thenReturn(Optional.of(autor));
//        when(autorMapper.toDTO(autor)).thenReturn(autorDTO);
//
//        AutorDTO resultado = buscarAutorUseCase.execute(id);
//
//        assertThat(resultado)
//                .isNotNull()
//                .satisfies(dto -> {
//                    assertThat(dto.getId()).isEqualTo(id);
//                    assertThat(dto.getNome()).isEqualTo("Robert C. Martin");
//                });
//    }
//
//    @Test
//    void deveLancarExcecaoQuandoAutorNaoEncontrado() {
//        Integer id = 1;
//        when(autorRepository.findByCodigo(id)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> buscarAutorUseCase.execute(id))
//                .isInstanceOf(EntityNotFoundException.class)
//                .hasMessage("Autor não encontrado");
//    }
//
//    private AutorDTO criarAutorDTO() {
//        AutorDTO dto = new AutorDTO();
//        dto.setId(1);
//        dto.setNome("Robert C. Martin");
//        return dto;
//    }
//
//    private Autor criarAutor() {
//        Autor autor = new Autor();
//        autor.setCodigo(1);
//        autor.setNome("Robert C. Martin");
//        return autor;
//    }
//}
