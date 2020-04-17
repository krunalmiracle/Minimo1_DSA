
import edu.upc.eetac.dsa.Covid19Manager;
import edu.upc.eetac.dsa.Covid19ManagerImpl;
import edu.upc.eetac.dsa.models.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

//Models or Element Entity
//Swagger Imports
@Api(value = "/users", description = "Endpoint to User Service")
@Path("/Covid19Service")
public class Covid19Service {
    static final Logger logger = Logger.getLogger(Covid19Service.class);
    private Covid19Manager manager;
    //Estructura de datos
    Brote brote;
    List<Caso> listaCasos;
    public Covid19Service(){
        //Configuring Log4j, location of the log4j.properties file and must always be inside the src folder
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        this.manager = Covid19ManagerImpl.getInstance();
        if (this.manager.numBrotes() == 0) {
            //Adding Brotes
            manager = Covid19ManagerImpl.getInstance();
            //Initializing Object List
            listaCasos =  new LinkedList<Caso>();
            //Appending Caso en Lista
            //Example Date firstDate3 = new Date(int year, int month, int date, int hrs, int min, int sec);
            Date dataNacimiento = new Date(1997,9,23,16,55,20);
            Date dataInforme = new Date(2020,04,12,05,10,10);
            //Caso( String idCaso,String nombre, String apellidos, String genero, String correo, String direccion, Date fechaNacimiento,Date fechaInforme, String nivelRiesgo, String classificacion, int telefono)
            listaCasos.add(new Caso("001","Midoriya","Izuku","H","midoriya@ua.com","Musutafu, Japan", dataNacimiento,dataInforme,"alto","confirmado",993355));
            listaCasos.add(new Caso("002","Uraraka","Ochako","F","uraraka@ua.com","Musutafu, Japan", dataNacimiento,dataInforme,"alto","sospechoso",882244));
            listaCasos.add(new Caso("003","Bakugo","Kuzuya","M","bakugo@ua.com","Musutafu, Japan", dataNacimiento,dataInforme,"medio","sospechoso",882244));
            listaCasos.add(new Caso("004","Todoroki","Shen","M","todoroki@ua.com","Musutafu, Japan", dataNacimiento,dataInforme,"bajo","no caso",882244));
            brote = new Brote("001","SARS",listaCasos);
            manager.añadirBrote(brote);
            brote = new Brote("002","Dengu",listaCasos.get(2));
            manager.añadirBrote(brote);
            brote = new Brote("003","kechu",listaCasos.get(3));
            manager.añadirBrote(brote);
        }
    }
    //When multiple GET, PUT, POSTS & DELETE EXIST on the same SERVICE, path must be aggregated
    //Lista de Brotes
    @GET
    @ApiOperation(value = "Get all Brotes", notes = "Retrieves the list of Brotes")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Brote.class, responseContainer="List"),
    })
    @Path("/listBrotes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<Brote> user = this.manager.getListaBrote();
        GenericEntity<List<Brote>> entity = new GenericEntity<List<Brote>>(user) {};
        return Response.status(201).entity(entity).build()  ;
    }
    //Añadir un Brote
    //Adds a new user given multiple parameters(name & surname)
    @POST
    @ApiOperation(value = "create a new brote", notes = "Adds a new brote")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Brote.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/addUser/{id}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response newUser(@PathParam("name") String name,@PathParam("id") String idBrote ) {
        if (name.isEmpty() || idBrote.isEmpty())  return Response.status(500).entity(new Brote()).build();
        this.manager.añadirBrote(idBrote,name,listaCasos);
        return Response.status(201).entity(manager.getBrote(idBrote)).build();
    }

    //Añadir un caso sobre un broto
    //Adds a new object given multiple parameters(userId & gameObjectId)
    @PUT
    @ApiOperation(value = "Adds a Game object to user", notes = "Adds an existing Game Object to user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=User.class),
            @ApiResponse(code = 500, message = "Validation Error"),
            @ApiResponse(code = 404, message = "User/GameObject Not found Error")
    })
    @Path("/addCaso/{BrotoId}/{CasoId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addObject(@PathParam("BrotoId") String BrotoId,@PathParam("CasoId") String CasoId ) {
        if (BrotoId.isEmpty() || CasoId.isEmpty())  return Response.status(500).entity(new Brote()).build();
        else{
            Brote brote = manager.getBrote(BrotoId);
            //Caso caso = listaCasos.contains(new Caso())
            //if(user==null || gameObject ==null)  return Response.status(404).entity(new User()).build();
        }
        //manager.addUserGameObject(userId,gameObjectId);
        return Response.status(201).entity(manager.getBrote(BrotoId)).build();
    }
}
