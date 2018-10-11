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
@RequestMapping(value = "/administration")
public class AdminController {

    private static final Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    private DtoService dtoService;

    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @RequestMapping(value = "/showclient")
    public String showClients(Model model, Locale locale) {

        model.addAttribute(SECTION,CLIENTS);

        try {

            model.addAttribute(LIST_OF_CLIENTS_FOR_TODAY, dtoService.getRoomWithCheckInOrDepartureForThisDay(LocalDate.now()));

        } catch (ServiceException e) {

            logger.error(e);

            model.addAttribute(MESSAGE,
                    messageSource.getMessage(KEY_ADMIN_PAGE_ERROR, new Object[]{}, locale));

        }

        return ADMIN_PAGE;
    }


    @RequestMapping(value = "/addRoom")
    public String addRoom(Model model) {

        model.addAttribute(SECTION, NEW_ROOM);

        return ADMIN_PAGE;
    }

    @RequestMapping(value = "/lockRoom")
    public String lockRoom(Model model) {

        model.addAttribute(SECTION, LOCK);

        return ADMIN_PAGE;
    }

}
