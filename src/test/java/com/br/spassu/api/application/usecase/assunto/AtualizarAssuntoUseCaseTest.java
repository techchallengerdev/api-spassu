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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para Atualizar Assunto Use Case")
class AtualizarAssuntoUseCaseTest {

    @Mock
    private AssuntoRepository assuntoRepository;

    @Mock
    private AssuntoMapper assuntoMapper;

    @InjectMocks
    private AtualizarAssuntoUseCase atualizarAssuntoUseCase;

    @Test
    @DisplayName("Deve atualizar assunto com sucesso")
    void deveAtualizarAssuntoComSucesso() {
        Integer id = 1;
        AssuntoDTO dto = criarAssuntoDTO();
        dto.setDescricao("Nova Descrição");
        Assunto assunto = criarAssunto();

        when(assuntoRepository.findByCodigo(id)).thenReturn(Optional.of(assunto));
        when(assuntoRepository.save(any(Assunto.class))).thenReturn(assunto);
        when(assuntoMapper.toDTO(assunto)).thenReturn(dto);

        AssuntoDTO resultado = atualizarAssuntoUseCase.execute(id, dto);

        assertThat(resultado)
                .isNotNull()
                .satisfies(r -> {
                    assertThat(r.getId()).isEqualTo(1);
                    assertThat(r.getDescricao()).isEqualTo("Nova Descrição");
                });

        verify(assuntoRepository).findByCodigo(id);
        verify(assuntoRepository).save(assunto);
        verify(assuntoMapper).toDTO(assunto);
    }

    @Test
    @DisplayName("Deve lançar exceção quando atualizar assunto inexistente")
    void deveLancarExcecaoQuandoAtualizarAssuntoInexistente() {
        Integer id = 1;
        AssuntoDTO dto = criarAssuntoDTO();
        when(assuntoRepository.findByCodigo(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> atualizarAssuntoUseCase.execute(id, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Assunto não encontrado");
    }

    @Test
    @DisplayName("Deve permitir atualização com campos opcionais nulos")
    void devePermitirAtualizacaoComCamposOpcionais() {
        Integer id = 1;
        Assunto assunto = criarAssunto();
        AssuntoDTO dto = new AssuntoDTO();

        when(assuntoRepository.findByCodigo(id)).thenReturn(Optional.of(assunto));
        when(assuntoRepository.save(any(Assunto.class))).thenReturn(assunto);
        when(assuntoMapper.toDTO(assunto)).thenReturn(dto);

        AssuntoDTO resultado = atualizarAssuntoUseCase.execute(id, dto);

        assertThat(resultado).isNotNull();
        verify(assuntoRepository).findByCodigo(id);
        verify(assuntoRepository).save(assunto);
        verify(assuntoMapper).toDTO(assunto);
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
