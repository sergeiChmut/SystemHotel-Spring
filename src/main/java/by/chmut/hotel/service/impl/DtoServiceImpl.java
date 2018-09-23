package by.chmut.hotel.service.impl;

import by.chmut.hotel.bean.dto.RoomDto;
import by.chmut.hotel.dao.impl.RoomDtoImpl;
import by.chmut.hotel.service.DtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class DtoServiceImpl implements DtoService {

    @Autowired
    private RoomDtoImpl roomDto;

    @Override
    public List<RoomDto> getRoomWithCheckInOrDepartureForThisDay(LocalDate date) {

        List<RoomDto> result = roomDto.getAllRoomsWhereCheckInOrCheckOutEqualsDate(date);

        if (result == null) {
            result = Collections.emptyList();
        }

        return result;
    }
}
