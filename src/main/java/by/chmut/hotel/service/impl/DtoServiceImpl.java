package by.chmut.hotel.service.impl;

import by.chmut.hotel.bean.dto.RoomDto;
import by.chmut.hotel.dao.DAOException;
import by.chmut.hotel.dao.Dto;
import by.chmut.hotel.service.DtoService;
import by.chmut.hotel.service.ServiceException;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Data
public class DtoServiceImpl extends AbstractService implements DtoService {

//    private DAOFactory factory = DAOFactory.getInstance();

    private Dto roomDto;

    @Override
    public List<RoomDto> getRoomWithCheckInOrDepartureForThisDay(LocalDate date) throws ServiceException {

        List<RoomDto> result = Collections.emptyList();

        try {
            startTransaction();
            result = roomDto.getAllRoomsWhereCheckInOrCheckOutEqualsDate(date);
            commit();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return result;
    }
}
