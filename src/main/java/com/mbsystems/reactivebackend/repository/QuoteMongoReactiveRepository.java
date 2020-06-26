package com.mbsystems.reactivebackend.repository;

import com.mbsystems.reactivebackend.domain.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface QuoteMongoReactiveRepository extends ReactiveSortingRepository<Quote, String> {

    Flux<Quote> findAllByIdNotNullOrderByIdAsc( final Pageable page );
}
