//package com.br.spassu.api.infrastructure.persistence.repository;
//
//import com.br.spassu.api.domain.entity.Autor;
//import com.br.spassu.api.domain.entity.Livro;
//import com.br.spassu.api.domain.repository.AutorRepository;
//import com.br.spassu.api.infrastructure.persistence.entity.AutorEntity;
//import com.br.spassu.api.infrastructure.persistence.entity.LivroEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Repository
//public class AutorRepositoryImpl implements AutorRepository {
//    private final JpaRepository<AutorEntity, Integer> autorJpaRepository;
//    private final JpaRepository<LivroEntity, Integer> livroJpaRepository;
//
//    public AutorRepositoryImpl(JpaRepository<AutorEntity, Integer> autorJpaRepository, JpaRepository<LivroEntity, Integer> livroJpaRepository) {
//        this.autorJpaRepository = autorJpaRepository;
//        this.livroJpaRepository = livroJpaRepository;
//    }
//
//    @Override
//    public Optional<Autor> findByCodigo(Integer codigo) {
//        return autorJpaRepository.findById(codigo)
//                .map(this::toDomain);
//    }
//
//    @Override
//    public List<Autor> findByLivroId(Integer livroId) {
//        return autorJpaRepository.findAll().stream()
//                .filter(entity -> entity.getLivros().stream().anyMatch(livro -> livro.getCodigo().equals(livroId)))
//                .map(this::toDomain)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<Livro> getLivrosByAutorId(Integer autorId) {
//        List<LivroEntity> livroEntities = livroJpaRepository.findAll().stream()
//                .filter(entity -> entity.getAutores().stream().anyMatch(autor -> autor.getCodigo().equals(autorId))).toList();
//
//        return livroEntities.stream()
//                .map(this::toDomainLivro)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Autor save(Autor autor) {
//        AutorEntity entity = toEntity(autor);
//        entity = autorJpaRepository.saveAndFlush(entity);
//        return toDomain(entity);
//    }
//
//    @Override
//    public void deleteById(Integer codigo) {
//        autorJpaRepository.deleteById(codigo);
//    }
//
//    @Override
//    public boolean existsById(Integer codigo) {
//        return autorJpaRepository.existsById(codigo);
//    }
//
//    private Autor toDomain(AutorEntity entity) {
//        Autor autor = new Autor();
//        autor.setCodigo(entity.getCodigo());
//        autor.setNome(entity.getNome());
//        return autor;
//    }
//
//    private AutorEntity toEntity(Autor autor) {
//        AutorEntity entity = new AutorEntity();
//        entity.setCodigo(autor.getCodigo());
//        entity.setNome(autor.getNome());
//        return entity;
//    }
//
//    private Livro toDomainLivro(LivroEntity entity) {
//        Livro livro = new Livro();
//        livro.setCodigo(entity.getCodigo());
//        livro.setTitulo(entity.getTitulo());
//        livro.setEditora(entity.getEditora());
//        livro.setEdicao(entity.getEdicao());
//        livro.setAnoPublicacao(entity.getAnoPublicacao());
//        return livro;
//    }
//}