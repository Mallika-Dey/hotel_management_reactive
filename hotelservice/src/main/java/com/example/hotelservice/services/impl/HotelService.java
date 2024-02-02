package com.example.hotelservice.services.impl;

import com.example.hotelservice.dto.request.CreateHotelRequestDTO;
import com.example.hotelservice.dto.request.LocationDto;
import com.example.hotelservice.dto.response.HotelResponseDTO;
import com.example.hotelservice.entity.Hotel;
import com.example.hotelservice.entity.Location;
import com.example.hotelservice.repositories.HotelRepository;
import com.example.hotelservice.repositories.LocationRepository;
import com.example.hotelservice.services.IHotelService;
import com.example.hotelservice.validator.HotelValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.example.hotelservice.utils.Constants.EMPTY_ROOM;
import static com.example.hotelservice.utils.Constants.ROOM_PRICE_ZERO;

/**
 * Service class for managing Hotel.
 * Author: K M Farhat Snigdah & Mallika Dey
 */
@Service
public class HotelService implements IHotelService {
    private static final Logger logger = LoggerFactory.getLogger(RoomTypeService.class);
    private final HotelRepository hotelRepository;
    private final HotelValidator hotelValidator;
    private final LocationRepository locationRepository;

    /**
     * Constructs a HotelService with the provided HotelRepository.
     *
     * @param hotelRepository the repository for room types
     */
    public HotelService(HotelRepository hotelRepository, HotelValidator hotelValidator, LocationRepository locationRepository) {
        this.hotelRepository = hotelRepository;
        this.hotelValidator = hotelValidator;
        this.locationRepository = locationRepository;
    }

    @Override
    public Mono<Hotel> createHotel(CreateHotelRequestDTO hotelRequestDTO) {
        return hotelValidator
                .hotelCreateValidation(hotelRequestDTO)
                .flatMap(location -> {
                    return hotelRepository.save(mapToEntity(hotelRequestDTO, location.getId()));
                })
                .doOnRequest(l -> logger.debug("Hotel Create start processing"))
                .doOnSuccess(hotel -> logger.info("Hotel {} saved successfully", hotel.getName()));
    }

    @Override
    public Mono<HotelResponseDTO> getHotelDetails(String hotelName) {
        return hotelValidator.hotelResponseValidation(hotelName)
                .flatMap(hotel -> {
                    return Mono.just(hotelResponseBuilder(hotel));
                })
                .doOnRequest(l -> logger.debug("Hotel response start processing"))
                .doOnSuccess(hotel -> logger.info("Hotel {} saved successfully", hotel.getHotelName()));
    }

    private HotelResponseDTO hotelResponseBuilder(Hotel hotel){
        Mono<Location> locationMono =  locationRepository.findById(hotel.getLocId());
       return mapToHotelDto(hotel, locationMono.block());
    }
    private HotelResponseDTO mapToHotelDto(Hotel hotel, Location location){
        return HotelResponseDTO
                .builder()
                .hotelName(hotel.getName())
                .locName(location.getDistrict())
                .amenities(hotel.getAmenities())
                .minPrice(hotel.getMinPrice())
                .maxPrice(hotel.getMaxPrice())
                .availability(hotel.getAvailability())
                .build();
    }


    private Hotel mapToEntity(CreateHotelRequestDTO createHotelRequestDTO, Integer locId) {
        return Hotel
                .builder()
                .locId(locId)
                .name(createHotelRequestDTO.getName())
                .amenities(createHotelRequestDTO.getAmenities())
                .availability(EMPTY_ROOM)
                .maxPrice(ROOM_PRICE_ZERO)
                .minPrice(ROOM_PRICE_ZERO)
                .build();
    }
}
