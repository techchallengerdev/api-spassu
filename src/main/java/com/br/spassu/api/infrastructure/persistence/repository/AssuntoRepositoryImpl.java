//package com.br.spassu.api.infrastructure.persistence.repository;
//
//import com.br.spassu.api.domain.entity.Assunto;
//import com.br.spassu.api.domain.repository.AssuntoRepository;
//import com.br.spassu.api.infrastructure.persistence.entity.AssuntoEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Repository
//public class AssuntoRepositoryImpl implements AssuntoRepository {
//    private final JpaRepository<AssuntoEntity, Long> jpaRepository;
//
//    public AssuntoRepositoryImpl(JpaRepository<AssuntoEntity, Long> jpaRepository) {
//        this.jpaRepository = jpaRepository;
//    }
//
//    @Override
//    public List<Assunto> findAll() {
//        return jpaRepository.findAll().stream()
//                .map(this::toDomain)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public void deleteByCodigo(Integer codigo) {
//        jpaRepository.deleteById(Long.valueOf(codigo));
//    }
//
//    @Override
//    public boolean existsByCodigo(Integer codigo) {
//        return jpaRepository.existsById(Long.valueOf(codigo));
//    }
//
//    @Override
//    public Assunto save(Assunto assunto) {
//        AssuntoEntity entity = toEntity(assunto);
//        entity = jpaRepository.saveAndFlush(entity);
//        return toDomain(entity);
//    }
//
//    @Override
//    public Optional<Assunto> findByCodigo(Integer codigo) {
//        return jpaRepository.findById(Long.valueOf(codigo))
//                .map(this::toDomain);
//    }
//
//    private Assunto toDomain(AssuntoEntity entity) {
//        Assunto assunto = new Assunto();
//        assunto.setCodigo(entity.getCodigo());
//        assunto.setDescricao(entity.getDescricao());
//        return assunto;
//    }
//
//    private AssuntoEntity toEntity(Assunto assunto) {
//        AssuntoEntity entity = new AssuntoEntity();
//        entity.setCodigo(assunto.getCodigo());
//        entity.setDescricao(assunto.getDescricao());
//        return entity;
//    }
//}