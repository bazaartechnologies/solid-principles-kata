package com.bazaar.api.order.service.impl;

import com.amazonaws.services.mq.model.NotFoundException;
import com.bazaar.api.common.constant.UserChannel;
import com.bazaar.api.common.constant.catalog.CustomerChannel;
import com.bazaar.api.common.constant.order.OrderChannel;
import com.bazaar.api.common.event.order.dto.FulfillmentOrderEvent;
import com.bazaar.api.common.event.order.dto.OrderSummaryDto;
import com.bazaar.api.common.event.order.dto.TaxDto;
import com.bazaar.api.fulfilment.event.outbound.OrderFulfilledEventAvroPayload;
import com.bazaar.api.order.config.event.OrderEventItemsAvroDto;
import com.bazaar.api.order.config.event.OrderSummaryAvroDto;
import com.bazaar.api.order.constant.FulfilmentStatus;
import com.bazaar.api.order.constant.OrderPriceBreakdownSubType;
import com.bazaar.api.order.constant.OrderPriceBreakdownType;
import com.bazaar.api.order.constant.OrderStatus;
import com.bazaar.api.order.constant.OrganisationId;
import com.bazaar.api.order.controller.dto.AssistOrderDto;
import com.bazaar.api.order.controller.dto.AvailedGift;
import com.bazaar.api.order.controller.dto.CreateOrderConfigDto;
import com.bazaar.api.order.controller.dto.CustomerAppFetchOrderDto;
import com.bazaar.api.order.controller.dto.CustomerAppOrderDetailDto;
import com.bazaar.api.order.controller.dto.CustomerOrderMetricsDto;
import com.bazaar.api.order.controller.dto.CustomerOrdersCountDto;
import com.bazaar.api.order.controller.dto.DeliveryDateDto;
import com.bazaar.api.order.controller.dto.FetchOrderDetailDto;
import com.bazaar.api.order.controller.dto.FetchOrderDto;
import com.bazaar.api.order.controller.dto.FetchOrderRequestDto;
import com.bazaar.api.order.controller.dto.IdValuePairDto;
import com.bazaar.api.order.controller.dto.LocusCollectionOrderResponseDto;
import com.bazaar.api.order.controller.dto.LocusCollectionsFiltersRequestDto;
import com.bazaar.api.order.controller.dto.LocusOrderDetailsResponseDto;
import com.bazaar.api.order.controller.dto.LocusOrderDetailsResponseDtoV3;
import com.bazaar.api.order.controller.dto.LocusOrderFiltersRequestDto;
import com.bazaar.api.order.controller.dto.LocusOrderFiltersRequestDtoV2;
import com.bazaar.api.order.controller.dto.OpenOrderRequestDto;
import com.bazaar.api.order.controller.dto.OrderConfigurationDto;
import com.bazaar.api.order.controller.dto.OrderCreateDtoV2;
import com.bazaar.api.order.controller.dto.OrderDeliveryDetailsUpdateDto;
import com.bazaar.api.order.controller.dto.OrderDiscountDetailsDto;
import com.bazaar.api.order.controller.dto.OrderItemBatchDto;
import com.bazaar.api.order.controller.dto.OrderItemDto;
import com.bazaar.api.order.controller.dto.OrderItemPriceResponseDto;
import com.bazaar.api.order.controller.dto.OrderItemPriceValidationRequestDto;
import com.bazaar.api.order.controller.dto.OrderStatusUpdateDto;
import com.bazaar.api.order.controller.dto.OrderUpdateDto;
import com.bazaar.api.order.controller.dto.OrderUpdatedItemDto;
import com.bazaar.api.order.controller.dto.OrdersAndCustomerDetailResponseDto;
import com.bazaar.api.order.controller.dto.OrdersDetailResponseDto;
import com.bazaar.api.order.controller.dto.OrdersResponseDto;
import com.bazaar.api.order.controller.dto.ServiceChargesDto;
import com.bazaar.api.order.controller.dto.UpdateDeliveryDateDto;
import com.bazaar.api.order.controller.dto.UpdateMovDto;
import com.bazaar.api.order.controller.dto.WheelSpreadSheetResponseDto;
import com.bazaar.api.order.event.outbound.OrderEventProducer;
import com.bazaar.api.order.event.outbound.OrderEventProducerKT;
import com.bazaar.api.order.exception.expected.MinimumOrderFailedException;
import com.bazaar.api.order.exception.expected.NegativeProductDiscountException;
import com.bazaar.api.order.exception.expected.OrderAmountUnderWalletThresholdException;
import com.bazaar.api.order.exception.expected.OrderDeliveryDetailsNotFoundException;
import com.bazaar.api.order.exception.expected.OrderInLockedStateException;
import com.bazaar.api.order.exception.expected.OrderNotFoundException;
import com.bazaar.api.order.exception.unexpected.CreateOrderConfigFailedException;
import com.bazaar.api.order.exception.unexpected.FailedToFetchOrderDetail;
import com.bazaar.api.order.exception.unexpected.FailedToFetchOrders;
import com.bazaar.api.order.exception.unexpected.FailedToFetchOrdersByProductIds;
import com.bazaar.api.order.exception.unexpected.FailedToUpdateDeliveryDate;
import com.bazaar.api.order.exception.unexpected.FailedToUpdateOrderItemBatchException;
import com.bazaar.api.order.exception.unexpected.FetchLocusOrdersFailedException;
import com.bazaar.api.order.exception.unexpected.FetchOrderFailedException;
import com.bazaar.api.order.exception.unexpected.FetchOrdersFailedException;
import com.bazaar.api.order.exception.unexpected.InvalidOrderStatusException;
import com.bazaar.api.order.exception.unexpected.OrderConfigurationFailedException;
import com.bazaar.api.order.exception.unexpected.OrderCreationFailedException;
import com.bazaar.api.order.exception.unexpected.OrderDeliveryDateFailedException;
import com.bazaar.api.order.exception.unexpected.OrderUpdateFailedException;
import com.bazaar.api.order.exception.unexpected.UpdateMovFailedDueToNotFoundException;
import com.bazaar.api.order.exception.unexpected.UpdateMovFailedException;
import com.bazaar.api.order.gateway.InventoryServiceClient;
import com.bazaar.api.order.gateway.SpreadSheetClient;
import com.bazaar.api.order.interceptor.dto.TokenDetailsDto;
import com.bazaar.api.order.model.Order;
import com.bazaar.api.order.model.OrderConfiguration;
import com.bazaar.api.order.model.OrderCustomer;
import com.bazaar.api.order.model.OrderDeliveryDetails;
import com.bazaar.api.order.model.OrderEvent;
import com.bazaar.api.order.model.OrderItem;
import com.bazaar.api.order.model.OrderItemBatch;
import com.bazaar.api.order.model.OrderPricingBreakdown;
import com.bazaar.api.order.repository.OrderConfigurationRepository;
import com.bazaar.api.order.repository.OrderDeliveryDetailsRepository;
import com.bazaar.api.order.repository.OrderEventRepository;
import com.bazaar.api.order.repository.OrderItemBatchRepository;
import com.bazaar.api.order.repository.OrderItemRepository;
import com.bazaar.api.order.repository.OrderPricingBreakdownRepository;
import com.bazaar.api.order.repository.OrderRepository;
import com.bazaar.api.order.service.OrderService;
import com.bazaar.api.order.service.OrderValidator;
import com.bazaar.api.order.service.mapper.OrderMapper;
import com.bazaar.api.order.util.DateUtil;
import com.bazaar.api.order.util.RequestUtil;
import com.bazaar.notitfication.sdk.NotificationClient;
import com.bazaar.notitfication.sdk.constant.ChannelType;
import com.bazaar.notitfication.sdk.dto.NotificationPayloadDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.common.header.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.bazaar.api.common.constant.order.OrderChannel.of;
import static com.bazaar.api.common.util.BazaarUtil.randomNumbers;
import static com.bazaar.api.order.constant.OrderEventName.CREATED_AT;
import static com.bazaar.api.order.constant.OrderEventName.MODIFIED_AT;
import static com.bazaar.api.order.constant.OrderStatus.CANCELLED;
import static com.bazaar.api.order.constant.OrderStatus.CLOSED;
import static com.bazaar.api.order.constant.OrderStatus.INVALID;
import static com.bazaar.api.order.constant.OrderStatus.OPEN;
import static com.bazaar.api.order.controller.dto.OrderUpdateDto.getDeliveryDate;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.apache.commons.lang3.StringUtils.isAllBlank;

@Deprecated
@Service
@Transactional(transactionManager = "orderTransactionManager", readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final String bazaarGoogleSpreadSheetKey;
    private final String BazaarSystemUserId;

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
    private static final String SORT_BY_CREATED_AT = "createdAt";
    private static final DateTimeFormatter ORDER_NUMBER_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyDDD");
    private static final DateTimeFormatter AVAILED_GIFT_DATE_FORMATTER = DateTimeFormatter.ofPattern("d-MM-yyyy");
    private static final ZoneId LOCAL_ZONE = ZoneId.of("Asia/Karachi");
    private static final Double PRIME_MOV = 500.0;

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderEventRepository orderEventRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderConfigurationRepository orderConfigurationRepository;
    private final OrderPricingBreakdownRepository orderPricingBreakdownRepository;
    private final OrderDeliveryDetailsRepository existingOrderDeliveryDetailsRepository;
    private final OrderItemBatchRepository orderItemBatchRepository;

    private final OrderEventProducer orderEventProducer;
    private final OrderEventProducerKT orderEventProducerMSK;
    private final TransactionTemplate transactionTemplate;
    private final InventoryServiceClient inventoryServiceClient;
    private final SpreadSheetClient spreadSheetClient;
    private final DateUtil dateUtil;
    private final NotificationClient notificationClient;
    private final String pinCodeSmsTemplate;

    private static final String EXCEPTION_MESSAGE = "An exception occurred while getting order detail for " +
            "orderId={}";

    private final double platformFees;
    private final double pharmaMinOrderValue;

    private final double industrialMinOrderValue;
    private final double defaultMinOrderValue;

    @Autowired
    public OrderServiceImpl(
            OrderMapper orderMapper,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            OrderPricingBreakdownRepository orderPricingBreakdownRepository,
            OrderItemBatchRepository orderItemBatchRepository,
            OrderEventRepository orderEventRepository,
            OrderConfigurationRepository orderConfigurationRepository,
            OrderDeliveryDetailsRepository existingOrderDeliveryDetailsRepository,
            OrderEventProducer orderEventProducer,
            OrderEventProducerKT orderEventProducerMSK,
            @Qualifier("orderTransactionTemplate") TransactionTemplate transactionTemplate,
            InventoryServiceClient inventoryServiceClient,
            SpreadSheetClient spreadSheetClient,
            @Value("${bazaar_google_wheel_sheet_api_key}") String googleWheelSheet,
            DateUtil dateUtil,
            NotificationClient notificationClient,
            @Value("${order.pincode.sms.template}") String pinCodeSmsTemplate,
            @Value("${bazaar.system.user.id}") String systemUserId,
            @Value("${bazaar.service_charges.platform_fees}") double platformFees,
            @Value("${bazaar.order.mov.pharma}") Double pharmaMinOrderValue,
            @Value("${bazaar.order.mov.industrial}") Double industrialMinOrderValue,
            @Value("${bazaar.order.mov.default}") Double defaultMinOrderValue) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderEventRepository = orderEventRepository;
        this.orderPricingBreakdownRepository = orderPricingBreakdownRepository;
        this.orderItemBatchRepository = orderItemBatchRepository;
        this.orderConfigurationRepository = orderConfigurationRepository;
        this.existingOrderDeliveryDetailsRepository = existingOrderDeliveryDetailsRepository;
        this.orderEventProducer = orderEventProducer;
        this.orderEventProducerMSK = orderEventProducerMSK;
        this.transactionTemplate = transactionTemplate;
        this.inventoryServiceClient = inventoryServiceClient;
        this.spreadSheetClient = spreadSheetClient;
        this.bazaarGoogleSpreadSheetKey = googleWheelSheet;
        this.dateUtil = dateUtil;
        this.notificationClient = notificationClient;
        this.pinCodeSmsTemplate = pinCodeSmsTemplate;
        this.BazaarSystemUserId = systemUserId;
        this.platformFees = platformFees;
        this.pharmaMinOrderValue = pharmaMinOrderValue;
        this.industrialMinOrderValue = industrialMinOrderValue;
        this.defaultMinOrderValue = defaultMinOrderValue;
    }

    @Override
    public List<FetchOrderDto> getOrders(FetchOrderRequestDto fetchOrderRequestDto) {
        try {

            List<Order> orderEntities;
            if (fetchOrderRequestDto.isSearchByPhoneNumber()) {
                orderEntities = orderRepository.findAllByCustomerPhoneNumber(fetchOrderRequestDto.getCriteria(), fetchOrderRequestDto.phoneNumberPageable());
            } else if (StringUtils.isNumeric(fetchOrderRequestDto.getCriteria()) || fetchOrderRequestDto.getCriteria() == null || fetchOrderRequestDto.getCriteria().isEmpty()) {
                orderEntities = orderRepository.findAllByOrderNumber(fetchOrderRequestDto.getCriteria(), fetchOrderRequestDto.defaultPageable());
            } else {
                orderEntities = orderRepository.findAllByCustomerName(fetchOrderRequestDto.getCriteria(), fetchOrderRequestDto.defaultPageable());
            }

            return orderEntities.stream().map(order -> {
                OrderCustomer orderCustomer = order.getOrderCustomer();
                return new FetchOrderDto(
                        order.getId(),
                        order.getOrderNumber(),
                        order.getCreatedAt(),
                        orderCustomer.getCustomerStoreName(),
                        order.getAmountTotal(),
                        order.getChannel(),
                        order.getStatus(),
                        order.getIsLocked());
            }).collect(toList());
        } catch (Exception ex) {
            logger.error("An exception occurred fetching orders for [fetchOrderRequestDto={}]", fetchOrderRequestDto.toString());
            throw new FetchOrdersFailedException(ex);
        }
    }

}