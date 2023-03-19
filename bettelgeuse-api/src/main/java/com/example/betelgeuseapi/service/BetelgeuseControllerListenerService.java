package com.example.betelgeuseapi.service;

import com.example.betelgeuseapi.model.contract.BetelgeuseController;
import com.example.betelgeuseapi.model.contract.BetelgeuseControllerImpl;
import io.reactivex.Flowable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class BetelgeuseControllerListenerService {
    private final Web3j web3j;
    private final SPService spService;
    private final NFTService nftService;
    private final TokenCriteriaCompletionsService tokenCriteriaCompletionsService;

    @Value("${web3j.betelgeuse-contract-address}")
    private String contractAddress;

    @Value("${web3j.betelgeuse-contract-spa-registered-event-signature}")
    private String spaRegisteredEventSignature;

    @Value("${web3j.betelgeuse-contract-mint-event-signature}")
    private String mintEventSignature;
    @Value("${web3j.betelgeuse-contract-pushXp-event-signature}")
    private String xpPushedEventSignature;
    @Value("${web3j.betelgeuse-contract-start-block}")
    private Long startBlock;

    /**
     * This method obtains the current block number by calling the `ethBlockNumber()` method of the injected Web3j object.
     * If an error occurs during the process, it throws a RuntimeException.
     *
     * @return the current block number
     */
    public BigInteger getCurrentBlockNumber() {
        try {
            final EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().send();

            return ethBlockNumber.getBlockNumber();
        } catch (IOException e) {
            throw new RuntimeException("Error when obtaining the current block number", e);
        }
    }


    private static final BigInteger NODE_LIMIT = BigInteger.valueOf(2000 - 1);


    /**
     * This method registers a listener to the Betelgeuse smart contract for the events
     * of new Service Providers registration and new NFT token minting. The registered listener will
     * listen to events that occurred between the starting block specified in the configuration file
     * and the current block. It will retrieve the events in chunks of 2000 blocks to avoid overloading
     * the node. The retrieved events will be filtered and processed to extract the relevant information
     * (i.e., the new registered Service Providers and the newly minted NFT tokens). This information
     * will then be stored in the database. Finally, the listener will keep running and will listen for
     * new events as they occur in real-time.
     *
     * @return void
     */
    @PostConstruct
    @Transactional
    protected void register() {

        ReadonlyTransactionManager transactionManager = new ReadonlyTransactionManager(web3j, contractAddress);
        DefaultGasProvider contractGasProvider = new DefaultGasProvider();


        BetelgeuseControllerImpl BetelgeuseController = new BetelgeuseControllerImpl(contractAddress, web3j, transactionManager, contractGasProvider);

        BigInteger currentBlockNumber = getCurrentBlockNumber();
        BigInteger start = BigInteger.valueOf(startBlock);


        while (currentBlockNumber.subtract(start).compareTo(NODE_LIMIT) > 0) {
            EthFilter filter = new EthFilter(new DefaultBlockParameterNumber(start), new DefaultBlockParameterNumber(start.add(NODE_LIMIT)), contractAddress);
            filter.addSingleTopic(spaRegisteredEventSignature);
            Flowable<com.example.betelgeuseapi.model.contract.BetelgeuseController.ServiceProviderCreatedEventResponse> responseFlowable = BetelgeuseController.serviceProviderCreatedEventFlowable(filter);
            filter(filter, responseFlowable);

            EthFilter filter1 = new EthFilter(new DefaultBlockParameterNumber(start), new DefaultBlockParameterNumber(start.add(NODE_LIMIT)), contractAddress);
            filter1.addSingleTopic(mintEventSignature);
            Flowable<BetelgeuseController.TokenPurchasedEventResponse> tokenPurchasedEventResponseFlowable = BetelgeuseController.tokenPurchasedEventFlowable(filter1);
            filter1(filter, tokenPurchasedEventResponseFlowable);

            EthFilter filter2 = new EthFilter(new DefaultBlockParameterNumber(start), new DefaultBlockParameterNumber(start.add(NODE_LIMIT)), contractAddress);
            filter2.addSingleTopic(xpPushedEventSignature);
            Flowable<BetelgeuseController.XpPushedEventResponse> xpPushedEventResponse = BetelgeuseController.xpPushedEventFlowable(filter2);
            filter2(filter2, xpPushedEventResponse);



            start = start.add(NODE_LIMIT);
        }

        EthFilter filter = new EthFilter(new DefaultBlockParameterNumber(start), DefaultBlockParameterName.LATEST, contractAddress);
        filter.addSingleTopic(spaRegisteredEventSignature);
        Flowable<com.example.betelgeuseapi.model.contract.BetelgeuseController.ServiceProviderCreatedEventResponse> responseFlowable = BetelgeuseController.serviceProviderCreatedEventFlowable(filter);
        filter(filter, responseFlowable);

        EthFilter filter1 = new EthFilter(new DefaultBlockParameterNumber(start), DefaultBlockParameterName.LATEST, contractAddress);
        filter1.addSingleTopic(mintEventSignature);
        Flowable<BetelgeuseController.TokenPurchasedEventResponse> tokenPurchasedEventResponseFlowable = BetelgeuseController.tokenPurchasedEventFlowable(filter1);
        filter1(filter, tokenPurchasedEventResponseFlowable);

        EthFilter filter2 = new EthFilter(new DefaultBlockParameterNumber(start), new DefaultBlockParameterNumber(start.add(NODE_LIMIT)), contractAddress);
        filter2.addSingleTopic(xpPushedEventSignature);
        Flowable<BetelgeuseController.XpPushedEventResponse> xpPushedEventResponse = BetelgeuseController.xpPushedEventFlowable(filter2);
        filter2(filter2, xpPushedEventResponse);

    }

    private void filter2(EthFilter filter2, Flowable<BetelgeuseController.XpPushedEventResponse> responseFlowable) {
        responseFlowable.subscribe(event -> {
            if (event != null) {
                log.info("received event: {}", event);
                tokenCriteriaCompletionsService.registerCompletion(event);
            }
        }, error -> {
            log.error("Error while listening for events: {}", error);
        });
    }

    private void filter1(EthFilter filter, Flowable<BetelgeuseController.TokenPurchasedEventResponse> responseFlowable) {
        responseFlowable.subscribe(event -> {
            if (event != null) {
                log.info("received event: {}", event);
                nftService.register(event.tokenId, event.serviceProviderId, event.userAddress, event.rank, event.uri);
            }
        }, error -> {
            log.error("Error while listening for events: {}", error);
        });
    }


    private void filter(EthFilter filter, Flowable<BetelgeuseController.ServiceProviderCreatedEventResponse> responseFlowable) {
        responseFlowable.subscribe(event -> {
            if (event != null) {
                log.info("received event: {}", event);
                spService.register(event.provider, event.ranks, event.criterias);
            }
        }, error -> {
            log.error("Error while listening for events: {}", error);
        });
    }
//
//    protected void filter() {
//        EthFilter filter = new EthFilter(
//                new DefaultBlockParameterNumber(BigInteger.valueOf(startBlock)), DefaultBlockParameterName.LATEST, contractAddress);
//        filter.addSingleTopic(spaRegisteredEventSignature);
//
//        Flowable<com.example.betelgeuseapi.model.contract.BetelgeuseController.ServiceProviderCreatedEventResponse> responseFlowable = BetelgeuseController.serviceProviderCreatedEventFlowable(filter);
//
//        responseFlowable.subscribe(event -> {
//            log.info("received event: {}", event);
//            spService.register(event.provider, event.ranks, event.criterias);
//        }, error -> {
//            log.error("Error while listening for events: {}", error);
//        });
//    }
}
