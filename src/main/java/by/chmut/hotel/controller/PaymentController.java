package by.chmut.hotel.controller;

import by.chmut.hotel.bean.Room;
import by.chmut.hotel.bean.User;
import by.chmut.hotel.service.ReservationService;
import by.chmut.hotel.service.UserService;
import by.chmut.hotel.service.validation.PaymentSender;
import by.chmut.hotel.service.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.chmut.hotel.controller.constant.Constants.*;

@Controller
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    private static final Logger logger = Logger.getLogger(PaymentController.class);

    @RequestMapping(value = "/payment")
    public String payment(HttpServletRequest req, Authentication authentication, Model model) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userService.getUser(userDetails.getUsername());

        List<Room> temporaryRooms = (List<Room>) req.getSession().getAttribute(TEMPORARY_ROOMS);

        if (makePaymentWithParams(req)) {

            setPaidStatusForReservation(user, temporaryRooms);

            removeAttributes(req.getSession());

            model.addAttribute("success", "ok");

        }

        return PAYMENT;
    }


    private boolean makePaymentWithParams(HttpServletRequest req) {

        if (req.getSession().getAttribute(TOTAL_SUM) != null) {

            PaymentSender paymentSender = new PaymentSender(req.getParameter("numCard"), req.getParameter("nameCard"),
                    req.getParameter("cvc2"));

            return paymentSender.payment().equals(SUCCESSFULLY_COMPLETED);
        }
        return false;
    }

    private void removeAttributes(HttpSession session) {
        session.removeAttribute(TEMPORARY_ROOMS);
        session.removeAttribute(CHECKIN);
        session.removeAttribute(CHECKOUT);
        session.removeAttribute(TOTAL_SUM);
    }

    private void setPaidStatusForReservation(User user, List<Room> rooms) {

        for (Room room : rooms) {
            int count = 0;
            int maxTries = 3;

            while (true) {

                try {
                    if (count == maxTries) {
                        break;
                    }
                    reservationService.setPaidStatus(user.getId(), room);

                    break;

                } catch (ServiceException e) {

                    logger.error(e);

                    count++;
                }
            }
        }
    }
}
