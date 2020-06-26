package com.mbsystems.reactivebackend.controller;

import com.mbsystems.reactivebackend.domain.Quote;
import com.mbsystems.reactivebackend.repository.QuoteMongoReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class QuoteReactiveController {

    private static final int DELAY_PER_ITEM_MS = 100;

    private final QuoteMongoReactiveRepository quoteMongoReactiveRepository;

    @Autowired
    public QuoteReactiveController( final QuoteMongoReactiveRepository quoteMongoReactiveRepository ) {
        this.quoteMongoReactiveRepository = quoteMongoReactiveRepository;
    }

    @GetMapping( "/quotes-reactive" )
    public Flux<Quote> getQuoteFlux() {
        return quoteMongoReactiveRepository.findAll().delayElements( Duration.ofMillis( DELAY_PER_ITEM_MS ) );
    }

    @GetMapping( "/quotes-reactive-paged" )
    public Flux<Quote> getQuoteFlux( final @RequestParam( name = "page" ) int page,
                                     final @RequestParam( name = "size" ) int size ) {
        return quoteMongoReactiveRepository.findAllByIdNotNullOrderByIdAsc( PageRequest.of( page, size ) )
                .delayElements( Duration.ofMillis( DELAY_PER_ITEM_MS ) );
    }
}
