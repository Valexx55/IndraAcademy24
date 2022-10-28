export class Alumno {

    id : number;
    nombre : string;
    apellido : string;
    email : string;
    edad : number;
    creadoEn : string;
    fotoHashCode : number;

    constructor ()
    {
        this.id=0;
        this.nombre="";
        this.apellido="";
        this.email="";
        this.edad=0;
        this.creadoEn="";
    }

    public toString () : string{
        return (`${this.id} ${this.nombre} ${this.apellido} ${this.email} ${this.edad} ${this.creadoEn}`);
    }


    /* 

     //"strictPropertyInitialization": false, 
     https://stackoverflow.com/questions/49699067/property-has-no-initializer-and-is-not-definitely-assigned-in-the-construc

    private Long id;
	
	private String nombre;
	private String apellido;
	private String email;
	private int edad;
	
	@Column(name = "creado_en")
	@Temporal(TemporalType.TIMESTAMP) //la fecha con HHMMSS DDMMAA
	private Date creadoEn;
	*/

}
