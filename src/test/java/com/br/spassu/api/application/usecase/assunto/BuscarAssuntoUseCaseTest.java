package com.br.spassu.api.application.usecase.assunto;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.application.mapper.AssuntoMapper;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para Buscar Assunto Use Case")
class BuscarAssuntoUseCaseTest {

    @Mock
    private AssuntoRepository assuntoRepository;

    @Mock
    private AssuntoMapper assuntoMapper;

    @InjectMocks
    private BuscarAssuntoUseCase buscarAssuntoUseCase;

    @Test
    @DisplayName("Deve buscar assunto com sucesso")
    void deveBuscarAssuntoComSucesso() {
        Integer id = 1;
        AssuntoDTO dto = criarAssuntoDTO();
        Assunto assunto = criarAssunto();

        when(assuntoRepository.findByCodigo(id)).thenReturn(Optional.of(assunto));
        when(assuntoMapper.toDTO(assunto)).thenReturn(dto);

        AssuntoDTO resultado = buscarAssuntoUseCase.execute(id);

        assertThat(resultado)
                .isNotNull()
                .satisfies(r -> {
                    assertThat(r.getId()).isEqualTo(1);
                    assertThat(r.getDescricao()).isEqualTo("Programação");
                });

        verify(assuntoRepository).findByCodigo(id);
        verify(assuntoMapper).toDTO(assunto);
    }

    @Test
    @DisplayName("Deve lançar exceção quando assunto não encontrado")
    void deveLancarExcecaoQuandoAssuntoNaoEncontrado() {
        Integer id = 1;
        when(assuntoRepository.findByCodigo(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> buscarAssuntoUseCase.execute(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Assunto não encontrado");
    }

    private AssuntoDTO criarAssuntoDTO() {
        AssuntoDTO dto = new AssuntoDTO();
        dto.setId(1);
        dto.setDescricao("Programação");
        return dto;
    }

    private Assunto criarAssunto() {
        Assunto assunto = new Assunto();
        assunto.setCodigo(1);
        assunto.setDescricao("Programação");
        return assunto;
    }
}
