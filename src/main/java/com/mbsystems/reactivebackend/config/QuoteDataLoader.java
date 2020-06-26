package com.mbsystems.reactivebackend.config;

import com.mbsystems.reactivebackend.domain.Quote;
import com.mbsystems.reactivebackend.repository.QuoteMongoReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Supplier;

@Component
@Slf4j
public class QuoteDataLoader implements ApplicationRunner {

    private final QuoteMongoReactiveRepository quoteMongoReactiveRepository;

    @Autowired
    public QuoteDataLoader( QuoteMongoReactiveRepository quoteMongoReactiveRepository ) {
        this.quoteMongoReactiveRepository = quoteMongoReactiveRepository;
    }

    @Override
    public void run( ApplicationArguments args ) {
        if (quoteMongoReactiveRepository.count().block() == 0L) {
            var idSupplier = getIdSequenceSupplier();
            var bufferedReader = new BufferedReader(
                    new InputStreamReader(getClass()
                            .getClassLoader()
                            .getResourceAsStream("pg2000.txt"))
            );
            Flux.fromStream( bufferedReader.lines()
                                        .filter( lineRead -> !lineRead.trim().isEmpty() )
                                        .map( lineRd -> quoteMongoReactiveRepository
                                                            .save( new Quote( idSupplier.get(), "El Quote", lineRd ) ) ) )
                    .subscribe( m -> log.info( "new quote loaded: {}", m.block() ) );

            log.info( "Repository contains now {} entries.", quoteMongoReactiveRepository.count().block() );

        }
    }

    private Supplier<String> getIdSequenceSupplier() {
        return new Supplier<>() {
            Long l = 0L;

            @Override
            public String get() {
                return String.format( "%05d", l );
            }
        };
    }
}
