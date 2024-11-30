package com.br.spassu.api.infrastructure.persistence.repository.assunto;

import com.br.spassu.api.infrastructure.persistence.entity.AssuntoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssuntoJpaRepository extends JpaRepository<AssuntoEntity, Integer> {
}
