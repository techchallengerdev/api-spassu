package com.br.spassu.api.application.usecase.assunto;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.application.mapper.AssuntoMapper;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para CriarAssuntoUseCase")
class CriarAssuntoUseCaseTest {

    @Mock
    private AssuntoRepository assuntoRepository;

    @Mock
    private AssuntoMapper assuntoMapper;

    @InjectMocks
    private CriarAssuntoUseCase criarAssuntoUseCase;

    @Nested
    @DisplayName("Testes de sucesso")
    class SucessoTests {

        @Test
        @DisplayName("Deve criar assunto com sucesso")
        void deveCriarAssuntoComSucesso() {
            // given
            AssuntoDTO dto = new AssuntoDTO();
            dto.setDescricao("Programação");

            Assunto assunto = new Assunto();
            assunto.setDescricao("Programação");

            Assunto assuntoSalvo = new Assunto();
            assuntoSalvo.setCodigo(1);
            assuntoSalvo.setDescricao("Programação");

            AssuntoDTO dtoSalvo = new AssuntoDTO();
            dtoSalvo.setId(1);
            dtoSalvo.setDescricao("Programação");

            when(assuntoMapper.toDomain(dto)).thenReturn(assunto);
            when(assuntoRepository.save(assunto)).thenReturn(assuntoSalvo);
            when(assuntoMapper.toDTO(assuntoSalvo)).thenReturn(dtoSalvo);

            // when
            AssuntoDTO resultado = criarAssuntoUseCase.execute(dto);

            // then
            assertThat(resultado)
                    .isNotNull()
                    .satisfies(r -> {
                        assertThat(r.getId()).isEqualTo(1);
                        assertThat(r.getDescricao()).isEqualTo("Programação");
                    });

            verify(assuntoMapper).toDomain(dto);
            verify(assuntoRepository).save(any(Assunto.class));
            verify(assuntoMapper).toDTO(any(Assunto.class));
        }
    }

    @Nested
    @DisplayName("Testes de validação")
    class ValidacaoTests {

        @Test
        @DisplayName("Deve validar campos obrigatórios")
        void deveValidarCamposObrigatorios() {
            // given
            AssuntoDTO dto = new AssuntoDTO();
            Assunto assunto = new Assunto();

            when(assuntoMapper.toDomain(dto)).thenReturn(assunto);
            when(assuntoRepository.save(assunto)).thenReturn(assunto);
            when(assuntoMapper.toDTO(assunto)).thenReturn(dto);

            // when
            AssuntoDTO resultado = criarAssuntoUseCase.execute(dto);

            // then
            assertThat(resultado).isNotNull();
            verify(assuntoRepository).save(any(Assunto.class));
        }
    }
}
