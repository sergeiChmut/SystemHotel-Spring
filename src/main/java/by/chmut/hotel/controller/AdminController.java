package by.chmut.hotel.controller;

import by.chmut.hotel.service.DtoService;
import by.chmut.hotel.service.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Locale;

import static by.chmut.hotel.controller.constant.Constants.*;


@Controller
public class AdminController {

    private static final Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    private DtoService dtoService;

    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }



    @RequestMapping(value = "/administration")

    public String admin(Model model, Locale locale) {

        try {

            model.addAttribute(LIST_OF_CLIENTS_FOR_TODAY, dtoService.getRoomWithCheckInOrDepartureForThisDay(LocalDate.now()));

        } catch (ServiceException e) {

            logger.error(e);

            model.addAttribute(MESSAGE,
                    messageSource.getMessage(KEY_RESERVATION_PAGE_ERROR, new Object[]{}, locale));

        }

        return ADMIN_PAGE;
    }
}
