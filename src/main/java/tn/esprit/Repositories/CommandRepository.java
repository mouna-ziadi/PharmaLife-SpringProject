package tn.esprit.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tn.esprit.Entities.Command;
import tn.esprit.Entities.User;

import java.util.List;

public interface CommandRepository extends CrudRepository<Command, Integer> {
    @Query("select d from Command d inner join d.userCommand ee where ee.idUser = ?1")
    List<Command> getCommandByUserCommand (Integer idUser);

    @Query("SELECT r FROM Command r WHERE r.userCommand.idUser =:user")
    User findCommandByUserCommand (@Param("user") int user);


    @Query("select d from Command d inner join d.userCommand ee where ee.idUser = ?1")
    List<Command> getCommandsByUser(Integer idUser);

}