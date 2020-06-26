package com.mbsystems.reactivebackend.repository;

import com.mbsystems.reactivebackend.domain.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteMongoBlockingRepository extends PagingAndSortingRepository<Quote, String> {

    List<Quote> findAllByIdNotNullOrderByIdAsc( final Pageable page );
}
