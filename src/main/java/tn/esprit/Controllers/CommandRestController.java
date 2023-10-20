package tn.esprit.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.Entities.Command;
import tn.esprit.Entities.Product;
import tn.esprit.Services.ICommandService;
import tn.esprit.Services.IProductService;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/commands")

public class CommandRestController {
    ICommandService iCommandService;

    IProductService iProductService;

    @PostMapping("/add-command")
    public String addCommand(@RequestBody Command d){
       // Command command = iCommandService.addCommand(d);
        return iCommandService.addCommand(d);
    }

    @PutMapping("/editCommand")
    public Command updateCommand(@RequestBody Command d){
        Command command = iCommandService.updateCommand(d);
        return command;
    }

    @DeleteMapping("/delete-command/{idCommand}")
    public void deleteCommand(@PathVariable("idCommand") Integer IdCommand){
        iCommandService.deleteCommand(IdCommand);
    }

    @GetMapping("/getMyCommand/{idUser}")
    public List<Command> getCommandByUser (@PathVariable("idUser") Integer idUser)
    {
        return iCommandService.retrieveMyCommand(idUser);

    }


    @GetMapping("/retrieveAllCommand")
    public List<Command> retrieveAllCommand(){
        List<Command> listCommand = iCommandService.retrieveAllCommand();
        return listCommand;
    }

    @GetMapping("/retrieveCommand/{idCommand}")
    public Command retrieveCommand(@PathVariable("idCommand")Integer IdCommand){
        return iCommandService.retrieveCommand(IdCommand);
    }

    @GetMapping("/statisticsCommandStatus")
    HashMap<String, Integer> CommandByStatus(){
        return iCommandService.CommandByStatus();
    }

    @PostMapping("/addCommandByMail")
    public String addCommandByMail(@RequestBody Command d){

        return iCommandService.addCommandByMail(d);
    }

    @PostMapping("/assignCommandToProduct/{id_product}/{id_user}")
    public String assignCommandToProduct(@RequestBody Command r, @PathVariable("id_product")Integer IdProduct
            , @PathVariable("id_user")Integer IdUser){

        iCommandService.assignCommandToProduct(r,IdProduct,IdUser);
        return "command added";
    }


    @GetMapping("/getMyCommands/{idUser}")
    public List<Command> getMyCommands (@PathVariable("idUser") Integer idUser)
    {
        return iCommandService.retrieveMyCommands(idUser);

    }

}