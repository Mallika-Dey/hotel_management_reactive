package com.example.inventoryservice.services.impl;

import com.example.inventoryservice.dto.request.CreateHotelRoomDTO;
import com.example.inventoryservice.dto.request.CreateRoomBookDTO;
import com.example.inventoryservice.dto.response.CheckHotelResponseDTO;
import com.example.inventoryservice.entity.HotelDetails;
import com.example.inventoryservice.entity.RoomBook;
import com.example.inventoryservice.logic.InventoryLogic;
import com.example.inventoryservice.mapper.InventoryMapper;
import com.example.inventoryservice.repositories.RoomBookRepository;
import com.example.inventoryservice.services.IHotelRoomService;
import com.example.inventoryservice.validator.HotelRoomValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class HotelRoomService implements IHotelRoomService {

    private final InventoryMapper inventoryMapper;
    private final InventoryLogic inventoryLogic;
    private final HotelRoomValidator hotelRoomValidator;
    private final RoomBookRepository roomBookRepository;
    private static final Logger logger = LoggerFactory.getLogger(HotelRoomService.class);

    public HotelRoomService(InventoryMapper inventoryMapper,
                            InventoryLogic inventoryLogic, HotelRoomValidator hotelRoomValidator, RoomBookRepository roomBookRepository) {
        this.inventoryMapper = inventoryMapper;
        this.inventoryLogic = inventoryLogic;
        this.hotelRoomValidator = hotelRoomValidator;
        this.roomBookRepository = roomBookRepository;
    }

    public Mono<HotelDetails> createHotelRoom(CreateHotelRoomDTO createHotelRoomDTO) {

        return inventoryMapper.buildInternalCallRequest(
                        createHotelRoomDTO.getHotelName(), createHotelRoomDTO.getRoomType())
                .flatMap(hotelResponse -> {
                    return inventoryLogic.dbOperation(hotelResponse, createHotelRoomDTO);
                })
                .doOnRequest(l -> logger.debug("Hotel Create start processing"))
                .doOnSuccess(hotel -> {
                    logger.info("Hotel info update in hotel microservice");
                });
    }

    public Flux<RoomBook> createRoomBooked(CreateRoomBookDTO createRoomBookDTO) {
        Mono<HotelDetails> hotelDetailsMono = roomBookValidation(createRoomBookDTO);
        Flux<RoomBook> data = hotelDetailsMono
                .flatMapMany(response -> findBookedRooms(response, createRoomBookDTO));
        Mono<Boolean> isPossible = hotelDetailsMono
                .map(response -> isRoomBookPossible(response.getRoomCount(), createRoomBookDTO, data));

        return data;
    }

    private Boolean isRoomBookPossible(Integer roomCount,
                                       CreateRoomBookDTO createRoomBookDTO, Flux<RoomBook> data) {
        int[] count = new int[64];

        data.collectList().map(roomBooks -> {
            return roomBooks.stream().map(roomBook -> {
                roomAssign(count, roomBook, createRoomBookDTO);
                return null;
            });
        });

        int start = createRoomBookDTO.getStartDate().getDayOfMonth();
        int end = createRoomBookDTO.getEndDate().getDayOfMonth();
        while (start <= end) {
            count[start] += count[start - 1];
            if (count[start] == roomCount)
                return false;
            start++;
        }
        return true;
    }

    private void roomAssign(int[] count, RoomBook roomBook, CreateRoomBookDTO roomBookDTO) {
        LocalDate startDate = roomBookDTO.getStartDate();
        LocalDate endDate = roomBookDTO.getEndDate();

        LocalDate lDate = (startDate.isBefore(roomBook.getStartDate())) ?
                roomBook.getStartDate() : startDate;

        LocalDate rDate = (endDate.isBefore(roomBook.getEndDate())) ?
                endDate : roomBook.getEndDate();

        if (startDate.getMonth().equals(lDate.getMonth()))
            count[lDate.getDayOfMonth() + 1]++;
        else count[startDate.lengthOfMonth() + 1]++;

        if (lDate.getMonth().equals(rDate.getMonth()))
            count[rDate.getDayOfMonth() + 2]--;
        else
            count[lDate.lengthOfMonth() + rDate.getDayOfMonth() + 2]--;
    }

    private Flux<RoomBook> findBookedRooms(HotelDetails response, CreateRoomBookDTO createRoomBookDTO) {
        return roomBookRepository.findAvailableRoomBooks(response.getHotelId(), createRoomBookDTO.getStartDate(), createRoomBookDTO.getEndDate());
    }

    public Mono<HotelDetails> roomBookValidation(CreateRoomBookDTO createRoomBookDTO) {
        return inventoryMapper.buildInternalCallRequest(
                createRoomBookDTO.getHotelName(), createRoomBookDTO.getRoomType()
        ).flatMap(hotelAndRoomID -> {
            return hotelRoomValidator.isHotelRoomExists(hotelAndRoomID.getHotelId(), hotelAndRoomID.getRoomTypeId());
        });
    }
}
