package it.unisa.is.c09.digitaldonation.web;

import it.unisa.is.c09.digitaldonation.ErroreManagement.OrganizzazioneSeduteError.CannotLoadDataRepositoryException;
import it.unisa.is.c09.digitaldonation.ErroreManagement.OrganizzazioneSeduteError.CannotRelaseFeedbackException;
import it.unisa.is.c09.digitaldonation.ErroreManagement.OrganizzazioneSeduteError.CannotSaveDataRepositoryException;
import it.unisa.is.c09.digitaldonation.Model.Entity.*;
import it.unisa.is.c09.digitaldonation.OrganizzazioneSeduteManagement.OrganizzazioneSeduteService;
import it.unisa.is.c09.digitaldonation.UtenteManagement.MailSingletonSender;
import it.unisa.is.c09.digitaldonation.UtenteManagement.UtenteService;
import it.unisa.is.c09.digitaldonation.Utils.Forms.GuestForm;
import it.unisa.is.c09.digitaldonation.Utils.Forms.GuestFormValidate;
import it.unisa.is.c09.digitaldonation.Utils.Forms.SedutaForm;
import it.unisa.is.c09.digitaldonation.Utils.Forms.SedutaFormValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class OrganizzazioneSeduteController {

    private static Logger logger = Logger.getLogger(String.valueOf(OrganizzazioneSeduteController.class));

    @Autowired
    OrganizzazioneSeduteService organizzazioneSeduteService;
    @Autowired
    UtenteService utenteService;
    @Autowired
    GuestFormValidate guestFormValidate;
    @Autowired
    SedutaFormValidate sedutaFormValidate;

    /**
     * Metodo che permette al donatore di poter inviare un feedback.
     *
     * @param request è la richiesta dalla sessione.
     * @param redirectAttribute è l'attributo di ridirezione.
     * @param model è l'oggetto Model.
     * @return String ridirezione ad una pagina.
     */
    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public String feedbackDonatore(HttpServletRequest request, RedirectAttributes redirectAttribute, Model model,
                                   @RequestParam(name= "feedbackSeduta") String feedbackSeduta, @RequestParam(name="idSeduta") Long idSeduta) {
        if(feedbackSeduta.equals("positivo")) {
            Donatore utente = (Donatore) request.getSession().getAttribute("utente");
            if (utente != null) {
                try {
                    organizzazioneSeduteService.feedbackDonatore(utente, idSeduta);
                    model.addAttribute("success", "Ti sei prenotato alla seduta, controlla la tua email.");
                    return "GUIGestioneUtente/dashboardDonatore";
                } catch (CannotRelaseFeedbackException e) {
                    return "GUIGestioneUtente/dashboardDonatore";
                }
            }
        }
        return "redirect:/dashboardDonatore";
    }

    /**
     * Metodo che permette all'operatore di poter visualizzare una seduta con l'elenco dei partecipanti.
     *
     * @param request è la richiesta dalla sessione.
     * @param redirectAttribute è l'attributo di ridirezione.
     * @param model è l'oggetto Model.
     * @return String ridirezione ad una pagina.
     */
    @RequestMapping(value = "/monitoraggioSeduta", method = RequestMethod.GET)
    public String monitoraggioSeduta(HttpServletRequest request, RedirectAttributes redirectAttribute, Model model) {
        Utente utente = (Utente) model.getAttribute("utente");
        Long idSeduta = (Long) model.getAttribute("idSeduta");
        if (utente instanceof Operatore) {
            try {
                ArrayList<Object> listaPartecipanti = organizzazioneSeduteService.monitoraggioSeduta(idSeduta);
                model.addAttribute("listaPartecipanti", listaPartecipanti);
                List<Seduta> listaSedute = (List<Seduta>) model.getAttribute("listaSedutePrenotabili");
                for (int i = 0; i < listaSedute.size(); i++) {
                    if (listaSedute.get(i).getIdSeduta() == idSeduta) {
                        model.addAttribute("sedutaScelta", listaSedute.get(i));
                    }
                }
                return "GUIOrganizzazioneSedute/elencoPartecipanti";
            } catch (CannotLoadDataRepositoryException e) {
                return "errorsPages/error503";
            }
        } else
            return "redirect:/";
    }

    /**
     * Metodo che permette all'operatore di poter visualizzare l'elenco delle sedute.
     *
     * @param request è la richiesta dalla sessione.
     * @param model   è l'oggetto Model.
     * @return String ridirezione ad una pagina.
     */
    @RequestMapping(value = "/visualizzaElencoSedute", method = RequestMethod.GET)
    public String visualizzaElencoSedute(HttpServletRequest request, Model model) {
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        if (utente instanceof Operatore) {
            try {
                List<Seduta> lista = organizzazioneSeduteService.visualizzaElencoSedute();
                model.addAttribute("listaSedute", lista);
            } catch (CannotLoadDataRepositoryException e) {
                e.printStackTrace();
            }
        } else {
            return "redirect:/";
        }
        return "GUIOrganizzazioneSedute/monitoraggioSedute";
    }

    /**
     * Metodo che permette di andare alla pagina dell'elenco dei partecipanti.
     *
     * @param model è l'oggetto model.
     * @return String ridirezione alla pagina delle sedute disponibile.
     */
    @RequestMapping(value = "/goSeduteDisponibili", method = RequestMethod.GET)
    public String seduteDisponibili(HttpServletRequest request,Model model) {
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        if (utente instanceof Donatore){
            try{
                List<Seduta> lista = organizzazioneSeduteService.visualizzaElencoSeduteDisponibili(utente.getCodiceFiscale());
                model.addAttribute("listaSedutePrenotabili", lista);
            } catch (CannotLoadDataRepositoryException e) {
                e.printStackTrace();
            }
        } else {
            return "redirect:/";
            }
        return "GUIOrganizzazioneSedute/seduteDisponibili";
        }

    /**
     * Metodo che permette al donatore di poter visualizzare una seduta.
     *
     * @param request è la richiesta dalla sessione.
     * @param model   è l'oggetto Model.
     * @return String ridirezione ad una pagina.
     */
    @RequestMapping(value = "/visualizzaSeduta", method = RequestMethod.GET)
    public String visualizzaSeduta(HttpServletRequest request, Model model) {
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        if (utente instanceof Donatore) {
            Long idSeduta = (Long) model.getAttribute("idSeduta");
            List<Seduta> lista = (List<Seduta>) model.getAttribute("listaSedutePrenotabili");
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getIdSeduta() == idSeduta) {
                    model.addAttribute("sedutaScelta", lista.get(i));
                }
            }
            return "GUIOrganizzazioneSedute/partecipaSeduta";
        } else {
            return "redirect:/";
        }
    }

    /**
     * Metodo che permette di andare alla pagina dell'elenco dei partecipanti.
     *
     * @param model è l'oggetto model.
     * @return String ridirezione alla pagina.
     */
    @RequestMapping(value = "/goElencoPartecipanti", method = RequestMethod.GET)
    public String elencoPartecipanti(Model model, HttpServletRequest request) {
        long idSeduta = Long.valueOf(request.getParameter("idSeduta"));
        String successo = request.getParameter("successo");
            model.addAttribute("idSeduta" , idSeduta);
        try {
            ArrayList<Object> list = organizzazioneSeduteService.monitoraggioSeduta(idSeduta);
            model.addAttribute("listaUtenti", list);
            //Array list per le instanza della lista 1/true = Donatore 0/false= Guest
            ArrayList<Boolean> partecipanti = new ArrayList<>();
            if (list.size() > 0) {

                for (Object o : list) {
                    if (o instanceof Donatore) {
                        partecipanti.add(true);
                    } else {
                        partecipanti.add(false);
                    }
                }
            } else {
                partecipanti.add(0, null);
            }
            model.addAttribute("success", successo);
            model.addAttribute("listaPartecipanti", partecipanti);
        } catch (CannotLoadDataRepositoryException e) {
            e.printStackTrace();
        }

        return "GUIOrganizzazioneSedute/elencoPartecipanti";
    }


    /**
     * Metodo che permette di andare alla pagina dell'elenco delle sedute.
     *
     * @param model è l'oggetto model.
     * @return String ridirezione alla pagina.
     */
    @RequestMapping(value = "/goMonitoraggioSedute", method = RequestMethod.GET)
    public String monitoraggioSedute(Model model) {
        return "GUIOrganizzazioneSedute/monitoraggioSedute";
    }

    /**
     * Metodo che permette di andare alla pagina di feedback della seduta.
     *
     * @param model è l'oggetto model.
     * @return String ridirezione alla pagina delle sedute disponibile.
     */
    @RequestMapping(value = "/goPartecipaSeduta", method = RequestMethod.GET)
    public String partecipaSeduta(HttpServletRequest request, Model model) {
        Long idSeduta = Long.valueOf(request.getParameter("idSeduta"));
        model.addAttribute("idSeduta" , idSeduta);

        try {
            Seduta seduta = organizzazioneSeduteService.visualizzaSeduta(idSeduta);
            model.addAttribute("seduta", seduta);
            return "GUIOrganizzazioneSedute/partecipaSeduta";

        } catch (CannotLoadDataRepositoryException e) {
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
            return "redirect:/error";
        }
    }

    /**
     * Metodo che permette di andare alla pagina di schedulazione di una nuova seduta.
     *
     * @param model è l'oggetto model.
     * @return String ridirezione alla pagina delle sedute disponibile.
     */
    @RequestMapping(value = "/goSchedulazioneSeduta", method = RequestMethod.GET)
    public String schedulazioneSeduta(Model model) {
        if (model.getAttribute("sedutaForm") != null) {
            return "GUIOrganizzazioneSedute/schedulazioneSeduta";
        }
        SedutaForm sedutaForm = new SedutaForm();
        model.addAttribute("sedutaForm", sedutaForm);
        return "GUIOrganizzazioneSedute/schedulazioneSeduta";
    }

    /**
     * Metodo che permette di andare alla pagina di inserimento utente guest.
     *
     * @param model è l'oggetto model.
     * @return String ridirezione alla pagina delle sedute disponibile.
     */
    @RequestMapping(value = "/goInserimentoUtenteGuest", method = RequestMethod.GET)
    public String inserimentoUtenteGuest(HttpServletRequest request, @ModelAttribute("guestForm")
        GuestForm guestForm, BindingResult result, RedirectAttributes redirectAttribute, Model model) {

        long idSeduta = Long.valueOf(request.getParameter("idSeduta"));
        model.addAttribute("idSeduta", idSeduta);
        return "GUIOrganizzazioneSedute/inserimentoUtenteGuest";
    }

    /**
     * Metodo che permette all'operatore di poter inserire un utente guest all'interno di una seduta.
     *
     * @param request           è la richiesta dalla sessione.
     * @param guestForm         è l'oggetto form dell'utente guest.
     * @param redirectAttribute è l'attributo di ridirezione.
     * @param model             è l'oggetto Model.
     * @param result            è la variabile di binding.
     * @return String ridirezione ad una pagina.
     */
    @RequestMapping(value = "/inserimentoGuest", method = RequestMethod.POST)
    public String inserimentoGuest(HttpServletRequest request, @ModelAttribute GuestForm guestForm, BindingResult
            result, RedirectAttributes redirectAttribute, Model model) {
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        long idSeduta = Long.valueOf(request.getParameter("idSeduta"));
        guestFormValidate.validate(guestForm, result);
        if (result.hasErrors()) {
            redirectAttribute.addFlashAttribute("guestForm", guestForm);
            for (ObjectError x : result.getGlobalErrors()) {
                redirectAttribute.addFlashAttribute(x.getCode(), x.getDefaultMessage());
            }
            return "redirect:/goInserimentoUtenteGuest?idSeduta=" + idSeduta ;
        }
        if (utente instanceof Operatore) {
            Guest guest = new Guest();
            guest.setNome(guestForm.getNome());
            guest.setCognome(guestForm.getCognome());
            guest.setcodiceFiscaleGueste(guestForm.getCodiceFiscale());
            guest.setPatologie(guestForm.getPatologie());
            guest.setTelefono(guestForm.getTelefono());
            guest.setGruppoSanguigno(guestForm.getGruppoSanguigno());
            try {
                organizzazioneSeduteService.inserimentoGuest(idSeduta, guest);
                return  "redirect:/goElencoPartecipanti?idSeduta=" + idSeduta + "&successo=" + "Utente guest inserito correttamente";
            } catch (CannotSaveDataRepositoryException e) {
                redirectAttribute.addFlashAttribute(e.getTarget(), e.getMessage());
                return "redirect:/goInserimentoUtenteGuest?idSeduta=" + idSeduta;
            }
        } else
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.UNAUTHORIZED);
        return "redirect:/error";

    }

    /**
     * Metodo che permette all'operatore di poter creare una nuova seduta.
     *
     * @param request           è la richiesta dalla sessione.
     * @param sedutaForm        è l'oggetto form della seduta.
     * @param redirectAttribute è l'attributo di ridirezione.
     * @param model             è l'oggetto Model.
     * @return String ridirezione ad una pagina.
     */
    @RequestMapping(value = "/schedulazioneSeduta", method = RequestMethod.POST)
    public String schedulazioneSeduta(HttpServletRequest request, @ModelAttribute SedutaForm sedutaForm, RedirectAttributes redirectAttribute, BindingResult result, Model model) {
        Utente utente = (Utente) request.getSession().getAttribute("utente");

        try {
            sedutaFormValidate.validate(sedutaForm, result);
            if (result.hasErrors()) {
                // se ci sono errori il metodo controller setta tutti i parametri
                redirectAttribute.addFlashAttribute("sedutaForm", sedutaForm);
                for (ObjectError x : result.getGlobalErrors()) {
                    redirectAttribute.addFlashAttribute(x.getCode(), x.getDefaultMessage());
                }
                return "redirect:/goSchedulazioneSeduta";
            }
            DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
            Operatore operatore = (Operatore) utente;
            SedeLocale sedeLocale = operatore.getSedeLocale();

            Seduta seduta = new Seduta();
            seduta.setDataFinePrenotazione(sedutaForm.getDataFinePrenotazione());
            seduta.setDataSeduta(sedutaForm.getDataSeduta());
            seduta.setDataInizioPrenotazione(sedutaForm.getDataInizioPrenotazione());
            seduta.setNumeroPartecipanti(sedutaForm.getNumeroPartecipanti());
            seduta.setOraInizio(Time.valueOf(sedutaForm.getOrarioInizio()));
            seduta.setOraFine(Time.valueOf(sedutaForm.getOrarioFine()));
            seduta.setSedeLocale(sedeLocale.getCodiceIdentificativo());
            String luogo = Seduta.parseToLuogo(sedutaForm.getIndirizzo(), sedutaForm.getCitta(), sedutaForm.getCAP(), sedutaForm.getProvincia());
            seduta.setLuogo(luogo);
            organizzazioneSeduteService.schedulazioneSeduta(seduta);
        } catch (CannotSaveDataRepositoryException e) {
            redirectAttribute.addFlashAttribute(e.getTarget(), e.getMessage());
        }
        model.addAttribute("success", "Seduta schedulata con successo!");
        return "GUIGestioneUtente/dashboardOperatore";
    }

    @RequestMapping(value = "/goEliminaSeduta", method = RequestMethod.GET)
    public String goEliminaSeduta(HttpServletRequest request, RedirectAttributes redirectAttribute, Model model) {

        long idSeduta = Long.valueOf(request.getParameter("idSeduta"));
        model.addAttribute("idSeduta", idSeduta);
        return "GUIOrganizzazioneSedute/eliminaSeduta";
    }

    @RequestMapping(value = "/goModificaSeduta", method = RequestMethod.GET)
    public String goModificaSeduta(HttpServletRequest request, RedirectAttributes redirectAttribute, Model model) {

        long idSeduta = Long.valueOf(request.getParameter("idSeduta"));
        model.addAttribute("idSeduta", idSeduta);
        return "GUIOrganizzazioneSedute/modificaSeduta";
    }

    @RequestMapping(value = "/goIndisponibilita", method = RequestMethod.GET)
    public String indisponibilitaByOperatore(HttpServletRequest request, RedirectAttributes redirectAttribute, Model model) {
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        String codiceFiscale = request.getParameter("codiceFiscale");
        try{
            if(utente == null) new IllegalArgumentException();
            if(codiceFiscale.matches(Utente.CF_REGEX));
        }catch (Exception e){
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
            return "redirect:/error";
        }
        //TODO Vanno fatti prima i form
        //Aggiungi al model il form indisponibilità
        //redirect su servlet /indisponibilita
        return "redirect:/error";
    }

}
