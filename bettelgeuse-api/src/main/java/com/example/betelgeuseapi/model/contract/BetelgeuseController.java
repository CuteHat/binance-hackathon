package com.example.betelgeuseapi.model.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.2.
 */
@SuppressWarnings("rawtypes")
public class BetelgeuseController extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_NFT = "NFT";

    public static final String FUNC_CRITERIAID = "criteriaId";

    public static final String FUNC_CRITERIAS = "criterias";

    public static final String FUNC_LEVELUP = "levelUp";

    public static final String FUNC_MINT = "mint";

    public static final String FUNC_PUSHXP = "pushXp";

    public static final String FUNC_RANKID = "rankId";

    public static final String FUNC_RANKS = "ranks";

    public static final String FUNC_REGISTERPROVIDER = "registerProvider";

    public static final String FUNC_SERVICEPROVIDERID = "serviceProviderId";

    public static final String FUNC_SERVICEPROVIDERS = "serviceProviders";

    public static final String FUNC_TOKENID = "tokenId";

    public static final String FUNC_TOKENRANKS = "tokenRanks";

    public static final String FUNC_XPS = "xps";

    public static final Event SERVICEPROVIDERCREATED_EVENT = new Event("ServiceProviderCreated",
            Arrays.<TypeReference<?>>asList(new TypeReference<ServiceProvider>() {
            }, new TypeReference<DynamicArray<Rank>>() {
            }, new TypeReference<DynamicArray<Criteria>>() {
            }));
    ;

    public static final Event TOKENLEVELEDUP_EVENT = new Event("TokenLeveledUp",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Utf8String>() {
            }));
    ;

    public static final Event TOKENPURCHASED_EVENT = new Event("TokenPurchased",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Utf8String>() {
            }));
    ;

    public static final Event XPPUSHED_EVENT = new Event("XpPushed",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {
            }, new TypeReference<Uint256>(true) {
            }, new TypeReference<Uint256>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));
    ;

    @Deprecated
    protected BetelgeuseController(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected BetelgeuseController(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected BetelgeuseController(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected BetelgeuseController(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ServiceProviderCreatedEventResponse> getServiceProviderCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SERVICEPROVIDERCREATED_EVENT, transactionReceipt);
        ArrayList<ServiceProviderCreatedEventResponse> responses = new ArrayList<ServiceProviderCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ServiceProviderCreatedEventResponse typedResponse = new ServiceProviderCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.provider = (ServiceProvider) eventValues.getNonIndexedValues().get(0);
            typedResponse.ranks = (List<Rank>) ((Array) eventValues.getNonIndexedValues().get(1)).getValue();
            typedResponse.criterias = (List<Criteria>) ((Array) eventValues.getNonIndexedValues().get(2)).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ServiceProviderCreatedEventResponse> serviceProviderCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ServiceProviderCreatedEventResponse>() {
            @Override
            public ServiceProviderCreatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SERVICEPROVIDERCREATED_EVENT, log);
                ServiceProviderCreatedEventResponse typedResponse = new ServiceProviderCreatedEventResponse();
                typedResponse.log = log;
                typedResponse.provider = (ServiceProvider) eventValues.getNonIndexedValues().get(0);
                typedResponse.ranks = (List<Rank>) ((Array) eventValues.getNonIndexedValues().get(1)).getValue();
                typedResponse.criterias = (List<Criteria>) ((Array) eventValues.getNonIndexedValues().get(2)).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ServiceProviderCreatedEventResponse> serviceProviderCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SERVICEPROVIDERCREATED_EVENT));
        return serviceProviderCreatedEventFlowable(filter);
    }

    public static List<TokenLeveledUpEventResponse> getTokenLeveledUpEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TOKENLEVELEDUP_EVENT, transactionReceipt);
        ArrayList<TokenLeveledUpEventResponse> responses = new ArrayList<TokenLeveledUpEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TokenLeveledUpEventResponse typedResponse = new TokenLeveledUpEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.serviceProviderId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.tokenRank = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.uri = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TokenLeveledUpEventResponse> tokenLeveledUpEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TokenLeveledUpEventResponse>() {
            @Override
            public TokenLeveledUpEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TOKENLEVELEDUP_EVENT, log);
                TokenLeveledUpEventResponse typedResponse = new TokenLeveledUpEventResponse();
                typedResponse.log = log;
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.serviceProviderId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.tokenRank = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.uri = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TokenLeveledUpEventResponse> tokenLeveledUpEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOKENLEVELEDUP_EVENT));
        return tokenLeveledUpEventFlowable(filter);
    }

    public static List<TokenPurchasedEventResponse> getTokenPurchasedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TOKENPURCHASED_EVENT, transactionReceipt);
        ArrayList<TokenPurchasedEventResponse> responses = new ArrayList<TokenPurchasedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TokenPurchasedEventResponse typedResponse = new TokenPurchasedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.userAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.serviceProviderId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.rank = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.uri = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TokenPurchasedEventResponse> tokenPurchasedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TokenPurchasedEventResponse>() {
            @Override
            public TokenPurchasedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TOKENPURCHASED_EVENT, log);
                TokenPurchasedEventResponse typedResponse = new TokenPurchasedEventResponse();
                typedResponse.log = log;
                typedResponse.userAddress = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.serviceProviderId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.rank = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.uri = (String) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TokenPurchasedEventResponse> tokenPurchasedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOKENPURCHASED_EVENT));
        return tokenPurchasedEventFlowable(filter);
    }

    public static List<XpPushedEventResponse> getXpPushedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(XPPUSHED_EVENT, transactionReceipt);
        ArrayList<XpPushedEventResponse> responses = new ArrayList<XpPushedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            XpPushedEventResponse typedResponse = new XpPushedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.serviceProviderId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.criteriaId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.added = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.totalValue = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<XpPushedEventResponse> xpPushedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, XpPushedEventResponse>() {
            @Override
            public XpPushedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(XPPUSHED_EVENT, log);
                XpPushedEventResponse typedResponse = new XpPushedEventResponse();
                typedResponse.log = log;
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.serviceProviderId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.criteriaId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.added = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.totalValue = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<XpPushedEventResponse> xpPushedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(XPPUSHED_EVENT));
        return xpPushedEventFlowable(filter);
    }

    public RemoteFunctionCall<String> NFT() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NFT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> criteriaId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CRITERIAID,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple6<BigInteger, String, String, BigInteger, BigInteger, BigInteger>> criterias(BigInteger param0, BigInteger param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CRITERIAS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0),
                        new org.web3j.abi.datatypes.generated.Uint256(param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }, new TypeReference<Utf8String>() {
                }, new TypeReference<Utf8String>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }));
        return new RemoteFunctionCall<Tuple6<BigInteger, String, String, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple6<BigInteger, String, String, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple6<BigInteger, String, String, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<BigInteger, String, String, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (String) results.get(2).getValue(),
                                (BigInteger) results.get(3).getValue(),
                                (BigInteger) results.get(4).getValue(),
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> levelUp(BigInteger _tokenId, BigInteger _serviceProviderId, List<BigInteger> _serviceProviderRankingIds, String uri) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_LEVELUP,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenId),
                        new org.web3j.abi.datatypes.generated.Uint256(_serviceProviderId),
                        new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                                org.web3j.abi.datatypes.generated.Uint256.class,
                                org.web3j.abi.Utils.typeMap(_serviceProviderRankingIds, org.web3j.abi.datatypes.generated.Uint256.class)),
                        new org.web3j.abi.datatypes.Utf8String(uri)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> mint(BigInteger _serviceProviderId, String uri) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MINT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_serviceProviderId),
                        new org.web3j.abi.datatypes.Utf8String(uri)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> pushXp(BigInteger _tokenId, BigInteger _serviceProviderId, BigInteger _criteriaId, BigInteger value) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PUSHXP,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenId),
                        new org.web3j.abi.datatypes.generated.Uint256(_serviceProviderId),
                        new org.web3j.abi.datatypes.generated.Uint256(_criteriaId),
                        new org.web3j.abi.datatypes.generated.Uint256(value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> rankId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_RANKID,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple4<BigInteger, BigInteger, BigInteger, String>> ranks(BigInteger param0, BigInteger param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_RANKS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0),
                        new org.web3j.abi.datatypes.generated.Uint256(param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Utf8String>() {
                }));
        return new RemoteFunctionCall<Tuple4<BigInteger, BigInteger, BigInteger, String>>(function,
                new Callable<Tuple4<BigInteger, BigInteger, BigInteger, String>>() {
                    @Override
                    public Tuple4<BigInteger, BigInteger, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, BigInteger, BigInteger, String>(
                                (BigInteger) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue(),
                                (String) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> registerProvider(String _name, String _description, String _websiteURL, List<Criteria> _criterias, List<Rank> _ranks) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REGISTERPROVIDER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name),
                        new org.web3j.abi.datatypes.Utf8String(_description),
                        new org.web3j.abi.datatypes.Utf8String(_websiteURL),
                        new org.web3j.abi.datatypes.DynamicArray<Criteria>(Criteria.class, _criterias),
                        new org.web3j.abi.datatypes.DynamicArray<Rank>(Rank.class, _ranks)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> serviceProviderId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SERVICEPROVIDERID,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple7<BigInteger, String, String, String, String, BigInteger, Boolean>> serviceProviders(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SERVICEPROVIDERS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }, new TypeReference<Utf8String>() {
                }, new TypeReference<Address>() {
                }, new TypeReference<Utf8String>() {
                }, new TypeReference<Utf8String>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Bool>() {
                }));
        return new RemoteFunctionCall<Tuple7<BigInteger, String, String, String, String, BigInteger, Boolean>>(function,
                new Callable<Tuple7<BigInteger, String, String, String, String, BigInteger, Boolean>>() {
                    @Override
                    public Tuple7<BigInteger, String, String, String, String, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<BigInteger, String, String, String, String, BigInteger, Boolean>(
                                (BigInteger) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (String) results.get(2).getValue(),
                                (String) results.get(3).getValue(),
                                (String) results.get(4).getValue(),
                                (BigInteger) results.get(5).getValue(),
                                (Boolean) results.get(6).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> tokenId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOKENID,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> tokenRanks(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOKENRANKS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> xps(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_XPS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static BetelgeuseController load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new BetelgeuseController(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static BetelgeuseController load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BetelgeuseController(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static BetelgeuseController load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new BetelgeuseController(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static BetelgeuseController load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new BetelgeuseController(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class ServiceProvider extends DynamicStruct {
        public BigInteger id;

        public String name;

        public String owner;

        public String description;

        public String websiteURL;

        public BigInteger registeredTimestamp;

        public Boolean isActive;

        public ServiceProvider(BigInteger id, String name, String owner, String description, String websiteURL, BigInteger registeredTimestamp, Boolean isActive) {
            super(new org.web3j.abi.datatypes.generated.Uint256(id),
                    new org.web3j.abi.datatypes.Utf8String(name),
                    new org.web3j.abi.datatypes.Address(160, owner),
                    new org.web3j.abi.datatypes.Utf8String(description),
                    new org.web3j.abi.datatypes.Utf8String(websiteURL),
                    new org.web3j.abi.datatypes.generated.Uint256(registeredTimestamp),
                    new org.web3j.abi.datatypes.Bool(isActive));
            this.id = id;
            this.name = name;
            this.owner = owner;
            this.description = description;
            this.websiteURL = websiteURL;
            this.registeredTimestamp = registeredTimestamp;
            this.isActive = isActive;
        }

        public ServiceProvider(Uint256 id, Utf8String name, Address owner, Utf8String description, Utf8String websiteURL, Uint256 registeredTimestamp, Bool isActive) {
            super(id, name, owner, description, websiteURL, registeredTimestamp, isActive);
            this.id = id.getValue();
            this.name = name.getValue();
            this.owner = owner.getValue();
            this.description = description.getValue();
            this.websiteURL = websiteURL.getValue();
            this.registeredTimestamp = registeredTimestamp.getValue();
            this.isActive = isActive.getValue();
        }
    }

    public static class Rank extends DynamicStruct {
        public BigInteger id;

        public BigInteger level;

        public BigInteger xpFrom;

        public String name;

        public Rank(BigInteger id, BigInteger level, BigInteger xpFrom, String name) {
            super(new org.web3j.abi.datatypes.generated.Uint256(id),
                    new org.web3j.abi.datatypes.generated.Uint256(level),
                    new org.web3j.abi.datatypes.generated.Uint256(xpFrom),
                    new org.web3j.abi.datatypes.Utf8String(name));
            this.id = id;
            this.level = level;
            this.xpFrom = xpFrom;
            this.name = name;
        }

        public Rank(Uint256 id, Uint256 level, Uint256 xpFrom, Utf8String name) {
            super(id, level, xpFrom, name);
            this.id = id.getValue();
            this.level = level.getValue();
            this.xpFrom = xpFrom.getValue();
            this.name = name.getValue();
        }
    }

    public static class Criteria extends DynamicStruct {
        public BigInteger id;

        public String name;

        public String description;

        public BigInteger weight;

        public BigInteger min;

        public BigInteger max;

        public Criteria(BigInteger id, String name, String description, BigInteger weight, BigInteger min, BigInteger max) {
            super(new org.web3j.abi.datatypes.generated.Uint256(id),
                    new org.web3j.abi.datatypes.Utf8String(name),
                    new org.web3j.abi.datatypes.Utf8String(description),
                    new org.web3j.abi.datatypes.generated.Uint256(weight),
                    new org.web3j.abi.datatypes.generated.Uint256(min),
                    new org.web3j.abi.datatypes.generated.Uint256(max));
            this.id = id;
            this.name = name;
            this.description = description;
            this.weight = weight;
            this.min = min;
            this.max = max;
        }

        public Criteria(Uint256 id, Utf8String name, Utf8String description, Uint256 weight, Uint256 min, Uint256 max) {
            super(id, name, description, weight, min, max);
            this.id = id.getValue();
            this.name = name.getValue();
            this.description = description.getValue();
            this.weight = weight.getValue();
            this.min = min.getValue();
            this.max = max.getValue();
        }
    }

    public static class ServiceProviderCreatedEventResponse extends BaseEventResponse {
        public ServiceProvider provider;

        public List<Rank> ranks;

        public List<Criteria> criterias;
    }

    public static class TokenLeveledUpEventResponse extends BaseEventResponse {
        public BigInteger tokenId;

        public BigInteger serviceProviderId;

        public BigInteger tokenRank;

        public String uri;
    }

    public static class TokenPurchasedEventResponse extends BaseEventResponse {
        public String userAddress;

        public BigInteger tokenId;

        public BigInteger serviceProviderId;

        public BigInteger rank;

        public String uri;
    }

    public static class XpPushedEventResponse extends BaseEventResponse {
        public BigInteger tokenId;

        public BigInteger serviceProviderId;

        public BigInteger criteriaId;

        public BigInteger added;

        public BigInteger totalValue;
    }
}