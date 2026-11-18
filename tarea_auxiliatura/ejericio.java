class Persona {
    private String nombre;
    private int edad;
    private float peso;

    public Persona(String nombre, int edad, float peso) {
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
    }

    public String toString() {
        return nombre + " " + edad + " " + peso;
    }

    public int getEdad() {
        return edad;
    }

    public float getPeso() {
        return peso;
    }
}


class Cabina {
    private int nroCabina;
    private Persona[] personasAbordo;

    public Cabina(int nroCabina) {
        this.nroCabina = nroCabina;
        personasAbordo = new Persona[10];
    }

    public void agregarPersona(Persona p) {
        int cantidad = 0;
        float pesoTotal = 0;

        for (int i = 0; i < personasAbordo.length; i++) {
            if (personasAbordo[i] != null) {
                cantidad++;
                pesoTotal = pesoTotal + personasAbordo[i].getPeso();
            }
        }

        if (cantidad < 10 && pesoTotal + p.getPeso() <= 850) {
            for (int i = 0; i < personasAbordo.length; i++) {
                if (personasAbordo[i] == null) {
                    personasAbordo[i] = p;
                    break;
                }
            }
        }
    }

    public float getPesoTotal() {
        float peso = 0;

        for (int i = 0; i < personasAbordo.length; i++) {
            if (personasAbordo[i] != null) {
                peso = peso + personasAbordo[i].getPeso();
            }
        }
        return peso;
    }

    public int getCantidadPersonas() {
        int cantidad = 0;

        for (int i = 0; i < personasAbordo.length; i++) {
            if (personasAbordo[i] != null) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public boolean cumpleReglas() {
        return getCantidadPersonas() <= 10 &&
               getPesoTotal() <= 850;
    }

    public int getNroCabina() {
        return nroCabina;
    }

    public Persona[] getPersonasAbordo() {
        return personasAbordo;
    }
}


class Linea {
    private String color;
    private Persona[] filaPersonas;
    private Cabina[] cabinas;
    private int cantidadCabinas;

    public Linea(String color) {
        this.color = color;
        filaPersonas = new Persona[100];
        cabinas = new Cabina[100];
        cantidadCabinas = 0;
    }

    public void agregarPersona(Persona p) {
        for (int i = 0; i < filaPersonas.length; i++) {
            if (filaPersonas[i] == null) {
                filaPersonas[i] = p;
                break;
            }
        }
    }

    public void agregarCabina(int nroCab) {
        if (cantidadCabinas < cabinas.length) {
            cabinas[cantidadCabinas] = new Cabina(nroCab);
            cantidadCabinas++;
        }
    }

    public void mostrarCabinas() {
        for (int i = 0; i < cantidadCabinas; i++) {
            System.out.println("Cabina: " + cabinas[i].getNroCabina());

            Persona[] personas = cabinas[i].getPersonasAbordo();

            for (int j = 0; j < personas.length; j++) {
                if (personas[j] != null) {
                    System.out.println(personas[j]);
                }
            }
        }
    }

    public float getIngreso() {
        float ingreso = 0;

        for (int i = 0; i < cantidadCabinas; i++) {

            Persona[] personas = cabinas[i].getPersonasAbordo();

            for (int j = 0; j < personas.length; j++) {

                if (personas[j] != null) {

                    if (personas[j].getEdad() < 25 ||
                        personas[j].getEdad() > 60) {

                        ingreso = ingreso + 1.5f;

                    } else {

                        ingreso = ingreso + 3;
                    }
                }
            }
        }

        return ingreso;
    }

    public float getIngresoRegular() {
        float ingreso = 0;

        for (int i = 0; i < cantidadCabinas; i++) {

            Persona[] personas = cabinas[i].getPersonasAbordo();

            for (int j = 0; j < personas.length; j++) {

                if (personas[j] != null) {

                    if (personas[j].getEdad() >= 25 &&
                        personas[j].getEdad() <= 60) {

                        ingreso = ingreso + 3;
                    }
                }
            }
        }

        return ingreso;
    }

    public boolean cumpleReglas() {
        for (int i = 0; i < cantidadCabinas; i++) {
            if (cabinas[i].cumpleReglas() == false) {
                return false;
            }
        }

        return true;
    }

    public String getColor() {
        return color;
    }

    public Cabina[] getCabinas() {
        return cabinas;
    }

    public int getCantidadCabinas() {
        return cantidadCabinas;
    }
}


class MiTeleferico {
    private Linea[] lineas;
    private float cantidadIngresos;

    public MiTeleferico() {
        lineas = new Linea[3];
        cantidadIngresos = 0;
    }

    public void agregarPersonaFila(Persona p, String linea) {
        for (int i = 0; i < lineas.length; i++) {

            if (lineas[i] != null &&
                lineas[i].getColor().equals(linea)) {

                lineas[i].agregarPersona(p);
                break;
            }
        }
    }

    public void agregarLinea(Linea linea) {
        for (int i = 0; i < lineas.length; i++) {
            if (lineas[i] == null) {
                lineas[i] = linea;
                break;
            }
        }
    }

    public boolean agregarPersonaCabina(Persona p, String color, int nroCab) {

        for (int i = 0; i < lineas.length; i++) {

            if (lineas[i] != null &&
                lineas[i].getColor().equals(color)) {

                Cabina[] cabinas = lineas[i].getCabinas();

                for (int j = 0; j < lineas[i].getCantidadCabinas(); j++) {

                    if (cabinas[j].getNroCabina() == nroCab) {

                        int cantidadAntes =
                            cabinas[j].getCantidadPersonas();

                        cabinas[j].agregarPersona(p);

                        int cantidadDespues =
                            cabinas[j].getCantidadPersonas();

                        if (cantidadDespues > cantidadAntes) {
                            return true;
                        }

                        return false;
                    }
                }
            }
        }

        return false;
    }

    public boolean verificarCabinas() {

        for (int i = 0; i < lineas.length; i++) {

            if (lineas[i] != null) {

                if (lineas[i].cumpleReglas() == false) {
                    return false;
                }
            }
        }

        return true;
    }

    public float calcularIngresoTotal() {

        cantidadIngresos = 0;

        for (int i = 0; i < lineas.length; i++) {

            if (lineas[i] != null) {
                cantidadIngresos =
                    cantidadIngresos + lineas[i].getIngreso();
            }
        }

        return cantidadIngresos;
    }

    public Linea lineaMayorIngresoRegular() {

        Linea mayor = null;
        float ingresoMayor = 0;

        for (int i = 0; i < lineas.length; i++) {

            if (lineas[i] != null) {

                float ingreso =
                    lineas[i].getIngresoRegular();

                if (ingreso > ingresoMayor) {
                    ingresoMayor = ingreso;
                    mayor = lineas[i];
                }
            }
        }

        return mayor;
    }
}


public class Main {
    public static void main(String[] args) {

        // Personas
        Persona p1 = new Persona("Juan", 20, 70);
        Persona p2 = new Persona("Pedro", 30, 80);
        Persona p3 = new Persona("Maria", 65, 60);
        Persona p4 = new Persona("Luis", 22, 75);
        Persona p5 = new Persona("Ana", 40, 55);
        Persona p6 = new Persona("Carlos", 70, 85);

        // Lineas
        Linea amarillo = new Linea("Amarillo");
        Linea rojo = new Linea("Rojo");
        Linea verde = new Linea("Verde");

        // Agregar cabinas
        amarillo.agregarCabina(1);
        amarillo.agregarCabina(2);

        rojo.agregarCabina(1);
        rojo.agregarCabina(2);

        verde.agregarCabina(1);
        verde.agregarCabina(2);

        // MiTeleferico
        MiTeleferico m = new MiTeleferico();

        m.agregarLinea(amarillo);
        m.agregarLinea(rojo);
        m.agregarLinea(verde);

        // Agregar personas a las cabinas
        m.agregarPersonaCabina(p1, "Amarillo", 1);
        m.agregarPersonaCabina(p2, "Amarillo", 1);

        m.agregarPersonaCabina(p3, "Rojo", 1);
        m.agregarPersonaCabina(p4, "Rojo", 1);

        m.agregarPersonaCabina(p5, "Verde", 1);
        m.agregarPersonaCabina(p6, "Verde", 1);

        // a) Agregar una persona a la cabina nro X
        Persona p7 = new Persona("Edgar", 24, 70);

        if (m.agregarPersonaCabina(p7, "Amarillo", 1)) {
            System.out.println("Persona agregada correctamente");
        } else {
            System.out.println("No se puede agregar la persona");
        }

        // b) Verificar reglas de las cabinas
        if (m.verificarCabinas()) {
            System.out.println("Todas las cabinas cumplen las reglas");
        } else {
            System.out.println("Hay cabinas que no cumplen las reglas");
        }

        // c) Calcular ingreso total
        float total = m.calcularIngresoTotal();

        System.out.println("Ingreso total: " + total + " Bs");

        // d) Linea con mayor ingreso solamente con tarifa regular
        Linea mayor = m.lineaMayorIngresoRegular();

        if (mayor != null) {
            System.out.println(
                "Linea con mayor ingreso regular: "
                + mayor.getColor()
            );

            System.out.println(
                "Ingreso regular: "
                + mayor.getIngresoRegular()
                + " Bs"
            );
        }
    }
}