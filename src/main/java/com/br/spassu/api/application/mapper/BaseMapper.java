package com.br.spassu.api.application.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class BaseMapper {
    @Lazy
    protected final LivroMapper livroMapper;
    @Lazy
    protected final AutorMapper autorMapper;
    @Lazy
    protected final AssuntoMapper assuntoMapper;
}